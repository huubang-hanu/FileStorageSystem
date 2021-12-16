package com.hanu.filestorage.controller;

import com.hanu.filestorage.entity.Setting;
import com.hanu.filestorage.service.SettingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SettingController {
    private SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @PostMapping("/api/setting/create")
    public ResponseEntity<Setting> createSetting(@RequestBody Setting setting){
        Setting settingResponse = settingService.createSetting(setting);
        return new ResponseEntity<Setting>(settingResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Setting> updateSetting(@PathVariable Integer id, @RequestBody Setting newSetting){
        return null;
    }



}
