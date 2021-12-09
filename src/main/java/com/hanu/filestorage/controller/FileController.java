package com.hanu.filestorage.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

    Logger logger = LoggerFactory.getLogger(FileController.class);

    @GetMapping("/")
    public String home(){
        logger.info("In File Controller");
        return "Hello anh em";
    }

}
