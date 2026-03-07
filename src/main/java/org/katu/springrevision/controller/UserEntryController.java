package org.katu.springrevision.controller;

import org.katu.springrevision.entity.UserEntity;
import org.katu.springrevision.repository.UserEntryRepository;
import org.katu.springrevision.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserEntryController {
    @Autowired
    private UserEntryService userEntryService;
    @Autowired
    private UserEntryRepository userEntryRepository;
//    @GetMapping
//    public List<UserEntity> getAllUser(){
//        return userEntryService.findAll();
//    }

    @GetMapping
    public String getUserName(){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        assert authentication != null;
        return authentication.getName();
    }
    @PutMapping
    public ResponseEntity<?> UpdateUser(@RequestBody UserEntity userEntity){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        assert authentication != null;
        String username =  authentication.getName();
        UserEntity user = userEntryService.findByUserName(username);
        if(user != null){
            user.setPassword(userEntity.getPassword());
            user.setUserName(userEntity.getUserName());
            userEntryService.saveEntry(user);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody UserEntity userEntity){
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        assert authentication != null;
        String username =  authentication.getName();
        userEntryRepository.deleteByUserName(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
