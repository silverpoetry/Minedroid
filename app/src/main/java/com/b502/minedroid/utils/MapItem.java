package com.b502.minedroid.utils;

import android.widget.Button;


public class MapItem {
    public enum State {
        DEFAULT, OPENED, FLAGED, BOOM, MISFLAGED
    }

    //地图项
    boolean isMine;
    int mineCount;
    Button viewButton;
    State buttonState;

    public State getButtonState() {
        return buttonState;
    }


    public boolean isMine() {
        return isMine;
    }

    public int getMineCount() {
        return mineCount;
    }

    public Button getViewButton() {
        return viewButton;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }

    public void setViewButton(Button viewButton) {
        this.viewButton = viewButton;
    }

    public MapItem(boolean ismine) {
        setMine(ismine);
    }

    public void setButtonState(MapItem.State state) {
        this.buttonState = state;
        if (state == MapItem.State.DEFAULT) {
            this.viewButton.setText("");
        } else if (state == MapItem.State.FLAGED) {
            this.viewButton.setText("标");
        } else if (state == MapItem.State.OPENED) {
            int mineCount = this.getMineCount();
            if (mineCount != 0) {
                this.viewButton.setText(Integer.toString(mineCount));
            }
            this.viewButton.setBackgroundColor(0x00000000);
        } else if (state == State.MISFLAGED) {
            this.viewButton.setText("X");
        } else if (state == State.BOOM) {
            this.viewButton.setText("雷");
        }
    }
}
