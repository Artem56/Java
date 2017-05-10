package Core;

import Utils.Levels;
import Utils.Listener;
import GameObjects.Collisions;
import GameObjects.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Artem Solomatin on 08.02.17.
 * 2DBubbleShooter
 *
 * The class is responsible for drawing the game screen
 */
public class GamePanel extends JPanel implements Runnable {

    //FIELDS
    private final static int WIDTH = 600;
    private final static int HEIGHT = 600;
    //public static Thread thread;   //для запуска игры

    private BufferedImage image;
    //private ArrayList<BufferedImage> images = new ArrayList<>();
    public static Graphics2D g;
    private static boolean paused;

    //CONSTRUCTOR
    /**
     * Class constructor
     */
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

    public static void setPaused(){
        paused = !paused;
    }

    /**
     * The flow displayed on the screen
     */
    @Override
    public void run(){
        GameLogic.setRunning(true);
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
        //images.add(image);
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

        for(Player player : GameLogic.players) {
            if (player.getLives() == 0) {
                g.setColor(new Color(100, 50, 200));     //game over
                g.fillRect(0, 0, WIDTH, HEIGHT);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.PLAIN, 26));
                String s = "G A M E   O V E R";
                int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2 - 80);
                //s = "Final Score: " + player.getScore();
                s = "Player " + (GameLogic.players.indexOf(player)==0?2:1) + " won";
                length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2 - 40);
                s = "Time: " + ((System.currentTimeMillis() - GameLogic.gameStartTime) / 1000) + "seconds";
                length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2);
            } /*else if (player.getLives() != 0) {
                g.setColor(new Color(100, 50, 200));
                g.fillRect(0, 0, WIDTH, HEIGHT);
                g.setColor(Color.WHITE);
                g.setFont(new Font("Arial", Font.PLAIN, 26));
                String s = "Y O U   W I N";
                int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2 - 80);
                s = "Final Score: " + player.getScore();
                length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2 - 40);
                s = "Time: " + ((System.currentTimeMillis() - GameLogic.gameStartTime) / 1000) + "seconds";
                length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
                g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2);
            }*/
        }

        //gameDraw();
        repaint();

        GameLogic.saveResult();

        //gameDraw();
        repaint();
    }

    /**
     * Update the state of all objects in the game
     */
    public void gameUpdate(){
        if(paused) return;

        GameLogic.wave();

        GameLogic.mobileUpdate();

        Collisions.bulletEnemyCollision();

        Collisions.bulletPlayerCollision();

        Collisions.playerEnemyCollision();

        Collisions.playerPowerCollision();

        GameLogic.deadUpdate();

        GameLogic.slowDownUpdate();
    }

    /**
     * Update all the graphic elements in the game
     */
    public void gameRender() {
        //background
        if(GameLogic.slowDownTimer == 0) {
            g.setColor(new Color(0, 100, 255));
            g.fillRect(0, 0, WIDTH, HEIGHT);


            //boss background
            if (Levels.getWaveNumber() % 5 == 0) {  //boss
                g.setColor(new Color(0, 100, 155));
                g.fillRect(0, 0, WIDTH, HEIGHT);
            }
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
        int i;

        //player
        for (i = 0; i < GameLogic.players.size(); i++) {
            GameLogic.players.get(i).draw(g);
        }

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
            g.setColor(Color.white);
            g.setFont(new Font("Areal", Font.PLAIN, 28));
            g.drawString(sPause, (WIDTH - length) / 2, 100);
        }


        //player lives
        for (i = 0; i < GameLogic.players.get(0).getLives(); i++) {
            g.setColor(GameLogic.players.get(0).getColor());
            g.fillOval(10 + 30 * i, 20, GameLogic.players.get(0).getRadius() * 2, GameLogic.players.get(0).getRadius() * 2);
            g.setStroke(new BasicStroke(3));           //граница
            g.setColor(Color.gray);
            g.drawOval(10 + 30 * i, 20, 2 * GameLogic.players.get(0).getRadius(), 2 * GameLogic.players.get(0).getRadius());
        }
        for (i = 0; i < GameLogic.players.get(1).getLives(); i++) {
            g.setColor(GameLogic.players.get(1).getColor());
            g.fillOval(10 + 30 * i, 520, GameLogic.players.get(1).getRadius() * 2, GameLogic.players.get(1).getRadius() * 2);
            g.setStroke(new BasicStroke(3));           //граница
            g.setColor(Color.gray);
            g.drawOval(10 + 30 * i, 520, 2 * GameLogic.players.get(1).getRadius(), 2 * GameLogic.players.get(1).getRadius());
        }

        //score
        g.setColor(Color.white);
        g.setFont(new Font("Areal", Font.PLAIN, 18));
        g.drawString("Score1: " + GameLogic.players.get(0).getScore(), WIDTH - 180, 30);
        g.drawString("Score2: " + GameLogic.players.get(1).getScore(), WIDTH - 180, 530);

        //power
        g.setColor(Color.yellow);
        for(int j = 0;j < GameLogic.players.size();j++) {
            if (GameLogic.players.get(j).getRequiredPower() <= 50) {
                g.fillRect(20, 40 + 500 * j, GameLogic.players.get(j).getPower() * 10, 10);
                for (i = 0; i < GameLogic.players.get(j).getRequiredPower(); i++) {
                    g.setStroke(new BasicStroke(2));
                    g.setColor(Color.yellow.darker());
                    g.drawRect(20 + 10 * i, 40 + 500 * j, 10, 10);
                    g.setStroke(new BasicStroke(1));
                }
            } else if (GameLogic.players.get(j).getRequiredPower() > 50 && GameLogic.players.get(j).getRequiredPower() <= 100) {
                if (GameLogic.players.get(j).getPower() <= 50)
                    g.fillRect(20, 40 + 500 * j, GameLogic.players.get(j).getPower() * 10, 10);
                if (GameLogic.players.get(j).getPower() > 50) {
                    g.fillRect(20, 40 + 500 * j, 500, 10);
                    g.fillRect(20, 55 + 500 * j, (GameLogic.players.get(j).getPower() - 50) * 10, 10);
                }
                for (i = 0; i < 50; i++) {
                    g.setStroke(new BasicStroke(2));
                    g.setColor(Color.yellow.darker());
                    g.drawRect(20 + 10 * i, 40 + 500 * j, 10, 10);
                    g.setStroke(new BasicStroke(1));
                }
                for (i = 0; i < GameLogic.players.get(j).getRequiredPower() - 50; i++) {
                    g.setStroke(new BasicStroke(2));
                    g.setColor(Color.yellow.darker());
                    g.drawRect(20 + 10 * i, 55 + 500 * j, 10, 10);
                    g.setStroke(new BasicStroke(1));
                }
            }
        }
    }

    /**
     * The rendering on the screen all the graphical elements in the game
     *
     * @param g the graphics context to use for printing
     */
    @Override
    public void paint(Graphics g){
        //Game.server.setImage(image, g2);
        g.drawImage(image, 0, 0, null);
        Game.server.setImage(image);
    }
    /*public void gameDraw(){

        Graphics g2 = this.getGraphics();           //рисует настоящий игровой экран
        Game.server.setImage(image, g2);
        g2.drawImage(image, 0, 0, null);
    }*/
}
