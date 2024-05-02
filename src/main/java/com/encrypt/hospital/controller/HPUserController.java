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

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody HpUser loginRequest) {
        System.out.println("接收到来自用户的登录请求: " + loginRequest.getUserName());
        try {
            HpUser user = userService.loginUser(loginRequest.getUserName(), loginRequest.getPassWord());
            return ResponseEntity.ok(user);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
