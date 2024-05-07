package com.encrypt.hospital.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Appointment")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appoID;

    @Column(name="DocID")
    private int docID;

    @Column(name = "PatientID")
    private int patientID;

    @Column(name = "Date")
    private Date date;

    @Column(name = "Time")
    private String time;

    @Column(name = "Department")
    private String department;

    @Column(name = "State")
    private String state;

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
}
