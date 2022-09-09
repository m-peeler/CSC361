package Matrix;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List; 

import org.junit.jupiter.api.Test;

class MatrixTest {

	@Test
	void testConstructorEmpty() { 
		Matrix mx = new Matrix(5, 4);
		assertEquals("0.0 0.0 0.0 0.0 \n" + 
				"0.0 0.0 0.0 0.0 \n" + 
				"0.0 0.0 0.0 0.0 \n" + 
				"0.0 0.0 0.0 0.0 \n" + 
				"0.0 0.0 0.0 0.0 \n", mx.toString());
		assertEquals(5, mx.getNumRows());
		assertEquals(4, mx.getNumCols());
		for (int i = 0; i < mx.getNumRows(); i++) {
			for (int j = 0; j < mx.getNumCols(); j++) {
				assertEquals(0.0, mx.get(i, j));
			}
		}
	}
	
	@Test 
	void testConstructorShortList() {
		List<Double> lst = new ArrayList<Double>(Arrays.asList(1.1, 2.2, 3.3, 4.4));
		Matrix mx = new Matrix(lst, 4, 2);
		assertEquals("1.1 2.2 \n" + 
				"3.3 4.4 \n" + 
				"0.0 0.0 \n" + 
				"0.0 0.0 \n", mx.toString());
		assertEquals(4, mx.getNumRows());
		assertEquals(2, mx.getNumCols());
		assertThrows(IndexOutOfBoundsException.class, ()-> {mx.get(4,3);});
		int index = 0;
		for (int i = 0; i < mx.getNumRows(); i++) {
			for (int j = 0; j < mx.getNumCols(); j++) {
				index = i * mx.getNumCols() + j;
				if (index < lst.size()) {
					assertEquals(lst.get(index), mx.get(i, j));
				} else {
					assertEquals(0.0, mx.get(i, j));
				}			
			}
		}
	}
	
	@Test
	void testConstructorEqualList() {
		List<Double> lst = new ArrayList<Double>(Arrays.asList(1.1, 2.2, 3.3, 4.4));
		Matrix mx = new Matrix(lst, 4, 1);
		assertEquals("1.1 \n2.2 \n3.3 \n4.4 \n", mx.toString());
		assertEquals(4, mx.getNumRows());
		assertEquals(1, mx.getNumCols());
		int index;
		for (int i = 0; i < mx.getNumRows(); i++) {
			for (int j = 0; j < mx.getNumCols(); j++) {
				index = i * mx.getNumCols() + j;
				if (index < lst.size()) {
					assertEquals(lst.get(index), mx.get(i, j));
				} else {
					assertEquals(0.0, mx.get(i, j));
				}
			}
		}
		assertThrows(IndexOutOfBoundsException.class, ()-> {mx.get(4,1);});
	}
	
	@Test
	void testConstructorLongList() {
		List<Double> lst = new ArrayList<Double>(Arrays.asList(1.1, 2.2, 3.3, 4.4));
		Matrix mx = new Matrix(lst, 3, 1);
		assertEquals("1.1 \n2.2 \n3.3 \n", mx.toString());
		assertEquals(3, mx.getNumRows());
		assertEquals(1, mx.getNumCols());
		int index;
		for (int i = 0; i < mx.getNumRows(); i++) {
			for (int j = 0; j < mx.getNumCols(); j++) {
				index = i * mx.getNumCols() + j;
				if (index < lst.size()) {
					assertEquals(lst.get(index), mx.get(i, j));
				} else {
					assertEquals(0.0, mx.get(i, j));
				}
			}
		}
		assertThrows(IndexOutOfBoundsException.class, ()-> {mx.get(4,0);});
	}
	
	@Test
	void testPlusNoExceptionsSameValues() {
		List<Double> arr = new ArrayList<Double>(Arrays.asList(1.0, 1.0, 1.0, 1.0));
		Matrix mx1 = new Matrix(arr, 2, 2);
		Matrix mx2 = new Matrix(arr, 2, 2);
		Matrix mx3 = mx1.plus(mx2);
		for (int i = 0; i < mx1.getNumRows(); i++) {
			for (int j = 0; j < mx2.getNumCols(); j++) {
				assertEquals(mx1.get(i, j) + mx2.get(i, j), mx3.get(i, j));
				assertEquals(2.0, mx3.get(i, j));
			}
		}
	}

