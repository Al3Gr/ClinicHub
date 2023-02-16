package org.example.interfaces;

import org.example.domain.Patient;

import java.util.List;

public interface BookingRegister<K> {
    void add(K item);
    void remove(int code) throws Exception;
    K getItem(int code) throws Exception;
    int getSize();
    float getRefund(int code);
    boolean checkPatient(int code, Patient p);
    List<K> getItems();
}
