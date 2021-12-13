package com.hanu.filestorage.service;

import com.hanu.filestorage.entity.File;
import com.hanu.filestorage.entity.FileVersion;
import com.hanu.filestorage.repository.FileRepository;
import com.hanu.filestorage.util.FileUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileService {
    private FileUtil fileUtil;
    private FileRepository fileRepo;

    public FileService(FileUtil fileUtil, FileRepository fileRepo) {
        this.fileUtil = fileUtil;
        this.fileRepo = fileRepo;
    }

    @SneakyThrows
    public String storeFile(MultipartFile newFile){
        Integer existedFileId = checkFileExist(newFile);
        String fileName = StringUtils.cleanPath(newFile.getOriginalFilename());
        long fileSize = newFile.getSize();
        if( existedFileId != null) {
            String path = fileUtil.saveFileToFolder(newFile, 1);
            FileVersion fileVersion = new FileVersion();
        }

        return null;
    }

    /**
     * Receive pagination information
     * Load files in database
     * @return
     */
    public List<File> getAll(){


        return null;
    }

    /**
     * Delete version of File
     * if file has no version --> Delete this file information
     * @return
     */
    public boolean deleteFile(Integer fileId, Integer fileVersionId){
        //delete fileversion with fileVersionId
        //if list version of File is null -- remove File

        return false;
    }

    /**
     * Check file exist by file name and mime
     * @param newFile
     * @return
     */
    public Integer checkFileExist(MultipartFile newFile){
        String fileName = StringUtils.cleanPath(newFile.getOriginalFilename());
        File file = fileRepo.findByName(fileName);
        if(file != null && file.getMime().equals(newFile.getContentType())){
            return file.getId();
        }
        return null;
    }





}
