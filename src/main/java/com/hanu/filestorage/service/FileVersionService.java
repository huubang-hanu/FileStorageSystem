package com.hanu.filestorage.service;

import com.hanu.filestorage.entity.File;
import com.hanu.filestorage.entity.FileVersion;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileVersionService {

    public boolean creteFileVersion(File id, MultipartFile file){
        //Custom file name and Store file in directory
        //Create file version
        //Save to database
        return false;
    }


    public List<FileVersion> findAll(){

        return null;
    }


    private String saveFileToDirectory(MultipartFile file){

        return "path";
    }

    public List<FileVersion> getVersionsByFileId(Integer fileId){
        return null;
    }
}
