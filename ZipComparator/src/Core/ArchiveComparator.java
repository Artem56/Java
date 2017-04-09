package Core;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Artem Solomatin on 09.04.17.
 * ZipComparator
 */
/*
ДОБАВИТЬ К ПРИЗНАКАМ ФАЙЛА ЕГО РАЗМЕР
ВОЗМОЖНО НЕ РАБОТАЕТ ПРИ ДОБАВЛЕНИИ ОДНОГО ФАЙЛА ДВАЖДЫ
НЕ РЕАГИРУЕТ ПРИ ИЗМЕНЕНИИ ФАЙЛА
ЗАМЕНИТЬ ВСЕ EXTEND ...<> НА ПОЛЕ ...<>
ДОБАВИТЬ ВНИЗУ ОПИСАНИЕ КАЖДОГО СИМВОЛА
ДОБАВИТЬ ОИСАНИЕ К КЛАССАМ
ДОБАВИТЬ ОПИСАНИЯ К ФУНКЦИЯМ И УБРАТЬ КОМЕНТАРИИ
ДОБАВИТЬ ДИСПЛЕЙ
 */
public class ArchiveComparator {
    public static void main(String[] args) throws IOException {
        FilesInitializer fileSet  = new FilesInitializer(args);   //для дальнейшей работы, если не всегда инициализация одинаковая

        /*switch(args.length) {
            case 2:
                fileChoicerZip = new FilesChoicer(args);
                break;
        }*/

        List<File> files = fileSet.getFiles();
        BufferedWriter writer = null;
        try {                          //ДОЛЖНА ЛИ ПРОГРАММА ЗДЕСЬ ПАДАТЬ?
            writer = new BufferedWriter(new FileWriter("report.txt"));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to open the file: report.txt");
        }
        writer.write(ZipHandler.zipComparison(files.get(0).getAbsolutePath(), files.get(1).getAbsolutePath()));
        writer.close();
    }
}
