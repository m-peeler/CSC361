package heap;

import org.junit.jupiter.api.Test;

/**
 * @author calvin
 *
 */
class ClassicMinHeapTest extends MinHeapTest
{

	@Test
	void test_extractMin()
	{
		MinHeap<Integer> heap = new ClassicMinHeap<Integer>();

		extractMinSortedTest(heap);
		
		heap = new SortedListMinHeap<Integer>();
		
		extractMinShuffledTest(heap);
		
		heap = new SortedListMinHeap<Integer>();

		extractMinReverseTest(heap);
	}

}
