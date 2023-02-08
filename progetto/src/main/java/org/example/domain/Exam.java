package org.example.domain;

import java.sql.Time;
import java.util.*;

public class Exam {
    private float price;
    private int code;
    private Calendar bookingDate;
    private Calendar readyDate;
    private Time time;
    private Patient patient;
    private Doctor doctor;
    private ExamType type;

    public float getPrice() {return price;};
    public int getCode() {return code;};
    public Calendar getBookingDate() {return bookingDate;};

    public Calendar getReadyDate() { return readyDate;};
    public Time getTime() {return time;};
    public void setPatient(Patient p) {patient=p;};
    public void setDoctor(Doctor d) {doctor=d;};
    public Exam(ExamType type){
        this.type=type;
        code= new Random().nextInt();
        price=type.getPrice();
    }
    public void setData(Calendar data) {
        bookingDate=data;
        readyDate=data;
        readyDate.add(Calendar.DAY_OF_MONTH, type.getDaysToReady());
    }
    public void setTime(Time time) {
        this.time = time;
    }

    public Doctor getDoctor(){
        return doctor;
    }
}
