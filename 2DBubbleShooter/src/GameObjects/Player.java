package GameObjects;

import Core.GameLogic;
import Core.GamePanel;

import java.awt.*;

/**
 * Created by Artem Solomatin on 08.02.17.
 * 2DBubbleShooter
 */
public class Player implements Mobile, Wounded {
    //FIELDS

    private double x;
    private double y;
    private final int radius = 7;

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private final int speed = 5;
    private int lives;
    private int score;
    private int power;           //очков
    private int powerLevel;      //когда достаточно очков - апнуть левел
    private int[] requiredPower = {
            1, 2, 4, 8, 14, 22, 32, 44, 58, 74, 92
    };
    private int maxPowerLevel = 10;

    private boolean hit;          //получил ли урон
    private long recoveryTimer;    //ns
    private final int untouchableTime = 2000;   //ms

    private boolean firing;
    private long firingTimer;         //ns
    private final long firingDelay = 200;   //ms

    private final Color colorDefault = Color.WHITE;
    private final Color colorHitted = Color.RED;

    private int mouseX;
    private int mouseY;
    private double angle;

    //CONSTRUCTOR

    public Player() {
        x = GamePanel.getWIDTH() / 2;
        y = GamePanel.getHEIGHT() / 2;

        lives = 3;

        firingTimer = System.nanoTime();
    }

