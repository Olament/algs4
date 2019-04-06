import java.util.TreeSet;
import java.util.LinkedList;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET
{
    private TreeSet<Point2D> tree;

    public PointSET()
    {
        tree = new TreeSet<>();
    }

    public boolean isEmpty()
    {
        return tree.isEmpty();
    }

    public int size()
    {
        return tree.size();
    }

    public void insert(Point2D p)
    {
        if (!contains(p))
            tree.add(p);
    }   

    public boolean contains(Point2D p)
    {
        return tree.contains(p);
    }

    public void draw()
    {
        for (Point2D p : tree)
        {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            StdDraw.point(p.x(), p.y());
        }
    }

    public Iterable<Point2D> range(RectHV rect)
    {
        LinkedList<Point2D> list = new LinkedList<>();

        for (Point2D p : tree)
            if (rect.contains(p))
                list.add(p);

        return list;
    }

    public Point2D nearest(Point2D p)
    {
        Point2D nearest = tree.first();

        for (Point2D point : tree)
            if (point.distanceTo(p) < nearest.distanceTo(p))
                nearest = point;

        return nearest;
    }
}