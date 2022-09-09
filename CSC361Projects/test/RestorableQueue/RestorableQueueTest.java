/**
 * JUnit test cases for the RestorableQueue class.
 * @author Michael Peeler
 *
 */

package RestorableQueue;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class RestorableQueueTest {

	/**
	 * Tests toArray by making sure that the array is of the expected length and contains the
	 * expected element.s
	 */
	@Test
	void testToArray() {
		
		RestorableQueue<Integer> q = new RestorableQueue<Integer>();
		
		Object[] arr = new Object[100];
		
		for(int i = 0; i < 100; i++) {
			
			q.enqueue(i);		
			arr[i] = i;
			
			Object[] curArr = q.toArray();
			
			assertEquals(i + 1, curArr.length);

			for (int j = 0; j <= i; j++) {
				assertEquals(arr[j], curArr[j]);
			}
		}		
	}
	
	/**
	 * Tests contains function to ensure that various elements are and are not returned as 
	 * being "contained.:
	 */
	@Test
	void testContains() {

		RestorableQueue<Integer> q = new RestorableQueue<Integer>();
		
		Object[] arr = new Object[100];
		
		for(int i = 0; i < 100; i++) {
			q.enqueue(i);
			
			for (int j = 0; j < 100; j++) {
				if (j > i) {
					assertFalse(q.contains(j));
				}
				else {
					assertTrue(q.contains(j));
				}
			}
		}
		
		for(int i = 99; i >= 0; i--) {
			q.dequeue();
			
			for (int j = 99; j >= 0; j--) {
				if (j > 99 - i) {
					assertTrue(q.contains(j));
				}
				else {
					assertFalse(q.contains(j));
				}
			}
		}
	}
	
	/**
	 * Makes sure constructor constructs with an empty queue.
	 */
	@Test
	void testConstructor() {
		RestorableQueue<Integer> q = new RestorableQueue<Integer>();
		
		assertEquals(0, q.size());
		assertTrue(q.isEmpty());
	}
	
	/**
	 * Tests size as various elements are added and removed.
	 */
	@Test
	void testSize() {
		RestorableQueue<Integer> q = new RestorableQueue<Integer>();
		
		assertEquals(0, q.size());
		
		for(int i = 1; i <= 100; i++) {
			
			q.enqueue(i);
			assertEquals(i, q.size());
			
		}

		for(int i = 100; i >= 1; i--) {
			assertEquals(i, q.size());
			q.dequeue();
		}
	}
	
	/**
	 * Tests isEmpty by checking after a variety of enqueues and dequeues. 
	 */
	@Test
	void testIsEmpty() {
		RestorableQueue<Integer> q = new RestorableQueue<Integer>();
		assertTrue(q.isEmpty());
		
		for (int i = 10; i < 100; i++) {
			q.enqueue(i);
			assertFalse(q.isEmpty());
			
			q.dequeue();
			assertTrue(q.isEmpty());
		}
		
		for (int i = 0; i < 50; i++) {
			q.enqueue(i);
			assertFalse(q.isEmpty());
		}
		
		for (int i = 0; i < 50; i++) {
			assertFalse(q.isEmpty());
			q.dequeue();
		}
		
		assertTrue(q.isEmpty());

	}

	/**
	 * Tests clear by enqueueing lots of elements, then clearing and
	 * making certain that the function is empty; does so several times.
	 */
	@Test
	void testClear() {
		RestorableQueue<Integer> q = new RestorableQueue<Integer>();
		
		for (int i = 0; i < 20; i ++) {
			for (int j = 0; j <= i; j++) {
		
				q.enqueue(j);
			
			}
			
			q.clear();
			assertTrue(q.isEmpty());
			assertEquals(0, q.size());
		}
	}
	
	/**
	 * Tests enqueue by making sure that elements are added and size increases,
	 * also makes sure that the array now contains the value.
	 */
	@Test
	void testEnqueue() {
		RestorableQueue<Integer> q = new RestorableQueue<Integer>();

		for (int i = 1; i <= 100; i++) {
			q.enqueue(i);
			assertFalse(q.isEmpty());
			assertEquals(i, q.size());
			assertTrue(q.contains(i));
		}
		
		for (int i = 1; i <= 100; i++) {
			q.enqueue(-i);
			assertFalse(q.isEmpty());
			assertEquals(100 + i, q.size());
			assertTrue(q.contains(i));
		}
	}
	
	/**
	 * Tests dequeue by making sure that size decreases, and that correct element is dequeued.
	 */
	@Test
	void testDequeue() {
		RestorableQueue<Integer> q = new RestorableQueue<Integer>();
		
		assertThrows(NoSuchElementException.class, () -> {q.dequeue();});
		
		for (int i = 0; i <100; i++) {
			q.enqueue(i);
			assertEquals(i + 1, q.size());
		}
		for (int i = 0; i < 100; i++) {
			assertEquals(i, q.dequeue());
			assertEquals(99 - i, q.size());
		}
		
		assertThrows(NoSuchElementException.class, () -> {q.dequeue();});
		assertTrue(q.isEmpty());
		assertEquals(0, q.size());
	}
	
	/**
	 * Tests peek function by making sure that the first added element is always 
	 * peeked first, then dequeues and makes sure that peeked element is dequeued element.
	 */
	@Test
	void testPeek() {
		RestorableQueue<Integer> q = new RestorableQueue<Integer>();
		
		for (int i = 0; i < 100; i++) {
			q.enqueue(i);
			assertEquals(0, q.peek());
		}
		
		for (int i = 0; i < 100; i++) {
			assertEquals(i, q.peek());
			assertEquals(q.peek(), q.dequeue());
		}
		
		assertEquals(null, q.peek());	
	}
	
	/**
	 * Tests the save state function by making sure that the state is added to the stack,
	 * and that the restorations restore to the right length queue.
	 */
	@Test
	void testSaveState() {
		RestorableQueue<Integer> q = new RestorableQueue<Integer>();
		assertTrue(q._momentos.isEmpty());
		
		for (int i = 0; i < 100; i++) {	q.enqueue(i); }
		
		for (int i = 0; i < 20; i++) {
			q.saveState();
			q.enqueue(100 + i);
			assertEquals(1 + i, q._momentos.size());
			assertEquals(i + 101, q._queue.size());
		}
		
		for (int i = 19; i >= 0; i--) {
			assertEquals(i + 1, q._momentos.size());
			assertTrue(q.revertState());
			assertEquals(100 + i, q._queue.size());
			assertEquals(i, q._momentos.size());
		}

		assertFalse(q.revertState());
	}
	
	/**
	 * Tests function to restore save state; makes sure that restoration occurs in the correct
	 * order and that correct versions are restored.
	 */
	@Test
	void testRestore() {
		RestorableQueue<Integer> q = new RestorableQueue<Integer>();
		assertTrue(q._momentos.isEmpty());
		
		for (int i = 0; i < 100; i++) { 
			q.enqueue(i); 
			q.saveState();
		}
		
		for (int i = 99; i >= 0; i--) {
			assertTrue(q.revertState());
			assertFalse(q._queue.isEmpty());
			assertEquals(i + 1, q._queue.size());
			
			for (int j = 0; j <= i; j++) {
				assertEquals(j, q.dequeue());
			}
			
			assertTrue(q._queue.isEmpty());
			
			q.saveState();
			q.enqueue(0);	
			assertEquals(1, q._queue.size());
			
			assertTrue(q.revertState());
			assertEquals(0, q._queue.size());
		}
		
		assertFalse(q.revertState());
	}
}
