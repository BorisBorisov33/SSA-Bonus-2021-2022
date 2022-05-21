


import java.util.Random;

public class NormalServiceDist {
    private final double mean;
    private final double standardDeviation;

    private final boolean isTruncated;
    private final double truncatedPoint;

    public NormalServiceDist(double mean, double standardDeviation, double truncatedPoint) {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
        this.isTruncated = true;
        this.truncatedPoint = truncatedPoint;
    }

    public NormalServiceDist(double mean, double standardDeviation) {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
        this.isTruncated = false;
        this.truncatedPoint = Double.NEGATIVE_INFINITY;
    }

    public double getMean() {
        return mean;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

    public boolean isTruncated() {
        return isTruncated;
    }

    public double getTruncatedPoint() {
        return truncatedPoint;
    }

    public double drawServiceTime()
    {
        // draw a [0,1] uniform distributed number
        Random random = new Random();
        // Convert it into a exponentially distributed random variate with mean 33
        double res = random.nextGaussian() * standardDeviation + mean;
        return res;
    }
}
