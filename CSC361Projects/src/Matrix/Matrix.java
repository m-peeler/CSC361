package Matrix;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Matrix {
	
	private Double[][] _data;
	private int _rows;
	private int _columns;
	
	/**
	 * Constructs a matrix of dimensions rows by columns.
	 * @param rows Number of rows that will be in the matrix.
	 * @param columns Number of columns that will be in the matrix.
	 */
	public Matrix(int rows, int columns) {
		_rows = rows;
		_columns = columns;
		_data = new Double[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				_data[i][j] = 0.0;
			}
		}
	}	
	
	/**
	 * Constructs a matrix of dimensions rows by columns. 
	 * The first rows times columns values inside items will fill
	 * in the matrix, starting from index [0][0] increasing columns
	 * position until a row is filled, then beginning from the leftmost value
	 * in the increasing row values until the matrix is filled.
	 * If the list is not full, than remaining positions will be filled
	 * by the value 0.0.
	 * 
	 * @param items List that values will be added from.
	 * @param rows Number of rows that will be in the matrix
	 * @param columns Number of columns that will be in the matrix
	 */
	public Matrix(List<Double> items, int rows, int columns) {
		this(rows, columns);
		for (int i = 0; i < items.size() && i < (rows * columns); i++) {
			_data[i / columns][i % columns] = items.get(i);
		}
	}
	
	/**
	 * Returns a string of the matrix, formatted to look like a printout 
	 * of the matrix.
	 */
	public String toString() {
		StringBuilder rtrn = new StringBuilder();
		for (int i = 0; i < _rows; i++) {
			for (int j = 0; j < _columns; j++) {
				rtrn.append(get(i, j));
				rtrn.append(" ");
			}
			rtrn.append("\n");
		}
		return rtrn.toString();
	}
	
	/**
	 * Returns the value in the matrix in rowIndex, colIndex.
	 * @param rowIndex Value of row that element will be returned from.
	 * @param colIndex Value of column that element will be returned from
	 * @return Element in specified position.
	 */
	public Double get(int rowIndex, int colIndex) {
		return _data[rowIndex][colIndex];
	}
	
	/**
	 * Returns the number of rows in the matrix
	 * @return Number of rows
	 */
	public int getNumRows() {
		return _rows;
	}
	
	/**
	 * Returns the number of columns in the matrix
	 * @return Number of columns
	 */
	public int getNumCols() {
		return _columns;
	}
		
	/**
	 * Adds the instance matrix with the argument matrix if they are of
	 * the same dimension;  returns the new summed matrix.
	 * @param that Argument matrix that will be added to instance matrix.
	 * @return New matrix that is the sum of the other two.
	 */
	public Matrix plus(Matrix that) {
		if (that.getNumCols() != getNumCols() || that.getNumRows() != getNumRows()) {
			throw new RuntimeException();		
		}
		Matrix newMat = new Matrix(_rows, _columns);
		for (int i = 0; i < _rows; i++) {
			for (int j = 0; j < _columns; j++) {
				newMat._data[i][j] = (that.get(i, j) + get(i, j));
			}
		}
		return newMat;
	}
	
	/**
	 * Performs matrix multiplication between instance matrix 
	 * and parameter matrix if instance matrix column equals 
	 * parameter matrix rows.
	 * 	   I====j====I		  I====k====I	     I=========================k==========================I
	 * |||[a00,...,a0j]   |||[b00,...,b0k]   |||[a00*b00 + ... + a0j*bj0, ... , a00*b0k + ... + a0j*bjk]
	 * |i|[ : , : , : ] x |j|[ : , : , : ] = |i|[           :           ,  :  ,            :           ]
	 * |||[ai0,...,aij]   |||[bj0,...,bjk]   |||[ai0*b00 + ... + aij*bj0, ... , ai0*b0k + ... + aij*bjk]
	 * 
	 * @param that Parameter matrix that will be multiplied by 
	 * instance matrix
	 * @return Multiplied matrix.
	 */
	public Matrix times(Matrix that) {
		if (getNumCols() != that.getNumRows()) {
			throw new RuntimeException();		
		}
		Matrix rtrn = new Matrix(getNumRows(), that.getNumCols());
		for (int i = 0; i < getNumRows(); i++) {
			for (int k = 0; k < that.getNumCols(); k++) {
				for (int j = 0; j < getNumCols(); j++) {
					rtrn._data[i][k] += (this.get(i, j) * that.get(j, k));					
				}
			}
		}
		return rtrn;
	}
	
	/**
	 * Creates a matrix of rows by columns with random values from [0, 1)
	 * @param rows Rows in the new matrix
	 * @param columns Columns in the new matrix
	 * @return Returns the new matrix.
	 */
	public static Matrix create(int rows, int columns) {
		Matrix rtrn = new Matrix(rows, columns);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				rtrn._data[i][j] = (Math.random());
			}
		}
		return rtrn;
	}
	
	
}