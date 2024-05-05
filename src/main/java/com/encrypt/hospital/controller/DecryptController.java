package com.encrypt.hospital.controller;

import com.encrypt.hospital.model.EncryptFile;
import com.encrypt.hospital.service.DecryptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/decrypt")
public class DecryptController {

    @Autowired
    private DecryptService decryptService;

    @GetMapping("/files")
    public ResponseEntity<List<EncryptFile>> searchFiles(@RequestParam String fileName) {
        try {
            List<EncryptFile> files = decryptService.searchFilesByName(fileName);
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> downloadFile(@PathVariable Long fileId, HttpServletResponse response) {
        try {
            EncryptFile file = decryptService.getFileById(fileId);
            String decryptedData = decryptService.decryptData(file);

            if (decryptedData == null) {
                // 解密失败
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }

            // 将解密后的数据转换为字节数组
            byte[] data = decryptedData.getBytes("UTF-8");

            // 保存解密后的数据到文件
            decryptService.saveDecryptedDataToFile(file);

            // 设置响应头
            String headerValue = String.format("attachment; filename=\"%s\"", file.getFileName());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, headerValue);
            // 允许前端访问 Content-Disposition
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .body(new ByteArrayResource(data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}