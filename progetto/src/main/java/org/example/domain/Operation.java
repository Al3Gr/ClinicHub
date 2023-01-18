package org.example.domain;


public enum Operation {

    VASECTOMY(100, 6),
    APPENDECTOMY(70, 3),
    HYSTERECTOMY(110, 8),
    MASTECTOMY(200, 14),
    TONSILLECTOMY(180, 10),
    HEMORRHOIDECTOMY(95, 15),
    CHOLECYSTECTOMY(100,20),
    HYSTEROSCOPY(85,10),
    PNEUMONECTOMY(70,20),
    PERICARDIECTOMY(150, 25);


    private float price;
    private int convalescence;

    Operation(float price, int convalescence) {
        this.price = price;
        this.convalescence = convalescence;
    }

    public float getPrice() {
        return price;
    }

    public int getConvalescence() {
        return convalescence;
    }


}