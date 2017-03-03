import java.awt.event.*;

/**
 * Created by Artem Solomatin on 17.02.17.
 * 2DBubbleShooter
 */
public class Listener implements KeyListener, MouseMotionListener, MouseListener {
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT : GameLogic.player.setLeft(true);
                break;
            case KeyEvent.VK_RIGHT : GameLogic.player.setRight(true);
                break;
            case KeyEvent.VK_UP : GameLogic.player.setUp(true);
                break;
            case KeyEvent.VK_DOWN : GameLogic.player.setDown(true);
                break;
            /*case KeyEvent.VK_SPACE : GameLogic.player.setFiring(true);
                break;*/
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT : GameLogic.player.setLeft(false);
                break;
            case KeyEvent.VK_RIGHT : GameLogic.player.setRight(false);
                break;
            case KeyEvent.VK_UP : GameLogic.player.setUp(false);
                break;
            case KeyEvent.VK_DOWN : GameLogic.player.setDown(false);
                break;
            /*case KeyEvent.VK_SPACE : GameLogic.player.setFiring(false);
                break;*/
            case KeyEvent.VK_Q : GamePanel.setPaused();
                break;
            case KeyEvent.VK_1 : GameLogic.player.addLive();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            GameLogic.player.setFiring(true);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            GameLogic.player.setFiring(false);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        GameLogic.player.setMouseX(e.getX());
        GameLogic.player.setMouseY(e.getY());

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        GameLogic.player.setMouseX(e.getX());
        GameLogic.player.setMouseY(e.getY());
    }
}
