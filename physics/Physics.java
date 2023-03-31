package physics;

import gameobjects.Ball;
import vectormath.Vector3;

public class Physics{
    public static void evolveBallMotion(Ball ball, double dt){
        Vector3 displacementVector = ball.getDisplacement();
        Vector3 additionVector = new Vector3(1 * dt, 1 * dt, 0);
        displacementVector.inPlaceAdd(additionVector);
        ball.setDisplacement(displacementVector);
    }
}
