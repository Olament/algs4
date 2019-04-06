import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point>
{
    private final int x;
    private final int y;

    public Point(int x, int y)
    {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    public void draw() 
    {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    public void drawTo(Point that)
    {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public int compareTo(Point that)
    {
       int diff = 0;

        if (that.y == this.y)
            diff = this.x - that.x;
        else
            diff = this.y - that.y;

        return diff;
    }

    public double slopeTo(Point that)
    {
        double slope = 0;

        if (this.x == that.x)
            if (this.y == that.y)
                slope = Double.POSITIVE_INFINITY;
            else
                slope = Double.NEGATIVE_INFINITY;
        else
            if (this.y == that.y)
                slope = 0;
            else
                slope = (that.y - this.y) / (that.x - this.x);

        return slope;
    }

    public Comparator<Point> slopeOrder()
    {
        return new Comparator<Point>()
        {
            public int compare(Point point1, Point point2)
            {
                double slopeDiff = slopeTo(point1) - slopeTo(point2);

                if (slopeDiff > 0)
                    return 1;
                else 
                    if (slopeDiff < 0)
                        return -1;
                return 0;
            }
        };
    }

    public String toString() 
    {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
}