package physics.event;

import gameobjects.Ball;
import gameobjects.Table;

public class BallPocketCollisionEvent extends Event{
    private Table table;
    private Ball ball;

    public BallPocketCollisionEvent(Table table, Ball ball, double dt){
        this.table = table;
        this.ball = ball;
        setTimeUntilEvent(dt);
    }

    public Ball getBall(){
        return this.ball;
    }

    public void resolveEvent(){
        table.removeBall(ball.getNumber());
    }
}
