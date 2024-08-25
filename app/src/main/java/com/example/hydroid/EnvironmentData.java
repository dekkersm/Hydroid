package com.example.hydroid;

import androidx.annotation.NonNull;

public class EnvironmentData {
    private float temperature;
    private float light_intensity;
    private float humidity;
    private float co2;
    private float baro;
    private long date;

    public EnvironmentData() {
    }

    public EnvironmentData(float temperature, float light_intensity, float humidity, float co2, float baro, long date) {
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

    public float getTemperature() {
        return temperature;
    }

    public float getLight_intensity() {
        return light_intensity;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getCo2() {
        return co2;
    }

    public float getBaro() {
        return baro;
    }

    public long getDate() {
        return date;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setLight_intensity(float light_intensity) {
        this.light_intensity = light_intensity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public void setCo2(float co2) {
        this.co2 = co2;
    }

    public void setBaro(float baro) {
        this.baro = baro;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
