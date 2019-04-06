public class LinkedListStack
{
    private Node head = null;

    private class Node
    {
        Node next;
        int item;
    }

    public boolean isEmpty()
    {
        return head == null;
    }

    public void push(int number)
    {
        Node oldHead = head;
        head = new Node();
        head.item = number;
        head.next = oldHead;
    }

    public int pop()
    {
        int data = head.item;
        head = head.next;

        return data;
    }
}