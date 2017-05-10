package Server;

import Client.Message;
import Core.GameLogic;
import GameObjects.Player;
import Utils.Constants;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Artem Solomatin on 30.04.17.
 * 2DBubbleShooter
 */
public class Server implements Runnable {
    //FIELDS
    //private static ArrayList<PrintWriter> clientOutputStreams;   //для нескольких игроков (больше двух)
    private BufferedImage image;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    public static Graphics graphics;


    //METHODS
    private String getImage() {
        if(image == null){
            JOptionPane.showMessageDialog(null, "Попытка переконвертировать картинку, которая еще не загружена");
            throw new ExceptionInInitializerError();
        }

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream(10_000);

        try {
            ImageIO.write(image, "png", byteStream);
            byteStream.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Не удалось переконвертировать картинку в массив байт");
            e.printStackTrace();
        }

        String base64String = Base64.encode(byteStream.toByteArray());
        try {
            byteStream.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Не удалось закрыть канал конвертирования картинки в Base64");
            e.printStackTrace();
        }

        return base64String;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void run() {
        //clientOutputStreams = new ArrayList<>(2);

        try(ServerSocket serverSock = new ServerSocket(Constants.Port)) {
            //ЗДЕСЬ ДОЛЖЕН БЫТЬ БЕСКОНЕЧНЫЙ ЦИКЛ, ЕСЛИ ОЖИДАЕТСЯ БОЛЬШЕ ОДНОГО ПОДКЛЮЧЕНИЯ
            //System.out.println("Test1 server.run");
            Socket clientSocket = serverSock.accept();
            //System.out.println("Test2 server.run");

            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());


            Thread inputThread = new Thread(new IncomingReader());
            inputThread.start();

            Thread outputThread = new Thread(new OutcomingWriter());
            outputThread.start();


        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Не удалось установить соединение с клиентом");
            e.printStackTrace();
        }
    }


    private class IncomingReader implements Runnable{
        //  FIELDS
        Player player = GameLogic.players.get(1);

        //METHODS
        @Override
        public void run() {
            //System.out.println("Test in ServerIncomingReader  start");
            Message message = null;
            try {
                while(true) {
                    //System.out.println("Test in ServerIncomingReader   " + (message!=null));
                    if ((message = (Message) inputStream.readObject()) != null) {
                        player.setFiring(message.isFiring());
                        player.setLeft(message.isLeft());
                        player.setRight(message.isRight());
                        player.setUp(message.isUp());
                        player.setDown(message.isDown());
                        System.out.println("сообщение наконец получено");
                    }
                    Thread.sleep(100);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Сервер не может считать сообщение");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Серверу передано сообщение, не содержащее объекта типа Message или сообщение повреждено");
                e.printStackTrace();
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Сервер не смог уснуть");
                e.printStackTrace();
            }
        }
    }

    private class OutcomingWriter implements Runnable{
        //METHODS
        @Override
        public void run() {
            //System.out.println("Test in ServerOutcomingWriter  start");
            try {
                while(true) {
                    //System.out.println("Test in ServerOutcomingWriter   " + (getImage()!=null));
                    outputStream.writeObject(getImage());

                    Thread.sleep(80);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Серверу не удалось отослать сообщение");
                e.printStackTrace();
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, "Сервер не смог уснуть");
                e.printStackTrace();
            }
        }
    }
}
