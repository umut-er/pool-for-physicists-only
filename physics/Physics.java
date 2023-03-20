package physics;

import gameobjects.*;
import vectormath.Vector3;

public class Physics{
    private static final double GRAVITATIONAL_CONSTANT = 9.81;
    // private static final double SPINNING_COEFFICIENT = 4 * Ball.BALL_RADIUS / 9; // I don't think this is needed.
    private static final double SPIN_MULTIPLIER = 10 * GRAVITATIONAL_CONSTANT / 9; // Derived by 5 * g * sp / (2 * r)
    private static final double ROLLING_COEFFICIENT = 0.01;

    /**
     * This method calculates the event queue and returns the minimum time.
     * @param table Table object to calculate the event on.
     * @return minimum time (in seconds) it will take for an event to happen.
     */
    public static double pollEventQueue(Table table){
        return 1.0;
    }

    /**
     * Calculates the next state of given ball and modifies it.
     * @param ball the Ball object to evolve.
     */
    public static void evolveBallMotion(Ball ball, double deltaTime){
        if(ball.isStationary()) return;

        if(ball.isSpinning()){
            evolveSpinningBallMotion(ball, deltaTime);
        }
        else if(ball.isRolling()){
            evolveRollingBallMotion(ball, deltaTime);
        }
        else evolveSlidingBallMotion(ball, deltaTime);
    }

    /**
     * Calculates the next state of given ball and modifies it, assuming it is spinning.
     * @param ball the Ball object to evolve.
     */
    private static void evolveSpinningBallMotion(Ball ball, double deltaTime){
        double ZAxisAngularVelocity = ball.getAngularVelocity().getAxis(2);
        double sign = Math.signum(ZAxisAngularVelocity);
        double diff = Math.min(SPIN_MULTIPLIER * deltaTime, Math.abs(ZAxisAngularVelocity));
        ZAxisAngularVelocity -= sign * diff;
        ball.getAngularVelocity().setAxis(2, ZAxisAngularVelocity);
    }

    /**
     * Calculates the next state of given ball and modifies it, assuming it is rolling.
     * @param ball the Ball object to evolve.
     */
    private static void evolveRollingBallMotion(Ball ball, double deltaTime){
        Vector3 normalizedVelocity = Vector3.normalize(ball.getVelocity());
        ball.getDisplacement().inPlaceAdd(Vector3.multiply(deltaTime, ball.getVelocity()));
        ball.getDisplacement().inPlaceSubtract(Vector3.multiply(ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime * deltaTime / 2, normalizedVelocity));

        normalizedVelocity.inPlaceMultiply(ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime);
        if(ball.getVelocity().getVectorLength() < normalizedVelocity.getVectorLength()){
            ball.getVelocity().setAxis(0, 0);
            ball.getVelocity().setAxis(1, 0);
        }
        else
            ball.getVelocity().inPlaceSubtract(normalizedVelocity);
        ball.getAngularVelocity().setAxis(0, -1 / Ball.BALL_RADIUS * ball.getVelocity().getAxis(1));
        ball.getAngularVelocity().setAxis(1, 1 / Ball.BALL_RADIUS * ball.getVelocity().getAxis(0));

        evolveSpinningBallMotion(ball, deltaTime);
    }

    /**
     * Calculates the next state of given ball and modifies it, assuming it is sliding.
     * @param ball the Ball object to evolve.
     */
    private static void evolveSlidingBallMotion(Ball ball, double deltaTime){
        
    }

    public static void main(String[] args) {
        Ball test = new Ball(Ball.Type.CUE, 0, 0, 0, 20 * Ball.BALL_RADIUS, -20 * Ball.BALL_RADIUS, 0, 20, 20, 0);
        while(test.getVelocity().getAxis(0) > 0){
            System.out.println(test);
            evolveBallMotion(test, 0.5);           
        }
        System.out.println(test);
    }
}
