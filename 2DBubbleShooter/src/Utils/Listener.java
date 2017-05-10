package Utils;

import Core.GameLogic;
import Core.GamePanel;

import java.awt.event.*;

/**
 * Created by Artem Solomatin on 17.02.17.
 * 2DBubbleShooter
 */

/**
 * The class is responsible for the interaction between game and player using keyboard and mouse
 */
public class Listener implements KeyListener, MouseMotionListener, MouseListener {
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT : GameLogic.players.get(0).setLeft(true);
                break;
            case KeyEvent.VK_RIGHT : GameLogic.players.get(0).setRight(true);
                break;
            case KeyEvent.VK_UP : GameLogic.players.get(0).setUp(true);
                break;
            case KeyEvent.VK_DOWN : GameLogic.players.get(0).setDown(true);
                break;
            case KeyEvent.VK_A : GameLogic.players.get(1).setLeft(true);
                break;
            case KeyEvent.VK_D : GameLogic.players.get(1).setRight(true);
                break;
            case KeyEvent.VK_W : GameLogic.players.get(1).setUp(true);
                break;
            case KeyEvent.VK_S : GameLogic.players.get(1).setDown(true);
                break;
            case KeyEvent.VK_SPACE : GameLogic.players.get(1).setFiring(true);
                break;
            /*case KeyEvent.VK_SPACE : GameLogic.players.get(0).setFiring(true);
                break;*/
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_LEFT : GameLogic.players.get(0).setLeft(false);
                break;
            case KeyEvent.VK_RIGHT : GameLogic.players.get(0).setRight(false);
                break;
            case KeyEvent.VK_UP : GameLogic.players.get(0).setUp(false);
                break;
            case KeyEvent.VK_DOWN : GameLogic.players.get(0).setDown(false);
                break;
            case KeyEvent.VK_A : GameLogic.players.get(1).setLeft(false);
                break;
            case KeyEvent.VK_D : GameLogic.players.get(1).setRight(false);
                break;
            case KeyEvent.VK_W : GameLogic.players.get(1).setUp(false);
                break;
            case KeyEvent.VK_S : GameLogic.players.get(1).setDown(false);
                break;
            case KeyEvent.VK_SPACE : GameLogic.players.get(1).setFiring(false);
                break;
            /*case KeyEvent.VK_SPACE : Core.GameLogic.player.setFiring(false);
                break;*/
            case KeyEvent.VK_Q : GamePanel.setPaused();
                break;
            case KeyEvent.VK_1 : GameLogic.players.get(0).addLive();
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            //System.out.println("size:  " + GameLogic.players.size());
            GameLogic.players.get(0).setFiring(true);
            
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            GameLogic.players.get(0).setFiring(false);
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
        /*GameLogic.players.get(0).setMouseX(e.getX());
        GameLogic.players.get(0).setMouseY(e.getY());*/

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        /*GameLogic.players.get(0).setMouseX(e.getX());
        GameLogic.players.get(0).setMouseY(e.getY());*/
    }
}
