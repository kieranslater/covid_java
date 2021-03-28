package com.company;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Row {
    public Row(String country, Date date, int deaths, int recovered, int active, int confirmed) {
        this.country = country;
        this.date = date;
        this.deaths = deaths;
        this.recovered = recovered;
        this.active = active;
        this.confirmed = confirmed;
    }

    public Row(Map<String, String> mapDetails) throws ParseException {
        this.country = mapDetails.get("country");
        this.date =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(mapDetails.get("date"));
        this.deaths = Integer.parseInt(mapDetails.get("deaths"));
        this.recovered = Integer.parseInt(mapDetails.get("recovered"));
        this.confirmed = Integer.parseInt(mapDetails.get("confirmed"));
        this.active = Integer.parseInt(mapDetails.get("active"));
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getRecovered() {
        return recovered;
    }

    public void setRecovered(int recovered) {
        this.recovered = recovered;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String toString() {
        return this.date.toString() + "," + this.country + ","
                + this.confirmed + "," + this.deaths + "," + this.recovered + "," + this.active;
    }

    private String country;
    private Date date;
    private int deaths;
    private int recovered;
    private int confirmed;
    private int active;
}
