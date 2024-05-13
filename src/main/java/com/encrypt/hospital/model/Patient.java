package com.encrypt.hospital.model;
import javax.persistence.*;

@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer patientID;
    @Column(name="PatientID_MAC")
    private String patientID_MAC;

    @Column(name="UserID")
    private Integer userID;
    @Column(name="UserID_MAC")
    private String userID_MAC;

    @Column(name="PatientName")
    private String patientName;
    @Column(name="PatientName_MAC")
    private String patientName_MAC;

    @Column(name="Sex")
    private String sex;
    @Column(name="Sex_MAC")
    private String sex_MAC;


    @Column(name="Age")
    private Integer age;
    @Column(name="Age_MAC")
    private String age_MAC;

    @Column(name="PhoneNumber")
    private String phoneNumber;
    @Column(name="PhoneNumber_MAC")
    private String phoneNumber_MAC;

    @Column(name="NationalID")
    private String nationalID;
    @Column(name="NationalID_MAC")
    private String nationalID_MAC;

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public String getPatientID_MAC() {
        return patientID_MAC;
    }

    public void setPatientID_MAC(String patientID_MAC) {
        this.patientID_MAC = patientID_MAC;
    }

    public String getUserID_MAC() {
        return userID_MAC;
    }

    public void setUserID_MAC(String userID_MAC) {
        this.userID_MAC = userID_MAC;
    }

    public String getPatientName_MAC() {
        return patientName_MAC;
    }

    public void setPatientName_MAC(String patientName_MAC) {
        this.patientName_MAC = patientName_MAC;
    }

    public String getSex_MAC() {
        return sex_MAC;
    }

    public void setSex_MAC(String sex_MAC) {
        this.sex_MAC = sex_MAC;
    }

    public String getAge_MAC() {
        return age_MAC;
    }

    public void setAge_MAC(String age_MAC) {
        this.age_MAC = age_MAC;
    }

    public String getPhoneNumber_MAC() {
        return phoneNumber_MAC;
    }

    public void setPhoneNumber_MAC(String phoneNumber_MAC) {
        this.phoneNumber_MAC = phoneNumber_MAC;
    }

    public String getNationalID_MAC() {
        return nationalID_MAC;
    }

    public void setNationalID_MAC(String nationalID_MAC) {
        this.nationalID_MAC = nationalID_MAC;
    }
}
