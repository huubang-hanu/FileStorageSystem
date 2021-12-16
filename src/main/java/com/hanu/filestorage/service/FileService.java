package com.hanu.filestorage.service;

import com.hanu.filestorage.entity.File;
import com.hanu.filestorage.entity.FileVersion;
import com.hanu.filestorage.exception.ResourceNotFoundException;
import com.hanu.filestorage.repository.FileRepository;
import com.hanu.filestorage.repository.FileVersionRepository;
import com.hanu.filestorage.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {
    private FileUtil fileUtil;
    private FileRepository fileRepo;
    private FileVersionService fileVersionService;

    public FileService(FileUtil fileUtil, FileRepository fileRepo, FileVersionService fileVersionService) {
        this.fileUtil = fileUtil;
        this.fileRepo = fileRepo;
        this.fileVersionService =fileVersionService;
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
            fileVersion.setVersion(file.getNumberOfVersion() +1);
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

    /**
     * Receive pagination information
     * Load files in database
     * @return
     */
    public List<File> getAll(){
        return fileRepo.findAll();
    }

    /**
     * Delete version of File
     * if file has no version --> Delete this file information
     * @return
     */
    public boolean deleteFile(Integer fileId, Integer fileVersionId){
        return false;
    }






}
