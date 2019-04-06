import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> 
{
    private Node head;
    private Node tail;
    private int size;

    private class Node
    {
        Item item;
        Node next;
        Node last;
    }

    public Deque()
    {
        head = null;
        tail = null;
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

    public void addFirst(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException();

        Node oldHead = head;
        head = new Node();
        head.item = item;
        head.next = oldHead;
        if (isEmpty())
        {
            tail = head;
        }
        else
        {
            oldHead.last = head;
        }
        size++;
    }

    public void addLast(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException();

        if (isEmpty())
        {
            addFirst(item);
        }
        else
        {
            Node oldTail = tail;
            tail = new Node();
            tail.item = item;
            oldTail.next = tail;
            tail.last = oldTail;
            size++;
        }
    }

    public Item removeFirst()
    {
        if (isEmpty())
            throw new NoSuchElementException();

        Item item = head.item;
        head = head.next;
        size--;

        if (isEmpty())
            tail = null;

        return item;
    }

    public Item removeLast()
    {
        if (isEmpty())
            throw new NoSuchElementException();

        Item item = tail.item;
        tail = tail.last;
        size--;

        if (isEmpty())
            head = null;

        return item;
    }                
   
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node pointer = head;

        public boolean hasNext()
        {
            return pointer != null;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            if (pointer.next == null)
                throw new NoSuchElementException();

            Item item = pointer.item;
            pointer = pointer.next;
            return item;
        }
    }      
}