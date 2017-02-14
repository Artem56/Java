import java.awt.*;

/**
 * Created by Artem Solomatin on 13.02.17.
 * 2DBubbleShooter
 */
public interface Mobile {
    public double getX();

    public double getY();

    public int getRadius();

    public boolean update();

    public void draw(Graphics2D g);
}
