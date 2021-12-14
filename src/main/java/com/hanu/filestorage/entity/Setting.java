package com.hanu.filestorage.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private double maxFileSize;
    private int itemPerPage;
    private String mimeTypeAllowed;

    @CreatedDate
    private Date createAt;

    @LastModifiedDate
    private Date updateAt;
}
