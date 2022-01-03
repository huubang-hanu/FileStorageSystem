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

    /**
     * Get file version by Id
     * @param fileVersionId
     * @return
     */
    public FileVersion getById(Integer fileVersionId){
        FileVersion fileVersion = fileVersionRepo.findById(fileVersionId)
                .orElseThrow(()-> new ResourceNotFoundException("File is not exist"));
        return fileVersion;
    }

    /**
     * Create file version
     * @param fileVersion
     * @return
     */
    public FileVersion createFileVersion(FileVersion fileVersion){
        return fileVersionRepo.save(fileVersion);
    }

    /**
     * Update file version
     * @param fileVersion
     * @return
     */
    public FileVersion update(FileVersion fileVersion){
        return fileVersionRepo.save(fileVersion);
    }

}
