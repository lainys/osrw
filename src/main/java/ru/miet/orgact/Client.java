package ru.miet.orgact;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Client {


    private static String ip;
    private static int port;
    private Socket socket;
    private BufferedReader inMessage;
    private PrintWriter outMessage;

    /*public Client() {
        connect("127.0.0.1", 9000);
    }*/

    public Client() {
        Client.ip = "127.0.0.1";
        //Client.ip = "192.168.1.4";
        Client.port = 9000;
    }

    public Client(String ip, int port) {
        Client.ip = ip;
        Client.port = port;
        connect();
    }

    public static void main(String[] args) {
        System.out.println(getJournals());
    }


    public static ArrayList<Journal> getJournals() {
        Client client = new Client();
        client.sendMessage("{\'type\':\'get_info\',\'data\':{\'name\':\'Журналы\', \"fields\": [\"*\"]}}");

        String message = client.getMessage();

        String[] result = message.split("\\(|\\)");

        ArrayList<Journal> journals = new ArrayList<>();
        for (int i = 0; i < result.length - 1; i += 2) {
            String[] journal_str = result[i + 1].split(",");

            ArrayList<String> parse_str = new ArrayList<>();
            for (int j = 0; j < journal_str.length; j++) {
                parse_str.add(journal_str[j]);
            }

            while (!parse_str.get(1).endsWith("'")) {
                parse_str.add(1, parse_str.get(1) + ',' + parse_str.get(2));
                parse_str.remove(2);
                parse_str.remove(2);

            }

            Journal journal = new Journal();
            journal.setCode(Integer.parseInt(parse_str.get(0)));
            journal.setName(parse_str.get(1).substring(2, parse_str.get(1).length() - 1));
            if (parse_str.get(2).compareTo(" None") != 0) {

                journal.setIssn(parse_str.get(2).substring(2, parse_str.get(2).length() - 1));
            } else {

                journal.setIssn("");
            }
            journal.setRussian(Boolean.parseBoolean(parse_str.get(3).substring(1, parse_str.get(3).length())));
            journal.setVak(Boolean.parseBoolean(parse_str.get(4).substring(1, parse_str.get(4).length())));
            journal.setRecenz(Boolean.parseBoolean(parse_str.get(5).substring(1, parse_str.get(5).length())));
            journal.setIsi(Boolean.parseBoolean(parse_str.get(6).substring(1, parse_str.get(6).length())));
            journal.setScopus(Boolean.parseBoolean(parse_str.get(7).substring(1, parse_str.get(7).length())));
            journal.setRinc(Boolean.parseBoolean(parse_str.get(8).substring(1, parse_str.get(8).length())));
            if (parse_str.get(9).equals(" None")) {
                journal.setImpact_factor(0);
            } else {
                journal.setImpact_factor(Double.parseDouble(parse_str.get(9)));
            }
            journal.setImpact_factor_JSR(Boolean.parseBoolean(parse_str.get(10).substring(1, parse_str.get(10).length())));
            if (parse_str.get(11).compareTo(" None") != 0) {

                journal.setLink(parse_str.get(11).substring(2, parse_str.get(11).length() - 1));
            } else {
                journal.setLink("");
            }
            journals.add(journal);
        }

        return journals;
    }

    public static ArrayList<Article> getArticles() {

        ArrayList<String> topics = getTopics();
        ArrayList<Conference> conferences = getConferences();
        ArrayList<Journal> journals = getJournals();
        ArrayList<String> ep = getEP();
        Client client = new Client();
        client.sendMessage("{\'type\':\'get_info\',\'data\':{\'name\':\'СписокПубликаций\', \"fields\": [\"*\"]}}");

        String message = client.getMessage();

        String[] result = message.split("\\),|\\)]");

        ArrayList<Article> articles = new ArrayList<>();
        for (int i = 0; i < result.length; i += 1) {
            String[] journal_str = result[i].substring(2, result[i].length()).split(",");

            ArrayList<String> p = new ArrayList<>();
            for (int j = 0; j < journal_str.length; j++) {
                p.add(journal_str[j].trim());
            }

            for (int j = 0; j < p.size(); j++) {
                if (p.get(j).startsWith("'")) {
                    while (!p.get(j).endsWith("'")) {
                        p.add(j, p.get(j) + " , " + p.get(j + 1));
                        p.remove(j + 1);
                        p.remove(j + 1);
                    }
                }
            }

            Article article = new Article();
            // 0 - code
            article.setCode(Integer.parseInt(p.get(0)));
            // 1 - check
            // 2 - authors
            ArrayList<String> authors = new ArrayList<>();
            ArrayList<String> positions = new ArrayList<>();
            String[] parse_authors = p.get(2).substring(1, p.get(2).length() - 1).split(",");
            for (int j = 0; j < parse_authors.length; j++) {
                authors.add(parse_authors[j].trim());
                for (int k = 0; k < ep.size(); k += 2) {
                    if (ep.get(k).startsWith(parse_authors[j].trim())) {
                        positions.add(ep.get(k + 1) + " МИЭТ");
                        break;
                    }
                }
                if (authors.size() != positions.size()) {
                    positions.add("Другое");
                }
            }
            article.setAuthors(authors);
            article.setPositions(positions);

            // 3 - topic
            if (p.get(3).compareTo("None") != 0) {
                article.setTopic(topics.get(Integer.parseInt(p.get(3)) - 1));
            }

            // 4 - name
            article.setName(p.get(4).substring(1, p.get(4).length() - 1));

            //journal
            // 5 - id_journal
            if (p.get(5).compareTo("None") != 0) {

                article.setType("journal");

                for (int k = 0; k < journals.size(); k++) {
                    if (journals.get(k).getCode() == Integer.parseInt(p.get(5))) {
                        Gson gson = new Gson();
                        article.setTypeJson(gson.toJson(journals.get(k)));
                        break;
                    }
                }
            }
            // 6 - number of journal

            //conference
            // 7 - id_conference
            if (p.get(7).compareTo("None") != 0) {

                article.setType("conference");

                for (int k = 0; k < conferences.size(); k++) {
                    if (conferences.get(k).getCode() == Integer.parseInt(p.get(7))) {
                        Gson gson = new Gson();
                        article.setTypeJson(gson.toJson(conferences.get(k)));
                        break;
                    }
                }
            }
            // 8 - link
            article.setLink(p.get(8).substring(1, p.get(8).length() - 1));
            // 9 - country
            article.setCountry(p.get(9).substring(1, p.get(9).length() - 1));
            // 10 - city
            article.setCity(p.get(10).substring(1, p.get(10).length() - 1));
            // 11 - publishing house
            article.setPublishingHouse(p.get(11).substring(1, p.get(11).length() - 1));
            // 12 - year
            article.setYear(Integer.parseInt(p.get(12)));
            // 13 - tom

            // 14 - pages

            // 15 - isbn/issn

            // 16 - doi

            if (article.getTypeJson().length() == 0) {
                if (p.get(11).compareTo("None") != 0) {
                    Book book = new Book();
                    book.setName(article.getName());
                    book.setLink(article.getLink());
                    book.setPages(p.get(14).substring(1, p.get(14).length() - 1));
                    book.setISBN(p.get(12));
                    book.setPublishingHouse(article.getPublishingHouse());

                    Gson gson = new Gson();
                    article.setType("book");
                    article.setTypeJson(gson.toJson(book));
                } else {
                    Other other = new Other();
                    other.setLink(article.getLink());
                    other.setName(article.getName());

                    Gson gson = new Gson();
                    article.setType("other");
                    article.setTypeJson(gson.toJson(other));
                }
            }

            // 17 - eith
            // 18 - tit
            // 19 - mnioz
            // 20 - sn
            // 21 - gn
            ObservableList<Integer> list = FXCollections.observableArrayList();
            if (Boolean.parseBoolean(p.get(17).toLowerCase())) {
                list.add(0);
            }
            if (Boolean.parseBoolean(p.get(18).toLowerCase())) {
                list.add(1);
            }
            if (Boolean.parseBoolean(p.get(19).toLowerCase())) {
                list.add(2);
            }
            if (Boolean.parseBoolean(p.get(20).toLowerCase())) {
                list.add(3);
            }
            if (Boolean.parseBoolean(p.get(21).toLowerCase())) {
                list.add(4);
            }
            article.setDirections(list);
            // 22 - wos
            // 23 - cit wos
            HashMap<String, Integer> map = new HashMap<>();
            if (Boolean.parseBoolean(p.get(22).toLowerCase())) {
                int countcit = Integer.parseInt(p.get(23));
                map.put("WOS", countcit);
            }

            // 24 - scopus
            // 25 - cit scopus
            if (Boolean.parseBoolean(p.get(24).toLowerCase())) {
                int countcit = Integer.parseInt(p.get(25));
                map.put("Scopus", countcit);
            }

            // 26 - rinz
            // 27 - cit rinz
            if (Boolean.parseBoolean(p.get(26).toLowerCase())) {
                int countcit = Integer.parseInt(p.get(27));
                map.put("РИНЦ", countcit);
            }
            // 28 - nuclear rinz
            // 29 - cit nrinz
            if (Boolean.parseBoolean(p.get(28).toLowerCase())) {
                int countcit = Integer.parseInt(p.get(29));
                map.put("ЯдроРИНЦ", countcit);
            }

            // 30 - google scholar
            // 31 - cit google

            if (Boolean.parseBoolean(p.get(30).toLowerCase())) {
                int countcit = Integer.parseInt(p.get(31));
                map.put("GoogleScholar", countcit);
            }
            // 32 - dimensions
            // 33 - cit dimensions
            if (Boolean.parseBoolean(p.get(32).toLowerCase())) {
                int countcit = Integer.parseInt(p.get(33));
                map.put("Dimensions", countcit);
            }
            // 34 - microsoft academic
            // 35 - cit microsoft academic
            if (Boolean.parseBoolean(p.get(34).toLowerCase())) {
                int countcit = Integer.parseInt(p.get(35));
                map.put("MicrosoftAcademic", countcit);
            }
            // 36 - erih_plus
            // 37 - cit erih_plus
            if (Boolean.parseBoolean(p.get(36).toLowerCase())) {
                if (p.get(37).startsWith("'")) {
                    int countcit = Integer.parseInt(p.get(37).substring(1, p.get(37).length() - 1));
                    map.put("ERIH_PLUS", countcit);
                } else {
                    int countcit = Integer.parseInt(p.get(37));
                    map.put("ERIH_PLUS", countcit);
                }
            }
            article.setCitations(map);
            // 38 - ieee_proceedings
            // 39 - ieee_proceedings_ c zarybej
            // 40 - ieee_proceedings c institutami ran
            // 41 - ieee proceedings s drugimi institutame

            articles.add(article);

        }

        return articles;
    }

    public static ArrayList<Conference> getConferences() {
        Client client = new Client();
        client.sendMessage("{\'type\':\'get_info\',\'data\':{\'name\':\'Конференции\', \"fields\": [\"Код,Наименование,Страна,Город\"]}}");
        String message = client.getMessage();


        client.sendMessage("{\'type\':\'get_info\',\'data\':{\'name\':\'Конференции\', \"fields\": [\"Дата_начала\"]}}");
        String start = client.getMessage();

        client.sendMessage("{\'type\':\'get_info\',\'data\':{\'name\':\'Конференции\', \"fields\": [\"Дата_окончания\"]}}");
        String finish = client.getMessage();

        String[] result = message.split("\\(|\\)");
        String[] starts = start.split("datetime.datetime\\(|\\)");
        String[] finishs = finish.split("datetime.datetime\\(|\\)");

        ArrayList<Conference> conferences = new ArrayList<>();
        for (int i = 0; i < result.length - 1; i += 2) {


            String[] conference_str = result[i + 1].split(",");
            ArrayList<String> parse_str = new ArrayList<>();
            for (int j = 0; j < conference_str.length; j++) {
                parse_str.add(conference_str[j]);
            }

            while (!parse_str.get(1).endsWith("'")) {
                parse_str.add(1, parse_str.get(1) + ',' + parse_str.get(2));
                parse_str.remove(2);
                parse_str.remove(2);

            }

            Conference conference = new Conference();
            conference.setCode(Integer.parseInt(parse_str.get(0)));
            conference.setName(parse_str.get(1).substring(2, parse_str.get(1).length() - 1));
            conference.setCountry(parse_str.get(2).substring(2, parse_str.get(2).length() - 1));
            conference.setCity(parse_str.get(3).substring(2, parse_str.get(3).length() - 1));

            //city country
            for (int j = 1; j < starts.length; j += 3) {
                String[] starts_split = starts[j].split(",");
                String[] finish_split = finishs[j].split(",");

                conference.setStart(starts_split[0] + "." + starts_split[1] + "." + starts_split[2]);
                conference.setFinish(finish_split[0] + "." + finish_split[1] + "." + finish_split[2]);

            }


            conferences.add(conference);
        }

        return conferences;
    }

    public static ArrayList<String> getEmployees() {
        Client client = new Client();
        client.sendMessage("{\'type\':\'get_info\',\'data\':{\'name\':\'Сотрудники\', \"fields\": [\"ФИО_сотрудника\"]}}");
        String message = client.getMessage();

        String[] result = message.split("\\[\\('|'");

        ArrayList<String> employees = new ArrayList<>();
        for (int i = 0; i < result.length - 1; i += 2) {
            employees.add(result[i + 1]);
        }

        return employees;
    }


    public static ArrayList<String> getEP() {
        Client client = new Client();
        client.sendMessage("{\'type\':\'get_info\',\'data\':{\'name\':\'Сотрудники\', \"fields\": [\"ФИО_сотрудника,Должность\"]}}");
        String message = client.getMessage();

        String[] result = message.split("\\[\\('|'");

        ArrayList<String> employees = new ArrayList<>();
        for (int i = 0; i < result.length - 1; i += 2) {
            employees.add(result[i + 1]);
        }

        return employees;
    }

    public static ArrayList<String> getTopics() {
        Client client = new Client();
        client.sendMessage("{\'type\':\'get_info\',\'data\':{\'name\':\'Разделы\', \"fields\": [\"Номер_раздела,Наименование_раздела\"]}}");
        String message = client.getMessage();

        String[] result = message.split("\\[\\('|'");

        ArrayList<String> employees = new ArrayList<>();
        for (int i = 0; i < result.length - 1; i += 2) {
            employees.add(result[i + 1]);
        }

        return employees;
    }

    private void connect() {
        try {
            socket = new Socket(Client.ip, Client.port);
            inMessage = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outMessage = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        connect();
        System.out.println("Отправка сообщения " + message);
        outMessage.print(message + '\0');
        outMessage.flush();
    }

    public String getMessage() {
        try {
            System.out.println("Приём сообщения ");
            //System.out.println("Принято сообщение " + inMessage.readLine());
            String answer = inMessage.readLine();
            return answer.substring(0, answer.length() - 1);
        } catch (IOException e) {
            e.printStackTrace();
            return "Не удалось получить ответ от сервера!";
        }

    }

}
