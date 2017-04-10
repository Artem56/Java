package Report;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Artem Solomatin on 10.04.17.
 * ZipComparator
 */
public class Note{
    //FIELDS
    private List<String> messages;
    private String reportOwner;     //из какого архива заметка
    private int rowWidth;

    //CONSTRUCTOR
    public Note(String name){
        messages = new ArrayList<>();
        rowWidth = name.length();
        reportOwner = name;
    }

    //METHODS
    public boolean addMessage(String text){    //устанавливаем сообщение, попутно подгоняя разделитель строк
        if(rowWidth < text.length()) {
            rowWidth = text.length();
        }
        return messages.add(text);
    }

    public String get(int index){
        if(index >= 0 && index < messages.size()) {
            return messages.get(index);
        }
        throw new NoSuchElementException("the message with index" + index + "does not exist");
    }

    public int getRowWidth(){
        return rowWidth;
    }

    public int size(){
        return messages.size();
    }

    public String getNameReport(){
        return reportOwner;
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
