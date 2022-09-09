/**
 * An evaluator for Expression Trees implemented with ASTnode classes.
 * Evaluation is implemented using a Visitor Design Pattern implementation,
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
 *      5 3 + -> 8
 *      5.0 3.0 * -> 15.0
 *      1.0 A 2.0 S 3.0 A 4.0 S -+5.0 + - * / A ----12.3 + 4 ** -> 23778.573440897722
 * 
 * @author M. Peeler
 * @adaptedFrom C. Alvin
 * 2/28/2022
 */
package ast;

import utils.Constants;

public class Evaluator extends Visitor
{
	protected ASTnode _root;             // root of the Expression tree
	protected double _db;         // output stream we will write to
	

	public Evaluator(ASTnode root)
	{
		_root = root;
		_db = 0.0;
	}

	/**
     * Invokes unparsing of this tree to evaluate using postfix traversal.
     */
	public double unparse()
	{
		this.visit(_root);
		
		return _db;
	}
	
	@Override
	public void visit(UnaryPlusNode n)
	{
		this.visit(n._expr);
		_db *= 1;
		
	}
	
	@Override
	public void visit(UnaryMinusNode n)
	{
		this.visit(n._expr);
		_db *= -1;
	}

	@Override
	public void visit(UnaryAbsoluteValueNode n)
	{
		this.visit(n._expr);
		_db = java.lang.Math.abs(_db);
	}
	
	@Override
	public void visit(UnarySquareRootNode n)
	{
		this.visit(n._expr);
		_db = java.lang.Math.sqrt(_db);
	}
	
	@Override
	public void visit(BinaryExprNode n) { }

	@Override
	public void visit(BinaryPlusNode n)
	{
		this.visit(n._left);
		double val1 = _db;
		this.visit(n._right);
		double val2 = _db;
		_db = val1 + val2;
	}

	@Override
	public void visit(BinaryMinusNode n)
	{
		this.visit(n._left);
		double val1 = _db;
		this.visit(n._right);
		double val2 = _db;
		_db = val1 - val2;
	}

	@Override
	public void visit(BinaryTimesNode n)
	{
		this.visit(n._left);
		double val1 = _db;
		this.visit(n._right);
		double val2 = _db;
		_db = val1 * val2;
	}

	@Override
	public void visit(BinaryDivideNode n)
	{
		this.visit(n._left);
		double val1 = _db;
		this.visit(n._right);
		double val2 = _db;
		_db = val1 / val2;
	}

	@Override
	public void visit(BinaryPowerNode n)
	{
		this.visit(n._left);
		double val1 = _db;
		this.visit(n._right);
		double val2 = _db;
		_db = java.lang.Math.pow(val1, val2);
	}

	@Override
	public void visit(LiteralNode n)
	{
		System.err.println("LiteralNode::Unparse");
	}

	@Override
	public void visit(IntLitNode n)
	{
		_db = n.get();
	}

	@Override
	public void visit(RealLitNode n)
	{
		_db = n.get();
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