import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

public class MoveToFront 
{
    private static final int radix = 256;

    public static void encode()
    {
        char[] sequence = createArray();

        while (!BinaryStdIn.isEmpty())
        {
            char in = BinaryStdIn.readChar();
            char temp = sequence[0];
            char count;

            for (count = 0; in != temp; count++)
            {
                temp = sequence[count + 1];
                sequence[count + 1] = sequence[0];
                sequence[0] = temp;
            }

            BinaryStdOut.write(count);
        }

        BinaryStdOut.close();
    }

    public static void decode()
    {
        char[] sequence = createArray();

        while (!BinaryStdIn.isEmpty())
        {
            int in = BinaryStdIn.readChar();
            char temp = sequence[0];

            for (int i = 0; i < in; i++)
            {
                temp = sequence[i + 1];
                sequence[i + 1] = sequence[0];
                sequence[0] = temp;
            }

            BinaryStdOut.write(sequence[0]);
        }

        BinaryStdOut.close();
    }

    private static char[] createArray()
    {
        char[] array = new char[radix];

        for (char i = 0; i < radix; i++)
            array[i] = i;

        return array;
    }

    public static void main(String[] args)
    {
        if (args[0].equals("-"))
            encode();
        else
            if (args[0].equals("+"))
                decode();
    }
}