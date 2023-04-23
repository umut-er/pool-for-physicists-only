package physics;

import gameobjects.*;
import physics.event.BallBallCollisionEvent;
import physics.event.BallStateChangeEvent;
import physics.event.Event;
import vectormath.Vector3;

public class Physics{
    private static final double GRAVITATIONAL_CONSTANT = 9.81;
    // private static final double SPINNING_COEFFICIENT = 4 * Ball.BALL_RADIUS / 9; // I don't think this is needed.
    private static final double SPINNING_COEFFICIENT = 10 * GRAVITATIONAL_CONSTANT / 9; // Derived by 5 * g * sp / (2 * r)
    private static final double ROLLING_COEFFICIENT = 0.01;
    private static final double SLIDING_COEFFICIENT = 0.2;

    private static double getAngle(Vector3 vector){
        return Vector3.getAngleBetweenVectors(vector, new Vector3(-1, 0, 0));
    }

    /**
     * This method calculates the event queue and returns the minimum time.
     * @param table Table object to calculate the event on.
     * @return minimum time (in seconds) it will take for an event to happen.
     */
    public static Event pollEventQueue(Table table){
        Event event = null;
        double time;

        for(Ball ball : table.getBallArray()){
            if(ball.isSpinning()){
                time = calculateSpinningTime(ball);
                if(event == null || event.getTimeUntilEvent() > time)
                    event = new BallStateChangeEvent(ball, time);
            }
            else if(ball.isRolling()){
                time = calculateRollingTime(ball);
                if(event == null || event.getTimeUntilEvent() > time)
                    event = new BallStateChangeEvent(ball, time);
            }
            else if(ball.isSliding()){
                time = calculateSlidingTime(ball);
                if(event == null || event.getTimeUntilEvent() > time)
                    event = new BallStateChangeEvent(ball, time);
            }
        }

        for(int i = 0; i < (table.getBallArray().size()); i++){
            for(int j = i + 1; j < (table.getBallArray().size()); j++){
                Ball ball1 = table.getBallArray().get(i);
                Ball ball2 = table.getBallArray().get(j);
                time = calculateBallBallCollisionTime(ball1, ball2);
                if(time > 0){
                    if(event == null || event.getTimeUntilEvent() > time)
                        event = new BallBallCollisionEvent(ball1, ball2, time);
                }
            } 
        }

        return event;
    }

    public static void updateTable(Table table, double dt){
        for(Ball ball : table.getBallArray())
            evolveBallMotion(ball, dt);
    }

    public static double calculateBallBallCollisionTime(Ball ball1, Ball ball2){
        double ax1 = 0, bx1 = 0, cx1 = -ball1.getDisplacement().getAxis(0);
        double ay1 = 0, by1 = 0, cy1 = -ball1.getDisplacement().getAxis(1);
        double az1 = 0, bz1 = 0, cz1 = 0;
        double phi = getAngle(ball1.getVelocity());
        if(ball1.isSpinning() || ball1.isStationary()){
            ax1 = 0; bx1 = 0;
            ay1 = 0; by1 = 0;
        }
        else{
            bx1 = ball1.getVelocity().getVectorLength() * Math.cos(phi);
            by1 = ball1.getVelocity().getVectorLength() * Math.sin(phi);
            if(ball1.isRolling()){
                ax1 = - ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * Math.cos(phi) / 2;
                ay1 = - ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * Math.sin(phi) / 2;
            }
            else if(ball1.isSliding()){
                Vector3 radiusVector = new Vector3(0, 0, Ball.BALL_RADIUS);
                Vector3 relativeVelocity = Vector3.add(ball1.getVelocity(), Vector3.crossProduct(radiusVector, ball1.getAngularVelocity()));
                relativeVelocity.normalize();
                ax1 = - SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * (relativeVelocity.getAxis(0) * Math.cos(phi) - relativeVelocity.getAxis(1) * Math.sin(phi)) / 2;
                ay1 = - SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * (relativeVelocity.getAxis(0) * Math.sin(phi) + relativeVelocity.getAxis(1) * Math.cos(phi)) / 2;
            }
        }

        double ax2 = 0, bx2 = 0, cx2 = -ball2.getDisplacement().getAxis(0);
        double ay2 = 0, by2 = 0, cy2 = -ball2.getDisplacement().getAxis(1);
        double az2 = 0, bz2 = 0, cz2 = 0;
        phi = getAngle(ball2.getVelocity());
        if(ball2.isSpinning() || ball2.isStationary()){
            ax2 = 0; bx2 = 0;
            ay2 = 0; by2 = 0;
        }
        else{
            bx2 = ball2.getVelocity().getVectorLength() * Math.cos(phi);
            by2 = ball2.getVelocity().getVectorLength() * Math.sin(phi);
            if(ball2.isRolling()){
                ax2 = - ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * Math.cos(phi) / 2;
                ay2 = - ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * Math.sin(phi) / 2;
            }
            else if(ball2.isSliding()){
                Vector3 radiusVector = new Vector3(0, 0, Ball.BALL_RADIUS);
                Vector3 relativeVelocity = Vector3.add(ball2.getVelocity(), Vector3.crossProduct(radiusVector, ball2.getAngularVelocity()));
                relativeVelocity.normalize();
                ax2 = - SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * (relativeVelocity.getAxis(0) * Math.cos(phi) - relativeVelocity.getAxis(1) * Math.sin(phi)) / 2;
                ay2 = - SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * (relativeVelocity.getAxis(0) * Math.sin(phi) + relativeVelocity.getAxis(1) * Math.cos(phi)) / 2;
            }
        }

        double Ax = ax1 - ax2, Ay = ay1 - ay2, Az = az1 - az2;
        double Bx = bx1 - bx2, By = by1 - by2, Bz = bz1 - bz2;
        double Cx = cx1 - cx2, Cy = cy1 - cy2, Cz = cz1 - cz2;

        return PolynomialSolver.solveQuarticEquation(
            Ax * Ax + Ay * Ay + Az * Az, 
            2 * (Ax * Bx + Ay * By + Az * Bz), 
            Bx * Bx + By * By + Bz * Bz + 2 * (Ax * Cx + Ay * Cy + Az * Cz), 
            2 * (Bx * Cx + By * Cy + Bz * Cz), 
            Cx * Cx + Cy * Cy + Cz * Cz - 4 * Ball.BALL_RADIUS * Ball.BALL_RADIUS
        );
    }

