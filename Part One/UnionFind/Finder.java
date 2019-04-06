public class Finder
{
    public static void main(String[] args) 
    {
        WeightedQuickUnion q = new WeightedQuickUnion(10);

        q.union(4, 3);
        q.union(3, 8);
        q.union(6, 5);
        q.union(9, 4);
        q.union(2, 1);
        q.union(5, 0);
        q.union(7, 2);
        q.union(6, 1);
        q.union(7, 3);

        q.status();
    }
}