/******************************************************************************
 *  
 *  Symbol table implementation with sequential search in an
 *  unordered linked list of key-value pairs.
 *  
 *  As a multimap, multiple pairs with the same key are possible.
 *  Although a <key, value> pair cannot be repeated. 
 *
 ******************************************************************************/

/**
 *  The {@code LinkedSymbolTable} class represents an (unordered)
 *  symbol table of generic key-value pairs.
 *  It supports the usual <em>put</em>, <em>get</em>, <em>contains</em>,
 *  <em>delete</em>, <em>size</em>, and <em>is-empty</em> methods.
 *  It also provides a <em>keys</em> method for iterating over all of the keys.
 *  <p>
 *  It relies on the {@code equals()} method to test whether two keys
 *  are equal. It does not call either the {@code compareTo()} or
 *  {@code hashCode()} method. 
 *  <p>
 *  This implementation uses a <em>singly linked list</em> and
 *  <em>sequential search</em>.
 *  The <em>put</em> and <em>delete</em> operations take &Theta;(<em>n</em>).
 *  The <em>get</em> and <em>contains</em> operations takes &Theta;(<em>n</em>)
 *  time in the worst case.
 *  The <em>size</em>, and <em>is-empty</em> operations take &Theta;(1) time.
 *  Construction takes &Theta;(1) time.
 *  <p>
 *  For additional documentation, see
 *  <a href="https://algs4.cs.princeton.edu/31elementary">Section 3.1</a> of
 *  <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 *	Timings:
 *	<t> <t>   <t> Put <t> Del
 *  <t> 500   <t> 4   <t> 5
 *  <t> 1000  <t> 3   <t> 4
 *  <t> 2000  <t> 13  <t> 15
 *  <t> 5000  <t> 76  <t> 91
 *  <t> 7500  <t> 146 <t> 186
 *  <t> 10000 <t> 284 <t> 328
 *  <t> 12500 <t> 507 <t> 572
 *  <t> 15000 <t> 657 <t> 765
 *  <t> <t>   <t> O(n)<t> O(n)
 *  
 *
 *  @author Robert Sedgewick
 *  @author Kevin Wayne
 *  
 *  Modified:
 *  @author C. Alvin
 *  3/5/21
 */

