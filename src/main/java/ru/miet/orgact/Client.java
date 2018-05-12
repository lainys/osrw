package ru.miet.orgact;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

    private Socket socket;
    private BufferedReader inMessage;
    private PrintWriter outMessage;

    /*public Client() {
        connect("127.0.0.1", 9000);
    }*/

    public Client() {
        connect("127.0.0.1", 9000);
    }

    public Client(String ip, int port) {
        connect(ip, port);
    }

    private void connect(String ip, int port) {
        try {
            socket = new Socket(ip, port);
            inMessage = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outMessage = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        getEmployees();
    }

    public void sendMessage(String message) {
        System.out.println("Отправка сообщения " + message);
        outMessage.print(message + '\0');
        outMessage.flush();
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

    public static String getJournals() {
        Client client = new Client();
        client.sendMessage("{\'type\':\'get_info\',\'data\':{\'name\':\'Журналы\', \"fields\": [\"*\"]}}");
        return client.getMessage();
    }

    public static String getConference() {
        Client client = new Client();
        client.sendMessage("{\'type\':\'get_info\',\'data\':{\'name\':\'Конференции\', \"fields\": [\"*\"]}}");
        return client.getMessage();
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
