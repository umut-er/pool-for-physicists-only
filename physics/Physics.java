package physics;

import gameobjects.Ball;
import gameobjects.*;
import gameobjects.Table;

public class Physics{
    private static final double GRAVITATIONAL_CONSTANT = 9.81;
    private static final double SPINNING_COEFFICIENT = 4 * Ball.BALL_RADIUS / 9;

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
        double diff = Math.min(5 * SPINNING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime / (2 * Ball.BALL_RADIUS), Math.abs(ZAxisAngularVelocity));
        ZAxisAngularVelocity -= sign * diff;
        ball.getAngularVelocity().setAxis(2, ZAxisAngularVelocity);
    }

    /**
     * Calculates the next state of given ball and modifies it, assuming it is rolling.
     * @param ball the Ball object to evolve.
     */
    private static void evolveRollingBallMotion(Ball ball, double deltaTime){

    }

    /**
     * Calculates the next state of given ball and modifies it, assuming it is sliding.
     * @param ball the Ball object to evolve.
     */
    private static void evolveSlidingBallMotion(Ball ball, double deltaTime){

    }

    public static void main(String[] args) {
        Ball test = new Ball(Ball.Type.CUE, 0, 0, 0, 0, 0, 0, 0, 0, 10);
        while(test.getAngularVelocity().getAxis(2) != 0){
            System.out.println(test);
            evolveBallMotion(test, 0.1);           
        }
        System.out.println(test);
    }
}
