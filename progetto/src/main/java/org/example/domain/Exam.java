package org.example.domain;

import javafx.beans.InvalidationListener;
import org.example.interfaces.Observable;

import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

public class Exam implements Observable<ExamBookingRegister> {
    private float price;
    private int code;
    private Calendar bookingDate;
    private Calendar readyDate;
    private LocalTime time;
    private Patient patient;
    private Doctor doctor;

    private Result result;

    private boolean stateReady = false;
    private final ExamType type;

    private List<ExamBookingRegister> observers = new ArrayList<>();

    public void setCode(int code) {
        this.code = code;
    }

    public Patient getPatient() {
        return patient;
    }

    public float getPrice() {return price;}
    public int getCode() {return code;}
    public Calendar getBookingDate() {return this.bookingDate;}

    public Calendar getReadyDate() { return this.readyDate;}
    public LocalTime getTime() {return time;}
    public void setPatient(Patient p) {this.patient=p;}
    public void setDoctor(Doctor d) {this.doctor=d;}

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Doctor getDoctor(){
        return doctor;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Result getResult() {
        return result;
    }

    public Exam(ExamType type){
        this.type=type;
        this.code= new Random().nextInt();
        this.price=type.getPrice();
    }
    public void setData(Calendar data) {
        this.bookingDate=data;
        setReadyDate(data);
    }

    private void setReadyDate(Calendar data){
        readyDate= (Calendar) data.clone();
        readyDate.add(Calendar.DAY_OF_MONTH, type.getDaysToReady());

    }

    public void setState(String info) {
        this.stateReady = true;
        result = new Result(info);
        notifyObserver();
    }

    @Override
    public void addObserver(ExamBookingRegister observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(ExamBookingRegister observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (ExamBookingRegister e : observers) {
            e.update(this);
        }
    }

    public String getResultInfo() throws Exception{
        if (result != null) {
            return result.getInfo();
        } else {
            throw new Exception("result is null");
        }

    }

    private class Result {
        private final String info;

        public Result(String info) {
            this.info = info;
        }

        public String getInfo() {
            return info;
        }
    }
}