package hashing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class LinkedMultiMap<Key, Value> implements MultiMap<Key, Value>
{
    private int  _size;    // number of key-value pairs
    private Node _first;   // the linked list of key-value pairs

    // a helper linked list data type
    private class Node
    {
        private Key _key;
        private Value _value;
        private Node _next;

        public Node(Key key, Value val, Node next)
        {
            this._key  = key;
            this._value  = val;
            this._next = next;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public LinkedMultiMap() { }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return the number of key-value pairs in this symbol table
     */
    public int size() { return _size; }

    /**
     * Returns true if this symbol table is empty.
     *
     * @return {@code true} if this symbol table is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() { return size() == 0; }

    /**
     * Returns true if this symbol table contains the specified key.
     *
     * @param  key the key
     * @return {@code true} if this symbol table contains {@code key};
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(Key key)
    {
        if (key == null) throw new IllegalArgumentException("argument to containsKey() is null"); 

        return contains(key, _first);
     }
    
    /**
     * Beginning at "node", checks for any Key of "key". Returns true if found,
     * or false when the end of the map is reached.
     * @param key Key which is being searched for
     * @param node Current node being searched
     * @return Boolean indicating if key is in the map.
     */
    private boolean contains(Key key, Node node) {
    	while (node != null) {
    	
    		if (node._key.equals(key)) return true;
    		node = node._next;
    	
    	}
    	return false;
    	
    }

    /**
     * Returns the value associated with the given key in this symbol table.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *     and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null} or
     *                                  {@code value} is {@code null}
     */
    public boolean containsPair(Key key, Value value)
    {
        if (key == null) throw new IllegalArgumentException("argument to get() is null"); 
        if (value == null) throw new IllegalArgumentException("argument to get() is null"); 
        
        return containsPair(key, value, _first);
    }

    /**
     * Returns a boolean indicating whether or not the map contains a specific
     * Key-Value pair, given by "key" and "value".
     * @param key Key being searched for
     * @param value Associated Value being searched for
     * @param node Node currently being searched
     * @return Boolean indicating whether specific key was found.
     */
    private boolean containsPair(Key key, Value value, Node node) {
    	
    	while (node != null) {    	
    		
    		if (node._key.equals(key) && node._value.equals(value))
    			return true;
    		node = node._next;
    		
    	}
    	
    	return false;
    }
    
    /**
     * Inserts the specified key-value pair into the symbol table if is it not
     * currently in the list
     *
     * @param  key the key
     * @param  val the value
     * @throws IllegalArgumentException if {@code key} is {@code null} or
                                        {@code value} is {@code null}
     */
    public void put(Key key, Value val)
    {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null"); 
        if (val == null) throw new IllegalArgumentException("second argument to put() is null"); 

        if (!containsPair(key, val)) {
		
        	_first = new Node(key, val, _first);
        	_size++;
        	
        }
    }

    /**
     * Removes the specified key and its associated value from this symbol table     
     * (if the key is in this symbol table).    
     *
     * @param key -- the key
     * @param val -- the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void delete(Key key, Value val)
    {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null"); 
        if (val == null) throw new IllegalArgumentException("second argument to put() is null"); 

        _first = delete(_first, key, val);
    }

    // delete <key, value> in linked list beginning at Node x
    // warning: function call stack too large if table is large
    private Node delete(Node x, Key key, Value val)
    {
        if (x == null) return null;

        if (key.equals(x._key) && val.equals(x._value))
        {
            _size--;
            return x._next;
        }

        x._next = delete(x._next, key, val);
        return x;
    }
    
    /**
     * Removes all pairs containing the specified key from this symbol table     
     * (if the key is in this symbol table).
     *
     * @param  key the key
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void deleteAll(Key key)
    {
        if (key == null) throw new IllegalArgumentException("argument to deleteAll() is null"); 

        _first = deleteAll(_first, key);
    }
    
    // delete <key, value> in linked list beginning at Node x
    // warning: function call stack too large if table is large
    private Node deleteAll(Node x, Key key)
    {
        if (x == null) return null;
        if (key.equals(x._key)) {
        	
        	_size--;
        	return deleteAll(x._next, key);
        }
        
        x._next = deleteAll(x._next, key);
        return x;
    	
    }
   
    /**
     * Returns all unique keys in the symbol table as an {@code Iterable}.
     * To iterate over all of the keys in the symbol table named {@code st},
     * use the foreach notation: {@code for (Key key : st.keys())}.
     *
     * @return all keys in the symbol table
     */
    public Iterable<Key> keySet()
    {
        ArrayList<Key> result = new ArrayList<Key>();
        
        Node node = _first;
        
        while (node != null) {
        	
        	if (!result.contains(node._key)) {
        		result.add(node._key);
        	}
        	node = node._next;
        }
        
        return result;
    }
    
    /**
     * Returns all Values in the symbol table as an {@code Iterable} given a
     * {@code key}. To iterate over all of the keys in the symbol table named
     * {@code st}, use the foreach notation:
     *             {@code for (Value value : st.getAllValues())}.
     *
     * @return all values corresponding to a key in the symbol table
     */
    public Iterable<Value> getAll(Key key)
    {
        Queue<Value> queue = new LinkedList<Value>();

        Node node = _first;
        
        while (node != null) {
        	
        	if (node._key.equals(key))
        		queue.add(node._value);
        	node = node._next;
        	
        }

        return queue;
    }
    
    public Key firstKey() {
    	if (_first == null) return null;
    	return _first._key;
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (Node x = _first; x != null; x = x._next)
        {
        	sb.append("(" + x._key.toString() + ", " + x._value.toString() + ") ");
        }

        return sb.toString();
	}
}