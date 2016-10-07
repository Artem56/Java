/**
 * Created by artem on 04.09.16.
 */

import java.util.*;
public class Runner {
    private static int dayViewer(Subject sub) {//сколько дней осталось
        return (int) (sub.getProblems()/sub.setPriority());
    }

    private static Subject PriorityMaker(ArrayList<Subject> arr) {
        Subject tmp = arr.get(0);
        for (Subject sub : arr) {
            sub.priority = sub.setPriority();
            if (sub.getPriority() > tmp.getPriority())
                tmp = sub;
        }
        return tmp;
    }

    public static void Information() {
        Subject math = new Subject("Math.txt", "мат. анализ");
        Subject phys = new Subject("Phys.txt", "физика");
        Subject diff = new Subject("Diff.txt", "диффуры");
        Subject tmech = new Subject("TMech.txt", "теормех");
        ArrayList<Subject> arr = new ArrayList<>();
        arr.add(math);
        arr.add(phys);
        arr.add(diff);
        arr.add(tmech);
        for (Subject sub : arr) {
            MyGUI.output.append("Осталось:\n");
            MyGUI.output.append("дней " + dayViewer(sub));
            MyGUI.output.append("  задач " + sub.getProblems());
            MyGUI.output.append("  по " + sub.getFileName() + "\n");
            MyGUI.output.append("\n");
        }
    }

    public static void  Chooser() {
        Subject math = new Subject("Math.txt", "мат. анализ");
        Subject phys = new Subject("Phys.txt", "физика");
        Subject diff = new Subject("Diff.txt", "диффуры");
        Subject tmech = new Subject("TMech.txt", "теормех");
        ArrayList<Subject> arr = new ArrayList<>();
        arr.add(math);
        arr.add(phys);
        arr.add(diff);
        arr.add(tmech);

        Subject prior = PriorityMaker(arr);
        double sumOfProblems = 0;

        for (Subject sub : arr) {
            sumOfProblems += sub.getPriority();
        }
        for (Subject sub : arr) {
            if (dayViewer(sub) == 1 || dayViewer(sub) == 0) {
                MyGUI.output.append("Сделай все задачи по предмету " + prior.getNameOfSubject() + "\n");
            }
            while (sumOfProblems != 0) {
                if (prior.getProblems() > sumOfProblems) {
                    MyGUI.output.append("Сделай " + Math.round(sumOfProblems) + " задач по предмету " + prior.getNameOfSubject() + "\n");
                    sumOfProblems = 0;
                } else {
                    MyGUI.output.append("Сделай " + prior.getProblems() + " задач по предмету " + prior.getNameOfSubject() + "\n");
                    sumOfProblems = sumOfProblems - prior.getProblems();
                    prior.setProblems(0);
                    prior = PriorityMaker(arr);
                }
            }
        }
    }
}

