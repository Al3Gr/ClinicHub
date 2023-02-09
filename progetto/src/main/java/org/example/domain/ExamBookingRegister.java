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

    //TODO 1
    public float getRefund(){
        Calendar examCalendar = ClinicHub.getInstance().getCurrentExam().getBookingDate();
        examCalendar.add(Calendar.HOUR_OF_DAY,ClinicHub.getInstance().getCurrentExam().getTime().getHours());
        examCalendar.add(Calendar.MINUTE,ClinicHub.getInstance().getCurrentExam().getTime().getMinutes());
        Calendar now = Calendar.getInstance();
        long diff = (examCalendar.getTimeInMillis() - now.getTimeInMillis()) / (24 * 60 * 60 * 1000);
        if (diff >= 4) {
            return ClinicHub.getInstance().getCurrentExam().getPrice();
        } else if (diff >= 0 && diff <= 2) {
            return (float) (0.5*(ClinicHub.getInstance().getCurrentExam().getPrice()));
        }
        return 0;
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
