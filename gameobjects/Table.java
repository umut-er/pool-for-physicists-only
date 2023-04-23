package gameobjects;

import java.util.ArrayList;

import physics.Physics;
import physics.event.Event;

public class Table{
    private ArrayList<Ball> ballArray;
    private Cushion[] cushions; // Will be implemented later.
    private Event currentEvent; // Useful for event-based updation algorithm

    public Table(ArrayList<Ball> ballArray){
        this.ballArray = ballArray;
    }

    public ArrayList<Ball> getBallArray(){
        return this.ballArray;
    }

    public void getNextEvent(){
        currentEvent = Physics.pollEventQueue(this);
    }

    public void evolveTable(double dt){
        if(currentEvent == null){
            getNextEvent();
        }

        if(dt > currentEvent.getTimeUntilEvent()){
            dt = currentEvent.getTimeUntilEvent();
            Physics.updateTable(this, dt);
            currentEvent.resolveEvent();
            currentEvent = null;
        }
        else{
            Physics.updateTable(this, dt);
            currentEvent.decreaseTimeUntilEvent(dt);
        }
    }
}
