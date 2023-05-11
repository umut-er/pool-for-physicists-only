package gameobjects;

import ui.TableUI;
import vectormath.Vector3;

public class Ball {
    public static final double RADIUS = 0.028575;
    public static final double MASS = 0.170097;

    private BallType type;
    private Vector3 displacement;
    private Vector3 velocity;
    private Vector3 angularVelocity;

    public Ball(BallType type){
        this(type, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public Ball(BallType type, double... data) throws IllegalArgumentException{
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

    public boolean isStationary(){
        return velocity.vectorLengthEquals(0) && angularVelocity.vectorLengthEquals(0);
    }

    public boolean isSpinning(){
        return velocity.vectorLengthEquals(0) &&
            angularVelocity.getAxis(0) <= 1e-6 &&
            angularVelocity.getAxis(1) <= 1e-6 &&
            angularVelocity.getAxis(2) > 1e-6;
    }

    public boolean isRolling(){
        Vector3 normalizedVector = new Vector3(0, 0, -RADIUS);
        return !angularVelocity.vectorLengthEquals(0) && Vector3.crossProduct(normalizedVector, angularVelocity).equals(velocity);
    }

    public boolean isSliding(){
        Vector3 normalizedVector = new Vector3(0, 0, -RADIUS);
        return !Vector3.crossProduct(normalizedVector, angularVelocity).equals(velocity);
    }

    public static Ball[] getStandardBallArray(){
        Ball cueBall2 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 1, TableUI.getTableHeightMeters() / 2, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall3 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.999 + Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 + Ball.RADIUS + 0.0001, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall4 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.999 + Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 - Ball.RADIUS - 0.0001, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall5 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.998 + 2 * Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 + 2 * Ball.RADIUS + 0.0002, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall6 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.998 + 2 * Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall7 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.998 + 2 * Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 - 2 * Ball.RADIUS - 0.0002, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall8 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.997 + 2 * Ball.RADIUS * Math.sqrt(3) + Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 + Ball.RADIUS + 0.0001, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall9 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.997 + 2 * Ball.RADIUS * Math.sqrt(3) + Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 - Ball.RADIUS - 0.0001, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall10 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.996 + 4 * Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2, 0, 0, 0, 0, 0, 0, 0);
        
        Ball cueBall1 = new Ball(BallType.CUE, 0.825, TableUI.getTableHeightMeters() / 2, 0, 0, 0, 0, 0, 0, 0); // circular cushion?
        Vector3 shot = new Vector3(11, 0.01, 0);
        double angle = Vector3.getSignedAngle2D(Vector3.subtract(cueBall2.getDisplacement(), cueBall1.getDisplacement()), new Vector3(1, 0, 0));
        shot = Vector3.rotateAboutZAxis(shot, -angle); // +angle causes bugs 
        cueBall1.setVelocity(shot);

        Ball[] ret = {cueBall1, cueBall2, cueBall3, cueBall4, cueBall5, cueBall6, cueBall7, cueBall8, cueBall9, cueBall10};
        return ret;
    }

    @Override
    public String toString(){
        String ballTypeString = "";
        if(type == BallType.CUE)
            ballTypeString = "cue";
        else if(type == BallType.SOLID)
            ballTypeString = "solid";
        else
            ballTypeString = "stripe";
        return String.format("Ball Type: %s\nPosition: %s\nVelocity: %s\nAngular Velocity: %s"
                                , ballTypeString
                                , getDisplacement()
                                , getVelocity()
                                , getAngularVelocity());
    }
}
