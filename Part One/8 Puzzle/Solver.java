import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.LinkedList;
import java.lang.IllegalArgumentException;

public class Solver
{
    private Node lastNode;

    private class Node implements Comparable<Node>
    {
        private final int moves;
        private final int priority;
        private final Node prev;
        private final Board curr;

        public Node(Board initial)
        {
            moves = 0;
            prev = null;
            curr = initial;
            priority = initial.manhattan();
        }

        public Node(Board board, Node previous)
        {
            this.moves = previous.moves + 1;
            prev = previous;
            curr = board;
            priority = this.moves + board.manhattan();
        }

        public int compareTo(Node another)
        {
            return priority - another.priority;
        }
    }

    public Solver(Board initial)
    {
        if (initial == null)
            throw new IllegalArgumentException();

        MinPQ<Node> steps = new MinPQ<>();
        steps.insert(new Node(initial));

        MinPQ<Node> twinSteps = new MinPQ<>();
        twinSteps.insert(new Node(initial.twin()));

        while (true)
        {
            lastNode = next(steps);
            if (lastNode != null || next(twinSteps) != null)
                break;
        }
    }

    private Node next(MinPQ<Node> nodes)
    {
        if (nodes.isEmpty())
            return null;

        Node nextStep = nodes.delMin();
        if (nextStep.curr.isGoal())
            return nextStep;

        for (Board neighbor : nextStep.curr.neighbors())
            if (nextStep.prev == null || !neighbor.equals(nextStep.prev.curr))
                nodes.insert(new Node(neighbor, nextStep));

        return null;
    }

    public boolean isSolvable()
    {
        return lastNode != null;
    }

    public int moves()
    {
        return isSolvable() ? lastNode.moves : -1;
    }

    public Iterable<Board> solution()
    {
        if (!isSolvable())
            return null;

        LinkedList<Board> solution = new LinkedList<>();
        Node node = lastNode;

        while (node != null)
        {
            solution.addFirst(node.curr);
            node = node.prev;
        }

        return solution;
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