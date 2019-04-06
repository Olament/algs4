import java.util.Random;

public class QuickSort
{
    public static void sort(Comparable[] array)
    {
        shuffle(array);
        sort(array, 0, array.length - 1);
    }

    public static int partition(Comparable[] array, int low, int high)
    {
        int i = low;
        int j = high + 1;

        while (true)
        {
            while (less(array[++i], array[low]))
                if (i == high)
                    break;

            while (less(array[low], array[--j]))
                if (j == low)
                    break;

            if (i >= j)
                break;
            
            exchange(array, i, j);
        }

        exchange(array, low, j);
        return j;
    }

    public static void sort(Comparable[] array, int low, int high)
    {
        if (low >= high)
            return;
        int mid = partition(array, low, high);
        sort(array, low, mid - 1);
        sort(array, mid + 1, high);
    }

    public static boolean less(Comparable i, Comparable j)
    {
        if (i.compareTo(j) == -1)
            return true;
        return false;
    }

    public static void exchange(Object[] array, int i, int j)
    {
        Object temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void shuffle(Object[] array)
    {
        Random rand = new Random();
        int index = 0;
        for (int i = array.length - 1; i > 0; i--)
        {
            index = rand.nextInt(i + 1);
            if (index != i)
                exchange(array, i, index);
        }
    }
}