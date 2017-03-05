/**
 * Created by Artem Solomatin on 05.03.17.
 * Xonix
 */
public abstract class GameLogic {
    //FIELDS
    private static boolean running;
    private static boolean paused;
    public static Xonix player;

    private static final int FPS = 30;

    //METHODS
    public static void setRunning(boolean b) {
        running = b;
    }

    public static boolean isRunning() {
        return running;
    }

    public static boolean isPaused() {
        return paused;
    }

    public static void setPaused(boolean paused) {
        GameLogic.paused = paused;
    }

    /**
     * description: main loop of the game.
     */
    public static void gameLoop(){
        player = new Xonix(10.0, 10.0 + GamePanel.getInfoHeight());

        long startTime;        //ns
        long loopTime;                  //ms
        long waitTime;                 //ms
        long targetTime = 1000/FPS;    //ms

        while(running){
            startTime = System.nanoTime();

            Game.panel.gameRender();
            Game.panel.gameUpdate();
            Game.panel.gameDraw();

            loopTime = (System.nanoTime() - startTime) / 1000_000;
            waitTime = (targetTime - loopTime);

            try {
                if(waitTime > 0) {
                    Game.thread.sleep(waitTime);
                }
            } catch (InterruptedException e) {
                System.out.println("Ошибка остановки потока игры.");
                e.printStackTrace();
            }
        }
    }

    /**
     * description: udates all mobile elements of the game.
     */
    public static void mobileUpdate(){
        //player update
        player.update();

    }
}
