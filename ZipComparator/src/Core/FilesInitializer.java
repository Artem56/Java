package Core;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem Solomatin on 09.04.17.
 * ZipComparator
 */
public class FilesInitializer {
    //FIELDS
    private List<File> files = new ArrayList<>(2);

    //CONSTRUCTOR
    public FilesInitializer(String[] filepaths) throws FileNotFoundException {
        for(String filepath : filepaths) {
            initializeFile(filepath);
        }
    }

    //METHODS
    public List<File> getFiles() {
        return files;
    }

    private void initializeFile(String filePath) throws FileNotFoundException{
        File file = new File(filePath);
        if (file.exists()) {
            files.add(file);
        }
        else {
            JOptionPane.showMessageDialog(null, "File " + file.getName() + "is not exist");
            throw new FileNotFoundException(filePath + " does not exist");
        }
    }
}
