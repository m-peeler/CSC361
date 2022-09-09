/**
 * An infix unparser for Expression Trees implemented with ASTnode classes.
 * Unparsing is implemented using a Visitor Design Pattern implementation,
 * thus inheriting from Visitor.
 * 
 *    Unary Postfix Operators
 *      A: UnaryAbsoluteValueNode
 *      S: UnarySquareRootNode
 *
 *    Binary Operators
 *      +:  BinaryPlusNode
 *      -:  BinaryMinusNode
 *      *:  BinaryTimesNode
 *      /:  BinaryDivideNode
 *      **: BinaryPowerNode
 *
 *    Unary Prefix
 *      +: UnaryPlusNode
 *      -: UNaryMinusNode
 *      
 *    Some sample expressions:
 *      ( 5 + 3 )
 *      ( 5.0 * 3.0 )
 *      ( ( A( ( A( 1.0 ) / ( S( 2.0 ) * ( A( 3.0 ) - ( S( 4.0 ) + -+5.0 ) ) ) )) + ----12.3 ) ** 4 )
 * 
 * @author M. Peeler
 * @adaptedFrom C. Alvin
 * 2/28/2021
 */
package ast;

import utils.Constants;

public class InfixUnparser extends Visitor {
	protected ASTnode _root;             // root of the Expression tree
	protected StringBuilder _sb;         // output stream we will write to
	
	public InfixUnparser(ASTnode root)
	{
		_root = root;
		_sb = new StringBuilder();
	}

	/**
     * Invokes unparsing of this tree using infix traversal.
     */
	public String unparse()
	{
		this.visit(_root);
		
		return _sb.toString();
	}
	
	@Override
	public void visit(UnaryPlusNode n)
	{
		_sb.append(Constants.PLUS);
		this.visit(n._expr);
	}
	
	@Override
	public void visit(UnaryMinusNode n)
	{
		_sb.append(Constants.MINUS);
		this.visit(n._expr);
	}

	@Override
	public void visit(UnaryAbsoluteValueNode n)
	{
		_sb.append(Constants.ABSOLUTE_VALUE);
		_sb.append(Constants.L_PAREN + Constants.DELIMITER);
		this.visit(n._expr);
		_sb.append(Constants.DELIMITER + Constants.R_PAREN);
	}
	
	@Override
	public void visit(UnarySquareRootNode n)
	{
		_sb.append(Constants.SQUARE_ROOT);
		_sb.append(Constants.L_PAREN + Constants.DELIMITER);
		this.visit(n._expr);
		_sb.append(Constants.DELIMITER + Constants.R_PAREN);

	}
	
	@Override
	public void visit(BinaryExprNode n) { }

	@Override
	public void visit(BinaryPlusNode n)
	{
		_sb.append(Constants.L_PAREN + Constants.DELIMITER);
		this.visit(n._left);
		_sb.append(Constants.DELIMITER);
		_sb.append(Constants.PLUS);
		_sb.append(Constants.DELIMITER);
		this.visit(n._right);
		_sb.append(Constants.DELIMITER + Constants.R_PAREN);
	}

	@Override
	public void visit(BinaryMinusNode n)
	{
		_sb.append(Constants.L_PAREN + Constants.DELIMITER);
		this.visit(n._left);
		_sb.append(Constants.DELIMITER);
		_sb.append(Constants.MINUS);
		_sb.append(Constants.DELIMITER);
		this.visit(n._right);
		_sb.append(Constants.DELIMITER + Constants.R_PAREN);
	}

	@Override
	public void visit(BinaryTimesNode n)
	{
		_sb.append(Constants.L_PAREN + Constants.DELIMITER);
		this.visit(n._left);
		_sb.append(Constants.DELIMITER);
		_sb.append(Constants.TIMES);
		_sb.append(Constants.DELIMITER);
		this.visit(n._right);
		_sb.append(Constants.DELIMITER + Constants.R_PAREN);
	}

	@Override
	public void visit(BinaryDivideNode n)
	{
		_sb.append(Constants.L_PAREN + Constants.DELIMITER);
		this.visit(n._left);
		_sb.append(Constants.DELIMITER);
		_sb.append(Constants.DIVIDE);
		_sb.append(Constants.DELIMITER);
		this.visit(n._right);
		_sb.append(Constants.DELIMITER + Constants.R_PAREN);
	}

	@Override
	public void visit(BinaryPowerNode n)
	{ 
		_sb.append(Constants.L_PAREN + Constants.DELIMITER);
		this.visit(n._left);
		_sb.append(Constants.DELIMITER);
		_sb.append(Constants.EXPONENTIAL);
		_sb.append(Constants.DELIMITER);
		this.visit(n._right);
		_sb.append(Constants.DELIMITER + Constants.R_PAREN);
	}

	@Override
	public void visit(LiteralNode n)
	{
		System.err.println("LiteralNode::Unparse");
	}

	@Override
	public void visit(IntLitNode n)
	{
		_sb.append(Integer.toString((int)n.get()));
	}

	@Override
	public void visit(RealLitNode n)
	{
		_sb.append(Double.toString(n.get()));
	}

	@Override
	public void visit(OperationNode n)
	{
		System.err.println("BinaryExprNode::Unparse");
	}

	@Override
	public void visit(UnaryExprNode n)
	{
		System.err.println("UnaryExprNode::Unparse");
	}

}
