package physics.event;

import gameobjects.Ball;
import gameobjects.Cushion;

public class BallCushionCollisionEvent extends Event{   
    private Ball ball;
    private Cushion cushion;

    public BallCushionCollisionEvent(Ball ball, Cushion cushion, double dt){
        this.ball=ball;
        this.cushion=cushion;
        setTimeUntilEvent(dt);
    }

    public void resolveEvent(){
        // Empty implementation
        // Physics.resolveBallCushionCollision(ball, cushion);
    }
}
