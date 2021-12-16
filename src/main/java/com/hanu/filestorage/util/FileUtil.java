package com.hanu.filestorage.util;

import com.hanu.filestorage.exception.StoreFileException;
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
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Component
public class FileUtil {
    public static final String FILE_LOCATION = "./src/main/resources/uploads";

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
    public String saveFileToFolder(MultipartFile file) {
        String fileName = UUID.randomUUID() + StringUtils.cleanPath(file.getOriginalFilename());
        Path filePath = Paths.get(FILE_LOCATION, fileName).toAbsolutePath().normalize();
        try {
            Files.copy(file.getInputStream(), filePath, REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StoreFileException("Store file fail");
        }
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
