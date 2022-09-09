/**
 * Abstract Syntax Tree (AST) class hierarchy for representing expressions trees
 * according to the following language:
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
 *      5 3 +
 *      5.0 3.0 *
 *      1.0 A 2.0 S 3.0 A 4.0 S -+5.0 + - * / A ----12.3 + 4 **
 * 
 * @author Michael Peeler and Deion Rivers
 */

package ast2;

import utils.Constants;

//
// Top-Level Abstract AST node class
//
public abstract class ASTnode
{
	ASTnode() { }
	
	boolean isNull(){ return false; }
	
	public abstract void unparse(StringBuilder sb);
}

//
// A support class to return from methods in case of failure.
//
class NullNode extends ASTnode
{
	NullNode() { super(); }

	boolean isNull(){ return true; }
	
	public void unparse(StringBuilder sb) { sb.append("null"); }
}

//
//
// LITERAL NODES
//
//
abstract class LiteralNode extends ASTnode
{
	protected double _value;
	
	public double get() { return _value; }
	
	LiteralNode(double value)
	{
		super();
        _value = value;
    }
	
	public abstract void unparse(StringBuilder sb);
}

class IntLitNode extends LiteralNode
{
	IntLitNode(int value)
    {
        super(value);
    }

	@Override
	public void unparse(StringBuilder sb) {
		// Casts to integer to remove decimal places
		int val = (int) _value;
		sb.append(Integer.toString(val));
	}

}

class RealLitNode extends LiteralNode
{
	RealLitNode(double value)
    {
        super(value);
    }

	@Override
	public void unparse(StringBuilder sb) {

		sb.append(Double.toString(_value));
	}

}

//                //
//                //
// Operations     //
//                //
//                //
abstract class OperationNode extends ASTnode
{
	OperationNode() { super(); }
}

//
//
// Binary Operation Nodes
//
//
abstract class BinaryExprNode extends OperationNode
{
	ASTnode _left;
	ASTnode _right;

	BinaryExprNode()
    {
		super();
		_left = null;
		_right = null;
    }

	BinaryExprNode(ASTnode left, ASTnode right)
    {
		super();
		_left = left;
		_right = right;
    }
}

class BinaryPlusNode extends BinaryExprNode
{
	BinaryPlusNode(ASTnode left, ASTnode right) { super(left, right); }

	@Override
	public void unparse(StringBuilder sb) {
		// Since this is a postfix operator, unparse the children
		// before unparsing the parent.
		_left.unparse(sb);
		sb.append(Constants.DELIMITER);
		_right.unparse(sb);
		sb.append(Constants.DELIMITER);
		sb.append(Constants.PLUS);
	}
	
	
	
}

class BinaryMinusNode extends BinaryExprNode
{
	BinaryMinusNode(ASTnode left, ASTnode right) { super(left, right); }

	@Override
	public void unparse(StringBuilder sb) {
		// Since this is a postfix operator, unparse the children
		// before unparsing the parent.
		_left.unparse(sb);
		sb.append(Constants.DELIMITER);
		_right.unparse(sb);		
		sb.append(Constants.DELIMITER);
		sb.append(Constants.MINUS);
	}
	
}

class BinaryTimesNode extends BinaryExprNode
{
	BinaryTimesNode(ASTnode left, ASTnode right) { super(left, right); }

	@Override
	public void unparse(StringBuilder sb) {
		// Since this is a postfix operator, unparse the children
		// before unparsing the parent.
		_left.unparse(sb);
		sb.append(Constants.DELIMITER);
		_right.unparse(sb);
		sb.append(Constants.DELIMITER);
		sb.append(Constants.TIMES);
	}
	
}

class BinaryDivideNode extends BinaryExprNode
{
	BinaryDivideNode(ASTnode left, ASTnode right) { super(left, right); }

	@Override
	public void unparse(StringBuilder sb) {
		// Since this is a postfix operator, unparse the children
		// before unparsing the parent.
		_left.unparse(sb);
		sb.append(Constants.DELIMITER);
		_right.unparse(sb);
		sb.append(Constants.DELIMITER);
		sb.append(Constants.DIVIDE);
	}
	
}

class BinaryPowerNode extends BinaryExprNode
{
	BinaryPowerNode(ASTnode left, ASTnode right) { super(left, right); }

	@Override
	public void unparse(StringBuilder sb) {
		// Since this is a postfix operator, unparse the children
		// before unparsing the parent.
		_left.unparse(sb);
		sb.append(Constants.DELIMITER);
		_right.unparse(sb);
		sb.append(Constants.DELIMITER);
		sb.append(Constants.EXPONENTIAL);
	}

}

//
//
// Unary Operation Nodes
//
//
abstract class UnaryExprNode extends OperationNode
{
	ASTnode _expr;

	UnaryExprNode()
    {
		super();
		_expr = null;
    }

	UnaryExprNode(ASTnode expr)
    {
		super();
		_expr = expr;
    }
}

class UnaryPlusNode extends UnaryExprNode
{
	UnaryPlusNode(ASTnode expr) { super(expr); }

	@Override
	public void unparse(StringBuilder sb) {
		// Since this is a prefix operator, unparse the parent before
		// unparsing the child; also, since there are no delimeters, no delimeter
		// is added.
		sb.append(Constants.PLUS);
		_expr.unparse(sb);
	}
	
}

class UnaryMinusNode extends UnaryExprNode
{
	UnaryMinusNode(ASTnode expr) { super(expr); }

	@Override
	public void unparse(StringBuilder sb) {
		// Since this is a prefix operator, unparse the parent before
		// unparsing the child; also, since there are no delimeters, no delimeter
		// is added.
		sb.append(Constants.MINUS);
		_expr.unparse(sb);
	}
	
}

class UnaryAbsoluteValueNode extends UnaryExprNode
{
	UnaryAbsoluteValueNode(ASTnode expr) { super(expr); }

	@Override
	public void unparse(StringBuilder sb) {
		// Since this is a postfix operator, unparse the child before
		// unparsing the parent.
		_expr.unparse(sb);
		sb.append(Constants.DELIMITER);
		sb.append(Constants.ABSOLUTE_VALUE);
	}
	
}

class UnarySquareRootNode extends UnaryExprNode
{
	UnarySquareRootNode(ASTnode expr) { super(expr); }

	@Override
	public void unparse(StringBuilder sb) {
		// Since this is a postfix operator, unparse the child before
		// unparsing the parent.
		_expr.unparse(sb);
		sb.append(Constants.DELIMITER);
		sb.append(Constants.SQUARE_ROOT);

	}
	
}