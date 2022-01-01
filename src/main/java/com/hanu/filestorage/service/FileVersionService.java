package com.hanu.filestorage.service;

import com.hanu.filestorage.entity.FileVersion;
import com.hanu.filestorage.exception.ResourceNotFoundException;
import com.hanu.filestorage.repository.FileVersionRepository;

import com.hanu.filestorage.util.FileUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileVersionService {


    private FileVersionRepository fileVersionRepo;
    private FileUtil fileUtil;

    public FileVersionService(FileVersionRepository fileVersionRepo, FileUtil fileUtil) {
        this.fileVersionRepo = fileVersionRepo;
        this.fileUtil = fileUtil;
    }


    public FileVersion getById(Integer fileVersionId){
        FileVersion fileVersion = fileVersionRepo.findById(fileVersionId)
                .orElseThrow(()-> new ResourceNotFoundException("File is not exist"));
        return fileVersion;
    }

    public FileVersion createFileVersion(FileVersion fileVersion){
        return fileVersionRepo.save(fileVersion);
    }

    public FileVersion update(FileVersion fileVersion){
        return fileVersionRepo.save(fileVersion);
    }

    public FileVersion delete(Integer id){
        return  this.fileVersionRepo.findById(id).map(version -> {
            this.fileVersionRepo.delete(version);
            return version;
        }).orElseThrow(() -> new ResourceNotFoundException("File Version is not exist"));
    }

}
