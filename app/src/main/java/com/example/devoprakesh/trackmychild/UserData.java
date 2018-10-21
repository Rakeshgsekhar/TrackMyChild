package com.example.devoprakesh.trackmychild;

public class UserData {

    String email;
    String name;
    String unicode;
    String phonenumber;



    public UserData(String name, String emailid, String unicode, String phonenumber) {

        this.name = name;
        this.email = emailid;
        this.unicode = unicode;
        this.phonenumber = phonenumber;
    }


    public String getEmailid() {
        return email;
    }

    public void setEmailid(String emailid) {
        this.email = emailid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnicode() {
        return unicode;
    }

    public void setUnicode(String unicode) {
        this.unicode = unicode;
    }
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
