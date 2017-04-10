package Core;

import File.Archive;
import File.FileInfo;
import File.FileStatus;
import Report.Report;
import Report.Note;

import javax.swing.*;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by Artem Solomatin on 09.04.17.
 * ZipComparator
 */
public abstract class ZipHandler {
    //METHODS
    public static String zipComparison(String pathToZip1, String pathToZip2) throws IOException {
        //System.out.println(pathToZip1 + "  " + pathToZip2);
        if(pathToZip1.equals(pathToZip2)){
            JOptionPane.showMessageDialog(null, "A comparison of the same file");
            return "A comparison of the same file\n";
        }

        Archive Zip1 = getContentsZip(new ZipFile(pathToZip1));
        Archive Zip2 = getContentsZip(new ZipFile(pathToZip2));

        Report mainReport = new Report();
        Note reportZip1 = new Note(pathToZip1);
        Note reportZip2 = new Note(pathToZip2);

        compareDirectoryInfo(reportZip1 , Zip1 , reportZip2 , Zip2);

        mainReport.messages.add(reportZip1);
        mainReport.messages.add(reportZip2);

        return mainReport.printReport();
    }

    private static Archive getContentsZip(ZipFile zip) throws IOException {   //НАХОДИТ ВСЕ ФАЙЛЫ В ZIP'Е
        Archive directory = new Archive();
        Enumeration<? extends ZipEntry> entryEnumeration = zip.entries();    //перечисление файлов в архиве
        while(entryEnumeration.hasMoreElements()) {
            ZipEntry zipEntry;
            zipEntry = entryEnumeration.nextElement();
            directory.add(new FileInfo(zipEntry));
        }
        return directory;
    }

    private static void compareDirectoryInfo(Note reportZip1, Archive Zip1, Note reportZip2, Archive Zip2){
        for(FileInfo fileFromZip1 : Zip1){
            FileStatus state = Zip2.compareToObject(fileFromZip1);
            FileInfo similarFile = Zip2.getComparedObject();
            switch (state){
                case CHANGED:
                    reportZip1.fileChanged(fileFromZip1.name);
                    reportZip2.fileChanged(similarFile.name);
                    Zip2.remove(similarFile);
                    break;
                case RENAMED:
                    reportZip1.fileRenamed(fileFromZip1.name);
                    reportZip2.fileRenamed(similarFile.name);
                    Zip2.remove(similarFile);
                    break;
                case NOT_EQUALS:
                    reportZip1.fileRemoved(fileFromZip1.name);
                    reportZip2.addMessage(" ");
                    break;
                case EQUALS:
                    Zip2.remove(similarFile);
            }
        }
        for(FileInfo fileFromZip2 : Zip2){
            reportZip2.fileAdd(fileFromZip2.name);
            reportZip1.addMessage(" ");
        }
    }
}
