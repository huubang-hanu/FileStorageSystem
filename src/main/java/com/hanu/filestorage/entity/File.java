package com.hanu.filestorage.entity;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name = "mime")
    private String mime;

    @Column(name = "number_of_version")
    private int numberOfVersion;

    @OneToMany(mappedBy = "file", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<FileVersion> fileVersions = new HashSet<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false, updatable = false)
    @LastModifiedDate
    private Date updatedAt;

    public void addFileVersion(FileVersion version){
        fileVersions.add(version);
        version.setFile(this);
    }
}
