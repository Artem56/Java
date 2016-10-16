package sample;

import javafx.scene.control.Button;

/**
 * Created by Artem Solomatin on 14.10.16.
 */

public class Cell extends Button {
    private int xNum;
    private int yNum;
    public int owner;
    public int cellArmy = 1;
    public int xPos;
    public int yPos;
    public String cellColor;
    public Cell(int xPos, int yPos) {
        super();
        this.xPos = xPos;            //ХЗ НУЖНО ЛИ
        this.yPos = yPos;
        owner = (int)((Math.random()*4 + 1));

        setText(Integer.toString(cellArmy));
        setColor(getColor());

        setTranslateX(xPos);
        setTranslateY(yPos);
        setPrefSize(51, 51);
        setOnMouseClicked(event -> {
            Game.play(xNum, yNum);
        });
    }

    public void setColor(String color){
        cellColor = color;
        setStyle("-fx-background-color: " + color);
    }
    private String getColor(){
        switch (owner){
            case 0 :
                return "gray";
            case 1 :
                GUI.pl1.setTerritory(1);
                cellColor = "red";
                return "red";
            case 2 :
                GUI.pl2.setTerritory(1);
                cellColor = "orange";
                return "orange";
            case 3 :
                GUI.pl3.setTerritory(1);
                cellColor = "green";
                return "green";
            case 4 :
                GUI.pl4.setTerritory(1);
                cellColor = "blue";
                return "blue";

            default : return "black";
        }
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public int getOwner() {
        return owner;
    }

    public void setCellArmy(int army) {
        this.cellArmy += army;
    }

    public int getCellArmy() {
        return cellArmy;
    }

    public void setxNum(int xNum) {
        this.xNum = xNum;
    }

    public int getxNum() {
        return xNum;
    }

    public void setyNum(int yNum) {
        this.yNum = yNum;
    }

    public int getyNum() {
        return yNum;
    }

    public void attack(int number) {
        if (Game.phase == 0) {               //РАССТАНОВКА
            GUI.information.setText("В этом режиме нельзя нападать или перемещать войска");
        }
        if (Game.phase == 1) {               //АТАКА
            if (GUI.allTerr.get(xNum + 10 * yNum).getOwner() != GUI.allTerr.get(number).getOwner()) {
                GUI.allTerr.get(number).setText("атакован");
                GUI.information.setText("");
            } else {
                GUI.information.setText("В этом режиме нельзя перемещать войска");
            }
        }
        if(Game.phase == 2){                 //ПЕРЕМЕЩЕНИЕ
            if (GUI.allTerr.get(xNum + 10 * yNum).getOwner() == GUI.allTerr.get(number).getOwner()) {
                GUI.allTerr.get(number).setText("перемещен");
                GUI.information.setText("");
            } else {
                GUI.information.setText("В этом режиме нельзя нападать");
            }
        }
    }
}

