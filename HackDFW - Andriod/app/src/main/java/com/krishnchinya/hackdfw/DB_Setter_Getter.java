package com.krishnchinya.hackdfw;

/**
 * Created by KrishnChinya on 4/21/17.
 */

public class DB_Setter_Getter {
    String fname;
    String lname;
    String dob;
    String gender;
    String DOB;
    String CELL;
    String mailId;
    String carNumber;
    String hpincode;
    String opincode;
    String password;

    public DB_Setter_Getter(String fname, String lname, String DOB, String CELL, String mailId, String carNumber, String hpincode, String opincode, String password) {
        this.fname = fname;
        this.lname = lname;
        //this.dob = dob;
        //this.gender = gender;
        this.DOB = DOB;
        this.CELL = CELL;
        this.mailId = mailId;
        this.carNumber = carNumber;
        this.hpincode = hpincode;
        this.opincode = opincode;
        this.password = password;
    }

    public DB_Setter_Getter(String mailId, String password) {
        this.mailId = mailId;
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getCELL() {
        return CELL;
    }

    public void setCELL(String CELL) {
        this.CELL = CELL;
    }

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getHpincode() {
        return hpincode;
    }

    public void setHpincode(String hpincode) {
        this.hpincode = hpincode;
    }

    public String getOpincode() {
        return opincode;
    }

    public void setOpincode(String opincode) {
        this.opincode = opincode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
