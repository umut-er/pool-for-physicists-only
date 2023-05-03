package physics.event;

import gameobjects.Ball;
import gameobjects.Cushion;
import physics.Physics;

public class BallCushionCollisionEvent extends Event{   
    private Ball ball;
    private Cushion cushion;

    public BallCushionCollisionEvent(Ball ball, Cushion cushion, double dt){
        this.ball=ball;
        this.cushion=cushion;
        setTimeUntilEvent(dt);
    }

    public void resolveEvent(){
        Physics.resolveBallCushionCollision(ball, cushion);
        // ball.setVelocity(0, 0, 0);
        // ball.setAngularVelocity(0, 0, 0);
    }
}
