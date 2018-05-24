package ru.miet.orgact;

public class Conference {
    private int code;
    private String name;
    private String start;
    private String finish;
    private String country;
    private String city;
    private String pages;

    public Conference() {
        code = -1;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
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
