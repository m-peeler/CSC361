package LinkedList;

/**
* LinkedList is an implementation of the singly linked list data structure
* with head and tail sentinels. Each Node of the singly linked list can 
* contain a single Item, and points to the next Node in the linked list.
* The list can only be traversed moving from head to tail.
* [_h] -> [_1] -> [_2] -> [_3] -> [_t]
*
* <p>Bugs: (a list of bugs and / or other problems)
*
* @author <Michael Peeler>
* @date <Jan 27, 2022>
*/

/***************************************************************
 * A basic singly linked list implementation: LinkedList<Item>
 * 
 * Basic Operations:
 *     LinkedList()
 *     isEmpty()
 *     size()
 *     push_front(Item)
 *     peek_front()
 *     pop_front()
 * 
 ***************************************************************/
import java.util.NoSuchElementException;

public class LinkedList<Item>
{    
    protected int _size;     // number of elements on list
    protected Node _head;    // sentinel before first item
    protected Node _tail;    // sentinel after last item

    //
    // Node helper class facilitating forward-directional pointing
	//
    protected class Node
    {
        protected Item _item;
        protected Node _next;
        
        public Node(Item item, Node next)
        {
            _item = item;
            _next = next;
        }
    }

    public LinkedList()
    {
        _tail = new Node(null, null);
        _head  = new Node(null, _tail);
		_size = 0;
    }

    public boolean isEmpty()    { return _head._next == _tail; }
    public int size()           { return _size;      }
 
    /**
     * Insert item between left and right nodes
     * @param left -- a node
     * @param item -- a user-provided item
     * @param right -- a node
     */
    protected void insert(Node left, Item item, Node right)
    {
        left._next = new Node(item, right);
        _size++;
    }
    
    /**
     * Add an element to the tail of the linked list: O(1) operation
     * @param item
     */
    public void push_front(Item item) 
    {
        insert(_head, item, _head._next);
    }

    /**
     * @return the first element in the list.
     */
    public Item peek_front() { return _head._next._item; }

    /**
     * Remove the first element and return it
     * @return an item
     * @throws NoSuchElementException if the list is empty
     */
    public Item pop_front() throws NoSuchElementException
    {
        if (isEmpty()) throw new NoSuchElementException();
        
        Item item = _head._next._item;
        
        // Delete the first valid node containing data; pass previous node to deleteNextNode
        deleteNextNode(_head);
        
        return item;
    }
    
    /**
     * Deletes the AFTER the given node (need to pass X.prev to delete X);
     * returns the same node in the list
     * @param prev -- a node: prev._next is deleted
     * @return prev
     * @throws RuntimeException if specified node does not point
     * to a valid list node.
     *  Nodes:   ...   prev   toDelete   ...  rest of list
     *
     */
    protected Node deleteNextNode(Node prev) throws RuntimeException
    {
    	// Throws exception to prevent deletion of Nodes not in between sentinels.
		if (atEnd(prev) || nodeNextToEnd(prev) || prev._next == null) {
			throw new RuntimeException();
		}
		// Removes old Node from list and updates size
		prev._next = prev._next._next;
		_size -= 1;
		return prev;
    }
    
    /**
     * Returns whether specified node is directly before the tail
     * sentinel 
     * @param prev Node being checked
     * @return Boolean indicating whether prev Node is next to the
     * tail sentinel.
     */
    protected boolean nodeNextToEnd(Node prev) {
    	return prev._next == this._tail;
    }
    
    /**
     * Returns whether specified node is the tail sentinel 
     * @param prev Node being checked
     * @return Boolean indicating whether node is the
     * tail sentinel.
     */
    protected boolean atEnd(Node node) {
    	return node == this._tail;
    }
    
    /**
     * Returns boolean of whether an item is inside the linked list.
     * @param target Item being searched for
     * @return Boolean indicating whether or not target was found
     */
    public boolean contains(Item target)
    {
    	return contains(target, this._head);
    }
    
    /**
     * Recursive implementation of contains function, searches
     * current node for item, returns true if target is found,
     * false if at end of list and no target found, and recursively
     * calls itself for next element if target not yet found.
     * @param target Item being searched for
     * @param current Node in list which is currently being searched.
     * @return Boolean indicating whether item is found.
     */
    protected boolean contains(Item target, Node current) {
    	if (atEnd(current)) { return false;}
    	if (target.equals(current._item)) { return true;}
    	return contains(target, current._next);
    }
    
    /**
     * Clears all nodes from a list. 
     */
    public void clear()
    {
    	// Recursively removes first element until all elements are removed.
    	if (isEmpty()) { return;}
    	pop_front();
    	clear();
    }

    /**
     * Finds the element in the middle node of the list. If the list
     * is of even length, then the "left" middle element will be returned.
     * @return Middle element of the list
     * @throws NoSuchElementException
     */
    public Item middle() throws NoSuchElementException
    {
        if (isEmpty()) { throw new NoSuchElementException();}
        return middle(_head, _head);
    }
    
    /**
     * Recursively finds the middle element of a linked list, given
     * two Nodes in the list. toEnd grows at twice the speed
     * of toMiddle and, when toEnd reaches nodeNextToEnd or atEnd,
     * toMiddle is returned. When both toEnd and toMiddle are initialized
     * to _head, will return the middle, or in the event of an even-length
     * list, "left" middle, element of the list.
     * @param toMiddle Node that will reach the middle of the list
     * @param toEnd Node searching for the end of the list.
     * @return
     */
    //    [_h] [_1] [_2] [_3] [_4] [_t]
    // tM   \___/\___/
    // tE   \________/\________/
    protected Item middle(Node toMiddle, Node toEnd) {
    	if (atEnd(toEnd) || nodeNextToEnd(toEnd)) { return toMiddle._item;}
    	return middle(toMiddle._next, toEnd._next._next);
    }
    
    /**
     * Reverses the order of Nodes in the list
     */
    public void reverse()
    {
    	reverse(_tail, _head._next);
    }    
    
    /**
     * Recursively reverses the order of Nodes in the list; 
     * @param prev Previous Node of list, which will become cur's next Node
     * @param cur Current Node of list being modified.
     */
    //        _________ _________
    //       /         >         \
    // [_h] [_1] [_2] [_3] [_4] [_t]
    //   \    \_<_/\_<_/\_<_//
    //    \_______>_________/
    protected void reverse(Node prev, Node cur) {
    	
    	if (atEnd(cur)) { return;}
    	
    	// Sets the old last value to be the first value
    	// by connecting it to head.
    	if (nodeNextToEnd(cur)) {
    		_head._next = cur;
    		cur._next = prev;
    		return;
    	}
    	Node newCur = cur._next;
    	cur._next = prev;
    	reverse(cur, newCur);
    	
    }
    
    /**
     * @return String representation of the contents of the list
     */
    @Override
    public String toString()
    {
        StringBuilder s = new StringBuilder();

        for (Node n = _head._next; n != _tail; n = n._next)
        {
            s.append(n._item + " ");
        }

        return s.toString();
    }
}