/**
 * Implements a RestorableQueue, which acts as an ADT queue with first-in, first-out
 * behavior that can have its state saved, and have previous states restored. 
 * Contains generic objects of type T.
 * @author Michael Peeler
 *
 */

package RestorableQueue;

import java.util.NoSuchElementException;
import java.util.Stack;


public class RestorableQueue<T> implements Restorable {
	
	DoublyLinkedList<T> _queue;
	Stack<Momento<T>> _momentos;
	
	/**
	 * Private momento class for saving the state of the queue.
	 * Each momento saves the state of the queue at one point in its
	 * _arr attribute. This array is used to restore the function.
	 * @author Michael Peeler
	 *
	 * @param <Type> Type that will be contained in the momento.
	 */
	protected class Momento<Type> {
		T[] _arr;
		
		/**
		 * Takes an input of an array which is saved to the _arr 
		 * attribute.
		 * @param arr Array that will be kept in the momento; in order of
		 * oldest first and newest last.
		 */
		protected Momento(T[] arr) {
			_arr = arr;
		}
		
	}
	
	/**
	 * Constructs RestorableQueue by initializing queue.
	 */
	public RestorableQueue() {
		_queue = new DoublyLinkedList<T>();
		_momentos = new Stack<Momento<T>>();
	}
	
	/**
	 * Returns the current size of the queue.
	 * @return Integer size of the queue.
	 */
	public int size() { return _queue.size();}
	
	/**
	 * Returns whether or not the queue is empty.
	 * @return Boolean indicating emptiness.
	 */
	public boolean isEmpty() { return _queue.isEmpty();}
	
	/**
	 * Clears all elements from the queue.
	 */
	public void clear() { _queue.clear();}
	
	/**
	 * Adds an item to the end of the queue.
	 * @param item Item being added.
	 */
	public void enqueue(T item) {_queue.push_back(item);}
	
	/**
	 * Removes the item from the front of the queue
	 * @return Item that was at the front of the queue.
	 * @throws If there is nothing in the queue, throws NoSuchElementException
	 */
	public T dequeue() throws NoSuchElementException {return _queue.pop_front();}
	
	/**
	 * Returns the item at the front of the queue but does not 
	 * remove it.
	 * @return Item that had been at front.
	 */
	public T peek() { return _queue.peek_front();}
	
	/**
	 * Returns whether or not an item is in the queue.
	 * @param o Object being searched for inside the queue.
	 * @return Boolean indicating if o is in queue.
	 */
	public boolean contains(Object o) {	
		
		// Searches every element in the queue for the object.
		for(T listEle : _queue) {
			if (o.equals(listEle)) return true;
		}

		return false;
	}
	
	/**
	 * Turns the queue into an array.
	 * @return Array of the items in the queue
	 */
	public Object[] toArray() {
		Object[] rtrn = new Object[size()];
		
		// Loops through queue and adds to position i in rtrn array
		int i = 0;
		for(T listEle : _queue) { 
			rtrn[i] = listEle;
			i++;
		}
		
		return rtrn;
	}

	/**
	 * Saves the current state of the queue
	 */
	@SuppressWarnings("unchecked")
	public void saveState() { 
		_momentos.push(new Momento<T>((T[]) toArray()));
	}
	
	/**
	 * Restores the last saved version of the queue
	 * @return Boolean indicating whether the restoration 
	 * was successful.
	 */
	public boolean revertState() {
		if (_momentos.isEmpty()) return false;
		
		// Clears the queue and adds the old elements back in.
		clear();
		for (T item : _momentos.pop()._arr) {
			enqueue(item);
		}
		
		return true;
	}
}
