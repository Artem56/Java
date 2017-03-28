import java.util.*;

/**
 * Created by Artem Solomatin on 28.03.17.
 * DataStructuresSpeed
 */

/**
 * Аргументом командной строки является количество элементов (NUMBER), на котором будут проводится тест.
 * Изначально все структуры данных содержат 10 * NUMBER элементов.
 * Затем проводится тест на добавление NUMBER элементов.
 * Затем проводится тест на удаление NUMBER элементов.
 * Затем сократим структуру до NUMBER элементов.
 * Затем проведем NUMBER тестов на поиск рандомных чисел.
 */
public class Main {
    private static int NUMBER;

    int[] intArray;
    Integer[] integerArray;

    ArrayList<Integer> arrayList;
    LinkedList<Integer> linkedList;
    List<Integer> synchronizedList;

    TreeSet<Integer> treeSet;
    Set<Integer> synchronizedTree;

    HashSet<Integer> hashSet;
    Set<Integer> synchronizedHash;

    ArrayList<Collection<Integer>> usedCollections;

    LinkedHashMap<String, Integer> insertResults;
    LinkedHashMap<String, Integer> removeResults;
    LinkedHashMap<String, Integer> searchResults;


    public static void main(String[] args){

        NUMBER = Integer.parseInt(args[0]);

        Main test = new Main();

        //test.arrayInitialization();
        test.initialization();
        for(Collection<Integer> collect: test.usedCollections){
            test.insertSpeed(collect);
            test.searchSpeed(collect);
            test.removeSpeed(collect);
        }

        test.print();

    }

    Main(){
        //массивы
        intArray = new int[NUMBER * 11];
        integerArray = new Integer[NUMBER * 11];

        //списки
        arrayList = new ArrayList<>();
        linkedList = new LinkedList<>();
        synchronizedList = Collections.synchronizedList(new ArrayList<Integer>());

        //деревья
        treeSet = new TreeSet<>();
        synchronizedTree = Collections.synchronizedSet(new TreeSet<Integer>());

        //хэш-таблицы
        hashSet = new HashSet<>();
        synchronizedHash = Collections.synchronizedSet(new HashSet<Integer>());

        //список использованных коллекций
        usedCollections = new ArrayList<>();

        usedCollections.add(arrayList);
        usedCollections.add(linkedList);
        usedCollections.add(synchronizedList);
        usedCollections.add(treeSet);
        usedCollections.add(synchronizedTree);
        usedCollections.add(hashSet);
        usedCollections.add(synchronizedHash);

        //результат
        insertResults = new LinkedHashMap<>();
        removeResults = new LinkedHashMap<>();
        searchResults = new LinkedHashMap<>();
    }

    private void arrayInitialization(){
        int i;
        for(i = 0;i < NUMBER * 10;i++){            //заполнение массивов начальными NUMBER * 10 элементами
            Integer tmp = (int)(Math.random() * NUMBER * 5);

            intArray[i] = tmp;
            integerArray[i] = tmp;
        }
    }

    private void initialization(){
        int i;
        for(i = 0;i < NUMBER * 10;i++){     //заполнение коллекций начальными NUMBER * 10 элементами
            Integer tmp = (int)(Math.random() * NUMBER);

            arrayList.add(tmp);
            linkedList.add(tmp);
            synchronizedList.add(tmp);

            treeSet.add(tmp);
            synchronizedTree.add(tmp);

            hashSet.add(tmp);
            synchronizedHash.add(tmp);
        }
    }

    private void insertSpeed(Collection<Integer> collection){
        int i;
        long startTime = System.currentTimeMillis();
        for(i = 0;i < NUMBER;i++){
            Integer tmp = (int)(Math.random() * NUMBER);
            collection.add(tmp);
        }
        long endTime = System.currentTimeMillis();
        insertResults.put(collection.getClass().toString(), (int)(endTime - startTime));
    }

    private void searchSpeed(Collection<Integer> collection){
        int i;
        for(i = 0;i < NUMBER * 9;i++){       //очистим структуру, оставив NUMBER элементов
            Integer tmp = (int)(Math.random() * NUMBER);
            collection.remove(tmp);
        }

        long startTime = System.currentTimeMillis();
        for(i = 0;i < NUMBER;i++){
            Integer tmp = (int)(Math.random() * NUMBER);
            collection.contains(tmp);
        }
        long endTime = System.currentTimeMillis();
        searchResults.put(collection.getClass().toString(), (int)(endTime - startTime));
    }

    private void removeSpeed(Collection<Integer> collection){
        int i;
        long startTime = System.currentTimeMillis();
        for(i = 0;i < NUMBER;i++){
            Integer tmp = (int)(Math.random() * NUMBER);
            collection.remove(tmp);
        }
        long endTime = System.currentTimeMillis();
        removeResults.put(collection.getClass().toString(), (int)(endTime - startTime));
    }

    private void print(){
        System.out.println("Insert speed");
        for(Map.Entry<String, Integer> entry : insertResults.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println("Search speed");
        for(Map.Entry<String, Integer> entry : searchResults.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println("Remove speed");
        for(Map.Entry<String, Integer> entry : removeResults.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
