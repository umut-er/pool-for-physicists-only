package server;

import java.io.Serializable;

public class HitInfo implements Serializable{
    private double xPosition;
    private double yPosition;
    private double powerValue;
    private double elevationAngle;
    private double directionAngle;

    public HitInfo(double x, double y, double power, double elevation, double direction){
        xPosition = x;
        yPosition = y;
        powerValue = power;
        elevationAngle = elevation;
        directionAngle = direction;
    }

    public double getX(){return xPosition;}
    public double getY(){return yPosition;}
    public double getPower(){return powerValue;}
    public double getElevation(){return elevationAngle;}
    public double getDirection(){return directionAngle;}
}
