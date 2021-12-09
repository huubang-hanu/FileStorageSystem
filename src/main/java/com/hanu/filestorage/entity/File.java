package com.hanu.filestorage.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    private Set<FileVersion> fileVersions;
}
