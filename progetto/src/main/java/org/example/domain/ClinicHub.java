package org.example.domain;

import java.time.LocalDate;
import java.sql.Time;
import java.util.*;

public class ClinicHub {

    private static ClinicHub clinicHub;
    private Map<String, Patient> patientRegister;
    private Patient currentPatient;
    private Hospitalization currentHosp;
    private Exam currentExam;
    private DoctorRegister doctorRegister;
    private ExamBookingRegister examBookingRegister;
    private HospitalizationBookingRegister hospitalizationBookingRegister;

    public Map<String, Patient> getPatientRegister() {
        return patientRegister;
    }

    public Patient getCurrentPatient() {
        return currentPatient;
    }

    public Hospitalization getCurrentHosp() {
        return currentHosp;
    }
    public Exam getCurrentExam(){
        return currentExam;
    }

    // Pattern Singleton
    public static ClinicHub getInstance(){
        if(clinicHub == null){
            clinicHub = new ClinicHub();
        }
        return clinicHub;
    }

    public boolean loginPatient(String cf) throws Exception {
        Patient p = patientRegister.get(cf);
        if(p != null) {
            currentPatient = p;
        } else {
            throw new Exception("Paziente non presente nel registro");
        }
        return true;
    }

    public boolean addPatient(String name, String lastname, LocalDate birthday, String residence, String cf, String telephone, String e_mail) throws Exception {
        Patient p = patientRegister.get(cf);
        if(p == null) {
            currentPatient = new Patient(name, lastname, birthday, residence, cf, telephone, e_mail);
        } else {
            throw new Exception("Paziente già registrato");
        }
        return true;
    }


    public void confirmPatient() throws Exception {
        if (currentPatient != null) {
            patientRegister.put(currentPatient.getCf(),currentPatient);
        } else {
            throw new Exception("Ordine chiamata metodi non rispettato");
        }
    }

    public List<Date> newHospitalization(String mode, Operation operation) throws Exception {
        if(currentPatient != null){
            currentHosp = HospitalizationFactory.getNewHospitalization(mode, operation);
            List<Date> dates = Utility.getDates();
            return dates;
        } else {
            throw new Exception();
        }

    }


    public void chooseHospitalization(Calendar start_date) throws Exception {
        if(currentHosp != null) {
            currentHosp.setData(start_date);
        } else {
        throw new Exception("Ordine chiamata metodi non rispettato");
        }
    }

    public float showPrice() throws Exception {
        if (currentHosp != null) {
            return currentHosp.getPrice();
        } else {
            throw new Exception("Ordine chiamata metodi non rispettato");
        }
    }

    public void confirmHospitalization() throws Exception {
        if (currentHosp != null && currentPatient != null) {
            currentHosp.setPatient(currentPatient);
            try {
                Doctor m = doctorRegister.getDoctor();
                currentHosp.setDoctor(m);
                hospitalizationBookingRegister.addBooking(currentHosp);
            } catch(Exception e) {
                System.out.println("Nessun paziente presente");
            }
        } else {
            throw new Exception("Ordine chiamata dei metodi errato");
        }
    }

    private ClinicHub(){
        this.patientRegister = Utility.loadPatient();
        this.doctorRegister = DoctorRegister.getInstance();
        Utility.loadDoctor();
        this.hospitalizationBookingRegister = HospitalizationBookingRegister.getInstance();
        this.examBookingRegister=ExamBookingRegister.getInstance();
    }

    public void chooseDoctor(String lastname) throws Exception{
        if(currentExam != null) {
            try {
                Doctor d = doctorRegister.getDoctor(lastname);
                currentExam.setDoctor(d);
                currentExam.setPrice((float) (currentExam.getPrice() + 0.5*currentExam.getPrice()));
            } catch (Exception e) {
                System.out.println("Nessun dottore trovato con quel cognome");
            }
        } else {
            throw new Exception("Ordine chiamata dei metodi errato");
        }
    }

    public List<Date> newExamBooking(ExamType examType){
        currentExam=new Exam(examType);
        List<Date> dates = Utility.getDates();
        return dates;
    }

    public List<Time> chooseExamDate(Calendar date) throws Exception{
        if (currentExam != null) {
            currentExam.setData(date);
            List<Time> times=Utility.getTimes();
            return times;
        } else throw new Exception("Ordine chiamata dei metodi errato");
    }

    public Calendar chooseExamTime(Time time) throws Exception{
        if (currentExam != null) {
            currentExam.setTime(time);
            return currentExam.getReadyDate();
        } else throw new Exception("Ordine chiamata dei metodi errato");
    }

    public float showExamPrice() throws Exception {
        if (currentExam != null) {
            return currentExam.getPrice();
        } else throw new Exception("Ordine chiamata dei metodi errato");
    }

    public void confirmBooking() throws Exception{
        if (currentExam != null) {
            if (currentExam.getDoctor() == null) {
                try {
                    Doctor m = doctorRegister.getDoctor();
                    currentExam.setDoctor(m);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            currentExam.setPatient(currentPatient);
            examBookingRegister.addBooking(currentExam);
        } else throw new Exception("Ordine chiamata dei metodi errato");
    }

    public boolean checkBooking(int codice,String tipologia) throws Exception{
        if(tipologia=="ESAME" && currentPatient != null){
            if(examBookingRegister.checkPatient(codice,currentPatient)){
                currentExam=examBookingRegister.getExam(codice);
                return true;
            } else {
                throw new Exception("Paziente diverso da quello che ha fatto la prenotazione");
            }
        }
        else if (tipologia=="RICOVERO" && currentPatient != null) {
            if(hospitalizationBookingRegister.checkPatient(codice,currentPatient)){
                currentHosp=hospitalizationBookingRegister.getHospitalization(codice);
                return true;
            } else {
                throw new Exception("Paziente diverso da quello che ha fatto la prenotazione");
            }
        } else {
            throw new Exception("Ordine chiamata metodi errato");
        }
    }

    public float calculateRefund(String tipologia) throws Exception {
        if(tipologia=="ESAME" && currentExam != null){
            return examBookingRegister.getRefund(currentExam.getCode());
        } else if (tipologia=="RICOVERO" && currentHosp != null) {
            return hospitalizationBookingRegister.getRefund(currentHosp.getCode());
        } else {
            throw new Exception("Ordine chiamata metodi errato");
        }
    }

    public void confirmCancel(String tipologia) throws Exception{
        if(tipologia=="ESAME" && currentExam != null){
            examBookingRegister.remove(currentExam.getCode());
        }
        else if (tipologia=="RICOVERO" && currentHosp != null) {
            hospitalizationBookingRegister.remove(currentHosp.getCode());
        } else {
            throw new Exception("Ordine chiamata metodi non rispettato");
        }
    }

}