package physics.event;

import gameobjects.Ball;
import gameobjects.Table;

public class BallPocketCollisionEvent extends Event{
    private Table table;
    private Ball ball;
    private int index;

    public BallPocketCollisionEvent(Table table, Ball ball, int index, double dt){
        this.table = table;
        this.ball = ball;
        this.index = index;
        setTimeUntilEvent(dt);
    }

    public int getIndex(){
        return index;
    }

    public void resolveEvent(){
        table.getBallArray().remove(index);
    }
}
