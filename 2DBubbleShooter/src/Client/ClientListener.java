package Client;

import java.awt.event.*;

/**
 * Created by Artem Solomatin on 30.04.17.
 * 2DBubbleShooter
 */
public class ClientListener implements KeyListener, MouseMotionListener, MouseListener {
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e){
        System.out.println("key pressed");
        switch (e.getKeyCode()){
            case KeyEvent.VK_A : ClientPanel.message.setLeft(true);
                break;
            case KeyEvent.VK_D : ClientPanel.message.setRight(true);
                break;
            case KeyEvent.VK_W : ClientPanel.message.setUp(true);
                break;
            case KeyEvent.VK_S : ClientPanel.message.setDown(true);
                break;
            case KeyEvent.VK_SPACE : ClientPanel.message.setFiring(true);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A : ClientPanel.message.setLeft(false);
                break;
            case KeyEvent.VK_D : ClientPanel.message.setRight(false);
                break;
            case KeyEvent.VK_W : ClientPanel.message.setUp(false);
                break;
            case KeyEvent.VK_S : ClientPanel.message.setDown(false);
                break;
            case KeyEvent.VK_SPACE : ClientPanel.message.setFiring(false);
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        /*if(e.getButton() == MouseEvent.BUTTON1){
            ClientPanel.message.setFiring(true);
        }*/

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        /*if(e.getButton() == MouseEvent.BUTTON1){
            ClientPanel.message.setFiring(false);
        }*/
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
