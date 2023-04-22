package physics.event;

import gameobjects.Ball;
import physics.Physics;

public class BallBallCollisionEvent extends Event{

    private Ball ball1;
    private Ball ball2;

    public BallBallCollisionEvent(Ball ball1, Ball ball2){
        this.ball1=ball1;
        this.ball2=ball2;
    }

    public void resolveEvent(){
        Physics.resolveBallBallCollision(ball1, ball2);
    }
}
