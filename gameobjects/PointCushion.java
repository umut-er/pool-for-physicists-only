package gameobjects;

import ui.TableUI;
import vectormath.Vector3;

public class PointCushion {
    private Vector3 position;

    public PointCushion(double... data) throws IllegalArgumentException{
        if(data.length != 3)
            throw new IllegalArgumentException("3 arguments required, " + data.length + " given.");
        position = new Vector3(data);
    }

    public Vector3 getPosition(){
        return this.position;
    }

    public double getX(){
        return position.getAxis(0);
    }

    public double getY(){
        return position.getAxis(1);
    }

    public static PointCushion[] getStandardPointCushionArray(){
        PointCushion[] pointCushions = new PointCushion[12];
        double[] xPositions = {38, 38, 58, 354, 394, 690, 710, 710, 690, 394, 354, 58};
        double[] yPositions = {58, 354, 374, 374, 374, 374, 354, 58, 38, 38, 38, 38, 38};
        for(int i = 0; i < pointCushions.length; i++){
            pointCushions[i] = new PointCushion(xPositions[i] / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 
                                                yPositions[i] / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0); 
        }
        return pointCushions;
    }
}
