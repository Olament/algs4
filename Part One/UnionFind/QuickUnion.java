public class QuickUnion
{
    private int[] id;

    public QuickUnion(int n)
    {
        id = new int[n];

        for (int i = 0; i < n; i++)
            id[i] = i;
    }

    public int root(int i)
    {
        while (i != id[i])
            i = id[i];

        return i;
    }

    public void union(int p, int q)
    {
        id[root(p)] = id[root(q)];
    }

    public boolean connected(int p, int q)
    {
        return root(p) == root(q);
    }
}