package com.encrypt.hospital.service;

import com.encrypt.hospital.model.Patient;
import com.encrypt.hospital.repository.PatientRepository;
import com.encrypt.hospital.util.HMACSM3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

//    public Patient getPatientDetails(int userID) {
//        return patientRepository.findByUserID(userID);
//    }

    public Patient getPatientDetails(int userID) {
        Patient patient = patientRepository.findByUserID(userID);
        if (patient != null) {
            // 解密 nationalID 和 phoneNumber
            if(patient.getNationalID() != null){
                patient.setNationalID(decryptSM4(Base64.getDecoder().decode(patient.getNationalID())));
            }
           if(patient.getPhoneNumber() != null){
               patient.setPhoneNumber(decryptSM4(Base64.getDecoder().decode(patient.getPhoneNumber())));
           }
        }
        return patient;
    }

//    @Transactional
//    public void updatePatientDetails(Patient updatedPatient) {
//        Patient patient = patientRepository.findByUserID(updatedPatient.getUserID());
//        if (patient != null) {
//            patient.setPatientName(updatedPatient.getPatientName());
//            patient.setSex(updatedPatient.getSex());
//            patient.setAge(updatedPatient.getAge());
//            patient.setPhoneNumber(updatedPatient.getPhoneNumber());
//            patient.setNationalID(updatedPatient.getNationalID());
//            patientRepository.save(patient);
//        } else {
//            throw new IllegalStateException("No patient found with the user ID: " + updatedPatient.getUserID());
//        }
//    }
    @Transactional
    public void updatePatientDetails(Patient updatedPatient) {
        Patient patient = patientRepository.findByUserID(updatedPatient.getUserID());
        if (patient != null) {
            patient.setPatientName(updatedPatient.getPatientName());
            patient.setSex(updatedPatient.getSex());
            patient.setAge(updatedPatient.getAge());
            // 加密 nationalID 和 phoneNumber
            patient.setNationalID(encryptToBase64(updatedPatient.getNationalID()));
            patient.setPhoneNumber(encryptToBase64(updatedPatient.getPhoneNumber()));
            System.out.println("加密成功");
            //完整性
            genMACforPatient(patient, patientRepository);
        } else {
            throw new IllegalStateException("No patient found with the user ID: " + updatedPatient.getUserID());
        }
    }

    static void genMACforPatient(Patient patient, PatientRepository patientRepository) {
        patient.setPatientID_MAC(HMACSM3.generateHmacSm3(String.valueOf(patient.getPatientID())));
        patient.setUserID_MAC(HMACSM3.generateHmacSm3(String.valueOf(patient.getUserID())));
        patient.setPatientName_MAC(HMACSM3.generateHmacSm3(patient.getPatientName()));
        patient.setSex_MAC(HMACSM3.generateHmacSm3(patient.getSex()));
        patient.setAge_MAC(HMACSM3.generateHmacSm3(String.valueOf(patient.getAge())));
        patient.setPhoneNumber_MAC(HMACSM3.generateHmacSm3(patient.getPhoneNumber()));
        patient.setNationalID_MAC(HMACSM3.generateHmacSm3(patient.getNationalID()));

        patientRepository.save(patient);
    }

    // 将文本加密并转换为Base64字符串
    private String encryptToBase64(String plaintext) {
        if (plaintext == null || plaintext.isEmpty()) return null;
        try {
            byte[] encrypted = EncryptionService.encryptSM4(plaintext);
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    // 从Base64编码的密文解密为明文
    private String decryptSM4(byte[] ciphertext) {
        try {
            byte[] decrypted = DecryptService.decryptSM4(ciphertext);
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }
}
