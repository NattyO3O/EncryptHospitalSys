package com.encrypt.hospital.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appoID;
    @Column(name="AppoID_MAC")
    private String appoID_MAC;

    @Column(name="DocID")
    private int docID;
    @Column(name="DocID_MAC")
    private String docID_MAC;

    @Column(name = "PatientID")
    private int patientID;
    @Column(name = "PatientID_MAC")
    private String patientID_MAC;

    @Column(name = "Date")
    private Date date;
    @Column(name = "Date_MAC")
    private String date_MAC;

    @Column(name = "Time")
    private String time;
    @Column(name = "Time_MAC")
    private String time_MAC;

    @Column(name = "Department")
    private String department;
    @Column(name = "Department_MAC")
    private String department_MAC;

    @Column(name = "State")
    private String state;
    @Column(name = "State_MAC")
    private String state_MAC;

    // 构造函数
    public Appointment() {
    }


    public int getAppoID() {
        return appoID;
    }

    public void setAppoID(int appoID) {
        this.appoID = appoID;
    }

    public int getDocID() {
        return docID;
    }

    public void setDocID(int docID) {
        this.docID = docID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAppoID_MAC() {
        return appoID_MAC;
    }

    public void setAppoID_MAC(String appoID_MAC) {
        this.appoID_MAC = appoID_MAC;
    }

    public String getDocID_MAC() {
        return docID_MAC;
    }

    public void setDocID_MAC(String docID_MAC) {
        this.docID_MAC = docID_MAC;
    }

    public String getPatientID_MAC() {
        return patientID_MAC;
    }

    public void setPatientID_MAC(String patientID_MAC) {
        this.patientID_MAC = patientID_MAC;
    }

    public String getDate_MAC() {
        return date_MAC;
    }

    public void setDate_MAC(String date_MAC) {
        this.date_MAC = date_MAC;
    }

    public String getTime_MAC() {
        return time_MAC;
    }

    public void setTime_MAC(String time_MAC) {
        this.time_MAC = time_MAC;
    }

    public String getDepartment_MAC() {
        return department_MAC;
    }

    public void setDepartment_MAC(String department_MAC) {
        this.department_MAC = department_MAC;
    }

    public String getState_MAC() {
        return state_MAC;
    }

    public void setState_MAC(String state_MAC) {
        this.state_MAC = state_MAC;
    }
}
