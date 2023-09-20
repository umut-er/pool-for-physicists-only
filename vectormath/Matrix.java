package vectormath;

import java.io.Serializable;

/**
 * A simple matrix class (mainly) for vectors and related mathematical equations.
 * @author Bilkent 2023 Spring CS102 Section 2 Group 5
 */
public class Matrix implements Serializable{
    private double[] data;
    private int rows;
    private int columns;

    public Matrix(int rows, int columns, double... data) throws IllegalArgumentException{
        if(rows * columns != data.length) throw new IllegalArgumentException("Data length should be equal to row(= " + rows + ") * column(= " + columns + ")");
        this.rows = rows;
        this.columns = columns;
        this.data = data;
    }

    public Matrix(Matrix matrix){
        this.rows = matrix.rows;
        this.columns = matrix.columns;
        this.data = matrix.data.clone();
    }

    public int getRowCount(){
        return this.rows;
    }

    public int getColumnCount(){
        return this.columns;
    }

    public double getItem(int row, int column){
        return this.data[row * this.columns + column];
    }

    public void setItem(int row, int column, double num){
        this.data[row * this.columns + column] = num;
    }

    /**
     * An implementation of constant * Matrix, i.e. matrix[i, j] *= c. This method returns a new Matrix.
     * @param c The constant in the multiplication.
     * @param matrix The matrix in the multiplication.
     * @return A new Matrix, the result of the operation.
     */
    public static Matrix multiply(double c, final Matrix matrix){
        Matrix res = matrix;
        for(int i = 0; i < res.rows; i++){
            for(int j = 0; j < res.columns; j++){
                res.setItem(i, j, res.getItem(i, j) * c);
            }
        }
        return res;
    }

    /**
     * An implementation of matrix * matrix by means of matrix multiplication. This method returns a new Matrix.
     * @param matrix1 First matrix, order matters.
     * @param matrix2 Second matrix, order matters.
     * @return A new Matrix of shape [matrix1.rows, matrix2.columns], result of the operation
     * @throws IllegalArgumentException When matrix1.columns != matrix2.rows.
     */
    public static Matrix multiply(final Matrix matrix1, final Matrix matrix2) throws IllegalArgumentException{
        if(matrix1.getColumnCount() != matrix2.getRowCount()){
            throw new IllegalArgumentException("Matrix 1 column count must be equal to matrix 2 row count for matrix multiplication");
        }
        int new_row = matrix1.getRowCount();
        int new_column = matrix2.getColumnCount();
        double[] new_data = new double[new_row * new_column]; 
        for(int i = 0; i < new_row; i++){
            for(int j = 0; j < new_column; j++){
                    for(int k = 0; k < matrix1.getColumnCount(); k++){
                    new_data[i * new_column + j] += matrix1.getItem(i, k) * matrix2.getItem(k, j);
                }
            }
        }
        return new Matrix(new_row, new_column ,new_data);
    }

    /**
     * An implementation of matrix addition. This method returns a new Matrix.
     * @param matrix1 First matrix, order doesn't matter.
     * @param matrix2 Second matrix, order doesn't matter.
     * @return A new Matrix, result of the operation.
     * @throws IllegalArgumentException When matrix1.rows != matrix2.rows or matrix1.columns != matrix2.columns.
     */
    public static Matrix add(final Matrix matrix1, final Matrix matrix2) throws IllegalArgumentException{
        if(matrix1.getRowCount() != matrix2.getRowCount() || matrix1.getColumnCount() != matrix2.getColumnCount()){
            throw new IllegalArgumentException("Matrix shapes should be the same for addition.");
        }
        Matrix res = matrix1;
        for(int i = 0; i < matrix1.getRowCount(); i++){
            for(int j = 0; j < matrix1.getColumnCount(); j++){
                res.setItem(i, j, matrix1.getItem(i, j) + matrix2.getItem(i, j));
            }
        }
        return res;
    }

