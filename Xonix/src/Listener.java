import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Artem Solomatin on 05.03.17.
 * Xonix
 */
public class Listener implements KeyListener {
    //FIELDS

    //METHODS
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                GameLogic.player.setLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                GameLogic.player.setRight(true);
                break;
            case KeyEvent.VK_UP:
                GameLogic.player.setUp(true);
                break;
            case KeyEvent.VK_DOWN:
                GameLogic.player.setDown(true);
                break;
            case KeyEvent.VK_Q:
                GameLogic.setPaused(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                GameLogic.player.setLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                GameLogic.player.setRight(false);
                break;
            case KeyEvent.VK_UP:
                GameLogic.player.setUp(false);
                break;
            case KeyEvent.VK_DOWN:
                GameLogic.player.setDown(false);
                break;
            case KeyEvent.VK_Q:
                GameLogic.setPaused(false);
                break;
        }
    }
}
