/**
 * Created by Artem Solomatin on 06.11.16.
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import javafx.scene.control.*;


public class Client extends Application {
    BufferedReader reader;
    PrintWriter writer;
    Socket sock;

    public static final int windowX = 800;
    public static final int windowY = 600;
    public static TextArea text;
    public static TextArea chat;
    private Label chatLabel;
    private Label textLabel;
    private Button send;

    public String name = "NO SERVER CONNECTION";
    public String ip;
    private Scanner scan;

    public void start(Stage primaryStage) {
        setUpNetworking();

        Pane root = new Pane();

        chat = new javafx.scene.control.TextArea();
        chat.setTranslateX(150);
        chat.setTranslateY(50);
        chat.setPrefSize(500, 250);

        chatLabel = new Label("Chat");
        chatLabel.setTranslateX(30);
        chatLabel.setTranslateY(100);
        chatLabel.setPrefSize(100, 100);
        chatLabel.setFont(new Font("Verdana", 22));

        text = new javafx.scene.control.TextArea();
        text.setTranslateX(150);
        text.setTranslateY(350);
        text.setPrefSize(300, 200);

        textLabel = new Label("    My\nmessage");
        textLabel.setTranslateX(30);
        textLabel.setTranslateY(400);
        textLabel.setPrefSize(100, 100);
        textLabel.setFont(new Font("Verdana", 22));

        send = new javafx.scene.control.Button("Send");
        send.setTranslateX(500);
        send.setTranslateY(400);
        send.setPrefSize(100, 60);
        send.setOnAction(event -> {
            try {
                writer.println(name + " : " + text.getText());
                writer.flush();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            text.setText("");
            text.requestFocus();
        });

        root.getChildren().addAll(chat, chatLabel, textLabel, text, send);
        Scene scene = new Scene(root, windowX, windowY);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Chat of " + name);
        primaryStage.show();

        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
    }

    private void setUpNetworking() {
        try {
            scan = new Scanner(System.in);
            System.out.println("Введите IP для подключения к серверу.");
            System.out.println("Формат: xxx.xxx.xxx.xxx");

            ip = scan.nextLine();

            sock = new Socket(ip, Constants.Port);
            InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
            reader = new BufferedReader(streamReader);
            writer = new PrintWriter(sock.getOutputStream());
            System.out.println("Networking established");

            System.out.println("Введите свой ник:");
            name = scan.nextLine();

            writer.println("SERVER>" + name + " has joined us\n");
            writer.flush();
        }
        catch(IOException ex) {
            System.out.println("Problems with server.\nPlease try later or try to reconnect.");
        }
    }

    class IncomingReader implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    chat.appendText(message + "\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
