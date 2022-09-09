/**
* A class that provides a static function to generate an abstract
* syntax tree for the string provided to it. 
* 	  
*    Unary Postfix Operators
*      A:  UnaryAbsoluteValueNode
*      S:  UnarySquareRootNode
*
*    Binary Operators
*      +:  BinaryPlusNode
*      -:  BinaryMinusNode
*      *:  BinaryTimesNode
*      /:  BinaryDivideNode
*      **: BinaryPowerNode
*
*    Unary Prefix
*      +:  UnaryPlusNode
*      -:  UNaryMinusNode
* 
* Construction strings should be presented in post-fix order with spaces used as 
* delimiters between arguments or numbers. 
* 
* Example input:
* 	1.0 A 2.0 S 3.0 A 4.0 S -+5.0 + - * / A ----12.3 + 4 **
* 	Equivalent to: (|(|1.0| / (sqrt(2.0) * (|3.0| - (sqrt(4.0) + -5))))| + -12.3) ** 4
* 
* 	1.0 2.0 +
* 	Equivalent to: 1.0 + 2.0
*
* <p>Bugs: Only legal inputs work correctly.
*
* @author Michael Peeler and Deion Rivers
* @date February 23, 2022
*/
package ast2;

import java.util.Stack;
import java.util.StringTokenizer;

import utils.Constants;

public class ASTbuilder
{
	/**
	 * Expression string ---> into an expression tree
	 *   * Stack-based construction
	 * 
	 * @param str -- a string corresponding to an expression in our language (mostly postfix)
                     All tokens must have a space between them: e.g., 4 12 +   evaluates to 16
					 except prefix expressions: "+-+4" equates to "-4".
	 * @return an Expression Tree in the form of an ASTnode
	 */
	public static ASTnode build(String str)
	{
		// Split the string into individual tokens
		StringTokenizer tokenizer = new StringTokenizer(str, Constants.DELIMITER);

		// Convert expression via a stack technique
		Stack<ASTnode> stack = new Stack<ASTnode>();

		// Iterate through the tokens
		while (tokenizer.hasMoreTokens())
		{
			String token = tokenizer.nextToken();

			// Generates the various nodes and pushes them to the stack.
			if (isBinaryPostfix(token)) {
				stack.push(genBinaryPostfix(token, stack.pop(), stack.pop()));
			} else if (isUnaryPostfix(token)) {
				stack.push(genUnaryPostfix(token, stack.pop()));
			} else if (isUnaryPrefix(token)) {
				stack.push(genUnaryPrefix(token));
			} else if (isLiteral(token)) {
				stack.push(genLiteralNode(token));
			}

		}

		return stack.peek();
	}
	
	/**
	 * Creates a binary postfix operator node, operating on the l and r nodes. The l
	 * node is the left node of the operator, and the r node is the right node; to work
	 * with the stack-implementation of the generator function, the right node should
	 * be the first entered as an input.
	 * @param token String indicating the operation performed.
	 * @param r Right input to operation.
	 * @param l Left input to operation.
	 * @return
	 */
	private static ASTnode genBinaryPostfix(String token, ASTnode r, ASTnode l) {
		
		if (token.equals(Constants.PLUS)) {
			return new BinaryPlusNode(l, r);
			
		} else if (token.equals(Constants.MINUS)) {

			return new BinaryMinusNode(l, r);
			
		} else if (token.equals(Constants.TIMES)) {

			return new BinaryTimesNode(l, r);
			
		} else if (token.equals(Constants.DIVIDE)) {
			
			return new BinaryDivideNode(l, r);
			
		} else if (token.equals(Constants.EXPONENTIAL)) {
			
			return new BinaryPowerNode(l, r);
		
		} 
		
		return null;			
	}
	
	/**
	 * Creates a unary postfix node based on the string provided and the top 
	 * @param token
	 * @param c Previous node, which the unary operator will be applied to.
	 * @return Returns the generated unary postfix node.
	 */
	private static ASTnode genUnaryPostfix(String token, ASTnode c) {
		
		if (token.equals(Constants.ABSOLUTE_VALUE)) {
			return new UnaryAbsoluteValueNode(c);
			
		} else if (token.equals(Constants.SQUARE_ROOT)) {
			return new UnarySquareRootNode(c);
		}
		return null;
	}
	
	/**
	 * Generates and returns a literal node based on the String that is
	 * provided. 
	 * @param token String being converted into a literal node.
	 * @return Returns the literal node generated based on the string.
	 */
	private static ASTnode genLiteralNode(String token) {
		
		try {
			int i = Integer.parseInt(token);
			return new IntLitNode(i);
			
		} catch (Exception NumberFormatException) {}
		
		try {
			double d = Double.parseDouble(token);
			return new RealLitNode(d);
			
		} catch (Exception NumberFormatException) {}
		
		return null;
	}
	
	/**
	 * Generates and returns a unary prefix node based on the String
	 * that is provided. Nodes further in the string will also be converted and
	 * nested inside the node returned, with a literal node on the leaf.
	 * @param token String that is being converted 
	 * @return ASTnode returned at the end of the function call.
	 */
	private static ASTnode genUnaryPrefix(String token) {
		if (token.charAt(0) == Constants.PLUS.charAt(0)) {
			return new UnaryPlusNode(genUnaryPrefix(token.substring(1)));
		} else if (token.charAt(0) == Constants.MINUS.charAt(0)) {
			return new UnaryMinusNode(genUnaryPrefix(token.substring(1)));			
		} else {
			return genLiteralNode(token);
		}
	}
	
	/**
	 * Checks if the current string begins with a unary prefix operator.
	 * Differentiated from binary postfix operators by having a length greater
	 * than one. 
	 * @param token String being checked for initial unary prefix operators.
	 * @return Boolean indicating if the string begins with a unary prefix
	 * operator.
	 */
	private static boolean isUnaryPrefix(String token) {
		if (token.length() > 1 && (token.charAt(0) == (Constants.PLUS.charAt(0)) ||
				token.charAt(0) == Constants.MINUS.charAt(0) )) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the current string is a binary postfix operator.
	 * @param token String that is being checked
	 * @return Boolean indicating if the sting is a binary postfix operator.
	 */
	private static boolean isBinaryPostfix(String token) {
		if (token.equals(Constants.PLUS) || token.equals(Constants.TIMES)||
				token.equals(Constants.MINUS) || token.equals(Constants.DIVIDE) ||
				token.equals(Constants.EXPONENTIAL)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the current string is a unary postfix operator
	 * @param token String that is being checked
	 * @return Boolean indicating if the sting is unary postfix operator.
	 */	
	private static boolean isUnaryPostfix(String token) {
		if (token.equals(Constants.SQUARE_ROOT) || token.equals(Constants.ABSOLUTE_VALUE)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the current string is a literal
	 * @param token String that is being checked
	 * @return Boolean indicating if the sting is a literal.
	 */
	private static boolean isLiteral(String token) {
		if (token.charAt(0) == Constants.PLUS.charAt(0) 
				|| token.charAt(0) == Constants.MINUS.charAt(0)) return false;
		try {
			Integer.parseInt(token);
			return true;
		} catch(Exception NumberFormatException) {}
		try {
			Double.parseDouble(token);
			return true;
		} catch(Exception NumberFormatException) { return false; }
	}
	
	public static void main(String[] args) {
		build("1 5 -+--++-6 * +");
	}
}
