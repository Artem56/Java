/**
 * Created by Artem Solomatin on 14.02.17.
 * 2DBubbleShooter
 */
public abstract class Collisions {

    public static void playerPowerCollision(){
        double px = GamePanel.player.getX();
        double py = GamePanel.player.getY();
        int pr = GamePanel.player.getRadius();
        for(int i = 0;i < GamePanel.powerUps.size();i++){
            double ux = GamePanel.powerUps.get(i).getX();
            double uy = GamePanel.powerUps.get(i).getY();
            int ur = GamePanel.powerUps.get(i).getRadius();

            double distance = Math.sqrt((ux - px)*(ux - px) + (uy - py)*(uy - py));
            if(distance <= ur + pr){
                int type = GamePanel.powerUps.get(i).getType();

                switch (type){
                    case 1 : GamePanel.player.addLive();
                        GamePanel.texts.add(new Text(ux, uy, "+1 life"));
                        break;
                    case 2 : GamePanel.player.increasePower(1);
                        GamePanel.texts.add(new Text(ux, uy, "Power"));
                        break;
                    case 3 : GamePanel.slowDownTimer = System.nanoTime();    //ns
                        for(int j = 0;j < GamePanel.enemies.size();j++){
                            GamePanel.enemies.get(j).setSlow(true);
                        }
                        GamePanel.texts.add(new Text(ux, uy, "Slow down"));
                        break;
                    case 4 : GamePanel.player.addScore(5 * Levels.waveNumber);
                        GamePanel.texts.add(new Text(ux, uy, "Extra points"));
                        break;
                    case 5 : GamePanel.player.increasePower(2);
                        GamePanel.texts.add(new Text(ux, uy, "Extra power"));
                        break;
                }

                GamePanel.powerUps.remove(i);
                i--;
            }
        }
    }

    public static void playerEnemyCollision(){
        if(!GamePanel.player.isHitting()){
            double px = GamePanel.player.getX();
            double py = GamePanel.player.getY();
            int pr = GamePanel.player.getRadius();
            for(int i = 0;i < GamePanel.enemies.size();i++){
                double ex = GamePanel.enemies.get(i).getX();
                double ey = GamePanel.enemies.get(i).getY();
                int er = GamePanel.enemies.get(i).getRadius();

                double distance = Math.sqrt((px - ex)*(px - ex) + (py - ey)*(py - ey));
                if(distance <= pr + er){
                    GamePanel.player.hit();
                }
            }
        }
    }

    public static void bulletEnemyCollision(){
        for(int i = 0;i < GamePanel.bullets.size();i++){
            for(int j = 0;j < GamePanel.enemies.size();j++){
                Bullet b = GamePanel.bullets.get(i);
                Enemy e = GamePanel.enemies.get(j);

                double bx = b.getX();
                double by = b.getY();
                double br = b.getRadius();

                double ex = e.getX();
                double ey = e.getY();
                double er = e.getRadius();

                double distance = Math.sqrt((bx - ex)*(bx - ex) + (by - ey)*(by - ey));
                if(distance <= br + er){        //collision
                    e.hit();
                    GamePanel.bullets.remove(i);
                    i--;
                    break;
                }
            }
        }
    }
}
