/**
 * An implementation of the MinHeap interface, stored as an unsorted array of key-value pairs. 
 * Extends ExpandableHeapBase, adding the unimplemented methods of extractMin, peekMin, and insert.
 *
 * <p>Bugs: None noted
 * 
 * <p>        Build  ExtractMin
 * <p>5000    0      2
 * <p>10000   0      3
 * <p>50000   37     20
 * <p>100000  26     42
 * <p>200000  83     82
 * <p>        O(n)   O(n)
 *
 * @author Michael Peeler
 * @date   3/30/2022
 */
package heap;

import java.util.List;

//
// Simple data structure that implements the required methods: decreaseKey O(1) and extractMin O(n)
//
public class UnsortedListMinHeap<T> extends ExpandableHeapBase<T>
{
	public UnsortedListMinHeap()
	{
		super();
	}
	
	/**
	 * Removes the smallest node from the heap, updates internal 
	 * index values for HeapNodes, and returns this node to the caller.
	 * 
	 * @return - Returns node containing minimum key and value associated with it.
	 */
	public HeapNode<T> extractMin() {
		
		HeapNode<T> least = peekMin();
		remove(least._index);
		
		// Updates internal index values.
		for (int i = 0; i < _size; i ++) _heap[i]._index = i;
		return least;
	}
	
	/**
	 * Returns the smallest node from the heap without removal.
	 * Finds smallest node by searching through all nodes and returning
	 * the smallest found.
	 * 
	 * @return - Returns node containing minimum key and value associated with it.
	 */
	public HeapNode<T> peekMin() {
		HeapNode<T> least = _heap[0];
		
		for (int i = least._index; i < _size; i++) {
			
			// Replaces least with node smaller than its old value, if found.
			if (_heap[i].compareTo(least) < 0) least = _heap[i];
			
		}
		
		return least;
	}
	
	/**
	 * Inserts the node supplied at the end of the array.
	 * 
	 * @param node HeapNode that will be inserted into the current heap.
	 */
	public void insert(HeapNode<T> node) {
		ensureCapacity(_size + 1);
		node._index = _size;
		_heap[_size] = node;
		_size++;
	}
}