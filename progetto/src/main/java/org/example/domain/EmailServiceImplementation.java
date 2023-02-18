package org.example.domain;

import org.example.interfaces.EmailService;

import java.io.FileWriter;
import java.io.IOException;

public class EmailServiceImplementation implements EmailService {


    public  boolean sendResult(Exam e, String info) {
        String filename= e.getPatient().getLastname() + "_" + e.getPatient().getName() + ".txt";
        String content = "Codice esame: " + e.getCode() + "\nCodice fiscale: " + e.getPatient().getCf() + "\nInformazioni: " + info;
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(content);
            writer.close();
        } catch (IOException ex) {
            System.err.println("Errore nella creazione e/o scrittura del file");
        }
        return true;
    }


}
