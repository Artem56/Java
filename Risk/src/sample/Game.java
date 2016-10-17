package sample;

/**
 * Created by Artem Solomatin on 15.10.16.
 */
public class Game {
    public static int phase = 0;   //0 - расстановка  1 - атака   2 - перемещение
    public static int currentPlayer = 1;
    public static boolean gameOver;
    public static int winner;
    //public static int num;

    public static void nextStep(){
        if(phase < 2){
            phase++;
            //System.out.println(GUI.pl1.getArmy() + GUI.pl1.getFreeArmy());
        }else{            //начнем новый ход
            phase = 0;
            if(currentPlayer == 4){
                currentPlayer = 1;
                for(Player tmp : GUI.players){
                    tmp.freeArmy += Math.round(tmp.getTerritory()*1.0/3);
                }
            }else {
                currentPlayer++;
            }
            GUI.phaseAndPlayer.setText("Игрок " + currentPlayer + " готовится");
            /*try {
                Thread.sleep(1000);
            }catch(InterruptedException e){
                System.out.println("Что-то пошло не так во время сна(");
            }*/
        }
        rewriter();
    }

    public static void save(){  //запись в файл
    }

    public static void rewriter(){   //Для перерисовки изменяющихся эл-в
        GUI.phaseAndPlayer.setText("Player: " + Game.currentPlayer + "            " + "Phase: " + (phase + 1));
        GUI.label1.setText("Territory: " + GUI.pl1.getTerritory() + "    " + "Army: " + (GUI.pl1.getArmy() + GUI.pl1.getFreeArmy()));
        GUI.label2.setText("Territory: " + GUI.pl2.getTerritory() + "    " + "Army: " + (GUI.pl2.getArmy() + GUI.pl2.getFreeArmy()));
        GUI.label3.setText("Territory: " + GUI.pl3.getTerritory() + "    " + "Army: " + (GUI.pl3.getArmy() + GUI.pl3.getFreeArmy()));
        GUI.label4.setText("Territory: " + GUI.pl4.getTerritory() + "    " + "Army: " + (GUI.pl4.getArmy() + GUI.pl4.getFreeArmy()));
    }
    public static void play(int xNum, int yNum){

        for(Player tmp : GUI.players){   //Победил ли кто-нибудь
            if(tmp.getTerritory() == 100){
                gameOver = true;
                winner = tmp.id;
                return;
            }
        }
        if(Game.gameOver == true){
            GUI.information.setText("Winner is player № " + Game.winner);
            //ЗДЕСЬ НУЖНО ВСЕ СДЕЛАТЬ НЕДОСТУПНЫМ
            return;
        }
        if(phase == 0){
            if (GUI.players.get(currentPlayer - 1).equals(GUI.pl1)) { //еси играет человек
                play0(xNum, yNum);
            }else{
                ComputerBehavior.play0(currentPlayer);
            }
        }if(phase == 1){
            if (GUI.players.get(currentPlayer - 1).equals(GUI.pl1)) { //еси играет человек
                play1(xNum, yNum, 1);
            }else{
                ComputerBehavior.play1(1, currentPlayer);
            }
        }if(phase == 2){
            if (GUI.players.get(currentPlayer - 1).equals(GUI.pl1)) { //еси играет человек
                play1(xNum, yNum, 2);
            }else{
                ComputerBehavior.play1(2, currentPlayer);
            }
        }
        //GUI.information.setText(GUI.allTerr.get(num).getCellArmy() + " " + GUI.allTerr.get(xNum + 10 * yNum).getCellArmy());
    }

    public static void play0(int xNum, int yNum) {
        //GUI.information.setText("Разместите " + current.getFreeArmy() + " отрядов на своей территории");

        if(GUI.pl1.getFreeArmy() != 0) {                                     //если еще можно добавлять
            if(GUI.allTerr.get(xNum + 10 * yNum).getOwner() == GUI.pl1.id) { //своя ли территория
                GUI.information.setText("Разместите еще " + GUI.pl1.getFreeArmy() + " отрядов на своей территории");
                GUI.allTerr.get(xNum + 10 * yNum).setCellArmy(1);
                GUI.pl1.setFreeArmy(-1);

                String str = GUI.allTerr.get(xNum + 10 * yNum).getText();  //перерисуем
                GUI.allTerr.get(xNum + 10 * yNum).setText(Integer.toString(Integer.parseInt(str) + 1));
                rewriter();
            }else{
                GUI.information.setText("Размещать отряды можно только на своей территории");
            }
        }else{
            GUI.information.setText("Все войска размещены. Нажмите Next step");
        }
    }

    public static void play1(int xNum, int yNum, int phase){
        if(GUI.allTerr.get(xNum + 10 * yNum).getOwner() == GUI.pl1.id) { //своя ли территория кликнута
            GUI.scene.setOnKeyReleased(event -> {
                switch (event.getCode()){
                    case UP:
                        move(xNum, yNum, xNum + 10*(yNum - 1), phase);
                        break;
                    case LEFT:
                        move(xNum, yNum, xNum - 1 + 10*yNum, phase);
                        break;
                    case RIGHT:
                        move(xNum, yNum, xNum + 1 + 10*yNum, phase);
                        break;
                    case DOWN:
                        move(xNum, yNum, xNum + 10*(yNum + 1), phase);
                        break;
                }
            });
        }else{
            GUI.information.setText("Атаковать или перемещаться можно только со своей территории");
        }
    }

