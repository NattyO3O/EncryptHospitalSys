package com.encrypt.hospital.service;

import com.encrypt.hospital.model.*;
import com.encrypt.hospital.repository.*;
import com.encrypt.hospital.util.HMACSM3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;

@Service
public class IntegrityService {

    @Autowired
    private HpUserRepository hpUserRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private EncryptedFileRepository encryptFileRepository;

    @Transactional
    public String generateDigest(String tableName) throws Exception {
        switch (tableName) {
            case "用户表":
                return generateDigestForHpUser();
            case "医生表":
                return generateDigestForDoctor();
            case "患者表":
                return generateDigestForPatient();
            case "预约表":
                return generateDigestForAppointment();
            case "病历表":
                return generateDigestForEncryptFile();
            default:
                throw new IllegalArgumentException("Unsupported table: " + tableName);
        }
    }

    private String generateDigestForEncryptFile() {
        Iterable<EncryptFile> files = encryptFileRepository.findAll();
        for (EncryptFile file : files) {
            file.setFileId_MAC(HMACSM3.generateHmacSm3(String.valueOf(file.getFileId())));
            file.setFileName_MAC(HMACSM3.generateHmacSm3(file.getFileName()));
            file.setAlgorithm_MAC(HMACSM3.generateHmacSm3(file.getAlgorithm()));
            file.setEncryptedData_MAC(HMACSM3.generateHmacSm3(file.getEncryptedData()));
            encryptFileRepository.save(file);
        }
        return "病历表摘要生成成功";
    }

    private String generateDigestForAppointment() {
        Iterable<Appointment> appointments = appointmentRepository.findAll();
        for (Appointment appointment : appointments) {
            appointment.setAppoID_MAC(HMACSM3.generateHmacSm3(String.valueOf(appointment.getAppoID())));
            appointment.setDocID_MAC(HMACSM3.generateHmacSm3(String.valueOf(appointment.getDocID())));
            appointment.setPatientID_MAC(HMACSM3.generateHmacSm3(String.valueOf(appointment.getPatientID())));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String formattedDate = sdf.format(appointment.getDate());
            appointment.setDate_MAC(HMACSM3.generateHmacSm3(formattedDate));
            //appointment.setDate_MAC(HMACSM3.generateHmacSm3(String.valueOf(appointment.getDate())));
            appointment.setTime_MAC(HMACSM3.generateHmacSm3(appointment.getTime()));
            appointment.setDepartment_MAC(HMACSM3.generateHmacSm3(appointment.getDepartment()));
            appointment.setState_MAC(HMACSM3.generateHmacSm3(appointment.getState()));
            appointmentRepository.save(appointment);
        }
        return "预约表摘要生成成功";
    }

    private String generateDigestForHpUser() {
        Iterable<HpUser> users = hpUserRepository.findAll();
        for (HpUser user : users) {
            user.setUserID_MAC(HMACSM3.generateHmacSm3(String.valueOf(user.getUserID())));
            user.setUserName_MAC(HMACSM3.generateHmacSm3(user.getUserName()));
            user.setPassWord_MAC(HMACSM3.generateHmacSm3(user.getPassWord()));
            user.setType_MAC(HMACSM3.generateHmacSm3(user.getType()));
            hpUserRepository.save(user);
        }
        return "用户表摘要生成成功";
    }

    private String generateDigestForDoctor() {
        Iterable<Doctor> doctors = doctorRepository.findAll();
        for (Doctor doctor : doctors) {
            doctor.setDocID_MAC(HMACSM3.generateHmacSm3(String.valueOf(doctor.getDocID())));
            doctor.setUserID_MAC(HMACSM3.generateHmacSm3(String.valueOf(doctor.getUserID())));
            doctor.setDocName_MAC(HMACSM3.generateHmacSm3(doctor.getDocName()));
            doctor.setDepartment_MAC(HMACSM3.generateHmacSm3(doctor.getDepartment()));
            doctor.setTitle_MAC(HMACSM3.generateHmacSm3(doctor.getTitle()));
            doctor.setEmail_MAC(HMACSM3.generateHmacSm3(doctor.getEmail()));
            doctor.setPhoneNumber_MAC(HMACSM3.generateHmacSm3(doctor.getPhoneNumber()));
            doctor.setProfile_MAC(HMACSM3.generateHmacSm3(doctor.getProfile()));
            doctorRepository.save(doctor);
        }
        return "医生表摘要生成成功";
    }

