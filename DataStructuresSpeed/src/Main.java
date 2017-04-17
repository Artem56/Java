import java.util.*;

/**
 * Created by Artem Solomatin on 28.03.17.
 * DataStructuresSpeed
 */

/**Измерение скорости выполнения основных операций в различных структурах данных в Java
 *
 *
 * Аргументом командной строки является количество элементов (NUMBER), на котором будут проводится тест.
 * Изначально все структуры данных содержат 10 * NUMBER элементов.
 * Затем проводится тест на добавление NUMBER элементов.
 * Затем проводится тест на удаление NUMBER элементов.
 * Затем сократим структуру до NUMBER элементов.
 * Затем проведем NUMBER тестов на поиск рандомных чисел.
 */

/*
Доделать массивы
 */
public class Main {
    private static int NUMBER;

    int[] intArray;

    ArrayList<Integer> arrayList;
    LinkedList linkedList;
    List<Integer> synchronizedList;

    TreeSet<Integer> treeSet;
    Set<Integer> synchronizedTree;

    HashSet<Integer> hashSet;
    Set<Integer> synchronizedHash;

    ArrayList<Collection<Integer>> usedCollections;

    LinkedHashMap<String, Long> insertResults;
    LinkedHashMap<String, Long> removeResults;
    LinkedHashMap<String, Long> searchResults;

    long totalTime;


    public static void main(String[] args) throws ClassNotFoundException {
        long startTime = System.currentTimeMillis();

        NUMBER = Integer.parseInt(args[0]);

        Main test = new Main();

        test.arrayInitialization();
        test.initialization();
        for(Collection<Integer> current: test.usedCollections){
            test.insertSpeed(current, null);
            test.removeSpeed(current, null);
            test.reduceCollection(current, null);
            test.searchSpeed(current, null);
        }

        test.print();

        test.totalTime = System.currentTimeMillis() - startTime;

    }

    private Main(){
        //массивы
        intArray = new int[NUMBER * 11];

        //списки
        arrayList = new ArrayList<>(NUMBER * 11);
        linkedList = new LinkedList<>();
        synchronizedList = Collections.synchronizedList(new ArrayList<Integer>(NUMBER * 11));

        //деревья
        treeSet = new TreeSet<>();
        synchronizedTree = Collections.synchronizedSet(new TreeSet<Integer>());

        //хэш-таблицы
        hashSet = new HashSet<>(NUMBER * 11);
        synchronizedHash = Collections.synchronizedSet(new HashSet<Integer>(NUMBER * 11));

        //список использованных коллекций
        usedCollections = new ArrayList<>(10);

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
        }
    }

    private void initialization(){
        int i;
        for(i = 0;i < NUMBER * 10;i++){     //заполнение коллекций начальными NUMBER * 10 элементами
            Integer tmp = (int)(Math.random() * NUMBER * 5);

            arrayList.add(tmp);
            linkedList.add(tmp);
            synchronizedList.add(tmp);

            treeSet.add(tmp);
            synchronizedTree.add(tmp);

            hashSet.add(tmp);
            synchronizedHash.add(tmp);
        }
    }



    private long insertSpeed(Collection<Integer> collection, int[] array){
        long resultTime = 0;
        long resultArrayTime = 0;
        long quantumTime = 0;
        for(int i = 0;i < NUMBER;i++){
            Integer tmp = (int)(Math.random() * NUMBER * 5);
            if(collection != null) {
                quantumTime = System.currentTimeMillis();
                collection.add(tmp);
                resultTime += (System.currentTimeMillis() - quantumTime);
            }else if(array != null){
                quantumTime = System.currentTimeMillis();
                array[i] = tmp;
                resultArrayTime += (System.currentTimeMillis() - quantumTime);
            }
        }
        if(collection != null){
            insertResults.put(collection.getClass().toString(), resultTime);
        }
        return array != null ? resultArrayTime : -1;
    }

    private long searchSpeed(Collection<Integer> collection, int[] array){
        long resultTime = 0;
        long resultArrayTime = 0;
        long quantumTime = 0;
        for(int i = 0;i < NUMBER;i++){
            Integer tmp = (int)(Math.random() * NUMBER);
            if(collection != null) {
                quantumTime = System.currentTimeMillis();
                collection.contains(tmp);
                resultTime += (System.currentTimeMillis() - quantumTime);
            }else if(array != null){
                quantumTime = System.currentTimeMillis();
                for (int anArray : array) {
                    if (anArray == tmp) break;
                }
                resultArrayTime += (System.currentTimeMillis() - quantumTime);
            }
        }
        if(collection != null){
            searchResults.put(collection.getClass().toString(), resultTime);
        }
        return array != null ? resultArrayTime : -1;
    }

    private long removeSpeed(Collection<Integer> collection, int[] array){
        long resultTime = 0;
        long resultArrayTime = 0;
        long quantumTime = 0;
        for(int i = 0;i < NUMBER;i++){
            Integer tmp = (int)(Math.random() * NUMBER);
            if(collection != null) {
                quantumTime = System.currentTimeMillis();
                collection.remove(tmp);
                resultTime += (System.currentTimeMillis() - quantumTime);
            }else if(array != null){
                quantumTime = System.currentTimeMillis();
                for (int anArray : array) {
                    if (anArray == tmp){
                        anArray = 0;
                        break;
                    }
                }
                array[i] = tmp;                                                   //   ЭТО НЕ УДАЛЕНИЕ
                resultArrayTime += (System.currentTimeMillis() - quantumTime);
            }
        }
        if(collection != null){
            removeResults.put(collection.getClass().toString(), resultTime);
        }

        return array != null ? resultArrayTime : -1;
    }

    private void reduceCollection(Collection<Integer> collection, int[] array){//очистим структуру, оставив NUMBER элементов
        int i = 0;
        if(collection != null) {
            Iterator<Integer> itr = collection.iterator();
            while (itr.hasNext() && i < NUMBER * 9) {
                itr.next();
                itr.remove();
                i++;
            }
            System.out.println("test " + collection.size());
        }




        //Collection newCollection = collection.getClass().newInstance();
        if(array != null) {
            int[] newArray = new int[NUMBER];
            System.arraycopy(array, 0, newArray, 0, NUMBER);
            intArray = newArray;
        }
    }

    private void print() throws ClassNotFoundException {
        System.out.println("Total time for test is: " + totalTime / 1000 + " seconds");
        System.out.println("Insert speed");
        System.out.println("class int[] : " + insertSpeed(null, intArray));
        for(Map.Entry<String, Long> entry : insertResults.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("Remove speed");
        System.out.println("class int[] : " + removeSpeed(null, intArray));
        for(Map.Entry<String, Long> entry : removeResults.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        reduceCollection(null, intArray);

        System.out.println("Search speed");
        System.out.println("class int[] : " + searchSpeed(null, intArray));
        for(Map.Entry<String, Long> entry : searchResults.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
