package com.example.devoprakesh.trackmychild;

public class UserData {

    String emailid;
    String name;
    String unicode;

    public UserData(String emailid, String name, String unicode) {
        this.emailid = emailid;
        this.name = name;
        this.unicode = unicode;
    }


    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
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
}
