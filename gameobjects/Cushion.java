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

    public static Cushion[] getStandartCushionArray(){
        Cushion[] cushions = new Cushion[18];
        cushions[0] = new Cushion(24. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 44. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                38. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 58. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[1] = new Cushion(38. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 58. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                38. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 354. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[2] = new Cushion(38. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 354. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                24. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 368. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[3] = new Cushion(44. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 388. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                58. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 374. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[4] = new Cushion(58. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 374. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                354. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 374. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[5] = new Cushion(354. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 374. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                359. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 388. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[6] = new Cushion(389. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 388. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                394. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 374. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[7] = new Cushion(394. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 374. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                690. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 374. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[8] = new Cushion(690. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 374. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                704. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 388. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[9] = new Cushion(724. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 368. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                710. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 354. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[10] = new Cushion(710. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 354. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                710. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 58. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[11] = new Cushion(710. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 58. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                724. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 44. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[12] = new Cushion(704. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 24. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                690. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 38. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[13] = new Cushion(690. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 38. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                394. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 38. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[14] = new Cushion(394. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 38. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                389. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 24. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[15] = new Cushion(359. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 24. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                354. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 38. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[16] = new Cushion(354. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 38. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                58. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 38. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        cushions[17] = new Cushion(58. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 38. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0,
                                44. / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 24. / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0);
        
        return cushions;
    }
}
