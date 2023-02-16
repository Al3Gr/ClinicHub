package org.example.interfaces;

import org.example.domain.Patient;

public interface BookingRegister<K> {
    void add(K item);
    void remove(int code) throws Exception;
    K getItem(int code) throws Exception;
    int getSize();
    float getRefund(int code);
    boolean checkPatient(int code, Patient p);
}
