package org.example.domain;

import org.example.interfaces.Observer;
import org.example.interfaces.BookingRegister;

import java.util.*;
import java.util.List;

public class ExamBookingRegister implements Observer<Exam>, BookingRegister<Exam> {
    private final Map<Integer, Exam> register;
    private static ExamBookingRegister examBookingRegister;

    //Pattern Singleton
    public static ExamBookingRegister getInstance(){
        if(examBookingRegister == null){
            examBookingRegister = new ExamBookingRegister();
        }
        return examBookingRegister;
    }
    private ExamBookingRegister(){this.register = new HashMap<>();}

    @Override
    public void add(Exam exam){
        register.put(exam.getCode(), exam);
        exam.addObserver(this);
    }

    @Override
    public float getRefund(int codice){
        Exam e = register.get(codice);
        Calendar examCalendar = e.getBookingDate();
        Calendar now = Calendar.getInstance();
        long diff = ((examCalendar.getTimeInMillis() - now.getTimeInMillis()) / (24 * 60 * 60 * 1000));
        if (diff >= 4) {
            return e.getPrice();
        } else if (diff >= 2) {
            return (float) (0.5*(e.getPrice()));
        } else {
            return 0;
        }
    }

    @Override
    public void remove(int codice){register.remove(codice);}

    @Override
    public int getSize() { return register.size(); }

    @Override
    public boolean checkPatient(int codice, Patient p){
        Exam e = register.get(codice);
        return e.getPatient().equals(p);
    }

    @Override
    public Exam getItem(int codice) throws Exception {
        Exam e = register.get(codice);
        if(e != null) {
            return e;
        } else {
            throw new Exception("Nessun esame presente con quel codice");
        }
    }

    public List<Exam> getTodayExamByDoc(Doctor d){
        Date now = Calendar.getInstance().getTime();
        List<Exam> list = new ArrayList<>();
        for(Exam e: register.values()){
            if((e.getDoctor().equals(d)) && (now.compareTo(e.getReadyDate().getTime()) >= 0)){
                list.add(e);
            }
        }
        return list;
    }

    @Override
    public void update(Exam observable) {
        register.remove(observable.getCode());
    }

    public List<Exam> getBookedExams() {
        return (List <Exam>) register.values();
    }

}
