import java.awt.*;

/**
 * Created by Artem Solomatin on 14.02.17.
 * 2DBubbleShooter
 */
public abstract class EnemyTypes {

    public static void createEnemy(Enemy e){
        //default
        if(e.type == 1) {      //e.cost   ==   (e.speed - 2) * 2 + (e.health - 1)
            e.color1 = Color.BLUE;
            if (e.size == 1) {
                e.speed = 3;
                e.setRadius(5);
                e.health = 1;
                e.cost = 2;
            }
            if(e.size == 2){ //4
                e.speed = 3;
                e.setRadius(10);
                e.health = 2;
                e.cost = 3;
            }
            if(e.size == 3){
                e.speed = 2;
                e.setRadius(15);
                e.health = 5;
                e.cost = 4;
            }
            if(e.size == 4){
                e.speed = 2;
                e.setRadius(25);
                e.health = 6;
                e.cost = 5;
            }
        }

        //middle
        if(e.type == 2){
            e.color1 = Color.green.darker();
            if(e.size == 1){
                e.speed = 3;
                e.setRadius(5);
                e.health = 3;
                e.cost = 4;
            }
            if(e.size == 2){
                e.speed = 2;
                e.setRadius(10);
                e.health = 6;
                e.cost = 5;
            }
            if(e.size == 3){
                e.speed = 2;
                e.setRadius(15);
                e.health = 7;
                e.cost = 6;
            }
            if(e.size == 4){
                e.speed = 2;
                e.setRadius(25);
                e.health = 9;
                e.cost = 8;
            }
        }

        //fat
        if(e.type == 3){
            e.color1 = Color.pink;
            if(e.size == 1){
                e.speed = 1.5;
                e.setRadius(10);
                e.health = 10;
                e.cost = 8;
            }
            if(e.size == 2){
                e.speed = 2;
                e.setRadius(15);
                e.health = 13;
                e.cost = 12;
            }
            if(e.size == 3){
                e.speed = 2;
                e.setRadius(25);
                e.health = 16;
                e.cost = 15;
            }
            if(e.size == 4){
                e.speed = 1.5;
                e.setRadius(40);
                e.health = 20;
                e.cost = 18;
            }
        }

        //fast
        if(e.type == 4){
            e.color1 = Color.magenta;
            if(e.size == 1){
                e.speed = 8;
                e.setRadius(5);
                e.health = 2;
                e.cost = 15;
            }
            if(e.size == 2){
                e.speed = 10;
                e.setRadius(10);
                e.health = 3;
                e.cost = 20;
            }
            if(e.size == 3){
                e.speed = 11;
                e.setRadius(15);
                e.health = 6;
                e.cost = 25;
            }
            if(e.size == 4){
                e.speed = 15;
                e.setRadius(25);
                e.health = 5;
                e.cost = 30;
            }
        }

        //heavy
        if(e.type == 5){
            e.color1 = Color.gray;
            if(e.size == 1){
                e.speed = 7;
                e.setRadius(7);
                e.health = 11;
                e.cost = 20;
            }
            if(e.size == 2){
                e.speed = 8;
                e.setRadius(12);
                e.health = 14;
                e.cost = 25;
            }
            if(e.size == 3){
                e.speed = 10;
                e.setRadius(18);
                e.health = 15;
                e.cost = 30;
            }
            if(e.size == 4){
                e.speed = 12.5;
                e.setRadius(25);
                e.health = 20;
                e.cost = 40;
            }
        }

        //big boss
        if(e.type == 10){
            e.color1 = Color.black;
            if(e.size == 1) {
                e.speed = 10;
                e.setRadius(10);
                e.health = 15;
                e.cost = 30;
            }
            if(e.size == 2){
                e.speed = 15;
                e.setRadius(15);
                e.health = 35;
                e.cost = 60;
            }
            if(e.size == 3){
                e.speed = 25;
                e.setRadius(20);
                e.health = 45;
                e.cost = 90;
            }
            if(e.size == 4){
                e.speed = 40;
                e.setRadius(25);
                e.health = 75;
                e.cost = 150;
            }
        }
    }
}
