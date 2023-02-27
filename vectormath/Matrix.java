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
    protected double[][] data;
    protected int rows;
    protected int columns;

    public Matrix(double[][] data){
        this.data = data.clone();
        this.rows = data.length;
        this.columns = data[0].length;
    }

    public Matrix(int rows, int columns, double[] data1d) throws ArrayIndexOutOfBoundsException{
        if(rows * columns != data1d.length) throw new ArrayIndexOutOfBoundsException("Data length should be equal to row(= " + rows + ") * column(= " + columns + ")");
        this.rows = rows;
        this.columns = columns;
        this.data = resize(rows, columns, data1d);
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
        return data[row][column];
    }

    public void setItem(int row, int column, double num){
        data[row][column] = num;
    }

    /**
     * A method to resize (reshape) a given 1D array into a 2D array.
     * @param rows How many rows the resized array should have
     * @param columns How many columns the resized array should have
     * @param data The 1D double array to resize
     * @return A 2d array resized according to the parameters.
     * @throws ArrayIndexOutOfBoundsException When the resize is not appropriate for given array, i.e. rows * columns != data.length
     */
    private static double[][] resize(int rows, int columns, double[] data) throws ArrayIndexOutOfBoundsException{
        if(data.length != rows * columns) throw new ArrayIndexOutOfBoundsException("Resize is not appropriate.");
        double[][] res = new double[rows][columns];
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                res[i][j] = data[i * columns + j];
            }
        }
        return res;
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
        double[][] new_data = new double[matrix1.getRowCount()][matrix2.getColumnCount()];
        for(int i = 0; i < matrix1.getRowCount(); i++){
            for(int k = 0; k < matrix1.getColumnCount(); k++){
                for(int j = 0; j < matrix2.getColumnCount(); j++){
                    new_data[i][j] += matrix1.getItem(i, k) * matrix2.getItem(k, j);
                }
            }
        }
        return new Matrix(new_data);
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
                matrix1.setItem(i, j, matrix1.getItem(i, j) + matrix2.getItem(i, j));
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
                matrix1.setItem(i, j, matrix1.getItem(i, j) - matrix2.getItem(i, j));
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
                data[i][j] *= c;
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
                data[i][j] += matrix.getItem(i, j);
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
                data[i][j] -= matrix.getItem(i, j);
            }
        }
    }

    @Override
    public String toString(){
        String s = "";
        for(int i = 0; i < rows; i++){
            s += Arrays.toString(data[i]) + "\n";
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
