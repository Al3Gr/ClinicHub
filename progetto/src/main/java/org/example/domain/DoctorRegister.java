package org.example.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DoctorRegister {

    private final Map<String, Doctor> register;
    private static DoctorRegister instance;

    public static DoctorRegister getInstance() {
        if(instance == null) {
            instance = new DoctorRegister();
        }
        return instance;
    }


    private DoctorRegister() {
        this.register = new HashMap<>();
    }

    public Doctor getDoctor() throws Exception {
        if (register.size() > 0) {
            int i = new Random().nextInt(register.size());
            Doctor doc = new ArrayList<>(register.values()).get(i);
            return doc;
        } else {
            throw new Exception("Nessun dottore presente");
        }
    }

    public void addDoctor(Doctor doc){
        register.put(doc.getCf(), doc);
    }

    public int getSize() { return register.size(); }

    public Doctor getDoctor(String lastname) throws Exception{
        for (Doctor d: register.values()) {
            if(d.getLastname().toLowerCase().equals(lastname.toLowerCase()))
                return d;
        }
        throw new Exception("Nessun dottore presente con quel cognome");
    }

    public Doctor getDoctorByCf(String cf) throws Exception{
        for (Doctor d: register.values()) {
            if(d.getCf().toLowerCase().equals(cf.toLowerCase()))
                return d;
        }
        throw new Exception("Nessun dottore presente con il codice fiscale indicato");
    }

}