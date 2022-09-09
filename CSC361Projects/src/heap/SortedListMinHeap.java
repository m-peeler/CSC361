/**
 * An implementation of the MinHeap interface, stored as a sorted array of key-value pairs. 
 * Extends ExpandableHeapBase, adding the unimplemented methods of extractMin, peekMin, and insert,
 * and overriding the build method for a more efficient build.
 * 
 * <p>Bugs: None noted
 * 
 * <p>        Build   ExtractMin
 * <p>5000    4        0
 * <p>10000   3        0
 * <p>50000   19       0
 * <p>100000  38       0
 * <p>200000  169      0
 * <p>      O(n lg(n))* O(1)**
 * 
 * * According to the Java API, Arrays.sort has an O(n lg(n)); however, this seems to
 * be displaying roughly O(n) behavior.
 * ** I refactored the code to have the smallest value at the highest index, allowing 
 * extraction in constant time.
 *
 * @author Michael Peeler
 * @date   3/30/2022
 */

package heap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortedListMinHeap<T> extends ExpandableHeapBase<T>
{
	public SortedListMinHeap()
	{
		super();
	}
	
	/**
	 * Removes the minimum node from the heap and returns the minimum node to the caller.
	 * No updates to index values, or shifts, are needed because values are kept in
	 * decreasing key order in _heap.
	 * 
	 * @return - Returns node containing minimum key and value associated with it.
	 */
	public HeapNode<T> extractMin(){
		HeapNode<T> least = remove(_size - 1);
		return least;
	}
	
	/**
	 * Returns the minimum node from the heap, which will always be the last node
	 * in the array.
	 * 
	 * @return - Returns node containing minimum key and value associated with it.
	 */
	public HeapNode<T> peekMin(){
		return _heap[_size - 1];
	}
	
	/**
	 * Inserts the input node at its correct sorted position in the array.
	 * 
	 * @param node HeapNode that will be inserted into the current heap.
	 */
	public void insert(HeapNode<T> node) {
		
		ensureCapacity(_size + 1);
		int i = _size;

		// Empty space starts at the end, and continues moving right until it where 
	    // node should be; this moves other nodes into the correct place without
		// a temporary holding variable. Node is added afterwards in correct place.
		while (i > 0 && _heap[i - 1].compareTo(node) < 0) {
			
			_heap[i] = _heap[i - 1];
			_heap[i]._index = i;
			
			i--;
		}

		
		// Adds node to the list
		_heap[i] = node;
		_heap[i]._index = i;
		
		_size ++;

	}

	/**
	 * Builds a sorted MinHeap from two lists, a list of values and a 
	 * comparable list of keys. Does so in O(n ln(n)) time, by inserting all values
	 * into the heap and then using the inbuilt Arrays.sort method to perform TimSort
	 * on the _heap array.
	 * 
	 * @param values -- a set of values; we will create HeapNode objects for them
	 * @param keys -- a set of keys (as Doubles)
	 * 
	 * We do NOT assume the lengths of the lists are equal; we also clear the _heap
	 * list before running to ensure that _heap is empty.
	 */
	@Override
	public void build(List<T> values, List<Double> keys)
	{
		this.clear();
		int sz = Math.min(values.size(), keys.size());
		ensureCapacity(sz);
		
		for (int i = 0; i < sz; i++)
		{
			_heap[i] = new HeapNode<T>(values.get(i), keys.get(i));
		}
		
		_size = sz;
	    Arrays.sort(_heap, Collections.reverseOrder());
	    
	    // Sets index values
		for (int j = 0; j < _size; j++) _heap[j]._index = j;
	}
	
}