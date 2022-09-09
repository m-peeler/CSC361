/**
 * A multimap interface.
 * 
 * The implementing class will store <key, value> pairings. Multiple pairs may
 * with each key. However, the same <key, value> can occur only once.
 * 
 * @author C. Alvin
 * @date 3/5/21
 */
package hashing;

public interface MultiMap<Key, Value>
{
	/**
	 * Returns the number of key-value pairs in this symbol table.
	 *
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size();

	/**
	 * Returns true if this symbol table is empty.
	 *
	 * @return {@code true} if this symbol table is empty;
	 *         {@code false} otherwise
	 */
	public boolean isEmpty();

	/**
	 * Returns true if this symbol table contains the specified key.
	 *
	 * @param  key the key
	 * @return {@code true} if this symbol table contains {@code key};
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public boolean contains(Key key);
	
	/**
	 * Returns true if this symbol table contains the specified
	 * <key, value> pair.
	 *
	 * @param  key  -- the key
	 * @param  value -- the value
	 * @return {@code true} if this symbol table contains {@code key};
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public boolean containsPair(Key key, Value value);
	
	/**
	 * Returns all values associated with the specified key in this symbol table.
	 *
	 * @param  key the key
	 * @return the value associated with {@code key} in the symbol table;
	 *         {@code null} if no such value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public Iterable<Value> getAll(Key key);

	/**
	 * Inserts the key-value pair into the symbol table (if not already contained).
	 *
	 * @param  key the key
	 * @param  val the value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void put(Key key, Value val);

	/**
	 * Removes the specified <key, value> pair value from this symbol table     
	 * (if present).
	 *
	 * @param  key -- the key
	 * @param  value -- the value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void delete(Key key, Value value);

	/**
	 * Removes all pairs with the given key.    
	 *
	 * @param  key the key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void deleteAll(Key key);

	/**
	 * Returns the set of unique keys in the ST as an iterable set
	 */
	public Iterable<Key> keySet();
}
