package com.encrypt.hospital.service;

import com.encrypt.hospital.model.HpUser;
import com.encrypt.hospital.repository.HpUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HpUserService {

    private final HpUserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    @Autowired
    public HpUserService(HpUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public HpUser registerUser(HpUser user) {
        if (userRepository.findByUserName(user.getUserName()) != null) {
            throw new IllegalStateException("用户名已存在");
        }
        //user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        user.setType("Patient");
        return userRepository.save(user);
    }

    public HpUser loginUser(String username, String password) {
        HpUser user = userRepository.findByUserName(username);
        if (user == null) {
            throw new IllegalStateException("用户不存在，请注册。");
        } else if (!user.getPassWord().equals(password)) {
            throw new IllegalStateException("登录失败，密码错误");
        }
        return user;
    }
}