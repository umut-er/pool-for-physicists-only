package physics.event;
import gameobjects.Ball;

@SuppressWarnings("unused")
public class BallStateChangeEvent extends Event{
    private Ball ball;

    public BallStateChangeEvent(Ball ball, double dt){
        this.ball=ball;
        setTimeUntilEvent(dt);
    }

    public void resolveEvent(){};
}
