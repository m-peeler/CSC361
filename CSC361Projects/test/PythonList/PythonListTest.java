package PythonList;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class PythonListTest {
	
	@Test
	void test_add() {
		PythonList<Integer> test_list = new PythonList<Integer>();
		
		final int VARS_TO_ADD = 10;
		
		ArrayList<Integer> expected = new ArrayList<Integer>();
		
		// Adds to mostly empty list, with positive increasing index
		for (int i = 0, curSize = 0; i < VARS_TO_ADD; i++) {
			if (i == 0) {
				test_list.add(10 + i);
				expected.add(10 + i);
			} else {
				test_list.add(i-1, 10 + i);
				expected.add(i-1, 10 + i);
			}
			curSize++;
			assertEquals(curSize, test_list.size());
			assertEquals(expected.toString(), test_list.toString());
		}
		
		// Adds to non-empty list, with positive increasing index
		for (int i = 0, curSize = VARS_TO_ADD; i < VARS_TO_ADD; i++) {
			test_list.add(i, 10 + i);
			expected.add(i, 10 + i);
			curSize++;
			assertEquals(curSize, test_list.size());
			assertEquals(expected.toString(), test_list.toString());
		}
				
		// Adds to non-empty list, with negative decreasing index
		for (int i = -1, curSize = 2 * VARS_TO_ADD; i >= -VARS_TO_ADD; i--) {
			test_list.add(i, i - 10);
			expected.add(expected.size() + i, i - 10);
			curSize++;
			assertEquals(curSize, test_list.size());
			assertEquals(expected.toString(), test_list.toString());
		}
		
		// Makes sure bounds are enforced
		assertThrows(IndexOutOfBoundsException.class, () -> {test_list.add(3 * VARS_TO_ADD + 1, 1);});
	}
	
	@Test
	void test_get() {
		PythonList<Integer> test_list = new PythonList<Integer>(Arrays.asList(10,11,12,13,14,15));
		
		// Makes sure positive indices get correct results
		for (int i = 0; i < test_list.size(); i++) {
			assertEquals(i + 10, test_list.get(i));
		}
		
		// Makes sure positive indices get correct results in reverse and after
		// adding an element
		for (int i = test_list.size() - 1; i >= 0; i--) {
			assertEquals(i + 10, test_list.get(i));
		}
		
		// Makes sure positive and negative indices get matching results
		for (int i = test_list.size() - 1, j=-1; i >= 0; i--, j--) {
			assertEquals(test_list.get(i), test_list.get(j));
		}
		
		test_list.add(17);
		test_list.add(18);
		
		// makes sure positive and negative indices get matching results
		// after adding two elements and in reverse order.
		for (int i = 0, j = -test_list.size(); i < test_list.size(); i++, j++) {
			assertEquals(test_list.get(i), test_list.get(j));
		}
		
		
	}
	
	@Test
	void test_remove() {
		ArrayList<Integer> comp_list = new ArrayList<Integer>(Arrays.asList(10,11,12,13,14,15,16,17,18));
		PythonList<Integer> test_list = new PythonList<Integer>(Arrays.asList(10,11,12,13,14,15,16,17,18));
		
		// Checks for removing items at positive indices in a PythonList
		for (int i = test_list.size() - 1; i >= 0; i--) {
			assertEquals(comp_list.get(i),test_list.remove(i));
			for (int j = 0; j < test_list.size(); j++) {
				assertEquals(comp_list.get(j), test_list.get(j));
			}
		}
		
		comp_list = new ArrayList<Integer>(Arrays.asList(10,11,12,13,14,15,16,17,18));
		test_list = new PythonList<Integer>(Arrays.asList(10,11,12,13,14,15,16,17,18));
		
		// Checks for removing items at negative indices in a PythonList
		for (int i = 0; i < comp_list.size(); i++) {
			assertEquals(comp_list.get(test_list.size() - 1),test_list.remove(-1));
			for (int j = 0; j < test_list.size(); j++) {
				assertEquals(comp_list.get(j), test_list.get(j));
			}
		}
		
		comp_list = new ArrayList<Integer>(Arrays.asList(10,11,12,13,14,15,16,17,18));
		test_list = new PythonList<Integer>(Arrays.asList(10,11,12,13,14,15,16,17,18));
		
		// Checks for removing items at changing indices in a PythonList
		for (int i = 0; i < comp_list.size(); i++) {
			assertEquals(comp_list.get(i),test_list.remove(0));
			for (int j = 0; j < test_list.size(); j++) {
				assertEquals(comp_list.get(1 + i + j), test_list.get(j));
			}
		}
		
		comp_list = new ArrayList<Integer>(Arrays.asList(10,11,12,13,14,15,16,17,18));
		test_list = new PythonList<Integer>(Arrays.asList(10,11,12,13,14,15,16,17,18));
		
		// Checks for removing items at changing negative 
		// indices in a PythonList
		for (int i = -comp_list.size(); i < 0; i++) {
			assertEquals(comp_list.get(comp_list.size() + i),test_list.remove(i));
			for (int j = -1; j > -test_list.size(); j--) {
				assertEquals(comp_list.get(comp_list.size() + j), test_list.get(test_list.size() + j));
			}
		}
	}
	
	@Test
	void test_set() {
		PythonList<Integer> test_list = new PythonList<Integer>(Arrays.asList(10,11,12,13,14,15));
		
		// Sets values at positive indices
		for (int i = 0; i < test_list.size(); i++) {
			assertEquals(i + 10, test_list.set(i,  i));
			assertEquals(i, test_list.get(i));
		}
		
		// Sets values at negative indices
		for (int i = -1; i >= -test_list.size(); i--) {
			assertEquals(i + test_list.size(), test_list.set(i, i));
			assertEquals(i, test_list.get(i));
		}
		
		// Makes sure that values cannot be set outside of permitted range
		assertThrows(IndexOutOfBoundsException.class, () -> {test_list.set(6, 1);});
		assertThrows(IndexOutOfBoundsException.class, () -> {test_list.set(-7, 1);});
		test_list.set(-6, 1);
		// Makes sure negative range is larger than positive range
		assertEquals(1, test_list.get(-6));
	}
	
	@Test
	void test_subList() {
		PythonList<Integer> test_list = new PythonList<Integer>(Arrays.asList(10,11,12,13,14,15));		
		int FIRST_INDEX = 0;
		int SECOND_INDEX = 3;
		int THIRD_INDEX = test_list.size();
		
		// Tests sublist creation with positive indices
		PythonList<Integer> sub_list_1 = test_list.subList(FIRST_INDEX, SECOND_INDEX);
		assertEquals(SECOND_INDEX - FIRST_INDEX, sub_list_1.size());
		for (int i = FIRST_INDEX; i < SECOND_INDEX; i++) {
			assertEquals(test_list.get(i), sub_list_1.get(i - FIRST_INDEX));
		}
		
		PythonList<Integer> sub_list_2 = test_list.subList(SECOND_INDEX, THIRD_INDEX);
		assertEquals(THIRD_INDEX - SECOND_INDEX, sub_list_2.size());
		for (int i = SECOND_INDEX; i < THIRD_INDEX; i++) {
			assertEquals(test_list.get(i), sub_list_2.get(i - SECOND_INDEX));
		}		

		// Tests sublist creation with positive indices
		FIRST_INDEX = -test_list.size();
		SECOND_INDEX = -3;
		THIRD_INDEX = test_list.size();
		
		sub_list_1 = test_list.subList(FIRST_INDEX, SECOND_INDEX);
		assertEquals(SECOND_INDEX - FIRST_INDEX, sub_list_1.size());
		for (int i = FIRST_INDEX; i < SECOND_INDEX; i++) {
			assertEquals(test_list.get(i), sub_list_1.get(i - FIRST_INDEX));
		}
		
		sub_list_2 = test_list.subList(SECOND_INDEX, THIRD_INDEX);
		assertEquals(THIRD_INDEX + SECOND_INDEX, sub_list_2.size());
		for (int i = -3; i < 0; i++) {
			assertEquals(test_list.get(i), sub_list_2.get(i));
		}
		
		// Tests for correct exceptions
		assertThrows(IllegalArgumentException.class, () -> {test_list.subList(2, 1);});
		assertThrows(IndexOutOfBoundsException.class, () -> {test_list.subList(0, test_list.size() + 1);});
		assertThrows(IndexOutOfBoundsException.class, () -> {test_list.subList(-1 - test_list.size(), test_list.size() - 1);});
		
		// Tests for 0-length sublist generation
		assertEquals(new PythonList<Integer>(), test_list.subList(1,1));
		assertEquals(new PythonList<Integer>(), test_list.subList(-test_list.size(), 0));
		assertEquals(new PythonList<Integer>(), test_list.subList(-test_list.size(), -test_list.size()));
		assertEquals(new PythonList<Integer>(), test_list.subList(0, -test_list.size()));
	}
	
	@Test
	void test_barrage() {
		PythonList<Integer> original_list = new PythonList<Integer>(Arrays.asList(10,11,12,13,14,15));
		PythonList<Integer> test_list = new PythonList<Integer>(original_list);		

		// Removes all items from the test list, getting comparison values
		// using negative indices
		for (int i = 0; i < original_list.size(); i++) {
			assertEquals(original_list.get(-original_list.size() + i), test_list.remove(0));
			assertEquals(original_list.size() - i - 1, test_list.size());
		}		
		
		test_list.add(9);
		// Adds all values back in reverse using negative indices
		for (int i = -1, curVal = 0; i >= -original_list.size(); i--, curVal ++) {
			test_list.add(i, original_list.get(curVal));
			assertEquals(original_list.get(curVal), test_list.get(0));
		}
		test_list.remove(test_list.size() - 1);
		
		// Tests sublist creation using positive and negative indices
		for (int i = 0; i < test_list.size(); i++) {
			PythonList<Integer> subList1 = test_list.subList(0, i);
			PythonList<Integer> subList2 = test_list.subList(-test_list.size(), -test_list.size() + i);
			assertEquals(subList1.size(), subList2.size());
			for (int dex = 0, in = -subList1.size(); dex < subList1.size(); dex++, in++) {
				assertEquals(subList1.get(dex), subList2.get(in));
			}
		}
		
		// Removes all values using negative indices, getting
		// comparison values using positive indices.
		for (int i = -original_list.size(), j = -1; i < 0; i++, j--) {
			assertEquals(original_list.get(j), test_list.remove(i));
			assertEquals(-i - 1, test_list.size());
		}
		
		test_list.add(9);
		//Adds all values back in normal order using positive indices
		for (int i = 0; i < original_list.size(); i++) {
			test_list.add(i, original_list.get(i));
			assertEquals(original_list.get(i), test_list.get(-2));
		}
	}
}


