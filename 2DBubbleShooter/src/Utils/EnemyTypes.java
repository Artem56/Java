package Utils;

import GameObjects.Enemy;

import java.awt.*;

/**
 * Created by Artem Solomatin on 14.02.17.
 * 2DBubbleShooter
 *
 * Class lists and description of all possible in-game enemies
 */
public abstract class EnemyTypes {

    /**
     * Creation of enemy
     *
     * @param e a link to the enemy
     */
    public static void createEnemy(Enemy e){
        //default
        if(e.getType() == 1) {      //e.cost   ==   (e.setSpeed();() - 2) * 2 + (e.health - 1)
            e.color1 = Color.BLUE;
            if (e.getSize() == 1) {
                e.setSpeed(3);
                e.setRadius(5);
                e.setHealth(1);
                e.setCost(2);
            }
            if(e.getSize() == 2){ //4
                e.setSpeed(3);
                e.setRadius(10);
                e.setHealth(2);
                e.setCost(3);
            }
            if(e.getSize() == 3){
                e.setSpeed(2);
                e.setRadius(15);
                e.setHealth(5);
                e.setCost(4);
            }
            if(e.getSize() == 4){
                e.setSpeed(2);
                e.setRadius(25);
                e.setHealth(6);
                e.setCost(5);
            }
        }

        //middle
        else if(e.getType() == 2){
            e.color1 = Color.green.darker();
            if(e.getSize() == 1){
                e.setSpeed(3);
                e.setRadius(5);
                e.setHealth(3);
                e.setCost(4);
            }
            if(e.getSize() == 2){
                e.setSpeed(2);
                e.setRadius(10);
                e.setHealth(6);
                e.setCost(5);
            }
            if(e.getSize() == 3){
                e.setSpeed(2);
                e.setRadius(15);
                e.setHealth(7);
                e.setCost(6);
            }
            if(e.getSize() == 4){
                e.setSpeed(2);
                e.setRadius(25);
                e.setHealth(9);
                e.setCost(8);
            }
        }

        //fat
        else if(e.getType() == 3){
            e.color1 = Color.pink;
            if(e.getSize() == 1){
                e.setSpeed(1.5);
                e.setRadius(10);
                e.setHealth(10);
                e.setCost(8);
            }
            if(e.getSize() == 2){
                e.setSpeed(2);
                e.setRadius(15);
                e.setHealth(13);
                e.setCost(12);
            }
            if(e.getSize() == 3){
                e.setSpeed(2);
                e.setRadius(25);
                e.setHealth(16);
                e.setCost(15);
            }
            if(e.getSize() == 4){
                e.setSpeed(1.5);
                e.setRadius(40);
                e.setHealth(20);
                e.setCost(18);
            }
        }

        //fast
        else if(e.getType() == 4){
            e.color1 = Color.magenta;
            if(e.getSize() == 1){
                e.setSpeed(8);
                e.setRadius(5);
                e.setHealth(2);
                e.setCost(15);
            }
            if(e.getSize() == 2){
                e.setSpeed(10);
                e.setRadius(10);
                e.setHealth(3);
                e.setCost(20);
            }
            if(e.getSize() == 3){
                e.setSpeed(11);
                e.setRadius(15);
                e.setHealth(6);
                e.setCost(25);
            }
            if(e.getSize() == 4){
                e.setSpeed(15);
                e.setRadius(25);
                e.setHealth(5);
                e.setCost(30);
            }
        }

        //heavy
        else if(e.getType() == 5){
            e.color1 = Color.gray;
            if(e.getSize() == 1){
                e.setSpeed(7);
                e.setRadius(7);
                e.setHealth(11);
                e.setCost(20);
            }
            if(e.getSize() == 2){
                e.setSpeed(8);
                e.setRadius(12);
                e.setHealth(14);
                e.setCost(25);
            }
            if(e.getSize() == 3){
                e.setSpeed(10);
                e.setRadius(18);
                e.setHealth(15);
                e.setCost(30);
            }
            if(e.getSize() == 4){
                e.setSpeed(12.5);
                e.setRadius(25);
                e.setHealth(20);
                e.setCost(40);
            }
        }

        //big boss
        else if(e.getType() == 10){
            e.color1 = Color.black;
            if(e.getSize() == 1) {
                e.setSpeed(10);
                e.setRadius(10);
                e.setHealth(15);
                e.setCost(30);
            }
            if(e.getSize() == 2){
                e.setSpeed(15);
                e.setRadius(15);
                e.setHealth(35);
                e.setCost(60);
            }
            if(e.getSize() == 3){
                e.setSpeed(25);
                e.setRadius(20);
                e.setHealth(45);
                e.setCost(90);
            }
            if(e.getSize() == 4){
                e.setSpeed(40);
                e.setRadius(25);
                e.setHealth(75);
                e.setCost(150);
            }
        }
        else{
            throw new IllegalArgumentException();
        }
    }
}
