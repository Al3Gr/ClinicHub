package org.example.domain;

import java.util.HashMap;
import java.util.Map;

public class HospitalizationBookingRegister {
    private Map<Integer, Hospitalization> register;
    private static HospitalizationBookingRegister hospitalizationBookingRegister;

    //Pattern Singleton
    public static HospitalizationBookingRegister getInstance(){
        if(hospitalizationBookingRegister == null){
            hospitalizationBookingRegister = new HospitalizationBookingRegister();
        }
        return hospitalizationBookingRegister;
    }

    private HospitalizationBookingRegister(){this.register = new HashMap<>();}

    public void addBooking(Hospitalization hospitalization){
        register.put(hospitalization.getCode(), hospitalization);
    }

    //TODO 2
    public float getRefund(int codice){
        return (register.get(codice)).getPrice(); //DA SISTEMARE QUANTO SI PUO TORNARE IN BASE AI GIORNI;
    }

    public void remove(int codice){register.remove(codice);}

    public int getSize() { return register.size(); }

    public boolean checkPatient(int codice, Patient p){
        Hospitalization h = register.get(codice);
        return h.getPatient().equals(p);
    }

    public Hospitalization getHospitalization(int codice){
        return register.get(codice);
    }
}
