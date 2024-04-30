package com.encrypt.hospital.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 * 运行HospitalApplication，然后在frontend文件夹下执行npm start，浏览器输入localhost:3000后显示API is working!
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String testApi() {
        return "API is working!";
    }
}
