package com.example.minedroid;

import android.widget.Button;

public class MapItem {
    public enum State{
        DEFAULT,OPENED,FLAGED
    }
    //地图项
    boolean isMine ;
    int mineCount ;
    Button viewButton ;
    State buttonState;

    public State getButtonState() {
        return buttonState;
    }

    public void setButtonState(State buttonState) {
        this.buttonState = buttonState;
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

    public  MapItem(boolean ismine) {
        setMine(ismine);
    }

     public void setState(MapItem.State state) {
        this.buttonState = state;
        if (state == MapItem.State.DEFAULT) {
            this.viewButton.setText("");
        } else if(state == MapItem.State.FLAGED) {
            this.viewButton.setText("标");
        } else if(state == MapItem.State.OPENED) {
            this.viewButton.setText(Integer.toString(this.getMineCount()));
        }
    }
}
