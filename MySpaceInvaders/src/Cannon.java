import org.omg.SendingContext.RunTime;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Created by artem on 17.09.16.
 */
public class Cannon{
    public final static int WIDTH = 26;
    public final static int HEIGHT = 16;
    protected final static int DX = 5, STAND = 18*GUI.MULT, BORDER = 10;    //СМЕЩЕНИЕ
    private static int x, y, direction;
    public static int shotNumber;
    public static int hitNumber;

    public  void pause(){
        try{
            Runtime.getRuntime().exec("shell -c " + "/home/artem/IdeaProjects/MySpaceInvaders/control.txt");//ОТКРЫТЬ ФАЙЛИК
        }
        catch (IOException e){

        }
    }

    public Cannon(){
        x = 10;
        y = GUI.WINDOW_Y - HEIGHT - STAND;
    }

    void move(){
        if (direction == KeyEvent.VK_LEFT && x > BORDER) x -= DX;
        if (direction == KeyEvent.VK_RIGHT && x < GUI.WINDOW_X - WIDTH - BORDER) x += DX;
    }

    void setDirection(int direction) {
        this.direction = direction; }

    void shot(){
        shotNumber++;
        Game.ray.start(x, y);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return WIDTH; }

    static void paint(Graphics g){
        if (!Game.bang.isBang()){
            g.setColor(Color.GREEN);
            g.fillRect(x, y + HEIGHT/2, WIDTH, HEIGHT/2);
            g.fillRect(x + 2, y + HEIGHT/2 - 2, WIDTH - 4, HEIGHT/2);
            g.fillRect(x + BORDER, y + 2, WIDTH - 20, HEIGHT/2);
            g.fillRect(x + BORDER + 2, y, 2, 2);
        }
    }
}

class Explosion{ // fire when hit the cannon
    int x, y, countCycles, timeOfCycle, view;
    final int TIME_OF_CYCLE = 3;

    final int[][][] FIRE = {
            {       {0,0,0,0,1,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,1,0,0,0,0,1,0,0,1,0,0,0,0},
                    {0,0,0,0,1,0,1,0,0,1,0,1,0,0,0}, {0,0,1,0,0,0,0,0,0,0,0,0,1,0,0}, {0,0,0,0,0,1,0,1,1,0,0,0,0,0,0},
                    {0,0,0,1,1,1,1,1,1,1,1,0,0,0,0}, {0,0,0,1,1,1,1,1,1,1,1,0,0,0,0}, {1,1,1,1,1,1,1,1,1,1,1,1,0,0,0}},
            };

    void enable(){
        x = Game.cannon.getX();
        y = Game.cannon.getY();
        countCycles = 5;
        timeOfCycle = TIME_OF_CYCLE;
        view = 0;
    }

    void show(){
        if (timeOfCycle == 0) {
            timeOfCycle = TIME_OF_CYCLE;
            if (countCycles > 0 && !Game.gameOver){
                countCycles--;
            }
        } else {
            timeOfCycle--;
        }
    }

    boolean isBang(){
        return countCycles != 0;
    }

    void paintExplode(Graphics g){
        g.setColor(Color.RED);
        if (countCycles > 0)
            for (int i = 0; i < FIRE[view].length; i++)
                for (int j = 0; j < FIRE[view][i].length; j++)
                    if (FIRE[view][i][j] == 1) g.fillRect(j*GUI.MULT + x, i*GUI.MULT + y, GUI.MULT, GUI.MULT);
    }
}

