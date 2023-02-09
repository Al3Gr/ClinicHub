package org.example.domain;

import java.util.Calendar;
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
    public float getRefund(){
        Calendar hospCalendar = ClinicHub.getInstance().getCurrentHosp().getStart_date();
        Calendar now = Calendar.getInstance();
        long diff = (hospCalendar.getTimeInMillis() - now.getTimeInMillis()) / (24 * 60 * 60 * 1000);
        if (diff >= 7) {
            return (float) (0.5*ClinicHub.getInstance().getCurrentHosp().getPrice());
        } else if (diff >= 0 && diff <= 3) {
            return (float) (0.2*(ClinicHub.getInstance().getCurrentExam().getPrice()));
        }
        return 0;
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
