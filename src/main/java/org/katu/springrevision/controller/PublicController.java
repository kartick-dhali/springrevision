package org.katu.springrevision.controller;

import org.katu.springrevision.entity.UserEntity;
import org.katu.springrevision.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/health-check")
    public String  healthCheck(){
        return "Ok";
    }

    @PostMapping("/enroll")
    public void CreateUser(@RequestBody UserEntity user){
        userEntryService.saveEntry(user);
    }
}
