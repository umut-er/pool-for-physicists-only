import vectormath.*;

// TODO: Figure out a project structure (packages and classes).

public class Main {
    public static void main(String[] args){
        double[] data = {5, 12, 0};
        Vector3 vector = new Vector3(data);
        System.out.println(vector.getVectorLength());
    }
}