    public static double calculateSpinningTime(Ball ball){
        return 2 * Ball.BALL_RADIUS * ball.getAngularVelocity().getAxis(2) / (5 * SPINNING_COEFFICIENT * GRAVITATIONAL_CONSTANT);
    }

    public static double calculateRollingTime(Ball ball){
        return ball.getVelocity().getVectorLength() / (ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT);
    }

    public static double calculateSlidingTime(Ball ball){
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
        if(calculateRollingTime(ball) < deltaTime)
            deltaTime = calculateRollingTime(ball);

        Vector3 normalizedVelocity = Vector3.normalize(ball.getVelocity());
        ball.getDisplacement().inPlaceAdd(Vector3.multiply(deltaTime, ball.getVelocity()));
        ball.getDisplacement().inPlaceSubtract(Vector3.multiply(ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime * deltaTime / 2, normalizedVelocity));

        normalizedVelocity.inPlaceMultiply(ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime);
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
        double slideTimeLeft = calculateSlidingTime(ball);
        if(slideTimeLeft < deltaTime)
            deltaTime = slideTimeLeft;

        Vector3 radiusVector = new Vector3(0, 0, Ball.BALL_RADIUS);
        Vector3 relativeVelocity = Vector3.add(ball.getVelocity(), Vector3.crossProduct(radiusVector, ball.getAngularVelocity()));
        Vector3 normalizedRelativeVelocity = Vector3.normalize(relativeVelocity);

        ball.getDisplacement().inPlaceAdd(Vector3.multiply(deltaTime, ball.getVelocity()));
        ball.getDisplacement().inPlaceSubtract(Vector3.multiply(SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime * deltaTime / 2, normalizedRelativeVelocity));

        Vector3 deltaVelocity = Vector3.multiply(SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime, normalizedRelativeVelocity);
        ball.getVelocity().inPlaceSubtract(deltaVelocity);

        double constantMultiplier = (5 * SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime) / (2 * Ball.BALL_RADIUS);
        Vector3 deltaVector = Vector3.multiply(constantMultiplier, normalizedRelativeVelocity);
        double deltaXAngularVelocity = - deltaVector.getAxis(1);
        double deltaYAngularVelocity = deltaVector.getAxis(0);
        ball.getAngularVelocity().setAxis(0, ball.getAngularVelocity().getAxis(0) + deltaXAngularVelocity);
        ball.getAngularVelocity().setAxis(1, ball.getAngularVelocity().getAxis(1) + deltaYAngularVelocity);
        
        evolveSpinningBallMotion(ball, deltaTime);
    }

    /**
     * Resolves the ball-ball collision. Assumes no friction between balls.
     * @param ball1 First ball.
     * @param ball2 Second ball.
     */
    public static void resolveBallBallCollision(Ball ball1, Ball ball2){
        Vector3 initialBall2Velocity = ball2.getVelocity();
        Vector3 initialVelocity = Vector3.subtract(ball1.getVelocity(), ball2.getVelocity());
        if(initialVelocity.vectorLengthEquals(0))
            return;
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
        Ball test = new Ball(BallType.CUE, 0.5, 0.5, 0, 1, 0, 0, 0, 0, 0);
        Ball test2 = new Ball(BallType.CUE, 1.5, 0.5, 0, 0, 0, 0, 0, 0, 0);
        double time = calculateSlidingTime(test);
        evolveBallMotion(test, time);
        time = calculateBallBallCollisionTime(test, test2);
        evolveBallMotion(test, time);
        System.out.println(time);
        System.out.println(test);
        System.out.println(test2);
    }
}
