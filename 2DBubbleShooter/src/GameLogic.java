import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Artem Solomatin on 16.02.17.
 * 2DBubbleShooter
 */
public abstract class GameLogic {

    //FIELDS
    public static long waveStartTimer;      //ns
    public static long waveStartTimerDiff;  //ms
    private static boolean waveStart;
    public static final int waveDelay = 3000;
    public static long slowDownTimer;
    public static long slowDownTimerDiff;
    public static final int slowDownLength = 5000;  //ms
    public static long gameStartTime;      //ms

    private static final int FPS = 30;

    public static boolean running;

    public static Player player;
    public static ArrayList<Bullet> bullets = new ArrayList<>();
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<PowerUp> powerUps = new ArrayList<>();
    public static ArrayList<Explosion> explosions = new ArrayList<>();
    public static ArrayList<Text> texts = new ArrayList<>();
    public static ArrayList<Saver> profiles = new ArrayList<>();
    private boolean paused;

    //CONSTRUCTOR

    //METHODS


    public static void setRunning(boolean b){
        running = b;
    }

    public static void gameLoop(){

        long startTime;                //ns
        long loopTime;                  //ms
        long waitTime;                 //ms
        long totalTime = 0;            //ms
        long targetTime = 1000/FPS;    //ms

        int frameCount = 0;
        final int maxFrameCount = 30;

        player = new Player();
        gameStartTime = System.currentTimeMillis();

        while(running){

            startTime = System.nanoTime();

            Game.panel.gameUpdate();        //update all of the game logic
            Game.panel.gameRender();        //draws the game on a back buffer
            Game.panel.gameDraw();          //put the buffer on the screen

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
        }

    }

    public static void saveResult(){
        //сохранение
        profiles = Saver.deserData();

        String name = JOptionPane.showInputDialog(null, "Введите ваше имя\n" +
                "для сохранения результата");
        Saver profile = new Saver(name, player.getScore());

        //проверка на одинаковые имена

        //System.out.println("1 " + profiles.size());
        for(int i = 0;i < profiles.size();i++){
            Saver pr = profiles.get(i);
            if (profile.getName().equals(pr.getName())) {
                if (profile.getScore() > pr.getScore()) {
                    //System.out.println("2 " + profiles.size());
                    profiles.remove(pr);
                    i = profiles.size();
                }else if (profile.getScore() <= pr.getScore()){
                    profiles.remove(profile);
                    i = profiles.size();
                    //System.out.println("3 " + profiles.size());
                    System.out.println(" " + profile.getName() + " " + pr.getName());
                }
            }
        }
        profiles.add(profile);
        //System.out.println("4 " + profiles.size());
        if(profiles.size() == 0){
            profiles.add(profile);
        }

        Collections.sort(profiles, new Comparator<Saver>() {
            public int compare(Saver o1, Saver o2) {
                return o2.getScore() - o1.getScore();
            }
        });

        Saver.serData(profiles);

        Saver.draw(GamePanel.g);
    }

    public static void wave(){
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
    }

    public static void mobileUpdate(){
        //player update
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
    }

    public static void deadUpdate(){
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
    }

    public static void slowDownUpdate(){
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
}
