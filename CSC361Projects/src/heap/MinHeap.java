/**
 * A minimum heap requires some fundamental operations:
 * 
 *  build
 *  insert
 *  extractMin
 *  peekMin
 *  isEmpty
 *  size
 *  clear
 *  
 *  @author C. Alvin
 */
package heap;

import java.util.List;

public interface MinHeap<T>
{
	/*
	 * Construct a heap from a set of values and corresponding set of keys
	 * 
	 * @param values -- a set of values; we will create HeapNode objects for them
	 * @param keys -- a set of keys (as Doubles)
	 * 
	 * We do NOT assume the lengths of the lists are equal; 
	 */
	public void build(List<T> values, List<Double> keys);
	
	/*
	 * Add to the heap
	 * @param node -- adding a node to the heap; requires the HeapNode object
                      be initialized externally. Initialization includes the key in HeapNode._key 
	 */
	public void insert(HeapNode<T> node);

	/*
	 * Remove and return the node corresponding to the minimum key
	 * 
	 * @return the HeapNode corresponding to the minimum key
	 */
	public HeapNode<T> extractMin();

	/*
	 * Return the node corresponding to the minimum key
     *
	 * @return the HeapNode corresponding to the minimum key
	 */
    public HeapNode<T> peekMin();
	
	public boolean isEmpty();
	public int size();
	public void clear();
}

//public void decreaseKey(HeapNode<T> node, double newKey);