package org.example.domain;

import java.util.Calendar;
import java.util.Random;

public abstract class Hospitalization{
    protected float price;
    protected Calendar start_date;
    protected int code;
    protected Calendar end_date;
    protected Patient patient;
    protected Doctor doctor;
    protected Operation op;

    private static int INDEX = 1;

    public Patient getPatient() {
        return patient;
    }

    public abstract void setData(Calendar data);

    public float getPrice(){
        return this.price;
    }

    public Calendar getStart_date() {
        return start_date;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Calendar getEnd_date() {
        return end_date;
    }

    public void setPatient(Patient p) { patient = p; }

    public void setDoctor(Doctor d) { doctor = d; }

    public Hospitalization(Operation op) {
        this.op = op;
        this.code = INDEX++;
    }

    @Override
    public String toString() {
        return "Codice: " + code +
                "\tPaziente: " + patient.getCf() +
                "\tOperazione: " + op;
    }
}