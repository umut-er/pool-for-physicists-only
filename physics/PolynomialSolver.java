package physics;

// TODO: Testing

public class PolynomialSolver{
    
    /**
     * Returns a real root of the cubic equation.
     * Parameters describe a cubic equation ax^3 + bx^2 + cx + d = 0.
     * The equations are exact but a small error can be present due to floating point arithmetic.
     * This uses the method explained in this link: https://math.vanderbilt.edu/schectex/courses/cubic/
     * @param a ax^3
     * @param b bx^2
     * @param c cx
     * @param d d
     * @return a double, a real root of the equation. It is guaranteed that this exists.
     */
    public static double solveCubicEquation(double a, double b, double c, double d){
        double p = -b / (3 * a);
        double q = p * p * p + (b * c - 3 * a * d) / (6 * a * a);
        double r = c / (3 * a);

        double differenceTerm = Math.sqrt(q * q + (r - p * p) * (r - p * p) * (r - p * p));
        double firstTerm = Math.cbrt(q + differenceTerm);
        double secondTerm = Math.cbrt(q - differenceTerm);
        return firstTerm + secondTerm + p;
    }

    /**
     * Returns the minimum positive real root of the quartic equation. -1 if no such roots exist.
     * Parameters describe the quartic equation ax^4 + bx^3 + cx^2 + dx + e = 0.
     * The equations are exact but a small error can be present due to floating point arithmetic.
     * This uses the method explained in this link: https://proofwiki.org/wiki/Ferrari%27s_Method
     * @param a ax^4
     * @param b bx^3
     * @param c cx^2
     * @param d dx
     * @param e e
     * @return a double, the smallest positive real root if exists, else -1.
     */
    public static double solveQuarticEquation(double a, double b, double c, double d, double e){
        double y1 = solveCubicEquation(1, -c / a, b * d / (a * a) - 4 * e / a, 4 * c * e / (a * a) - b * b * e / (a * a * a) - d * d / (a * a));
        double pDifferenceTerm = Math.sqrt(b * b / (a * a) - 4 * c / a + 4 * y1);
        double[] p = {b / a + pDifferenceTerm, b / a - pDifferenceTerm};
        double qDifferenceTerm = Math.sqrt(y1 * y1 - 4 * e / a);
        double[] q = {y1 + qDifferenceTerm, y1 - qDifferenceTerm};

        // To get the smallest positive real root. Probably suboptimal.
        double min = -4, result;
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++){
                for(int k = 0; k < 2; k++){
                    double answerDifferenceTerm = Math.sqrt(p[i] * p[i] - 8 * q[j]);                    
                    if(i == 0) result = -p[i] - answerDifferenceTerm;
                    else result = -p[i] + answerDifferenceTerm;

                    if(!Double.isNaN(result) && result > 0 && (min == -4 || min > result)) min = result;
                }
            }
        }
        return min / 4;
    }

    public static void main(String[] args) {
        System.out.println(PolynomialSolver.solveQuarticEquation(1,-4,6,-4,1));
    }
}
