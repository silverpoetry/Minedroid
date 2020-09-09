package com.b502.minedroid.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import java.nio.channels.IllegalSelectorException;


public class TimeManagementMaster {
    boolean hangedup = false;
    boolean finished = false;
    Thread t;
    Handler hand;
    int interval; //单位0.1s

    public TimeManagementMaster(Handler hand, int interval) {
        this.hand = hand;
        this.interval = interval;
    }

    public void setInterval(int var) {
        interval = var;
    }

    public void pause() {
        hangedup = true;
    }

    public void stop() {
        finished = true;

    }


    public void start() {
        if (hangedup == true) {
            hangedup = false;
            return;
        }
        finished = false;
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                int ticker = 0;
                while (!finished) {
                    while (hangedup) ;
                    if (ticker >= interval) {
                        ticker = 0;
                        hand.sendMessage(new Message());
                    }
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {

                    }
                    ticker++;


                }
            }
        });
        t.start();

    }

    public boolean isHangedup() {
        return hangedup;
    }

    public boolean isFinished() {
        return finished;
    }

    public Thread getT() {
        return t;
    }

    public Handler getHand() {
        return hand;
    }

    public int getInterval() {
        return interval;
    }

    public void setHangedup(boolean hangedup) {
        this.hangedup = hangedup;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setT(Thread t) {
        this.t = t;
    }

    public void setHand(Handler hand) {
        this.hand = hand;
    }
}
