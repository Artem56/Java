import java.awt.*;

/**
 * Created by Artem Solomatin on 13.02.17.
 * 2DBubbleShooter
 */

/**
 * Class represents the explosion animation of the enemies when it collision with bullet
 */
public class Explosion implements Mobile {

    //FIELDS
    private double x;
    private double y;
    private int radius;
    private int maxRadius;

    private final double speed = 1.5;

    //CONSTRUCTOR
    public Explosion(double x, double y, int radius, int maxRadius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.maxRadius = maxRadius;
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

    public boolean update(){
        radius += speed;
        return radius >= maxRadius;
    }

    public void draw(Graphics2D g){
        g.setColor(new Color(255, 255, 255, 150));
        g.setStroke(new BasicStroke(2));
        g.drawOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
        g.setStroke(new BasicStroke(1));
    }
}
