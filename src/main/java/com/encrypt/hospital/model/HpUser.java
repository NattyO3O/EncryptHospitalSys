package com.encrypt.hospital.model;

import javax.persistence.*;

@Entity
@Table(name = "hpuser")
public class HpUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;
    @Column(name="UserID_MAC")
    private String userID_MAC;
    @Column(name="UserName")
    private String userName;
    @Column(name="UserName_MAC")
    private String userName_MAC;
    @Column(name="PassWord")
    private String passWord;
    @Column(name="PassWord_MAC")
    private String passWord_MAC;
    @Column(name="Type")
    private String type;
    @Column(name="Type_MAC")
    private String type_MAC;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserID_MAC() {
        return userID_MAC;
    }

    public void setUserID_MAC(String userID_MAC) {
        this.userID_MAC = userID_MAC;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName_MAC() {
        return userName_MAC;
    }

    public void setUserName_MAC(String userName_MAC) {
        this.userName_MAC = userName_MAC;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPassWord_MAC() {
        return passWord_MAC;
    }

    public void setPassWord_MAC(String passWord_MAC) {
        this.passWord_MAC = passWord_MAC;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType_MAC() {
        return type_MAC;
    }

    public void setType_MAC(String type_MAC) {
        this.type_MAC = type_MAC;
    }
}