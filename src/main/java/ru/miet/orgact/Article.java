package ru.miet.orgact;

import javafx.collections.ObservableList;

import java.util.*;

public class Article {

    private int code;
    private ArrayList<String> authors;
    private ArrayList<String> positions;
    private String name;
    private Integer year;
    private String type;
    private String country;
    private String city;
    private String publishingHouse;
    private HashMap<String, Integer> citations;
    private String topic;
    private ArrayList<Integer> directions;
    private String typeJson;
    private String link;
    private String pages;
    private String number;
    private String doi;
    private String IEEE1;
    private String IEEE2;
    private String IEEE3;
    private String IEEE4;

    public Article() {
        code = -1;
        name = "";
        authors = new ArrayList<>();
        positions = new ArrayList<>();
        year = Calendar.getInstance().get(Calendar.YEAR);
        type = "";
        country = "";
        city = "";
        publishingHouse = "";
        citations = new HashMap<>();
        topic = "";
        directions = new ArrayList<>();
        typeJson = "";
        pages = "";
        doi = "";
        number = "";
        link = "";
    }

    public Article(String name, ArrayList<String> authors, ArrayList<String> positions, Integer year, String type, String country, String city, String publishingHouse, HashMap<String, Integer> citations, String topic, ArrayList<Integer> directions) {
        this.name = name;
        this.authors = authors;
        this.positions = positions;
        this.year = year;
        this.type = type;
        this.country = country;
        this.city = city;
        this.publishingHouse = publishingHouse;
        this.citations = citations;
        this.topic = topic;
        this.directions = directions;
        this.typeJson = "";
        this.link = "";
        this.IEEE1 = "";
        this.IEEE2 = "";
        this.IEEE3 = "";
        this.IEEE4 = "";
    }

    public String getIEEE1() {
        return IEEE1;
    }

    public void setIEEE1(String IEEE1) {
        this.IEEE1 = IEEE1;
    }

    public String getIEEE2() {
        return IEEE2;
    }

    public void setIEEE2(String IEEE2) {
        this.IEEE2 = IEEE2;
    }

    public String getIEEE3() {
        return IEEE3;
    }

    public void setIEEE3(String IEEE3) {
        this.IEEE3 = IEEE3;
    }

    public String getIEEE4() {
        return IEEE4;
    }

    public void setIEEE4(String IEEE4) {
        this.IEEE4 = IEEE4;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public static void main(String[] args) {
        Article article = new Article("dd", null, null, 1, "ssfd", "dfsfd", "dfs", "dfssdfs", null, "fgdg", null);
        article.toJSON();

    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String toJSON() {
        String result = "{";

        result += "\"code\":\"" + code + "\",";
        result += "\"name\":\"" + name + "\",";
        result += "\"pages\":\"" + pages + "\",";
        result += "\"doi\":\"" + doi + "\",";
        result += "\"number\":\"" + number + "\",";
        result += "\"link\":\"" + link + "\",";
        result += "\"IEEE1\":\"" + IEEE1 + "\",";
        result += "\"IEEE2\":\"" + IEEE2 + "\",";
        result += "\"IEEE3\":\"" + IEEE3 + "\",";
        result += "\"IEEE4\":\"" + IEEE4 + "\",";

        /*if (authors != null) {
            result += "\"authors\":{";
            for (int i = 0; i < authors.size(); i++) {

                result += "\"" + i + "\":{";

                result += "\"fio\":\"" + authors.get(i) + "\",";
                result += "\"position\":\"" + positions.get(i) + "\",";

                result += "},";
            }
            result += "},";
        } else {
            result += "\"authors\":\"null\",";
        }*/

        if (authors != null) {
            result += "\"authors\":[";
            for (int i = 0; i < authors.size(); i++) {
                result += "\"" + authors.get(i) + "\",";
            }
            result += "],";
        } else {
            result += "\"authors\":\"null\",";
        }

        if (positions != null) {
            result += "\"positions\":[";
            for (int i = 0; i < positions.size(); i++) {
                result += "\"" + positions.get(i) + "\",";
            }
            result += "],";
        } else {
            result += "\"positions\":\"null\",";
        }


        result += "\"year\":\"" + year + "\",";

        result += "\"type\":\"" + type + "\",";

        result += "\"typeJson\":" + typeJson + ",";

        //result += typeJson + ",";

        result += "\"country\":\"" + country + "\",";

        result += "\"city\":\"" + city + "\",";

        result += "\"publishingHouse\":\"" + publishingHouse + "\",";

        if (citations != null) {
            result += "\"citations\":{";
            Iterator<Map.Entry<String, Integer>> set = citations.entrySet().iterator();
            while (set.hasNext()) {
                Map.Entry<String, Integer> next = set.next();

                result += "\"" + next.getKey() + "\":{\"have\":\"1\",\"citations\":\"" + next.getValue() + "\"},";
            }

            result += "},";
        } else {
            result += "\"citations\":\"null\",";
        }

        result += "\"topic\":\"" + topic + "\",";

        if (directions != null) {
            result += "\"directions\":[";
            for (int i = 0; i < directions.size(); i++) {
                result += "\"" + directions.get(i) + "\",";
            }
            result += "]";
        } else {
            result += "\"directions\":\"null\",";
        }


        result += "}";

        return result;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthors(ArrayList<String> authors) {
        this.authors.addAll(authors);
    }

    public void setPositions(ArrayList<String> positions) {
        this.positions.addAll(positions);
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public void setCitations(HashMap<String, Integer> map) {
        citations.putAll(map);
    }

    public ArrayList<String> getPositions() {
        return positions;
    }

    public String getType() {
        return type;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public HashMap<String, Integer> getCitations() {
        return citations;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public ArrayList<Integer> getDirections() {
        return directions;
    }

    public void setDirections(ObservableList<Integer> temp) {
        ArrayList<Integer> list = new ArrayList<>();
        list.addAll(temp);
        directions.clear();
        for (int i = 0; i < 5; i++) {
            if (list.contains(i)) {
                list.remove((Integer) i);
                directions.add(1);
            } else {
                directions.add(0);
            }
        }
    }

    public String getTypeJson() {
        return typeJson;
    }

    public void setTypeJson(String type) {
        typeJson = type;
    }

    public String getCity() {

        return city;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getCountry() {
        return country;
    }
}
