package vectormath;

// TODO: Testing

/**
 * A simple 3D Vector class capable of crucial vector operations such as cross multiplication.
 * Inherits from the Matrix class so it is possible to multiply them.
 * @author Bilkent 2023 Spring CS102 Section 2 Group 5
 */
public class Vector3 extends Matrix{
    
    public Vector3(double... data){
        super(3, 1, data);
    }

    /**
     * This method calls the getItem(int row, int column) method from Matrix class with column = 0.
     * @param index The axis: 0 -> x, 1 -> y, 2 -> z.
     * @return A double, the scalar value of the vector at given axis.
     */
    public double getAxis(int index){
        return getItem(index, 0);
    }

    /**
     * @return A double, length of the vector.
     */
    public double getVectorLength(){
        return Math.sqrt(this.getAxis(0) * this.getAxis(0) + this.getAxis(1) * this.getAxis(1) + this.getAxis(2) * this.getAxis(2));
    }

    /**
     * This method calls the setItem(int row, int column, int num) method from Matrix class.
     * @param index The axis: 0 -> x, 1 -> y, 2 -> z.
     * @param num The value to update this axis to.
     */
    public void setAxis(int index, double num){
        setItem(index, 0, num);
    }

    /**
     * Updates all axes. Calls setAxis thrice.
     * @param x The value to set the x axis to.
     * @param y The value to set the y axis to.
     * @param z The value to set the z axis to.
     */
    public void setAll(double x, double y, double z){
        setAxis(0, x);
        setAxis(1, y);
        setAxis(2, z);
    }

    /**
     * A method to convert a Matrix into a Vector3.
     * @param matrix The matrix to convert, this is not modified.
     * @return A new Vector3 constructed from the data on matrix.
     * @throws IllegalArgumentException When matrix shape is not [1, 3].
     */
    public static Vector3 vectorFromMatrix(Matrix matrix) throws IllegalArgumentException{
        if(matrix.getRowCount() != 3 || matrix.getColumnCount() != 1){
            throw new IllegalArgumentException("Matrix is not compatible with a vector");
        }
        double[] empty_data = new double[3];
        Vector3 res = new Vector3(empty_data);
        res.setAll(matrix.getItem(0, 0), matrix.getItem(1, 0), matrix.getItem(2, 0));
        return res;
    }

    /**
     * Implements a rotation about the z axis. This method returns a new Vector3.
     * @param vector The vector to rotate.
     * @param phi The angle of rotation (in radians) in the positive direction (counterclockwise).
     * @return A new Vector3, the rotated vector.
     */
    public static Vector3 rotateAboutZAxis(Vector3 vector, double phi){
        double[] rotation_data = {Math.cos(phi), -Math.sin(phi), 0, Math.sin(phi), Math.cos(phi), 0, 0, 0, 1};
        Matrix rotation_matrix = new Matrix(3, 3, rotation_data);
        return Vector3.vectorFromMatrix(Matrix.multiply(rotation_matrix, vector));
    }

    /**
     * Implements the cross product of two vectors. This method returns a new Vector3.
     * @param vector1 First vector, order matters.
     * @param vector2 Second vector, order matters.
     * @return A new Vector3, the cross product.
     */
    public static Vector3 crossProduct(Vector3 vector1, Vector3 vector2){
        double first_item = vector1.getAxis(1) * vector2.getAxis(2) - vector1.getAxis(2) * vector2.getAxis(1);
        double second_item = - (vector1.getAxis(0) * vector2.getAxis(2) - vector1.getAxis(2) * vector2.getAxis(0));
        double third_item = vector1.getAxis(0) * vector2.getAxis(1) - vector1.getAxis(1) * vector2.getAxis(0);
        double[] new_data = new double[3]; new_data[0] = first_item; new_data[1] = second_item; new_data[2] = third_item;
        return new Vector3(new_data);
    }

    /**
     * Normalizes the input vector. This method returns a new Vector3.
     * @param vector The vector to be normalized.
     * @return A new Vector3, normalized vector.
     */
    public static Vector3 normalize(Vector3 vector){
        return Vector3.vectorFromMatrix(Matrix.multiply(1/vector.getVectorLength(), vector));
    }

    @Override
    public String toString(){
        return "[" + getAxis(0) + ", " + getAxis(1) + ", " + getAxis(2) + "]";
    }
}
