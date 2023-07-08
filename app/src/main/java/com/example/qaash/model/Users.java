package com.example.qaash.model;

public class Users {
    String userName,userEmail,number,uuId;

    public Users() {
    }

    public Users(String userName, String userEmail, String number, String uuId) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.number = number;
        this.uuId = uuId;
    }

    public Users(String userName, String userEmail, String number) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.number = number;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
