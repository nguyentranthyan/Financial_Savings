package com.example.financial_savings.modules.dates;

import com.example.financial_savings.entities.SoGiaoDich;

import java.sql.Date;

public class DateSortModule implements Comparable<SoGiaoDich> {

    private Date dateTime;

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date datetime) {
        this.dateTime = datetime;
    }

    @Override
    public int compareTo(SoGiaoDich o) {
        return getDateTime().compareTo(o.getNgayGiaoDich());
    }
}
