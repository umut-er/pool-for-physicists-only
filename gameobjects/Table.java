package gameobjects;

import physics.event.Event;

public class Table{
    private Ball[] ballArray;
    private Cushion[] cushions; // Will be implemented later.
    private Event currentEvent; // Useful for event-based updation algorithm

    public Table(Ball[] ballArray){
        this.ballArray = ballArray;
    }

    public Ball[] getBallArray(){
        return this.ballArray;
    }
}
