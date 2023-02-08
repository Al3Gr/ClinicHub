package org.example.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DoctorRegister {

    private Map<String, Doctor> register;
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
            Doctor doc = new ArrayList<Doctor>(register.values()).get(i);
            return doc;
        } else {
            throw new Exception("Nessun dottore presente");
        }
    }

    public void addDoctor(Doctor doc){
        register.put(doc.getCf(), doc);
    }

    public int getSize() { return register.size(); }
    public Doctor getDoctorByLastname(String lastname){
        return null; //COME LO CERCHIAMO?
    }
}