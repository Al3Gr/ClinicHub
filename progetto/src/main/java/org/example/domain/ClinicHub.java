package org.example.domain;

import org.example.interfaces.EmailService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ClinicHub {

    private static ClinicHub clinicHub;
    private final Map<String, Patient> patientRegister;
    private Patient currentPatient;
    private Hospitalization currentHosp;
    private Exam currentExam;
    private Doctor currentDoctor;
    private final DoctorRegister doctorRegister;
    private final ExamBookingRegister examBookingRegister;
    private final HospitalizationBookingRegister hospitalizationBookingRegister;

    private final EmailService emailService;

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

    public List<Date> newHospitalizationBooking(String mode, Operation operation) throws Exception {
        if(currentPatient != null){
            currentHosp = HospitalizationFactory.getNewHospitalization(mode, operation);
            return Utility.getDates();
        } else {
            throw new Exception("currentPatient is null");
        }

    }

    public void chooseHospitalizationDate(Calendar start_date) throws Exception {
        if(currentHosp != null) {
            currentHosp.setData(start_date);
        } else {
        throw new Exception("Ordine chiamata metodi non rispettato");
        }
    }

    public float showHospPrice() throws Exception {
        if (currentHosp != null) {
            return currentHosp.getPrice();
        } else {
            throw new Exception("Ordine chiamata metodi non rispettato");
        }
    }

    public void confirmHospitalizationBooking() throws Exception {
        if (currentHosp != null && currentPatient != null) {
            currentHosp.setPatient(currentPatient);
            try {
                Doctor m = doctorRegister.getDoctor();
                currentHosp.setDoctor(m);
                hospitalizationBookingRegister.add(currentHosp);
            } catch(Exception e) {
                System.out.println("Nessun medico presente");
            }
        } else {
            throw new Exception("Ordine chiamata dei metodi errato");
        }
    }

    private ClinicHub(){
        this.patientRegister = Utility.loadPatient();
        this.doctorRegister = DoctorRegister.getInstance();
        this.hospitalizationBookingRegister = HospitalizationBookingRegister.getInstance();
        this.examBookingRegister=ExamBookingRegister.getInstance();
        this.emailService = new EmailServiceImplementation();
        Utility.loadDoctor();
        Utility.loadTodayExams();
    }

    public void chooseDoctor(String lastname) throws Exception{
        if(currentExam != null) {
            try {
                Doctor d = doctorRegister.getDoctor(lastname.toLowerCase());
                currentExam.setDoctor(d);
                currentExam.setPrice((float) (currentExam.getPrice() + 0.5*currentExam.getPrice()));
            } catch (Exception e) {
                throw new Exception("Nessun dottore trovato con quel cognome");
            }
        } else {
            throw new Exception("Ordine chiamata dei metodi errato");
        }
    }

    public List<Date> newExamBooking(ExamType examType) throws Exception{
        if(currentPatient != null) {
            currentExam = new Exam(examType);
            return Utility.getDates();
        } else {
            throw new Exception("currentPatient is null");
        }
    }

    public List<LocalTime> chooseExamDate(Calendar date) throws Exception{
        if(currentExam != null){
         currentExam.setData(date);
         return Utility.getTimes();
        } else {
            throw new Exception("Ordine chiamata metodi non rispettato");
        }
    }

    public Calendar chooseExamTime(LocalTime time) throws Exception{
        if(currentExam != null){
            currentExam.setTime(time);
            return currentExam.getReadyDate();
        } else {
            throw new Exception("Ordine chiamata metodi non rispettato");
        }

    }

    public float showExamPrice(){ return currentExam.getPrice();}

    public void confirmExamBooking() throws Exception{
        if(currentPatient != null) {
            if (currentExam.getDoctor() == null) {
                Doctor m = doctorRegister.getDoctor();
                currentExam.setDoctor(m);
            }
            currentExam.setPatient(currentPatient);
            examBookingRegister.add(currentExam);
        } else {
            throw new Exception("ordine chiamata dei metodi errato");
        }
    }

    public boolean checkBooking(int codice,String tipologia) throws Exception{
        if(tipologia=="ESAME"){
            if(examBookingRegister.checkPatient(codice,currentPatient)){
                currentExam=examBookingRegister.getItem(codice);
                return true;
            } else {
                throw new Exception("Paziente diverso da quello che ha fatto la prenotazione");
            }
        }
        else if (tipologia=="RICOVERO") {
            if(hospitalizationBookingRegister.checkPatient(codice,currentPatient)){
                currentHosp=hospitalizationBookingRegister.getItem(codice);
                return true;
            } else {
                throw new Exception("Paziente diverso da quello che ha fatto la prenotazione");
            }
        }
        return false;
    }

    public float calculateRefund(String tipologia) throws Exception {
        float refund = 0;

        if(tipologia=="ESAME"){
            if(currentExam != null) {
                refund = examBookingRegister.getRefund(currentExam.getCode());
            } else {
                throw new Exception("currentExam is null");
            }
        }
        else if (tipologia=="RICOVERO") {
            if(currentHosp != null) {
                refund = hospitalizationBookingRegister.getRefund(currentHosp.getCode());
            } else {
                throw new Exception("currentHosp is null");
            }
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

    public List<Exam> loginMed(String cf) throws Exception{
        try {
            currentDoctor = doctorRegister.getDoctorByCf(cf);
            return examBookingRegister.getTodayExamByDoc(currentDoctor);
        } catch(Exception e) {
            throw new Exception("Nessun medico trovato con il codice fiscale indicato");
        }
    }

    public Doctor getCurrentDoctor() {
        return currentDoctor;
    }

    public void selectExamReady(int code, String info) throws Exception {
        currentExam = examBookingRegister.getItem(code);
        currentExam.setState(info);
    }

    public boolean sendResultForEmail() throws Exception{
        if (currentExam != null) {
            return emailService.sendResult(currentExam, currentExam.getResultInfo());
        } else {
            throw new Exception("Ordine di chiamata dei metodi non rispettato");

        }
    }


    public boolean addDoctor(String nome, String cognome, LocalDate dataNascita, String cf, String telefono, String email) {
        try {
            Doctor d = doctorRegister.getDoctorByCf(cf);
        } catch (Exception e) {
            currentDoctor = new Doctor(nome, cognome, dataNascita, cf, email, telefono);
            return true;
        }
        return false;
    }

    public void confirmDoctor() throws Exception{
        if (currentDoctor != null) {
            doctorRegister.addDoctor(currentDoctor);
        } else {
            throw new Exception("Ordine di chiamata dei metodi non rispettato");
        }
    }

    public List<Patient> printRegisteredPatients() {
        return new ArrayList<>(patientRegister.values());
    }

    public List<Hospitalization> printBookedHospitalizations() {
        return hospitalizationBookingRegister.getItems();
    }

    public List<Exam> printBookingExams() {
        return examBookingRegister.getItems();
    }

    public List<Doctor> printMedicalStaff() {
        return doctorRegister.getDoctors();
    }

}