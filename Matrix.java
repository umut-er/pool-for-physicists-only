import java.util.Arrays;

// TODO: Docstrings

/**
 * A simple matrix class (mainly) for vectors
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

    public double getItem(int i, int j){
        return data[i][j];
    }

    public void setItem(int i, int j, double num){
        data[i][j] = num;
    }

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

    public static Matrix multiply(double c, Matrix matrix){
        Matrix res = matrix;
        for(int i = 0; i < res.rows; i++){
            for(int j = 0; j < res.columns; j++){
                res.setItem(i, j, res.getItem(i, j) * c);
            }
        }
        return res;
    }

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

    public void inPlaceMultiply(double c){
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                data[i][j] *= c;
            }
        }
    }

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

    public String toString(){
        String s = "";
        for(int i = 0; i < rows; i++){
            s += Arrays.toString(data[i]) + "\n";
        }
        return s;
    }

    // public static void main(String[] args) {
    //     double[][] vector3d = new double[1][3];
    //     vector3d[0][0] = 0; vector3d[0][1] = 0; vector3d[0][2] = 1;
    //     double[] data = {9, 5, 2, 1, 8, 5, 3, 1, 6};
    //     double[] data2 = {3, 2, 1, 4, 5, 3};
    //     Matrix matrix1 = new Matrix(3, 3, data);
    //     Matrix matrix2 = new Matrix(3, 2, data2);
    //     System.out.println(Matrix.multiply(matrix1, matrix2));
    //     System.out.println(matrix1);
    // }
}
