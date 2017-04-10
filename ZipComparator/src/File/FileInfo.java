package File;

import java.util.zip.ZipEntry;

/**
 * Created by Artem Solomatin on 09.04.17.
 * ZipComparator
 */
public class FileInfo {
    //FIELDS
    public final String name;
    private final long size;
    private final long checkSum;

    //CONSTRUCTOR
    public FileInfo(ZipEntry entry){
        this.name = entry.getName();
        this.size = entry.getSize();
        this.checkSum = entry.getCrc();
    }

    //METHODS
    public FileStatus compareToObject(FileInfo fileInfo){   //сравнение текущего файла с переданным
        if(name.equals(fileInfo.name)){
            if(size == fileInfo.size && checkSum == fileInfo.checkSum) {
                return FileStatus.EQUALS;
            }
            return FileStatus.CHANGED;
        }
        else{
            if(size == fileInfo.size && checkSum == fileInfo.checkSum)
                return FileStatus.RENAMED;
            return FileStatus.NOT_EQUALS;
        }
    }
}
