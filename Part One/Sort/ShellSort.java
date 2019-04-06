public class ShellSort
{
    public static void sort(Comparable[] array)
    {
        int length = array.length;

        int h = 1;
        while (h < length / 3)
            h = h * 3 + 1;

        while (h >= 1)
        {
            for (int i = h; i < length; i++)
                for (int j = i; j >= h && array[j].compareTo(array[j - h]) == -1; j -= h)
                    exchange(array, j, j - h);
            h /= 3;
        }
    }

    private static void exchange(Comparable[] a, int i, int j)
    {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }
}