    //FUNCTIONS
    public void setFiring(boolean b) {
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

    public boolean isHitting() {
        return hit;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public void addLive() {
        if (lives < 10) {
            lives++;
        }
    }

    public void hit(int useless) {
        lives--;
        hit = true;
        recoveryTimer = System.nanoTime();
    }

    public boolean isDead() {
        return lives <= 0;
    }

    public void increasePower(int gain) {
        power += gain;
        if (powerLevel == maxPowerLevel) {
            if (power > requiredPower[powerLevel]) {
                power = requiredPower[powerLevel];
            }
            return;
        }
        if (power >= requiredPower[powerLevel]) {
            power -= requiredPower[powerLevel];
            if (powerLevel < maxPowerLevel) {
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
        if (y < radius) y = radius;
        if (x > GamePanel.getWIDTH() - radius) x = GamePanel.getWIDTH() - radius;
        if (y > GamePanel.getHEIGHT() - radius) y = GamePanel.getHEIGHT() - radius;

        //firing
        if (firing) {
            long elapsed = (System.nanoTime() - firingTimer) / 1000_000;   //ms
            if (elapsed > firingDelay) {
                setDirection();
                firingTimer = System.nanoTime();
                if (powerLevel < 2) {
                    GameLogic.bullets.add(new Bullet(270, x, y));
                } else if (powerLevel < 3) {
                    GameLogic.bullets.add(new Bullet(270, x + 5, y));
                    GameLogic.bullets.add(new Bullet(270, x - 5, y));
                } else if (powerLevel < 4) {
                    GameLogic.bullets.add(new Bullet(260, x - 5, y));
                    GameLogic.bullets.add(new Bullet(270, x, y));
                    GameLogic.bullets.add(new Bullet(280, x + 5, y));
                } else if (powerLevel < 5) {
                    GameLogic.bullets.add(new Bullet(262, x - 10, y));
                    GameLogic.bullets.add(new Bullet(266, x - 5, y));
                    GameLogic.bullets.add(new Bullet(270, x, y));
                    GameLogic.bullets.add(new Bullet(274, x + 5, y));
                    GameLogic.bullets.add(new Bullet(278, x + 10, y));
                } else if (powerLevel < 6) {
                    GameLogic.bullets.add(new Bullet(270, 4, x + 5, y));
                    GameLogic.bullets.add(new Bullet(270, 4, x - 5, y));
                } else if (powerLevel < 7) {
                    GameLogic.bullets.add(new Bullet(260, 4, x - 7, y));
                    GameLogic.bullets.add(new Bullet(270, 4, x, y));
                    GameLogic.bullets.add(new Bullet(270, 4, x + 7, y));
                } else if (powerLevel < 8) {
                    GameLogic.bullets.add(new Bullet(262, 4, x - 15, y));
                    GameLogic.bullets.add(new Bullet(266, 4, x - 7, y));
                    GameLogic.bullets.add(new Bullet(270, 4, x, y));
                    GameLogic.bullets.add(new Bullet(274, 4, x + 7, y));
                    GameLogic.bullets.add(new Bullet(278, 4, x + 15, y));
                } else if (powerLevel < 9){
                    GameLogic.bullets.add(new Bullet(258, 4, x - 6, y));
                    GameLogic.bullets.add(new Bullet(262, 4, x - 4, y));
                    GameLogic.bullets.add(new Bullet(266, 4, x - 2, y));
                    GameLogic.bullets.add(new Bullet(270, 4, x, y));
                    GameLogic.bullets.add(new Bullet(274, 4, x + 2, y));
                    GameLogic.bullets.add(new Bullet(278, 4, x + 4, y));
                    GameLogic.bullets.add(new Bullet(282, 4, x + 6, y));

                } else if (powerLevel < 10){
                    GameLogic.bullets.add(new Bullet(258, 6, x - 15, y));
                    GameLogic.bullets.add(new Bullet(262, 6, x - 10, y));
                    GameLogic.bullets.add(new Bullet(266, 6, x - 5, y));
                    GameLogic.bullets.add(new Bullet(270, 6, x, y));
                    GameLogic.bullets.add(new Bullet(274, 6, x + 5, y));
                    GameLogic.bullets.add(new Bullet(278, 6, x + 10, y));
                    GameLogic.bullets.add(new Bullet(282, 6, x + 15, y));

                } else if (powerLevel < 11){
                    GameLogic.bullets.add(new Bullet(250, 6, x - 25, y));
                    GameLogic.bullets.add(new Bullet(254, 6, x - 20, y));
                    GameLogic.bullets.add(new Bullet(258, 6, x - 15, y));
                    GameLogic.bullets.add(new Bullet(262, 6, x - 10, y));
                    GameLogic.bullets.add(new Bullet(266, 6, x - 5, y));
                    GameLogic.bullets.add(new Bullet(270, 6, x, y));
                    GameLogic.bullets.add(new Bullet(274, 6, x + 5, y));
                    GameLogic.bullets.add(new Bullet(278, 6, x + 10, y));
                    GameLogic.bullets.add(new Bullet(282, 6, x + 15, y));
                    GameLogic.bullets.add(new Bullet(286, 6, x + 20, y));
                    GameLogic.bullets.add(new Bullet(290, 6, x + 25, y));

                }


                    /*if(powerLevel < 2){          //ДЛЯ СТРЕЛЬБЫ МЫШКОЙ
                        Core.GameLogic.bullets.add(new GameObjects.Bullet(angle, x, y));
                    }else if(powerLevel < 3){
                        Core.GameLogic.bullets.add(new GameObjects.Bullet(angle, x + 5, y));
                        Core.GameLogic.bullets.add(new GameObjects.Bullet(angle, x - 5, y));
                    }
                    else if(powerLevel < 4){
                        Core.GameLogic.bullets.add(new GameObjects.Bullet(angle - 0.1, x - 5, y));
                        Core.GameLogic.bullets.add(new GameObjects.Bullet(angle, x, y));
                        Core.GameLogic.bullets.add(new GameObjects.Bullet(angle + 0.1, x + 5, y));
                    }else if(powerLevel < 5){
                        Core.GameLogic.bullets.add(new GameObjects.Bullet(265, x - 5, y));
                        Core.GameLogic.bullets.add(new GameObjects.Bullet(265, x - 10, y));
                        Core.GameLogic.bullets.add(new GameObjects.Bullet(270, x, y));
                        Core.GameLogic.bullets.add(new GameObjects.Bullet(275, x + 5, y));
                        Core.GameLogic.bullets.add(new GameObjects.Bullet(275, x + 10, y));
                    }else if (powerLevel < 6) {
                        Core.GameLogic.bullets.add(new GameObjects.Bullet(270, 4, x + 5, y));
                        Core.GameLogic.bullets.add(new GameObjects.Bullet(270, 4, x - 5, y));
                    } else if(powerLevel < 7) {
                    Core.GameLogic.bullets.add(new GameObjects.Bullet(265, 4, x - 7, y));
                    Core.GameLogic.bullets.add(new GameObjects.Bullet(270, 4, x, y));
                    Core.GameLogic.bullets.add(new GameObjects.Bullet(275, 4, x + 7, y));
                }else if(powerLevel < 8) {
                    Core.GameLogic.bullets.add(new GameObjects.Bullet(265, 4, x - 7, y));
                    Core.GameLogic.bullets.add(new GameObjects.Bullet(265, 4, x - 15, y));
                    Core.GameLogic.bullets.add(new GameObjects.Bullet(270, 4, x, y));
                    Core.GameLogic.bullets.add(new GameObjects.Bullet(275, 4, x + 7, y));
                    Core.GameLogic.bullets.add(new GameObjects.Bullet(275, 4, x + 15, y));
                }*/

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

    public void setDirection() {  //для стрельбы мышкой
        //direction
        if (mouseX < GamePanel.getWIDTH() && mouseY < GamePanel.getHEIGHT()) {
            double dx = mouseX - x;
            double dy = mouseY - y;
            double dist = Math.sqrt(dx*dx + dy*dy);

            if(dx > 0) {
                angle = Math.asin(dy / dist);
            }
            else if(dx < 0){
                angle = Math.PI - Math.asin(dy / dist);
            }

        }
    }
}
