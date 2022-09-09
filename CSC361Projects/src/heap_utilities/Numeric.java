package heap_utilities;

public class Numeric
{
    private static final double EPSILON = 0.000001;
	
	public static boolean neighborhoodEquals(double a, double b)
	{
		return Math.abs(a - b) < EPSILON;
	}
}
