import java.awt.*;

/**
 * Created by Artem Solomatin on 08.02.17.
 * 2DBubbleShooter
 */
public class Player implements Mobile, Wounded {
    //FIELDS

    private double x;
    private double y;
    private final int radius = 5;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean active;

    private final int speed = 5;
    private int lives;
    private int score;
    private int power;           //очков
    private int powerLevel;      //когда достаточно очков - апнуть левел
    private int[] requiredPower = {
            1, 2, 4, 8, 16, 32, 64
    };
    private int maxPowerLevel = 6;

    private boolean hit;          //получил ли урон
    private long recoveryTimer;    //ns
    private final int untouchableTime = 2000;   //ms

    private boolean firing;
    private long firingTimer;         //ns
    private final long firingDelay = 200;   //ms

    private final Color colorDefault = Color.WHITE;
    private final Color colorHitted = Color.RED;

    //CONSTRUCTOR

    public Player(){
        x = GamePanel.WIDTH/2;
        y = GamePanel.HEIGHT/2;

        lives = 3;

        firingTimer = System.nanoTime();
    }

    //FUNCTIONS
    public void setFiring(boolean b){
        firing = b;
    }

    public int getLives() {
        return lives;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isHitting(){
        return hit;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void addLive(){
        lives++;
    }

    public void hit(){
        lives--;
        hit = true;
        recoveryTimer = System.nanoTime();
    }

    public boolean isDead(){
        return lives <= 0;
    }

    public void increasePower(int gain){
        power += gain;
        if(powerLevel == maxPowerLevel){
            if(power > requiredPower[powerLevel]){
                power = requiredPower[powerLevel];
            }
            return;
        }
        if(power >= requiredPower[powerLevel]){
            power -= requiredPower[powerLevel];
            if(powerLevel < maxPowerLevel) {
                    powerLevel++;
            }
        }
    }

    public int getPower() {
        return power;
    }

    public int getRequiredPower() {
        return requiredPower[powerLevel];
    }

    public boolean update(){
        if(left) {
            x -= speed;
        }
        if(right) {
            x += speed;
        }
        if(up) {
            y -= speed;
        }
        if(down) {
            y += speed;
        }

        //reflected from the sides
        if(x < radius) x = radius;
        if(y < radius) y = radius;
        if(x > GamePanel.WIDTH - radius) x = GamePanel.WIDTH - radius;
        if(y > GamePanel.HEIGHT - radius) y = GamePanel.HEIGHT - radius;

        //firing
        if(firing){
            long elapsed = (System.nanoTime() - firingTimer) / 1000_000;   //ms
            if(elapsed > firingDelay){
                firingTimer = System.nanoTime();
                if(powerLevel < 2){
                    GamePanel.bullets.add(new Bullet(270, x, y));
                }else if(powerLevel < 3){
                    GamePanel.bullets.add(new Bullet(270, x + 5, y));
                    GamePanel.bullets.add(new Bullet(270, x - 5, y));
                }
                else if(powerLevel < 4){
                    GamePanel.bullets.add(new Bullet(265, x - 5, y));
                    GamePanel.bullets.add(new Bullet(270, x, y));
                    GamePanel.bullets.add(new Bullet(275, x + 5, y));
                }else if(powerLevel < 5){
                    GamePanel.bullets.add(new Bullet(265, x - 5, y));
                    GamePanel.bullets.add(new Bullet(265, x - 10, y));
                    GamePanel.bullets.add(new Bullet(270, x, y));
                    GamePanel.bullets.add(new Bullet(275, x + 5, y));
                    GamePanel.bullets.add(new Bullet(275, x + 10, y));
                }else if (powerLevel < 6) {
                    GamePanel.bullets.add(new Bullet(270, 5, x + 5, y));
                    GamePanel.bullets.add(new Bullet(270, 5, x - 5, y));
                } else {
                    GamePanel.bullets.add(new Bullet(265, x - 5, y));
                    GamePanel.bullets.add(new Bullet(270, x, y));
                    GamePanel.bullets.add(new Bullet(275, x + 5, y));
                    GamePanel.bullets.add(new Bullet(315, x, y));
                    GamePanel.bullets.add(new Bullet(0, x, y));
                    GamePanel.bullets.add(new Bullet(45, x, y));
                    GamePanel.bullets.add(new Bullet(90, x, y));
                    GamePanel.bullets.add(new Bullet(135, x, y));
                    GamePanel.bullets.add(new Bullet(180, x, y));
                    GamePanel.bullets.add(new Bullet(225, x, y));

                }

            }
        }

        //collapse
        if(hit){
            int elapsed = (int)(System.nanoTime() - recoveryTimer) / 1000_000;   //ms
            if(elapsed > untouchableTime){
                hit = false;
                recoveryTimer = 0;
            }

        }
        return false;        //для соответствия интерфейсу
    }


    public void draw(Graphics2D g){
        if(hit){
            g.setColor(colorHitted);
            g.fillOval((int)x - radius, (int)y - radius, 2*radius, 2*radius);

            g.setStroke(new BasicStroke(2));           //граница
            g.setColor(colorHitted.darker());
            g.drawOval((int)x - radius, (int)y - radius, 2*radius, 2*radius);
        }else {
            g.setColor(colorDefault);
            g.fillOval((int) x - radius, (int) y - radius, 2 * radius, 2 * radius);

            g.setStroke(new BasicStroke(2));           //граница
            g.setColor(Color.gray);
            g.drawOval((int) x - radius, (int) y - radius, 2 * radius, 2 * radius);
        }

    }
}
