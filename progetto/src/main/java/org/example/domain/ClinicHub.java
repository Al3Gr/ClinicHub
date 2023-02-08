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
        this.patientRegister = Utility.loadPatient();
        this.doctorRegister = DoctorRegister.getInstance();
        Utility.loadDoctor();
        this.hospRegister = new HashMap<>();
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