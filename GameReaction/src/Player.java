/**
 * Created by artem on 11.09.16.
 */
public class Player {
    private double score;
    private String name;
    protected int aim;
    protected int winner = 1;  //0 - проиграл, 1 - играет, 2 - выйграл  ДОДЕЛАТЬ ПРОИГРЫШ ПРИ ДОСТИЖЕНИИ SCORE = -20!!!
    private int series;
    private int bestSeries;

    public Player(String s){
        name = s;
        setAim();
    }

    public void setBestSeries(){
        if(series > bestSeries) bestSeries = series;
    }

    public int getBestSeries(){
        return bestSeries;
    }

    public void changeSeries(boolean gotcha){
        if(gotcha) series++;
        else series = 0;
    }
    public int getSeries(){
        return series;
    }

    public String getAim(){
        String[] arr = new String[]{"UP   ", "LEFT ", "DOWN ", "RIGHT"};
        return arr[aim];
    }
    public void setAim(){
        aim = Game.createAim();
    }
    public String getName(){
        return name;
    }
    public int getScore(){
        return (int)score;
    }
    public void changeScore(double d){
        score += d;
        if(score >= 20) winner = 2;
        if(score <= -20) winner = 0;
    }
}
