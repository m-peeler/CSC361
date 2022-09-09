package LinkedList;

public class DoublyLinkedQuiz<T> {
	protected DNode _head, _tail;
	
	private class DNode {
		
		protected DNode _prev;
		protected T		_data;
		protected DNode _next;
		
		public DNode(DNode p, T data, DNode n) {
			_prev = p; 
			_data = data;
			_next = n;
		}
	}
	
	public DoublyLinkedQuiz() {
		_head._prev = null;
		_tail._next = null;
		_head._next = _tail;
		_tail._prev = _head;
	}
	
	public int size() {
		int i = 0;
		for (DNode cur = _head; cur._next != _tail; cur = cur._next) {
			i++;
		}
		return i;
	}
	
	public int recursiveSize() {
		DNode cur = _head;
		return recursiveSize(cur, 0);
	}
	
	public int recursiveSize(DNode cur, int i) {
		if (cur._next == _tail) {
			return i;
		}
		return recursiveSize(cur._next, i + 1);
	}
}
