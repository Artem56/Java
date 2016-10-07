import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Created by artem on 17.09.16.
 */
public class Wave {
    protected static int numberOfWave;
    protected static int STEP_X = 5 + 2*numberOfWave;
    protected static int STEP_Y = 15;
    private final int MAX_ALIEN_RAYS = 2 + numberOfWave/2;     //ИЗМЕНИТЬ В СЛОЖНОСТИ

    private final int[][] PATTERN = {
            {2,2,2,2,2,2,2,2,2,2,2,2},
            {1,1,1,1,1,1,1,1,1,1,1,1}, {1,1,1,1,1,1,1,1,1,1,1,1},
            {0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0},

            {0,0,0,0,0,0,0,0,0,0,0,0}, {0,0,0,0,0,0,0,0,0,0,0,0},
            {1,1,1,1,1,1,1,1,1,1,1,1}, {1,1,1,1,1,1,1,1,1,1,1,1},
            {2,2,2,2,2,2,2,2,2,2,2,2}};//Увеличить кол-во alien в линии
    private ArrayList<Alien> wave = new ArrayList<Alien>();
    private int countFrames;
    private int direction = KeyEvent.VK_RIGHT;
    private boolean stepDown;
    private int startX = 20;
    private int startY = 60;

    private final int FRAMES_DELAY = 1;/* ((numberOfWave == 0)?10 + 20/(1 + numberOfWave):10 + 20/numberOfWave) ; //сколько прорисовок анимации волна стоит
                                                                 //функция, стремится из 30 к 10*/

    Wave() {
        numberOfWave++;
        for (int y = 0; y < 4 + numberOfWave; y++)
            for (int x = 0; x < PATTERN[y].length; x++)
                wave.add(new Alien(startX + x*GUI.MULT*15 + PATTERN[y][x]*GUI.MULT, startY + y*GUI.MULT*16, PATTERN[y][x]));
    }

    void nextStep() {
        if (countFrames == FRAMES_DELAY) {//ШАГ
            if ((startX == 10) || (startX == 17*STEP_X + 10)) { // 17 шагов и смена направления
                if (!stepDown) {
                    direction = KeyEvent.VK_DOWN;
                } else {
                    direction = (startX == 10)? KeyEvent.VK_RIGHT : KeyEvent.VK_LEFT;
                    stepDown = false;
                }
            }
            for (Alien alien : wave) { // wave moves and shots
                alien.nextStep(direction);
                if (Game.random.nextInt(10 - numberOfWave) == 5 + 5/(numberOfWave + 1))
                    if (Game.rays.getSize() < MAX_ALIEN_RAYS)
                        alien.shot();
            }
            if (direction == KeyEvent.VK_DOWN) {
                startY += STEP_Y;
                stepDown = true;
            } else {
                startX += (direction == KeyEvent.VK_RIGHT)? STEP_X : -STEP_X;
            }
            countFrames = 0;
        } else { countFrames++; }
        for (Alien alien : wave) // check hit alien
            if (alien.isHitRay()) {
                Game.countScore += (alien.getType() + 1) * 10;
                Cannon.hitNumber++;
                alien.bang();
                wave.remove(alien);
                break;
            }
        for(Alien alien : wave){   //СОПРИКОСНОВЕНИЕ С ЗЕМЛЁЙ
            if(alien.y > GUI.GROUND_Y - Cannon.HEIGHT - 20){
                Game.gameOver = true;
                break;
            }
        }
    }

    boolean isDestroyed() { return wave.size() == 0; }

    void paint(Graphics g) {
        for (Alien alien : wave) alien.paint(g);
    }
}
