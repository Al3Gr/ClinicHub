package org.example.interfaces;

public interface Observable <T>{
    void addObserver(T observer);
    void removeObserver(T observer);
    void notifyObserver();
}
