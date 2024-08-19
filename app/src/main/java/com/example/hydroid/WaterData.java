package com.example.hydroid;

import androidx.annotation.NonNull;

public class WaterData {
    private float tempValue;
    private long date;
    private boolean fillOn;
    private boolean pumpOn;

    public WaterData() {
    }

    public WaterData(boolean pumpOn, boolean fillOn, long date, float tempValue) {
        this.pumpOn = pumpOn;
        this.fillOn = fillOn;
        this.date = date;
        this.tempValue = tempValue;
    }

    @NonNull
    @Override
    public String toString() {
        return "tempValue=" + tempValue +
                ", date=" + date +
                ", fillOn=" + fillOn +
                ", pumpOn=" + pumpOn;
    }

    public float getTempValue() {
        return tempValue;
    }

    public long getDate() {
        return date;
    }

    public boolean isFillOn() {
        return fillOn;
    }

    public boolean isPumpOn() {
        return pumpOn;
    }

    public void setTempValue(float tempValue) {
        this.tempValue = tempValue;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setFillOn(boolean fillOn) {
        this.fillOn = fillOn;
    }

    public void setPumpOn(boolean pumpOn) {
        this.pumpOn = pumpOn;
    }
}
