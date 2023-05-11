package gameobjects;

import java.util.ArrayList;

import physics.Physics;
import physics.event.BallPocketCollisionEvent;
import physics.event.Event;

public class Table{
    static double total = 0;
    static int amount = 0; 

    private ArrayList<Ball> ballArray;
    private Cushion[] cushions;
    private PointCushion[] pointCushions;
    private Pocket[] pockets;
    private Event currentEvent;

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

    public void getNextEvent(){
        currentEvent = Physics.pollEventQueue(this);
    }

    // -2 -> no event found, -1 -> no ball pocketed, >=0 -> index of pocketed ball
    public int evolveTable(double dt){  
        if(currentEvent == null){         
            long start = System.nanoTime();
            getNextEvent();
            long finish = System.nanoTime();
            System.out.println((finish - start) / 1000000. + " ms");
            total += finish - start;
            amount++;
            System.out.println("Average: " + total / (amount * 1000000) + " ms");
        }
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

            if(dt <= 1e-6)
                evolveTable(0.05);
        }
        else{
            Physics.updateTable(this, dt);
            currentEvent.decreaseTimeUntilEvent(dt);
        }
        return ret;
    }
}
