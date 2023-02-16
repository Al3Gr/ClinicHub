package org.example.domain;

import java.time.LocalDate;
import java.util.Date;

public class Patient {
    private String name;
    private String lastname;
    private LocalDate birthday;
    private String residence;
    private String cf;
    private String telephone;
    private String e_mail;


    public Patient(String name, String lastname, LocalDate birthday, String residence, String cf, String telephone, String e_mail) {
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
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

    @Override
    public String toString() {
        return "Paziente:\n" +
                "\tnome: " + name + '\n' +
                "\tcognome: " + lastname + '\n' +
                "\tbirthday: " + birthday + '\n' +
                "\tresidence: " + residence + '\n' +
                "\tcf: " + cf + '\n' +
                "\ttelephone: " + telephone + '\n' +
                "\te_mail: " + e_mail;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) {
            return false;
        }
        final Patient p = (Patient) obj;
        return this.cf == p.cf;
    }
}