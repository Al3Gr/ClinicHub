package org.example.domain;

public class HospitalizationFactory {
    public static Hospitalization getNewHospitalization(String mode, Operation operation){
        if(mode == "DAILY") {
            return new H_Daily(operation);
        } else {
            return new H_Standard(operation);
        }
    }
}