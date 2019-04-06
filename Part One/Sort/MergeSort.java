public class MergeSort
{
    public static void sort(Comparable[] array)
    {
        Comparable[] aux = new Comparable[array.length];
        sort(array, aux, 0, array.length - 1);
    }

    public static void sort(Comparable[] array, Comparable[] aux, int low, int high)
    {
        if (low >= high)
            return;
        int mid = low + (high - low) / 2;
        sort(array, aux, low, mid);
        sort(array, aux, mid + 1, high);
        merge(array, aux, low, mid, high);
    }

    public static void merge(Comparable[] array, Comparable[] aux, int low, int mid, int high)
    {
        for (int i = low; i <= high; i++)
            aux[i] = array[i];

        int i = low;
        int j = mid + 1;
        for (int k = low; k <= high; k++)
            if (i > mid)
                array[k] = aux[j++];
            else
                if (j > high)
                    array[k] = aux[i++];
                else
                    if (aux[i].compareTo(aux[j]) <= 0)
                        array[k] = aux[i++];
                    else
                        array[k] = aux[j++];
    }
}