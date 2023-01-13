package org.example.domain;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class DoctorRegister {

    private Map<String, Doctor> register;
    public Doctor getDoctor(){
        int i = new Random().nextInt(register.size());
        Doctor doc = new ArrayList<Doctor>(register.values()).get(i);
        return doc;
    }

    public void addDoctor(Doctor doc){

    }
}