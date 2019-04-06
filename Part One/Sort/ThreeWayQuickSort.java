import java.util.Random;

public class ThreeWayQuickSort
{
    public static void sort(Comparable[] array)
    {
        
        sort(array, 0, array.length - 1);
    }

    public static void sort(Comparable[] array, int low, int high)
    {
        if (high <= low)
            return;

        int lp = low;
        int hp = high;
        int i = low;
        int mid = low;

        while (i <= hp)
        {
            int cmd = array[i].compareTo(array[mid]);
            
            if (cmd < 0)
                exchange(array, i++, lp++);
            else
                if (cmd > 0)
                    exchange(array, i, hp--);
                else
                    i++;
        }

        sort(array, low, lp - 1);
        sort(array, hp + 1, high);
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