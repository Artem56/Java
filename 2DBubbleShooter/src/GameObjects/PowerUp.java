package GameObjects;

import Core.GamePanel;

import java.awt.*;

/**
 * Created by Artem Solomatin on 10.02.17.
 * 2DBubbleShooter
 */

/**
 * Class represents the possible power-ups in the game and is responsible for the drawing and changing on the game screen
 */
public class PowerUp {
    // FIELDS
    private double x;
    private double y;
    private int radius;

    private final int speed = 2;

    int type;
    //1 -- +1 live
    //2 -- power for weapon
    //3 -- decrease time speed
    //4 -- more points  (5*waveNumber поинтов)
    //5 -- 2 * power for weapon
    private Color color;


    //CONSTRUCTOR
    public PowerUp(int type, double x, double y){
        this.type = type;
        this.x = x;
        this.y = y;
        radius = 3;
        switch (type){
            case 1 : this.color = Color.red;
                radius = 4;
                break;
            case 2 : this.color = Color.yellow;
                break;
            case 3 : this.color = Color.lightGray;
                break;
            case 4 : this.color = Color.cyan;
                radius = 4;
                break;
            case 5 : this.color = Color.yellow.darker();
                radius = 4;
        }
    }


    //METHODS
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public int getType() {
        return type;
    }

    public boolean update(){     //если нужно удалить (при выходе за экран например), то вернуть true
        y += speed;

        return y > GamePanel.getHEIGHT();
    }

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillRect((int)(x - radius), (int)(y - radius), 2 * radius, 2 * radius);

        g.setStroke(new BasicStroke(3));
        g.setColor(color.darker());
        g.drawRect((int)(x - radius), (int)(y - radius), 2 * radius, 2 * radius);
    }
}
