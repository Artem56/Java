import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by artem on 17.09.16.
 */
public class Alien { // for attacking wave
    public int x, y, type, view = 0;
    private int SIZE_X, SIZE_Y = 4*GUI.MULT;
    private final static int[][][][] PATTERN_OF_ALIENS = {
            {{{0,0,0,0,1,1,1,1,0,0,0,0}, {0,1,1,1,1,1,1,1,1,1,1,0}, {1,1,1,1,1,1,1,1,1,1,1,1}, // alien 1/1
                    {1,1,1,0,0,1,1,0,0,1,1,1}, {1,1,1,1,1,1,1,1,1,1,1,1}, {0,0,1,1,1,0,0,1,1,1,0,0},
                    {0,1,1,0,0,1,1,0,0,1,1,0}, {0,0,1,1,0,0,0,0,1,1,0,0}, {12}},

                    {{0,0,0,0,1,1,1,1,0,0,0,0}, {0,1,1,1,1,1,1,1,1,1,1,0}, {1,1,1,1,1,1,1,1,1,1,1,1}, // alien 1/2
                            {1,1,1,0,0,1,1,0,0,1,1,1}, {1,1,1,1,1,1,1,1,1,1,1,1}, {0,0,0,1,1,0,0,1,1,0,0,0},
                            {0,0,1,1,0,1,1,0,1,1,0,0}, {1,1,0,0,0,0,0,0,0,0,1,1}}},

            {{{0,0,1,0,0,0,0,0,1,0,0,0}, {0,0,0,1,0,0,0,1,0,0,0,0}, {0,0,1,1,1,1,1,1,1,0,0,0}, // alien 2/1
                    {0,1,1,0,1,1,1,0,1,1,0,0}, {1,1,1,1,1,1,1,1,1,1,1,0}, {1,0,1,1,1,1,1,1,1,0,1,0},
                    {1,0,1,0,0,0,0,0,1,0,1,0}, {0,0,0,1,1,0,1,1,0,0,0,0}, {11}},

                    {{0,0,1,0,0,0,0,0,1,0,0,0}, {1,0,0,1,0,0,0,1,0,0,1,0}, {1,0,1,1,1,1,1,1,1,0,1,0}, // alien 2/2
                            {1,1,1,0,1,1,1,0,1,1,1,0}, {1,1,1,1,1,1,1,1,1,1,1,0}, {0,1,1,1,1,1,1,1,1,1,0,0},
                            {0,0,1,0,0,0,0,0,1,0,0,0}, {0,1,0,0,0,0,0,0,0,1,0,0}}},

            {{{0,0,0,1,1,0,0,0,0,0,0,0}, {0,0,1,1,1,1,0,0,0,0,0,0}, {0,1,1,1,1,1,1,0,0,0,0,0}, // alien 3/1
                    {1,1,0,1,1,0,1,1,0,0,0,0}, {1,1,1,1,1,1,1,1,0,0,0,0}, {0,0,1,0,0,1,0,0,0,0,0,0},
                    {0,1,0,1,1,0,1,0,0,0,0,0}, {1,0,1,0,0,1,0,1,0,0,0,0}, {8}},

                    {{0,0,0,1,1,0,0,0,0,0,0,0}, {0,0,1,1,1,1,0,0,0,0,0,0}, {0,1,1,1,1,1,1,0,0,0,0,0}, // alien 3/2
                            {1,1,0,1,1,0,1,1,0,0,0,0}, {1,1,1,1,1,1,1,1,0,0,0,0}, {0,1,0,1,1,0,1,0,0,0,0,0},
                            {1,0,0,0,0,0,0,1,0,0,0,0}, {0,1,0,0,0,0,1,0,0,0,0,0}}}
    };

    Alien(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
        SIZE_X = PATTERN_OF_ALIENS[type][view][8][0];
    }

    int getType() { return type; }

    boolean isHitRay() {    //столкновение с лучом?
        if (Game.ray.isEnable())
            if ((Game.ray.getX() >= x) && (Game.ray.getX() <= x + SIZE_X*GUI.MULT))
                if (Game.ray.getY() < y + SIZE_Y*GUI.MULT) {
                    Game.ray.disable();
                    return true;
                }
        return false;
    }

    void nextStep(int direction) {
        view = 1 - view; // change view each step
        if (direction == KeyEvent.VK_RIGHT) x += Wave.STEP_X;
        else if (direction == KeyEvent.VK_LEFT) x -= Wave.STEP_X;
        else if (direction == KeyEvent.VK_DOWN) y += Wave.STEP_Y;
    }

    void bang() { Game.flash.enable(x, y - 2); }

    void shot() { Game.rays.add(x + SIZE_X/2, y + SIZE_Y); }

    void paint(Graphics g) {
        g.setColor(Color.white);
        for (int col = 0; col < SIZE_X; col++)
            for (int row = 0; row < SIZE_Y; row++)
                if (PATTERN_OF_ALIENS[type][view][row][col] == 1)
                    g.fillRect(col*GUI.MULT + x, row*GUI.MULT + y, GUI.MULT, GUI.MULT);
    }
}

class FlashAlien { // flash when the alien explodes
    final int[][] BANG = {
            {0,0,0,0,0,1,0,0,0,0,0,0}, {0,1,0,0,0,1,0,0,1,0,0,0}, {0,0,1,0,0,0,0,0,1,0,0,1}, {0,0,0,1,0,0,0,1,0,0,1,0}, {1,1,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,1,1}, {0,1,0,0,1,0,0,0,1,0,0,0}, {1,0,0,1,0,0,0,0,0,1,0,0}, {0,0,0,1,0,0,1,0,0,0,1,0}, {0,0,0,0,0,0,1,0,0,0,0,0}
    };
    int x, y, counter;

    void enable(int x, int y) {
        this.x = x;
        this.y = y;
        counter = 5; // the show time in the animation cycles
    }

    void show() { if (counter > 0) counter--; }

    void paint(Graphics g) {
        if (counter > 0)
            for (int i = 0; i < BANG.length; i++)
                for (int j = 0; j < BANG[i].length; j++)
                    if (BANG[i][j] == 1) g.fillRect(j*GUI.MULT + x, i*GUI.MULT + y, GUI.MULT, GUI.MULT);
    }
}
