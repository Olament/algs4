import edu.princeton.cs.algs4.Stopwatch;
import java.util.Arrays;

public class ThreeSum
{
    public void bruteForce(int[] numbers)
    {
        Stopwatch stopwatch = new Stopwatch();
        int counter = 0;

        for (int i = 0; i < numbers.length; i++)
            for (int j = i + 1; j < numbers.length; j++)
                for (int k = j + 1; k < numbers.length; k++)
                    if (numbers[i] + numbers[j] + numbers[k] == 0)
                        counter++;

        System.out.printf("Brute-force: %.4f\n", stopwatch.elapsedTime());
        System.out.println("ThreeSum find: " + counter);
    }

    public void sorted(int[] numbers)
    {
        Stopwatch stopwatch = new Stopwatch();
        int counter = 0;
        Arrays.sort(numbers);

        for (int i = 0; i < numbers.length; i++)
            for (int j = i + 1; j < numbers.length; j++)
                if (bi(numbers, -(numbers[i] + numbers[j])) != -1)
                    counter++;

        System.out.printf("Sorted     : %.4f\n", stopwatch.elapsedTime());
        System.out.println("ThreeSum find: " + counter);
    }

    public double bi(int[] array, int number)
    {
        int low = 0;
        int high = array.length - 1;

        while (low <= high)
        {
            int midpoint = (low + high) / 2;

            if (array[midpoint] == number)
                return midpoint;

            if (array[midpoint] > number)
                high = midpoint - 1;

            if (array[midpoint] < number)
                low = midpoint + 1;
        }

        return -1;
    }
}