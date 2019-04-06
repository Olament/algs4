import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import java.util.Arrays;
import java.util.Comparator;


public class BurrowsWheeler
{
    public static void transform()
    {
        String string = BinaryStdIn.readString();
        int stringLength = string.length();
        CircularSuffixArray suffix = new CircularSuffixArray(string);
        StringBuilder bw = new StringBuilder(stringLength);
        int start = 0;

        for (int i = 0; i < stringLength; i++)
        {
            if (suffix.index(i) == 0)
                start = i;
            bw.append(string.charAt((suffix.index(i) + stringLength - 1) 
                % stringLength));
        }

        BinaryStdOut.write(start);
        BinaryStdOut.write(bw.toString());
        BinaryStdOut.close();
    }

    public static void inverseTransform()
    {
        /*
        int start = BinaryStdIn.readInt();
        String string = BinaryStdIn.readString();
        int[] next = new int[string.length()];
        char[] first = string.toCharArray();
        Arrays.sort(first);
        boolean[] isUsed = new boolean[string.length()];

        for (int i = 0; i < first.length; i++) //construct next array
            for (int j = 0; j < first.length; j++)
                if (string.charAt(j) == first[i] && !isUsed[j])
                {
                    isUsed[j] = true;
                    next[i] = j;
                    break;
                }

        int pointer = start;
        for (int i = 0; i < string.length(); i++)
        {
            BinaryStdOut.write(first[pointer]);
            pointer = next[pointer];
        }
        BinaryStdOut.close();
        */

        int start = BinaryStdIn.readInt();
        String string = BinaryStdIn.readString();
        Integer[] index = new Integer[string.length()];
        for (int i = 0; i < string.length(); i++)
            index[i] = i;

        Arrays.sort(index, new Comparator<Integer>()
        {
            public int compare(Integer i, Integer j)
            {
                return string.charAt(i) - string.charAt(j);
            }
        });

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < index.length; i++)
        {
            start = index[start];
            char c = string.charAt(start);
            builder.append(c);
        }

        BinaryStdOut.write(builder.toString());
        BinaryStdOut.close();
    }

    public static void main(String[] args)
    {
        if (args[0].equals("-"))
            transform();
        else
            if (args[0].equals("+"))
                inverseTransform();
    }
}