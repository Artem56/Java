package Core;

import Client.ClientPanel;
import Server.Server;

import javax.swing.*;


/**
 * Created by Artem Solomatin on 08.02.17.
 * 2DBubbleShooter
 *
 * Class starts the game thread and displays a game window on the screen
 */

//попробовать использовать время не в наносекундах
    //speed должен зависить от fps, если вдруг я захочу его изменить. Или наоборот
    //? заставить врагов отталкиваться друг от друга
    //!!! можно сделать режим дуэли. Плюшки будут появляться в рандомных местах, немного врагов
    //!!! плохая система замедления, проходить по всем врагам это долго
    //!!! после каждого уровня бонус к очкам за время прохождения
    //при стрельбе мышкой снаряды сливаются
    //тип оружия добавить в enum вместо магических цифр
    //можно сделать замедление плюшек, нужно ли?


public class Game {
    public static GamePanel panel = new GamePanel();
    private static Thread panelThread;   //для запуска игры
    public static Server server = new Server();
    private static ClientPanel client;

    public static void main (String[] args){
        JFrame window = new JFrame("Bubble Shooter");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(panel);


        if (panelThread == null) {
            panelThread = new Thread(panel);
        }
        panelThread.start();
/*
        while(true) {
            String mode = JOptionPane.showInputDialog(null, "Launch server(s) or client(c)?");
            if (mode.equals("s")) {
            /*if (panelThread == null) {
                panelThread = new Thread(panel);
            }

                panelThread = new Thread(panel);
                panelThread.start();
                //server = new Server();               //просто временно, чтоб куда-то отправлять изображение
                Thread serverThread = new Thread(server);
                serverThread.start();

                break;
            } else if (mode.equals("c")) {
                client = new ClientPanel();
                Thread clientThread = new Thread(client);
                clientThread.start();

                break;
            }
        }*/

        window.pack();
        window.setResizable(false);
        window.setVisible(true);
    }
}
