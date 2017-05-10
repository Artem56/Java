package Tests;


import GameObjects.Bullet;
import GameObjects.Enemy;
import GameObjects.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

/**
 * Created by Artem Solomatin on 10.05.17.
 * 2DBubbleShooter
 */
public class GameUnitsTests {
    //FIELDS
    static Player player;
    static Player secondPlayer;

    static Enemy enemy;

    static Bullet bullet;

    @Before
    public void setUp(){
        player = new Player(Color.white, 0);
        secondPlayer = new Player(Color.black, 1);

        enemy = new Enemy(1, 1);

        bullet = new Bullet(90, 100, 100, player);
    }

    //PLAYER
    @Test
    public void playerConstructorTest(){
        new Player(Color.black, 0);
    }

    @Test
    public void playerLiveTest(){
        player.addLive();
        Assert.assertEquals(4, player.getLives());

        for(int i = 0;i < 20;i++){
            player.addLive();
        }
        Assert.assertEquals(10, player.getLives());
    }

    @Test
    public void playerHitTest(){
        Assert.assertEquals(false, player.isHitting());

        int startLives = player.getLives();
        player.hit(-1);
        Assert.assertEquals(startLives - 1, player.getLives());
        Assert.assertEquals(true, player.isHitting());
    }

//    /**
//     * Test for movement and collisions with borders
//     */
//    @Test
//    public void playerMovementTest(){
//        int speed = player.getSpeed();
//        double startX = player.getX();
//
//        player.setLeft(true);
//
//        for(int i = 0;i < 10;i++){
//            player.update();
//        }
//        Assert.assertEquals(startX - speed * 20, player.getX(), 1);
//
//        for(int i = 0;i < 50;i++){
//            player.update();
//        }
//        Assert.assertEquals(player.getRadius(), player.getX(), 0.5);
//    }

    //ENEMIES
    @Test(expected = IllegalArgumentException.class)
    public void enemyConstructorTest(){
        new Enemy(20, 20);
    }

    @Test
    public void enemyHittingTest(){
        int startHP = enemy.getHealth();
        enemy.hit(2);

        Assert.assertEquals(startHP - 1, enemy.getHealth());
    }

    @Test
    public void enemyDeathTest(){
        enemy.hit(2);

        Assert.assertEquals(true, enemy.isDead());
    }

    @Test
    public void enemyUpdateTest(){
        enemy.hit(2);

        Assert.assertEquals(false, enemy.update());
    }

    //BULLETS
    @Test
    public void bulletConstructorTest1(){
        new Bullet(-1, 100, 200, player);
    }

    @Test(expected = NullPointerException.class)
    public void bulletConstructorTest2(){
        new Bullet(-1, 100, 200, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void bulletConstructorTest3(){
        new Bullet(-1, 0, 200, player);
    }

    @Test
    public void bulletConstructorTest4(){
        new Bullet(4,-1, 0, 200, player);
    }
}
