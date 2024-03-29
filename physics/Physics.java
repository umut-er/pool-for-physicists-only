package physics;

import gameobjects.*;
import physics.event.BallBallCollisionEvent;
import physics.event.BallCushionCollisionEvent;
import physics.event.BallPocketCollisionEvent;
import physics.event.BallPointCushionCollisionEvent;
import physics.event.BallStateChangeEvent;
import physics.event.Event;
import vectormath.Vector3;

public class Physics{
    private static final double GRAVITATIONAL_CONSTANT = 9.81;
    private static final double SPINNING_COEFFICIENT = 4 * Ball.RADIUS / 9;
    private static final double ROLLING_COEFFICIENT = 0.01;
    private static final double SLIDING_COEFFICIENT = 0.2;
    private static final double CUE_MASS = 0.567;

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
                double dist = Vector3.subtract(ball1.getPosition(), ball2.getPosition()).getVectorLength();
                if(dist < 2 * Ball.RADIUS - 1e-4){
                    // System.out.println("Ball collision is not detected between: " + i + " " + j);
                    Physics.resolveBallBallCollision(ball1, ball2);
                    continue;
                }

                time = calculateBallBallCollisionTime(ball1, ball2);
                if(time >= 0){
                    if(event == null || event.getTimeUntilEvent() > time)
                        event = new BallBallCollisionEvent(ball1, ball2, time);
                }
            } 
        }

        for(Ball ball : table.getBallArray()){
            for(Cushion cushion : table.getCushionArray()){
                time = calculateBallCushionCollisionTime(ball, cushion);
                if(time >= 0){
                    if(event == null || event.getTimeUntilEvent() > time){
                        event = new BallCushionCollisionEvent(ball, cushion, time);
                    }
                }
            }
        }

        for(Ball ball : table.getBallArray()){
            for(PointCushion pointCushion : table.getPointCushionArray()){
                time = calculateBallPointCushionCollisionTime(ball, pointCushion);
                if(time >= 0){
                    if(event == null || event.getTimeUntilEvent() > time){
                        event = new BallPointCushionCollisionEvent(ball, pointCushion, time);
                    }
                }
            }
        }

        for(int i = 0; i < table.getBallArray().size(); i++){
            for(Pocket pocket : table.getPocketArray()){
                Ball ball = table.getBallArray().get(i);
                time = calculateBallPocketCollisionTime(ball, pocket);
                if(time >= 0){
                    if(event == null || event.getTimeUntilEvent() > time){
                        event = new BallPocketCollisionEvent(table, ball, time);
                    }
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
        double ax1 = 0, bx1 = 0, cx1 = ball1.getPosition().getAxis(0);
        double ay1 = 0, by1 = 0, cy1 = ball1.getPosition().getAxis(1);
        double az1 = 0, bz1 = 0, cz1 = 0;
        if(ball1.isSpinning() || ball1.isStationary()){
            ax1 = 0; bx1 = 0;
            ay1 = 0; by1 = 0;
        }
        else{
            bx1 = ball1.getVelocity().getAxis(0);
            by1 = ball1.getVelocity().getAxis(1);
            if(ball1.isRolling()){
                Vector3 normalizedVelocity = Vector3.normalize(ball1.getVelocity());
                ax1 = - ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * normalizedVelocity.getAxis(0) / 2;
                ay1 = - ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * normalizedVelocity.getAxis(1) / 2;
            }
            else if(ball1.isSliding()){
                Vector3 radiusVector = new Vector3(0, 0, Ball.RADIUS);
                Vector3 relativeVelocity = Vector3.add(ball1.getVelocity(), Vector3.crossProduct(radiusVector, ball1.getAngularVelocity()));
                relativeVelocity.normalize();
                ax1 = - SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * relativeVelocity.getAxis(0) / 2;
                ay1 = - SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * relativeVelocity.getAxis(1) / 2;
            }
        }

        double ax2 = 0, bx2 = 0, cx2 = ball2.getPosition().getAxis(0);
        double ay2 = 0, by2 = 0, cy2 = ball2.getPosition().getAxis(1);
        double az2 = 0, bz2 = 0, cz2 = 0;
        if(ball2.isSpinning() || ball2.isStationary()){
            ax2 = 0; bx2 = 0;
            ay2 = 0; by2 = 0;
        }
        else{
            bx2 = ball2.getVelocity().getAxis(0);
            by2 = ball2.getVelocity().getAxis(1);
            if(ball2.isRolling()){
                Vector3 normalizedVelocity = Vector3.normalize(ball2.getVelocity());
                ax2 = - ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * normalizedVelocity.getAxis(0) / 2;
                ay2 = - ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * normalizedVelocity.getAxis(1) / 2;
            }
            else if(ball2.isSliding()){
                Vector3 radiusVector = new Vector3(0, 0, Ball.RADIUS);
                Vector3 relativeVelocity = Vector3.add(ball2.getVelocity(), Vector3.crossProduct(radiusVector, ball2.getAngularVelocity()));
                relativeVelocity.normalize();
                ax2 = - SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * relativeVelocity.getAxis(0) / 2;
                ay2 = - SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * relativeVelocity.getAxis(1) / 2;
            }
        }

        double Ax = ax1 - ax2, Ay = ay1 - ay2, Az = az1 - az2;
        double Bx = bx1 - bx2, By = by1 - by2, Bz = bz1 - bz2;
        double Cx = cx1 - cx2, Cy = cy1 - cy2, Cz = cz1 - cz2;

        Vector3 lineOfCenters = Vector3.subtract(ball2.getPosition(), ball1.getPosition());
        if(Cx * Cx + Cy * Cy + Cz * Cz - 4 * Ball.RADIUS * Ball.RADIUS < 1e-3){
            Vector3 relativeVelocity = Vector3.subtract(ball1.getVelocity(), ball2.getVelocity()); 
            if(Math.abs(Vector3.getSignedAngle2D(relativeVelocity, lineOfCenters)) >= Math.PI / 2 + 1e-6)
                return -1;
        }

        return PolynomialSolver.solveQuarticEquation(
            Ax * Ax + Ay * Ay + Az * Az, 
            2 * (Ax * Bx + Ay * By + Az * Bz), 
            Bx * Bx + By * By + Bz * Bz + 2 * (Ax * Cx + Ay * Cy + Az * Cz), 
            2 * (Bx * Cx + By * Cy + Bz * Cz), 
            Cx * Cx + Cy * Cy + Cz * Cz - 4 * Ball.RADIUS * Ball.RADIUS
        );
    }

    public static double calculateBallCushionCollisionTime(Ball ball, Cushion cushion){
        double lx = -(cushion.getEnd().getAxis(1) - cushion.getStart().getAxis(1)) / (cushion.getEnd().getAxis(0) - cushion.getStart().getAxis(0));
        double ly = 1;
        double l0 = -lx * cushion.getStart().getAxis(0) - cushion.getStart().getAxis(1);

        double d;
        if(cushion.getEnd().getAxis(0) - cushion.getStart().getAxis(0) == 0){
            d = Math.abs(ball.getPosition().getAxis(0) - cushion.getStart().getAxis(0));
        }
        else{
            d = Math.abs(lx * ball.getPosition().getAxis(0) + ly * ball.getPosition().getAxis(1) + l0) / Math.sqrt(lx * lx + ly * ly);
        }
        
        if(d <= Ball.RADIUS + 1e-8 && d >= Ball.RADIUS - 1e-8){
            double s = - Vector3.dotProduct(Vector3.subtract(cushion.getStart(), ball.getPosition()), Vector3.subtract(cushion.getEnd(), cushion.getStart())) / 
                        Vector3.dotProduct(Vector3.subtract(cushion.getEnd(), cushion.getStart()), Vector3.subtract(cushion.getEnd(), cushion.getStart()));
                        if(s >= 0 && s <= 1)
                            return -1;
        }

        // Get ball movement coefficients
        double ax = 0, bx = 0, cx = ball.getPosition().getAxis(0);
        double ay = 0, by = 0, cy = ball.getPosition().getAxis(1);
        if(ball.isSpinning() || ball.isStationary()){
            ax = 0; bx = 0;
            ay = 0; by = 0;
        }
        else{
            bx = ball.getVelocity().getAxis(0);
            by = ball.getVelocity().getAxis(1);
            if(ball.isRolling()){
                Vector3 normalizedVelocity = Vector3.normalize(ball.getVelocity());
                ax = - ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * normalizedVelocity.getAxis(0) / 2;
                ay = - ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * normalizedVelocity.getAxis(1) / 2;
            }
            else if(ball.isSliding()){
                Vector3 radiusVector = new Vector3(0, 0, Ball.RADIUS);
                Vector3 relativeVelocity = Vector3.add(ball.getVelocity(), Vector3.crossProduct(radiusVector, ball.getAngularVelocity()));
                relativeVelocity.normalize();
                ax = - SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * relativeVelocity.getAxis(0) / 2;
                ay = - SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * relativeVelocity.getAxis(1) / 2;
            }
        }

        double A = lx * ax + ly * ay;
        double B = lx * bx + ly * by;
        double C = l0 + lx * cx + ly * cy;
        double s;
        double min = -1;
        double[] solutions = new double[4];
        double[] firstTwoSolutions;
        double[] secondTwoSolutions;
        if(cushion.getEnd().getAxis(0) - cushion.getStart().getAxis(0) == 0){
            firstTwoSolutions = PolynomialSolver.solveQuadraticEquationAllRoots(ax, bx, cx - cushion.getStart().getAxis(0) + Ball.RADIUS);
            secondTwoSolutions = PolynomialSolver.solveQuadraticEquationAllRoots(ax, bx, cx - cushion.getStart().getAxis(0) - Ball.RADIUS);
        }
        else{
            firstTwoSolutions = PolynomialSolver.solveQuadraticEquationAllRoots(A, B, C + Ball.RADIUS * Math.sqrt(lx * lx + ly * ly));
            secondTwoSolutions = PolynomialSolver.solveQuadraticEquationAllRoots(A, B, C - Ball.RADIUS * Math.sqrt(lx * lx + ly * ly));
        }
        solutions[0] = firstTwoSolutions[0]; solutions[1] = firstTwoSolutions[1]; solutions[2] = secondTwoSolutions[0]; solutions[3] = secondTwoSolutions[1];
        for(int i = 0; i < 4; i++){
            if(!Double.isNaN(solutions[i])){
                s = - Vector3.dotProduct(Vector3.subtract(cushion.getStart(), returnPosition(ball, solutions[i])), Vector3.subtract(cushion.getEnd(), cushion.getStart())) / 
                    Vector3.dotProduct(Vector3.subtract(cushion.getEnd(), cushion.getStart()), Vector3.subtract(cushion.getEnd(), cushion.getStart()));
                if(s >= 0 && s <= 1 && ((min == -1 || solutions[i] < min) && solutions[i] > 0))
                    min = solutions[i];
            }
        }

        return min;
    }

    public static double calculateBallPointCushionCollisionTime(Ball ball, PointCushion pointCushion){
        Vector3 relativeVector = Vector3.subtract(ball.getPosition(), pointCushion.getPosition());
        if(relativeVector.vectorLengthEquals(Ball.RADIUS)){
            if(Math.abs(Vector3.getSignedAngle2D(relativeVector, ball.getVelocity())) <= Math.PI / 2 + 1e-6){
                return -1;
            }
        }

        // Get ball movement coefficients
        double ax = 0, bx = 0, cx = ball.getPosition().getAxis(0);
        double ay = 0, by = 0, cy = ball.getPosition().getAxis(1);
        if(ball.isSpinning() || ball.isStationary()){
            ax = 0; bx = 0;
            ay = 0; by = 0;
        }
        else{
            bx = ball.getVelocity().getAxis(0);
            by = ball.getVelocity().getAxis(1);
            if(ball.isRolling()){
                Vector3 normalizedVelocity = Vector3.normalize(ball.getVelocity());
                ax = - ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * normalizedVelocity.getAxis(0) / 2;
                ay = - ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * normalizedVelocity.getAxis(1) / 2;
            }
            else if(ball.isSliding()){
                Vector3 radiusVector = new Vector3(0, 0, Ball.RADIUS);
                Vector3 relativeVelocity = Vector3.add(ball.getVelocity(), Vector3.crossProduct(radiusVector, ball.getAngularVelocity()));
                relativeVelocity.normalize();
                ax = - SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * relativeVelocity.getAxis(0) / 2;
                ay = - SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * relativeVelocity.getAxis(1) / 2;
            }
        }

        double Cx = cx - pointCushion.getX();
        double Cy = cy - pointCushion.getY();
        return PolynomialSolver.solveQuarticEquation(
            ax * ax + ay * ay, 
            2 * (ax * bx + ay * by), 
            bx * bx + by * by + 2 * ax * Cx + 2 * ay * Cy,
            2 * bx * Cx + 2 * by * Cy,
            Cx * Cx + Cy * Cy - Ball.RADIUS * Ball.RADIUS
        );
    }

    public static double calculateBallPocketCollisionTime(Ball ball, Pocket pocket){
        if(ball.isSpinning() || ball.isStationary())
            return -1;
        
        // Get movement coefficients.
        double ax = 0, bx = ball.getVelocity().getAxis(0), cx = ball.getPosition().getAxis(0);
        double ay = 0, by = ball.getVelocity().getAxis(1), cy = ball.getPosition().getAxis(1);
        if(ball.isRolling()){
            Vector3 normalizedVelocity = Vector3.normalize(ball.getVelocity());
            ax = - ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * normalizedVelocity.getAxis(0) / 2;
            ay = - ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * normalizedVelocity.getAxis(1) / 2;
        }
        else if(ball.isSliding()){
            Vector3 radiusVector = new Vector3(0, 0, Ball.RADIUS);
            Vector3 relativeVelocity = Vector3.add(ball.getVelocity(), Vector3.crossProduct(radiusVector, ball.getAngularVelocity()));
            relativeVelocity.normalize();
            ax = - SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * relativeVelocity.getAxis(0) / 2;
            ay = - SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * relativeVelocity.getAxis(1) / 2;
        }

        double A = (ax * ax + ay * ay) / 2;
        double B = ax * bx + ay * by;
        double C = ax * (cx - pocket.getX()) + ay * (cy - pocket.getY()) + (bx * bx + by * by) / 2;
        double D = bx * (cx - pocket.getX()) + by * (cy - pocket.getY());
        double E = (pocket.getX() * pocket.getX() + pocket.getY() * pocket.getY() + cx * cx + cy * cy - pocket.getRadius() * pocket.getRadius()) / 2 - (cx * pocket.getX() + cy * pocket.getY());
        return PolynomialSolver.solveQuarticEquation(A, B, C, D, E);

    }

    public static double calculateSpinningTime(Ball ball){
        return 2 * Ball.RADIUS * Math.abs(ball.getAngularVelocity().getAxis(2)) / (5 * SPINNING_COEFFICIENT * GRAVITATIONAL_CONSTANT);
    }

    public static double calculateRollingTime(Ball ball){
        return ball.getVelocity().getVectorLength() / (ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT);
    }

    public static double calculateSlidingTime(Ball ball){
        Vector3 uVector = Vector3.add(ball.getVelocity(), Vector3.crossProduct(new Vector3(0, 0, Ball.RADIUS), ball.getAngularVelocity()));
        return (2 * uVector.getVectorLength()) / (7 * SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT);
    }

    public static Vector3 returnPosition(Ball ball, double dt){
        Vector3 res = new Vector3(ball.getPosition());
        res.inPlaceAdd(Vector3.multiply(dt, ball.getVelocity()));        
        if(ball.isRolling()){
            Vector3 normalizedVelocity = Vector3.normalize(ball.getVelocity());                    
            res.inPlaceSubtract(Vector3.multiply(ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * dt * dt / 2, normalizedVelocity));
        }
        else if(ball.isSliding()){
            Vector3 radiusVector = new Vector3(0, 0, Ball.RADIUS);
            Vector3 relativeVelocity = Vector3.add(ball.getVelocity(), Vector3.crossProduct(radiusVector, ball.getAngularVelocity()));
            Vector3 normalizedRelativeVelocity = Vector3.normalize(relativeVelocity);
            res.inPlaceSubtract(Vector3.multiply(SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * dt * dt / 2, normalizedRelativeVelocity));
        }
        return res;
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
        double diff = Math.min(5 * SPINNING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime/ (2 * Ball.RADIUS), Math.abs(ZAxisAngularVelocity));
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
        ball.getPosition().inPlaceAdd(Vector3.multiply(deltaTime, ball.getVelocity()));
        ball.getPosition().inPlaceSubtract(Vector3.multiply(ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime * deltaTime / 2, normalizedVelocity));

        normalizedVelocity.inPlaceMultiply(ROLLING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime);
        ball.getVelocity().inPlaceSubtract(normalizedVelocity);

        ball.getAngularVelocity().setAxis(0, -1 / Ball.RADIUS * ball.getVelocity().getAxis(1));
        ball.getAngularVelocity().setAxis(1, 1 / Ball.RADIUS * ball.getVelocity().getAxis(0));

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

        Vector3 radiusVector = new Vector3(0, 0, Ball.RADIUS);
        Vector3 relativeVelocity = Vector3.add(ball.getVelocity(), Vector3.crossProduct(radiusVector, ball.getAngularVelocity()));
        Vector3 normalizedRelativeVelocity = Vector3.normalize(relativeVelocity);
        ball.getPosition().inPlaceAdd(Vector3.multiply(deltaTime, ball.getVelocity()));
        ball.getPosition().inPlaceSubtract(Vector3.multiply(SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime * deltaTime / 2, normalizedRelativeVelocity));

        Vector3 deltaVelocity = Vector3.multiply(SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime, normalizedRelativeVelocity);
        ball.getVelocity().inPlaceSubtract(deltaVelocity);

        double constantMultiplier = (5 * SLIDING_COEFFICIENT * GRAVITATIONAL_CONSTANT * deltaTime) / (2 * Ball.RADIUS);
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
        Vector3 lineOfCollision = Vector3.subtract(ball2.getPosition(), ball1.getPosition());
        lineOfCollision.normalize();
        double angle = Vector3.getSignedAngle2D(initialVelocity, lineOfCollision);
        double ball2VelocityScalar = initialVelocity.getVectorLength() * Math.cos(angle);
        Vector3 ball2Velocity = Vector3.multiply(ball2VelocityScalar, lineOfCollision);
        Vector3 ball1Velocity = Vector3.subtract(initialVelocity, ball2Velocity);
        ball1.setVelocity(Vector3.add(ball1Velocity, initialBall2Velocity));
        ball2.setVelocity(Vector3.add(ball2Velocity, initialBall2Velocity));
    }
    
    public static void resolveBallCushionCollision(Ball ball, Cushion cushion){
        Vector3 leftmostCushionPoint = cushion.getEnd(), rightmostCushionPoint = cushion.getStart();
        if(cushion.getStart().getAxis(0) < cushion.getEnd().getAxis(0) || (cushion.getStart().getAxis(0) == cushion.getEnd().getAxis(0) && cushion.getStart().getAxis(1) > cushion.getEnd().getAxis(1))){
            leftmostCushionPoint = cushion.getStart();
            rightmostCushionPoint = cushion.getEnd();
        }
        Vector3 relativeBallPosition = Vector3.subtract(ball.getPosition(), rightmostCushionPoint);
        Vector3 cushionVector = Vector3.subtract(leftmostCushionPoint, rightmostCushionPoint);
        double vectorAngle = Vector3.getSignedAngle2D(new Vector3(0, 1, 0), cushionVector);
        if(Vector3.getSignedAngle2D(cushionVector, relativeBallPosition) < 0)
            vectorAngle = -(Math.PI - vectorAngle);

        ball.setVelocity(Vector3.rotateAboutZAxis(ball.getVelocity(), -vectorAngle));
        ball.setAngularVelocity(Vector3.rotateAboutZAxis(ball.getAngularVelocity(), -vectorAngle));

        double e = 0.85;
        double sx = ball.getVelocity().getAxis(0) * Math.sin(Cushion.THETA) - ball.getVelocity().getAxis(2) * Math.cos(Cushion.THETA) + Ball.RADIUS * ball.getAngularVelocity().getAxis(1);
        double sy = -ball.getVelocity().getAxis(1) - Ball.RADIUS * ball.getAngularVelocity().getAxis(2) * Math.cos(Cushion.THETA) + Ball.RADIUS * ball.getAngularVelocity().getAxis(0) * Math.sin(Cushion.THETA);
        double c = ball.getVelocity().getAxis(0) * Math.cos(Cushion.THETA);
        double I = 2 * Ball.MASS * Ball.RADIUS * Ball.RADIUS / 5;
        double PzE = Ball.MASS * c * (1 + e);
        double PzS = 2 * Ball.MASS * Math.sqrt(sx * sx + sy * sy) / 7;

        // Velocity
        double deltaX = 0, deltaY = 0, deltaZ = 0;
        if(PzS <= PzE){
            deltaX = -2 * sx * Math.sin(Cushion.THETA) / 7 - (1 + e) * c * Math.cos(Cushion.THETA);
            deltaY = 2 * sy / 7;
            deltaZ = 2 * sx / 7 * Math.cos(Cushion.THETA) - (1 + e) * c * Math.sin(Cushion.THETA);
        }
        else{
            double phi = Vector3.getSignedAngle2D(ball.getVelocity(), new Vector3(1, 0, 0));
            double mu = 0.2;
            if(phi > Math.PI / 2)
                phi = Math.PI - phi;

            deltaX = -c * (1 + e) * (mu * Math.cos(phi) * Math.sin(Cushion.THETA) + Math.cos(Cushion.THETA));
            deltaY = c * (1 + e) * mu * Math.sin(phi);
            deltaZ = mu * (1 + e) * c * Math.cos(phi) * Math.cos(Cushion.THETA) - (1 + e) * c * Math.sin(Cushion.THETA);
        }

        ball.setVelocity(ball.getVelocity().getAxis(0) + deltaX, 
                        ball.getVelocity().getAxis(1) + deltaY, 
                        ball.getVelocity().getAxis(2));
        ball.setAngularVelocity(ball.getAngularVelocity().getAxis(0) - Ball.MASS * Ball.RADIUS * deltaY * Math.sin(Cushion.THETA) / I,
                                ball.getAngularVelocity().getAxis(1) + Ball.MASS * Ball.RADIUS * (deltaX * Math.sin(Cushion.THETA) - deltaZ * Math.cos(Cushion.THETA)) / I,
                                ball.getAngularVelocity().getAxis(2) + Ball.MASS * Ball.RADIUS * deltaY * Math.cos(Cushion.THETA) / I);

        Vector3 correctionTerm = Vector3.rotateAboutZAxis(new Vector3(-1e-5, 0, 0), vectorAngle); // unnoticable in game but useful to seperate ball from cushion after impact for calculations.
        ball.setPosition(Vector3.add(ball.getPosition(), correctionTerm));
        ball.setVelocity(Vector3.rotateAboutZAxis(ball.getVelocity(), vectorAngle));
        ball.setAngularVelocity(Vector3.rotateAboutZAxis(ball.getAngularVelocity(), vectorAngle));
    }

    public static void resolveBallPointCushionCollision(Ball ball, PointCushion pointCushion){
        Vector3 relativeVector = Vector3.subtract(ball.getPosition(), pointCushion.getPosition());
        double vectorAngle = Vector3.getSignedAngle2D(new Vector3(0, 1, 0), relativeVector);
        if(vectorAngle < 0)
            vectorAngle = 2 * Math.PI + vectorAngle;
        vectorAngle -= Math.PI / 2;

        ball.setVelocity(Vector3.rotateAboutZAxis(ball.getVelocity(), -vectorAngle));
        ball.setAngularVelocity(Vector3.rotateAboutZAxis(ball.getAngularVelocity(), -vectorAngle));

        double e = 0.85;
        double sx = ball.getVelocity().getAxis(0) * Math.sin(Cushion.THETA) - ball.getVelocity().getAxis(2) * Math.cos(Cushion.THETA) + Ball.RADIUS * ball.getAngularVelocity().getAxis(1);
        double sy = -ball.getVelocity().getAxis(1) - Ball.RADIUS * ball.getAngularVelocity().getAxis(2) * Math.cos(Cushion.THETA) + Ball.RADIUS * ball.getAngularVelocity().getAxis(0) * Math.sin(Cushion.THETA);
        double c = ball.getVelocity().getAxis(0) * Math.cos(Cushion.THETA);
        double I = 2 * Ball.MASS * Ball.RADIUS * Ball.RADIUS / 5;
        double PzE = Ball.MASS * c * (1 + e);
        double PzS = 2 * Ball.MASS * Math.sqrt(sx * sx + sy * sy) / 7;

        // Velocity
        double deltaX = 0, deltaY = 0, deltaZ = 0;
        if(PzS <= PzE){
            deltaX = -2 * sx * Math.sin(Cushion.THETA) / 7 - (1 + e) * c * Math.cos(Cushion.THETA);
            deltaY = 2 * sy / 7;
            deltaZ = 2 * sx / 7 * Math.cos(Cushion.THETA) - (1 + e) * c * Math.sin(Cushion.THETA);
        }
        else{
            double phi = Vector3.getSignedAngle2D(ball.getVelocity(), new Vector3(1, 0, 0));
            double mu = 0.2;
            if(phi > Math.PI / 2)
                phi = Math.PI - phi;

            deltaX = -c * (1 + e) * (mu * Math.cos(phi) * Math.sin(Cushion.THETA) + Math.cos(Cushion.THETA));
            deltaY = c * (1 + e) * mu * Math.sin(phi);
            deltaZ = mu * (1 + e) * c * Math.cos(phi) * Math.cos(Cushion.THETA) - (1 + e) * c * Math.sin(Cushion.THETA);
        }

        ball.setVelocity(ball.getVelocity().getAxis(0) + deltaX, 
                        ball.getVelocity().getAxis(1) + deltaY, 
                        ball.getVelocity().getAxis(2));
        ball.setAngularVelocity(ball.getAngularVelocity().getAxis(0) - Ball.MASS * Ball.RADIUS * deltaY * Math.sin(Cushion.THETA) / I,
                                ball.getAngularVelocity().getAxis(1) + Ball.MASS * Ball.RADIUS * (deltaX * Math.sin(Cushion.THETA) - deltaZ * Math.cos(Cushion.THETA)) / I,
                                ball.getAngularVelocity().getAxis(2) + Ball.MASS * Ball.RADIUS * deltaY * Math.cos(Cushion.THETA) / I);
        
        Vector3 correctionTerm = Vector3.rotateAboutZAxis(new Vector3(-1e-5, 0, 0), vectorAngle); // unnoticable in game but useful to seperate ball from cushion after impact for calculations.
        ball.setPosition(Vector3.add(ball.getPosition(), correctionTerm));
        ball.setVelocity(Vector3.rotateAboutZAxis(ball.getVelocity(), vectorAngle));
        ball.setAngularVelocity(Vector3.rotateAboutZAxis(ball.getAngularVelocity(), vectorAngle));
    }

    // See Evan Kiefl's implementation.
    public static void hitBall(Ball ball, double cueSpeed, double directionAngle, double elevationAngle, double horizontalSpin, double verticalSpin){
        double spinCorrector = 0.5;
        horizontalSpin *= -Ball.RADIUS * spinCorrector;
        verticalSpin *= -Ball.RADIUS * spinCorrector;

        double c = Math.sqrt(Ball.RADIUS * Ball.RADIUS - horizontalSpin * horizontalSpin - verticalSpin * verticalSpin);
        double temp = horizontalSpin * horizontalSpin 
                    + verticalSpin * verticalSpin * Math.cos(elevationAngle) * Math.cos(elevationAngle) 
                    + c * c * Math.cos(elevationAngle) * Math.cos(elevationAngle)
                    - 2 * verticalSpin * c * Math.cos(elevationAngle) * Math.sin(elevationAngle);
        double force = (2 * CUE_MASS * cueSpeed) / (1 + Ball.MASS / CUE_MASS + (5 * temp) / (2 * Ball.RADIUS * Ball.RADIUS));

        Vector3 velocity = new Vector3(0, -force * Math.cos(elevationAngle) / Ball.MASS , 0);
        velocity = Vector3.rotateAboutZAxis(velocity, directionAngle);
        ball.setVelocity(velocity);

        double momentOfInertia = 2 * Ball.MASS * Ball.RADIUS * Ball.RADIUS / 5;
        double wx = -c * Math.sin(elevationAngle) + verticalSpin * Math.cos(elevationAngle);
        double wy = horizontalSpin * Math.sin(elevationAngle);
        double wz = -horizontalSpin * Math.cos(elevationAngle);

        Vector3 w = Vector3.rotateAboutZAxis(new Vector3(wx, wy, wz), directionAngle);
        w.inPlaceMultiply(force / momentOfInertia);
        ball.setAngularVelocity(w);
    }
}
