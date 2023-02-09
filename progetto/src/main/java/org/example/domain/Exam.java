package org.example.domain;

import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

public class Exam {
    private float price;
    private int code;
    private Calendar bookingDate;
    private Calendar readyDate;
    private LocalTime time;
    private Patient patient;
    private Doctor doctor;
    private ExamType type;

    public Patient getPatient() {
        return patient;
    }

    public float getPrice() {return price;};
    public int getCode() {return code;};
    public Calendar getBookingDate() {return bookingDate;};

    public Calendar getReadyDate() { return readyDate;};
    public LocalTime getTime() {return time;};
    public void setPatient(Patient p) {this.patient=p;};
    public void setDoctor(Doctor d) {this.doctor=d;};
    public Exam(ExamType type){
        this.type=type;
        this.code= new Random().nextInt();
        this.price=type.getPrice();
    }
    public void setData(Calendar data) {
        this.bookingDate=data;
        this.readyDate=data;
        this.readyDate.add(Calendar.DAY_OF_MONTH, this.type.getDaysToReady());
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Doctor getDoctor(){
        return doctor;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
