package File;

import java.util.zip.ZipEntry;

/**
 * Created by Artem Solomatin on 09.04.17.
 * ZipComparator
 */
public class ZipFileInfo extends FileInfo {
    public ZipFileInfo(ZipEntry entry){
        super(entry.getName(), entry.getSize(), entry.getCrc());
    }
}
