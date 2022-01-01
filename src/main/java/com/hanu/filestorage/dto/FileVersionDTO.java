package com.hanu.filestorage.dto;

import com.hanu.filestorage.entity.File;

import java.util.Date;

public class FileVersionDTO {
    private Integer id;
    private int version;
    private boolean isDeleted;
    private int numberOfDownload;
    private long fileSize;
    private Date createdAt;
}
