package GameObjects;

import java.awt.*;

/**
 * Created by Artem Solomatin on 13.02.17.
 * 2DBubbleShooter
 */

/**
 * The interface should be implemented by all moving objects in the game
 */
public interface Mobile {
    double getX();

    double getY();

    int getRadius();

    boolean update();

    void draw(Graphics2D g);
}
