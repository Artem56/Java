package Tests;

import Core.GamePanel;
import GameObjects.*;
import Utils.Saver;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

/**
 * Created by Artem Solomatin on 10.05.17.
 * 2DBubbleShooter
 *
 * Testing graphics in the game
 */
public class GraphicsTests {
    //FIELDS
    static Player player;
    static Enemy enemy;
    static Bullet bullet;
    static Text text;
    static Explosion explosion;

    static GamePanel panel;

    @Before
    public void setUp(){
        player = new Player(Color.white, 0);
        enemy = new Enemy(1, 1);
        bullet = new Bullet(90, 100, 100, player);
        text = new Text(100, 100, "null");
        explosion = new Explosion(100, 100, 200, 300);

        panel = new GamePanel();
    }

    @Test(expected = NullPointerException.class)
    public void drawPlayer(){
        player.draw(null);
    }

    @Test(expected = NullPointerException.class)
    public void drawEnemy(){
        enemy.draw(null);
    }

    @Test(expected = NullPointerException.class)
    public void drawBullet(){
        bullet.draw(null);
    }

    @Test(expected = NullPointerException.class)
    public void drawText(){
        text.draw(null);
    }

    @Test(expected = NullPointerException.class)
    public void drawExplosion(){
        explosion.draw(null);
    }

    @Test(expected = NullPointerException.class)
    public void drawPanel(){
        panel.paint(null);
    }

    @Test
    public void runPanel() throws InterruptedException {
        Thread panelTest = new Thread(panel);

        Assert.assertEquals(false, panelTest.isAlive());
        panelTest.start();
        Assert.assertEquals(true, panelTest.isAlive());
        panelTest.sleep(50);

    }

    @Test(expected = NullPointerException.class)
    public void drawSaver(){
        Saver.draw(null);
    }
}
