package com.hanu.filestorage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileVersion {

    @Id
    private Integer id;
    @ManyToOne
    private File fileId;
    private int version;
    private double fileSize;
    private int numberOfDownload;
    private String path;
    private Date createTime;
}
