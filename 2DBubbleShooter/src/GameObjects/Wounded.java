package GameObjects; /**
 * Created by Artem Solomatin on 13.02.17.
 * 2DBubbleShooter
 */

/**
 * The interface should be implemented by all objects, which are able to take damage or die
 */

public interface Wounded {
    void hit(int powerOfBullet);

    boolean isDead();
}
