import java.util.Arrays;

public class BruteCollinearPoints
{
    private Point[] points;

    public BruteCollinearPoints(Point[] points)
    {
        if (points == null)
            throw new IllegalArgumentException();
        checkRepeat(points);

        this.points = new Point[points.length];
        for (int i = 0; i < points.length; i++)
            this.points[i] = points[i];
    }

    public int numberOfSegments()
    {
        return segments().length;
    }

    public LineSegment[] segments()
    {
        Arrays.sort(points);
        int length = points.length;
        for (int i = 0; i < length; i++)
            for (int j = i + 1; j < length; j++)
                for (int k = j + 1; k < length; k++)
                    for (int l = k + 1; l < length; l++)
                        if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])&& 
                            points[j].slopeTo(points[k]) == points[k].slopeTo(points[l]))
                            return new LineSegment[] {new LineSegment(points[i], points[l])};
        return new LineSegment[0];
    }

    public void checkRepeat(Point[] points)
    {
        for (int i = 0; i < points.length; i++)
            for (int j = i + 1; j < points.length; j++)
                if (points[i].compareTo(points[j]) == 0 || points[i] == null
                    || points[j] == null)
                    throw new IllegalArgumentException();
    }
}