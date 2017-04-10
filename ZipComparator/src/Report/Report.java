package Report;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Artem Solomatin on 10.04.17.
 * ZipComparator
 */
public class Report{
    //FIELDS
    public ArrayList<Note> messages = new ArrayList<Note>();
    private final String columnSeparator = "|";
    private final char rowSeparator = '*';
    private int width;

    //METHODS
    public String printReport() {
        if(!messages.isEmpty()) {
            return printHead() + printBody() + printTail();
        }
        return "";
    }

    private String constructLine(String report , int width){    //ФОРМАТ?
        return String.format(" %-" + (width) + "s " , report) + columnSeparator;
    }

    private int findMaxSize(){    //МАКСИМАЛЬНЫЙ РАЗМЕР ДОКЛАДА
        int size = 0;
        for(Note report : messages)
            if(report.size() > size)
                size = report.size();
        return size;
    }

    private String createLineSeparator(int length){    //СОЗДАЕТ ГОРИЗОНТАЛЬНЫЙ СЕПАРАТОР   НЕРАЦИОНАЛЬНО!!!
        final char buffer[] = new char[length];
        Arrays.fill(buffer, rowSeparator);
        return columnSeparator + new String(buffer) + columnSeparator;
    }

    private String printHead() {     //ПЕРВАЯ СТРОКА ОТЧЕТА
        String headString = columnSeparator;
        for (Note report : messages) {
            headString += constructLine(report.getNameReport(), report.getRowWidth());
        }
        width = headString.length();
        return createLineSeparator(headString.length() - 2) + "\n" + headString + "\n" + createLineSeparator(headString.length() - 2) + "\n";
    }

    private String printBody(){
        StringBuilder body = new StringBuilder();     //сам отчет
        int maxSize = findMaxSize();
        for(int i = 0 ; i < maxSize ; i++){               //по строкам
            String bodyString = columnSeparator;
            for(Note report : messages)
                bodyString += constructLine(report.get(i) , report.getRowWidth());
            body.append(bodyString + "\n");
        }
        return body.toString() + createLineSeparator(width - 2) + "\n";
    }

    private String printTail(){
        return columnSeparator + constructLine("+ added   - deleted   * renamed   # changed", width - 4) + "\n" + createLineSeparator(width - 2) + "\n";
    }

}
