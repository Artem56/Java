package Client;

import java.io.Serializable;

/**
 * Created by Artem Solomatin on 30.04.17.
 * 2DBubbleShooter
 */
public class Message implements Serializable {
    //FIELDS
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean firing;
    //private boolean paused;


    //METHODS
    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isFiring() {
        return firing;
    }

    public void setFiring(boolean firing) {
        this.firing = firing;
    }
}
