package physics;

import gameobjects.Ball;
import vectormath.Vector3;

public class Physics{
    public static void evolveBallMotion(Ball ball, double dt){
        Vector3 displacementVector = ball.getDisplacement();
        Vector3 additionVector = new Vector3(0.5 * dt, 0.5 * dt, 0.5 * dt);
        displacementVector.inPlaceAdd(additionVector);
        ball.setDisplacement(displacementVector);
    }
}
