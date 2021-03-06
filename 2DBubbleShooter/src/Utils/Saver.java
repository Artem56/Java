package Utils;

import Core.GameLogic;
import Core.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;


/**
 * Created by Artem Solomatin on 14.02.17.
 * 2DBubbleShooter
 */

/**
 * The class is responsible for saving and loading of results and drawing them on the screen
 */
public class Saver implements Serializable {
    //FIELDS
    private String name;
    private int score;

    private transient static final String fileName = "liderboard.ser";


    //CONSTRUCTOR

    /**
     * Constructor of the class
     *
     * @param name   name of the winner
     * @param score  score of the winner
     */
    public Saver(String name, int score){
        this.name = name;
        this.score = score;
    }


    //METHODS
    public static String getFileName() {
        return fileName;
    }

    /*public static void setFileName(String fileName) {
        Saver.fileName = fileName;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Serialization of data from the best player
     *
     * @param profiles  data of the best player
     */
     public static void serData(ArrayList<Saver> profiles){
        try(FileOutputStream fileOut = new FileOutputStream(fileName);

        ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(profiles);
        }

        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Файл для сохранения не найден");
            System.exit(1);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ошибка ввода/вывода");
            System.exit(2);
        }
    }

    /**
     * Deserialization of the player's data
     *
     * @return List of profiles
     */
    public static ArrayList<Saver> deserData(){
        ArrayList<Saver> retSaver = null;
        try(FileInputStream fileIn = new FileInputStream(fileName); ObjectInputStream in = new ObjectInputStream(fileIn)) {
            retSaver = (ArrayList<Saver>) in.readObject();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Файл сохранения не найден");
            System.exit(1);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Ошибка ввода/вывода");
            System.exit(2);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Ошибка чтения класса");
            System.exit(3);
        }
        return retSaver;
    }

    public static void draw(Graphics2D g){

        g.setColor(new Color(100, 50, 200));     //game over
        g.fillRect(0, 0, GamePanel.getWIDTH(), GamePanel.getHEIGHT());
        g.setColor(Color.black);
        g.drawString("Name " + "               " + "Score\n", GamePanel.getWIDTH() - 580, GamePanel.getHEIGHT() - 570 );

        for(int i = 0;i < 10 && i < GameLogic.profiles.size();i++) {
                Saver pr = GameLogic.profiles.get(i);
                g.drawString("" + pr.getName(), GamePanel.getWIDTH() - 580, GamePanel.getHEIGHT() - 520 + i * 50);
                g.drawString("" + pr.getScore() + "\n", GamePanel.getWIDTH() - 380, GamePanel.getHEIGHT() - 520 + i * 50);
        }
    }
}
