import java.util.LinkedList;

public class Board
{
    private final int[][] blocks;
    private final int dimension;
    private final int blankRow;
    private final int blankCol;

    public Board(int[][] aBlocks)
    {
        dimension = aBlocks.length;
        blocks = new int[dimension][dimension];

        int aBlankRow = 0;
        int aBlankCol = 0;

        for (int row = 0; row < dimension; row++)
            for (int col = 0; col < dimension; col++)
            {
                blocks[row][col] = aBlocks[row][col];
                if (aBlocks[row][col] == 0)
                {
                    aBlankCol = col;
                    aBlankRow = row;
                }
            }

        blankRow = aBlankRow;
        blankCol = aBlankCol;
    }

    public int dimension()
    {
        return dimension;
    }

    public int hamming()
    {
        int expected = 1;
        int count = 0;

        for (int row = 0; row < dimension; row++)
            for(int col = 0; col < dimension; col++)
            {
                if (blocks[row][col] != expected)
                    count++;
                expected++;
            }

        return --count;
    }

    public int manhattan()
    {
        int count = 0;

        for (int row = 0; row < dimension; row++)
            for (int col = 0; col < dimension; col++)
                if (blocks[row][col] != 0)
                    count += getDistance(row, col, 
                        getRow(blocks[row][col]), getCol(blocks[row][col]));

        return count;
    }

    private int getDistance(int x, int y, int i, int j)
    {
        return Math.abs(i - x) + Math.abs(j - y);
    }

    private int getRow(int num)
    {
        return (num - 1) / dimension;
    }

    private int getCol(int num)
    {
        return (num - 1) % dimension;
    }

    public boolean isGoal()
    {
        return hamming() == 0;
    }

    public Board twin()
    {
        int[][] twin = copyOf(blocks);

        if (blankRow != 0)
            exchange(twin, 0, 0, 0, 1);
        else
            exchange(twin, 1, 0, 1, 1);

        return new Board(twin);
    }

    private void exchange(int[][] array, int x, int y, int i, int j)
    {
        int temp = array[x][y];
        array[x][y] = array[i][j];
        array[i][j] = temp;
    }

    private int[][] copyOf(int[][] array)
    {
        int[][] copy = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++)
            for (int j = 0; j < dimension; j++)
                copy[i][j] = array[i][j];

        return copy;
    }

    public boolean equals(Object y)
    {
        if (y == this)
            return true;

        if (y == null || !(y instanceof Board) || ((Board) y).dimension() != dimension)
            return false;

        for (int row = 0; row < dimension; row++)
            for (int col = 0; col < dimension; col++)
                if (((Board) y).blocks[row][col] != this.blocks[row][col])
                    return false;

        return true;
    }

    public Iterable<Board> neighbors()
    {
        LinkedList<Board> neighbors = new LinkedList<>();

        if (blankCol > 0)
        {
            int[][] cloned = copyOf(blocks);
            exchange(cloned, blankRow, blankCol, blankRow, blankCol - 1);
            neighbors.add(new Board(cloned));
        }
        if (blankCol < dimension - 1)
        {
            int[][] cloned = copyOf(blocks);
            exchange(cloned, blankRow, blankCol, blankRow, blankCol + 1);
            neighbors.add(new Board(cloned));
        }
        if (blankRow > 0)
        {
            int[][] cloned = copyOf(blocks);
            exchange(cloned, blankRow, blankCol, blankRow - 1, blankCol);
            neighbors.add(new Board(cloned));
        }
        if (blankRow < dimension - 1)
        {
            int[][] cloned = copyOf(blocks);
            exchange(cloned, blankRow, blankCol, blankRow + 1, blankCol);
            neighbors.add(new Board(cloned));
        }

        return neighbors;
    }

    public String toString()
    {
        String str = "";

        str += Integer.toString(dimension) + "\n";
        for (int row = 0; row < dimension; row++)
        {
            for (int col = 0; col < dimension; col++)
                str += String.format("%2d ", blocks[row][col]);
            str += "\n";
        }

        return str;
    }

    public static void main(String[] args) 
    {
       int[][] blocks = new int[][]{
        {1, 0},
        {2, 3},
        };

        Board board = new Board(blocks);
        System.out.println(board.dimension());
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println(board.isGoal());
        System.out.println(board.toString());
        System.out.println(board.twin().toString());
    }
}