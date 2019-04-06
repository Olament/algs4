import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.LinkedList;

public class KdTree
{
    private Node root;
    private int size;

    private static class Node
    {
        private Point2D point;
        private Node left;
        private Node right;
        private RectHV rect;

        public Node(Point2D aPoint, RectHV aRect)
        {
            point = aPoint;
            rect = aRect;
        }
    }

    public boolean isEmpty()
    {
        return root == null;
    }

    public int size()
    {
        return size;
    }
    
    public void insert(Point2D p)
    {
        root = insert(root, p, true, new RectHV(0.0, 0.0, 1.0, 1.0));
    }

    private Node insert(Node node, Point2D p, boolean isVertical, RectHV rect)
    {
        if (node == null)
        {
            size++;
            return new Node(p, rect);
        }
        if (node.point.compareTo(p) == 0)
            return node;

        if (isVertical)
        {
            if (p.x() - node.point.x() >= 0)
            {
                RectHV newRect = 
                    new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
                node.right = insert(node.right, p, !isVertical, newRect);
            }
            else
            {
                RectHV newRect = 
                    new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
                node.left = insert(node.left, p, !isVertical, newRect);
            }
        }
        else
        {
            if (p.y() - node.point.y() >= 0)
            {
                RectHV newRect = 
                    new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
                node.right = insert(node.right, p, !isVertical, newRect);
            }
            else
            {
                RectHV newRect = 
                    new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
                node.left = insert(node.left, p, !isVertical, newRect);
            } 
        }

        return node;
    }

    public boolean contains(Point2D p)
    {
        Node node = root;
        boolean isVertical = true;

        while (node != null)
        {
            if (node.point.compareTo(p) == 0)
                return true;

            if (isVertical)
                node = (p.x() - node.point.x() >= 0) ? node.right : node.left;
            else
                node = (p.y() - node.point.y() >= 0) ? node.right : node.left;

            isVertical = !isVertical;
        }

        return false;
    }

    public void draw()
    {
        draw(root, true);
    }

    private void draw(Node node, boolean isVertical)
    {
        if (node.right != null) draw(node.right, !isVertical);
        if (node.left != null) draw(node.left, !isVertical);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        StdDraw.point(node.point.x(), node.point.y());

        double xmin, ymin, xmax, ymax;
        if (isVertical)
        {
            StdDraw.setPenColor(StdDraw.RED);
            xmin = node.point.x();
            xmax = node.point.x();
            ymin = node.rect.ymin();
            ymax = node.rect.ymax();
        } 
        else
        {
            StdDraw.setPenColor(StdDraw.BLUE);
            ymin = node.point.y();
            ymax = node.point.y();
            xmin = node.rect.xmin();
            xmax = node.rect.xmax();
        }
        StdDraw.setPenRadius();
        StdDraw.line(xmin, ymin, xmax, ymax);
    }

    public Iterable<Point2D> range(RectHV rect)
    {
        if (isEmpty())
            return null;
        LinkedList<Point2D> list = new LinkedList<>();
        range(root, rect, list);
        return list;
    }

    private void range(Node node, RectHV rect, LinkedList<Point2D> list)
    {
        if (rect.contains(node.point))
            list.add(node.point);
        if (node.right != null && node.right.rect.intersects(rect))
            range(node.right, rect, list);
        if (node.left != null && node.left.rect.intersects(rect))
            range(node.left, rect, list);
    }

    public Point2D nearest(Point2D p)
    {
        if (isEmpty())
            return null;
        return nearest(p, root.point, root, true);
    }

    private Point2D nearest(Point2D target, Point2D closest, Node node, boolean isVertical)
    {
        if (node == null)
            return closest;

        double closestDist = closest.distanceTo(target);

        if (node.rect.distanceTo(target) < closestDist) 
        {
            double nodeDist = node.point.distanceTo(target);
            if (nodeDist < closestDist)
                closest = node.point;

            double distance = isVertical ? target.x() - node.point.x() : 
                target.y() - node.point.y();

            if (distance > 0) 
            {
                closest = nearest(target, closest, node.left, !isVertical);
                closest = nearest(target, closest, node.right, !isVertical);
            } 
            else 
            {
                closest = nearest(target, closest, node.right, !isVertical);
                closest = nearest(target, closest, node.left, !isVertical);
            }
        }
        
        return closest;
    }
}