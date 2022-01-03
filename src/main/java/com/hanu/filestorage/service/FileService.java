package com.hanu.filestorage.service;

import com.hanu.filestorage.dto.FileDTO;
import com.hanu.filestorage.dto.SettingDTO;
import com.hanu.filestorage.entity.File;
import com.hanu.filestorage.entity.FileVersion;
import com.hanu.filestorage.exception.InvalidFileException;
import com.hanu.filestorage.exception.ResourceNotFoundException;
import com.hanu.filestorage.repository.FileRepository;
import com.hanu.filestorage.repository.FileVersionRepository;
import com.hanu.filestorage.util.FileUtil;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    private FileUtil fileUtil;
    private FileRepository fileRepo;
    private FileVersionService fileVersionService;
    private SettingService settingService;
    private ModelMapper modelMapper;

    public FileService(FileUtil fileUtil,
                       FileRepository fileRepo,
                       FileVersionService fileVersionService,
                       SettingService settingService,
                       ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.fileRepo = fileRepo;
        this.fileVersionService =fileVersionService;
        this.settingService = settingService;
        this.modelMapper = modelMapper;
    }

    /**
     *  Save file to folder and update database
     *
     * @param newFile
     * @return
     */
    public File storeFile(MultipartFile newFile) {
        //Get setting information
        SettingDTO settingDTO = this.settingService.getSetting(1);

        //Validate file type upload
        if(!newFile.getContentType().equals(settingDTO.getMimeTypeAllowed())){
            throw new InvalidFileException("This file type is not allowed");
        }

        //Validate file size upload
        if(settingDTO.getMaxFileSize()*1048576 < newFile.getSize()){
            throw  new InvalidFileException("Out of file size upload");
        }

        String fileName = StringUtils.cleanPath(newFile.getOriginalFilename());
        //Check file exist
        File file = fileRepo.findByName(fileName);
        boolean isExist = file != null && file.getMime().equals(newFile.getContentType());

        //Save file
        String path = fileUtil.saveFileToFolder(newFile);
        //Create file version
        FileVersion fileVersion = FileVersion.builder()
                .numberOfDownload(0)
                .isDeleted(false)
                .fileSize(newFile.getSize())
                .path(path)
                .build();


        if( isExist) { //If file already exists, we update new version for it
            fileVersion.setVersion(file.getFileVersions().size() +1);
            fileVersion.setFile(file);
            file.getFileVersions().add(fileVersion);
            file.setNumberOfVersion(file.getNumberOfVersion() +1);
            fileVersionService.createFileVersion(fileVersion);
           return  fileRepo.findById(file.getId())
                            .orElseThrow(() ->new ResourceNotFoundException("File is not exist"));
        } else{ //If this file is not exist, create new file with version 1

            File savedFile = File.builder()
                    .mime(newFile.getContentType())
                    .name(fileName)
                    .numberOfVersion(1)
                    .build();
            fileVersion.setVersion(1);
            fileVersion.setFile(savedFile);
            File result = fileRepo.save(savedFile);
            fileVersionService.createFileVersion(fileVersion);

            //Return file upload with version
            return fileRepo.findById(result.getId())
                            .orElseThrow(() ->new ResourceNotFoundException("File is not exist"));
        }
    }


    /**
     *
     * @param fileName
     * @param fileVersionId
     * @return
     */
    public Resource downloadFile( String fileName,Integer fileVersionId){
        FileVersion fileVersion = fileVersionService.getById(fileVersionId);
        Resource resource = fileUtil.getFileByPath(fileVersion.getPath());
        if(resource != null){
           fileVersion.setNumberOfDownload(fileVersion.getNumberOfDownload()+1);
           fileVersionService.update(fileVersion);
        }
        return resource;
    }
    /**
     * Receive pagination information
     * Load files in database
     * @return
     */
    public List<FileDTO> getAll(){
        return fileRepo.findAll()
                .stream()
                .map(file -> modelMapper.map(file, FileDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Delete version of File
     * if file has no version --> Delete this file information
     * @return
     */
    public void deleteFile(String  fileName, Integer fileVersionId){
       //Get file which have version is deleted
        File file = fileRepo.findByName(fileName);

        //Update number of version
        file.setNumberOfVersion(
                file.getNumberOfVersion() - 1
        );
        fileRepo.save(file);

        //Update file version status to be deleted
        FileVersion fileVersion = fileVersionService.getById(fileVersionId);
        fileVersion.setDeleted(true);
        fileVersionService.update(fileVersion);
    }
}
