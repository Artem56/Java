import javax.swing.*;

/**
 * Created by Artem Solomatin on 03.03.17.
 * Xonix
 */
public class Game {
    public static void main(String[] args){
        JFrame window = new JFrame("XONIX");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel panel = new GamePanel();

        Thread thread = new Thread(panel);   //launch the game
        thread.start();

        window.setContentPane(panel);

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}
