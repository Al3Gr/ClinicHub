package org.example.domain;

import java.util.Calendar;

public class H_Standard extends Hospitalization {

    public H_Standard(Operation op) {
        super(op);
        this.price = op.getPrice() + (50 * op.getConvalescence());
    }

    @Override
    public void setData(Calendar data) {
        start_date = data;
        setEndDate(data);

    }

    private void setEndDate(Calendar data){
        end_date = (Calendar) data.clone();
        end_date.add(Calendar.DAY_OF_MONTH, op.getConvalescence());
    }



}