import java.util.LinkedList;
import java.util.List;

public class Board
{
    private final int[][] board;
    private final int hamming;
    private final int manhattan;
    private final int dimension;

    public Board(int[][] blocks)
    {
        board = copyOf(blocks);
        dimension = blocks.length;
        hamming = getHamming(blocks);
        manhattan = getManhattan(blocks);
    }

    private int getHamming(int[][] blocks)
    {
        int hamming = 0;

        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (blocks[i][j] != i * dimension + (j + 1))
                    if (i != dimension - 1 || j != dimension - 1)
                        hamming++;

        return hamming;
    }

    private int getManhattan(int[][] blocks)
    {
        int manhattan = 0;

        for (int row = 0; row < dimension; row++)
            for (int col = 0; col < dimension; col++)
                if (blocks[row][col] != 0)
                {
                    int expectRow = (blocks[row][col] / dimension) +
                        (blocks[row][col] % dimension == 0 ? 0 : 1);
                    int expectCol = blocks[row][col] % dimension == 0 ? 
                        dimension : blocks[row][col] % dimension;
                    manhattan += getDistance(row + 1, col + 1, expectRow, expectCol);
                    /*
                    System.out.println(blocks[row][col]);
                    System.out.printf("row: %d, col: %d\n", row + 1, col + 1);
                    System.out.printf("expectRow: %d, expectCol: %d\n", 
                        expectRow, expectCol);
                    System.out.println("manhattan: " + 
                        getDistance(row + 1, col + 1, expectRow, expectCol));
                    System.out.println();
                    */
                }
        return manhattan;
    }

    private int getDistance(int i, int j, int x, int y)
    {
        return Math.abs(x - i) + Math.abs(y - j);
    }

    public int dimension()
    {
        return dimension;
    }

    public int hamming()
    {
        return hamming;
    }

    public int manhattan()
    {
        return manhattan;
    }

    public boolean isGoal()
    {
        return hamming() == 0;
    }

    public Board twin()
    {
        int[][] twin = copyOf(board);

        if (twin[0][0] != 0 && twin[1][0] != 0)
            exchange(twin, 0, 0, 1, 0);
        else
            exchange(twin, 0, 1, 1, 1);
                
        return new Board(twin);
    }

    private int[][] copyOf(int[][] matrix) 
    {
        int length = matrix.length;
        int[][] clone = new int[length][length];

        for (int i = 0; i < length; i++)
            for (int j = 0; j < length; j++)
                clone[i][j] = matrix[i][j];

        return clone;
    }

    private void exchange(int[][] array, int i, int j, int x, int y)
    {
        int temp = array[i][j];
        array[i][j] = array[x][y];
        array[x][y] = temp;
    }

    public boolean equals(Object y)
    {
        Board anotherBoard = (Board) y;

        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (board[i][j] != anotherBoard.board[i][j])
                    return false;
        return true;
    }

    public Iterable<Board> neighbors()
    {
        List<Board> neighbors = new LinkedList<>();

        int blankCol = 0;
        int blankRow = 0;

        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                if (board[i][j] == 0)
                {
                    blankCol = i;
                    blankRow = j;
                    break;
                }

        if (blankCol + 1 < dimension)
        {
            int[][] cloned = copyOf(board);
            exchange(cloned, blankCol, blankRow, blankCol + 1, blankRow);
            neighbors.add(new Board(cloned));
        }

        if (blankCol - 1 >= 0)
        {
            int[][] cloned = copyOf(board);
            exchange(cloned, blankCol, blankRow, blankCol - 1, blankRow);
            neighbors.add(new Board(cloned));
        }

        if (blankRow + 1 < dimension)
        {
            int[][] cloned = copyOf(board);
            exchange(cloned, blankCol, blankRow, blankCol, blankRow + 1);
            neighbors.add(new Board(cloned));
        }

        if (blankRow - 1 >= 0)
        {
            int[][] cloned = copyOf(board);
            exchange(cloned, blankCol, blankRow, blankCol, blankRow - 1);
            neighbors.add(new Board(cloned));
        }

        return neighbors;
    }

    public String toString()
    {
        int n = dimension * dimension - 1;
        int figures = 0;

        while (n > 0)
        {
            n /= 10;
            figures++;
        }

        String string = "";

        string += String.format("%" + Integer.toString(figures) + "d\n", dimension);
        for (int i = 0; i < dimension; i++)
        {
            for (int j = 0; j < dimension; j++)
                string += String.format("%" + Integer.toString(figures) + "d ", 
                    board[i][j]);
            string += '\n';
        }

        return string;
    }
}