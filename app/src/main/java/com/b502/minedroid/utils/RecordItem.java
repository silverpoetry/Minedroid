package com.b502.minedroid.utils;

import java.util.Date;

public class RecordItem {
    String date   ;
    int costtime;
    public RecordItem(String date, int time)
    {

        this.date =date;
        this.costtime =time;
    }
    public  RecordItem()
    {

    }

    @Override
    public String toString() {
        String time ;
        if(costtime>60)
        {
            time =Integer.toString(costtime/60)+"分"+Integer.toString(costtime%60)+"秒";
        }else
        {
            time =Integer.toString(costtime%60)+"秒";
        }
        return
                 date + ' ' +
                "用时" + time;
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
