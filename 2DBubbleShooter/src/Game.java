import javax.swing.*;

/**
 * Created by Artem Solomatin on 08.02.17.
 * 2DBubbleShooter
 */

//попробовать использовать время не в наносекундах
    //speed должен зависить от fps, если вдруг я захочу его изменить. Или наоборот
    //? заставить врагов отталкиваться друг от друга
    //!!! можно сделать режим дуэли. Плюшки будут появляться в рандомных местах, немного врагов
    //!!! плохая система замедления, проходить по всем врагам это долго
    //!!! после каждого уровня бонус к очкам за время прохождения
    //закрытие потоков в Saver перенести в finally
    //при стрельбе мышкой снаряды сливаются
    //тип оружия добавить в enum вместо магических цифр
    //можно сделать замедление плюшек, нужно ли?

public class Game {
    public static GamePanel panel = new GamePanel();

    public static void main (String[] args){
        JFrame window = new JFrame("Bubble Shooter");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(panel);

        window.pack();
        window.setResizable(false);
        window.setVisible(true);

    }
}
