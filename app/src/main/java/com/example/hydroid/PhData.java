package com.example.hydroid;

import androidx.annotation.NonNull;

public class PhData {
    private float value;
    private long date;
    private boolean phUpOn;
    private boolean phDownOn;

    public PhData() {
    }

    public PhData(float value, long date, boolean phUpOn, boolean phDownOn) {
        this.value = value;
        this.date = date;
        this.phUpOn = phUpOn;
        this.phDownOn = phDownOn;
    }

    @NonNull
    @Override
    public String toString() {
        return "value=" + value +
                ", date=" + DateUtils.getReadableDateFromLong((long) date) +
                ", phUpOn=" + phUpOn +
                ", phDownOn=" + phDownOn;
    }

    public float getValue() {
        return value;
    }

    public long getDate() {
        return date;
    }

    public boolean isPhUpOn() {
        return phUpOn;
    }

    public boolean isPhDownOn() {
        return phDownOn;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void setPhUpOn(boolean phUpOn) {
        this.phUpOn = phUpOn;
    }

    public void setPhDownOn(boolean phDownOn) {
        this.phDownOn = phDownOn;
    }
}
