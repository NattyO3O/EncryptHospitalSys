package com.encrypt.hospital.repository;
import com.encrypt.hospital.model.EncryptFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EncryptedFileRepository extends JpaRepository<EncryptFile, Long> {
}
