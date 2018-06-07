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

    public static String getIP() {
        return Client.ip;
    }


    public static String getPort() {
        return Integer.toString(Client.port);
    }

    public Client() {
    }

    public static void reset() {
        Client.ip = "127.0.0.1";
        //Client.ip = "192.168.1.4";
        Client.port = 9000;
    }


    public static void setSet(String ip, String port) {
        Client.ip = ip;
        Client.port = Integer.parseInt(port);
        System.out.println(ip + " " + port);
    }

    public static void main(String[] args) {
        String a = "И";
        byte[] b = a.getBytes();
        System.out.println(b);
    }


    public static ArrayList<Journal> getJournals() {
        Client client = new Client();
        client.sendMessage("{\'type\':\'get_info\',\'data\':{\'name\':\'Журналы\', \"fields\": [\"*\"]}}");

        String message = client.getMessage();

        String[] result = message.split("\\(|\\)");

        ArrayList<Journal> journals = new ArrayList<>();
        for (int i = 0; i < result.length - 1; i += 2) {
            String[] journal_str = result[i + 1].split(", ");

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
            journal.setName(parse_str.get(1).substring(1, parse_str.get(1).length() - 1));
            if (parse_str.get(2).compareTo("None") != 0) {

                journal.setIssn(parse_str.get(2).substring(1, parse_str.get(2).length() - 1));
            } else {

                journal.setIssn("");
            }
            journal.setRussian(Boolean.parseBoolean(parse_str.get(3)));
            journal.setVak(Boolean.parseBoolean(parse_str.get(4)));
            journal.setRecenz(Boolean.parseBoolean(parse_str.get(5)));
            journal.setIsi(Boolean.parseBoolean(parse_str.get(6)));
            journal.setScopus(Boolean.parseBoolean(parse_str.get(7)));
            journal.setRinc(Boolean.parseBoolean(parse_str.get(8)));
            if (parse_str.get(9).equals("None")) {
                journal.setImpact_factor(0);
            } else {
                String number = parse_str.get(9).substring(1, parse_str.get(9).length() - 1);
                journal.setImpact_factor(Double.parseDouble(number.replace(",", ".")));
            }
            journal.setImpact_factor_JSR(Boolean.parseBoolean(parse_str.get(10).substring(1, parse_str.get(10).length())));
            if (parse_str.get(11).compareTo("None") != 0) {

                journal.setLink(parse_str.get(11).substring(1, parse_str.get(11).length() - 1));
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
                        try {
                            p.add(j, p.get(j) + " , " + p.get(j + 1));
                            p.remove(j + 1);
                            p.remove(j + 1);
                        } catch (Exception e) {
                            int a = 0;
                        }
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
            String[] parse_authors = p.get(2).substring(1, p.get(2).length() - 1).split("[,;]");
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
            if (p.get(4).equals("None")) {
                article.setName("");
            } else {
                article.setName(p.get(4).substring(1, p.get(4).length() - 1));
            }

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
            if (p.get(6).equals("None")) {
                article.setNumber("");
            } else {
                article.setNumber(p.get(6).substring(1, p.get(6).length() - 1));
            }
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
            if (p.get(8).equals("None")) {
                article.setLink("");
            } else {
                article.setLink(p.get(8).substring(1, p.get(8).length() - 1));
            }

            // 9 - country
            if (p.get(9).equals("None")) {
                article.setCountry("");
            } else {
                article.setCountry(p.get(9).substring(1, p.get(9).length() - 1));
            }
            // 10 - city
            if (p.get(10).equals("None")) {
                article.setCity("");
            } else {
                article.setCity(p.get(10).substring(1, p.get(10).length() - 1));
            }
            // 11 - publishing house
            if (p.get(11).equals("None")) {
                article.setPublishingHouse("");
            } else {
                article.setPublishingHouse(p.get(11).substring(1, p.get(11).length() - 1));
            }
            // 12 - year
            if (p.get(12).equals("None")) {
                article.setYear(2018);
            } else {
                article.setYear(Integer.parseInt(p.get(12)));
            }
            // 13 - tom
            // 14 - pages
            if (p.get(14).equals("None")) {
                article.setPages("");
            } else {
                article.setPages(p.get(14).substring(1, p.get(14).length() - 1));
            }
            // 15 - isbn/issn

            // 16 - doi
            if (p.get(16).equals("None")) {
                article.setDoi("");
            } else {
                article.setDoi(p.get(16).substring(1, p.get(16).length() - 1));
            }


            if (article.getTypeJson().length() == 0) {
                if (p.get(11).compareTo("None") != 0) {
                    Book book = new Book();
                    if (p.get(13).equals("None")) {
                        book.setBookTom("");
                    } else {
                        book.setBookTom(p.get(13).substring(1, p.get(13).length() - 1));
                    }
                    book.setName(article.getName());
                    book.setLink(article.getLink());

                    if (p.get(15).equals("None")) {
                        book.setISBN("");
                    } else {
                        book.setISBN(p.get(15).substring(1, p.get(15).length() - 1));
                    }
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
            if (Boolean.parseBoolean(p.get(38).toLowerCase())) {
                article.setIEEE1("1");
            } else {
                article.setIEEE1("0");
            }
            // 39 - ieee_proceedings_ c zarybej

            if (Boolean.parseBoolean(p.get(39).toLowerCase())) {
                article.setIEEE2("1");
            } else {
                article.setIEEE2("0");
            }
            // 40 - ieee_proceedings c institutami ran

            if (Boolean.parseBoolean(p.get(40).toLowerCase())) {
                article.setIEEE3("1");
            } else {
                article.setIEEE3("0");
            }
            // 41 - ieee proceedings s drugimi institutame

            if (Boolean.parseBoolean(p.get(41).toLowerCase())) {
                article.setIEEE4("1");
            } else {
                article.setIEEE4("0");
            }
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

            byte[] b = inMessage.readLine().getBytes();

            //String answer = new String(b, "utf-8");
            ArrayList<Byte> byt = new ArrayList<>();
            for (int i = 0; i < b.length; i++) {
                byt.add(b[i]);
            }

            for (int i = 1; i < byt.size(); i++) {
                if (byt.get(i) == -48 && byt.get(i - 1) == 63) {
                    byt.remove(i - 1);
                    byt.add(i - 1, (byte) -104);
                }
                if (byt.get(i) == -48 && byt.get(i - 1) == -193) {
                    byt.remove(i - 1);
                    byt.add(i - 1, (byte) -104);
                }


                if (byt.get(i) == -56) {
                    byt.remove(i);
                    byt.add(i, (byte) -48);
                    byt.add(i + 1, (byte) -104);
                }
            }
            byte[] res = new byte[byt.size()];
            for (int i = 0; i < byt.size(); i++) {
                res[i] = byt.get(i);
            }
/*
            for (int i = 1; i < b.length; i++) {
                if (b[i] == -48 && b[i - 1] == 63) {
                    b[i - 1] = -104;
                }
                if (b[i] == -48 && b[i - 1] == -193) {
                    b[i - 1] = -104;
                }
                if (b[i] == 56) {
                    byte[] a = new byte[2];
                    a[0] = -48;
                    a[1] = -104;
                    b[i] = -48;
                }
            }*/
            String answer = new String(res, "utf-8");




            return answer.substring(0, answer.length() - 1);
        } catch (IOException e) {
            e.printStackTrace();
            return "Не удалось получить ответ от сервера!";
        }

    }

    public byte[] insert(byte[] b, int ind, byte[] a) {
        byte[] c = new byte[b.length + a.length - 1];
        for (int i = 0; i < ind; i++) {
            c[i] = b[i];
        }
        for (int i = ind; i < a.length; i++) {
            c[i] = a[i - ind];
        }
        ind += a.length;
        for (int i = ind; i < b.length + a.length - 1; i++) {

            c[i] = b[i - a.length + 1];
        }
        return c;
    }

}
