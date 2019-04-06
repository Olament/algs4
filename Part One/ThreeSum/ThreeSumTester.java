
import java.util.Random;

public class ThreeSumTester
{
    public static void main(String[] args) 
    {
        int[] numbers = new int[Integer.parseInt(args[0])];
        Random rand = new Random();

        for (int i = 0; i < numbers.length; i++)
            numbers[i] = rand.nextInt(101) - 50;

        ThreeSum t = new ThreeSum();

        t.bruteForce(numbers);
        t.sorted(numbers);
    }
}