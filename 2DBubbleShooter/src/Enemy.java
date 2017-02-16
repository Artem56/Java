import java.awt.*;

/**
 * Created by Artem Solomatin on 09.02.17.
 * 2DBubbleShooter
 */
public class Enemy implements Mobile, Wounded {
    //FIELDS

    private double x;
    private double y;
    public int radius;
    private double dx;
    private double dy;

    private double radians;
    public double speed;
    public int health;
    public int cost;

    public int type;
    public int size;

    public Color color1;
    private final Color color2 = Color.white;

    private boolean dead;
    private boolean slow;

    private boolean hit;      //получил ли урон
    private long hitTimer;    //то же самое что recoveryTimer    ns
    private final int hittenTime = 50;   //ms то же самое что  untouchableTime


    //CONSTRUCTOR
    public Enemy(int type, int size){
        this.type = type;
        this.size = size;

        EnemyTypes.createEnemy(this);

        x = Math.random() * GamePanel.WIDTH;
        y = -radius;

        radians = Math.toRadians(Math.random() * 120 + 30);

        dx = Math.cos(radians) * speed;
        dy = Math.sin(radians) * speed;
    }
    //FUNCTIONS

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getRadius() {
        return radius;
    }

    public int getCost(){
        return cost;
    }

    public int getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public boolean isSlow() {
        return slow;
    }

    public void setSlow(boolean slow) {
        this.slow = slow;
    }

    public void hit(){
        health--;
        if(health <= 0){
            dead = true;
        }

        hit = true;
        hitTimer = System.nanoTime();
    }

    public boolean isDead(){
        return dead;
    }

    public void explode(){
        if(size > 1){
            int amount = 2;

            for(int i = 0;i < amount; i++){
                Enemy newEnemy = new Enemy(this.getType(), this.getSize() - 1);
                newEnemy.setSlow(slow);
                newEnemy.x = this.x;
                newEnemy.y = this.y;

                newEnemy.radians = Math.toRadians(Math.random() * 360);
                GameLogic.enemies.add(newEnemy);
            }

        }
    }

    public boolean update(){
        if(slow) {
            x += dx * 0.3;
            y += dy * 0.3;
        }else{
            x += dx;
            y += dy;
        }

        /*if(!ready){
            if(x > radius && x < GamePanel.WIDTH - radius &&
                    y > radius && y < GamePanel.HEIGHT - radius){
                ready = true;
            }
        }*/

        if(x < radius && dx < 0) dx = -dx;
        if(y < radius && dy < 0) dy = -dy;
        if(x > GamePanel.WIDTH - radius && dx > 0) dx = -dx;
        if(y > GamePanel.HEIGHT - radius && dy > 0) dy = -dy;

        if(hit){
            int elapsed = (int)(System.nanoTime() - hitTimer) / 1000_000;     //ms
            if(elapsed > hittenTime){
                hit = false;
                hitTimer = 0;
            }
        }

        return false;
    }

    public void draw(Graphics2D g) {
        if(hit){
            g.setColor(color2);
            g.fillOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);

            g.setStroke(new BasicStroke(3));
            g.setColor(color2.darker());
            g.drawOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
            g.setStroke(new BasicStroke(1));
        }else {
            g.setColor(color1);
            g.fillOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);

            g.setStroke(new BasicStroke(3));
            g.setColor(color1.darker());
            g.drawOval((int) (x - radius), (int) (y - radius), 2 * radius, 2 * radius);
            g.setStroke(new BasicStroke(1));
        }
    }
}
