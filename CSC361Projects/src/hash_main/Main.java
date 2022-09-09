package hash_main;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import hashing.LinkedMultiMap;
import hashing.MultiHashMap;
import hashing.MultiMap;
import hash_util.Timer;

public class Main
{
	public static void main(String[] args)
	{
		final int[] SIZES = new int[] {50, 100, 200, 500, 750, 1000, 1250, 1500};
		
		for (int index = 0; index < SIZES.length; index++)
		{
			timeMultiMap(new MultiHashMap<Integer, String>(), SIZES[index]);
			timeMultiMap(new LinkedMultiMap<Integer, String>(), SIZES[index]);
		}
	}

	private static void timeMultiMap(MultiMap<Integer, String> map, int ITERATIONS)
	{
		List<String> values = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");
		ArrayList<Integer>[] a = new ArrayList[10];
		a[0] = new ArrayList<Integer>();
		
		//
		// SIZING UP: Adding 10 pairs for each key
		//
		Timer timer = new Timer();
		timer.start();
		for (int KEY = 1; KEY <= ITERATIONS; KEY++)
		{
			for (String value : values)
			{
				map.put(KEY, value);
			}
		}
		long duration_put = timer.getCurrentInterval();

		//
		// SIZING DOWN
		//
		for (int KEY = 1; KEY <= ITERATIONS; KEY++)
		{
			map.deleteAll(KEY);
		}
		long duration_all = timer.stop();

		System.out.println(map.getClass().getName());
		System.out.println("\tAdding " + ITERATIONS * values.size() + " took " + duration_put);
		System.out.println("\tDeleting " + ITERATIONS * values.size() + " took " + duration_all);
	}
}
