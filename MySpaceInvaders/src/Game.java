import java.util.Random;

/**
 * Created by artem on 25.09.16.
 */

/* 25,09,16
увеличить фрейм в полтора раза по х и в 1.25 по у              ХЗ ЦЕЛЕСООБРАЗНО ЛИ, УДЛИННИТ УРОВНИ
исправить проблему с лэйблами(положение)
доделать кнопку newGame
доделать инструкцию по управлению
*/
/*
Для разных типов пушки можно задать разную скорость перемещения, разное кол-во хп, разную скорость полета снаряда,
мб некоторые смогут стрелять очередями
Или с каждым новым левелом можно апать пушку)
Или замедлять время при нажатии на какую - нибудь кнопку
Или добавить прицел чтоб знать куда снаряд полетит, а прицел 2-го уровня под углом, чтоб знать точно во что снаряд врежется
Или увеличивать скорость и количество лучей пришельцев с каждой новой волной   DONE
Или увеличить кол-во пришельцев в линии или увеличить кол-во линий
* */
public class Game {
    protected static Canvas canvasPanel = new Canvas();
    protected static Cannon cannon = new Cannon();
    protected static Explosion bang = new Explosion();
    protected static Ray ray = new Ray();
    protected static Wave wave = new Wave();
    protected static FlashAlien flash = new FlashAlien();
    protected static AlienRays rays = new AlienRays();
    protected static Random random = new Random();
    protected static boolean gameOver;
    protected static int countScore, countLives = 3;    //ИЗМЕНИТЬ В СЛОЖНОСТИ
    public static boolean pause;


    public static void main(String[] args) {
        new GUI().go();
    }
}
