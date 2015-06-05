package com.myweather.vishakha14.myweatherapp.Classes;


public class Weather {

    String weatherType, description, image;
    double dayTmp, morningTmp, minimumTmp, maximumTmp, nightTmp, eveningTmp, pressure, speed;
    int humidity,deg, clouds;
    long dt;

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getDayTmp() {
        return dayTmp;
    }

    public void setDayTmp(double dayTmp) {
        this.dayTmp = dayTmp;
    }

    public double getMorningTmp() {
        return morningTmp;
    }

    public void setMorningTmp(double morningTmp) {
        this.morningTmp = morningTmp;
    }

    public double getMinimumTmp() {
        return minimumTmp;
    }

    public void setMinimumTmp(double minimumTmp) {
        this.minimumTmp = minimumTmp;
    }

    public double getMaximumTmp() {
        return maximumTmp;
    }

    public void setMaximumTmp(double maximumTmp) {
        this.maximumTmp = maximumTmp;
    }

    public double getNightTmp() {
        return nightTmp;
    }

    public void setNightTmp(double nightTmp) {
        this.nightTmp = nightTmp;
    }

    public double getEveningTmp() {
        return eveningTmp;
    }

    public void setEveningTmp(double eveningTmp) {
        this.eveningTmp = eveningTmp;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public int getClouds() {
        return clouds;
    }

    public void setClouds(int clouds) {
        this.clouds = clouds;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }
}
