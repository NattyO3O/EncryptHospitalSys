package com.encrypt.hospital.controller;

import org.quickssl.api.CryptoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.encrypt.hospital.service.EncryptionService;
import com.encrypt.hospital.model.EncryptFile;

import java.util.Base64;

@RestController
@RequestMapping("/api")
public class EncryptController {

    @Autowired
    private EncryptionService encryptionService;

    @PostMapping("/encrypt")
    public ResponseEntity<String> encryptFile(@RequestParam("file") MultipartFile file,
                                              @RequestParam("algorithm") String algorithm) {
        try {
            String base64EncodedEncryptedData = encryptionService.encryptFile(file, algorithm);
            byte[] encryptedData = Base64.getDecoder().decode(base64EncodedEncryptedData);
            EncryptFile encryptedFile = encryptionService.saveEncryptedFile(file.getOriginalFilename(), algorithm, encryptedData);
            return ResponseEntity.ok("File encrypted and saved with ID: " + encryptedFile.getFileId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Encryption failed: " + e.getMessage());
        }
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadEncryptedData(@RequestParam("fileName") String fileName,
                                                      @RequestParam("algorithm") String algorithm,
                                                      @RequestParam("encryptedData") String encryptedData) {
        try {
            // 将加密数据保存到数据库
            byte[] dataBytes = Base64.getDecoder().decode(encryptedData);
            EncryptFile encryptedFile = encryptionService.saveEncryptedFile(fileName, algorithm, dataBytes);

            // 返回成功消息
            return ResponseEntity.ok("文件已上传成功，文件ID: " + encryptedFile.getFileId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传失败: " + e.getMessage());
        }
    }

}
