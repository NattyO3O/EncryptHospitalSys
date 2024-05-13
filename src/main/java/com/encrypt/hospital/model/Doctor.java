package com.encrypt.hospital.model;

import javax.persistence.*;

@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int DocID;
    @Column(name="DocID_MAC")
    private String DocID_MAC;
    @Column(name="UserID")
    private int userID;
    @Column(name="UserID_MAC")
    private String userID_MAC;
    @Column(name="DocName")
    private String docName;
    @Column(name="DocName_MAC")
    private String docName_MAC;
    @Column(name="Department")
    private String department;
    @Column(name="Department_MAC")
    private String department_MAC;
    @Column(name="Title")
    private String title;
    @Column(name="Title_MAC")
    private String title_MAC;
    @Column(name="Email")
    private String email;
    @Column(name="Email_MAC")
    private String email_MAC;
    @Column(name="PhoneNumber")
    private String phoneNumber;
    @Column(name="PhoneNumber_MAC")
    private String phoneNumber_MAC;
    @Column(name="Profile")
    private String profile;
    @Column(name="Profile_MAC")
    private String profile_MAC;

    public int getDocID() {
        return DocID;
    }

    public void setDocID(int DocID) {
        this.DocID = DocID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDocID_MAC() {
        return DocID_MAC;
    }

    public void setDocID_MAC(String docID_MAC) {
        DocID_MAC = docID_MAC;
    }

    public String getUserID_MAC() {
        return userID_MAC;
    }

    public void setUserID_MAC(String userID_MAC) {
        this.userID_MAC = userID_MAC;
    }

    public String getDocName_MAC() {
        return docName_MAC;
    }

    public void setDocName_MAC(String docName_MAC) {
        this.docName_MAC = docName_MAC;
    }

    public String getDepartment_MAC() {
        return department_MAC;
    }

    public void setDepartment_MAC(String department_MAC) {
        this.department_MAC = department_MAC;
    }

    public String getTitle_MAC() {
        return title_MAC;
    }

    public void setTitle_MAC(String title_MAC) {
        this.title_MAC = title_MAC;
    }

    public String getEmail_MAC() {
        return email_MAC;
    }

    public void setEmail_MAC(String email_MAC) {
        this.email_MAC = email_MAC;
    }

    public String getPhoneNumber_MAC() {
        return phoneNumber_MAC;
    }

    public void setPhoneNumber_MAC(String phoneNumber_MAC) {
        this.phoneNumber_MAC = phoneNumber_MAC;
    }

    public String getProfile_MAC() {
        return profile_MAC;
    }

    public void setProfile_MAC(String profile_MAC) {
        this.profile_MAC = profile_MAC;
    }
}
