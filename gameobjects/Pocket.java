package gameobjects;

import ui.TableUI;
import vectormath.Vector3;

public class Pocket {
    private double radius;
    private Vector3 position;

    public Pocket(Vector3 position, double radius){
        this.position = position;
        this.radius = radius;
    }

    public double getX(){
        return position.getAxis(0);
    }

    public double getY(){
        return position.getAxis(1);
    }

    public double getRadius(){
        return this.radius;
    }

    public static Pocket[] getStandardPocketArray(){
        Pocket[] pockets = new Pocket[6];
        double[] xs = {24, 24, 374, 724, 724, 374};
        double[] ys = {24, 388, 389, 388, 24, 23};
        double[] rs = {20, 20, 15, 20, 20, 15};

        for(int i = 0; i < 6; i++){
            pockets[i] = new Pocket(new Vector3(xs[i] / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters(), 
                                    ys[i] / TableUI.getTableHeightPixels() * TableUI.getTableHeightMeters(), 0),
                                    rs[i] / TableUI.getTableWidthPixels() * TableUI.getTableWidthMeters());
        }
        return pockets;
    }
}
