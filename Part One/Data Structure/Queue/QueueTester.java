public class QueueTester
{
    public static void main(String[] args) 
    {
        Queue q = new Queue();
        
        q.enqueue("hello");
        q.enqueue("h");

        System.out.println(q.dequeue());
        System.out.println(q.isEmpty());
    }
}