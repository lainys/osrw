package ru.miet.orgact;

import java.util.Calendar;

public class Conference {
    private int code;
    private String name;
    private Calendar start;
    private Calendar finish;
    private String country;
    private String city;


    public Conference() {

    }

    public static void main(String[] args) {
        Conference con = new Conference();
        con.setStart(null);
        System.out.println(con.getStart());

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = Calendar.getInstance();
    }

    public Calendar getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = Calendar.getInstance();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String toString() {
        String result = name;
        return result;
    }
}
