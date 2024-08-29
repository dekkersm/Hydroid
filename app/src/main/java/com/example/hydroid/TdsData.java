package com.example.hydroid;

public class TdsData {
    private int value;
    private long date;
    private boolean pumpOn;

    public TdsData() {
    }

    public TdsData(int value, long date, boolean pumpOn) {
        this.value = value;
        this.date = date;
        this.pumpOn = pumpOn;
    }

    public int getValue() {
        return value;
    }

    public long getDate() {
        return date;
    }

    public boolean isPumpOn() {
        return pumpOn;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setPumpOn(boolean pumpOn) {
        this.pumpOn = pumpOn;
    }
}
