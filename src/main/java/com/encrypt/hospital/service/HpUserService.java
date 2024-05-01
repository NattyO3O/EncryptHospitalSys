package com.encrypt.hospital.service;

import com.encrypt.hospital.model.HpUser;
import com.encrypt.hospital.repository.HpUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class HpUserService {
    @Autowired
    private HpUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public HpUser registerUser(HpUser user) {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new IllegalStateException("{\"error\": \"用户名已存在\"}");
        }
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        user.setType("Patient");
        return userRepository.save(user);
    }
}
