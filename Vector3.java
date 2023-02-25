// TODO: Docstrings
// TODO: Testing
// TODO: Better error messages

public class Vector3 extends Matrix{
    
    public Vector3(double[] data1d){
        super(3, 1, data1d);
    }

    public double getAxis(int index){
        return data[index][0];
    }

    public double getVectorLength(){
        return Math.sqrt(this.getAxis(0) * this.getAxis(0) + this.getAxis(1) * this.getAxis(1) + this.getAxis(2) * this.getAxis(2));
    }

    public void setAxis(int index, double num){
        data[index][0] = num;
    }

    public void setAll(double x, double y, double z){
        data[0][0] = x;
        data[1][0] = y;
        data[2][0] = z;
    }

    public static Vector3 vectorFromMatrix(Matrix matrix) throws ArrayIndexOutOfBoundsException{
        if(matrix.getRowCount() != 3 || matrix.getColumnCount() != 1){
            throw new ArrayIndexOutOfBoundsException("Matrix is not compatible with a vector");
        }
        double[] empty_data = new double[3];
        Vector3 res = new Vector3(empty_data);
        res.setAll(matrix.getItem(0, 0), matrix.getItem(1, 0), matrix.getItem(2, 0));
        return res;
    }

    public static Vector3 rotateAboutZAxis(Vector3 vector, double phi){
        double[] rotation_data = {Math.cos(phi), -Math.sin(phi), 0, Math.sin(phi), Math.cos(phi), 0, 0, 0, 1};
        Matrix rotation_matrix = new Matrix(3, 3, rotation_data);
        return Vector3.vectorFromMatrix(Matrix.multiply(rotation_matrix, vector));
    }

    public static Vector3 crossProduct(Vector3 vector1, Vector3 vector2){
        double first_item = vector1.getAxis(1) * vector2.getAxis(2) - vector1.getAxis(2) * vector2.getAxis(1);
        double second_item = - (vector1.getAxis(0) * vector2.getAxis(2) - vector1.getAxis(2) * vector2.getAxis(0));
        double third_item = vector1.getAxis(0) * vector2.getAxis(1) - vector1.getAxis(1) * vector2.getAxis(0);
        double[] new_data = new double[3]; new_data[0] = first_item; new_data[1] = second_item; new_data[2] = third_item;
        return new Vector3(new_data);
    }

    public static Vector3 normalize(Vector3 vector){
        return Vector3.vectorFromMatrix(Matrix.multiply(1/vector.getVectorLength(), vector));
    }

    @Override
    public String toString(){
        return "[" + getAxis(0) + ", " + getAxis(1) + ", " + getAxis(2) + "]";
    }

    public static void main(String[] args){
        double[] data1 = {3.234234234, 4.2453454534234, 0};
        Vector3 vector = new Vector3(data1);
        // Matrix matrix = new Matrix(3, 3, rotation);
        // System.out.println(Vector3.normalize(vector));
        System.out.println(vector);
    }
}
