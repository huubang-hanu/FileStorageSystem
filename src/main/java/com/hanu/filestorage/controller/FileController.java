package com.hanu.filestorage.controller;

import com.hanu.filestorage.entity.File;
import com.hanu.filestorage.repository.FileRepository;
import com.hanu.filestorage.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
public class FileController {

    Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/api/files/create")
    public ResponseEntity<File> createFile(@RequestBody MultipartFile file){
        logger.info("In File Controller");

        File savedFile = null;
        try {
            savedFile = fileService.storeFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<File>(savedFile, HttpStatus.CREATED);
    }


    @GetMapping("/api/files")
    public ResponseEntity<List<File>> getAll(){
       List<File> files = fileService.getAll();
       return new ResponseEntity<List<File>>(files, HttpStatus.OK);
    }

}
