package com.hanu.filestorage.service;

import com.hanu.filestorage.entity.FileVersion;
import com.hanu.filestorage.exception.ResourceNotFoundException;
import com.hanu.filestorage.repository.FileVersionRepository;

import com.hanu.filestorage.util.FileUtil;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileVersionService {


    private FileVersionRepository fileVersionRepo;
    private FileUtil fileUtil;

    public FileVersionService(FileVersionRepository fileVersionRepo, FileUtil fileUtil) {
        this.fileVersionRepo = fileVersionRepo;
        this.fileUtil = fileUtil;
    }

    public List<FileVersion> getAllFileVersionInfo() {
        List<FileVersion> fileVersions = fileVersionRepo.findAll();
        return fileVersions;
    }

    public FileVersion getById(Integer fileVersionId){
        FileVersion fileVersion = fileVersionRepo.findById(fileVersionId)
                .orElseThrow(()-> new ResourceNotFoundException("File is not exist"));
        return fileVersion;
    }

    public List<FileVersion> getVersionsByFileId(Integer fileId) {
        return fileVersionRepo.findFileVersionsByFileId(fileId);
    }


}
