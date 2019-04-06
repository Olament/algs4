import java.util.Random;

public class SortTester
{
    public static void main(String[] args) 
    {
        Random rand = new Random();
        Integer[] array1 = new Integer[10000000];

        for (int i = 0; i < array1.length; i++)
            array1[i] = rand.nextInt();
        
        Integer[] array2 = array1.clone();

        System.out.println("Start:");
        long start = System.nanoTime();
        HeapSort.sort(array1);
        System.out.println((System.nanoTime() - start) / 1000000000.0);

        start = System.nanoTime();
        QuickSort.sort(array2);
        System.out.println((System.nanoTime() - start) / 1000000000.0);
    }

    private static void printArray(Object[] array)
    {
        System.out.print("[");
        for (int i = 0; i < array.length; i++)
            if (i == array.length - 1)
                System.out.print(array[i]);
            else
                System.out.print(array[i] + " ");
        System.out.println("]");
    }
}