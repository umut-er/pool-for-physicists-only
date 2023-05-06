package gameobjects;

import ui.TableUI;
import vectormath.Vector3;

public class Cushion{
    private static final double EPSILON = 0.15 * Ball.RADIUS;
    public static final double THETA = Math.asin(EPSILON / Ball.RADIUS);
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

    public static Cushion[] getStandardCushionArray(){
        Cushion[] cushions = new Cushion[18];
        Double[] numbers1 = {24.,38.,38.,44.,58.,354.,389.,394.,690.,724.,710.,710.,704.,690.,394.,359.,354.,58.};
        Double[] numbers2 = {44.,58.,354.,388.,374.,374.,388.,374.,374.,368.,354.,58.,24.,38.,38.,24.,38.,38.};
        Double[] numbers3 = {38.,38.,24.,58.,354.,359.,394.,690.,704.,710.,710.,724.,690.,394.,389.,354.,58.,44.};
        Double[] numbers4 = {58.,354.,368.,374.,374.,388.,374.,374.,388.,354.,58.,44.,38.,38.,24.,38.,38.,24.};

        for(int i = 0; i < cushions.length; i++){
            cushions[i] = new Cushion(numbers1[i]/TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(),numbers2[i]/TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(),0,
                                    numbers3[i]/TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), numbers4[i]/TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(),0);
        }            
        return cushions;
    }
}
