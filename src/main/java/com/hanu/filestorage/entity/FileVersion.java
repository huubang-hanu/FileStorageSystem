package com.hanu.filestorage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "file_id", nullable = false)
    private File file;

    private int version;
    private long fileSize;
    private int numberOfDownload;
    private String path;

    @CreatedDate
    @Column(name = "created_at")
    private Timestamp createTime;

    @Column(name = "updated_at")
    @LastModifiedDate
    private Timestamp updatedAt;
}
