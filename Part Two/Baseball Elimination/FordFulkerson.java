import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;

public class FordFulkerson
{
    private boolean[] mark;
    private FlowEdge[] edgeTo;
    private double flow;

    public FordFulkerson(FlowNetwork g, int s, int t)
    {
        flow = 0;
        while (hasAugmentingPath(g, s ,t))
        {
            double bottle = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v))
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            
            for (int v = t; v != s; v = edgeTo[v].other(v))
                edgeTo[v].addResidualFlowTo(v, bottle);
            
            flow += bottle;
        }
    }

    public double value()
    {
        return flow;
    }

    public boolean inCut(int v)
    {
        return mark[v];
    }

    public boolean hasAugmentingPath(FlowNetwork g, int s, int t)
    {
        mark = new boolean[g.V()];
        edgeTo = new FlowEdge[g.V()];
        Queue<Integer> queue = new Queue<>();

        queue.enqueue(s);
        mark[s] = true;
        while (!queue.isEmpty())
        {
            int edgeIndex = queue.dequeue();
            
            for (FlowEdge edge : g.adj(edgeIndex))
            {
                int w = edge.other(edgeIndex);
                if (edge.residualCapacityTo(w) > 0 && !mark[w])
                {
                    mark[w] = true;
                    edgeTo[w] = edge;
                    queue.enqueue(w);
                }
            }
        }

        return mark[t];
    }
}