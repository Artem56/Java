import javax.swing.*;
import java.awt.*;

/**
 * Created by Artem Solomatin on 02.03.17.
 * Xonix
 */
public class GamePanel extends JPanel implements Runnable {
    //FIELDS
    private static final int basicSize = 10;
    private static final int WIDTH = 80 * basicSize;
    private static final int HEIGHT = 70 * basicSize;
    private Color[] colors = {Color.blue, Color.red, Color.cyan, Color.yellow.darker(), Color.green, Color.magenta};//возможно добавлю рандомный цвет на каждый уровень

    //CONSTRUCTOR
    GamePanel(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        addKeyListener(new Listener());
    }

    //METHODS

    /**
     * description: includes main loop of the game and draws the background.
     */
    @Override
    public void run() {
        this.setBackground(Color.black);  //test
    }

    /**
     * description: updates the game logic.
     */
    public void gameUpdate(){

    }

    /**
     * description: draws the game.
     */
    public void gameDraw(){

    }
}
