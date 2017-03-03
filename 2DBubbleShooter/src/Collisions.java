/**
 * Created by Artem Solomatin on 14.02.17.
 * 2DBubbleShooter
 */
public abstract class Collisions {

    public static void playerPowerCollision(){
        double px = GameLogic.player.getX();
        double py = GameLogic.player.getY();
        int pr = GameLogic.player.getRadius();
        for(int i = 0;i < GameLogic.powerUps.size();i++){
            double ux = GameLogic.powerUps.get(i).getX();
            double uy = GameLogic.powerUps.get(i).getY();
            int ur = GameLogic.powerUps.get(i).getRadius();

            double distance = Math.sqrt((ux - px)*(ux - px) + (uy - py)*(uy - py));
            if(distance <= ur + pr){
                int type = GameLogic.powerUps.get(i).getType();

                switch (type){
                    case 1 : GameLogic.player.addLive();
                        GameLogic.texts.add(new Text(ux, uy, "+1 life"));
                        break;
                    case 2 : GameLogic.player.increasePower(1);
                        GameLogic.texts.add(new Text(ux, uy, "Power"));
                        break;
                    case 3 : GameLogic.slowDownTimer = System.nanoTime();    //ns
                        for(int j = 0;j < GameLogic.enemies.size();j++){
                            GameLogic.enemies.get(j).setSlow(true);
                        }
                        GameLogic.texts.add(new Text(ux, uy, "Slow down"));
                        break;
                    case 4 : GameLogic.player.addScore(10 * Levels.getWaveNumber());
                        GameLogic.texts.add(new Text(ux, uy, "Extra points"));
                        break;
                    case 5 : GameLogic.player.increasePower(2);
                        GameLogic.texts.add(new Text(ux, uy, "Extra power"));
                        break;
                }

                GameLogic.powerUps.remove(i);
                i--;
            }
        }
    }

    public static void playerEnemyCollision(){
        if(!GameLogic.player.isHitting()){
            double px = GameLogic.player.getX();
            double py = GameLogic.player.getY();
            int pr = GameLogic.player.getRadius();
            for(int i = 0;i < GameLogic.enemies.size();i++){
                double ex = GameLogic.enemies.get(i).getX();
                double ey = GameLogic.enemies.get(i).getY();
                int er = GameLogic.enemies.get(i).getRadius();

                double distance = Math.sqrt((px - ex)*(px - ex) + (py - ey)*(py - ey));
                if(distance <= pr + er){
                    GameLogic.player.hit(0);
                }
            }
        }
    }

    public static void bulletEnemyCollision(){
        for(int i = 0;i < GameLogic.bullets.size();i++){
            for(int j = 0;j < GameLogic.enemies.size();j++){
                Bullet b = GameLogic.bullets.get(i);
                Enemy e = GameLogic.enemies.get(j);

                double bx = b.getX();
                double by = b.getY();
                double br = b.getRadius();

                double ex = e.getX();
                double ey = e.getY();
                double er = e.getRadius();

                double distance = Math.sqrt((bx - ex)*(bx - ex) + (by - ey)*(by - ey));
                if(distance <= br + er){        //collision
                    e.hit(b.getRadius());
                    GameLogic.bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
    }
}
