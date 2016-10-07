import javafx.application.Application;
import javafx.scene.text.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Created by artem on 17.09.16.
 */
public class GUI extends JFrame {
    private final String TITLE_OF_PROGRAM = "Space Invaders!!!";
    protected final static int MULT = 2;
    protected final static int WINDOW_X = 225*MULT;
    protected final static int WINDOW_Y = 250*MULT;
    private final int START_LOCATION = 50;
    final static int SHOW_DELAY = 20; //SPEED OF THE GAME  ИЗМЕНИТЬ В СЛОЖНОСТИ
    final static int GROUND_Y = WINDOW_Y - Cannon.STAND + 3;

    private static JLabel score = new JLabel("Score");
    private static JLabel lives = new JLabel("Lives");
    private static JLabel gameOver;
    private static JLabel scoreNumber = new JLabel();
    private static JLabel livesNumber = new JLabel();
    private static JLabel waveNumber = new JLabel(Integer.toString(Wave.numberOfWave));
    private static JLabel wave = new JLabel("Wave");
    //private static JButton newGame;
    private static JMenuBar menuBar = new JMenuBar();
    private static boolean creation;   //костыль для лэйблов



    static void drawLabels(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(10, GROUND_Y, WINDOW_X - 10, 2);

        score.setForeground(Color.BLUE);
        score.setBounds((int)(0.045*WINDOW_X), (int)(0.027*WINDOW_Y), 35*MULT, 10*MULT);   //(int)(0.045*WINDOW_X), (int)(0.027*WINDOW_Y),
        score.setFont(new Font("Verdana", Font.PLAIN, 23));
        scoreNumber.removeAll();
        scoreNumber.setForeground(Color.GREEN);
        scoreNumber.setBounds((int)(0.22 * WINDOW_X), (int)(0.027*WINDOW_Y), 15*MULT, 10*MULT);  //(int)(0.22 * WINDOW_X), (int)(0.027*WINDOW_Y),
        scoreNumber.setFont(new Font("Verdana", Font.PLAIN, 23));

        lives.setForeground(Color.BLUE);
        lives.setBounds((int)(0.74 * WINDOW_X), (int)(0.027*WINDOW_Y), 35*MULT, 10*MULT);
        lives.setFont(new Font("Verdana", Font.PLAIN, 23));
        livesNumber.setForeground(Color.GREEN);
        livesNumber.setBounds((int)(0.92 * WINDOW_X), (int)(0.027*WINDOW_Y), 15*MULT, 10*MULT);
        livesNumber.setFont(new Font("Verdana", Font.PLAIN, 23));

        wave.setForeground(Color.BLUE);
        wave.setBounds((int)(0.74 * WINDOW_X), (int)(0.037*WINDOW_Y), 35*MULT, 10*MULT);
        wave.setFont(new Font("Verdana", Font.PLAIN, 23));
        waveNumber.setForeground(Color.GREEN);
        waveNumber.setBounds((int)(0.22 * WINDOW_X), (int)(0.037*WINDOW_Y), 15*MULT, 10*MULT);  //(int)(0.22 * WINDOW_X), (int)(0.037*WINDOW_Y),
        waveNumber.setFont(new Font("Verdana", Font.PLAIN, 20));
        /*newGame = new JButton("Press to start a new game)");
        newGame.setBounds((int)0.4*WINDOW_X,(int)0.5*WINDOW_Y, (int)0.2*WINDOW_X,(int)0.1*WINDOW_Y);
        newGame.addActionListener(event -> {
            gameOver.setText("KEK");
        });*/


        if(Game.gameOver) {
            gameOver = new JLabel("Game over\n" + "   accuracy" + ((Cannon.shotNumber == 0)?0:(Cannon.hitNumber * 100/Cannon.shotNumber)) + "%");
            gameOver.setForeground(Color.RED);
            gameOver.setBounds((int)(0.32 * WINDOW_X), (int)(0.39*WINDOW_Y), 80*MULT, 80*MULT);
            gameOver.setFont(new Font("Verdana", Font.PLAIN, 28));


            Game.bang.show();
            if(!creation) {
                Game.canvasPanel.setBackground(Color.WHITE);
                score.setText("");
                scoreNumber.setText("");
                lives.setText("");
                livesNumber.setText("");
                wave.setText("");
                waveNumber.setText("");
                Game.canvasPanel.add(gameOver);
                //Game.canvasPanel.add(newGame);
                creation = true;
            }
        }
        if(!Game.gameOver) {
            Game.canvasPanel.add(score);
            scoreNumber.setText(Integer.toString(Game.countScore));
            Game.canvasPanel.add(scoreNumber);
            Game.canvasPanel.add(lives);
            livesNumber.setText(Integer.toString(Game.countLives));
            Game.canvasPanel.add(livesNumber);
            Game.canvasPanel.add(wave);
            waveNumber.setText(Integer.toString(Wave.numberOfWave));
            Game.canvasPanel.add(waveNumber);
        }
    }


