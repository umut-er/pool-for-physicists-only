
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

//Testing for PolynomialSolver methods solveQuadraticEquation and solveQuarticEquation
public class PolynomialSolverTest{
    //Testcases for the method solveCubicEquation

    @Test
    public void shouldTwoOverThree()
    {
         assertEquals(2/3.0,  PolynomialSolver.solveQuadraticEquation(6,5,-6),0.01);
    }
    @Test
    public void shouldReturnTwo()
    {
        assertEquals(2,PolynomialSolver.solveQuadraticEquation(2, -10, 12),0.01);
    }
    
    @Test
    public void shouldReturnFive()
    {
         assertEquals(5,PolynomialSolver.solveQuadraticEquation(3,-6,-45),0.01);
    }

    @Test
    public void shouldReturnNoResult()
    {
        assertEquals(-1,PolynomialSolver.solveQuadraticEquation(1, -3, 20), 0);
    }

     @Test
    public void shouldReturnSquareRootTwo(){
        assertEquals(Math.sqrt(2), PolynomialSolver.solveQuadraticEquation(4, 4*Math.sqrt(2), -16), 0.01);
    }

    //Testcases for the method solveQuarticEquation

    @Test
    public void shouldReturnOne()
    {
        assertEquals(1,PolynomialSolver.solveQuarticEquation(1, -4, 6, -4, 1),0.01);
    }

    @Test
    public void shouldReturnMinusOne()
    {
        assertEquals(-1,PolynomialSolver.solveQuarticEquation(2, 4, 6, 8, 10),0.01);
    }

    @Test
    public void shouldReturnThree()
    {
        assertEquals(3,PolynomialSolver.solveQuarticEquation(3, 6, -123, -126, 1080),0.01);
    }

    @Test
    public void shouldReturnSquareRootTwoMinusOne()
    {
        assertEquals(Math.sqrt(2) - 1, PolynomialSolver.solveQuarticEquation(1, 2, -4, -6, 3), 0.01);
    }

    @Test
    public void shouldReturnNoResults()
    {
        assertEquals(-1, PolynomialSolver.solveQuarticEquation(2, 24, 102, 184, 120), 0);
    }

}
