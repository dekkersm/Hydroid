package com.example.hydroid;

public class EnvironmentData {
    private float temperature;
    private float light_intensity;
    private int humidity;

    public EnvironmentData() {
    }

    public EnvironmentData(float temperature, float light_intensity, int humidity) {
        this.temperature = temperature;
        this.light_intensity = light_intensity;
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "EnvironmentData{" +
                "temperature=" + temperature +
                ", light_intensity=" + light_intensity +
                ", humidity=" + humidity +
                '}';
    }

    public float getTemperature() {
        return temperature;
    }

    public float getLight_intensity() {
        return light_intensity;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setLight_intensity(float light_intensity) {
        this.light_intensity = light_intensity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }
}
