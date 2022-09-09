/**
 * An implementation of the MinHeap interface, stored as an array of key-value pairs which
 * abides by the shape property (all levels except the lowest are completely filled, and the lowest
 * level is filled from left to right) and the min-heap property (all parent nodes are smaller than
 * all nodes in the subheap based at that node). 
 * Extends ExpandableHeapBase, adding the unimplemented methods of extractMin, peekMin, and insert,
 * and overriding the build method for a more efficient build.
 * 
 * <p>Bugs: None noted
 * 
 * <p>        Build   ExtractMin
 * <p>5000    0        0
 * <p>10000   0        0
 * <p>50000   3        0
 * <p>100000  5        0
 * <p>200000  25       0
 * <p>      O(n lg(n))* O(n)**
 * 
 * * Since the function is initially sorted by inserting all elements and then sinking the
 * upper half, this will take up to (n lg(n)) operations. 
 * ** Though this appears to be an O(1) operation, in actuality the worst case should be an O(lg(n)) 
 * operation, as the sink function could require up to that many exchanges.
 *
 * @author Michael Peeler
 * @date   3/30/2022
 */

package heap;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClassicMinHeap<T> extends ExpandableHeapBase<T>
{
	public ClassicMinHeap()
	{
		super();
	}
	
	/**
	 * Removes the minimum node from the heap and returns the minimum node to the caller.
	 * Does so after switching the first node with the last node. The node that was formerly
	 * last is then sunk to its new position, allowing the new minimum node to float to the top.
	 * 
	 * @return - Returns node containing minimum key and value associated with it.
	 */
	public HeapNode<T> extractMin() {
		
		exchange(_heap[0], _heap[_size - 1]);
		HeapNode<T> node = _heap[_size - 1];
		_heap[_size - 1] = null;
		
		_size--;
		
		sink(_heap[0]);
		
		return node;
	}
	
	/**
	 * Returns the minimum node from the heap, which will always be the first node
	 * in the array.
	 * 
	 * @return - Returns node containing minimum key and value associated with it.
	 */
	public HeapNode<T> peekMin(){
		return _heap[0];
	}
	
	/**
	 * Switches the positions in the heap of node1 and node2
	 * @param node1 First node being swapped
	 * @param node2 Second node being swapped
	 */
	private void exchange(HeapNode<T> node1, HeapNode<T> node2) {
		int n1 = node1._index;
		int n2 = node2._index;
		
		_heap[n1] = node2;
		_heap[n2] = node1;
		
		_heap[n1]._index = n1;
		_heap[n2]._index = n2;
	}
	
	
	/**
	 * Checks whether the specified child index can be sunk with; that is to say, whether
	 * there is a value there and whether that value is less than the parent node.
	 * @param node Node being compared to children
	 * @param childIndex Index of child node.
	 * @return Boolean indicating sinkability.
	 */
	private boolean canChildBeSwitchedWith(HeapNode<T> node, int childIndex) {
		return childIndex < _size && node.compareTo(_heap[childIndex]) > 0;
	}
	
	/**
	 * Node sinks downwards until the nodes below it are greater than it.
	 * @param node Node that is sinking.
	 */
	private void sink(HeapNode<T> node) {
		
		if (node == null) return;
				
		int childL = node._index * 2 + 1;
		int childR = node._index * 2 + 2;
				
		
		// Makes certain that one of the child nodes can be switched with
		if (canChildBeSwitchedWith(node, childL) || canChildBeSwitchedWith(node, childR)) {
			
			// Switches with child that is smaller, then sinks the new child node.
			if (childR >= _size || _heap[childL].compareTo(_heap[childR]) < 0) {
				
				exchange(node, _heap[childL]);
				sink(_heap[childL]);
				
			} else {

				exchange(node, _heap[childR]);
				sink(_heap[childR]);
				
			}
			
		}
		
	}
	
	/**
	 * Node moves upward until its parents are less than it and its children
	 * are greater than it.
	 * @param node Node that is swimming upwards.
	 */
	private void swim(HeapNode<T> node) {
		// Stops swimming up if the node is at the root.
		if (node._index != 0) {
			
			int parent = (node._index - 1) / 2;
			
			// Switches a node with its parent if it is less than its parent.
			if (node.compareTo(_heap[parent]) < 0) {
				exchange(node, _heap[parent]);
				swim(_heap[parent]);
			}
		}
	}
	
	/**
	 * Inserts the input node at the end of the array, then swims it to ensure it abides
	 * by the shape and heap properties.
	 * 
	 * @param node HeapNode that will be inserted into the current heap.
	 */
	public void insert(HeapNode<T> node) {
		
		ensureCapacity(_size + 1);

		// Adds node to end of array
		_heap[_size] = node;
		_heap[_size]._index = _size;
		_size ++;

		// Swims node to correct position.
		swim(node);

	}

	/**
	 * Builds a sorted MinHeap from two lists, a list of values and a 
	 * comparable list of keys. Does so in O(n ln(n)) time, by inserting all values
	 * into the heap and then sinking each internal node.
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
		
		// Inserts each node into the heap in order.
		for (int i = 0; i < sz; i++)
		{
			_heap[i] = new HeapNode<T>(values.get(i), keys.get(i));
			_heap[i]._index = i;
		}
		
		_size = sz;
		
		// For each of the bottom half nodes, sinks them to the correct
		// position.
		for (int i = _size / 2; i >= 0; i--) {
			sink(_heap[i]);
		}
	}
	
	/** 
	 * Converts the tree into a string that contains a list of all nodes and their parents.
	 */
	public String toString() {
		String rtrn = "Tree:\n";
		
		for (int i = 0; i < _size; i++) {
			rtrn += " " + _heap[i]._key + " " + _heap[i]._index;
		}
		for (int i = 0; i < _size; i++) {
			rtrn += "\n";
			int j = i;
			while (j >= 0) {
				rtrn += _heap[j] + " ";
				if (j == 0) j = -1;
				j = (j - 1) / 2;
			}

		}
		return rtrn;
	}
	
}