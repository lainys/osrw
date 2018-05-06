package ru.miet.orgact;

import java.util.*;

public class Article {

    public ArrayList<String> authors;
    private String name;
    private Integer year;
    private String type;
    private String country;
    private String city;
    private String publicHouse;
    private HashMap<String, Integer> citations;
    private String topic;
    private ArrayList<String> directions;

    public Article(String name, ArrayList<String> authors, Integer year, String type, String country, String city, String publicHouse, HashMap<String, Integer> citations, String topic, ArrayList<String> directions) {
        this.name = name;
        this.authors = authors;
        this.year = year;
        this.type = type;
        this.country = country;
        this.city = city;
        this.publicHouse = publicHouse;
        this.citations = citations;
        this.topic = topic;
        this.directions = directions;
    }

    public Article() {
        name = "";
        authors = new ArrayList<>();
        year = Calendar.getInstance().get(Calendar.YEAR);
        type = "";
        country = "";
        city = "";
        publicHouse = "";
        citations = new HashMap<>();
        topic = "";
        directions = new ArrayList<>();
    }

    public static void main(String[] args) {
        Article article = new Article("dd", null, 1, "ssfd", "dfsfd", "dfs", "dfssdfs", null, "fgdg", null);
        article.toJSON();

    }

    public String toJSON() {
        String result = "{";
        result += "\"name\":\"" + name + "\",";

        if (authors != null) {
            result += "\"authors\":{";
            for (int i = 0; i < authors.size(); i++) {
                result += "\"" + i + "\":\"" + authors.get(i) + "\",";
            }
            result += "},";
        } else {
            result += "\"authors\":\"null\",";
        }

        result += "\"year\":" + year + ",";

        result += "\"type\":\"" + type + "\",";

        result += "\"country\":\"" + country + "\",";

        result += "\"city\":\"" + city + "\",";

        result += "\"publicHouse\":\"" + publicHouse + "\",";

        if (citations != null) {
            result += "\"citations\":{";
            Iterator<Map.Entry<String, Integer>> set = citations.entrySet().iterator();
            while (set.hasNext()) {
                Map.Entry<String, Integer> next = set.next();

                result += "\"" + next.getKey() + "\":\"" + next.getValue() + "\",";
            }

            result += "},";
        } else {
            result += "\"citations\":\"null\",";
        }

        result += "\"topic\":\"" + topic + "\",";

        if (directions != null) {
            result += "\"directions\":{";
            for (int i = 0; i < directions.size(); i++) {
                result += "\"" + i + "\":\"" + directions.get(i) + "\",";
            }
            result += "}";
        } else {
            result += "\"directions\":\"null\",";
        }


        result += "}";

        return result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addAuthors(String... authors) {
        for (String author : authors) {
            this.authors.add(author);
        }
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

    public void setPublicHouse(String publicHouse) {
        this.publicHouse = publicHouse;
    }

    public void setCitations(HashMap<String, Integer> map) {
        citations.putAll(map);

    }

    public void setDirections(String... directions) {
        for (String direct : directions) {
            this.directions.add(direct);
        }
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }


}
