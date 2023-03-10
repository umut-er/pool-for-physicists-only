package gameobjects;

import vectormath.Vector3;

public class Cushion {
    private Vector3 startPosition;
    private Vector3 endPosition;

    public Cushion(double... data) throws IllegalArgumentException{
        if(data.length != 6) throw new IllegalArgumentException("6 arguments required, " + data.length + " given.");
        startPosition = new Vector3(data[0], data[1], data[2]);
        endPosition = new Vector3(data[3], data[4], data[5]);
    }

    public Vector3 getStart(){
        return this.startPosition;
    }

    public Vector3 getEnd(){
        return this.endPosition;
    }

    public void setStart(double x, double y, double z){
        this.startPosition.setAll(x, y, z);
    }

    public void setStart(Vector3 start){
        this.startPosition = start;
    }

    public void setEnd(double x, double y, double z){
        this.endPosition.setAll(x, y, z);
    }

    public void setEnd(Vector3 end){
        this.startPosition = end;
    }
}
