import java.util.Arrays;

public class WeightedQuickUnion
{
    private int[] id;
    private int[] size;

    public WeightedQuickUnion(int n)
    {
        id = new int[n];
        size = new int[n];

        for (int i = 0; i < n; i++)
        {
            id[i] = i;
            size[i] = 1;
        }
    }

    public int root(int i)
    {
        while (i != id[i])
        {
            id[i] = id[id[i]];
            i = id[i];
        }
        return i;
    }

    public void union(int p, int q)
    {
        int pRoot = root(p);
        int qRoot = root(q);

        if (qRoot != pRoot)
        {
            if (size[p] >= size[q])
            {
                id[qRoot] = pRoot;
                size[pRoot] += size[qRoot];
            }
            else
            {
                id[pRoot] = qRoot;
                size[qRoot] += size[pRoot];
            }
        }
    }

    public boolean connected(int p, int q)
    {
        return root(p) == root(q);
    }

    public void status()
    {
        System.out.println(Arrays.toString(id));
        System.out.println(Arrays.toString(size));
    }
}