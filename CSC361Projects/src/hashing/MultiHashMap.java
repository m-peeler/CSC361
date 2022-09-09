/******************************************************************************
 *
 *  A multi-map symbol table implemented with a separate-chaining hash table.
 * 
 ******************************************************************************/

/**
 *  The {@code LinkedSymbolTable} class represents a symbol table of generic
 *  key-value pairs.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
 *  It also provides a <em>keys</em> method for iterating over all of the keys.
 *  A symbol table implements the <em>associative array</em> abstraction:
 *  when associating a value with a key that is already in the symbol table,
 *  the convention is to replace the old value with the new value.
 *  Unlike {@link java.util.Map}, this class uses the convention that
 *  values cannot be {@code null}—setting the
 *  value associated with a key to {@code null} is equivalent to deleting the key
 *  from the symbol table.
 *  <p>
 *  This implementation uses a separate chaining hash table. It requires that
 *  the key type overrides the {@code equals()} and {@code hashCode()} methods.
 *  The expected time per <em>put</em>, <em>contains</em>, or <em>remove</em>
 *  operation is constant, subject to the uniform hashing assumption.
 *  The <em>size</em>, and <em>is-empty</em> operations take constant time.
 *  Construction takes constant time.
 *  <p>
 *  For additional documentation, see <a href="https://algs4.cs.princeton.edu/34hash">Section 3.4</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *  For other implementations, see {@link ST}, {@link BinarySearchST},
 *  {@link SequentialSearchST}, {@link BST}, {@link RedBlackBST}, and
 *  {@link LinearProbingHashST},
 *
 *	Timings:
 *	<t> <t>   <t> Put <t> Del
 *  <t> 500   <t> 10  <t> 11
 *  <t> 1000  <t> 4   <t> 5
 *  <t> 2000  <t> 5   <t> 7
 *  <t> 5000  <t> 5   <t> 6
 *  <t> 7500  <t> 6   <t> 10
 *  <t> 10000 <t> 6   <t> 8
 *  <t> 12500 <t> 5   <t> 7
 *  <t> 15000 <t> 4   <t> 5
 *  <t> <t>   <t> O(1)<t> O(1)
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  @modified Michael Peeler
 *  @date April 24, 2022
 */

package hashing;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import hash_util.Constants;

public class MultiHashMap<Key, Value> implements MultiMap<Key, Value>
{
	private static final int INIT_CAPACITY = 4;

	private int _size;                        // number of key-value pairs
	private int _tableLength;                 // hash table size
	private LinkedMultiMap<Key, Value>[] st;  // array of linked-list symbol tables

	private static int INCREASE_DECREASE_FACTOR = 2;

	/**
	 * Initializes an empty symbol table.
	 */
	public MultiHashMap() { this(INIT_CAPACITY); } 

	/**
	 * Initializes an empty symbol table with {@code m} chains.
	 * @param _tableLength the initial number of chains
	 */
	@SuppressWarnings("unchecked")
	public MultiHashMap(int m)
	{
		_tableLength = m;

		st = (LinkedMultiMap<Key, Value>[]) new LinkedMultiMap[_tableLength];

		for (int i = 0; i < _tableLength; i++)
		{
			st[i] = new LinkedMultiMap<Key, Value>();
		}
	}

	// resize the hash table to have the given number of chains,
	// rehashing all of the keys
	private void resize(int chains)
	{
		if (Constants.DEBUG)
           System.err.println("Resizing from " + _tableLength + "(" + _size + ")" + " to " + chains);
		
		MultiHashMap<Key, Value> temp = new MultiHashMap<Key, Value>(chains);

		for (int i = 0; i < _tableLength; i++)
		{
			//
			// Get all the values for each key and add them back to the temp
			for (Key key : st[i].keySet())
			{
				for (Value value : st[i].getAll(key))
				{
					temp.put(key, value);
				}								
			}
		}

		this._tableLength  = temp._tableLength;
		this._size  = temp._size;
		this.st = temp.st;
	}

	// hash function for keys - returns value between 0 and m-1 (assumes m is a power of 2)
	// (from Java 7 implementation, protects against poor quality hashCode() implementations)
	private int hash(Key key)
	{
		int h = key.hashCode();
		h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
		return h & (_tableLength-1);
	}

	/**
	 * Returns the number of key-value pairs in this symbol table.
	 *
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size() { return _size; }

	/**
	 * Returns *true* if this symbol table is empty.
	 *
	 * @return {@code true} if this symbol table is empty;
	 *         {@code false} otherwise
	 */
	public boolean isEmpty() { return size() == 0; }

	/**
	 * Returns *true* if this symbol table contains the specified key.
	 *
	 * @param  key the key
	 * @return {@code true} if this symbol table contains {@code key};
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public boolean contains(Key key)
	{
		if (key == null) throw new IllegalArgumentException("argument to contains() is null");

		// Do we have at least one <key,value> pair?
		return st[hash(key)].getAll(key).iterator().hasNext();
	} 
	
	/**
	 * Returns true if this symbol table contains the specified key.
	 *
	 * @param  key the key
	 * @return {@code true} if this symbol table contains {@code key};
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public boolean containsPair(Key key, Value value)
	{
		if (key == null) throw new IllegalArgumentException("argument to contains() is null");

		// Do we have at least one <key,value> pair?
		return st[hash(key)].containsPair(key, value);
	} 

	/**
	 * Inserts the specified key-value pair into the symbol table.
	 *
	 * @param  key the key
	 * @param  value the value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void put(Key key, Value value)
	{
		if (key == null) throw new IllegalArgumentException("first argument to put() is null");
		if (value == null) throw new IllegalArgumentException("second argument to put() is null");

        // double table size if average length of list >= 10
        if (_size >= 10 * _tableLength) resize(INCREASE_DECREASE_FACTOR * _tableLength);
        
        int origSize = st[hash(key)].size();
        st[hash(key)].put(key, value);
        _size += st[hash(key)].size() - origSize;
	}

	/**
	 * Removes the specified key and its associated value from this symbol table     
	 * (if the key is in this symbol table).    
	 *
	 * @param  key the key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void delete(Key key, Value value)
	{
		if (key == null) throw new IllegalArgumentException("argument to delete() is null");
		
		int origSize = st[hash(key)].size();
        st[hash(key)].delete(key,  value);
        _size -= origSize - st[hash(key)].size();

		// halve table size if average length of list <= 2
		if (_tableLength > INIT_CAPACITY && _size <= 2 * _tableLength)
			resize(_tableLength / INCREASE_DECREASE_FACTOR);
	}

	@Override
	public Iterable<Value> getAll(Key key)
	{
		if (key == null) throw new IllegalArgumentException("argument to get() is null");

        return st[hash(key)].getAll(key);
	}

	@Override
	public void deleteAll(Key key)
	{
		if (key == null) throw new IllegalArgumentException("argument to get() is null");

		int origSize = st[hash(key)].size();
        st[hash(key)].deleteAll(key);
        _size -= origSize - st[hash(key)].size();
        
		// halve table size if average length of list <= 2
		if (_tableLength > INIT_CAPACITY && _size <= 2 * _tableLength)
			resize(_tableLength / INCREASE_DECREASE_FACTOR);
	} 

	// return all unique keys in symbol table as an Iterable
	public Iterable<Key> keySet()
	{
		Queue<Key> queue = new LinkedList<Key>();

		for (LinkedMultiMap<Key,Value> chains : st) {
			if (chains != null && chains.firstKey() != null) {
				queue.add(chains.firstKey());
			}
		}
        
		return queue;
	}
}