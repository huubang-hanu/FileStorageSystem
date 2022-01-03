package com.hanu.filestorage.dto;

import com.hanu.filestorage.entity.FileVersion;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
public class FileDTO {
    private Integer id;
    private String name;
    private String mime;
    private int numberOfVersion;
    private Date updatedAt;
    private Set<FileVersion> fileVersions;
}