	@Test
	void testPlusNoExceptionsDiffValuesSameLists() {
		List<Double> arr = new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0));
		Matrix mx1 = new Matrix(arr, 2, 2);
		Matrix mx2 = new Matrix(arr, 2, 2);
		Matrix mx3 = mx1.plus(mx2);
		for (int i = 0; i < mx1.getNumRows(); i++) {
			for (int j = 0; j < mx2.getNumCols(); j++) {
				assertEquals(mx1.get(i, j) + mx2.get(i, j), mx3.get(i, j));
				assertEquals(arr.get(i * mx1.getNumCols() + j) * 2.0, mx3.get(i, j));
			}
		}
	}
	
	@Test
	void testPlusNoExceptionsDiffValuesDiffLists() {
		List<Double> arr1 = new ArrayList<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0));
		List<Double> arr2 = new ArrayList<Double>(Arrays.asList(4.0, 3.0, 2.0, 1.0));
		Matrix mx1 = new Matrix(arr1, 2, 2);
		Matrix mx2 = new Matrix(arr2, 2, 2);
		Matrix mx3 = mx1.plus(mx2);
		for (int i = 0; i < mx1.getNumRows(); i++) {
			for (int j = 0; j < mx2.getNumCols(); j++) {
				assertEquals(mx1.get(i, j) + mx2.get(i, j), mx3.get(i, j));
				assertEquals(arr1.get(i * mx1.getNumCols() + j) +
						arr2.get(i * mx1.getNumCols() + j), 
						mx3.get(i, j));
			}
		}
	}	
	
	@Test 
	void testPlusExceptionThrows() {
		List<Double> arr = new ArrayList<Double>(Arrays.asList(1.0, 1.0, 1.0, 1.0));
		Matrix mx1 = new Matrix(arr, 2, 2);
		assertThrows(RuntimeException.class, ()->{mx1.plus(Matrix.create(3, 2));});
		assertThrows(RuntimeException.class, ()->{mx1.plus(Matrix.create(2, 3));});
		assertThrows(RuntimeException.class, ()->{mx1.plus(Matrix.create(3, 3));});
		assertThrows(RuntimeException.class, ()->{mx1.plus(Matrix.create(1, 2));});
		assertThrows(RuntimeException.class, ()->{mx1.plus(Matrix.create(2, 1));});
		assertThrows(RuntimeException.class, ()->{mx1.plus(Matrix.create(1, 1));});
	}
	
	@Test 
	void testTimesExceptionThrows() {
		List<Double> arr = new ArrayList<Double>(Arrays.asList(1.0, 1.0, 1.0, 1.0));
		Matrix mx1 = new Matrix(arr, 2, 2);
		assertThrows(RuntimeException.class, ()->{mx1.times(Matrix.create(3, 1));});
		assertThrows(RuntimeException.class, ()->{mx1.times(Matrix.create(3, 2));});
		assertThrows(RuntimeException.class, ()->{mx1.times(Matrix.create(3, 3));});
		assertThrows(RuntimeException.class, ()->{mx1.times(Matrix.create(1, 2));});
		assertThrows(RuntimeException.class, ()->{mx1.times(Matrix.create(1, 3));});
		assertThrows(RuntimeException.class, ()->{mx1.times(Matrix.create(1, 1));});
	}
	
	@Test
	void testTimesSameDimensionsSameValAllCells() {
		List<Double> arr1 = new ArrayList<Double>(Arrays.asList(2.0, 2.0, 2.0, 2.0));
		List<Double> arr2 = new ArrayList<Double>(Arrays.asList(2.0, 2.0, 2.0, 2.0));
		Matrix mx1 = new Matrix(arr1, 2, 2);
		Matrix mx2 = new Matrix(arr2, 2, 2);
		Matrix mx3 = mx1.times(mx2);
		for (int i = 0; i < mx3.getNumRows(); i++) {
			for (int j = 0; j < mx3.getNumCols(); j++) {
				assertEquals(8,	mx3.get(i,  j));
			}
		}
	}
	
	@Test
	void testTimesDiffDimensionsSameValAllCells() {
		List<Double> arr1 = new ArrayList<Double>(Arrays.asList(2.0, 2.0, 2.0, 2.0));
		List<Double> arr2 = new ArrayList<Double>(Arrays.asList(2.0, 2.0, 2.0, 2.0));
		Matrix mx1 = new Matrix(arr1, 4, 1);
		Matrix mx2 = new Matrix(arr2, 1, 4);
		Matrix mx3 = mx1.times(mx2);
		for (int i = 0; i < mx3.getNumRows(); i++) {
			for (int j = 0; j < mx3.getNumCols(); j++) {
				assertEquals(4,	mx3.get(i,  j));
			}
		}
		
		Matrix mx4 = mx2.times(mx1);
		for (int i = 0; i < mx4.getNumRows(); i++) {
			for (int j = 0; j < mx4.getNumCols(); j++) {
				assertEquals(16, mx4.get(i,  j));
			}
		}
	}
	
	@Test
	void testTimesDiffDimensionsDiffVals() {
		List<Double> lst1 = new ArrayList<Double>(Arrays.asList(4.0, 5.0, 8.0, -1.0, 4.0, 6.0));
		Matrix mx = new Matrix(lst1, 2, 3);
		List<Double> lst2 = new ArrayList<Double>(Arrays.asList(2.0, 6.0, 9.0));
		Matrix mn = new Matrix(lst2, 3, 1);
		Matrix mv = mx.times(mn);
		assertEquals("110.0 \n76.0 \n", mv.toString());
	}
	
	@Test 
	void testTimesSameDimensionsDiffVals() {
		List<Double> lst1 = new ArrayList<Double>(Arrays.asList(4.0, 5.0, 8.0, -1.0));
		Matrix mx = new Matrix(lst1, 2, 2);
		List<Double> lst2 = new ArrayList<Double>(Arrays.asList(2.0, 6.0, 9.0, 8.0));
		Matrix mn = new Matrix(lst2, 2, 2);
		Matrix mv = mx.times(mn);
		assertEquals("53.0 64.0 \n7.0 40.0 \n", mv.toString());
	}
	
	@Test
	void testCreate() {
		Matrix test = Matrix.create(4, 5);
		assertEquals(test.getNumRows(), 4);
		assertEquals(test.getNumCols(), 5);
		for (int i = 0; i < test.getNumRows(); i ++) {
			for (int j = 0; j < test.getNumCols(); j++) {
				assertTrue(test.get(i, j) < 1 && test.get(i, j) >= 0);
			}
		}
		assertThrows(IndexOutOfBoundsException.class, () -> {test.get(4, 5);});
	}

}
