import edu.princeton.cs.algs4.Bag;

public class FlowNetwork
{
    private Bag<FlowEdge>[] adj;
    private final int vertex;

    public FlowNetwork(int v)
    {
        vertex = v;
        adj = (Bag<FlowEdge>[]) new Bag[v];
        for (int i = 0; i < v; i++)
            adj[i] = new Bag<FlowEdge>();
    }

    public void addEdge(FlowEdge edge)
    {
        int from = edge.from();
        int to = edge.to();
        adj[from].add(edge);
        adj[to].add(edge);
    }

    public Iterable<FlowEdge> adj(int vertex)
    {
        return adj[vertex];
    }

    public int v()
    {
        return vertex;
    }

    public Iterable<FlowEdge> edges()
    {
        Bag<FlowEdge> edges = new Bag<FlowEdge>();
        for (int i = 0; i < vertex; i++)
            for (FlowEdge edge : adj[i])
                edges.add(edge);
        return edges;
    }

    public String toString()
    {
        String string = "";
        for (int i = 0; i < vertex; i++)
            for (FlowEdge edge : adj[i])
                if (edge.from() == i)
                    string += edge.toString() + "\n";
        return string;
    }
}