import java.util.Random;
import java.util.Arrays;

public class MergeSortCompare
{
    public static void main(String[] args) 
    {
        int size = Integer.parseInt(args[0]);
        Integer[] array1 = new Integer[size];
        Random rand = new Random();
        for (int i = 0; i < array1.length; i++)
            array1[i] = rand.nextInt();
        Integer[] array2 = array1.clone();

        long start = System.currentTimeMillis();
        MergeSort.sort(array1);
        long end = System.currentTimeMillis();
        System.out.printf("Regular Merge Sort:\n");
        System.out.printf("    is sorted: %b\n", isSort(array1));
        System.out.printf("    time taken: %f\n", (end - start) / 1000.0);

        start = System.currentTimeMillis();
        AdvancedMergeSort.sort(array2);
        end = System.currentTimeMillis();
        System.out.printf("Advanced Merge Sort:\n");
        System.out.printf("    is sorted: %b\n", isSort(array2));
        System.out.printf("    time taken: %f\n", (end - start) / 1000.0);
    }

    public static boolean isSort(Comparable[] array)
    {
        for (int i = 0; i < array.length - 1; i++)
            if (array[i].compareTo(array[i + 1]) > 0)
                return false;
        return true;
    }
}