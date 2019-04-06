import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;


import java.util.Arrays;

public class BaseballElimination
{
    private int number; //number of team
    private int numberOfVertex;
    private String[] team;
    private int[] win;
    private int[] loss;
    private int[] remain;
    private int[][] game;
    private FlowNetwork flow;
    private String currentTeam;

    public BaseballElimination(String filename)
    {
        In in = new In(filename);
        number = in.readInt();

        team = new String[number];
        win = new int[number];
        loss = new int[number];
        remain = new int[number];
        game = new int[number][number];

        for (int i = 0; i < number; i++)
        {
            team[i] = in.readString();
            win[i] = in.readInt();
            loss[i] = in.readInt();
            remain[i] = in.readInt();
            for (int j = 0; j < number; j++)
                game[i][j] = in.readInt();
        }

        numberOfVertex = matchup(number) + 2 + number;
    }

    private static int matchup(int n)
    {
        int matchup = 0;
        for (int i = 0; i < n; i++)
            for (int j = i + 1; j < n; j++)
                matchup++;
        return matchup;
    }

    public int numberOfTeams()
    {
        return number;
    }

    public Iterable<String> teams()
    {
        return Arrays.asList(team);
    }

    private int find(String teamName)
    {
        for (int i = 0; i < number; i++)
            if (teamName.equals(team[i]))
                return i;
        return -1;
    }

    public int wins(String team)
    {
        if (find(team) == -1)
            throw new IllegalArgumentException("Team not find");
        return win[find(team)];
    }

    public int losses(String team)
    {
        if (find(team) == -1)
            throw new IllegalArgumentException("Team not find");
        return loss[find(team)];
    }

    public int remaining(String team)
    {
        if (find(team) == -1)
            throw new IllegalArgumentException("Team not find");
        return remain[find(team)];
    }

    public int against(String team1, String team2)
    {
        if (find(team1) == -1 || find(team2) == -1)
            throw new IllegalArgumentException("Team not find");
        return game[find(team1)][find(team2)];
    }

    public boolean isEliminated(String team)
    {
        if (find(team) == -1)
            throw new IllegalArgumentException();

        if (trivialCheck(team))
            return true;
        
        //if (currentTeam == null || !currentTeam.equals(team))
            buildNetwork(team);
        //System.out.println(flow.toString());
        FordFulkerson maxFlow = new FordFulkerson(flow, 0, numberOfVertex - 1);
        int sum = 0;
        for (int i = 0; i < number; i++)
            for (int j = i + 1; j < number; j++)
                sum += game[i][j];

        return maxFlow.value() < sum;
    }

    private boolean trivialCheck(String team)
    {
        int teamIndex = find(team);
        if (win[teamIndex] + remain[teamIndex] < win[find(findMostWin())])
            return true;
        return false;
    }

    private String findMostWin()
    {
        int mostWin = -1;
        String mostWinTeam = "";
        for (int i = 0; i < number; i++)
            if (win[i] > mostWin)
            {
                mostWin = win[i];
                mostWinTeam = team[i];
            }

        return mostWinTeam;
    }

    
    public Iterable<String> certificateOfElimination(String team)
    {
        if (!isEliminated(team))
            return null;

        if (trivialCheck(team))
        {
            Bag<String> bag = new Bag<>();
            bag.add(findMostWin());
            return bag;
        } 

        boolean[] mark = new boolean[numberOfVertex];
        Queue<Integer> queue = new Queue<>();

        queue.enqueue(0);
        mark[0] = true;
        while (!queue.isEmpty())
        {
            int currentVertex = queue.dequeue();
            for (FlowEdge edge : flow.adj(currentVertex))
                if (edge.residualCapacityTo(edge.other(currentVertex)) != 0 
                    && !mark[edge.other(currentVertex)])
                {
                    queue.enqueue(edge.other(currentVertex));
                    mark[edge.other(currentVertex)] = true; 
                }
        }

        Bag<String> bag = new Bag<>();
        for (int i = 1; i <= number; i++)
            if (mark[i] && !this.team[i - 1].equals(team))
                bag.add(this.team[i - 1]);

        return bag;
    }

    private void buildNetwork(String team)
    {
        currentTeam = team;
        int teamNumber = find(team);
        flow = new FlowNetwork(numberOfVertex);

        int indexOfMatch = 1;
        for (int i = 0; i < number; i++)
            for (int j = i + 1; j < number; j++)
            {
                FlowEdge edge1 = new FlowEdge(0, number + indexOfMatch, game[i][j]);
                FlowEdge edge2 = new FlowEdge(number + indexOfMatch, i + 1,
                                                Double.POSITIVE_INFINITY);
                FlowEdge edge3 = new FlowEdge(number + indexOfMatch, j + 1,
                                                Double.POSITIVE_INFINITY);
                flow.addEdge(edge1);
                flow.addEdge(edge2);
                flow.addEdge(edge3);
                indexOfMatch++;
            }

        int maxWin = win[teamNumber] + remain[teamNumber];
        for (int i = 1; i <= number; i++)
        {
            FlowEdge edge = new FlowEdge(i, numberOfVertex - 1, 
                            Math.max(0, maxWin - win[i - 1]));
            flow.addEdge(edge);
        }
    }

    public String toString()
    {
        return flow.toString();
    }

    public static void main(String[] args) 
    {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) 
        {
            if (division.isEliminated(team)) 
            {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) 
                    StdOut.print(t + " ");
                StdOut.println("}");
            }
            else
                StdOut.println(team + " is not eliminated");
        }
    }
}