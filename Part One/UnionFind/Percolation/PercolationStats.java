import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats 
{
    private double[] data;
    
    public PercolationStats(int n, int trials)
    {
        if (n < 1 || trials < 1)
            throw new IllegalArgumentException("invalid input");

        data = new double[trials];
        double size = n * n;

        for (int i = 0; i < trials; i++)
        {
            Percolation perco = new Percolation(n);
            while (!perco.percolates())
                perco.open(StdRandom.uniform(n) + 1, StdRandom.uniform(n) + 1);
            data[i] = perco.numberOfOpenSites() / size;
        }
    }

    public double mean()
    {
        return StdStats.mean(data);
    }                 

    public double stddev()
    {
        return StdStats.stddev(data);
    }       

    public double confidenceLo()
    {
        return mean() - (1.96 * stddev()) / Math.sqrt(data.length);
    }

    public double confidenceHi()
    {
        return mean() + (1.96 * stddev()) / Math.sqrt(data.length);
    }                 

    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats stat = new PercolationStats(n, trials);

        System.out.printf("mean                    = %f\n", stat.mean());
        System.out.printf("stddev                  = %f\n", stat.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]\n",
            stat.confidenceLo(), stat.confidenceHi());
    }
}