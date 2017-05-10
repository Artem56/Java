package GameObjects;

import Core.GameLogic;
import Core.GamePanel;
import Utils.EnemyTypes;

import java.awt.*;

/**
 * Created by Artem Solomatin on 09.02.17.
 * 2DBubbleShooter
 */

/**
 * Class represents enemies and is responsible for rendering and change the state of enemies
 */
public class Enemy implements Mobile, Wounded {
    //FIELDS

    private double x;
    private double y;
    private int radius;
    private double dx;
    private double dy;

    private double radians;
    private double speed;
    private int health;
    private int cost;

    private int type;
    private int size;

    public Color color1;
    private final Color color2 = Color.white;
    private Player killerNumber;

    private boolean dead;
    private boolean slow;

    private boolean hit;      //получил ли урон
    private long hitTimer;    //то же самое что recoveryTimer    ns
    private final int hittenTime = 50;   //ms то же самое что  untouchableTime


    //CONSTRUCTOR
    /**
     * Class constructor
     */
    public Enemy(int type, int size){
        //System.out.println("type: " + type + " size " + size);
        this.type = type;
        this.size = size;

        EnemyTypes.createEnemy(this);

        x = Math.random() * GamePanel.getWIDTH();
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
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

    public double getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }

    public Color getColor1() {
        return color1;
    }

    public boolean isSlow() {
        return slow;
    }

    public void setSlow(boolean slow) {
        this.slow = slow;
    }

    public Player getKillerNumber() {
        return killerNumber;
    }

    public void setKillerNumber(Player killerNumber) {
        this.killerNumber = killerNumber;
    }

    public void hit(int radius){
        health = health - radius / 2;
        if(health <= 0){
            dead = true;
        }

        hit = true;
        hitTimer = System.nanoTime();
    }

    public boolean isDead(){
        return dead;
    }

    /**
     * The division of the enemy for several
     */
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

    /**
     * Update all enemies in game
     *
     * @return false means everything OK, otherwise we should delete the enemy
     */
    public boolean update(){
        if(slow) {
            x += dx * 0.3;
            y += dy * 0.3;
        }else{
            x += dx;
            y += dy;
        }

        /*if(!ready){
            if(x > radius && x < Core.GamePanel.WIDTH - radius &&
                    y > radius && y < Core.GamePanel.HEIGHT - radius){
                ready = true;
            }
        }*/

        if(x < radius && dx < 0) dx = -dx;
        if(y < radius && dy < 0) dy = -dy;
        if(x > GamePanel.getWIDTH() - radius && dx > 0) dx = -dx;
        if(y > GamePanel.getHEIGHT() - radius && dy > 0) dy = -dy;

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
