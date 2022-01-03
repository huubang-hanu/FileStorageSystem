package com.hanu.filestorage.service;

import com.hanu.filestorage.dto.SettingDTO;
import com.hanu.filestorage.entity.Setting;
import com.hanu.filestorage.exception.ResourceNotFoundException;
import com.hanu.filestorage.repository.SettingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SettingService {

    private SettingRepository settingRepository;
    private ModelMapper mapper;

    public SettingService(SettingRepository settingRepository,
                          ModelMapper mapper) {
        this.mapper = mapper;
        this.settingRepository = settingRepository;
    }

    /**
     * Create setting
     * @param settingDTO
     * @return
     */
    public SettingDTO createSetting(SettingDTO settingDTO){
        Setting setting = mapper.map(settingDTO, Setting.class);

       return mapper.map(settingRepository.save(setting), SettingDTO.class);
    }

    /**
     * Get setting by id
     * @param id
     * @return
     */
    public SettingDTO getSetting(Integer id){
        return settingRepository
                .findById(id)
                .map(setting ->  mapper.map(setting, SettingDTO.class))
                .orElseThrow(() ->new ResourceNotFoundException("Setting is not exist"));
    }

    /**
     * Update setting
     * @param id
     * @param settingDTO
     * @return
     */
    public SettingDTO updateSetting(Integer id, SettingDTO settingDTO){

        return settingRepository.findById(id).map(setting -> {
            setting.setMaxFileSize(settingDTO.getMaxFileSize());
            setting.setMimeTypeAllowed(settingDTO.getMimeTypeAllowed());
            Setting updatedSetting = settingRepository.save(setting);
            return mapper.map(updatedSetting, SettingDTO.class);
        }).orElseThrow(() -> new ResourceNotFoundException("This setting is not exist"));
    }

}
