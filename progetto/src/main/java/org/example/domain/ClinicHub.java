package org.example.domain;

import java.time.LocalDate;
import java.util.*;

public class ClinicHub {

    private static ClinicHub clinicHub;
    private Map<String, Patient> patientRegister;

    private Patient currentPatient;
    private Hospitalization currentHosp;
    private DoctorRegister doctorRegister;
    private Map<Integer,Hospitalization> hospRegister;

    public Map<String, Patient> getPatientRegister() {
        return patientRegister;
    }

    public Patient getCurrentPatient() {
        return currentPatient;
    }

    public Hospitalization getCurrentHosp() {
        return currentHosp;
    }

    public Map<Integer, Hospitalization> getHospRegister() {
        return hospRegister;
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
            List<Date> dates = getAvailableDates();
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
                int cod=currentHosp.getCode();
                hospRegister.put(cod,currentHosp);
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
        this.hospRegister = new HashMap<>();
        this.loadPatients();
        this.loadDoctors();
    }
    private List<Date> getAvailableDates(){
        Calendar cal = Calendar.getInstance();
        ArrayList<Date> dates = new ArrayList<>();
        dates.add(cal.getTime());
        for(int i = 1; i <= 30; i++) {
            cal.add(Calendar.DAY_OF_MONTH, 1);
            dates.add(cal.getTime());
        }
        return dates;
    }

}