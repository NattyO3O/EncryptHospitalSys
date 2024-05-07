package com.encrypt.hospital.repository;

import com.encrypt.hospital.model.EncryptFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DecryptFileRepository extends JpaRepository<EncryptFile, Long> {
    List<EncryptFile> findByFileNameContaining(String fileName);
}
