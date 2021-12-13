package com.hanu.filestorage.repository;

import com.hanu.filestorage.entity.SettingInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingInformationRepository extends JpaRepository<SettingInformation, Integer> {
}
