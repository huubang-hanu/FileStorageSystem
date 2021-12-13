package com.hanu.filestorage.repository;

import com.hanu.filestorage.entity.FileVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileVersionRepository extends JpaRepository<FileVersion, Integer> {

    List<FileVersion> findFileVersionsByFileId(Integer fileId);
}
