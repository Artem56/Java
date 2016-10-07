/**
 * Created by artem on 05.09.16.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class MyGUI {
    public static JFrame frame;
    public JTextField input_problems;
    public static JTextArea output;
    public JPanel panel;
    public JPanel input_panel;
    public JComboBox comboBox;
    public JScrollPane scroll;
    private String fileName = "Math.txt";

    public static void main(String[] args){
        MyGUI gui = new MyGUI();
        gui.go();
    }

    public void go() {
        input_problems = new JTextField(20);
        input_problems.setEditable(true);

        output = new JTextArea(8, 80);

        scroll = new JScrollPane(output);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        frame = new JFrame("Planner 1.0");
        panel = new JPanel();
        input_panel = new JPanel();

        String[] items = {
                "Матан",
                "Физика",
                "Диффуры",
                "Теормех"
        };
        comboBox = new JComboBox(items);
        comboBox.setEditable(true);
        comboBox.addActionListener(new comboListener());

        output.append("Нажмите Get problems для получения информации о оставшихся задачах и дедлайнах.\n" + "\n" +
                "Нажмите Decrease/Increase для изменения файла.\nДля этого введите название предмета и кол-во " +
                "решенных задач в нижние поля.\n" + "\n" + "Нажмите Get aim для получения задач на сегодня.\n" + "\n");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(800, 500);

        JButton aimButton = new JButton("Get aim");
        aimButton.addActionListener(new aimListener());
        JButton editButton = new JButton("Decrease problems");
        editButton.addActionListener(new editListener());
        JButton addButton = new JButton("Increase problems");
        addButton.addActionListener(new addListener());
        JButton getProblemsButton = new JButton("Get problems");
        getProblemsButton.addActionListener(new getProblemsListener());
        JButton weekButton = new JButton("next week");
        weekButton.addActionListener(new weekListener());

        panel.add(aimButton);
        panel.add(getProblemsButton);
        panel.add(editButton);
        panel.add(addButton);
        panel.add(weekButton);

        input_panel.add(comboBox);
        input_panel.add(input_problems);

        panel.setLayout(new GridLayout(6,2));
        frame.getContentPane().add(BorderLayout.EAST, panel);
        frame.getContentPane().add(BorderLayout.CENTER, scroll);
        frame.getContentPane().add(BorderLayout.SOUTH, input_panel);
    }

    class aimListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            output.setText("Нажмите Get problems для получения информации о оставшихся задачах и дедлайнах.\n" +
                    "Нажмите Decrease/Increase для изменения файла. Для этого введите название предмета и кол-во " +
                    "решенных задач в нижние поля.\nНажмите Get aim для получения задач на сегодня.\n" + "\n");
            Runner.Chooser();  //только цель
        }
    }

    class weekListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            rewriter(true, false);  //увеличить дату
        }
    }

    class getProblemsListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            output.setText("Нажмите Get problems для получения информации о оставшихся задачах и дедлайнах.\n" +
                    "Нажмите Decrease/Increase для изменения файла. Для этого введите название предмета и кол-во " +
                    "решенных задач в нижние поля.\nНажмите Get aim для получения задач на сегодня.\n" + "\n");
            Runner.Information();   //вся информация
        }
    }

    class comboListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            switch (comboBox.getSelectedIndex()) {
                case 0:
                    fileName = "Math.txt";
                    break;
                case 1:
                    fileName = "Phys.txt";
                    break;
                case 2:
                    fileName = "Diff.txt";
                    break;
                case 3:
                    fileName = "TMech.txt";
                    break;
            }
        }
    }

    class addListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            rewriter(true, true); //значит увеличить задачи
        }
    }

    private void rewriter(boolean sign, boolean problemsORdate) {  //перезаписывает файл с учетом знака(уменьшит или увеличит)   true = задачи
        String date = null;
        int oldNumber = 0, number = 0;
        Scanner in = null;

            try {
                in = new Scanner(new File(fileName));
                oldNumber = Integer.parseInt(in.nextLine());
                date = in.nextLine();
            } catch (FileNotFoundException e) {
                output.append("can't open the file" + fileName);
            }

            if(problemsORdate) {
                try { //получаем задачи из ввода
                    number = Integer.parseInt(input_problems.getText());
                } catch (NumberFormatException e) {
                    output.append("\nНеправильный ввод.\n");
                    return;
                }

                try {   //перезаписываем файл
                    FileWriter writer = new FileWriter(fileName);
                    if (!sign) {
                        int tmp = oldNumber - number;
                        if (tmp >= 0) {
                            writer.write(Integer.toString(tmp));
                            writer.write("\n");
                            writer.write(date);
                            writer.flush();
                            output.append("Изменения внесены\n" + "\n");
                        } else {
                            writer.write(Integer.toString(oldNumber));
                            writer.write("\n");
                            writer.write(date);
                            writer.flush();
                            output.append("По предмету нет стольких задач.");
                        }
                    } else {
                        writer.write(Integer.toString(oldNumber + number));
                        writer.write("\n");
                        writer.write(date);
                        writer.flush();
                        output.append("Изменения внесены\n" + "\n");
                    }
                } catch (IOException e) {
                    output.append("can't open the file" + fileName);
                }
            }else{ //перезаписываем дату
                try{
                    String[] arr = date.split(" ");
                    int days = Integer.parseInt(arr[0]) + 7;
                    int month = Integer.parseInt(arr[1]);
                    if(days > 31 - month%2 ){
                        days = days - 31 + month%2;
                        month++;
                    }
                    FileWriter writer = new FileWriter(fileName);
                    writer.write(Integer.toString(oldNumber));
                    writer.write("\n");
                    writer.write(days + " " + month);
                    writer.flush();
                    output.append("Изменения внесены\n" + "\n");
                }
                catch(IOException e){
                    output.append("can't open the file" + fileName);
                }
            }

    }

    class editListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            rewriter(false, true); //значит уменьшить задачи
        }
    }
}


