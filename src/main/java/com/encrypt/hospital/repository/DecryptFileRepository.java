package com.encrypt.hospital.repository;

import com.encrypt.hospital.model.EncryptFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DecryptFileRepository extends JpaRepository<EncryptFile, Long> {
    List<EncryptFile> findByFileNameContaining(String fileName);
    // 可以添加其他需要的数据库操作方法
}
