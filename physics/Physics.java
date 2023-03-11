package physics;

import gameobjects.Ball;

public class Physics{
    /**
     * Calculates the next state of given ball and modifies it.
     * @param ball the Ball object to evolve.
     */
    public static void evolveBallMotion(Ball ball){
        if(ball.isStationary()) return;

        if(ball.isSpinning()){
            evolveSpinningBallMotion(ball);
        }
        else if(ball.isRolling()){
            evolveRollingBallMotion(ball);
        }
        else evolveSlidingBallMotion(ball);
    }

    /**
     * Calculates the next state of given ball and modifies it, assuming it is spinning.
     * @param ball the Ball object to evolve.
     */
    private static void evolveSpinningBallMotion(Ball ball){

    }

    /**
     * Calculates the next state of given ball and modifies it, assuming it is rolling.
     * @param ball the Ball object to evolve.
     */
    private static void evolveRollingBallMotion(Ball ball){

    }

    /**
     * Calculates the next state of given ball and modifies it, assuming it is sliding.
     * @param ball the Ball object to evolve.
     */
    private static void evolveSlidingBallMotion(Ball ball){

    }
}
