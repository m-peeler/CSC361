/**
 * Performs timings on two different types of MinHeap implementations, 
 * the SortedListMinHeap and the UnsortedListMinHeap, using randomized Double
 * values of keys.
 *
 * <p>Bugs: None noted
 *
 * @author Michael Peeler
 * @date   3/30/2022
 */
 
package heap_main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import heap.MinHeap;
import heap.SortedListMinHeap;
import heap.UnsortedListMinHeap;
import heap.ClassicMinHeap;
import heap_utilities.Timer;
import java.util.Random;

public class Main
{
	/**
	 * Generates an ArrayList of length len containing random Double values.
	 * @param len Number of values in the ArrayList that will be returned.
	 * @return ArrayList with len random elements.
	 */
	public static ArrayList<Double> genRandList(int len) {
		ArrayList<Double> rtrn = new ArrayList<Double>();
		java.util.Random rand = new java.util.Random();
		for (int i = 0; i < len; i++) {
			rtrn.add(rand.nextDouble());
		}
		return rtrn;
	}
	
	/**
	 * Generates an ArrayList of length len containing sequential int values.
	 * @param len Number of values in the ArrayList that will be returned.
	 * @return ArrayList with len sequential integers, beginning at 0.
	 */
	public static ArrayList<Integer> genIntList(int len) {
		ArrayList<Integer> rtrn = new ArrayList<Integer>();
		for (int i = 0; i < len; i++) {
			rtrn.add(i);
		}
		return rtrn;
	}
	
	/**
	 * Deals with each individual heap that will be tested in the timing. 
	 * @param keys A list of random Doubles that will be used as keys in 
	 * the MinHeaps.
	 * @param vals The values that will be stored inside the MinHeaps.
	 * @param time Timer used to measure time that each test takes.
	 * @param heaps Instance of specific MinHeap implementation being tested.
	 */	
	private static long[] runTimedTests(ArrayList<Double> keys, ArrayList<Integer> vals, MinHeap<Integer> heap, Timer time) {
		long[] rtrn = new long[2];
		Collections.shuffle(keys);
		
		// Adds the time it takes to build the heap to the running total.
		time.start();
		heap.build(vals, keys);
		rtrn[0] = time.stop();
	
		// Adds the time it takes to extract minimum to the running total.
		time.start();
		heap.extractMin();
		rtrn[1] = time.stop();
		return rtrn;
	}
	
	/**
	 * Deals with each individual heap that will be tested in the timing. 
	 * @param keys A list of random Doubles that will be used as keys in 
	 * the MinHeaps.
	 * @param vals The values that will be stored inside the MinHeaps.
	 * @param time Timer used to measure time that each test takes.
	 * @param heaps Instance of specific MinHeap implementation being tested.
	 */	
	private static void processHeap(ArrayList<Double> keys, ArrayList<Integer> vals, MinHeap<Integer> heap, Timer time) {
		System.out.println(heap.getClass() + " Build Heap");
		
		int timesRun = 100;
		int[] timings = new int[2];
		long[] res = new long[2];
		
		for (int i = 0; i < timesRun; i++) {
			res = runTimedTests(keys, vals, heap, time);
			timings[0] += res[0];
			timings[1] += res[1];
		}
		
		System.out.println("Build: " + timings[0]/timesRun + "  ExtractMin: " + timings[1] + " " + timings[1]/timesRun);

		System.out.println();

		heap.clear();
	}
	
	/**
	 * Runs a timing test on each the heaps provided in heaps with a number of nodes equal to
	 * elements. 
	 * @param elements How many nodes will be in each MinHeap for the tests
	 * @param time Timer used to measure time that each test takes.
	 * @param heaps List of MinHeaps that will be timed.
	 */
	private static void testWithNElements(int elements, Timer time, List<MinHeap<Integer>> heaps) {
		ArrayList<Double> keys = genRandList(elements);
		ArrayList<Integer> vals = genIntList(elements);
		
		System.out.println(elements + " Elements");
		
		// Execute the test process over all the heaps
		for (MinHeap<Integer> heap : heaps)
		{
			processHeap(keys, vals, heap, time);
		}
	}

	
	public static void main(String[] args)
	{
		final int[] ELEMENT_COUNT = new int[] {5000, 10000, 50000, 100000, 200000 }; //, 500000};

		//
		// All heaps we are testing
		List<MinHeap<Integer>> heaps = new ArrayList<MinHeap<Integer>>();
		heaps.add(new UnsortedListMinHeap<Integer>());
		heaps.add(new SortedListMinHeap<Integer>());
		heaps.add(new ClassicMinHeap<Integer>());

		Timer time = new Timer();
		
		// Executes the test process on each type of heap for every number of elements.
		for (int elements : ELEMENT_COUNT ) {		
			testWithNElements(elements, time, heaps);
		}
	}
}