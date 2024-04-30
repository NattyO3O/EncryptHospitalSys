package com.encrypt.hospital.controller;

import com.encrypt.hospital.model.HpUser;
import com.encrypt.hospital.repository.HpUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class HPUserController {

    @Autowired
    private HpUserRepository hpUserRepository;

    @GetMapping
    public List<HpUser> getAllUsers() {
        return hpUserRepository.findAll();
    }
}