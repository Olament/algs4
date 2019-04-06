import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.LinkedList;

public class Solver
{
    private Node lastNode;

    private class Node implements Comparable<Node>
    {
        public Board curr;
        public Node prev;
        public int move;

        public Node(Board aCurr, Node aPrev)
        {
            curr = aCurr;
            prev = aPrev;
            move = aPrev.move + 1;
        }

        public Node(Board aCurr)
        {
            curr = aCurr;
            move = 0;
        }

        public int compareTo(Node another)
        {
            return (this.curr.manhattan() - another.curr.manhattan()) +
                (this.move - another.move);
        }
    }

    public Solver(Board initial)
    {
        MinPQ<Node> main = new MinPQ<>();
        MinPQ<Node> twin = new MinPQ<>();

        lastNode = new Node(initial);
        main.insert(lastNode);
        twin.insert(new Node(initial.twin()));

        while (true)
        {
            lastNode = next(main);
            if (lastNode != null || next(twin) != null)
                return;
        }
    }

    private Node next(MinPQ<Node> node)
    {
        if (node.isEmpty())
            return null;
        Node current = node.delMin();
        //System.out.println(current.curr.toString());
        if (current.curr.isGoal())
            return current;
        for (Board nextBoard : current.curr.neighbors())
            if (current.prev == null || !current.prev.equals(nextBoard))
                node.insert(new Node(nextBoard, current));
        return null;
    }
    
    public boolean isSolvable()
    {
        return lastNode != null;
    }

    public int moves()
    {
        if (lastNode == null)
            return -1;
        return lastNode.move;
    }

    public Iterable<Board> solution()
    {
        if (!isSolvable())
            return null;

        LinkedList<Board> moves = new LinkedList<>();
        Node node = lastNode;

        for (int i = lastNode.move; i >= 0; i--)
        {
            moves.addFirst(node.curr);
            node = node.prev;
        }

        return moves;
    }

    public static void main(String[] args) 
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        //System.out.println(initial);
        //System.out.println(initial.twin());

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else 
        {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}