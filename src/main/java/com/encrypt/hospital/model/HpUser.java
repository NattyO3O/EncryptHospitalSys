package com.encrypt.hospital.model;

import javax.persistence.*;

@Entity
@Table(name="HPUser")
public class HpUser{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;
    @Column(name="UserName")
    private String userName;
    @Column(name="PassWord")
    private String passWord;
    @Column(name="Type")
    private String type;

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}