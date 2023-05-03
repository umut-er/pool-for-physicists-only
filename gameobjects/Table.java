package gameobjects;

import java.util.ArrayList;

import physics.Physics;
import physics.event.Event;

public class Table{
    private ArrayList<Ball> ballArray;
    private Cushion[] cushions; // Will be implemented later.
    private Event currentEvent; // Useful for event-based updation algorithm
    private boolean turnDone = false;

    public Table(ArrayList<Ball> ballArray, Cushion... cushions){
        this.ballArray = ballArray;
        this.cushions = cushions;
    }

    public ArrayList<Ball> getBallArray(){
        return this.ballArray;
    }

    public Cushion[] getCushionArray(){
        return this.cushions;
    }

    public void getNextEvent(){
        currentEvent = Physics.pollEventQueue(this);
    }

    public void evolveTable(double dt){
        if(currentEvent == null)
            getNextEvent();
        if(currentEvent == null){
            return;
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
