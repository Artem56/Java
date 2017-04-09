/**
 * Created by Artem Solomatin on 09.04.17.
 * ZipComparator
 */
public class FileInfo  implements ComparableObject<FileInfo> {
    //FIELDS
    public final String name;
    private final long size;
    private final long crc;           //контрольная сумма

    //CONSTRUCTOR
    FileInfo(String name , long size , long crc){
        this.name = name;
        this.size = size;
        this.crc = crc;
    }

    //METHODS
    public FileInfo getComparedObject(){
        return this;
    }

    public FileStatus compareToObject(FileInfo fileInfo){   //сравнение текущего файла с переданным
        if(name.equals(fileInfo.name)){
            if(size == fileInfo.size && crc == fileInfo.crc) {
                return FileStatus.EQUALS;
            }
            return FileStatus.CHANGED;
        }
        else{
            if(size == fileInfo.size && crc == fileInfo.crc)
                return FileStatus.RENAMED;
            return FileStatus.NOT_EQUALS;
        }
    }

}
