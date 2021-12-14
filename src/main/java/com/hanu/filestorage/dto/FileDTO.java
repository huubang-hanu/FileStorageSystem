package com.hanu.filestorage.dto;

import com.hanu.filestorage.entity.FileVersion;
import lombok.Data;
import java.util.Set;

@Data
public class FileDTO {
    private Integer id;
    private String name;
    private String mime;
    private int numberOfVersion;
    private Set<FileVersion> fileVersions;
}
