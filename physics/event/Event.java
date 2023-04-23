package physics.event;

public abstract class Event{
    private double timeUntilEvent;

    public double getTimeUntilEvent(){
        return this.timeUntilEvent;
    }

    public void setTimeUntilEvent(double dt){
        this.timeUntilEvent = dt;
    }

    public void decreaseTimeUntilEvent(double dt){
        this.timeUntilEvent -= dt;
    }

    public abstract void resolveEvent();
}
