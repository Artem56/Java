import javax.swing.*;

/**
 * Created by Artem Solomatin on 05.03.17.
 * Xonix
 */
public class Game {
    public static GamePanel panel;
    public static Thread thread;
    public static void main(String[] args){
        JFrame window = new JFrame("XONIX");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new GamePanel();

        if(thread == null) {
            thread = new Thread(panel);
        }

        window.setContentPane(panel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        thread.start();
        window.setVisible(true);

    }
}
