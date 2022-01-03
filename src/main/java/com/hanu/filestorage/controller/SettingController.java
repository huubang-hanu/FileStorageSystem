package com.hanu.filestorage.controller;

import com.hanu.filestorage.dto.SettingDTO;
import com.hanu.filestorage.service.SettingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/setting")
public class SettingController {
    private SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SettingDTO> findById(@PathVariable Integer id){
        SettingDTO settingDTO =  settingService.getSetting(id);
        return new ResponseEntity<SettingDTO>(settingDTO, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<SettingDTO> createSetting(@RequestBody SettingDTO setting){
        SettingDTO settingResponse = settingService.createSetting(setting);
        return new ResponseEntity<SettingDTO>(settingResponse, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SettingDTO> updateSetting(@PathVariable Integer id, @RequestBody SettingDTO newSetting){
        SettingDTO settingDTO = settingService.updateSetting(id, newSetting);
        return new ResponseEntity(settingDTO, HttpStatus.OK);
    }
}
