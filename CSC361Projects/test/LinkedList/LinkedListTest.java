package LinkedList;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import LinkedList.LinkedList;

class LinkedListTest
{
	private LinkedList<Integer> populateLinkedList(List<Integer> values)
	{
        LinkedList<Integer> list = new LinkedList<Integer>();

        // Push elements in reverse order
        for (int i = values.size() - 1; i >= 0; i--)
        {
        	list.push_front(values.get(i));
        }
        
        return list;
	}
	
	@Test
	void push_frontTest()
	{
        LinkedList<Integer> list = new LinkedList<Integer>();

        list.push_front(3);
        list.push_front(4);
        list.push_front(5);

        assertEquals("Size 1a", 3, list.size());
        assertEquals("Size 1b ", "5 4 3 ", list.toString());
        
        
        list = new LinkedList<Integer>();

        list.push_front(1);
        list.push_front(-1);
        list.push_front(2);
        list.push_front(-2);
        list.push_front(3);
        list.push_front(-3);

        assertEquals("Size 2a", 6, list.size());
        assertEquals("Size 2b ", "-3 3 -2 2 -1 1 ", list.toString());
	}
	
	@Test
	void pop_frontTest()
	{
        LinkedList<Integer> list = new LinkedList<Integer>();

        list.push_front(1);
        list.push_front(2);
        list.push_front(3);

        Integer item = list.pop_front();
        
        assert list.size() == 2 : "Size not 2: " + list.size();
        assert item.equals(3) : "Item discontinuity: " + item;
        assert list.toString().equals("2 1 ") : list.toString();
        
        assertEquals("pop_front 1a", 2, list.size());
        assertEquals("pop_front 1b", (Integer)3, item);
        assertEquals("pop_front 1c", "2 1 ", list.toString());
        
        list.push_front(10);
        list.push_front(11);
        list.push_front(12);

        item = list.pop_front();

        assertEquals("pop_front 2a", 4, list.size());
        assertEquals("pop_front 2b", (Integer)12, item);
        assertEquals("pop_front 2c", "11 10 2 1 ", list.toString());
      
        item = list.pop_front();
        item = list.pop_front();

        assertEquals("pop_front 3a", 2, list.size());
        assertEquals("pop_front 3b", (Integer)10, item);
        assertEquals("pop_front 3c", "2 1 ", list.toString());

        item = list.pop_front();
        item = list.pop_front();

        assertEquals("pop_front 4a", 0, list.size());
        assertEquals("pop_front 4b", (Integer)1, item);
        assertEquals("pop_front 4c", "", list.toString());
        
        assertThrows(RuntimeException.class, () -> {list.pop_front();});
	}
	
	@Test
	void clearTest()
	{
        LinkedList<Integer> list = new LinkedList<Integer>();
        
        LinkedList.Node head = list._head;
        LinkedList.Node tail = list._tail;
        list.clear();
        
        assertEquals("Checks for sentinel head", head, list._head);
        assertEquals("Checks for element after head", head._next, list._head._next);
        assertEquals("Checks for sentinel tail", tail, list._tail);
        assertEquals("Checks for element after head", null, list._tail._next);        
        
        list.push_front(1);
        list.push_front(2);
        list.push_front(3);

        list.clear();
        
        assertEquals("clear 1a", 0, list.size());
        assertTrue("clear 1b", list.isEmpty());
        
        List<Integer> original = Arrays.asList(-2, 4, 8, 0, 7, 5, 6, 3, 4, 2, 1, 12);
        list = populateLinkedList(original);
        
        list.reverse();
        
        list.clear();
        
        assertEquals("clear 2a", 0, list.size());
        assertTrue("clear 2b", list.isEmpty());
        
        for (Integer item: original) {
        	assertFalse(list.contains(item));
        }
        
        list.clear();
        list.clear();
        list.clear();
        
        list.push_front(10);
        
        assertEquals("post clear size 3a", 1, list.size());
        assertFalse("clear 3b", list.isEmpty());
        assertTrue(list.contains(10));        
	}
	
