public class SymbolTable<Key extends Comparable, Value>
{
    private Key[] key;
    private Value[] value;
    private int size;
    private final int capacity;

    public SymbolTable(int size)
    {
        key = (Key[]) new Object[size];
        value = (Value[]) new Object[size];
        capacity = size;
        size = 0;
    }

    public void put(Key aKey, Value aValue)
    {
        if (contains(aKey))
            return;
    }

    public boolean contains(Key aKey)
    {
        return rank(aKey) != -1;
    }

    private int rank(Key aKey)
    {
        int low = 0;
        int high = size - 1;

        while (high >= low)
        {
            int mid = low + (high - low) / 2;

            if (key[mid].compareTo(aKey) == 0)
                return mid;
            else
                if (key[mid].compareTo(aKey) > 0)
                    high = mid - 1;
                else
                    low = mid + 1;
        }

        return -1;
    }
}