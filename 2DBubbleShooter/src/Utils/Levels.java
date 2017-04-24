package Utils; /**
 * Created by Artem Solomatin on 13.02.17.
 * 2DBubbleShooter
 */

import Core.GameLogic;
import GameObjects.Enemy;

/**
 * The class contains the description of all the waves of enemies
 */
public abstract class Levels {
    //FIELDS
    private static int waveNumber;

    //METHODS
    public static int getWaveNumber() {
        return waveNumber;
    }

    public static void addWaveNumber() {
        waveNumber++;
    }

    public static void createNewWave(){
        //size1 - 1  size2 - 3   size3 - 7   size4 - 15противников

    if(waveNumber == 1){   //25     20противников
        for(int i = 0;i<5 ;i++){
            GameLogic.enemies.add(new Enemy(1, 1));
            GameLogic.enemies.add(new Enemy(1, 2));
        }
    }

        if(waveNumber == 2){    //50   32противника
        for(int i = 0;i<4;i++){
            GameLogic.enemies.add(new Enemy(3, 1));
        }
        for(int i = 0;i<3;i++){
            GameLogic.enemies.add(new Enemy(1, 3));
        }
            GameLogic.enemies.add(new Enemy(2, 3));
    }

        if(waveNumber == 3){    //100    20противников
        for(int i = 0;i<5;i++){
            GameLogic.enemies.add(new Enemy(4, 1));
        }
        for(int i = 0;i<5;i++){
            GameLogic.enemies.add(new Enemy(2, 2));
        }
    }

        if(waveNumber == 4){    //150    94противника
        for(int i = 0;i<2;i++){
            GameLogic.enemies.add(new Enemy(1, 4));
        }
        for(int i = 0;i<4;i++){
            GameLogic.enemies.add(new Enemy(2, 3));
        }
        for(int i = 0;i<5;i++){
            GameLogic.enemies.add(new Enemy(4, 2));
        }
        for(int i = 0;i<3;i++){
            GameLogic.enemies.add(new Enemy(3, 3));
        }
    }

        if(waveNumber == 5){   //230    16противников
        for(int i = 0;i<2;i++){
            GameLogic.enemies.add(new Enemy(5, 2));
        }
        for(int i = 0;i<3;i++){
            GameLogic.enemies.add(new Enemy(10, 1));
        }
            GameLogic.enemies.add(new Enemy(10, 3));
    }

        if(waveNumber == 6){     //320    307противников
        for(int i = 0;i<5;i++){
            GameLogic.enemies.add(new Enemy(5, 3));
        }
        for(int i = 0;i<11;i++){
            GameLogic.enemies.add(new Enemy(2, 3));
        }
        for(int i = 0;i<13;i++){
            GameLogic.enemies.add(new Enemy(2, 4));
        }
    }

        if(waveNumber == 7){   //450    98противников
        for(int i = 0;i<8;i++){
            GameLogic.enemies.add(new Enemy(3, 3));
        }
        for(int i = 0;i<4;i++){
            GameLogic.enemies.add(new Enemy(4, 2));
        }
        for(int i = 0;i<10;i++){
            GameLogic.enemies.add(new Enemy(5, 2));
        }
    }

        if(waveNumber == 8){   //600   255противников
        for(int i = 0;i<10;i++){
            GameLogic.enemies.add(new Enemy(3, 4));
        }
        for(int i = 0;i<14;i++){
            GameLogic.enemies.add(new Enemy(4, 3));
        }
        for(int i = 0;i<2;i++){
            GameLogic.enemies.add(new Enemy(5, 2));
        }
            GameLogic.enemies.add(new Enemy(5, 1));
    }

        if(waveNumber == 9){   //725     366противников
        for(int i = 0;i<23;i++){
            GameLogic.enemies.add(new Enemy(4, 4));
        }
        for(int i = 0;i<7;i++){
            GameLogic.enemies.add(new Enemy(2, 2));
        }
    }

        if(waveNumber == 10){   //1050   105противников
        for(int i = 0;i<7;i++){
            GameLogic.enemies.add(new Enemy(10, 4));
        }
    }

        if(waveNumber == 11){   //1560   795противников
        for(int i = 0;i<10;i++){
            GameLogic.enemies.add(new Enemy(1, 4));
        }
        for(int i = 0;i<10;i++){
            GameLogic.enemies.add(new Enemy(2, 4));
        }
        for(int i = 0;i<10;i++){
            GameLogic.enemies.add(new Enemy(3, 4));
        }
        for(int i = 0;i<10;i++){
            GameLogic.enemies.add(new Enemy(4, 4));
        }
        for(int i = 0;i<10;i++){
            GameLogic.enemies.add(new Enemy(5, 4));
        }
        for(int i = 0;i<3;i++){
            GameLogic.enemies.add(new Enemy(5, 4));
        }
    }

        if(waveNumber == 12){   //вместо этого сделать бесконечную волну
            GameLogic.setRunning(false);
    }

}
}
