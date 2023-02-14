package org.example.domain;

import java.io.FileWriter;
import java.io.IOException;

public class  EmailService {

    public static boolean sendResult(Exam e, String info) {
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
