package sample;

/**
 * Created by Artem Solomatin on 16.10.16.
 */
public class ComputerBehavior {
    public static void play0(int currentPlayer) {//сейчас играет GUI.players.get(currentPlayer - 1)
        Player comp = GUI.players.get(currentPlayer - 1);
            while (comp.freeArmy != 0) {
                int rand = (int)(Math.random() * comp.territory);
                comp.terr.get(rand).setCellArmy(1);
                comp.terr.get(rand).setText("" + comp.terr.get(rand).getCellArmy());
                comp.freeArmy--;
        }
        Game.phase++;
        Game.rewriter();
    }

    public static void play1(int phase, int currentPlayer){
        if(phase == 1){  //АТАКА

        }

        if(phase == 2){  //ПЕРЕМЕЩЕНИЕ

        }
        Game.phase++;
    }
}
