package org.example;

import java.util.*;

public class ClinicHub {

    private static ClinicHub clinicHub;
    private Map<String, Patient> patientRegister;

    private Patient currentPatient;
    private Hospitalization currentHosp;
    private DoctorRegister doctorRegister;
    private Map<Integer,Hospitalization> hospRegister; //VEDI

    // Pattern Singleton
    public static ClinicHub getInstance(){
        if(clinicHub == null){
            clinicHub = new ClinicHub();
        }
        return clinicHub;
    }

    public Patient checkPatient(String cf){
        Patient p = patientRegister.get(cf);
        if(p == null) {
            return null;
        } else {
            currentPatient = p;
            return p;
        }
    }
    public void addPatient(String name, String lastname, Date birthday, String residence, String cf, String telephone, String e_mail){
        currentPatient = new Patient(name,lastname,birthday,residence,cf,telephone,e_mail);
    }
    public void confirmPatient(){
        if (currentPatient != null) {
            patientRegister.put(currentPatient.getCf(),currentPatient);
        }
    }
    public List<Date> newHospitalization(String mode, Operation operation){
        currentHosp = HospitalizationFactory.getNewHospitalization(mode, operation);
        List<Date> dates = getAvailableDates();
        return dates;
    }
    private void loadDoctors(){

    }
    private void loadPatients(){

    }
    public void chooseHospitalization(Calendar start_date){
        if(currentHosp != null) {
            currentHosp.setData(start_date);
        }
    }
    public float calculatePrice(){
        return currentHosp.getPrice();
    }
    public void confirmHospitalization(){
        currentHosp.setPatient(currentPatient);
        Doctor m= doctorRegister.getDoctor();
        currentHosp.setDoctor(m);
        int cod=currentHosp.getCode();
        hospRegister.put(cod,currentHosp); //VEDI
    }
    private void ClinicHub(){
        patientRegister = new HashMap<>();
        doctorRegister = new DoctorRegister();
        hospRegister = new HashMap<>(); //VEDI
    }
    private List<Date> getAvailableDates(){
        Calendar cal = Calendar.getInstance();
        ArrayList<Date> dates = new ArrayList<>();
        for(int i = 0; i <= 30; i++) {
            cal.add(Calendar.DAY_OF_MONTH, i);
            dates.add(cal.getTime());
        }
        return dates;
    }
}