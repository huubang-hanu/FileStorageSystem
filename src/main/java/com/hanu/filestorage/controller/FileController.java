package com.hanu.filestorage.controller;

import com.hanu.filestorage.dto.FileDTO;
import com.hanu.filestorage.entity.File;
import com.hanu.filestorage.exception.InvalidFileException;
import com.hanu.filestorage.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/files")
public class FileController {

    Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<File> createFile(@RequestBody MultipartFile file){
        if(file.getContentType() == null){
            throw new InvalidFileException("Invalid file");
        }
        File savedFile = fileService.storeFile(file);
        return new ResponseEntity<File>(savedFile, HttpStatus.CREATED);
    }


    @GetMapping("")
    public ResponseEntity<List<FileDTO>> getAll(){
       List<FileDTO> files = fileService.getAll();
       return new ResponseEntity<List<FileDTO>>(files, HttpStatus.OK);
    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    public ResponseEntity<Page<File>> getFilesWithPagination(@PathVariable int offset, @PathVariable int pageSize){
        Page<File> filesWithPagination = fileService.getFilesWithPagination(offset, pageSize);
        return new ResponseEntity<Page<File>>(filesWithPagination, HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}/{fileVersionId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName,
                                                 @PathVariable Integer fileVersionId,
                                                 HttpServletRequest request){
        Resource resource = fileService.downloadFile(fileName, fileVersionId);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", fileName);
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .headers(httpHeaders)
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}/{fileVersionId}")
    public ResponseEntity<File> deleteFile(@PathVariable String fileName,
                                           @PathVariable Integer fileVersionId){
        fileService.deleteFile(fileName, fileVersionId);
        return null;
    }
}
