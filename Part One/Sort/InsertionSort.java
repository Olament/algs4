public class InsertionSort
{
    public static void sort(Comparable[] array)
    {
        int length = array.length;
        for (int i = 0; i < length; i++)
            for (int j = i; j > 0; j--)
                if (array[j].compareTo(array[j - 1]) == 1)
                    break;
                else
                    exchange(array, j, j - 1);
    }

    private static void exchange(Comparable[] a, int i, int j)
    {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}