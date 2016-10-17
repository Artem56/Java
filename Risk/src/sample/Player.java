package sample;

import java.util.ArrayList;

/**
 * Created by Artem Solomatin on 14.10.16.
 */
public class Player {
    public final int agressionFactor = 1;
    public int id;
    public int territory;
    public int army;
    public int freeArmy = 70 - 3*territory;
    public Cell[] terr = new Cell[100];
    public String color;

    public Player(int num){
        id = num;
        switch (num){
            case 0 :
                color = "black";
                break;
            case 1 :
                color = "red";
                break;
            case 2 :
                color = "orange";
                break;
            case 3 :
                color = "green";
                break;
            case 4 :
                color = "blue";
                break;
        }
    }

    public int getTerritory() {
        return territory;
    }

    public void setTerritory(int territory) {
        this.territory += territory;
    }

    public int getArmy() {
        setArmy();
        return army;
    }

    public void setArmy() {
        army = 0;
        int i;
        for(i = 0;i < 100;i++) {
            if (terr[i] != null) {
                army += terr[i].cellArmy;
            }/*else{
                GUI.information.setText("Попытка перейти по нулевой ссылке в Player.setArmy()");
            }*/
        }
    }

    public int getFreeArmy() {
        return freeArmy;
    }

    public void setFreeArmy(int freeArmy) {
        this.freeArmy += freeArmy;
    }

    public void move(){

    }
}
