package com.example.hydroid;

import androidx.annotation.NonNull;

public class TdsData {
    private float value;
    private float date;
    private boolean pumpOn;

    public TdsData() {
    }

    public TdsData(float value, float date, boolean pumpOn) {
        this.value = value;
        this.date = date;
        this.pumpOn = pumpOn;
    }

    @NonNull
    @Override
    public String toString() {
        return "value=" + value +
                ", date=" + date +
                ", pumpOn=" + pumpOn;
    }

    public float getValue() {
        return value;
    }

    public float getDate() {
        return date;
    }

    public boolean isPumpOn() {
        return pumpOn;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setDate(float date) {
        this.date = date;
    }

    public void setPumpOn(boolean pumpOn) {
        this.pumpOn = pumpOn;
    }
}
