package physics.event;
import gameobjects.Ball;

public class BallStateChangeEvent extends Event{
    private Ball ball;

    public BallStateChangeEvent(Ball ball, double dt){
        this.ball=ball;
        setTimeUntilEvent(dt);
    }

    public void resolveEvent(){};
}
