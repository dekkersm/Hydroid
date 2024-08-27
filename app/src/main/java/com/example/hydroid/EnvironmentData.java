package com.example.hydroid;

import androidx.annotation.NonNull;

public class EnvironmentData {
    private double temperature;
    private int light_intensity;
    private int humidity;
    private int co2;
    private int baro;
    private long date;

    public EnvironmentData() {
    }

    public EnvironmentData(double temperature, int light_intensity, int humidity, int co2, int baro, long date) {
        this.temperature = temperature;
        this.light_intensity = light_intensity;
        this.humidity = humidity;
        this.co2 = co2;
        this.baro = baro;
        this.date = date;
    }

    @NonNull
    @Override
    public String toString() {
        return "EnvironmentData{" +
                "temperature=" + temperature +
                ", light_intensity=" + light_intensity +
                ", humidity=" + humidity +
                ", co2=" + co2 +
                ", baro=" + baro +
                ", date=" + date +
                '}';
    }

    public double getTemperature() {
        return temperature;
    }

    public int getLight_intensity() {
        return light_intensity;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getCo2() {
        return co2;
    }

    public int getBaro() {
        return baro;
    }

    public long getDate() {
        return date;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setLight_intensity(int light_intensity) {
        this.light_intensity = light_intensity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public void setBaro(int baro) {
        this.baro = baro;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
