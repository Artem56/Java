package GameObjects; /**
 * Created by Artem Solomatin on 14.02.17.
 * 2DBubbleShooter
 */

import Core.GameLogic;
import Utils.Levels;

/**
 * The class is responsible for calculating collisions occurring in the game
 */
public abstract class Collisions {

    public static void playerPowerCollision(){
        for(Player player : GameLogic.players) {
            double px = player.getX();
            double py = player.getY();
            double pr = player.getRadius();
            for (int i = 0; i < GameLogic.powerUps.size(); i++) {
                double ux = GameLogic.powerUps.get(i).getX();
                double uy = GameLogic.powerUps.get(i).getY();
                int ur = GameLogic.powerUps.get(i).getRadius();

                double distance = Math.sqrt((ux - px) * (ux - px) + (uy - py) * (uy - py));
                if (distance <= ur + pr) {
                    int type = GameLogic.powerUps.get(i).getType();

                    switch (type) {
                        case 1:
                            player.addLive();
                            GameLogic.texts.add(new Text(ux, uy, "+1 life"));
                            break;
                        case 2:
                            player.increasePower(1);
                            GameLogic.texts.add(new Text(ux, uy, "Power"));
                            break;
                        case 3:
                            GameLogic.slowDownTimer = System.nanoTime();    //ns
                            for (int j = 0; j < GameLogic.enemies.size(); j++) {
                                GameLogic.enemies.get(j).setSlow(true);
                            }
                            GameLogic.texts.add(new Text(ux, uy, "Slow down"));
                            break;
                        case 4:
                            player.addScore(10 * Levels.getWaveNumber());
                            GameLogic.texts.add(new Text(ux, uy, "Extra points"));
                            break;
                        case 5:
                            player.increasePower(2);
                            GameLogic.texts.add(new Text(ux, uy, "Extra power"));
                            break;
                    }

                    GameLogic.powerUps.remove(i);
                    i--;
                }
            }
        }
    }

    public static void playerEnemyCollision() {
        for (Player player : GameLogic.players) {
            if (!player.isHitting()) {
                double px = player.getX();
                double py = player.getY();
                int pr = player.getRadius();
                for (int i = 0; i < GameLogic.enemies.size(); i++) {
                    double ex = GameLogic.enemies.get(i).getX();
                    double ey = GameLogic.enemies.get(i).getY();
                    int er = GameLogic.enemies.get(i).getRadius();

                    double distance = Math.sqrt((px - ex) * (px - ex) + (py - ey) * (py - ey));
                    if (distance <= pr + er) {
                        player.hit(0);
                    }
                }
            }
        }
    }

    public static void bulletPlayerCollision(){    //ВЕРНО ТОЛЬКО ДЛЯ ДВУХ ИГРОКОВ
        for(int i = 0;i < GameLogic.bullets.size();i++) {
            for (int j = 0; j < GameLogic.players.size(); j++) {
                Bullet b = GameLogic.bullets.get(i);
                Player pl = GameLogic.players.get(j);
                if (!b.getOwner().equals(pl)) {
                    double bx = b.getX();
                    double by = b.getY();
                    double br = b.getRadius();

                    double plx = pl.getX();
                    double ply = pl.getY();
                    double plr = pl.getRadius();
                    double distance = Math.sqrt(Math.pow((bx - plx), 2) + Math.pow((by - ply), 2));

                    if(distance <= br + plr){        //collision
                        pl.hit(b.getRadius());
                        GameLogic.bullets.remove(i);
                        i--;
                        break;
                    }
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
                    if(e.isDead()){      //контрольный выстрел
                        e.setKillerNumber(b.getOwner());
                    }
                    GameLogic.bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
    }
}
