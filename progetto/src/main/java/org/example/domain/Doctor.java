package org.example.domain;

import java.util.Date;

public class Doctor {
    private String name;
    private String lastname;
    private Date birthday;
    private String cf;
    private String e_mail;
    private String telephone;

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

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Doctor() {
    }

    public Doctor(String name, String lastname, Date birthday, String cf, String e_mail, String telephone) {
        this.name = name;
        this.lastname = lastname;
        this.birthday = birthday;
        this.cf = cf;
        this.e_mail = e_mail;
        this.telephone = telephone;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        final Doctor d = (Doctor) obj;
        return this.cf == d.cf;
    }
}