package physics;

// TODO: Testing

public class PolynomialSolver{
    public static double[] solveQuadraticEquationAllRoots(double a, double b, double c){
        double determinant = b * b - 4 * a * c;
        double[] res = new double[2];
        res[0] = (-b + Math.sqrt(determinant)) / (2 * a);
        res[1] = (-b - Math.sqrt(determinant)) / (2 * a);
        return res;
    }

    public static double solveQuadraticEquation(double a, double b, double c){
        double determinant = b * b - 4 * a * c;
        if(determinant < 0)
            return -1;
        if(c <= 1e-6 && c >= -1e-6){
            return 0;
        }
        if(-b - Math.sqrt(determinant) > 0)
            return (-b - Math.sqrt(determinant)) / (2 * a);
        if(-b + Math.sqrt(determinant) > 0)
            return (-b + Math.sqrt(determinant)) / (2 * a);
        return -1;
    }

    /**
     * This method is called Bairstow's method. It only uses real arithmetic to split the quartic into two quadratics.
     * Source: https://mycareerwise.com/programming/category/numerical-analysis/baristow-method#c
     * @param a ax^4
     * @param b bx^3
     * @param c cx^2
     * @param d dx
     * @param e e
     * @return Minimum positive real root of the quartic equation if it exists, else -1.
     */
    public static double solveQuarticEquation(double a, double b, double c, double d, double e){
        double p = 10;
        double q = 20;

        double[] aa = {a, b, c, d, e};
        double[] bb = new double[5];
        double[] cc = new double[5];
        double dp = 0, dq = 0, dn = 0;
        
        int count = 0;
        do {
            // bb[0] = aa[0];
            // cc[0] = bb[0];
            cc[0] = bb[0] = aa[0];
            bb[1] = aa[1] - p * bb[0];
            cc[1] = bb[1] - p * cc[0];

            for(int i = 2; i <= 4; i++){
                bb[i] = aa[i] - p * bb[i - 1] - q * bb[i - 2];
                cc[i] = bb[i] - p * cc[i - 1] - q * cc[i - 2];
            }

            dn = cc[2] * cc[2] - cc[1] * (cc[3] - bb[3]);
            dp = (bb[3] * cc[2] - bb[4] * cc[1]) / dn;
            dq = (bb[4] * cc[2] - bb[3] * (cc[3] - bb[3])) / dn;
            p += dp;
            q += dq;
            count++;
        } while ((Math.abs(dp) > 1e-10 || Math.abs(dq) > 1e-10) && count < 10000);

        double r1 = solveQuadraticEquation(1, p, q);
        double a2 = a;
        double b2 = (b - p * a2);
        double c2 = e / q;
        double r2 = solveQuadraticEquation(a2, b2, c2);
        double min = -1;
        if(!Double.isNaN(r1) && r1 >= 0 && (min == -1 || r1 < min))
            min = r1;
        if(!Double.isNaN(r2) && r2 >= 0 && (min == -1 || r2 < min))
            min = r2;
        return min;
    }
}
