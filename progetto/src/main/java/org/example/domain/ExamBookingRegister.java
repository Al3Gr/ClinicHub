package org.example.domain;

import java.util.HashMap;
import java.util.Map;

public class ExamBookingRegister {
    private Map<Integer, Exam> register;
    private static ExamBookingRegister examBookingRegister;
    //Pattern Singletone
    public static ExamBookingRegister getInstance(){
        if(examBookingRegister == null){
            examBookingRegister = new ExamBookingRegister();
        }
        return examBookingRegister;
    }
    private ExamBookingRegister(){this.register = new HashMap<>();}
    public void addBooking(Exam exam){register.put(exam.getCode(), exam);}
    public float getRefund(int codice){
        return (register.get(codice)).getPrice(); //DA SISTEMARE QUANTO SI PUO TORNARE IN BASE AI GIORNI
    }
    public void remove(int codice){register.remove(codice);}
    public int getSize() { return register.size(); }
    public boolean checkPatient(int codice, Patient p){
        return true; //SISTEMA
    }
    public Exam getExam(int codice){
        return register.get(codice);
    }
}
