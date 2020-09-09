package com.b502.minedroid.utils;

import java.util.Date;

public class RecordItem {
    String date   ;
    int costtime;
    public RecordItem(String date, int time)
    {
        Date d = new Date();
        this.date =date;
        this.costtime =time;
    }


    public String getDate() {
        return date;
    }

    public int getCosttime() {
        return costtime;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCosttime(int costtime) {
        this.costtime = costtime;
    }
}
