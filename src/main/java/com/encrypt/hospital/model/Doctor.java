package com.encrypt.hospital.model;

import javax.persistence.*;

@Entity
@Table(name = "doctor")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int DocID;
    @Column(name="UserID")
    private int userID;
    @Column(name="DocName")
    private String docName;
    @Column(name="Department")
    private String department;
    @Column(name="Title")
    private String title;
    @Column(name="Email")
    private String email;
    @Column(name="PhoneNumber")
    private String phoneNumber;
    @Column(name="Profile")
    private String profile;

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

}
