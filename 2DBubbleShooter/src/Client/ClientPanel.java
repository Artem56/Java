package Client;

import Core.GamePanel;
import Utils.Constants;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Artem Solomatin on 01.05.17.
 * 2DBubbleShooter
 */
public class ClientPanel extends JPanel implements Runnable  {

    //FIELDS
    private final static int WIDTH = GamePanel.getWIDTH();
    private final static int HEIGHT = GamePanel.getHEIGHT();

    private String ip;
    private Socket sock;

    public static final Message message = new Message();
    private static BufferedImage image;

    private ObjectOutputStream outputStream;
    //private BufferedReader inputStream;
    private ObjectInputStream inputStream;


    //CONSTRUCTOR
    public ClientPanel(){
        setUpNetworking();

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();       //for work with the keyboard

        addKeyListener(new ClientListener());
        addMouseMotionListener(new ClientListener());
        addMouseListener(new ClientListener());

    }


    //METHODS
    private void setUpNetworking(){
        /*ip = JOptionPane.showInputDialog(null,
                "Введите IP для подключения к серверу.\nФормат: xxx.xxx.xxx.xxx");*/

        try {
            sock = new Socket("127.0.0.1", Constants.Port);
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
            //inputStream = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            inputStream  = new ObjectInputStream(sock.getInputStream());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, " Клиент не может установить входящий канал");
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g){
        System.out.println("after paint");
        g.drawImage(image, 0, 0, this);
    }

    /*private void gameDraw(){
        Graphics g2 = this.getGraphics();           //рисует настоящий игровой экран
        System.out.println("g2 != null  " + (g2 != null));
        if(image != null) {

            g2.drawImage(image, 0, 0, null);
        }
    }*/

    @Override
    public void run() {
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();

        Thread writerThread = new Thread(new ClientPanel.OutcomingWriter());
        writerThread.start();

        //gameDraw();
    }

    // ДЛЯ КАРТИНКИ
    public class IncomingReader implements Runnable{

        @Override
        public void run() {
            //System.out.println("Test in ClientIncomingReader  start");
            String base64String = null;
            byte[] resByteArray;
            try {
                while(true) {
                    //System.out.println("Test in ClientIncomingReader   " + (base64String!=null));
                    if ((base64String = (String) inputStream.readObject()) != null) {  //Здесь нужно принять base64
                        resByteArray = Base64.decode(base64String);   // декодируем полученную строку в массив байт
                        //System.out.println("image != null   " + (image != null) + "  resByteArray != null  " + (resByteArray != null));
                        image = ImageIO.read(new ByteArrayInputStream(resByteArray));
                    }
                    Thread.sleep(20);
                    //gameDraw();
                    //System.out.println("before repaint");
                    repaint();
                }

            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, " Клиент не может считать сообщение");
                e.printStackTrace();
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, " Клиент не смог уснуть");
                e.printStackTrace();
            }
        }
    }


    public class OutcomingWriter implements Runnable{

        @Override
        public void run() {
            //System.out.println("Test in ClientOutcomingWriter  start");
            try {
                while (true) {
                    //System.out.println("Test in ClientOutcomingWriter   " + (message!=null));
                    if (message.isFiring() || message.isUp() || message.isDown() || message.isLeft() || message.isRight()) {
                        outputStream.writeObject(message);
                    }
                    Thread.sleep(60);
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Клиенту не удалось отослать сообщение");
                e.printStackTrace();
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, " Клиент не смог уснуть");
                e.printStackTrace();
            }
        }
    }
}
