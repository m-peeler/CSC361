package PythonList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.lang.IndexOutOfBoundsException;

/**
* Adaptation of the Array type that allows items to be reached
* using negative indexes as in Python.
*
* <p>Bugs: (a list of bugs and / or other problems)
*
* @author Michael Peeler
* @date January 13, 2022
*/

public class PythonList<E> extends ArrayList<E>
{	
	protected final int 	_INITIAL_CAPACITY = 10;
	
	/**
	 * Constructs an empty PythonList with an 
	 * initial capacity of 10 elements.
	 */
	public PythonList() 
	{
		super();
	}
	
	/**
	 * Constructs a PythonList containing the elements of
	 * the specified collection.
	 * @param c The collection whose elements will be placed in
	 * the PythonList
	 */
	public PythonList(Collection<?extends E> c) 
	{
		super(c);
	}
	
	/**
	 * Checks that a standardized index value is 
	 * within legal index value range.
	 * @param stdIndex Index being checked
	 * @param extd Boolean indicating whether the range should be 
	 * extended by 1, for operations that use index values 
	 * that extend outside the range of the PythonList size.
	 * @throws IndexOutOfBoundsException - If the standardized index
	 * is not in the range (0 <= index && index < size()
	 */
	protected void rangeCheck(int stdIndex, boolean extd) {
		// Ensures standardized index is within correct range
		int extdFctr = 0;
		if (extd) {extdFctr ++;}
		if (!((0 <= stdIndex) && (stdIndex < this.size() + extdFctr))) {
			throw new IndexOutOfBoundsException();
		} 
	}
	
	/**
	 * Checks that a standardized index value is 
	 * less than the PythonList size and >= 0.
	 * @param stdIndex Index being checked
	 * @throws IndexOutOfBoundsException - If the standardized index
	 * is not in the range (0 <= index && index < size()
	 */
	
	protected void rangeCheck(int stdIndex) {
		rangeCheck(stdIndex, false);
	}
	
	/**
	 * Standardizes index values by making negative
	 * values into their positive counterparts.
	 * @param index Index value that is being standardized
	 * @return Standardized index value
	 */
	protected int standardizeIndex(int index)
	{
		int stdIndex = index;
		// Standardizes negative index values by adding to
		// size of PythonList.
		if (stdIndex < 0) 
		{
			stdIndex = this.size() + index;
		}
				
		return stdIndex;
	}

	
	/**
	 * Adds element to PythonList at position "index" 
	 * @param index Index value where element will be placed
	 * @param element Element being added to PythonList
	 * @throws IndexOutOfBoundsException - If the standardized index
	 * is not in the range (0 <= index && index < size()
	 */
	public void add(int index, E element)
	{
		int stdIndex = standardizeIndex(index);
		rangeCheck(stdIndex);
				
		super.add(stdIndex, element);
	}
	
	/**
	 * Returns the element at the specified index.
	 * @param index Index value of item sought from PythonList
	 * @return The element at specified index.
	 * @throws IndexOutOfBoundsException - If the standardized index
	 * is not in the range (0 <= index && index < size()
	 */
	public E get(int index) {
		int stdIndex = 			standardizeIndex(index);
		rangeCheck(stdIndex);
				
		return super.get(stdIndex);
	}
	
	/**
	 * Removes and returns the element from the specified
	 * index.
	 * @param index Index value being deleted
	 * @return Element removed from the list.
	 * @throws IndexOutOfBoundsException - If the standardized index
	 * is not in the range (0 <= index && index < size()
	 */
	public E remove(int index) {
		int stdIndex = 			standardizeIndex(index);
		rangeCheck(stdIndex);
		
		return super.remove(stdIndex);
	}
	
	/**
	 * Replaces and returns element at specified index
	 * with new element.
	 * @param index Index value being replaced
	 * @param element New element replacing old one in PythonList
	 * @return Element previously at index value.
	 * @throws IndexOutOfBoundsException - If the standardized index
	 * is not in the range (0 <= index && index < size()
	 */
	public E set (int index, E element)
	{
		int stdIndex = 			standardizeIndex(index);
		rangeCheck(stdIndex);
				
		return super.set(stdIndex, element);
	}
	
	/**
	 * Creates and returns a portion of the list going from 
	 * fromIndex (Inclusive) to toIndex (exclusive). 
	 * @param fromIndex First index that will be included in the new list
	 * @param toIndex End of sublist section, index of first element since
	 * the fromIndex that will not be included in the sublist
	 * @return A PythonList sublist from the fromIndex (inclusive) to the 
	 * toIndex (exclusive).
	 * @throws IllegalArgumentException
	 */
	public PythonList<E> subList(int fromIndex, int toIndex)	
	{
		// Standardizes and range checks indices
		int stdFromIndex = 		standardizeIndex(fromIndex);
		rangeCheck(stdFromIndex);
		
		int stdToIndex = 		standardizeIndex(toIndex);
		// Needs extended index range so sublist can contain final
		// element of original list.
		rangeCheck(stdToIndex, true);
		return new PythonList(super.subList(stdFromIndex, stdToIndex));
	}
}
