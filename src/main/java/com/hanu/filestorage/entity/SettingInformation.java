package com.hanu.filestorage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SettingInformation {

    @Id()
    private Integer id;

    private double maxFileSize;
    private int itemPerPage;
    private String mineTypeAllowed;
    private Date lastUpdatedTime;
}
