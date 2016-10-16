package sample;

import java.util.ArrayList;

/**
 * Created by Artem Solomatin on 14.10.16.
 */
public class Player {
    public int id;
    public int territory;
    public int army;
    public int freeArmy = 70 - 3*territory;
    public ArrayList<Cell> terr = new ArrayList<>();
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
        for(Cell cell : terr){
            army += cell.cellArmy;
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
