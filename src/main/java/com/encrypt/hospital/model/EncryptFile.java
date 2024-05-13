package com.encrypt.hospital.model;

import javax.persistence.*;

@Entity
@Table(name = "encrypted_files")
public class EncryptFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FileID")
    private Long fileId;
    @Column(name = "FileID_MAC")
    private String fileId_MAC;

    @Column(name = "File_name")
    private String fileName;
    @Column(name = "File_name_MAC")
    private String fileName_MAC;

    @Column(name = "Algorithm")
    private String algorithm;
    @Column(name = "Algorithm_MAC")
    private String algorithm_MAC;

    @Lob
    @Column(name = "Encrypted_data", columnDefinition = "TEXT")
    private String encryptedData;  // 存储为 Base64 编码的字符串
    @Column(name = "Encrypted_data_MAC")
    private String encryptedData_MAC;  // 存储为 Base64 编码的字符串

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileId_MAC() {
        return fileId_MAC;
    }

    public void setFileId_MAC(String fileId_MAC) {
        this.fileId_MAC = fileId_MAC;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName_MAC() {
        return fileName_MAC;
    }

    public void setFileName_MAC(String fileName_MAC) {
        this.fileName_MAC = fileName_MAC;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getAlgorithm_MAC() {
        return algorithm_MAC;
    }

    public void setAlgorithm_MAC(String algorithm_MAC) {
        this.algorithm_MAC = algorithm_MAC;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getEncryptedData_MAC() {
        return encryptedData_MAC;
    }

    public void setEncryptedData_MAC(String encryptedData_MAC) {
        this.encryptedData_MAC = encryptedData_MAC;
    }
}