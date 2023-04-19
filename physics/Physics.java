package physics;

import gameobjects.*;
import vectormath.Vector3;

public class Physics{
    private static final double GRAVITATIONAL_CONSTANT = 9.81;
    // private static final double SPINNING_COEFFICIENT = 4 * Ball.BALL_RADIUS / 9; // I don't think this is needed.
    private static final double SPINNING_COEFFICIENT = 10 * GRAVITATIONAL_CONSTANT / 9; // Derived by 5 * g * sp / (2 * r)
    private static final double ROLLING_COEFFICIENT = 0.01;
    private static final double SLIDING_COEFFICIENT = 0.2;

    /**
     * This method calculates the event queue and returns the minimum time.
     * @param table Table object to calculate the event on.
     * @return minimum time (in seconds) it will take for an event to happen.
     */
    public static double pollEventQueue(Table table){
        return 1.0;
    }

    public static double calculateSlideTime(Ball ball){
        Vector3 uVector = Vector3.add(ball.getVelocity(), Vector3.crossProduct(new Vector3(0, 0, Ball.BALL_RADIUS), ball.getAngularVelocity()));
        return (2 * uVector.getVectorLength()) / (7 * SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT);
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
        double diff = Math.min(SPINNING_COEFFICIENT * deltaTime, Math.abs(ZAxisAngularVelocity));
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
        else{
            ball.getVelocity().inPlaceSubtract(normalizedVelocity);
        }

        ball.getAngularVelocity().setAxis(0, -1 / Ball.BALL_RADIUS * ball.getVelocity().getAxis(1));
        ball.getAngularVelocity().setAxis(1, 1 / Ball.BALL_RADIUS * ball.getVelocity().getAxis(0));

        evolveSpinningBallMotion(ball, deltaTime);
    }

    /**
     * Calculates the next state of given ball and modifies it, assuming it is sliding.
     * @param ball the Ball object to evolve.
     */
    private static void evolveSlidingBallMotion(Ball ball, double deltaTime){
        double slideTimeLeft = calculateSlideTime(ball);
        if(slideTimeLeft < deltaTime)
            deltaTime = slideTimeLeft;

        Vector3 radiusVector = new Vector3(0, 0, Ball.BALL_RADIUS);
        Vector3 relativeVelocity = Vector3.add(ball.getVelocity(), Vector3.crossProduct(radiusVector, ball.getAngularVelocity()));
        Vector3 normalizedRelativeVelocity = Vector3.normalize(relativeVelocity);

        ball.getDisplacement().inPlaceAdd(Vector3.multiply(deltaTime, ball.getVelocity()));
        ball.getDisplacement().inPlaceSubtract(Vector3.multiply(SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime * deltaTime / 2, normalizedRelativeVelocity));

        Vector3 deltaVelocity = Vector3.multiply(SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime, normalizedRelativeVelocity);
        if(ball.getVelocity().getVectorLength() < deltaVelocity.getVectorLength()){
            ball.getVelocity().setAxis(0, 0);
            ball.getVelocity().setAxis(1, 0);
        }
        else{
            ball.getVelocity().inPlaceSubtract(deltaVelocity);
        }

        double constantMultiplier = (5 * SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime) / (2 * Ball.BALL_RADIUS);
        Vector3 deltaVector = Vector3.multiply(constantMultiplier, normalizedRelativeVelocity);
        double deltaXAngularVelocity = - deltaVector.getAxis(1);
        double deltaYAngularVelocity = deltaVector.getAxis(0);
        ball.getAngularVelocity().setAxis(0, ball.getAngularVelocity().getAxis(0) + deltaXAngularVelocity);
        ball.getAngularVelocity().setAxis(1, ball.getAngularVelocity().getAxis(1) + deltaYAngularVelocity);
        
        evolveSpinningBallMotion(ball, deltaTime);
    }

    private static void resolveBallBallCollision(Ball ball1, Ball ball2){
        Vector3 initialBall2Velocity = ball2.getVelocity();
        Vector3 initialVelocity = Vector3.subtract(ball1.getVelocity(), ball2.getVelocity());
        Vector3 lineOfCollision = Vector3.subtract(ball2.getDisplacement(), ball1.getDisplacement());
        lineOfCollision.normalize();
        double angle = Vector3.getAngleBetweenVectors(initialVelocity, lineOfCollision);
        double ball2VelocityScalar = initialVelocity.getVectorLength() * Math.cos(angle);
        Vector3 ball2Velocity = Vector3.multiply(ball2VelocityScalar, lineOfCollision);
        Vector3 ball1Velocity = Vector3.subtract(initialVelocity, ball2Velocity);
        ball1.setVelocity(Vector3.add(ball1Velocity, initialBall2Velocity));
        ball2.setVelocity(Vector3.add(ball2Velocity, initialBall2Velocity));
    }

    public static void main(String[] args) {
        Ball test = new Ball(BallType.CUE, 0.5, 0.5, 0, 0, 1, 0, 0, 0, 0);
        Ball test2 = new Ball(BallType.CUE, 0.5, 1, 0, 0, 0, 0, 0, 0, 0);
        for(int i = 0; i < 5; i++){
            Physics.evolveBallMotion(test, 0.1);
            System.out.println("Ball 1");
            System.out.println(test);
        }
        Physics.resolveBallBallCollision(test, test2);
        System.out.println("HANDLEDDDD");
        for(int i = 0; i < 10; i++){
            Physics.evolveBallMotion(test, 0.05);
            Physics.evolveBallMotion(test2, 0.05);
            System.out.println("Ball 1");
            System.out.println(test);
            System.out.println("Ball 2");
            System.out.println(test2);
        }
    }
}
