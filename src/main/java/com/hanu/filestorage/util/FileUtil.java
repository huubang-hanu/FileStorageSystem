package com.hanu.filestorage.util;

import com.hanu.filestorage.exception.ResourceNotFoundException;
import com.hanu.filestorage.exception.StoreFileException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Component
public class FileUtil {
    public static final String FILE_LOCATION = "./src/main/resources/uploads";

    /**
     * Get file by path
     * @param path
     * @return
     * @throws IOException
     */
    public Resource getFileByPath(String path)  {
        Path filePath = Paths.get(path).toAbsolutePath().normalize();
        if (!Files.exists(filePath)) {
            throw new ResourceNotFoundException( "This file path was not found on the server");
        }
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new ResourceNotFoundException( "Resource was not found on the server");
        }

        return resource;

    }


    /**
     *  Save file and return path
     * @param file
     * @return
     * @throws IOException
     */
    public String saveFileToFolder(MultipartFile file) {
        //Create unique file name
        String fileName = UUID.randomUUID() + StringUtils.cleanPath(file.getOriginalFilename());

        //Create file path
        Path filePath = Paths.get(FILE_LOCATION, fileName).toAbsolutePath().normalize();
        try {
            Files.copy(file.getInputStream(), filePath, REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StoreFileException("Store file fail");
        }
        return filePath.toString();
    }
}
