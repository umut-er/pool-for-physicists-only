package vectormath;

import java.util.Arrays;

// TODO: Testing
// TODO: Better error messages / Custom error types (?)
// TODO: Represent a matrix using 1d data array (?) (Big change) (Somewhat sure this only affects Matrix, power of abstraction. - Umut Erşahince).

/**
 * A simple matrix class (mainly) for vectors and related mathematical equations.
 * @author Bilkent 2023 Spring CS102 Section 2 Group 5
 */
public class Matrix{
    private double[] data;
    private int rows;
    private int columns;

    public Matrix(int rows, int columns, double[] data1d) throws ArrayIndexOutOfBoundsException{
        if(rows * columns != data1d.length) throw new ArrayIndexOutOfBoundsException("Data length should be equal to row(= " + rows + ") * column(= " + columns + ")");
        this.rows = rows;
        this.columns = columns;
        this.data = data1d;
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
    public static Matrix multiply(double c, Matrix matrix){
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
     * @throws ArrayIndexOutOfBoundsException When matrix1.columns != matrix2.rows.
     */
    public static Matrix multiply(Matrix matrix1, Matrix matrix2) throws ArrayIndexOutOfBoundsException{
        if(matrix1.getColumnCount() != matrix2.getRowCount()){
            throw new ArrayIndexOutOfBoundsException("Matrix 1 column count must be equal to matrix 2 row count for matrix multiplication");
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
     * @throws ArrayIndexOutOfBoundsException When matrix1.rows != matrix2.rows or matrix1.columns != matrix2.columns.
     */
    public static Matrix add(Matrix matrix1, Matrix matrix2) throws ArrayIndexOutOfBoundsException{
        if(matrix1.getRowCount() != matrix2.getRowCount() || matrix1.getColumnCount() != matrix2.getColumnCount()){
            throw new ArrayIndexOutOfBoundsException("Matrix shapes should be the same for addition.");
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
     * @throws ArrayIndexOutOfBoundsException When matrix1.rows != matrix2.rows or matrix1.columns != matrix2.columns.
     */
    public static Matrix subtract(Matrix matrix1, Matrix matrix2) throws ArrayIndexOutOfBoundsException{
        if(matrix1.getRowCount() != matrix2.getRowCount() || matrix1.getColumnCount() != matrix2.getColumnCount()){
            throw new ArrayIndexOutOfBoundsException("Matrix shapes should be the same for subtraction.");
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
     * An implementation of the dot product. This method returns a new Matrix.
     * @param matrix1 First matrix, order doesn't matter.
     * @param matrix2 Second matrix, order doesn't matter.
     * @return An integer, result of the operation.
     * @throws ArrayIndexOutOfBoundsException When matrix1.rows != matrix2.rows or matrix1.columns != matrix2.columns.
     */
    public static int dotProduct(Matrix matrix1, Matrix matrix2) throws ArrayIndexOutOfBoundsException{
        if(matrix1.getRowCount() != matrix2.getRowCount() || matrix1.getColumnCount() != matrix2.getColumnCount()){
            throw new ArrayIndexOutOfBoundsException("Matrix shapes should be the same for dot product.");
        }
        int sum = 0;
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
     * @throws ArrayIndexOutOfBoundsException When this.rows != matrix.rows or this.columns != matrix.columns.
     */
    public void inPlaceAdd(Matrix matrix) throws ArrayIndexOutOfBoundsException{
        if(matrix.getRowCount() != rows || matrix.getColumnCount() != columns){
            throw new ArrayIndexOutOfBoundsException("Matrix shapes should be the same for addition.");
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
     * @throws ArrayIndexOutOfBoundsException When this.rows != matrix.rows or this.columns != matrix.columns.
     */
    public void inPlaceSubtract(Matrix matrix) throws ArrayIndexOutOfBoundsException{
        if(matrix.getRowCount() != rows || matrix.getColumnCount() != columns){
            throw new ArrayIndexOutOfBoundsException("Matrix shapes should be the same for addition.");
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

    public static void main(String[] args) {
        double[][] vector3d = new double[1][3];
        vector3d[0][0] = 0; vector3d[0][1] = 0; vector3d[0][2] = 1;
        double[] data = {9, 5, 2, 1, 8, 5, 3, 1, 6};
        double[] data2 = {3, 2, 1, 4, 5, 3};
        Matrix matrix1 = new Matrix(3, 3, data);
        Matrix matrix2 = new Matrix(3, 2, data2);
        System.out.println(Matrix.multiply(matrix1, matrix2));
        System.out.println(matrix1);
    }
}
