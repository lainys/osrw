package ru.miet.orgact;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private BufferedReader inMessage;
    private PrintWriter outMessage;

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
        Client client = new Client("127.0.0.1", 9000);
        client.sendMessage("ТЕСТ!");
        client.getMessage();
    }

    public void sendMessage(String message) {
        System.out.println("Отправка сообщения " + message);
        outMessage.print(message + '\0');
        outMessage.flush();
    }

    public void getMessage() {
        try {
            System.out.println("Приём сообщения ");
            System.out.println("Принято сообщение " + inMessage.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendArticle(Article article) {

    }

}