    private String generateDigestForPatient() {
        Iterable<Patient> patients = patientRepository.findAll();
        for (Patient patient : patients) {
            patient.setPatientID_MAC(HMACSM3.generateHmacSm3(String.valueOf(patient.getPatientID())));
            patient.setUserID_MAC(HMACSM3.generateHmacSm3(String.valueOf(patient.getUserID())));
            patient.setPatientName_MAC(HMACSM3.generateHmacSm3(patient.getPatientName()));
            patient.setSex_MAC(HMACSM3.generateHmacSm3(patient.getSex()));
            patient.setAge_MAC(HMACSM3.generateHmacSm3(String.valueOf(patient.getAge())));
            patient.setPhoneNumber_MAC(HMACSM3.generateHmacSm3(patient.getPhoneNumber()));
            patient.setNationalID_MAC(HMACSM3.generateHmacSm3(patient.getNationalID()));
            patientRepository.save(patient);
        }
        return "患者表摘要生成成功";
    }

    @Transactional
    public String checkIntegrity(String tableName) {
        switch (tableName) {
            case "用户表":
                return checkIntegrityForHpUser();
            case "医生表":
                return checkIntegrityForDoctor();
            case "患者表":
                return checkIntegrityForPatient();
            case "预约表":
                return checkIntegrityForAppointment();
            case "病历表":
                return checkIntegrityForEncryptFile();
            default:
                throw new IllegalArgumentException("Unsupported table: " + tableName);
        }
    }

    private String checkIntegrityForEncryptFile() {
        Iterable<EncryptFile> files = encryptFileRepository.findAll();
        for (EncryptFile file : files) {
            if (!HMACSM3.verifyHmacSm3(String.valueOf(file.getFileId()), file.getFileId_MAC()) ||
                    !HMACSM3.verifyHmacSm3(file.getFileName(), file.getFileName_MAC()) ||
                    !HMACSM3.verifyHmacSm3(file.getAlgorithm(), file.getAlgorithm_MAC()) ||
                    !HMACSM3.verifyHmacSm3(file.getEncryptedData(), file.getEncryptedData_MAC())) {
                return "病历表完整性检查失败，ID: " + file.getFileId();
            }
        }
        return "病历表完整性检查成功";
    }

