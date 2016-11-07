/**
 * Created by Artem Solomatin on 06.11.16.
 */

/*ComboBox кому приватное сообщение
перед сообщением написано что оно приватное
 */
import java.io.*;
import java.net.*;
import java.util.*;


public class Server {
    public static ArrayList<PrintWriter> clientOutputStreams;

    public class ClientHandler implements Runnable {
        BufferedReader reader;
        Socket sock;

        public ClientHandler(Socket clientSocket) {
            try {
                sock = clientSocket;
                InputStreamReader isReader = new InputStreamReader(sock.getInputStream());
                reader = new BufferedReader(isReader);

            } catch (Exception ex) { ex.printStackTrace(); }
        }

        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    tellEveryone(message);
                }
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    public static void main(String[] args) {
        new Server().go();
    }

    public void go() {
        clientOutputStreams = new ArrayList<>();
        try {
            ServerSocket serverSock = new ServerSocket(Constants.Port);
            while(true) {
                Socket clientSocket = serverSock.accept();
                PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
                clientOutputStreams.add(writer);

                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();
                System.out.println("got a connection");
            }
        } catch (Exception ex) { ex.printStackTrace(); }
    }

    public static void tellEveryone(String message) {
        for(PrintWriter tmp : clientOutputStreams) {
            try {
                tmp.println(message);
                tmp.flush();
            } catch (Exception ex) { ex.printStackTrace(); }
        }
    }
}
