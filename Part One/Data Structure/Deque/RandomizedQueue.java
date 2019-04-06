import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> 
{
    private Item[] array;
    private int size;

    public RandomizedQueue()
    {
        array = (Item[]) new Object[1];
        size = 0;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public int size()
    {
        return size;
    }

    private void resize(int newSize)
    {
        Item[] newArray = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++)
            newArray[i] = array[i];
        array = newArray;
    }

    public void enqueue(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException();

        if (size == array.length)
            resize(size * 2);
        array[size++] = item;
    }

    public Item dequeue()
    {
        if (size == 0)
            throw new NoSuchElementException();

        int index = StdRandom.uniform(size);
        Item item = array[index];
        size--;
        array[index] = array[size];
        array[size] = null;

        if (size > 0 && size < array.length / 4)
            resize(array.length / 2);

        return item;
    }

    public Item sample()
    {
        if (size == 0)
            throw new NoSuchElementException();

        return array[StdRandom.uniform(size)];
    }

    public Iterator<Item> iterator()
    {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item>
    {
        private int pointer = 0;
        private int[] shuffle;

        public RandomizedQueueIterator()
        {
            shuffle = shuffleArray(size);
        }

        public boolean hasNext()
        {
            return pointer != size;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            if (pointer == size)
                throw new NoSuchElementException();
            
            return array[shuffle[pointer++]];
        }
    }

    private int[] shuffleArray(int size)
    {
        int[] shuffle = new int[size];
        for (int i = 0; i < size; i++)
            shuffle[i] = i;

        int pointer = size;
        while (pointer != 0)
        {
            int index = StdRandom.uniform(pointer);
            int temp = shuffle[pointer - 1];
            shuffle[pointer - 1] = shuffle[index];
            shuffle[index] = temp;
            pointer--;
        }

        return shuffle;
    }
}