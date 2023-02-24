// TODO: Docstrings
// TODO: Complete the rotation about z-axis.

public class Vector3 extends Matrix{
    
    public Vector3(double[] data1d){
        super(3, 1, data1d);
    }

    public double getAxis(int index){
        return data[index][0];
    }

    public void setAxis(int index, double num){
        data[index][0] = num;
    }

    public void setAll(double x, double y, double z){
        data[0][0] = x;
        data[0][1] = y;
        data[0][2] = z;
    }

    // public static Vector3 rotateAboutZAxis(double phi, Vector3 vector){
    //     double[] rotation_data = {Math.cos(phi), -Math.sin(phi), 0, Math.sin(phi), Math.cos(phi), 0, 0, 0, 1};
    //     Matrix rotation_matrix = new Matrix(3, 3, rotation_data);
    //     return Matrix.multiply(rotation_matrix, vector);
    // }

    public static Vector3 crossProduct(Vector3 vector1, Vector3 vector2){
        double first_item = vector1.getAxis(1) * vector2.getAxis(2) - vector1.getAxis(2) * vector2.getAxis(1);
        double second_item = - (vector1.getAxis(0) * vector2.getAxis(2) - vector1.getAxis(2) * vector2.getAxis(0));
        double third_item = vector1.getAxis(0) * vector2.getAxis(1) - vector1.getAxis(1) * vector2.getAxis(0);
        double[] new_data = new double[3]; new_data[0] = first_item; new_data[1] = second_item; new_data[2] = third_item;
        return new Vector3(new_data);
    }

    public static void main(String[] args){
        double[] data1 = {2, 3, 0};
        double[] rotation = {0, -1, 0, 1, 0, 0, 0, 0, 1};
        Vector3 vector = new Vector3(data1);
        Matrix matrix = new Matrix(3, 3, rotation);
        System.out.println(Matrix.multiply(matrix, vector));
    }
}