    public static void move(int xNum, int yNum, int number, int phase) {
        //int atArmy = GUI.players.get(GUI.allTerr.get(xNum + 10 * yNum).getOwner() - 1).getArmy();
        //int defArmy =  GUI.players.get(GUI.allTerr.get(number).getOwner() - 1).getArmy();
        Cell attackerCell = GUI.allTerr.get(xNum + 10 * yNum);
        //System.out.println(number);
        Cell defenderCell = GUI.allTerr.get(number);

        if (phase == 1) {                        //АТАКА
            //System.out.println("Фаза 2 начало " + defenderCell.getCellArmy());
            if (attackerCell.getOwner() != defenderCell.getOwner()) {
                if(attackerCell.getCellArmy() > 1) {
                    //ПРОЦЕСС АТАКИ(борьба, проверка на захват)
                    int attackCount = 0;   //Для ничьей
                    while ((attackerCell.getCellArmy() > 0) && (defenderCell.getCellArmy() > 0)
                            && (attackCount < 5)) {  //бой
                        attackCount++;
                        attackerCell.setCellArmy((int) Math.round(-0.1 * (Math.random() * 7 + 1) //атака защитников
                                * defenderCell.getCellArmy()));
                        defenderCell.setCellArmy((int) Math.round(-0.1 * (Math.random() * 7 + 1)           //атака нападающих
                                * attackerCell.getCellArmy()));
                    }
                        if (defenderCell.getCellArmy() <= 0) {  //победа нападающего
                            /*System.out.println("atArmy " + atArmy + "defArmy" + defArmy);
                            System.out.println("at ostalos" + GUI.players.get(attackerCell.getOwner() - 1).getArmy() + "def ostalos" + GUI.players.get(defenderCell.getOwner() - 1).getArmy());
                            System.out.println((atArmy - GUI.players.get(attackerCell.getOwner() - 1).getArmy()) + "" + (defArmy - GUI.players.get(defenderCell.getOwner() - 1).getArmy()));
                            GUI.information.setText("Игрок " + attackerCell.getOwner() + " победил, " +
                                    "потеряв " + (atArmy - GUI.players.get(attackerCell.getOwner() - 1).getArmy())
                                    + " отрядов.\nИгрок " + defenderCell.getOwner() + " потерял " +
                                    (defArmy - GUI.players.get(defenderCell.getOwner() - 1).getArmy()) + " отрядов");*/

                            //установим армию
                            defenderCell.cellArmy = attackerCell.getCellArmy() - 1;
                            GUI.players.get(attackerCell.getOwner() - 1).setArmy();


                            //GUI.information.setText(defenderCell.getCellArmy() + " " + attackerCell.getCellArmy());

                            //Внесем изменения в значения игроков
                            GUI.players.get(attackerCell.getOwner() - 1).setTerritory(1);
                            GUI.players.get(defenderCell.getOwner() - 1).setTerritory(-1);
                            GUI.players.get(attackerCell.getOwner() - 1).terr[number] = defenderCell;
                            GUI.players.get(defenderCell.getOwner() - 1).terr[number] = null;

                            //изменим владельца, цвет и армию
                            defenderCell.setOwner(attackerCell.getOwner());
                            defenderCell.setColor(attackerCell.cellColor);
                        }
                            //перепишем армию в любом случае
                            attackerCell.cellArmy = 1;   //пока что она в любом случае будет равна единице
                            //GUI.players.get(attackerCell.getOwner() - 1).setArmy();
                            if(defenderCell.getCellArmy() <= 0){  //если вдруг защищающаяся клетка обнулилась
                                defenderCell.cellArmy = 1;
                                //GUI.players.get(defenderCell.getOwner() - 1).setArmy();
                            }
                            GUI.players.get(defenderCell.getOwner() - 1).setArmy();//Нужно ли????????????
                            GUI.players.get(attackerCell.getOwner() - 1).setArmy();//Нужно ли????????????

                            //перепишем текст на ячейках в любом случае
                            defenderCell.setText("" + defenderCell.getCellArmy());
                            attackerCell.setText("" + attackerCell.getCellArmy());
                }else{
                    GUI.information.setText("Недостаточно войск для атаки");
                }
            } else {
                GUI.information.setText("В этом режиме нельзя перемещать войска");
            }
            //System.out.println("Фаза 1 конец " + defenderCell.getCellArmy());
        }

        if(phase == 2){                          //ПЕРЕМЕЩЕНИЕ
            //System.out.println("Фаза 2 " + defenderCell.getCellArmy());
            //GUI.information.setText(GUI.allTerr.get(number).getCellArmy() + " " + GUI.allTerr.get(xNum + 10 * yNum).getCellArmy());
            if (attackerCell.getOwner() == defenderCell.getOwner()) {
                if(attackerCell.getCellArmy() > 1) {  //Достаточно ли войск
                    defenderCell.setCellArmy(1);
                    attackerCell.setCellArmy(-1);
                    //defenderCell.setCellArmy(1);                   //ПЕРЕДЕЛАТЬ С COMBO BOX
                    //attackerCell.setCellArmy(-1);
                    //перепишем текст на ячейках
                    defenderCell.setText("" + defenderCell.getCellArmy());
                    attackerCell.setText("" + attackerCell.getCellArmy());
                }else{
                    GUI.information.setText("Недостаточно отрядов для перемещения");
                }
            } else {
                GUI.information.setText("В этом режиме нельзя атаковать");
            }
        }
        rewriter();
    }
}
