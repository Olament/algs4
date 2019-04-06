import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints 
{
    private Point[] points;

    public FastCollinearPoints(Point[] points)
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
        ArrayList<Point> currentLine = new ArrayList<Point>();

        for (int i = 0; i < points.length; i++)
        {
            ArrayList<Point> currentPoints = new ArrayList<Point>();
            Arrays.sort(points, points[i].slopeOrder());

            for (int j = 1; j < points.length; j++)
            {
                double preSlope = points[i].slopeTo(points[j - 1]);
                if (points[i].slopeTo(points[j]) == preSlope)
                    currentPoints.add(points[j]);
                else
                    if (currentPoints.size() < 3)
                        currentPoints.clear();
                    else
                    {
                        currentPoints.add(points[i]);
                        Collections.sort(currentPoints);

                        boolean exist = false;
                        for (Point p : currentLine)
                            if (p.compareTo(currentPoints.get(0)) == 0)
                            {
                                exist = true;
                                break;
                            }
                        if (!exist)
                        {
                            currentLine.add(currentPoints.get(0));
                            currentLine.add(currentPoints.get(currentPoints.size()));
                        }
                    }
            }
        }

        LineSegment[] lines = new LineSegment[currentLine.size() / 2];
        for (int i = 0; i < lines.length; i++)
        {
            lines[i] = new LineSegment(currentLine.get(i * 2), currentLine.get(i * 2 + 1));
        }

        return lines;
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