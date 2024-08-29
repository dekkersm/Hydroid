package com.example.hydroid;

public class WaterData {
    private double tempValue;
    private long date;
    private boolean fillOn;
    private boolean pumpOn;

    public WaterData() {
    }
    public double getTempValue() {
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

    public void setTempValue(double tempValue) {
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
