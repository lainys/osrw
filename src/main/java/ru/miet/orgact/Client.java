package ru.miet.orgact;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

    String ip;
    int port;
    private Socket socket;
    private BufferedReader inMessage;
    private PrintWriter outMessage;

    /*public Client() {
        connect("127.0.0.1", 9000);
    }*/

    public Client() {
        ip = "127.0.0.1";
        ip = "192.168.1.4";
        port = 9000;
    }

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
        connect();
    }

    public static void main(String[] args) {
        System.out.println(getConferences());
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
            journal.setIssn(parse_str.get(2));
            journal.setRussian(Boolean.parseBoolean(parse_str.get(3)));
            journal.setVak(Boolean.parseBoolean(parse_str.get(4)));
            journal.setRecenz(Boolean.parseBoolean(parse_str.get(5)));
            journal.setIsi(Boolean.parseBoolean(parse_str.get(6)));
            journal.setScopus(Boolean.parseBoolean(parse_str.get(7)));
            journal.setRinc(Boolean.parseBoolean(parse_str.get(8)));
            if (parse_str.get(9).equals(" None")) {
                journal.setImpact_factor(0);
            } else {
                journal.setImpact_factor(Double.parseDouble(parse_str.get(9)));
            }
            journal.setImpact_factor_JSR(Boolean.parseBoolean(parse_str.get(10)));
            journal.setLink(parse_str.get(11));
            journals.add(journal);
        }

        return journals;
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
            conference.setCountry(parse_str.get(2));
            conference.setCity(parse_str.get(3));

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

    public static String parseAnswerFromGet(String answer) {
        String pattern = "(\'(.*)\')";
        if (answer.matches(pattern)) {
            System.out.println(answer.matches(pattern));
        }
        return "";
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

    public static String getTopics() {
        Client client = new Client();
        client.sendMessage("{\'type\':\'get_info\',\'data\':{\'name\':\'Разделы\', \"fields\": [\"Наименование_раздела\"]}}");
        return client.getMessage();
    }

    private void connect() {
        try {
            socket = new Socket(ip, port);
            inMessage = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outMessage = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
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
            return inMessage.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "Не удалось получить ответ от сервера!";
        }

    }

    public void sendArticle(Article article) {

    }

}
