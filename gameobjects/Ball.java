package gameobjects;

import ui.TableUI;
import vectormath.Vector3;

public class Ball {
    public static final double RADIUS = 0.028575;
    public static final double MASS = 0.170097;

    private int ballNumber;
    private Vector3 displacement;
    private Vector3 velocity;
    private Vector3 angularVelocity;

    public Ball(int number, double... data) throws IllegalArgumentException{
        if(data.length != 9) throw new IllegalArgumentException("9 arguments required for vector initialization, " + data.length + " given.");
        this.ballNumber = number;
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

    public int getNumber(){
        return this.ballNumber;
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
        Ball cueBall2 = new Ball(1, TableUI.getTableWidthMeters() - 0.82, TableUI.getTableHeightMeters() / 2, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall3 = new Ball(2, TableUI.getTableWidthMeters() - 0.8199 + Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 + Ball.RADIUS, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall4 = new Ball(3, TableUI.getTableWidthMeters() - 0.8199 + Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 - Ball.RADIUS, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall5 = new Ball(4, TableUI.getTableWidthMeters() - 0.8189 + 2 * Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 + 2 * Ball.RADIUS, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall6 = new Ball(5, TableUI.getTableWidthMeters() - 0.8189 + 2 * Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall7 = new Ball(6, TableUI.getTableWidthMeters() - 0.8189 + 2 * Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 - 2 * Ball.RADIUS, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall8 = new Ball(7, TableUI.getTableWidthMeters() - 0.8179 + 2 * Ball.RADIUS * Math.sqrt(3) + Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 + Ball.RADIUS, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall9 = new Ball(8, TableUI.getTableWidthMeters() - 0.8179 + 2 * Ball.RADIUS * Math.sqrt(3) + Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 - Ball.RADIUS, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall10 = new Ball(9, TableUI.getTableWidthMeters() - 0.8169 + 4 * Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2, 0, 0, 0, 0, 0, 0, 0);
        
        Ball cueBall1 = new Ball(0, 0.7, TableUI.getTableHeightMeters() / 2, 0, 0, 0, 0, 0, 0, 0);

        // TODO: Correct order, implement shuffling.

        Ball[] ret = {cueBall1, cueBall2, cueBall3, cueBall4, cueBall5, cueBall6, cueBall7, cueBall8, cueBall9, cueBall10};
        return ret;
    }

    @Override
    public String toString(){
        return String.format("Position: %s\nVelocity: %s\nAngular Velocity: %s",
                                getDisplacement(),
                                getVelocity(),
                                getAngularVelocity());
    }
}