    GUI() {
        JMenu menuGame = new JMenu("Game");  //ПОДМЕНЮ
        JMenu menuHelp = new JMenu("Help");
        menuBar.add(menuGame);
        menuBar.add(menuHelp);
        setJMenuBar(menuBar);
        JMenuItem newGame = new JMenuItem("New game");//Создаем элементы подменю Game с обработчиками событий
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem control = new JMenuItem("Control keys");
        newGame.addActionListener(e -> {
            if(isFocused()) Game.pause = !Game.pause;
            //newGame();
        });
        exit.addActionListener(e -> {
            if(isFocused()) Game.pause = !Game.pause;
            System.exit(0);//Выход из системы
        });
        control.addActionListener(e ->{
            Game.cannon.pause();
        });
        menuGame.add(newGame);//Добавляем созданные элементы к подменю
        menuGame.add(exit);
        menuHelp.add(control);

        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, WINDOW_X + 10, WINDOW_Y + 20);
        setResizable(false);
        setVisible(true);
        Game.canvasPanel.setBackground(Color.black);

        getContentPane().add(BorderLayout.CENTER, Game.canvasPanel);



        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_RIGHT))
                    Game.cannon.setDirection(e.getKeyCode());
                if (e.getKeyCode() == KeyEvent.VK_SPACE)
                    Game.cannon.shot();
                if(e.getKeyCode() == KeyEvent.VK_Q){
                    Game.pause = !Game.pause;
                }
                /*if(e.getKeyCode() == KeyEvent.VK_W){ //ОТКРЫТЬ ФАЙЛ С ИНСТРУКЦИЯМИ
                    try{
                        Runtime.getRuntime().exec("shell -c " + "/home/artem/IdeaProjects/MySpaceInvaders/control.txt");//ОТКРЫТЬ ФАЙЛИК
                    }
                    catch (IOException k){

                    }
                }*/
            }
            public void keyReleased(KeyEvent e) {
                if ((e.getKeyCode() == KeyEvent.VK_LEFT) || (e.getKeyCode() == KeyEvent.VK_RIGHT));
                Game.cannon.setDirection(0);   //ЧТОБ ОСТАНОВИЛСЯ
            }
        });
    }

    public static void go() {
        //Game.ray.start(Game.cannon.getX(),Game.cannon.getY());
        while (true) {
            try {
                Thread.sleep(SHOW_DELAY);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (!Game.pause) {
                Game.canvasPanel.repaint();
                if (!Game.gameOver) {
                    Game.cannon.move();
                    Game.flash.show();
                    Game.bang.show();
                    Game.ray.fly();
                    Game.rays.fly();
                    Game.wave.nextStep();
                }
                if (Game.wave.isDestroyed()) { // if the wave completely destroyed
                    Game.wave = new Wave();
                    Game.countLives++;
                }
            } else continue;
        }
    }
}

class Canvas extends JPanel { // my canvas for painting
    public void paint(Graphics g) {
        super.paint(g);
        GUI.drawLabels(g);
        Game.bang.paintExplode(g);
        if (!Game.gameOver) {
            Game.cannon.paint(g);
            Game.ray.paint(g);
            Game.wave.paint(g);
            Game.flash.paint(g);
            Game.rays.paint(g);
        }
    }
}