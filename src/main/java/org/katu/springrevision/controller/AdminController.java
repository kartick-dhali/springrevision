package org.katu.springrevision.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.katu.springrevision.entity.UserEntity;
import org.katu.springrevision.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin API", description = "Get all user")
public class AdminController {

    @Autowired
    private UserEntryService userEntryService;

    @GetMapping("/getusers")
    public ResponseEntity<?> getAllUsers() {
        List<UserEntity> users = userEntryService.findAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(users);
    }
}
