package File;

import java.util.ArrayList;

/**
 * Created by Artem Solomatin on 09.04.17.
 * ZipComparator
 */
public class Directory extends ArrayList<FileInfo>{
    //FIELDS
    private FileInfo lastComparedObject;

    //METHODS
    public FileInfo getComparedObject(){
        return lastComparedObject;
    }

    public FileStatus compareToObject(FileInfo file){
        for(FileInfo f : this){
            FileStatus fileState = f.compareToObject(file);
            if(fileState != FileStatus.NOT_EQUALS) {
                lastComparedObject = f;
                return fileState;
            }
        }
        return FileStatus.NOT_EQUALS;
    }
}
