package org.example.domain;

import org.example.interfaces.BookingRegister;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class HospitalizationBookingRegister implements BookingRegister<Hospitalization> {
    private final Map<Integer, Hospitalization> register;
    private static HospitalizationBookingRegister hospitalizationBookingRegister;

    //Pattern Singleton
    public static HospitalizationBookingRegister getInstance(){
        if(hospitalizationBookingRegister == null){
            hospitalizationBookingRegister = new HospitalizationBookingRegister();
        }
        return hospitalizationBookingRegister;
    }

    private HospitalizationBookingRegister(){this.register = new HashMap<>();}

    @Override
    public void add(Hospitalization hospitalization){
        register.put(hospitalization.getCode(), hospitalization);
    }

    @Override
    public float getRefund(int codice){
        Hospitalization hosp = register.get(codice);
        Calendar hospCalendar = hosp.getStart_date();
        Calendar now = Calendar.getInstance();
        long diff = ((hospCalendar.getTimeInMillis() - now.getTimeInMillis()) / (24 * 60 * 60 * 1000));
        if (diff >= 7) {
            return (float) (0.5 * hosp.getPrice());
        } else if (diff >= 3) {
            return (float) (0.2 * hosp.getPrice());
        } else {
            return 0;
        }
    }

    @Override
    public void remove(int codice){register.remove(codice);}

    @Override
    public int getSize() { return register.size(); }

    @Override
    public boolean checkPatient(int codice, Patient p){
        Hospitalization h = register.get(codice);
        return h.getPatient().equals(p);
    }

    @Override
    public Hospitalization getItem(int codice) throws Exception{
        Hospitalization h = register.get(codice);
        if(h != null) {
            return h;
        } else {
            throw new Exception("Nessun esame presente con quel codice");
        }
    }

    public List<Hospitalization> getHospitalizations() {
        return (List<Hospitalization>) register.values();
    }
}
