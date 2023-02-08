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

    private void loadDoctors(){
        Doctor d1 = new Doctor("Damiano", "Gr", new Date(), "cf0", "da.gr@gmail.com", "000111222");
        Doctor d2 = new Doctor("Eleonora", "Fd", new Date(), "cf1", "el.fg@gmail.com", "333444555");
        doctorRegister.addDoctor(d1);
        doctorRegister.addDoctor(d2);
        System.out.println("Caricamento dottori completato");
    }

    private void loadPatients(){
        Patient p1 = new Patient("Carlo", "Bianchi", LocalDate.now(), "via S.Carlo 1", "cf2", "3331112222", "ca.bianchi@gmail.com");
        Patient p2 = new Patient("Marco", "Rossi", LocalDate.now(), "via S.Marco 2", "cf3", "3444444555", "mar.rss@gmail.com");
        patientRegister.put(p1.getCf(),p1);
        patientRegister.put(p2.getCf(),p2);
        System.out.println("Caricamento pazienti completato");
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
        this.patientRegister = new HashMap<>();
        this.doctorRegister = DoctorRegister.getInstance();
        this.hospitalizationBookingRegister = HospitalizationBookingRegister.getInstance();
        this.examBookingRegister=ExamBookingRegister.getInstance();
        this.loadPatients();
        this.loadDoctors();
    }
    public void chooseDoctor(String lastname){
        Doctor d=doctorRegister.getDoctorByLastname(lastname); //SISTEMA IL METODO GETDOCTOR
        currentExam.setDoctor(d);
        //currentExam.setprice() ??? VEDI COSA FARE
    }
    public List<Date> newExamBooking(ExamType examType){
        currentExam=new Exam(examType);
        List<Date> dates = Utility.getDates();
        return dates;
    }
    public List<Time> chooseExamDate(Calendar date){
         currentExam.setData(date);
         List<Time> times=Utility.getTimes();
         return times;
    }
    public Calendar chooseExamTime(Time time){
        currentExam.setTime(time);
        return currentExam.getReadyDate();
    }
    public float showExamPrice(){ return currentExam.getPrice();}
    public void confirmBooking() throws Exception{
        if(currentExam.getDoctor()==null){
            try {
                Doctor m = doctorRegister.getDoctor();
                currentExam.setDoctor(m);
            }catch(Exception e){
                System.out.println(e);
            }
        }
        currentExam.setPatient(currentPatient);
        examBookingRegister.addBooking(currentExam);
    }
    public boolean checkBooking(int codice,String tipologia){
        boolean f=false;
        if(tipologia=="ESAME"){
            f=examBookingRegister.checkPatient(codice,currentPatient);
            if(f==true){
                currentExam=examBookingRegister.getExam(codice);
            }
            return f;
        }
        else if (tipologia=="RICOVERO") {
            f=hospitalizationBookingRegister.checkPatient(codice,currentPatient);
            if(f==true){
                currentHosp=hospitalizationBookingRegister.getHospitalization(codice);
            }
            return f;
        }
        return f;
    }
    public float calculateRefund(String tipologia){
        float refund=0;
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