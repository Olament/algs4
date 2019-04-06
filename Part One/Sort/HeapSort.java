public class HeapSort
{
    public static void sort(Comparable[] array)
    {
        int length = array.length;

        for (int i = length / 2; i >= 1; i--)
            sink(array, i, length);

        while (length > 1)
        {
            exchange(array, 1, length);
            sink(array, 1,--length);
        }
    }

    public static void sink(Comparable[] array, int p, int n)
    {
        while (p * 2 <= n)
        {
            int i = p * 2;
            if (i < n && less(array[i - 1], array[i]))
                i++;
            if (!less(array[p - 1], array[i - 1]))
                break;
            exchange(array, p, i);
            p = i;
        }
    }

    public static boolean less(Comparable i, Comparable j)
    {
        if (i.compareTo(j) == -1)
            return true;
        return false;
    }

    public static void exchange(Object[] array, int i, int j)
    {
        Object temp = array[i - 1];
        array[i - 1] = array[j - 1];
        array[j - 1] = temp;
    }
}