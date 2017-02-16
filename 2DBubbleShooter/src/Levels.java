/**
 * Created by Artem Solomatin on 13.02.17.
 * 2DBubbleShooter
 */
public abstract class Levels {
    //FIELDS
    public static int waveNumber;

    //METHODS
    public static void createNewWave(){
    if(waveNumber == 1){   //25
        for(int i = 0;i<5 ;i++){
            GameLogic.enemies.add(new Enemy(1, 1));
            GameLogic.enemies.add(new Enemy(1, 2));
        }
    }

        if(waveNumber == 2){    //50
        for(int i = 0;i<4;i++){
            GameLogic.enemies.add(new Enemy(3, 1));
        }
        for(int i = 0;i<3;i++){
            GameLogic.enemies.add(new Enemy(1, 3));
        }
            GameLogic.enemies.add(new Enemy(2, 3));
    }

        if(waveNumber == 3){    //100
        for(int i = 0;i<5;i++){
            GameLogic.enemies.add(new Enemy(4, 1));
        }
        for(int i = 0;i<5;i++){
            GameLogic.enemies.add(new Enemy(2, 2));
        }
    }

        if(waveNumber == 4){    //150
        for(int i = 0;i<8;i++){
            GameLogic.enemies.add(new Enemy(1, 4));
        }
        for(int i = 0;i<5;i++){
            GameLogic.enemies.add(new Enemy(2, 3));
        }
        for(int i = 0;i<5;i++){
            GameLogic.enemies.add(new Enemy(4, 2));
        }
    }

        if(waveNumber == 5){   //240
        for(int i = 0;i<2;i++){
            GameLogic.enemies.add(new Enemy(5, 3));
        }
        for(int i = 0;i<6;i++){
            GameLogic.enemies.add(new Enemy(10, 1));
        }
    }

        if(waveNumber == 6){     //320
        for(int i = 0;i<3;i++){
            GameLogic.enemies.add(new Enemy(5, 3));
        }
        for(int i = 0;i<5;i++){
            GameLogic.enemies.add(new Enemy(1, 3));
        }
        for(int i = 0;i<11;i++){
            GameLogic.enemies.add(new Enemy(2, 3));
        }
        for(int i = 0;i<18;i++){
            GameLogic.enemies.add(new Enemy(2, 4));
        }
    }

        if(waveNumber == 7){   //450
        for(int i = 0;i<10;i++){
            GameLogic.enemies.add(new Enemy(1, 4));
        }
        for(int i = 0;i<6;i++){
            GameLogic.enemies.add(new Enemy(3, 3));
        }
        for(int i = 0;i<4;i++){
            GameLogic.enemies.add(new Enemy(4, 1));
        }
        for(int i = 0;i<10;i++){
            GameLogic.enemies.add(new Enemy(5, 2));
        }
    }

        if(waveNumber == 8){   //600
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

        if(waveNumber == 9){   //725
        for(int i = 0;i<23;i++){
            GameLogic.enemies.add(new Enemy(4, 4));
        }
        for(int i = 0;i<7;i++){
            GameLogic.enemies.add(new Enemy(2, 2));
        }
    }

        if(waveNumber == 10){   //1000
        for(int i = 0;i<10;i++){
            GameLogic.enemies.add(new Enemy(10, 4));
        }
    }

        if(waveNumber == 11){   //1500
        for(int i = 0;i<10;i++){
            GameLogic.enemies.add(new Enemy(1, 4));
        }
        for(int i = 0;i<10;i++){
            GameLogic.enemies.add(new Enemy(2, 4));
        }
        for(int i = 0;i<20;i++){
            GameLogic.enemies.add(new Enemy(3, 4));
        }
        for(int i = 0;i<17;i++){
            GameLogic.enemies.add(new Enemy(4, 4));
        }
        for(int i = 0;i<10;i++){
            GameLogic.enemies.add(new Enemy(5, 4));
        }
    }

        if(waveNumber == 12){   //вместо этого сделать бесконечную волну
            GameLogic.setRunning(false);
    }

}

}
