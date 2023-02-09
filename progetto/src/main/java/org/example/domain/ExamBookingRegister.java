package org.example.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.time.*;

public class ExamBookingRegister {
    private Map<Integer, Exam> register;
    private static ExamBookingRegister examBookingRegister;

    //Pattern Singleton
    public static ExamBookingRegister getInstance(){
        if(examBookingRegister == null){
            examBookingRegister = new ExamBookingRegister();
        }
        return examBookingRegister;
    }
    private ExamBookingRegister(){this.register = new HashMap<>();}

    public void addBooking(Exam exam){register.put(exam.getCode(), exam);}

    public float getRefund(int codice){
        Exam e = register.get(codice);
        Calendar examCalendar = e.getBookingDate();
        Calendar now = Calendar.getInstance();
        //TODO
        long diff = ((examCalendar.getTimeInMillis() - now.getTimeInMillis()) / (24 * 60 * 60 * 1000));
        if (diff >= 4) {
            return e.getPrice();
        } else if (diff > 0 && diff <= 2) {
            return (float) (0.5*(e.getPrice()));
        } else {
            return 0;
        }
    }

    public void remove(int codice){register.remove(codice);}

    public int getSize() { return register.size(); }

    public boolean checkPatient(int codice, Patient p){
        Exam e = register.get(codice);
        return e.getPatient().equals(p);
    }

    public Exam getExam(int codice){
        return register.get(codice);
    }
}
