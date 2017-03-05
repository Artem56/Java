import java.awt.*;

/**
 * Created by Artem Solomatin on 05.03.17.
 * Xonix
 */
public class Xonix implements Movable{
    //FIELDS
    private boolean right;
    private boolean left;
    private boolean up;
    private boolean down;

    private double x;
    private double y;
    private final double radius = 0.5 * GamePanel.getBasicSize();

    private final int speed = 5;
    private int lives;
    private int score;
    private final Color colorDefault = Color.white;

    //CONSTRUCTOR
    Xonix(double startX, double startY){
        x = startX;
        y = startY;

        lives = 3;
    }

    //METHODS
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    @Override
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public double getRadius() {
        return radius;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    @Override
    public boolean update() {
        if (left) {
            x -= speed;
        }
        if (right) {
            x += speed;
        }
        if (up) {
            y -= speed;
        }
        if (down) {
            y += speed;
        }

        //reflected from the sides
        if (x < radius) x = radius;
        if (y < radius + GamePanel.getInfoHeight()) y = radius + GamePanel.getInfoHeight();
        if (x > GamePanel.getWIDTH() - radius) x = GamePanel.getWIDTH() - radius;
        if (y > GamePanel.getHEIGHT() - radius) y = GamePanel.getHEIGHT() - radius;
        return false;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(colorDefault);
        g.fillOval((int)(x - radius), (int) (y - radius), (int)(2 * radius), (int)(2 * radius));

        g.setStroke(new BasicStroke(2));           //граница
        g.setColor(Color.gray);
        g.drawOval((int) (x - radius), (int) (y - radius),(int) (2 * radius),(int)(2 * radius));
    }
}
