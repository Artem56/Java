import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Artem Solomatin on 09.04.17.
 * ZipComparator
 */
public class GeneralReport extends ArrayList<Note> {
    //FIELDS
    private final String columnSeparator = "|";
    private final char rowSeparator = '_';
    private int width;

    //CONSTRUCTOR

    //METHODS
    /**
     * Create general report of inner simple reports.
     * @return general report like this:
     *      | <name_report1> | <name_report2> | *** |
     *      |_______________________________________|
     *      |   message1     |   message2     | *** |
     */
    public String printReport() {
        if(!isEmpty()) {
            return printHead() + printBody();
        }
        return "";
    }

    private String constructLine(String report , int width){    //ФОРМАТ?
        return String.format(" %-" + (width) + "s " , report) + columnSeparator;
    }

    private int findMaxSize(){    //МАКСИМАЛЬНЫЙ РАЗМЕР ДОКЛАДА
        int size = 0;
        for(Note report : this)
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
        for (Note report : this) {
            headString += constructLine(report.getNameReport(), report.getRowWidth());
        }
        width = headString.length();
        return headString + "\n" + createLineSeparator(headString.length() - 2) + "\n";
    }

    private String printBody(){
        StringBuilder body = new StringBuilder();     //сам отчет
        int maxSize = findMaxSize();
        for(int i = 0 ; i < maxSize ; i++){               //по строкам
            String bodyString = columnSeparator;
            for(Note report : this)
                bodyString += constructLine(report.get(i) , report.getRowWidth());
            body.append(bodyString + "\n");
        }
        return body.toString() + createLineSeparator(width - 2) + "\n";
    }

}
