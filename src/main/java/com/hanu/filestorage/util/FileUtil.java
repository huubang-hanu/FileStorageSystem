package com.hanu.filestorage.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Component
public class FileUtil {
    public static final String FILE_LOCATION = System.getProperty("user.home") + "Downloads/uploads/";

    /**
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public Resource getFileByFileName(String fileName) throws IOException {
        Path filePath = Paths.get(FILE_LOCATION).toAbsolutePath().normalize().resolve(fileName);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException(fileName + " was not found on the server");
        }
        Resource resource = new UrlResource(filePath.toUri());
        return resource;
    }


    /**
     *
     * @param file
     * @return
     * @throws IOException
     */
    public String saveFileToFolder(MultipartFile file, int version) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename()) +"_v"+version;
        Path filePath = Paths.get(FILE_LOCATION, fileName).toAbsolutePath().normalize();
        Files.copy(file.getInputStream(), filePath, REPLACE_EXISTING);
        return filePath.toString();
    }


    /**
     * Delete file from path
     * @param path
     * @return
     * @throws IOException
     */
    public boolean deleteFileFromFolder(String path) throws IOException {
        Path filePath = Paths.get(path).toAbsolutePath().normalize();
        Files.delete(filePath);
        return true;
    }



}
