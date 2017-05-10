package GameObjects;

import Core.*;

import java.awt.*;

/**
 * Created by Artem Solomatin on 09.02.17.
 * 2DBubbleShooter
 *
 * Class GameObjects.Bullet represents the bullet and is responsible for drawing and changing the state of bullets
 */
public class Bullet implements Mobile {

    //FIELDS
    private double x;
    private double y;
    private int radius;

    private final Player owner;
    private double radians;
    private final int speed = 10;
    private final Color color1 = Color.yellow;

    private double distX;
    private double distY;
    private double dist;

    //CONSTRUCTOR
    /**
     * Class constructor
     */
    public Bullet(double angle, double x, double y, Player owner){
        if(owner == null){
            throw new NullPointerException();
        }
        if(x < owner.getRadius() || y < owner.getRadius() || x > GamePanel.getWIDTH() - owner.getRadius() || y > GamePanel.getHEIGHT() - owner.getRadius()){
            throw new IllegalArgumentException();
        }
        this.owner = owner;
        this.x = x;
        this.y = y;
        radius = 2;

        /*distX = Core.GameLogic.player.getMouseX() - Core.GameLogic.player.getX();
        distY = Core.GameLogic.player.getMouseY() - Core.GameLogic.player.getY();
        dist = Math.sqrt(distX * distX + distY * distY);*/       //ДЛЯ СТРЕЛЬБЫ МЫШКОЙ

        radians = Math.toRadians(angle);
        //radians = angle;           //ДЛЯ СТРЕЛЬБЫ МЫШКОЙ
    }

    /**
     * Class constructor
     */
    public Bullet(double angle, int radius, double x, double y, Player owner){
        this.owner = owner;
        this.x = x;
        this.y = y;
        this.radius = radius;

        radians = Math.toRadians(angle);
        //radians = angle;           //ДЛЯ СТРЕЛЬБЫ МЫШКОЙ
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

    public Player getOwner() {
        return owner;
    }

    /**
     * Update all bullets in game
     *
     * @return false means everything OK, otherwise we should delete the bullet
     */
    public boolean update(){
        x += Math.cos(radians) * speed;
        y += Math.sin(radians) * speed;

        /*x +=  distX/dist * speed;//ДЛЯ СТРЕЛЬБЫ МЫШКОЙ
        y +=  distY/dist * speed;*/


        return x < -radius || x > GamePanel.getWIDTH() + radius || y < -radius || y > GamePanel.getHEIGHT() + radius;

    }

    public void draw(Graphics2D g){
        g.setColor(color1);
        g.fillOval((int)x - radius, (int)y - radius, 2*radius, 2*radius);

    }
}
