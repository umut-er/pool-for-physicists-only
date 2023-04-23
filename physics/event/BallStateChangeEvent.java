package physics.event;
import gameobjects.Ball;

public class BallStateChangeEvent extends Event{
    private Ball ball;

    public BallStateChangeEvent(Ball ball){
        this.ball=ball;
    }

    public void resolveEvent(){};
}
