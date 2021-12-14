package com.hanu.filestorage.service;

import com.hanu.filestorage.entity.Setting;
import com.hanu.filestorage.repository.SettingRepository;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    private SettingRepository settingRepository;

    public SettingService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    public Setting createSetting(Setting setting){
       return settingRepository.save(setting);
    }
}
