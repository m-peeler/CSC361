package Homework5;
import org.w3c.dom.Node;

public class BinaryDigitTree extends BinaryTree<Integer>{

	/**
	 * Floor divides the binary digit tree by 2.
	 */
	public void divideByTwo() {
		_root = _root._left;
	}
	
	/**
	 * Floor divides the binary tree by two to the power of the value input; 
	 * an input of 3 results in a floor division of 2 ^ 3, or 8.
	 * @param power Power of 2 which the value is being divided by.
	 */
	public void divideByPowTwo(int power) {
		for ( ; power > 0; power--) {
			divideByTwo();
		}
	}
	
	/**
	 * Recursively finds the value of the BinaryDigitTree
	 * @return Value of tree
	 */
	public int valueOfTreeRecur() {
		// Calls the recursive function
		return valueOfTree(0, _root);
	}
	
	/**
	 * Recursively finds the value of the tree
	 * @param level The current level of the tree that you are on.
	 * @param node The current node of the tree
	 * @return The integer value of the binary digit three.
	 */
	private int valueOfTree(int level, TreeNode<Integer> node) {
		
		if (node._left == null) {
			return node._right._data << level;
		}
		// Returns value bit shifted by level to raise it to power of two, 
		// added to recursive call of function
		return node._right._data << level + valueOfTree(level + 1, node._left);
	}
	
	/**
	 * Iteratively finds the value of the binary digit tree.
	 * @return The value of the binary digit tree. 
	 */
	public int valueOfTreeIter() {
		int value = 0;
		TreeNode<Integer> node = _root;
				
		for (int i = 0; node != null; i++, node = node._left) {
			// Bit shifts by i to raise to power of 2
			value += node._right._data << i;
		}
		
		return value;
	}
}
