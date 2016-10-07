import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.*;
import java.util.Date;

/**
 * Created by artem on 12.09.16.
 */
public class GUI  extends Application {
    protected static int windowX = 800;
    protected static int windowY = 500;
    public static Player pl1;
    public static Player pl2;
    protected static Label rightLabel;
    protected static Label leftLabel;
    protected static Label rightScore;
    protected static Label leftScore;
    protected static Label centerLabel;
    protected static Label rightSeries;
    protected static Label leftSeries;
    protected Label centerScore;
    protected static Label centerSeries;
    public Label leftPicture;
    protected Label rightPicture;

    public void start(Stage primaryStage){
        pl1 = new Player("Right player");
        pl2 = new Player("Left Player");
        draw(primaryStage);

    }

    public void draw(Stage primaryStage) {
        Pane root = new Pane();

        //System.out.println("aaaaaaaaa");
        rightLabel = new Label(pl1.getAim());
        rightLabel.setTranslateX(0.8125 * windowX);
        rightLabel.setTranslateY(0.7 * windowY);
        rightLabel.setFont(new Font("Verdana", 32));
        rightScore = new Label("0");
        rightScore.setTranslateX(0.6875 * windowX);
        rightScore.setTranslateY(0.4*windowY);
        rightScore.setFont(new Font("Verdana", 34));
        rightSeries = new Label(Integer.toString(pl1.getSeries()));
        rightSeries.setTranslateX(0.80625 * windowX);
        rightSeries.setTranslateY(0.18 * windowY);
        rightSeries.setFont(new Font("Verdana", 22));

        leftLabel = new Label(pl2.getAim());
        leftLabel.setTranslateX(0.1091 * windowX);
        leftLabel.setTranslateY(0.7 * windowY);
        leftLabel.setFont(new Font("Verdana", 32));
        leftScore = new Label("0");
        leftScore.setTranslateX(0.2875 * windowX);
        leftScore.setTranslateY(0.4 * windowY);
        leftScore.setFont(new Font("Verdana", 34));
        leftSeries = new Label(Integer.toString(pl2.getSeries()));
        leftSeries.setTranslateX(0.1875 * windowX);
        leftSeries.setTranslateY(0.18 * windowY);
        leftSeries.setFont(new Font("Verdana", 22));

        centerLabel = new Label("Let's start");
        centerLabel.setTranslateX(0.65 * windowY);
        centerLabel.setFont(new Font("Verdana", 30));
        centerLabel.setOnMouseEntered(event -> {
            centerLabel.setScaleX(1.2);
            centerLabel.setScaleY(1.2);
            centerLabel.setTextFill(Color.GRAY);
        });
        centerLabel.setOnMouseExited(event -> {
            centerLabel.setScaleX(1);
            centerLabel.setScaleY(1);
            centerLabel.setTextFill(Color.BLACK);
        });
        centerLabel.setOnMouseClicked(event -> {
            rightPicture = Game.setImage();
        });
        centerScore = new Label("Текущий счет");
        centerScore.setTranslateX(0.4125 * windowX);
        centerScore.setTranslateY(0.4 * windowY);
        centerScore.setFont(new Font("Verdana", 20));
        centerSeries = new Label("Текущая серия");
        centerSeries.setTranslateX(0.40625 * windowX);
        centerSeries.setTranslateY(0.18 * windowY);
        centerSeries.setFont(new Font("Verdana", 20));

        Line line = new Line();
        line.setStartX(0.5 * windowX);
        line.setStartY(0.6 * windowY);
        line.setEndX(0.5 * windowX);
        line.setEndY(windowY);

        Button newGame = new Button("New game");
        newGame.setTranslateX(0.4 * windowX);
        newGame.setTranslateY(0.48 * windowY);
        newGame.setPrefSize(0.2 * windowX, 0.12 * windowY);
        newGame.setOnAction(event -> {
            pl1 = new Player("Right player");
            pl2 = new Player("Left Player");
            draw(primaryStage);
        });

        rightPicture = Game.setImage();
        rightPicture.setTranslateX(0.8125 * windowX);
        rightPicture.setTranslateY(0.36 * windowY);
        leftPicture = Game.setImage();
        leftPicture.setTranslateX(0.025 * windowX);
        leftPicture.setTranslateY(0.36 * windowY);

        root.getChildren().addAll(rightLabel, rightScore, leftLabel, leftScore, centerLabel, newGame, line,
                rightSeries, leftSeries, centerScore, centerSeries, leftPicture, rightPicture);
        Scene scene = new Scene(root, windowX, windowY);
        //System.out.println(scene.getHeight());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("My Game)");
        primaryStage.show();
        scene.setOnKeyReleased(e -> Game.play(e));   //что это значит
    }
}
