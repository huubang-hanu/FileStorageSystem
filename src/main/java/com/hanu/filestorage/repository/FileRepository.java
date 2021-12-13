package com.hanu.filestorage.repository;

import com.hanu.filestorage.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
    boolean existsFileByName(String name);
    File findByName(String name);
}
