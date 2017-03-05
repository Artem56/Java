import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Artem Solomatin on 06.03.17.
 * TestRandom
 */
public class TestRandom {

    public static void main(String[] args){
        double number = 10_000_000_000.0;
        int[] array = new int[100];

        long startTime = System.nanoTime();
        for(int i = 0;i < number;i++){
            int tmp = (int)(Math.random() * 100);
            array[tmp]++;
        }

        long endTime = System.nanoTime();
        for(int i = 0;i < 100;i++){
            System.out.println(array[i]);
        }
        System.out.println("***************************************************");
        Arrays.sort(array);
        System.out.println(array[0]);
        System.out.println(array[99]);
        System.out.println("Максимальное отклонение на " + number + " итераций: " + (array[99] - array[0]) * 100.0 / number + " процента. Программа работала " + (endTime - startTime) / 1_000_000 + " миллисекунд.");
    }
}
