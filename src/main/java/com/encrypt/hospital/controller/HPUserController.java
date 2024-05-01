package com.encrypt.hospital.controller;

import com.encrypt.hospital.model.HpUser;
import com.encrypt.hospital.service.HpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class HPUserController {

    @Autowired
    private HpUserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody HpUser user) {
        try {
            HpUser registeredUser = userService.registerUser(user);
            return ResponseEntity.ok(registeredUser);
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
