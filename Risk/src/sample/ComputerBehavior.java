package sample;

/**
 * Created by Artem Solomatin on 16.10.16.
 */
public class ComputerBehavior {
    public static void play0(int currentPlayer) {//сейчас играет GUI.players.get(currentPlayer - 1)
        Player comp = GUI.players.get(currentPlayer - 1);
            while (comp.freeArmy != 0) {
                int rand = (int)(Math.random() * 100);
                if(comp.terr[rand] != null) {
                    comp.terr[rand].setCellArmy(1);
                    comp.terr[rand].setText("" + comp.terr[rand].getCellArmy());
                    comp.freeArmy--;
                }else{
                    continue;
                }
        }
        Game.phase++;
        Game.rewriter();
    }

    public static void play1(int phase, int currentPlayer){
        int i;
        Player comp = GUI.players.get(currentPlayer - 1);
        if(phase == 1){  //АТАКА
            for(i = 0;i < 100;i++) {  //пройдем по своим ячейкам
                if (comp.terr[i] != null) {
                    int xNum = i % 10;
                    int yNum = i / 10;
                    compPlay1(xNum, yNum, 1, comp);
                }
            }
        }
        Game.phase++;
        if(phase == 2){  //ПЕРЕМЕЩЕНИЕ

        }
        Game.rewriter();
        Game.phase++;
    }

    public static void compPlay1(int xNum, int yNum, int phase, Player comp){
        if(GUI.allTerr.get(xNum + 10 * yNum).getOwner() == comp.id) { //своя ли территория кликнута
            int vector;
            for(vector = 0;vector < 4;vector++) {      //Он не должен нападать по всем территориям по всем направлениям
                switch (vector) {
                    case 0:  //UP
                        if((yNum > 0) && (xNum + 10 * (yNum - 1) < 100)) {
                            if (decision(xNum, yNum, xNum + 10 * (yNum - 1), comp)) {
                                Game.move(xNum, yNum, xNum + 10 * (yNum - 1), phase);
                                break;
                            }
                        }
                    case 1:   //LEFT
                        if(((xNum > 0) || (yNum > 0)) && (xNum - 1 + 10 * yNum < 100)) {
                            if (decision(xNum, yNum, xNum - 1 + 10 * yNum, comp)) {
                                Game.move(xNum, yNum, xNum - 1 + 10 * yNum, phase);
                                break;
                            }
                        }
                    case 2:   //RIGHT
                        if(xNum + 1 + 10 * yNum < 100) {
                            if (decision(xNum, yNum, xNum + 1 + 10 * yNum, comp)) {
                                Game.move(xNum, yNum, xNum + 1 + 10 * yNum, phase);
                                break;
                            }
                        }
                    case 3:    //DOWN
                        if(xNum + 10 * (yNum + 1) < 100) {
                            if (decision(xNum, yNum, xNum + 10 * (yNum + 1), comp)) {
                                Game.move(xNum, yNum, xNum + 10 * (yNum + 1), phase);
                                break;
                            }
                        }
                }
            }
        }else{
            GUI.information.setText("Атаковать или перемещаться можно только со своей территории");
        }
        Game.rewriter();
    }

    public static boolean decision(int xNum, int yNum, int number, Player comp){
        Cell attackerCell = GUI.allTerr.get(xNum + 10 * yNum);
        Cell defenderCell = GUI.allTerr.get(number);
        if((Math.random() * comp.agressionFactor) < ((attackerCell.getCellArmy() * 1.0)/(attackerCell.getCellArmy() + defenderCell.getCellArmy()))){
            return true;
        }else{
            return false;
        }
    }
}
