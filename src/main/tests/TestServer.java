import org.junit.Test;
import ru.miet.orgact.Client;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

class ClientThreadTest extends Thread {
    String result = "";
    String query;
    int answer;

    public ClientThreadTest(String result, String query) {
        this.result = result;
        this.query = query;
        answer = 0;
    }

    @Override
    public void run()    //Этот метод будет выполнен в побочном потоке
    {
        try {
            Client client = new Client();
            client.sendMessage(query);
            String message = "";
            message = client.getMessage();
            if (message.contains(result)) {
                answer = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            answer = -1;
        }
    }

    public int getAnswer() {
        return answer;
    }
}

public class TestServer {


    //нагрузочный тест
    @Test
    public void testServer() throws Exception {

        int countTrue = 0;
        int countFalse = 0;
        int countError = 0;
        int maxClients = 100;
        String query = "{\'type\':\'get_info\',\'data\':{\'name\':\'Сотрудники\', \"fields\": [\"ФИО_сотрудника\"]}}";
        Client startClient = new Client();
        startClient.sendMessage(query);
        String result = startClient.getMessage();

        ArrayList<ClientThreadTest> clients = new ArrayList<>();

        //инициализация
        for (int i = 0; i < maxClients; i++) {
            clients.add(new ClientThreadTest(result, query));
        }

        //запуск
        for (int i = 0; i < maxClients; i++) {
            clients.get(i).start();
        }

        Thread.currentThread().join(1000);

        //проверка
        for (int i = 0; i < maxClients; i++) {
            if (clients.get(i).getAnswer() == 1) {
                countTrue++;
            }
            if (clients.get(i).getAnswer() == -1) {
                countError++;
            }
            if (clients.get(i).getAnswer() == 0) {
                countFalse++;
            }
        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Получено корректных ответов от сервера " + countTrue + " из " + maxClients);
        assertEquals(maxClients, countTrue);
    }

    //стрессовый тест
    @Test
    public void testServer2() throws Exception {

        int countTrue = 0;
        int maxClients = 50;
        String query = "{\'type\':\'get_info\',\'data\':{\'name\':\'Сотрудники\', \"fields\": [\"ФИО_сотрудника\"]}}";
        Client startClient = new Client();
        startClient.sendMessage(query);
        String result = startClient.getMessage();

        int max = 5000;

        for (int i = 0; i < max; i++) {
            countTrue += generate(1, query, result);
            Thread.sleep(1000);
        }
        Thread.sleep(5000);
        System.out.println(countTrue);
        assertEquals(max, countTrue);
    }

    public int generate(int maxClients, String query, String result) {

        int countTrue = 0;
        ArrayList<ClientThreadTest> clients = new ArrayList<>();

        //инициализация
        for (int i = 0; i < maxClients; i++) {
            clients.add(new ClientThreadTest(result, query));
        }

        //запуск
        for (int i = 0; i < maxClients; i++) {
            clients.get(i).start();
        }
        try {
            Thread.currentThread().sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        //проверка
        for (int i = 0; i < maxClients; i++) {
            if (clients.get(i).getAnswer() == 1) {
                countTrue++;
            }
        }
        return countTrue;

    }

}