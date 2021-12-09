package com.hanu.filestorage.service;

import com.hanu.filestorage.entity.File;
import com.hanu.filestorage.repository.FileRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileService {



    public String storeFile(MultipartFile file){
        //Check mime and file size

        //Check file exist in system

        //if file's name is not exist --> Create File and FileVersion

        //if file's name already exit --> Update file(version numer) and create new FileVersion

        //Return file File information and all of its versions
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







}
