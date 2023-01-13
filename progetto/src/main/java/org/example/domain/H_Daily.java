package org.example.domain;

import java.util.Calendar;

public class H_Daily extends Hospitalization{

    public H_Daily(Operation op) {
        super(op);
    }

    @Override
    public void setData(Calendar data) {
        start_date = data;
        end_date = start_date;
    }

    @Override
    public float getPrice() {
        return op.getPrice();
    }


}