package com.example.hydroid;

import androidx.annotation.NonNull;

public class TdsData {
    private long value;
    private long date;
    private boolean pumpOn;

    public TdsData() {
    }

    public TdsData(long value, long date, boolean pumpOn) {
        this.value = value;
        this.date = date;
        this.pumpOn = pumpOn;
    }

    @NonNull
    @Override
    public String toString() {
        return "value=" + value +
                ", date=" + DateUtils.getReadableDateFromLong((long) date) +
                ", pumpOn=" + pumpOn;
    }

    public long getValue() {
        return value;
    }

    public long getDate() {
        return date;
    }

    public boolean isPumpOn() {
        return pumpOn;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setPumpOn(boolean pumpOn) {
        this.pumpOn = pumpOn;
    }
}
