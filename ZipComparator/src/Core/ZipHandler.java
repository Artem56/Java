package Core;

import File.Directory;
import File.FileInfo;
import File.FileStatus;
import File.ZipFileInfo;
import Report.FileReport;
import Report.GeneralReport;

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

        Directory Zip1 = getContentsZip(new ZipFile(pathToZip1));
        Directory Zip2 = getContentsZip(new ZipFile(pathToZip2));

        GeneralReport generalReport = new GeneralReport();
        FileReport reportZip1 = new FileReport(pathToZip1);
        FileReport reportZip2 = new FileReport(pathToZip2);

        compareDirectoryInfo(reportZip1 , Zip1 , reportZip2 , Zip2);

        generalReport.add(reportZip1);
        generalReport.add(reportZip2);

        return generalReport.printReport();
    }

    private static Directory getContentsZip(ZipFile zip) throws IOException {   //НАХОДИТ ВСЕ ФАЙЛЫ В ZIP'Е
        Directory directory = new Directory();
        Enumeration<? extends ZipEntry> entryEnumeration = zip.entries();    //перечисление файлов в архиве
        while(entryEnumeration.hasMoreElements()) {
            ZipEntry zipEntry;
            zipEntry = entryEnumeration.nextElement();
            directory.add(new ZipFileInfo(zipEntry));
        }
        return directory;
    }

    private static void compareDirectoryInfo(FileReport reportZip1, Directory Zip1, FileReport reportZip2, Directory Zip2){
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
