
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Artem Solomatin on 08.02.17.
 * 2DBubbleShooter
 */
public class GamePanel extends JPanel implements Runnable, KeyListener {

    //FIELDS
    public final static int WIDTH = 600;
    public final static int HEIGHT = 600;
    private Thread thread;   //для запуска игры
    private static boolean running;

    private BufferedImage image;
    private Graphics2D g;
    private final int FPS = 30;
    private double averageFPS;

    private long waveStartTimer;      //ns
    private long waveStartTimerDiff;  //ms
    private boolean waveStart;
    private final int waveDelay = 3000;
    public static long slowDownTimer;
    private long slowDownTimerDiff;
    private final int slowDownLength = 5000;  //ms
    private long gameStartTime;      //ms


    public static Player player;
    public static ArrayList<Bullet> bullets = new ArrayList<>();
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<PowerUp> powerUps = new ArrayList<>();
    public static ArrayList<Explosion> explosions = new ArrayList<>();
    public static ArrayList<Text> texts = new ArrayList<>();
    public static ArrayList<Saver> profiles = new ArrayList<>();
    private boolean paused;

    //CONSTRUCTOR
    public GamePanel(){
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();       //for work with the keyboard
    }

    //FUNCTIONS
    public static void setRunning(boolean b){
        running = b;
    }

    public void addNotify(){   // метод вызывается тогда, когда GamePanel добавляется в родительский компонент с помощью add()
        super.addNotify();

        if(thread == null){
            thread = new Thread(this);
        }
        thread.start();

        addKeyListener(this);
    }

