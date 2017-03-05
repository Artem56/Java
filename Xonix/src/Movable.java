import java.awt.*;

/**
 * Created by Artem Solomatin on 05.03.17.
 * Xonix
 */
public interface Movable {
    double getX();

    double getY();

    double getRadius();

    boolean update();

    void draw(Graphics2D g);
}
