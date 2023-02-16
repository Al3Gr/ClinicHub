package org.example.domain;

import java.time.LocalTime;
import java.util.*;
import java.time.LocalDate;
public class Utility {
    public static HashMap<String, Patient> loadPatient(){
        HashMap<String, Patient> register = new HashMap<>();
        Patient p1 = new Patient("Carlo", "Bianchi", LocalDate.now(), "via S.Carlo 1", "cf2", "3331112222", "ca.bianchi@gmail.com");
        Patient p2 = new Patient("Marco", "Rossi", LocalDate.now(), "via S.Marco 2", "cf3", "3444444555", "mar.rss@gmail.com");
        register.put(p1.getCf(),p1);
        register.put(p2.getCf(),p2);
        System.out.println("Caricamento pazienti completato");
        return register;
    }

    public static void loadDoctor(){
        DoctorRegister register = DoctorRegister.getInstance();
        Doctor d1 = new Doctor("Damiano", "Gr", LocalDate.now(), "cf0", "da.gr@gmail.com", "000111222");
        Doctor d2 = new Doctor("Eleonora", "Fd", LocalDate.now(), "cf1", "el.fg@gmail.com", "333444555");
        register.addDoctor(d1);
        register.addDoctor(d2);
        System.out.println("Caricamento dottori completato");
    }

    public static List<Date> getDates(){
        Calendar cal = Calendar.getInstance();
        ArrayList<Date> dates = new ArrayList<>();
        dates.add(cal.getTime());
        for(int i = 1; i <= 30; i++) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            dates.add(cal.getTime());
        }
        return dates;
    }

    public static List<LocalTime> getTimes(){
        LocalTime start_time = LocalTime.of(8, 0, 0);
        LocalTime end_time = LocalTime.of(18, 0, 0);
        ArrayList<LocalTime> times = new ArrayList<>();
        int i = 30;
        while (start_time.compareTo(end_time) <= 0){
            times.add(start_time);
            start_time = start_time.plusMinutes(i);
        }

        return times;
    }

    public static void loadTodayExams(){
        Doctor d = new Doctor("Eleonora", "Fd", LocalDate.now(), "cf1", "el.fg@gmail.com", "333444555");
        Patient p= new Patient("Mario", "Rossi", LocalDate.now(), "via mario rossi 10", "cf12345", "000111555", "prova@gmail.com");
        Patient p1= new Patient("Topolino", "Rossi", LocalDate.now(), "via mario rossi 10", "cf4040", "000111555", "prova@gmail.com");
        Exam e =new Exam(ExamType.BLOOD_ANALYSIS);
        e.setDoctor(d);
        e.setPatient(p);
        e.setReadyDateToday(Calendar.getInstance());
        ExamBookingRegister.getInstance().add(e);
        e=new Exam(ExamType.ELECTROMYOGRAPHY);
        e.setReadyDateToday(Calendar.getInstance());
        e.setDoctor(d);
        e.setPatient(p1);
        //e.setCode(2);
        ExamBookingRegister.getInstance().add(e);
    }


}
