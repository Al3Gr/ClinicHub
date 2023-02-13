package org.example.domain;

public class EmailService {

    private static EmailService instance;

    public static EmailService getIstance() {
        if(instance == null) {
            instance = new EmailService();
        }
        return instance;
    }

    //TODO
    public boolean sendResult(Exam e, String r) {
        return true;
    }
}
