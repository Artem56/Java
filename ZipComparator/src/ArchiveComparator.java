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
ЗАМЕНИТЬ ВСЕ EXTEND ...<> НА ПОЛЕ ...<>
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
            JOptionPane.showMessageDialog(null, "Failed to open the file for the report: report.txt");

        }

        writer.write(ZipHandler.zipComparison(files.get(0).getAbsolutePath(), files.get(1).getAbsolutePath()));
        writer.close();
    }
}
