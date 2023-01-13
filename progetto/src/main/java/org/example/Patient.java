package org.example;

import java.util.Date;

public class Patient {
    private String name;
    private String lastname;
    private Date birthday;
    private String residence;
    private String cf;
    private String telephone;
    private String e_mail;


    public Patient(String name, String lastname, Date birthday, String residence, String cf, String telephone, String e_mail) {
        this.name = name;
        this.lastname = lastname;
        this.birthday = birthday;
        this.residence = residence;
        this.cf = cf;
        this.telephone = telephone;
        this.e_mail = e_mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }
}