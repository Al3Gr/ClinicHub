package org.example;


import java.util.Date;
import java.util.List;

public class ClinicHub {

    private static ClinicHub clinicHub;

    // Pattern Singleton
    public static ClinicHub getInstance(){
        if(clinicHub == null){
            clinicHub = new ClinicHub();
        }
        return clinicHub;
    }

    public Patient checkPatient(String cf){
        return null;
    }
    public void addPatient(String name, String lastname, Date birthday, String residence, String cf, String telephone, String e_mail){

    }
    public void confirmPatient(){

    }
    public List<Date> newHospitalization(String mode, Operation operation){
        return null;
    }
    private void loadDoctors(){

    }
    private void loadPatients(){

    }
    public void chooseHospitalization(Date start_date){

    }
    public float calculatePrice(){
        return 0;
    }
    public void confirmHospitalization(){

    }
    private void ClinicHub(){

    }
    private List<Date> getAvailableDates(){
        return null;
    }
}