package physics.event;

import gameobjects.Ball;
import gameobjects.PointCushion;
import physics.Physics;

public class BallPointCushionCollisionEvent extends Event{
    private Ball ball;
    private PointCushion pointCushion;

    public BallPointCushionCollisionEvent(Ball ball, PointCushion pointCushion, double dt){
        this.ball = ball;
        this.pointCushion = pointCushion;
        setTimeUntilEvent(dt);
    }

    public void resolveEvent(){
        Physics.resolveBallPointCushionCollision(ball, pointCushion);
    }
}
