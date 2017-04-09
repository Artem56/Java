/**
 * Created by Artem Solomatin on 09.04.17.
 * ZipComparator
 */
public class FileReport extends Note{
    public FileReport(String name){
        super(name);
    }

    public void fileChanged(String name){
        addMessage("* " + name);
    }

    public void fileRemoved(String name){
        addMessage("- " + name);
    }

    public void fileAdd(String name){
        addMessage("+ " + name);
    }

    public void fileRenamed(String name){
        addMessage("# " + name);
    }
}
