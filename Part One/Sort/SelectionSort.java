public class SelectionSort
{
    public static void sort(Comparable[] a)
    {
        int length = a.length;
        for (int i = 0; i < length; i++)
        {
            int min = i;
            for (int j = i; j < length; j++)
                if (a[j].compareTo(a[min]) == -1)
                    min = j;
            exchange(a, i, min);
        }
    }

    private static void exchange(Comparable[] a, int i, int j)
    {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}