package com.encrypt.hospital.controller;

import com.encrypt.hospital.service.IntegrityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IntegrityController {
    @Autowired
    private IntegrityService integrityService;

    @PostMapping("/generateDigest/{tableName}")
    public ResponseEntity<String> generateDigest(@PathVariable String tableName) {
        try {
            String result = integrityService.generateDigest(tableName);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("摘要生成失败: " + e.getMessage());
        }
    }

    @GetMapping("/checkIntegrity/{tableName}")
    public ResponseEntity<String> checkIntegrity(@PathVariable String tableName) {
        try {
            String result = integrityService.checkIntegrity(tableName);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("完整性检查失败: " + e.getMessage());
        }
    }
}
