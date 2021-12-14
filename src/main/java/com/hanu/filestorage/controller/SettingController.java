package com.hanu.filestorage.controller;

import com.hanu.filestorage.entity.Setting;
import com.hanu.filestorage.service.SettingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/setting")
public class SettingController {
    private SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @PostMapping("/")
    public ResponseEntity<Setting> createSetting(@RequestBody Setting setting){

        Setting settingResponse = settingService.createSetting(setting);
        return new ResponseEntity<Setting>(settingResponse, HttpStatus.CREATED);
    }

}
