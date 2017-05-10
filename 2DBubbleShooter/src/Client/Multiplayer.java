/*package Client;

import Core.GameLogic;
import GameObjects.Player;
import Utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Artem Solomatin on 02.05.17.
 * 2DBubbleShooter
 *//*
public class Multiplayer implements Runnable {
    //FIELDS
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private String ip;
    private Socket sock;

    //public static Message message;
    public static Player player;


    //CONSTRUCTOR
    public Multiplayer(){
        String mode = JOptionPane.showInputDialog(null, "Launch server(s) or client(c)?");
        while(true) {
            if (mode.equals("s")) {  //SERVER
                setUpasServer();



                break;
            } else if (mode.equals("c")) {  //CLIENT
                setUpasClient();



                break;
            } else {
                mode = JOptionPane.showInputDialog(null, "Введите c или s");
            }
        }
    }

    //METHODS
    /**
     * Два метода просто откроют стримы
     *//*
    private void setUpasClient(){
        ip = JOptionPane.showInputDialog(null,
                "Введите IP для подключения к серверу.\nФормат: xxx.xxx.xxx.xxx");

        try {
            sock = new Socket(ip, Constants.Port);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Не удалось создать сокет с ip " + ip + " на порту " + Constants.Port);
            e.printStackTrace();
        }

        try {                                //сюда передавать message
            outputStream = new ObjectOutputStream(sock.getOutputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, " Клиент не может установить исходящий канал");
            e.printStackTrace();
        }

        try {                                 //получать image
            inputStream = new ObjectInputStream(sock.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, " Клиент не может установить входящий канал");
            e.printStackTrace();
        }
    }

    private void setUpasServer(){
        try(ServerSocket serverSock = new ServerSocket(Constants.Port)) {
            //ЗДЕСЬ ДОЛЖЕН БЫТЬ БЕСКОНЕЧНЫЙ ЦИКЛ, ЕСЛИ ОЖИДАЕТСЯ БОЛЬШЕ ОДНОГО ПОДКЛЮЧЕНИЯ
            Socket clientSocket = serverSock.accept();

            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());


        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Не удалось установить соединение с клиентом");
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("test1");
            Thread inputThread = new Thread(new IncomingReader());
            inputThread.start();

            Thread outputThread = new Thread(new OutcomingWriter());
            outputThread.start();
    }


    private class IncomingReader implements Runnable{

        @Override
        public void run() {
            //Message mes;
            System.out.println("test2");
            if (GameLogic.players.size() == 2) {
                Player pl = GameLogic.players.get(1);
            }
            Player obj;
            try {
                System.out.println("test3");
                while (true) {
                    while ((obj = (Player) inputStream.readObject()) != null) {
                        GameLogic.players.remove(1);
                        GameLogic.players.add(obj);
                        System.out.println(GameLogic.players.get(1).isFiring());
                        System.out.println("test4");
                        Thread.sleep(30);
                    /*pl.setFiring(player.isFiring());
                    pl.setLeft(player.isLeft());
                    pl.setRight(player.isRight());
                    pl.setUp(player.isUp());
                    pl.setDown(player.isDown());*//*
                    }
                }
                } catch(IOException e){
                    JOptionPane.showMessageDialog(null, " Клиент не может считать сообщение");
                    e.printStackTrace();
                } catch(ClassNotFoundException e){
                    JOptionPane.showMessageDialog(null, "Клиенту передано сообщение, не содержащее действий");
                    e.printStackTrace();
                } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private class OutcomingWriter implements Runnable{

        @Override
        public void run() {
            player = new Player(Color.black);
            try {
                while(true) {
                    outputStream.writeObject(player);
                    Thread.sleep(30);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Клиенту не удалось отослать сообщение");
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            player = null;
        }
    }
}
*/