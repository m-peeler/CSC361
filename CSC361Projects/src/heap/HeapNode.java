/**
 * Implements a node for the mininum-heaps.
 */
package heap;

public class HeapNode<T> implements Comparable<T>
{
	public T      _data;
	public double _key;
	public int    _index;   // index in the min-heap

	public HeapNode(T data, double key)
	{
		this._data = data;
		this._key = key;
	}

	public String toString() { return Double.toString(_key); }

	@Override
	public int compareTo(Object o)
	{
		if (!(o instanceof HeapNode)) return -1;
		
		@SuppressWarnings("unchecked")
		HeapNode<T> that = (HeapNode<T>)o;
		
		if (heap_utilities.Numeric.neighborhoodEquals(that._key, this._key)) return 0;
		
		if (this._key < that._key) return -1;
		
		return 1;
	}
}