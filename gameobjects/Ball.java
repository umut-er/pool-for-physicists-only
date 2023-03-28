package gameobjects;

import ui.BallUI;
import vectormath.Vector3;

enum BallType{
    STRIPE, SOLID, CUE
}

public class Ball {
    private BallType type;
    private Vector3 displacement;
    private Vector3 velocity;
    private Vector3 angularVelocity;
    private BallUI ballui;

    public Ball(BallType type, String imageName){
        this(type, imageName, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public Ball(BallType type, String imageName, double... data) throws IllegalArgumentException{
        if(data.length != 9) throw new IllegalArgumentException("9 arguments required for vector initialization, " + data.length + " given.");
        this.type = type;
        this.displacement = new Vector3(data[0], data[1], data[2]);
        this.velocity = new Vector3(data[3], data[4], data[5]);
        this.angularVelocity = new Vector3(data[6], data[7], data[8]);
    }

    public Vector3 getDisplacement(){
        return this.displacement;
    }

    public Vector3 getVelocity(){
        return this.velocity;
    }

    public Vector3 getAngularVelocity(){
        return this.angularVelocity;
    }

    public BallType getType(){
        return this.type;
    }

    public void setDisplacement(double x, double y, double z){
        this.displacement.setAll(x, y, z);
    }

    public void setDisplacement(Vector3 displacement){
        this.displacement = displacement;
    }

    public void setVelocity(double x, double y, double z){
        this.velocity.setAll(x, y, z);
    }
    
    public void setVelocity(Vector3 velocity){
        this.velocity = velocity;
    }

    public void setAngularVelocity(double x, double y, double z){
        this.angularVelocity.setAll(x, y, z);
    }

    public void setAngularVelocity(Vector3 angularVelocity){
        this.angularVelocity = angularVelocity;
    }

}
