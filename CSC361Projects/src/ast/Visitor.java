package ast;

//
// Generic visit class
//
public abstract class Visitor
{
    void visit(ASTnode n) { n.accept(this); }
	
	//
	// Literals
	//
	abstract void visit(LiteralNode n);
	
	abstract void visit(IntLitNode n);
	abstract void visit(RealLitNode n);

	//
	// Operations
	//
	abstract void visit(OperationNode n);
	
	//
	// Unary Operations
	//
	abstract void visit(UnaryExprNode n);
	
	abstract void visit(UnaryPlusNode n);
	abstract void visit(UnaryMinusNode n);
	abstract void visit(UnaryAbsoluteValueNode n);
	abstract void visit(UnarySquareRootNode n);
	
	//
	// Binary Operations
	//
	abstract void visit(BinaryExprNode n);

	abstract void visit(BinaryPlusNode n);
	abstract void visit(BinaryMinusNode n);
	abstract void visit(BinaryTimesNode n);
	abstract void visit(BinaryDivideNode n);
	abstract void visit(BinaryPowerNode n);
}  