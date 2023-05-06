package gameobjects;

import java.util.ArrayList;

import physics.Physics;
import physics.event.BallPocketCollisionEvent;
import physics.event.Event;

public class Table{
    private ArrayList<Ball> ballArray;
    private Cushion[] cushions; // Will be implemented later.
    private Pocket[] pockets;
    private Event currentEvent; // Useful for event-based updation algorithm

    public Table(ArrayList<Ball> ballArray){
        this.ballArray = ballArray;
        this.cushions = Cushion.getStandardCushionArray();
        this.pockets = Pocket.getStandardPocketArray();
    }

    public ArrayList<Ball> getBallArray(){
        return this.ballArray;
    }

    public Cushion[] getCushionArray(){
        return this.cushions;
    }

    public Pocket[] getPocketArray(){
        return this.pockets;
    }

    public void getNextEvent(){
        currentEvent = Physics.pollEventQueue(this);
    }

    // -2 -> no event found, -1 -> no ball pocketed, >=0 -> index of pocketed ball
    public int evolveTable(double dt){
        if(currentEvent == null)
            getNextEvent();
        if(currentEvent == null){
            return -2;
        }

        int ret = -1;
        if(dt > currentEvent.getTimeUntilEvent()){
            dt = currentEvent.getTimeUntilEvent();
            Physics.updateTable(this, dt);
            currentEvent.resolveEvent();
            if(currentEvent instanceof BallPocketCollisionEvent){
                BallPocketCollisionEvent convertedEvent = (BallPocketCollisionEvent)currentEvent;
                ret = convertedEvent.getIndex();
            }
            currentEvent = null;
        }
        else{
            Physics.updateTable(this, dt);
            currentEvent.decreaseTimeUntilEvent(dt);
        }
        return ret;
    }
}
