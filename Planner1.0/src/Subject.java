/**
 * Created by artem on 04.09.16.
 */

import java.util.*;
import java.io.*;
public class Subject {
    public static int countOfSubjects;
    private final String fileName;
    private int problems;
    private int deadLine_Day;
    private int deadLine_Month;
    public double priority;
    private String nameOfSubject;
    public static int currentMonth = new Date().getMonth();
    public static int currentDay = new Date().getDate();
    public float mult = 28 + (currentMonth - 1 + (currentMonth - 1)/8)%2 + 2%(currentMonth - 1) + 1/(currentMonth - 1)*2;

    public Subject(String fileName, String name){
        this.fileName = fileName;
        nameOfSubject = name;
        int[] n = {-1, -1};
        this.setProblems(-1);
        this.setDate(n);

        this.priority = this.setPriority();
        countOfSubjects++;
    }

    public void setProblems(int n){
        if(n == -1) {
            Scanner in = null;
            try {
                in = new Scanner(new File(fileName));
            } catch (IOException e) {
                System.out.println("не удалось открыть файл " + fileName + " для чтения");
            }
            problems = Integer.parseInt(in.nextLine());
            return;
        }
        problems = n;
    }

    public void setDate(int[] n) {
        if (n[0] == -1 || n[1] == -1) {
            Scanner in = null;
            try {
                in = new Scanner(new File(fileName));
            } catch (IOException e) {
                System.out.println("не удалось открыть файл " + fileName + " для чтения");
            }
            in.nextLine();
            deadLine_Day = in.nextInt();
            deadLine_Month = in.nextInt();
            return;
        }
        deadLine_Day = n[0];
        deadLine_Month = n[1];
    }

    public float setPriority() {
        return (getProblems()/((getDeadLine_Month() - currentMonth - 1)
               * mult + (getDeadLine_Day() - currentDay)));
    }

    public String getFileName(){
        return fileName;
    }
    public int getProblems(){
        return problems;
    }
    public int getDeadLine_Day(){
        return deadLine_Day;
    }
    public int getDeadLine_Month(){
        return deadLine_Month;
    }
    public double getPriority(){
        return priority;
    }
    public String getNameOfSubject(){
        return nameOfSubject;
    }
}
