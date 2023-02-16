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

    private static int INDEX = 1;

    private boolean stateReady = false;
    private final ExamType type;

    private List<ExamBookingRegister> observers;

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
        this.code= INDEX++;
        this.price=type.getPrice();
        this.observers = new ArrayList<>();
    }
    public void setData(Calendar data) {
        this.bookingDate=data;
        setReadyDate(data);
    }

    private void setReadyDate(Calendar data){
        readyDate= (Calendar) data.clone();
        readyDate.add(Calendar.DAY_OF_MONTH, type.getDaysToReady());

    }

    public void setReadyDateToday(Calendar data){
        readyDate=data;
    }

    public void setState(String info) {
        this.stateReady = true;
        result = new Result(info);
        notifyObserver();
    }

    public boolean getState(){
        return stateReady;
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

    @Override
    public String toString() {
        return "Codice: " + code +
                "\tPaziente: " + patient.getCf() +
                "\tTipo: " + type;
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
