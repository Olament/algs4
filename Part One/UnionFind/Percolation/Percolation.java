import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    private int openSites;
    private boolean[][] status;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF full;
    private int size;

    public Percolation(int n)
    {
        if (n <= 0)
            throw new IllegalArgumentException("n should bigger than zero");

        status = new boolean[n][n];
        grid = new WeightedQuickUnionUF(n * n + 2);
        full = new WeightedQuickUnionUF(n * n + 1);
        openSites = 0;
        size = n;

        for (int i = 1; i <= size; i++)
        {
            grid.union(0, i);
            full.union(0, i);
            grid.union(size * size + 1, size * (size - 1) + i);
        }
    }

    public void open(int row, int col)
    {
        if (row < 1 || col < 1 || row > size || col >size)
            throw new IllegalArgumentException("invalid input");

        if (!isOpen(row, col))
        {
            status[row - 1][col -1] = true;
            openSites++;

            if (row != 1 && isOpen(row - 1, col))
            {
                full.union(getNumber(row, col), getNumber(row - 1, col));
                grid.union(getNumber(row, col), getNumber(row - 1, col));
            }
            if (row != size && isOpen(row + 1, col))
            {
                full.union(getNumber(row, col), getNumber(row + 1, col));
                grid.union(getNumber(row, col), getNumber(row + 1, col));
            }
            if (col != 1 && isOpen(row, col - 1))
            {
                full.union(getNumber(row, col), getNumber(row, col - 1));
                grid.union(getNumber(row, col), getNumber(row, col - 1));
            }
            if (col != size && isOpen(row, col + 1))
            {
                full.union(getNumber(row, col), getNumber(row, col +1));
                grid.union(getNumber(row, col), getNumber(row, col +1));
            }
        }
    }

    public boolean isOpen(int row, int col)
    {
        if (row < 1 || col < 1 || row > size || col >size)
            throw new IllegalArgumentException("invalid input");

        return status[row - 1][col - 1];
    }

    public boolean isFull(int row, int col)
    {
        if (row < 1 || col < 1 || row > size || col >size)
            throw new IllegalArgumentException("invalid input");
        
        return isOpen(row, col) && full.connected(0, getNumber(row, col));
    }

    public int numberOfOpenSites()
    {
        return openSites;
    }

    public boolean percolates()
    {
        return grid.connected(0, size * size + 1);
    }

    private int getNumber(int row, int col)
    {
        return size * (row - 1) + col;
    }

}