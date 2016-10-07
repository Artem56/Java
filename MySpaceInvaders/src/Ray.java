import java.awt.*;
import java.util.ArrayList;

/**
 * Created by artem on 17.09.16.
 */
public class Ray { // from laser cannon
    private final int WIDTH = GUI.MULT;
    private final int HEIGHT = 5*GUI.MULT;
    private final int OFFSET = 6*GUI.MULT;
    private int x, y;
    private boolean exists;

    void start(int x, int y) {
        if (!exists) {
            exists = true;
            this.x = x + (Game.cannon.getWidth() - WIDTH) / 2;  //создание луча с координатами
            this.y = y - HEIGHT;
        }
    }

    void fly() {
        if (exists) {
            y -= OFFSET;
            exists = ((y + OFFSET) > 0); //перестает существовать, если выходит за пределы экрана
        }
    }

    void disable() { exists = false; }

    boolean isEnable() { return exists; }

    int getX() { return x; }
    int getY() { return y; }

    void paint(Graphics g) {
        if (exists) g.fillRect(x, y, WIDTH, HEIGHT);
    }
}

class AlienRay { // from one alien
    final int width = 6;
    final int height = 10;
    final int dy = 6; // define speed of ray
    int x, y;

    AlienRay(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void fly() { y += dy; }

    boolean hitGround() { return y + height > GUI.GROUND_Y; }

    boolean hitCannon() {
        if (y + height > Game.cannon.getY())
            if (x >= Game.cannon.getX() && x <= Game.cannon.getX() + Game.cannon.getWidth())
                return true;
        return false;
    }

    void paint(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(x + 2, y, 2, height);
        g.fillRect(x, y + height - 4, width, 2);
    }
}

class AlienRays { // a few rays from alien
    ArrayList<AlienRay> rays = new ArrayList<AlienRay>();

    void add(int x, int y) {
        rays.add(new AlienRay(x, y));
    }

    void fly() {
        for (AlienRay ray : rays) ray.fly();
        for (AlienRay ray : rays) // check hit ground
            if (ray.hitGround()) {
                rays.remove(ray);
                break;
            }
        for (AlienRay ray : rays) // check hit cannon
            if (ray.hitCannon()) {
                Game.bang.enable();
                Game.countLives--;
                Game.cannon = new Cannon();
                if(Game.countLives == 0) {
                    Game.gameOver = true;
                }
                rays.remove(ray);
                break;
            }
    }

    int getSize() { return rays.size(); }

    void paint(Graphics g) {
        for (AlienRay ray : rays) ray.paint(g);
    }
}