	@Test
	void containsTest()
	{
        assertFalse("Empty", new LinkedList<Integer>().contains(1));

        List<Integer> original = Arrays.asList(-2, 4, 8, 0);
        LinkedList<Integer> list = populateLinkedList(original);
        
        for (Integer item : original)
        {
            assertTrue(list.contains(item));
        }
        
        list.clear();

        for (Integer item : original)
        {
            assertFalse(list.contains(item));
        }
        
        List<String> newOrig = Arrays.asList("A", "B", "C", "D");
        LinkedList<String> newList = new LinkedList<String>();
        
        for (String item : newOrig) {
        	newList.push_front(item);
        	assertTrue(newList.contains(item));
        }
        
        assertTrue(newList.contains(newList.middle()));
        
        newList.clear();
        
        for (String item : newOrig) {
        	assertFalse(newList.contains(item));
        }
        
        newList.push_front("A");
        newList.push_front("b");
        
        assertTrue(newList.contains("A"));
        assertTrue(newList.contains("b"));
        
        assertFalse(newList.contains("a"));
        assertFalse(newList.contains("B"));
        
        newList.reverse();
        
        assertTrue(newList.contains("A"));
        assertTrue(newList.contains("b"));
        
        assertFalse(newList.contains("a"));
        assertFalse(newList.contains("B"));        
             
        
	}
	
	@Test
	void middleTest()
	{
        assertThrows(NoSuchElementException.class, () -> { new LinkedList<Integer>().middle(); } );

        LinkedList<Integer> list = populateLinkedList(Arrays.asList(-2, 4, 8, 0));

        assertEquals("Even Middle", (Integer)4, list.middle());
        
        list = populateLinkedList(Arrays.asList(-2, 8, 0));

        assertEquals("Odd Middle", (Integer)8, list.middle());
        
        list = populateLinkedList(Arrays.asList(-2));
        
        assertEquals("Tiny Middle", (Integer)(-2), list.middle());
        
        list = populateLinkedList(Arrays.asList(-2, 4, 8, 0, 7, 5, 6, 3, 4, 2, 1, 12));
        
        assertEquals("Big Even Middle", (Integer)5, list.middle());
        
        list = populateLinkedList(Arrays.asList(-2, 4, 8, 0, 7, 5, 6, 3, 4, 2, 1, 12, 13));
        
        assertEquals("Big Odd Middle", (Integer)6, list.middle());
        
        list.clear();
        
        assertThrows(NoSuchElementException.class, () -> {
        		LinkedList<Integer> lst = populateLinkedList(Arrays.asList(-2, 4, 8, 0)); 
        		lst.clear();
        		lst.middle();
        	}
        );

	}
	
	@Test
	void reversalTest()
	{
        LinkedList<Integer> list = new LinkedList<Integer>();

        list.reverse();
        assertEquals("Empty Reversal", list.toString(), "");
        
        list.push_front(1);
        list.push_front(2);
        list.push_front(3);

        int sz_before = list.size();
        list.reverse();
        
        assertEquals("Size unequal on reversal", list.size(), sz_before);
        assertEquals("Reversal Contents", list.toString(), "1 2 3 ");
        
        
        list = populateLinkedList(Arrays.asList(1, 2, 3, 4));
        sz_before = list.size();
        list.reverse();
        list.reverse();
        
        assertEquals("Size unequal on reversal", list.size(), sz_before);
        assertEquals("Reversal Contents", list.toString(), "1 2 3 4 ");
        
        list = populateLinkedList(Arrays.asList(1, 2));
        sz_before = list.size();
        list.reverse();
        
        assertEquals("Size unequal on reversal", list.size(), sz_before);
        assertEquals("Reversal Contents", list.toString(), "2 1 ");        
        
        list = populateLinkedList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        sz_before = list.size();
        list.reverse();
        
        assertEquals("Size unequal on reversal", list.size(), sz_before);
        assertEquals("Reversal Contents", list.toString(), "11 10 9 8 7 6 5 4 3 2 1 ");
        
        list.clear();
        list.push_front(1);
        list.reverse();
        
        assertEquals("Size unequal on reversal", list.size(), 1);
        assertEquals("Reversal Contents", list.toString(), "1 ");
	}
}
