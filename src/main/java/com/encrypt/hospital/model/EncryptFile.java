package com.encrypt.hospital.model;

import javax.persistence.*;

@Entity
@Table(name = "encrypted_files")
public class EncryptFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FileID")
    private Long fileId;

    @Column(name = "UserID")
    private Integer userID;

    @Column(name = "DocID")
    private Integer docId;

    @Column(name = "File_name")
    private String fileName;

    @Column(name = "Algorithm")
    private String algorithm;

    @Lob
    @Column(name = "Encrypted_data", columnDefinition = "TEXT")
    private String encryptedData;  // 存储为 Base64 编码的字符串

    // Getters and Setters
    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }
}