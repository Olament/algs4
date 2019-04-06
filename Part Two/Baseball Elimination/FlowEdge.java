public class FlowEdge
{
    private final int from;
    private final int to;
    private final int capacity;
    private int flow;

    public FlowEdge(int v, int w, int capacity)
    {
        from = v;
        to = w;
        this.capacity = capacity;
    }

    public int from()
    {
        return from;
    }

    public int to()
    {
        return to;
    }

    public int other(int v)
    {
        if (v == from) 
            return to;
        else 
            if (v == to) 
                return from;
            else 
                throw new IllegalArgumentException();
    }

    public int capacity()
    {
        return capacity;
    }

    public int flow()
    {
        return flow;
    }

    public int residualCapacityTo(int v)
    {
        if (v == from)
            return flow;
        else
            if (v == to)
                return capacity - flow;
            else
                throw new IllegalArgumentException();
    }

    public void addResidualFlowTo(int v, int delta)
    {
        if (v == from)
            flow -= delta;
        else
            if (v == to)
                flow += delta;
            else
                throw new IllegalArgumentException();
    }

    public String toString()
    {
        return "" + from + " -> " + to + " " + flow + "/" + capacity;
    }
}