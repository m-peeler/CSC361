package Homework5;

public class BinaryTree < DataT > {
	protected TreeNode < DataT > _root ;
	
	protected final class TreeNode < DataT > {
		
		public TreeNode < DataT > _left ;
		public TreeNode < DataT > _right ;
		public DataT _data ;

		public TreeNode ( TreeNode < DataT > left,
			DataT data, TreeNode < DataT > right) {
			_left = left ;
			_data = data ;
			_right = right ;
		}
		
		public TreeNode (DataT data) {
			this (null , data , null ) ;
		}
	}
}