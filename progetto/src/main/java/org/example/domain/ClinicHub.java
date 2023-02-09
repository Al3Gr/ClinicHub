package org.example.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ClinicHub {

    private static ClinicHub clinicHub;
    private Map<String, Patient> patientRegister;
    private Patient currentPatient;
    private Hospitalization currentHosp;
    private Exam currentExam;
    private final DoctorRegister doctorRegister;
    private final ExamBookingRegister examBookingRegister;
    private final HospitalizationBookingRegister hospitalizationBookingRegister;

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

    public List<LocalTime> chooseExamDate(Calendar date){
         currentExam.setData(date);
         List<LocalTime> times=Utility.getTimes();
         return times;
    }

    public Calendar chooseExamTime(LocalTime time){
        currentExam.setTime(time);
        return currentExam.getReadyDate();
    }

    public float showExamPrice(){ return currentExam.getPrice();}


    public void confirmBooking() throws Exception{
        if(currentPatient != null) {
            if (currentExam.getDoctor() == null) {
                Doctor m = doctorRegister.getDoctor();
                currentExam.setDoctor(m);
            }
            currentExam.setPatient(currentPatient);
            examBookingRegister.addBooking(currentExam);
        } else {
            throw new Exception("ordine chiamata dei metodi errato");
        }
    }

    public boolean checkBooking(int codice,String tipologia) throws Exception{
        if(tipologia=="ESAME"){
            if(examBookingRegister.checkPatient(codice,currentPatient)){
                currentExam=examBookingRegister.getExam(codice);
                return true;
            } else {
                throw new Exception("Paziente diverso da quello che ha fatto la prenotazione");
            }
        }
        else if (tipologia=="RICOVERO") {
            if(hospitalizationBookingRegister.checkPatient(codice,currentPatient)){
                currentHosp=hospitalizationBookingRegister.getHospitalization(codice);
                return true;
            } else {
                throw new Exception("Paziente diverso da quello che ha fatto la prenotazione");
            }
        }
        return false;
    }

    public float calculateRefund(String tipologia){
        float refund = 0;

        if(tipologia=="ESAME"){
            refund=examBookingRegister.getRefund(currentExam.getCode());
        }
        else if (tipologia=="RICOVERO") {
            refund=hospitalizationBookingRegister.getRefund(currentHosp.getCode());
        }

        return refund;
    }

    public void confirmCancel(String tipologia){

        if(tipologia=="ESAME"){
            examBookingRegister.remove(currentExam.getCode());
        }
        else if (tipologia=="RICOVERO") {
            hospitalizationBookingRegister.remove(currentHosp.getCode());
        }

    }


}