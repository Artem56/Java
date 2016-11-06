package main;

/**
 * Created by Artem Solomatin on 06.11.16.
 */

/*
1.Изменить все catch
2.synchronized() прочесть и понять
!!!3.Добавить GUI
4.Thread() дочитать
 */

import java.util.Scanner;
import server.Server;
import client.Client;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println(">Запустить программу в режиме сервера или клиента? (S(erver) / C(lient))");
        while (true) {
            char answer = Character.toLowerCase(in.nextLine().charAt(0));
            if (answer == 's') {
                new Server();
                break;
            } else if (answer == 'c') {
                new Client();
                break;
            } else {
                System.out.println("Некорректный ввод. Повторите.");
            }
        }
    }

}
