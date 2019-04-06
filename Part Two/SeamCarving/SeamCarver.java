import edu.princeton.cs.algs4.Picture;
import java.awt.Color;

public class SeamCarver 
{
    private Picture picture;
    private double[][] energy;
    private int width;
    private int height;

    public SeamCarver(Picture aPicture) 
    {
        if (aPicture == null)
            throw new IllegalArgumentException(); 

        picture = copyOfPicture(aPicture);
        energy = new double[picture.width()][picture.height()];
        width = picture.width();
        height = picture.height();

        reEnergy();
    }

    private void reEnergy()
    {
        for(int i = 0; i < picture.width(); i++)
            for (int j = 0; j < picture.height(); j++)
                energy[i][j] = energy(i, j);
    }

    private boolean isBoarder(int col, int row)
    {
        if(col == 0 || row == 0 || col == width() - 1 || row == height() - 1)
            return true;
        return false;
    }

    private Picture copyOfPicture(Picture aPicture)
    {
        Picture picture = new Picture(aPicture.width(), aPicture.height());
        for (int i = 0; i < aPicture.width(); i++)
            for (int j = 0; j < aPicture.height(); j++)
                picture.set(i, j, aPicture.get(i, j));
        return picture;
    }

    public Picture picture()
    {
        return copyOfPicture(picture);
    }

    public int width()
    {
        return width;
    }

    public int height()
    {
        return height;
    }

    public double energy(int x, int y)
    {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1)
            throw new IllegalArgumentException(); 
        if (isBoarder(x, y))
            return 1000.0;

        Color leftColor = picture.get(x - 1, y);
        Color rightColor = picture.get(x + 1, y);
        Color upperColor = picture.get(x, y - 1);
        Color lowerColor = picture.get(x, y + 1);

        double deltaX = Math.pow(rightColor.getRed() - leftColor.getRed(), 2)
                     + Math.pow(rightColor.getGreen() - leftColor.getGreen(), 2)
                     + Math.pow(rightColor.getBlue() - leftColor.getBlue(), 2);

        double deltaY = Math.pow(lowerColor.getRed() - upperColor.getRed(), 2)
                     + Math.pow(lowerColor.getGreen() - upperColor.getGreen(), 2)
                     + Math.pow(lowerColor.getBlue() - upperColor.getBlue(), 2);

        return Math.sqrt(deltaY + deltaX);
    }

    public int[] findHorizontalSeam()
    {
        double[][] distTo = new double[width()][height()];
        int[][] edgeTo = new int[width()][height()];
        for (int i = 0; i < width(); i++)
            for (int j = 0; j < height(); j++)
                distTo[i][j] = Double.POSITIVE_INFINITY;
        for (int i = 0; i < height(); i++)
            distTo[0][i] = energy[0][i];

        for (int i = 0; i < width() - 1; i++)
            for (int j = 0; j < height(); j++)
                for (int k = -1; k <= 1; k++)
                {
                    if (j + k < 0 || j + k >= height())
                        continue;
                    if (distTo[i][j] + energy[i + 1][j + k] < distTo[i + 1][j + k])
                    {
                        distTo[i + 1][j + k] = distTo[i][j] + energy[i + 1][j + k];
                        edgeTo[i + 1][j + k] = k;
                    }
                }

        double shortest = distTo[width() - 1][0];
        int shorestIndex = 0;
        for (int i = 0; i < height(); i++)
            if (distTo[width() - 1][i] < shortest)
            {
                shortest = distTo[width() - 1][i];
                shorestIndex = i;
            }
        int[] seam = new int[width()];
        seam[width() - 1] = shorestIndex;
        for (int i = width() - 2; i >= 0; i--)
            seam[i] = seam[i + 1] - edgeTo[i + 1][seam[i + 1]];

        return seam;
    }

    public int[] findVerticalSeam()
    {
        double[][] distTo = new double[width()][height()];
        int[][] edgeTo = new int[width()][height()];
        for (int i = 0; i < width(); i++)
            for (int j = 0; j < height(); j++)
                distTo[i][j] = Double.POSITIVE_INFINITY;
        for (int i = 0; i < width(); i++)
            distTo[i][0] = energy[i][0];

        for (int j = 0; j < height() - 1; j++)
            for (int i = 0; i < width(); i++)
                for (int k = -1; k <= 1; k++)
                {
                    if (i + k < 0 || i + k >= width())
                        continue;
                    if (distTo[i][j] + energy[i + k][j + 1] < distTo[i + k][j + 1])
                    {
                        distTo[i + k][j + 1] = distTo[i][j] + energy[i + k][j + 1];
                        edgeTo[i + k][j + 1] = k;
                    }
                }

        double shortest = distTo[0][height() - 1];
        int shorestIndex = 0;
        for (int i = 0; i < width(); i++)
            if (distTo[i][height() - 1] < shortest)
            {
                shortest = distTo[i][height() - 1];
                shorestIndex = i;
            }
        int[] seam = new int[height()];
        seam[height - 1] = shorestIndex;
        for (int i = height() - 2; i >= 0; i--)
            seam[i] = seam[i + 1] - edgeTo[seam[i + 1]][i + 1];

        return seam;
    }

    public void removeHorizontalSeam(int[] seam)
    {
        if (seam == null || height() <= 1 || seam.length != width())
            throw new IllegalArgumentException();
        for (int i = 0; i < seam.length; i++)
        {
            if (i > 0 && Math.abs(seam[i - 1] - seam[i]) > 1)
                throw new IllegalArgumentException();
            if (seam[i] < 0 || seam[i] > height() - 1)
                throw new IllegalArgumentException();
        }

        Picture aPicture = new Picture(width(), height() - 1);
        int hPointer = 0;
        for (int i = 0; i < width(); i++)
        {
            for (int j = 0; j < height(); j++)
                if (seam[i] != j)
                    aPicture.set(i, hPointer++, picture.get(i, j));
            hPointer = 0;
        }

        picture = aPicture;
        height = height - 1;
        reEnergy();
    }

    public void removeVerticalSeam(int[] seam)
    {
        if (seam == null || width() <= 1 || seam.length != height())
            throw new IllegalArgumentException();
        for (int i = 0; i < seam.length; i++)
        {
            if (i > 0 && Math.abs(seam[i - 1] - seam[i]) > 1)
                throw new IllegalArgumentException();
            if (seam[i] < 0 || seam[i] > width() - 1)
                throw new IllegalArgumentException();
        }

        Picture aPicture = new Picture(width() - 1, height());
        int wPointer = 0;
        for (int i = 0; i < height(); i++)
        {
            for (int j = 0; j < width(); j++)
                if (seam[i] != j)
                    aPicture.set(wPointer++, i, picture.get(j, i));
            wPointer = 0;
        }

        picture = aPicture;
        width = width - 1;
        reEnergy();
    }
}