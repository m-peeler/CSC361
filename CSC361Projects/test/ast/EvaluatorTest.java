/**
 * Unit tests for evaluating an expression from an expression tree of ASTnode objects.
 * Tests include:
 *    Unary Postfix Operators
 *      A, S
 *    Binary Operators
 *      +, -, *, / **
 *    Unary Prefix
 *      +, -
 *    A set of complex tests using all possible operators.
 * 
 * @author M. Peeler
 * @adaptedFrom C. Alvin
 * 2/28/2022
 */
package ast;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class EvaluatorTest
{
	// Switch to false to turn off output
	private static final boolean DEBUG = true;

	private final ArrayList<String> _in = new ArrayList<String>();
	private final ArrayList<Double> _out = new ArrayList<Double>();

	private void addTest(String in, double out)
	{
		_in.add(in);
		_out.add(out);
	}
	
	/**
	 * The main execution method:
	 * 
	 *    Given a set of <input, output> pairs stored within the lists <_in, _out>,
	 *    execute the creation and unparsing of the expression.
     *
	 *    A test will pass if the input / output strings are equal().
	 */
	private void run()
	{
		//
		// Execute tree building and unparsing for all input Strings
		// Compare to output strings (assert)
		//
		for (int index = 0; index < _in.size(); index++)
		{
			if (DEBUG) System.out.println(index + " In:  |" + _in.get(index) + "|");

			// BUILD
			ASTnode tree = ASTbuilder.build(_in.get(index));

			// UNPARSE
			Evaluator eval = new Evaluator(tree);
			Double val = eval.unparse();

			// COMPARE
			if (DEBUG) System.out.println("Out: |" + val + "|");
			
			assertEquals(_out.get(index), val);
		}
	}
	
	private void constructBinaryInOutPairs()
	{
		// Start fresh
		_in.clear();
		_out.clear();

		//
		// Basic binary operations (integers)
		//
		
		String in = "5 3 +";
		double out = 8;
		addTest(in, out);
				
		in = "5 3 -";
		out = 2;
		addTest(in, out);

		in = "5 3 *";
		out = 15;
		addTest(in, out);		
		
		in = "5 3 /";
		out = (double) 5 / 3;
		addTest(in, out);
		
		in = "5 3 **";
		out = 125;
		addTest(in, out);

		//
		// Basic binary operations (reals)
		//
		in = "5.0 3.0 +";
		out = 8.0;
		addTest(in, out);
		
		in = "5.0 3.0 -";
		out = 2.0;
		addTest(in, out);

		in = "5.0 3.0 *";
		out = 15;
		addTest(in, out);		
		
		in = "5.0 3.0 /";
		out = 5.0 / 3.0;
		addTest(in, out);
		
		in = "5.0 3.0 **";
		out = 125.0;
		addTest(in, out);
	}
	
	@Test
	void binary_test()
	{
		if (DEBUG) System.out.println("Binary Tests");
		constructBinaryInOutPairs();
		run();
	}

	private void constructUnaryInOutPairs()
	{
		// Start fresh
		_in.clear();
		_out.clear();

		//
		// Basic unary operations (integers)
		//
		String in = "+3";
		addTest(in, 3);
		
		in = "-3";
		addTest(in, -3);

		in = "-5 A";
		addTest(in, 5);
		
		in = "-+++--++-+16 A";
		addTest(in, 16);

		//
		// Basic unary operations (reals)
		//
		in = "+3.0";
		addTest(in, 3.0);
		
		in = "-3.0";
		addTest(in, -3.0);

		in = "5.0 A";
		addTest(in, 5.0);
		
		in = "16.0 S";
		addTest(in, 4.0);
		
		//
		// Combined unary operations (not in expressions)
		//
		in = "+-3.0";
		addTest(in, -3.0);
		
		in = "-+3.0";
		addTest(in, -3.0);

		in = "++3.0";
		addTest(in, 3.0);

		in = "--3.0";
		addTest(in, 3.0);

		in = "+-++-++-++-++-++-+2";
		addTest(in, 2);
		
		in = "+-+5.0 A";
		addTest(in, 5);
		
		in = "--16.0 S";
		addTest(in, 4.0);
	}
	
	@Test
	void unary_test()
	{
		if (DEBUG) System.out.println("Unary Tests");
		constructUnaryInOutPairs();
		run();
	}
	
	private void constructComplexInOutPairs()
	{
		// Start fresh
		_in.clear();
		_out.clear();

		String in = "+3 5 -";
		addTest(in, -2);
		
		in = "3 2 1 - *";
		addTest(in, 3);

		in = "3 A 2 S +";
		addTest(in, 3 + java.lang.Math.sqrt(2));
		
		in = "1 2 3 4 5 + - * /";
		double out = (double) 1 / (2 * ( 3 - (4 + 5)));
		addTest(in, out);
		
		in = "1 2 3 4 + - *";
		out = (double) 1 * (2 - (3 + 4));
		addTest(in, out);
		
		in = "+3 5 - A 3 ** S";
		out = java.lang.Math.sqrt(java.lang.Math.pow(2, 3));
		addTest(in, out);
		
		in = "+-+-----++++3 A A A A A A A S S S S S S";
		out = java.lang.Math.sqrt(java.lang.Math.sqrt(java.lang.Math.sqrt(java.lang.Math.sqrt(
				java.lang.Math.sqrt(java.lang.Math.sqrt(3))))));
		addTest(in, out);

		in = "1.23 123 3 -4 ** ** **";
		out = java.lang.Math.pow(1.23, java.lang.Math.pow(123, java.lang.Math.pow(3, -4)));
		addTest(in, out);
		
		in = "+-2.0 -+4 ** S";
		addTest(in, java.lang.Math.sqrt(java.lang.Math.pow(-2.0, -4)));
				
	}
	
	@Test
	void complex_tests()
	{
		if (DEBUG) System.out.println("Complex Tests");
		constructComplexInOutPairs();
		run();
	}
}
