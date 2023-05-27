package gameobjects;

import java.util.ArrayList;

import physics.Physics;
import physics.event.BallBallCollisionEvent;
import physics.event.BallPocketCollisionEvent;
import physics.event.Event;
import ui.TableUI;
import vectormath.Vector3;

public class Table{
    private ArrayList<Ball> ballArray;
    private Cushion[] cushions = Cushion.getStandardCushionArray();
    private PointCushion[] pointCushions = PointCushion.getStandardPointCushionArray();
    private Pocket[] pockets = Pocket.getStandardPocketArray();
    private Event currentEvent;

    private double timeThisTurn = 0;
    private BallBallCollisionEvent firstCollisionEvent;

    private final double TABLE_LEFT = cushions[1].getStart().getAxis(0);
    private final double TABLE_RIGHT = cushions[10].getStart().getAxis(0);
    private final double TABLE_TOP = cushions[4].getStart().getAxis(1);
    private final double TABLE_BOTTOM = cushions[13].getStart().getAxis(1);

    public Table(ArrayList<Ball> ballArray){
        this.ballArray = ballArray;
    }

    public ArrayList<Ball> getBallArray(){
        return this.ballArray;
    }

    public void setBallArray(ArrayList<Ball> array){
        this.ballArray = array;
    }
    
    public Cushion[] getCushionArray(){
        return this.cushions;
    }

    public PointCushion[] getPointCushionArray(){
        return this.pointCushions;
    }

    public Pocket[] getPocketArray(){
        return this.pockets;
    }

    public BallBallCollisionEvent getFirstCollision(){
        return firstCollisionEvent;
    }

    public void resetTurn(){
        firstCollisionEvent = null;
        timeThisTurn = 0;
    }

    public Ball getLowestNumberedBall(){
        int num = getLowestNumberOnTable();
        for(Ball ball : ballArray){
            if(ball.getNumber() == num)
                return ball;
        }
        return null;
    }

    public int getLowestNumberOnTable(){
        int min = 10;
        for(Ball ball : ballArray){
            if(ball.getNumber() < min && ball.getNumber() > 0){
                min = ball.getNumber();
            }
        }
        return min;
    }

    public boolean cueBallPocketed(){
        return ballArray.get(0).getNumber() != 0;
    }

    public boolean nineBallPocketed(){
        for(Ball ball : ballArray){
            if(ball.getNumber() == 9)
                return false;
        }
        return true;
    }

    public boolean isPositionValid(double x, double y){
        if(x < TABLE_LEFT + Ball.RADIUS || x > TABLE_RIGHT - Ball.RADIUS || y < TABLE_BOTTOM + Ball.RADIUS || y > TABLE_TOP - Ball.RADIUS)
            return false;

        Vector3 pos = new Vector3(x, y, 0);
        for(Ball ball : ballArray){
            Vector3 diffVector = Vector3.subtract(ball.getPosition(), pos);
            if(diffVector.getVectorLength() <= 2 * Ball.RADIUS)
                return false;
        }
        return true;
    }

    public void getNextEvent(){
        currentEvent = Physics.pollEventQueue(this);
    }

    // -2 -> no event found, -1 -> normal exit.
    public int evolveTable(TableUI tableUI, double dt){
        if(currentEvent == null){               
            getNextEvent();
        }
        if(currentEvent == null){
            return -2;
        }

        if(dt > currentEvent.getTimeUntilEvent()){
            dt = currentEvent.getTimeUntilEvent();
            Physics.updateTable(this, dt);
            currentEvent.resolveEvent();
            if(currentEvent instanceof BallBallCollisionEvent && firstCollisionEvent == null){
                firstCollisionEvent = (BallBallCollisionEvent)currentEvent;
            }
            else if(currentEvent instanceof BallPocketCollisionEvent){
                BallPocketCollisionEvent convertedEvent = (BallPocketCollisionEvent)currentEvent;
                int idx = convertedEvent.getIndex();
                tableUI.getBallUIArray().remove(idx);
            }
            currentEvent = null;
            timeThisTurn += dt;
            if(timeThisTurn <= TableUI.UPDATION_INTERVAL / 1000.)
                evolveTable(tableUI, TableUI.UPDATION_INTERVAL / 1000.);
        }
        else{
            Physics.updateTable(this, dt);
            currentEvent.decreaseTimeUntilEvent(dt);
        }
        timeThisTurn = 0;
        return -1;
    }
}
