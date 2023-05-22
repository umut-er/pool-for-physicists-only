package gameobjects;

import java.util.ArrayList;

import physics.Physics;
import physics.event.BallBallCollisionEvent;
import physics.event.BallPocketCollisionEvent;
import physics.event.Event;
import ui.TableUI;

public class Table{
    private ArrayList<Ball> ballArray;
    private Cushion[] cushions;
    private PointCushion[] pointCushions;
    private Pocket[] pockets;
    private Event currentEvent;

    private double timeThisTurn = 0;

    private BallBallCollisionEvent firstCollisionEvent;

    public Table(ArrayList<Ball> ballArray){
        this.ballArray = ballArray;
        this.cushions = Cushion.getStandardCushionArray();
        this.pockets = Pocket.getStandardPocketArray();
        this.pointCushions = PointCushion.getStandardPointCushionArray();
    }

    public ArrayList<Ball> getBallArray(){
        return this.ballArray;
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

    public void getNextEvent(){
        currentEvent = Physics.pollEventQueue(this);
    }

    // -2 -> no event found, -1 -> no ball pocketed, >=0 -> index of pocketed ball
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
