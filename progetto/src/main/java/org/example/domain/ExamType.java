package org.example.domain;
public enum ExamType {

    BLOOD_ANALYSIS(30,3),
    URINE_ANALYSIS(20,2),
    MAMMOGRAPHY(40,7),
    ECHOCARDIOGRAPHY(50,8),
    ELECTROMYOGRAPHY(50,8),
    ELECTROENCEPHALOGRAPHY(50,7);
    private float price;
    private int daysToReady;
    ExamType(float price, int daysToReady) {
        this.price = price;
        this.daysToReady = daysToReady;
    }

    public float getPrice() {
        return price;
    }

    public int getDaysToReady() {
        return daysToReady;
    }
}
