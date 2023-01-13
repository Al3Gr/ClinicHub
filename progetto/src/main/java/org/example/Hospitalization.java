package org.example;

import java.util.Calendar;
import java.util.Date;

public abstract class Hospitalization{
    protected float price;
    protected Calendar start_date;
    protected int code;
    protected Calendar end_date;

    protected Operation op;
    public abstract void setData(Calendar data);

    public abstract float getPrice();

    public Calendar getStart_date() {
        return start_date;
    }

    public int getCode() {
        return code;
    }

    public Calendar getEnd_date() {
        return end_date;
    }

    public Hospitalization(Operation op) {
        this.op = op;
    }
}