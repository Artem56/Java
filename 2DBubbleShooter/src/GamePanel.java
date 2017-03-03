import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Artem Solomatin on 08.02.17.
 * 2DBubbleShooter
 */
public class GamePanel extends JPanel implements Runnable {

    //FIELDS
    private final static int WIDTH = 600;
    private final static int HEIGHT = 600;
    private Thread thread;   //для запуска игры

    private BufferedImage image;
    public static Graphics2D g;
    private static boolean paused;

    //CONSTRUCTOR
    public GamePanel(){
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();       //for work with the keyboard
        addKeyListener(new Listener());
        addMouseMotionListener(new Listener());
        addMouseListener(new Listener());
    }

    //FUNCTIONS


    public static int getWIDTH() {
        return WIDTH;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public static boolean isPaused() {
        return paused;
    }

    public static void setPaused(){
        paused = !paused;
    }

    public void addNotify(){   // метод вызывается тогда, когда GamePanel добавляется в родительский компонент с помощью add() и заканчивает создаваться
        super.addNotify();

        if(thread == null){
            thread = new Thread(this);
        }
        thread.start();
    }

    @Override
    public void run(){
        GameLogic.setRunning(true);
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
        g = (Graphics2D) image.getGraphics();     //в переменную g мы записали ссылку на контекст отображения для изображения image
        g.setRenderingHint(                       //сглаживание
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );

        GameLogic.gameLoop();

        if (GameLogic.player.getLives() == 0) {
            g.setColor(new Color(100, 50, 200));     //game over
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 26));
            String s = "G A M E   O V E R";
            int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2 - 80);
            s = "Final Score: " + GameLogic.player.getScore();
            length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2 - 40);
            s = "Time: " + ((System.currentTimeMillis() - GameLogic.gameStartTime) / 1000) + "seconds";
            length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2);
        }else if (GameLogic.player.getLives() != 0){
            g.setColor(new Color(100, 50, 200));     //game over
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 26));
            String s = "Y O U   W I N";
            int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2 - 80);
            s = "Final Score: " + GameLogic.player.getScore();
            length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2 - 40);
            s = "Time: " + ((System.currentTimeMillis() - GameLogic.gameStartTime) / 1000) + "seconds";
            length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2);
        }

        gameDraw();

        GameLogic.saveResult();

        gameDraw();
    }

    public void gameUpdate(){
        if(paused) return;

        GameLogic.wave();

        GameLogic.mobileUpdate();

        Collisions.bulletEnemyCollision();

        Collisions.playerEnemyCollision();

        Collisions.playerPowerCollision();

        GameLogic.deadUpdate();

        GameLogic.slowDownUpdate();
    }

    public void gameRender() {
        //background
        if(GameLogic.slowDownTimer == 0) {
            g.setColor(new Color(0, 100, 255));
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }

        //boss background
        if(Levels.getWaveNumber() % 5 == 0){  //boss
            g.setColor(new Color(0, 100, 155));
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }

        //slow background
        if(GameLogic.slowDownTimer != 0){
            g.setColor(new Color(155, 155, 255, 64));
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }

        //бэкграунд слоями
        /*
        g.setColor(new Color(0, 100, 255));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if(slowDownTimer != 0){
            g.setColor(new Color(255, 255, 255, 64));
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }
        */

        //player
        GameLogic.player.draw(g);

        int i;
        //bullets
        for (i = 0; i < GameLogic.bullets.size(); i++) {
            GameLogic.bullets.get(i).draw(g);
        }

        //enemies
        for (i = 0; i < GameLogic.enemies.size(); i++) {
            GameLogic.enemies.get(i).draw(g);
        }

        //powerUp
        for (i = 0; i < GameLogic.powerUps.size(); i++) {
            GameLogic.powerUps.get(i).draw(g);
        }

        //explosions
        for (i = 0; i < GameLogic.explosions.size(); i++) {
            GameLogic.explosions.get(i).draw(g);
        }

        //texts
        for (i = 0; i < GameLogic.texts.size(); i++) {
            GameLogic.texts.get(i).draw(g);
        }

        //wave number
        String s = null;
        if(Levels.getWaveNumber() % 5 != 0) {
            s = "| W A V E   " + Levels.getWaveNumber() + "|";
        }else if(Levels.getWaveNumber() % 5 == 0){
            s = "| B O S S |";
        }
        //g.drawString(" " + slowDownTimer / 1000_000, 100, 50);
        g.setFont(new Font("Areal", Font.BOLD, 32));
        int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        int transparancy = (int) (Math.sin(Math.PI * GameLogic.waveStartTimerDiff / GameLogic.waveDelay) * 255);
        g.setColor(new Color(255, 255, 255, transparancy));
        g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2);

        //player lives
        for (i = 0; i < GameLogic.player.getLives(); i++) {
            g.setColor(Color.white);
            g.fillOval(10 + 30 * i, 20, GameLogic.player.getRadius() * 2, GameLogic.player.getRadius() * 2);
            g.setStroke(new BasicStroke(3));           //граница
            g.setColor(Color.gray);
            g.drawOval(10 + 30 * i, 20, 2 * GameLogic.player.getRadius(), 2 * GameLogic.player.getRadius());
        }

        //score
        g.setColor(Color.white);
        g.setFont(new Font("Areal", Font.PLAIN, 18));
        g.drawString("Score: " + GameLogic.player.getScore(), WIDTH - 180, 30);

        //power
        g.setColor(Color.yellow);
        if(GameLogic.player.getRequiredPower() <= 50) {
            g.fillRect(20, 40, GameLogic.player.getPower() * 10, 10);
            for (i = 0; i < GameLogic.player.getRequiredPower(); i++) {
                g.setStroke(new BasicStroke(2));
                g.setColor(Color.yellow.darker());
                g.drawRect(20 + 10 * i, 40, 10, 10);
                g.setStroke(new BasicStroke(1));
            }
        }else if(GameLogic.player.getRequiredPower() > 50 && GameLogic.player.getRequiredPower() <= 100){
            if(GameLogic.player.getPower() <= 50)
                g.fillRect(20, 40, GameLogic.player.getPower() * 10, 10);
            if(GameLogic.player.getPower() > 50) {
                g.fillRect(20, 40, 500, 10);
                g.fillRect(20, 55, (GameLogic.player.getPower() - 50) * 10, 10);
            }
            for (i = 0; i < 50; i++) {
                g.setStroke(new BasicStroke(2));
                g.setColor(Color.yellow.darker());
                g.drawRect(20 + 10 * i, 40, 10, 10);
                g.setStroke(new BasicStroke(1));
            }
            for (i = 0; i < GameLogic.player.getRequiredPower() - 50; i++) {
                g.setStroke(new BasicStroke(2));
                g.setColor(Color.yellow.darker());
                g.drawRect(20 + 10 * i, 55, 10, 10);
                g.setStroke(new BasicStroke(1));
            }
        }

        //slowdown meter
        if(GameLogic.slowDownTimer != 0){
            g.setColor(Color.white);
            g.drawRect(20, 60, 100, 10);
            g.fillRect(20, 60,
                    (int)(100 - 100 * GameLogic.slowDownTimerDiff / GameLogic.slowDownLength), 10);
        }

        //paused
        if(paused) {
            String sPause = "Game is paused";
            int lengthPause = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
            g.setColor(Color.white);
            g.setFont(new Font("Areal", Font.PLAIN, 28));
            g.drawString(sPause, (WIDTH - length) / 2, 100);
        }
    }

    public void gameDraw(){
        Graphics g2 = this.getGraphics();           //рисует настоящий игровой экран
        g2.drawImage(image, 0, 0, null);
    }
}
