public class Queue
{
    private class Node
    {
        Node next;
        String item;
    }

    private Node head;
    private Node tail;

    public void enqueue(String data)
    {
        Node oldTail = tail;
        tail = new Node();
        tail.item = data;
        tail.next = null;

        if (oldTail == null)
        {
            head = tail;
        }
        else
        {
            oldTail.next = tail;
        }
    }

    public String dequeue()
    {
        String data = head.item;
        head = head.next;
        if (isEmpty())
            tail = null;
        return data;
    }

    public boolean isEmpty()
    {
        return head == null;
    }
}