public class StackTester
{
    public static void main(String[] args) 
    {
        LinkedListStack listStack = new LinkedListStack();
        ArrayStack arrayStack = new ArrayStack();

        System.out.println("LinkedListStack:");
        System.out.println(listStack.isEmpty());
        listStack.push(1);
        listStack.push(2);
        System.out.println(listStack.isEmpty());
        System.out.println(listStack.pop());
        System.out.println(listStack.pop());
        System.out.println(listStack.isEmpty());

        System.out.println("ArrayStack:");
        System.out.println(arrayStack.isEmpty());
        arrayStack.push(1);
        arrayStack.push(2);
        System.out.println(arrayStack.isEmpty());
        System.out.println(arrayStack.pop());
        System.out.println(arrayStack.pop());
        System.out.println(arrayStack.isEmpty());
    }
}