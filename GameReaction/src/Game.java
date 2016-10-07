import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Created by artem on 11.09.16.
 */
public class Game {
    private static int engaged;
    private static int numberOfPhotos = 6;

    public static int createAim(){
        return (int)(Math.random() * 4);
    }

    public static Label setImage(){
        int random = -1;
        do {  //чтоб не повторялись
            random = (int) (Math.random() * numberOfPhotos + 1);
        }
        while(engaged == random);

        engaged = random;
        Image img = new Image("pic" + random + ".jpg");
        ImageView image = new ImageView(img);
        image.setFitWidth(GUI.windowX * 0.1625);
        image.setFitHeight(0.26 * GUI.windowY);
        Label imgl = new Label();
        imgl.setGraphic(image);

        return imgl;
    }

    public static void play(javafx.scene.input.KeyEvent e) {
        //Date startTime = new Date();
        switch (e.getCode()) {
            case W:
                attempt(GUI.pl2, 0);
                break;
            case A:
                attempt(GUI.pl2, 1);
                break;
            case D:
                attempt(GUI.pl2, 3);
                break;
            case S:
                attempt(GUI.pl2, 2);
                break;
            case UP:
                attempt(GUI.pl1, 0);
                break;
            case LEFT:
                attempt(GUI.pl1, 1);
                break;
            case RIGHT:
                attempt(GUI.pl1, 3);
                break;
            case DOWN:
                attempt(GUI.pl1, 2);
                break;
        }

    }

    private static void attempt(Player pl, int answer/*, Date startTime*/){
        //System.out.println(pl.getBestSeries());
        if(pl.aim == answer){                                  //дан верный ответ
            //pl.changeScore(new Date().getSeconds() - startTime.getSeconds());
            pl.changeScore(1);
            pl.changeSeries(true);
            GUI.centerLabel.setText(pl.getName() + " gives the right answer!");
            GUI.centerLabel.setTranslateX(0.175 * GUI.windowX);
            if(pl.winner == 2) {
                GUI.centerLabel.setScaleX(1.6);
                GUI.centerLabel.setScaleY(1.3);
                GUI.centerLabel.setText(pl.getName() + " is a winner!!!");
                GUI.pl1.setBestSeries();
                GUI.pl2.setBestSeries();
                GUI.centerSeries.setText("The best series is " + (GUI.pl1.getBestSeries() > GUI.pl2.getBestSeries()
                        ? GUI.pl1.getBestSeries() + "\nof the right player" : GUI.pl2.getBestSeries() +
                        "\nof the left player"));
                //frame.setFocusable(false);
            }
            pl.setAim();

            if(pl.equals(GUI.pl1)){
                GUI.rightLabel.setText(GUI.pl1.getAim());
                GUI.rightScore.setText(Integer.toString(GUI.pl1.getScore()));
                GUI.rightSeries.setText(Integer.toString(GUI.pl1.getSeries()));
            }else {
                GUI.leftLabel.setText(GUI.pl2.getAim());
                GUI.leftScore.setText(Integer.toString(GUI.pl2.getScore()));
                GUI.leftSeries.setText(Integer.toString(GUI.pl2.getSeries()));
            }
        }else{
            GUI.centerLabel.setText(pl.getName() + " make a mistake");
            GUI.centerLabel.setTranslateX(0.225 * GUI.windowX);
            pl.changeScore(-2); //нужно снимать пять процентов счета за ошибку
            pl.setBestSeries();
            pl.changeSeries(false);
            GUI.rightScore.setText(Integer.toString(GUI.pl1.getScore()));
            GUI.leftScore.setText(Integer.toString(GUI.pl2.getScore()));
            GUI.rightSeries.setText(Integer.toString(GUI.pl1.getSeries()));
            GUI.leftSeries.setText(Integer.toString(GUI.pl2.getSeries()));

        }
    }
}
