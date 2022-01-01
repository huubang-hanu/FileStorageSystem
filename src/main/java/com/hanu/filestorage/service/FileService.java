package com.hanu.filestorage.service;

import com.hanu.filestorage.dto.FileDTO;
import com.hanu.filestorage.entity.File;
import com.hanu.filestorage.entity.FileVersion;
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
    private ModelMapper modelMapper;

    public FileService(FileUtil fileUtil,
                       FileRepository fileRepo,
                       FileVersionService fileVersionService,
                       ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.fileRepo = fileRepo;
        this.fileVersionService =fileVersionService;
        this.modelMapper = modelMapper;
    }

    public File storeFile(MultipartFile newFile) {
        String fileName = StringUtils.cleanPath(newFile.getOriginalFilename());
        File file = fileRepo.findByName(fileName);
        boolean isExist = file != null && file.getMime().equals(newFile.getContentType());
        String path = fileUtil.saveFileToFolder(newFile);
        FileVersion fileVersion = FileVersion.builder()
                .numberOfDownload(0)
                .isDeleted(false)
                .fileSize(newFile.getSize())
                .path(path)
                .build();


        if( isExist) {
            fileVersion.setVersion(file.getFileVersions().size() +1);
            fileVersion.setFile(file);
            file.getFileVersions().add(fileVersion);
            file.setNumberOfVersion(file.getNumberOfVersion() +1);
            fileVersionService.createFileVersion(fileVersion);
           return  fileRepo.findById(file.getId()).orElseThrow(() ->new ResourceNotFoundException("File is not exist"));
        } else{

            File savedFile = File.builder()
                    .mime(newFile.getContentType())
                    .name(fileName)
                    .numberOfVersion(1)
                    .build();

            fileVersion.setVersion(1);
            fileVersion.setFile(savedFile);
            File result = fileRepo.save(savedFile);
            fileVersionService.createFileVersion(fileVersion);


            return fileRepo.findById(result.getId()).orElseThrow(() ->new ResourceNotFoundException("File is not exist"));
        }
    }


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
     *
     * @param offset
     * @param pageSize
     * @return
     */
    public Page<File> getFilesWithPagination(int offset, int pageSize){
        Page<File> files = fileRepo.findAll(PageRequest.of(offset, pageSize));
        return files;
    }
    /**
     * Delete version of File
     * if file has no version --> Delete this file information
     * @return
     */
    public boolean deleteFile(String  fileName, Integer fileVersionId){
       //Get file which have version is deleted
        File file = fileRepo.findByName(fileName);

        //Update number of version
        file.setNumberOfVersion(
                file.getNumberOfVersion() - 1
        );
        fileRepo.save(file);

        //Update file version status
        FileVersion fileVersion = fileVersionService.getById(fileVersionId);
        fileVersion.setDeleted(true);
        fileVersionService.update(fileVersion);
        return true;

    }
}
