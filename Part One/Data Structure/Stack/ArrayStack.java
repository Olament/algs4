public class ArrayStack
{
    int[] a = new int[1];
    int head = 0;

    public void resize(int size)
    {
        int[] newArray = new int[size];

        for (int i = 0; i < a.length; i++)
            newArray[i] = a[i];

        a = newArray;
    }

    public void push(int data)
    {
        if (head == a.length)
            resize(a.length * 2);

        a[head++] = data; 
    }

    public int pop()
    {
        int data = a[--head];
        a[head] = 0;

        if (head > 0 && head == a.length / 4)
            resize(a.length / 2);
        return data;
    }

    public boolean isEmpty()
    {
        return head == 0;
    }
}