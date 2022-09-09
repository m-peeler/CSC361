package heap;

import java.util.List;

/*
 * A base class for an array-based implementation of a heap.
 * All implementations are naive: no special ordering of nodes based on keys.
 */
public abstract class ExpandableHeapBase<T> implements MinHeap<T>
{
	protected HeapNode<T>[] _heap;
	protected int           _size;
	protected final int     _MIN_CAPACITY = 10;

	public ExpandableHeapBase() { init(); }

	@SuppressWarnings("unchecked")
	protected void init()
	{
		_heap = (HeapNode<T>[]) new HeapNode[_MIN_CAPACITY];
		_size = 0;
	}

	public void clear() { init(); }
	public boolean isEmpty() { return _size == 0; }
	public int size() { return _size; }
	
	/*
	 * By default, the build operation constructs a heap using the
	 * particular insert operation
     *
	 * @param values -- a set of values; we will create HeapNode objects for them
	 * @param keys -- a set of keys (as Doubles)
	 * 
	 * We do NOT assume the lengths of the lists are equal; 
	 */
	public void build(List<T> values, List<Double> keys)
	{
		int sz = Math.min(values.size(), keys.size());
		for (int i = 0; i < sz; i++)
		{
			this.insert(new HeapNode<T>(values.get(i), keys.get(i)));
		}
	}
	
	/** 
	 * Increases the capacity of this expandable array-based data structure, if 
	 * necessary, to ensure  that it can hold at least the number of elements 
	 * specified by the minimum capacity argument.  
	 * 
	 * @param   minCapacity   the desired minimum capacity. 
	 */
	@SuppressWarnings("unchecked")
	protected void ensureCapacity(int minCapacity)  
	{  
		int oldCapacity = _heap.length;  

		// If the user is checking that the container is large enough already
		if (minCapacity <= oldCapacity) return;

		//
		// enlarge and copy
		//
		HeapNode<T> oldData[] = _heap;  

		int newCapacity = (oldCapacity * 3)/2 + 1;  

		if (newCapacity < minCapacity) newCapacity = minCapacity;  

		_heap = (HeapNode<T>[])new HeapNode[newCapacity];  

		System.arraycopy(oldData, 0, _heap, 0, _size);
	}
	
	/**
	 * Remove the element at the particular index by shifting elements down the array
	 * @param index - a valid index: 0 <= index < _size 
	 * @return the node at the index
	 */
	protected HeapNode<T> remove(int index)
	{
		if (index < 0 || index >= _size) return null;

		// Grab the object
		HeapNode<T> node = _heap[index];
		
		// Shifts elements down to overwrite the one element we are removing
		System.arraycopy(_heap, index + 1, _heap, index, _size - index - 1);

		_size--;
		
		// Overwrite the last element (to avoid copies in the last position)
		_heap[_size] = null;

		return node;
	}

	/*
	 * @return (For debugging purposes) dump (key, data) pairs
	 */
	public String toString()
	{
		String retS = "";

		// Traverse the array and dump the (key, data) pairs
		for (int i = 0; i < _size; i++)
		{
			retS += "(" + _heap[i]._key + ", " + _heap[i]._data + ") ";
		}

		return retS + "\n";
	}
}