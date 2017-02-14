import java.awt.*;

/**
 * Created by Artem Solomatin on 09.02.17.
 * 2DBubbleShooter
 */
public class Bullet implements Mobile {

    //FIELDS
    private double x;
    private double y;


    private int radius;

    private double radians;
    private final int speed = 10;
    private final Color color1 = Color.yellow;

    //CONSTRUCTOR

    public Bullet(double angle, double x, double y){
        this.x = x;
        this.y = y;
        radius = 2;

        radians = Math.toRadians(angle);
    }

    public Bullet(double angle, int radius, double x, double y){
        this.x = x;
        this.y = y;
        this.radius = radius;

        radians = Math.toRadians(angle);
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

    public boolean update(){     //false means everything OK, otherwise we should delete the bullet
        x += Math.cos(radians) * speed;
        y += Math.sin(radians) * speed;

        if(x < -radius || x > GamePanel.WIDTH + radius || y < -radius || y > GamePanel.HEIGHT + radius){
            return true;
        }

        return false;
    }

    public void draw(Graphics2D g){
        g.setColor(color1);
        g.fillOval((int)x - radius, (int)y - radius, 2*radius, 2*radius);

    }

}