    /**
     * An implementation of matrix subtraction (matrix1 - matrix2). This method returns a new Matrix.
     * @param matrix1 First matrix, order matters.
     * @param matrix2 Second matrix, order matters.
     * @return A new Matrix, result of the operation.
     * @throws IllegalArgumentException When matrix1.rows != matrix2.rows or matrix1.columns != matrix2.columns.
     */
    public static Matrix subtract(final Matrix matrix1, final Matrix matrix2) throws IllegalArgumentException{
        if(matrix1.getRowCount() != matrix2.getRowCount() || matrix1.getColumnCount() != matrix2.getColumnCount()){
            throw new IllegalArgumentException("Matrix shapes should be the same for subtraction.");
        }
        Matrix res = matrix1;
        for(int i = 0; i < matrix1.getRowCount(); i++){
            for(int j = 0; j < matrix1.getColumnCount(); j++){
                res.setItem(i, j, matrix1.getItem(i, j) - matrix2.getItem(i, j));
            }
        }
        return res;
    }

    /**
     * An implementation of the dot product. This method returns a double.
     * @param matrix1 First matrix, order doesn't matter.
     * @param matrix2 Second matrix, order doesn't matter.
     * @return A double, result of the operation.
     * @throws IllegalArgumentException When matrix1.rows != matrix2.rows or matrix1.columns != matrix2.columns.
     */
    public static double dotProduct(final Matrix matrix1, final Matrix matrix2) throws IllegalArgumentException{
        if(matrix1.getRowCount() != matrix2.getRowCount() || matrix1.getColumnCount() != matrix2.getColumnCount()){
            throw new IllegalArgumentException("Matrix shapes should be the same for dot product.");
        }
        double sum = 0;
        for(int i = 0; i < matrix1.getRowCount(); i++){
            for(int j = 0; j < matrix1.getColumnCount(); j++){
                sum += matrix1.getItem(i, j) * matrix2.getItem(i, j);
            }
        }
        return sum;
    }

    /**
     * Same as the multiply(double c, Matrix matrix) but modifies the matrix on which it is called.
     * @param c The constant in the multiplication.
     */
    public void inPlaceMultiply(double c){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                this.setItem(i, j, this.getItem(i, j)*c);
            }
        }
    }

    /**
     * Same as add(Matrix matrix1, Matrix matrix2) but modifies the matrix on which it is called.
     * @param matrix The matrix to add. This is not modified.
     * @throws IllegalArgumentException When this.rows != matrix.rows or this.columns != matrix.columns.
     */
    public void inPlaceAdd(Matrix matrix) throws IllegalArgumentException{
        if(matrix.getRowCount() != rows || matrix.getColumnCount() != columns){
            throw new IllegalArgumentException("Matrix shapes should be the same for addition.");
        }
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                this.setItem(i, j, this.getItem(i, j) + matrix.getItem(i, j));
            }
        }
    }
    
    /**
     * Same as subtract(Matrix matrix1, Matrix matrix2) but modifies the matrix on which it is called.
     * @param matrix matrix2 in the static method. This is not modified.
     * @throws IllegalArgumentException When this.rows != matrix.rows or this.columns != matrix.columns.
     */
    public void inPlaceSubtract(Matrix matrix) throws IllegalArgumentException{
        if(matrix.getRowCount() != rows || matrix.getColumnCount() != columns){
            throw new IllegalArgumentException("Matrix shapes should be the same for addition.");
        }
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                this.setItem(i, j, this.getItem(i, j) - matrix.getItem(i, j));
            }
        }
    }

    @Override
    public String toString(){
        String s = "";
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.columns; j++){
                s += this.getItem(j, i) + "\t";
            }
            s += "\n";
        }
        return s;
    }

    @Override
    public boolean equals(Object obj){
        Matrix comp = (Matrix)obj;
        if(this.getRowCount() != comp.getRowCount() || this.getColumnCount() != comp.getColumnCount()) return false;
        for(int i = 0; i < getRowCount(); i++){
            for(int j = 0; j < getColumnCount(); j++){
                if(Math.abs(this.getItem(i, j) - comp.getItem(i, j)) > 1e-6) return false;
            }
        }
        return true;
    }
}
