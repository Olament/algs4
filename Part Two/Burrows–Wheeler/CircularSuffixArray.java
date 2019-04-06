import java.util.Comparator;
import java.util.Arrays;

public class CircularSuffixArray
{
    private int length;
    private Integer[] index;
    private String string;

    public CircularSuffixArray(String s)
    {
        if (s == null)
            throw new IllegalArgumentException();

        string = s;
        length = s.length();
        index = new Integer[length];
        for (int i = 0; i < length; i++)
            index[i] = i;

        Arrays.sort(index, new Comparator<Integer>()
        {
            public int compare(Integer i, Integer j)
            {
                for (int k = 0; k < length; k++)
                {
                    int compare = Character.compare(getChar(i, k), getChar(j, k));
                    if (compare != 0)
                        return compare;
                }
                return 0;
            }
        });
    }

    private char getChar(int i, int j) //get the char at pos j of ith origin suffix array
    {
        return string.charAt((i + j) % length);
    }

    public int length()
    {
        return length;
    }

    public int index(int i)
    {
        if (i < 0 || i > length - 1)
            throw new IllegalArgumentException("i is outside prescribed range");
        return index[i];
    }
    
    public static void main(String[] args)
    {
        CircularSuffixArray c = new CircularSuffixArray("ABRACADABRA!");
        System.out.println(c.index(11));
    }
}