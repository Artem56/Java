package GameObjects;

import java.awt.*;

/**
 * Created by Artem Solomatin on 13.02.17.
 * 2DBubbleShooter
 */

/**
 * The class is responsible for drawing and modifying pop-up labels on the screen
 */
public class Text implements Mobile {

    //FIELDS
    private double x;
    private double y;
    private final long time = 700;   //ms
    private long startTime;   //ns

    private String s;

    //CONSTRUCTOR
    /**
     * Class constructor
     */
    public Text(double x, double y, String s) {
        this.x = x;
        this.y = y;
        this.s = s;

        startTime = System.nanoTime();
    }

    //METHODS
    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public int getRadius(){return 0;}

    /**
     * Update all texts in game
     *
     * @return false means everything OK, otherwise we should delete the text
     */
    public boolean update(){
        long elapsed = (System.nanoTime() - startTime) / 1000_000;   //ms
        return elapsed >= time;
    }

    public void draw(Graphics2D g){
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        long elapsed = (System.nanoTime() - startTime) / 1000_000;   //ms
        int transparancy = (int)(255*Math.sin(Math.PI * elapsed / time));

        if(transparancy >= 255){   //почему- то может вылететь ошибка)
            transparancy = 254;
        }
        try {
            g.setColor(new Color(255, 255, 255, transparancy));
        }catch (IllegalArgumentException e){
            System.out.println("Ошибка прорисовки текста. Поправил.");
        }
        g.drawString(s, (int)x, (int)y);

    }
}
