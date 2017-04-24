package Core;

import GameObjects.*;
import Utils.Saver;
import Utils.Levels;

import javax.swing.*;
import java.util.ArrayList;


/**
 * Created by Artem Solomatin on 16.02.17.
 * 2DBubbleShooter
 */

/**
 * the class contains the game loop and is responsible for the creation of new waves, updating the game screen, cutting out bonuses from dead enemies, and starting a saving at the end
 */
public abstract class GameLogic {

    //FIELDS
    private static long waveStartTimer;      //ns
    protected static long waveStartTimerDiff;  //ms
    private static boolean waveStart;
    public static final int waveDelay = 4000;
    public static long slowDownTimer;
    public static long slowDownTimerDiff;
    public static final int slowDownLength = 5000;  //ms
    public static long gameStartTime;      //ms

    private static final int FPS = 30;

    private static boolean running;

    public static Player player;
    public static ArrayList<Bullet> bullets = new ArrayList<>();
    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<PowerUp> powerUps = new ArrayList<>();
    public static ArrayList<Explosion> explosions = new ArrayList<>();
    public static ArrayList<Text> texts = new ArrayList<>();
    public static ArrayList<Saver> profiles = new ArrayList<>();


    //METHODS
    public static void setRunning(boolean b){
        running = b;
    }

    public static void gameLoop(){
        gameStartTime = System.currentTimeMillis();

        long startTime;                //ns
        long loopTime;                  //ms
        long waitTime;                 //ms
        long targetTime = 1000/FPS;    //ms

        player = new Player();
        //gameStartTime = System.currentTimeMillis();

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
        }

    }

    public static void saveResult() {
        //сохранение
        profiles = Saver.deserData();

        String name = JOptionPane.showInputDialog(null, "Введите ваше имя\n" +
                "для сохранения результата");
        Saver profile = new Saver(name, player.getScore());
        if(profile.getName() != null && profile.getScore() != 0) {

            //проверка на одинаковые имена
            if (profiles.isEmpty()) {
                profiles.add(profile);
            }

            for (int i = 0; i < profiles.size(); i++) {
                Saver pr = profiles.get(i);
                if (profile.getName().equals(pr.getName())) {
                    if (profile.getScore() > pr.getScore()) {
                        pr.setScore(profile.getScore());
                        i = profiles.size();
                    }
                }else{
                    profiles.add(profile);
                    i = profiles.size();
                }
            }
        }

        profiles.sort((Saver o1, Saver o2) -> o2.getScore() - o1.getScore());

        Saver.serData(profiles);

        Saver.draw(GamePanel.g);
    }

    public static void wave(){
        if(waveStartTimer == 0 && enemies.isEmpty()){
            Levels.addWaveNumber();
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
        if(waveStart && enemies.isEmpty()){
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
                }else if(random < 0.06){
                    powerUps.add(new PowerUp(3, dead.getX(), dead.getY()));
                }else if(random < 0.11){
                    powerUps.add(new PowerUp(5, dead.getX(), dead.getY()));
                }else if(random < 0.21){
                    powerUps.add(new PowerUp(4, dead.getX(), dead.getY()));
                }else if(random < 0.33){
                    powerUps.add(new PowerUp(2, dead.getX(), dead.getY()));
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
            for (Enemy enemy : enemies) {
                enemy.setSlow(false);
            }
        }
    }
}