    private String checkIntegrityForAppointment() {
        Iterable<Appointment> appointments = appointmentRepository.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Appointment appointment : appointments) {
            String formattedDate = sdf.format(appointment.getDate());
//            if (!HMACSM3.verifyHmacSm3(String.valueOf(appointment.getAppoID()), appointment.getAppoID_MAC()) ||
//                    !HMACSM3.verifyHmacSm3(String.valueOf(appointment.getDocID()), appointment.getDocID_MAC()) ||
//                    !HMACSM3.verifyHmacSm3(String.valueOf(appointment.getPatientID()), appointment.getPatientID_MAC()) ||
//                    !HMACSM3.verifyHmacSm3(String.valueOf(appointment.getDate()), appointment.getDate_MAC()) ||
//                    !HMACSM3.verifyHmacSm3(appointment.getTime(), appointment.getTime_MAC()) ||
//                    !HMACSM3.verifyHmacSm3(appointment.getDepartment(), appointment.getDepartment_MAC()) ||
//                    !HMACSM3.verifyHmacSm3(appointment.getState(), appointment.getState_MAC())) {
//                return "预约表完整性检查失败，ID: " + appointment.getAppoID();
//            }
            if (!HMACSM3.verifyHmacSm3(String.valueOf(appointment.getAppoID()), appointment.getAppoID_MAC()) ||
                    !HMACSM3.verifyHmacSm3(String.valueOf(appointment.getDocID()), appointment.getDocID_MAC()) ||
                    !HMACSM3.verifyHmacSm3(String.valueOf(appointment.getPatientID()), appointment.getPatientID_MAC()) ||
                    !HMACSM3.verifyHmacSm3(formattedDate, appointment.getDate_MAC()) ||
                    !HMACSM3.verifyHmacSm3(appointment.getTime(), appointment.getTime_MAC()) ||
                    !HMACSM3.verifyHmacSm3(appointment.getDepartment(), appointment.getDepartment_MAC()) ||
                    !HMACSM3.verifyHmacSm3(appointment.getState(), appointment.getState_MAC())) {
                return "预约表完整性检查失败，ID: " + appointment.getAppoID();
            }
        }
        return "预约表完整性检查成功";
    }

    private String checkIntegrityForHpUser() {
        Iterable<HpUser> users = hpUserRepository.findAll();
        for (HpUser user : users) {
            if (!HMACSM3.verifyHmacSm3(String.valueOf(user.getUserID()), user.getUserID_MAC()) ||
                    !HMACSM3.verifyHmacSm3(user.getUserName(), user.getUserName_MAC()) ||
                    !HMACSM3.verifyHmacSm3(user.getPassWord(), user.getPassWord_MAC()) ||
                    !HMACSM3.verifyHmacSm3(user.getType(), user.getType_MAC())) {
                return "用户表完整性检查失败，ID: " + user.getUserID();
            }
        }
        return "用户表完整性检查成功";
    }

    private String checkIntegrityForDoctor() {
        Iterable<Doctor> doctors = doctorRepository.findAll();
        for (Doctor doctor : doctors) {
            if (!HMACSM3.verifyHmacSm3(String.valueOf(doctor.getDocID()), doctor.getDocID_MAC()) ||
                    !HMACSM3.verifyHmacSm3(String.valueOf(doctor.getUserID()), doctor.getUserID_MAC()) ||
                    !HMACSM3.verifyHmacSm3(doctor.getDocName(), doctor.getDocName_MAC()) ||
                    !HMACSM3.verifyHmacSm3(doctor.getDepartment(), doctor.getDepartment_MAC()) ||
                    !HMACSM3.verifyHmacSm3(doctor.getTitle(), doctor.getTitle_MAC()) ||
                    !HMACSM3.verifyHmacSm3(doctor.getEmail(), doctor.getEmail_MAC()) ||
                    !HMACSM3.verifyHmacSm3(doctor.getPhoneNumber(), doctor.getPhoneNumber_MAC()) ||
                    !HMACSM3.verifyHmacSm3(doctor.getProfile(), doctor.getProfile_MAC())) {
                return "医生表完整性检查失败，ID: " + doctor.getDocID();
            }
        }
        return "医生表完整性检查成功";
    }

    private String checkIntegrityForPatient() {
        Iterable<Patient> patients = patientRepository.findAll();
        for (Patient patient : patients) {
            if (!HMACSM3.verifyHmacSm3(String.valueOf(patient.getPatientID()), patient.getPatientID_MAC()) ||
                    !HMACSM3.verifyHmacSm3(String.valueOf(patient.getUserID()), patient.getUserID_MAC()) ||
                    !HMACSM3.verifyHmacSm3(patient.getPatientName(), patient.getPatientName_MAC()) ||
                    !HMACSM3.verifyHmacSm3(patient.getSex(), patient.getSex_MAC()) ||
                    !HMACSM3.verifyHmacSm3(String.valueOf(patient.getAge()), patient.getAge_MAC()) ||
                    !HMACSM3.verifyHmacSm3(patient.getPhoneNumber(), patient.getPhoneNumber_MAC()) ||
                    !HMACSM3.verifyHmacSm3(patient.getNationalID(), patient.getNationalID_MAC())) {
                return "患者表完整性检查失败，ID: " + patient.getPatientID();
            }
        }
        return "患者表完整性检查成功";
    }
}
