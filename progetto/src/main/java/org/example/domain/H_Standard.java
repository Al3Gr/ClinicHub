package org.example.domain;

import java.util.Calendar;

public class H_Standard extends Hospitalization {

    public H_Standard(Operation op) {
        super(op);
    }

    @Override
    public void setData(Calendar data) {
        start_date = data;
        end_date = data;
        end_date.add(Calendar.DAY_OF_MONTH, op.getConvalescence());
    }

    @Override
    public float getPrice() {
        return op.getPrice() + (50 * op.getConvalescence());
    }


}