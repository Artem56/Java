package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;

/*
1.Сохранять в выбираемый файл, а не в файл по умолчанию
2.Доделать функцию сохранения(она пока что ничего не делает)
3.Добавить ценность каждой плитки
4.На время сна сделать поле недоступным
            GUI.root.setManaged(false);
            ,,,
            GUI.root.setManaged(true);
5.Клеток слишком много, пусть 35-45 будут препятствиями
6.В Cell.getColor() неправильно отрабатывает рандом
9.В 0-й фазе должен быть еще лэбл, показывающий сколько еще можно разместить
10.В Game.play(...) нужно все сделать недоступным после завершения игры
11.В Game.play_ сделать возможность нескольким игрокам играть
12.Game.play(...) не должен вызываться нажатием на кнопку
13.Game.attack(...) Доделать атаку
!!!15.реализовать ИИ
16.В Game.move(...) армия при захвате не должна делиться пополам. Использовать ComboBox
 */

public class GUI extends Application {
    public static ArrayList<Player> players = new ArrayList<>();
    public static int windowX = 1000;
    public static int windowY = 700;
    public static ArrayList<Cell> allTerr = new ArrayList<>();
    public static Scene scene;
    public static Pane root;
    public static Label label1;
    public static Label label2;
    public static Label label3;
    public static Label label4;
    public static Label phaseAndPlayer;
    public static Label information;
    public static Player pl1;
    public static Player pl2;
    public static Player pl3;
    public static Player pl4;
    public static Button nextStep;
    public static Button save;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //go(primaryStage);
        draw(primaryStage);

    }

    public void draw(Stage primaryStage){
        pl1 = new Player(1);
        pl2 = new Player(2);
        pl3 = new Player(3);
        pl4 = new Player(4);
        players.add(pl1);
        players.add(pl2);
        players.add(pl3);
        players.add(pl4);

        primaryStage.setTitle("Risk");
        primaryStage.setWidth(windowX);
        primaryStage.setHeight(windowY);
        primaryStage.setResizable(false);

        root = new Pane();
        scene = new Scene(root);
        primaryStage.setScene(scene);

        int xNum, yNum;
        for(yNum = 0;yNum < 10; yNum++){     //строка
            for(xNum = 0;xNum < 10;xNum++){  //столбец
                Cell cell = new Cell(xNum*50 + 40, yNum*50 + 60);
                cell.setxNum(xNum);
                cell.setyNum(yNum);
                allTerr.add(cell);
                switch (cell.owner){
                    case 1 :
                        pl1.terr.add(cell);
                        break;
                    case 2 :
                        pl2.terr.add(cell);
                        break;
                    case 3 :
                        pl3.terr.add(cell);
                        break;
                    case 4 :
                        pl4.terr.add(cell);
                        break;
                }
            }
        }

        label1 = new Label("Territory: " + pl1.getTerritory() + "    " + "Army: " + (pl1.getArmy() + pl1.getFreeArmy()));
        //System.out.println(pl1.getArmy());
        label1.setTextFill(Color.RED);
        label1.setTranslateX(670);
        label1.setTranslateY(50);
        label1.setFont(new Font("Verdana", 16));

        label2 = new Label("Territory: " + pl2.getTerritory() + "    " + "Army: " + (pl2.getArmy() + pl2.getFreeArmy()));
        label2.setTextFill(Color.ORANGE);
        label2.setTranslateX(670);
        label2.setTranslateY(200);
        label2.setFont(new Font("Verdana", 16));

        label3 = new Label("Territory: " + pl3.getTerritory() + "    " + "Army: " + (pl3.getArmy() + pl3.getFreeArmy()));
        label3.setTextFill(Color.GREEN);
        label3.setTranslateX(670);
        label3.setTranslateY(350);
        label3.setFont(new Font("Verdana", 16));

        label4 = new Label("Territory: " + pl4.getTerritory() + "    " + "Army: " + (pl4.getArmy() + pl4.getFreeArmy()));
        label4.setTextFill(Color.BLUE);
        label4.setTranslateX(670);
        label4.setTranslateY(500);
        label4.setFont(new Font("Verdana", 16));

        information = new Label("");
        information.setTranslateX(100);
        information.setTranslateY(605);
        information.setFont(new Font("Verdana", 16));

        phaseAndPlayer = new Label("Player: " + Game.currentPlayer + "            " + "Phase: " + (Game.phase + 1));
        phaseAndPlayer.setTranslateX(300);
        phaseAndPlayer.setTranslateY(10);
        phaseAndPlayer.setFont(new Font("Verdana", 20));

        nextStep = new Button("Next step");
        nextStep.setTranslateX(680);
        nextStep.setTranslateY(580);
        nextStep.setPrefSize(120, 70);
        nextStep.setOnAction(event -> {
            Game.nextStep();
        });
        save = new Button("Save");
        save.setTranslateX(810);
        save.setTranslateY(580);
        save.setPrefSize(120, 70);
        save.setOnAction(event -> {
            Game.save();
        });


        root.getChildren().addAll(allTerr);
        root.getChildren().addAll(label1, label2, label3, label4, phaseAndPlayer, information);
        root.getChildren().addAll(nextStep, save);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}