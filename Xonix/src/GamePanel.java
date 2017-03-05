import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Artem Solomatin on 05.03.17.
 * Xonix
 */
public class GamePanel extends JPanel implements Runnable {
    //FIELDS
    private static final int basicSize = 10;
    private static final int WIDTH = 80 * basicSize;
    private static final int HEIGHT = 65 * basicSize;
    private static final int INFO_HEIGHT = 5 * basicSize;
    private Color[] colors = {Color.blue, Color.red, Color.cyan, Color.yellow.darker(), Color.green, Color.magenta};//возможно добавлю рандомный цвет на каждый уровень
    private Graphics2D g;
    private BufferedImage image;     //холст
    private Thread thread;

    //CONSTRUCTOR
    GamePanel(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        addKeyListener(new Listener());
    }

    //METHODS
    public static int getBasicSize() {
        return basicSize;
    }

    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static int getInfoHeight() {
        return INFO_HEIGHT;
    }

    public Color[] getColors() {
        return colors;
    }

    /**
     * description: includes main loop of the game and draws the background.
     */
    @Override
    public void run() {
        GameLogic.setRunning(true);
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
        g = (Graphics2D) image.getGraphics();

        GameLogic.gameLoop();

        gameDraw();
    }

    /**
     * description: updates the game logic.
     */
    public void gameUpdate(){
        if(GameLogic.isPaused()){
            return;
        }
        GameLogic.mobileUpdate();



    }

    /**
     * description: draw all elements of the game.
     */
    public void gameRender(){
        //draw main background
        g.setColor(new Color(80, 10, 200));
        g.fillRect(0, INFO_HEIGHT, WIDTH, HEIGHT);

        //draw information panel
        g.setColor(new Color(30, 10, 50));
        g.fillRect(0, 0, WIDTH, INFO_HEIGHT);

        //draw score
        g.setColor(Color.white);
        g.setFont(new Font("Areal", Font.PLAIN, 20));
        g.drawString("Score: " + GameLogic.player.getScore(), WIDTH - basicSize * 18, basicSize * 3);

        //draw lives
        int i;
        for (i = 0; i < GameLogic.player.getLives(); i++) {
            g.setColor(Color.white);
            g.fillOval(basicSize + 3 * i * basicSize, 2 * basicSize,
                    (int)(GameLogic.player.getRadius() * 2), (int)(GameLogic.player.getRadius() * 2));
            g.setStroke(new BasicStroke(3));           //граница
            g.setColor(Color.gray);
            g.drawOval(basicSize + 3 * i * basicSize, 2 * basicSize,
                    (int)(GameLogic.player.getRadius() * 2), (int)(GameLogic.player.getRadius() * 2));
        }

        //draw level
        g.setColor(Color.white);
        g.setFont(new Font("Areal", Font.PLAIN, 20));
        g.drawString("Score: " + GameLogic.player.getScore(), WIDTH - basicSize * 18, basicSize * 3);

        //draw player
        GameLogic.player.draw(g);
    }

    /**
     * description: draws the game in the main scene.
     */
    public void gameDraw(){
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
    }
}