    @Override
    public void run(){
        running = true;
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



        long startTime;                //ns
        long loopTime;                  //ms
        long waitTime;                 //ms
        long totalTime = 0;            //ms
        long targetTime = 1000/FPS;    //ms

        int frameCount = 0;
        final int maxFrameCount = 30;

        player = new Player();
        gameStartTime = System.currentTimeMillis();

        /*for(int i = 0;i < 200;i++){            //МОЖНО ИСПОЛЬЗОВАТЬ ДЛЯ БЕСКОНЕЧНОЙ ВОЛНЫ, ПРОСТО ВЫПУСКАТЬ ВРАГОВ С ЗАДЕРЖКОЙ
            enemies.add(new Enemy(1, 1));
        }*/
        //waves

        //GAME LOOP
        while(running){

            startTime = System.nanoTime();

            gameUpdate();        //update all of the game logic
            gameRender();        //draws the game on a back buffer
            gameDraw();          //put the buffer on the screen

            loopTime = (System.nanoTime() - startTime) / 1000_000;
            waitTime = (targetTime - loopTime);
            try{
                if(waitTime > 0) {     //время первого прохода огромное
                    Thread.sleep(waitTime);
                }
            } catch (InterruptedException e) {
                System.out.println("ERROR in loop, the thread can't sleep");
                e.printStackTrace();
            }

            totalTime += (System.nanoTime() - startTime)/1000_000;
            frameCount++;
            if(frameCount == maxFrameCount){
                averageFPS = 1000/(totalTime/frameCount);

                frameCount = 0;
                totalTime = 0;
            }
        }

        g.setColor(new Color(100, 50, 200));     //game over
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 26));
        String s = "G A M E   O V E R";
        int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2 - 80);
        s = "Final Score: " + player.getScore();
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2 - 40);
        s = "Time: " + ((System.currentTimeMillis() - gameStartTime) / 1000) + "seconds";
        length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, (WIDTH - length) / 2, HEIGHT / 2);

        gameDraw();

        //сохранение
        profiles = Saver.deserData();

        String name = JOptionPane.showInputDialog(null, "Введите ваше имя\n" +
                "для сохранения результата");
        Saver profile = new Saver(name, player.getScore());

        //проверка на одинаковые имена
        profiles.add(profile);
        for(int i = 0;i < profiles.size();i++){
            Saver pr = profiles.get(i);
            if (profile.getName().equals(pr.getName())) {
                if (profile.getScore() > pr.getScore()) {
                    profiles.remove(pr);
                    i = profiles.size();
                }else{
                    profiles.remove(profile);
                    i = profiles.size();
                }
            }
        }
        System.out.println(profiles.size());
        if(profiles.size() == 0){
            System.out.println("kek1");
            profiles.add(profile);
        }

        Collections.sort(GamePanel.profiles, new Comparator<Saver>() {
            public int compare(Saver o1, Saver o2) {
                return o2.getScore() - o1.getScore();
            }
        });

        Saver.serData(profiles);

        Saver.draw(g);
        gameDraw();
    }

    private void gameUpdate(){
        if(paused) return;
        //new wave
        if(waveStartTimer == 0 && enemies.size() == 0){
            Levels.waveNumber++;
            waveStartTimer = System.nanoTime();
        }else{     //если волна уже идет
            waveStartTimerDiff = (System.nanoTime() - waveStartTimer) / 1000_000;
            if(waveStartTimerDiff > waveDelay){
                waveStart = true;
                waveStartTimer = 0;
                waveStartTimerDiff = 0;
            }
        }

        //create wave
        if(waveStart && enemies.size() == 0){
            Levels.createNewWave();
        }

        player.update();

        //bullets update
        for(int i = 0; i < bullets.size(); i++) {
            boolean remove = bullets.get(i).update();
            if(remove) {
                bullets.remove(i);
                i--;
            }
        }

        //enemies update
        for(int i = 0;i < enemies.size();i++){
            enemies.get(i).update();
        }

        //powerUp update
        for(int i = 0;i < powerUps.size();i++){
            boolean remove = powerUps.get(i).update();
            if(remove){
                powerUps.remove(i);
                i--;
            }
        }

        //explosion update
        for(int i = 0;i < explosions.size();i++){
            boolean remove = explosions.get(i).update();
            if(remove){
                explosions.remove(i);
                i--;
            }
        }

        //text update
        for(int i = 0;i < texts.size();i++){
            boolean remove = texts.get(i).update();
            if(remove){
                texts.remove(i);
                i--;
            }
        }
        Collisions.bulletEnemyCollision();

        Collisions.playerEnemyCollision();

        Collisions.playerPowerCollision();

        //dead enemies
        for(int j = 0;j < enemies.size();j++){
            if(enemies.get(j).isDead()){
                Enemy dead = enemies.get(j);

                //power up
                double random = Math.random();
                if(random < 0.01){          //шанс 1 к 100, всего в 1 из 3 что-то выпадает
                    powerUps.add(new PowerUp(1, dead.getX(), dead.getY()));
                }else if(random < 0.1){
                    powerUps.add(new PowerUp(2, dead.getX(), dead.getY()));
                }else if(random < 0.02){
                    powerUps.add(new PowerUp(3, dead.getX(), dead.getY()));
                }else if(random < 0.15){
                    powerUps.add(new PowerUp(4, dead.getX(), dead.getY()));
                }else if(random < 0.05){
                    powerUps.add(new PowerUp(5, dead.getX(), dead.getY()));
                }

                player.addScore(dead.getCost());
                enemies.remove(j);
                j--;

                dead.explode();
                explosions.add(new Explosion(dead.getX(), dead.getY(), dead.getRadius(), dead.getCost() * 4 + 10));
            }
        }

        //is player dead?
        if(player.isDead()){
            running = false;
        }

        //slowdown update
        if(slowDownTimer != 0){
            slowDownTimerDiff = (System.nanoTime() - slowDownTimer) / 1000_000;     //ms
        }
        if(slowDownTimerDiff > slowDownLength){
            slowDownTimer = 0;
            for(int j = 0;j < enemies.size();j++){
                enemies.get(j).setSlow(false);
            }
        }
    }

    private void gameRender() {
        //background
        if(slowDownTimer == 0) {
            g.setColor(new Color(0, 100, 255));
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }

        //boss background
        if(Levels.waveNumber % 5 == 0){  //boss
            g.setColor(new Color(0, 100, 155));
            g.fillRect(0, 0, WIDTH, HEIGHT);
        }

        //slow background
        if(slowDownTimer != 0){
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
        player.draw(g);

        int i;
        //bullets
        for (i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(g);
        }

        //enemies
        for (i = 0; i < enemies.size(); i++) {
            enemies.get(i).draw(g);
        }

        //powerUp
        for (i = 0; i < powerUps.size(); i++) {
            powerUps.get(i).draw(g);
        }

        //explosions
        for (i = 0; i < explosions.size(); i++) {
            explosions.get(i).draw(g);
        }

        //texts
        for (i = 0; i < texts.size(); i++) {
            texts.get(i).draw(g);
        }

        //wave number
        String s = null;
        if(Levels.waveNumber % 5 != 0) {
            s = "| W A V E   " + Levels.waveNumber + "|";
        }else if(Levels.waveNumber % 5 == 0){
            s = "| B O S S |";
        }
        //g.drawString(" " + slowDownTimer / 1000_000, 100, 50);
        g.setFont(new Font("Areal", Font.BOLD, 32));
        int length = (int) g.getFontMetrics().getStringBounds(s, g).getWidth();
        int transparancy = (int) (Math.sin(Math.PI * waveStartTimerDiff / waveDelay) * 255);
        g.setColor(new Color(255, 255, 255, transparancy));
        g.drawString(s, WIDTH / 2 - length / 2, HEIGHT / 2);

        //player lives
        for (i = 0; i < player.getLives(); i++) {
            g.setColor(Color.white);
            g.fillOval(10 + 30 * i, 20, player.getRadius() * 3, player.getRadius() * 3);
            g.setStroke(new BasicStroke(3));           //граница
            g.setColor(Color.gray);
            g.drawOval(10 + 30 * i, 20, 3 * player.getRadius(), 3 * player.getRadius());
        }

        //score
        g.setColor(Color.white);
        g.setFont(new Font("Areal", Font.PLAIN, 18));
        g.drawString("Score: " + player.getScore(), WIDTH - 180, 30);

        //power
        g.setColor(Color.yellow);
        g.fillRect(20, 40, player.getPower() * 10, 10);
        for (i = 0; i < player.getRequiredPower(); i++) {
            g.setStroke(new BasicStroke(2));
            g.setColor(Color.yellow.darker());
            g.drawRect(20 + 10 * i, 40, 10, 10);
            g.setStroke(new BasicStroke(1));

        }

        //slowdown meter
        if(slowDownTimer != 0){
            g.setColor(Color.white);
            g.drawRect(20, 60, 100, 10);
            g.fillRect(20, 60,
                    (int)(100 - 100 * slowDownTimerDiff / slowDownLength), 10);
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

    private void gameDraw(){
        Graphics g2 = this.getGraphics();           //рисует настоящий игровой экран
        g2.drawImage(image, 0, 0, null);
    }

    public void setPaused(){
        paused = !paused;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT : player.setLeft(true);
                break;
            case KeyEvent.VK_RIGHT : player.setRight(true);
                break;
            case KeyEvent.VK_UP : player.setUp(true);
                break;
            case KeyEvent.VK_DOWN : player.setDown(true);
                break;
            case KeyEvent.VK_SPACE : player.setFiring(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT : player.setLeft(false);
                break;
            case KeyEvent.VK_RIGHT : player.setRight(false);
                break;
            case KeyEvent.VK_UP : player.setUp(false);
                break;
            case KeyEvent.VK_DOWN : player.setDown(false);
                break;
            case KeyEvent.VK_SPACE : player.setFiring(false);
                break;
            case KeyEvent.VK_Q : setPaused();
                break;
        }
    }
}
