package com.hanu.filestorage.dto;

import lombok.Data;

@Data
public class SettingDTO {
    private Integer id;
    private double maxFileSize;
    private String mimeTypeAllowed;
}
