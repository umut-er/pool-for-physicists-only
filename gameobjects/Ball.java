package gameobjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import ui.TableUI;
import vectormath.Vector3;

public class Ball implements Serializable{
    public static final double RADIUS = 0.028575;
    public static final double MASS = 0.170097;

    private int ballNumber;
    private Vector3 position;
    private Vector3 internalPosition;
    private Vector3 velocity;
    private Vector3 internalVelocity;
    private Vector3 angularVelocity;
    private Vector3 internalAngularVelocity;

    private boolean pocketed = false;

    public Ball(int number, double... data) throws IllegalArgumentException{
        if(data.length != 9) throw new IllegalArgumentException("9 arguments required for vector initialization, " + data.length + " given.");
        this.ballNumber = number;
        this.position = new Vector3(data[0], data[1], data[2]);
        this.velocity = new Vector3(data[3], data[4], data[5]);
        this.angularVelocity = new Vector3(data[6], data[7], data[8]);
        this.internalPosition = new Vector3(this.position);
        this.internalVelocity = new Vector3(this.velocity);
        this.internalAngularVelocity = new Vector3(this.angularVelocity);
        equatePropertyVectors();
    }

    public Ball(int number, double x, double y){
        this(number, x, y, 0., 0., 0., 0., 0., 0., 0.);
    }

    public Vector3 getPosition(){
        return this.position;
    }

    public Vector3 getInternalPosition(){
        return this.internalPosition;
    }

    public Vector3 getVelocity(){
        return this.velocity;
    }

    public Vector3 getInternalVelocity(){
        return this.internalVelocity;
    }

    public Vector3 getAngularVelocity(){
        return this.angularVelocity;
    }

    public Vector3 getInternalAngularVelocity(){
        return this.internalAngularVelocity;
    }

    public int getNumber(){
        return this.ballNumber;
    }

    public boolean getPocketed(){
        return this.pocketed;
    }

    public void setPocketed(boolean pocketed){
        this.pocketed = pocketed;
    }

    public void setPosition(double x, double y, double z){
        this.position.setAll(x, y, z);
    }

    public void setPosition(Vector3 displacement){
        this.position = displacement;
    }

    public void setInternalPosition(double x, double y, double z){
        this.internalPosition.setAll(x, y, z);
    }

    public void setInternalPosition(Vector3 position){
        this.internalPosition = position;
    }

    public void setVelocity(double x, double y, double z){
        this.velocity.setAll(x, y, z);
    }

    public void setVelocity(Vector3 velocity){
        this.velocity = velocity;
    }

    public void setInternalVelocity(double x, double y, double z){
        this.internalVelocity.setAll(x, y, z);
    }

    public void setInternalVelocity(Vector3 velocity){
        this.internalVelocity = velocity;
    }

    public void setAngularVelocity(double x, double y, double z){
        this.angularVelocity.setAll(x, y, z);
    }

    public void setAngularVelocity(Vector3 angularVelocity){
        this.angularVelocity = angularVelocity;
    }

    public void setInternalAngularVelocity(double x, double y, double z){
        this.internalAngularVelocity.setAll(x, y, z);
    }

    public void setInternalAngularVelocity(Vector3 angularVelocity){
        this.internalAngularVelocity = angularVelocity;
    }

    public void equatePropertyVectors(){
        this.internalPosition.setAll(this.position);
        this.internalVelocity.setAll(this.velocity);
        this.internalAngularVelocity.setAll(this.angularVelocity);
    }

    public boolean isStationary(){
        return velocity.vectorLengthEquals(0) && angularVelocity.vectorLengthEquals(0);
    }

    public boolean isSpinning(){
        return velocity.vectorLengthEquals(0) &&
            Math.abs(angularVelocity.getAxis(0)) <= 1e-6 &&
            Math.abs(angularVelocity.getAxis(1)) <= 1e-6 &&
            Math.abs(angularVelocity.getAxis(2)) > 1e-6;
    }

    public boolean isRolling(){
        Vector3 normalizedVector = new Vector3(0, 0, -RADIUS);
        return !angularVelocity.vectorLengthEquals(0) && Vector3.crossProduct(normalizedVector, angularVelocity).equals(velocity);
    }

    public boolean isSliding(){
        Vector3 normalizedVector = new Vector3(0, 0, -RADIUS);
        return !Vector3.crossProduct(normalizedVector, angularVelocity).equals(velocity);
    }

    public static ArrayList<Ball> getTestingLayout(){
        double[] positions = getStandardEightBallPositions();
        ArrayList<Ball> ret = new ArrayList<>();
        Ball cueBall = new Ball(0, 0.9, TableUI.getTableHeightMeters() / 2);
        Ball nineBall = new Ball(9, 0.6, TableUI.getTableHeightMeters() / 2);
        ret.add(cueBall);
        ret.add(nineBall);
        for(int i = 0; i < 6; i++){
            Ball newBall = new Ball(i+1, positions[2*i], positions[2*i+1]);
            ret.add(newBall);
        }

        return ret;
    }

    /**
     *          0
     *         2 4
     *        6 8 10
     *     12 14 16 18
     *    20 22 24 26 28
     * This is rotated counterclockwise 90 degrees in the game UI.
     * @return
     */
    public static double[] getStandardEightBallPositions(){
        double randTerm = ((Math.random() * 4) + 2) / 10000.;
        double[] positions = new double[30];

        double xDefault = TableUI.getTableWidthMeters() - 0.82;
        double yDefault = TableUI.getTableHeightMeters() / 2;

        positions[0] = xDefault;
        positions[1] = yDefault;

        for(int i = 2; i <= 4; i+=2){
            positions[i] = xDefault + randTerm + Ball.RADIUS * Math.sqrt(3);
            positions[i+1] = yDefault + (i - 3) * Ball.RADIUS;
        }

        for(int i = 6; i <= 10; i+=2){
            positions[i] = xDefault + 2 * randTerm + 2 * Ball.RADIUS * Math.sqrt(3);
            positions[i+1] = yDefault + (i - 8) * Ball.RADIUS;
        }

        for(int i = 12; i <= 18; i+=2){
            positions[i] = xDefault + 3 * randTerm + 3 * Ball.RADIUS * Math.sqrt(3);
            positions[i+1] = yDefault + (i - 15) * Ball.RADIUS;
        }

        for(int i = 20; i <= 28; i+=2){
            positions[i] = xDefault + 4 * randTerm + 4 * Ball.RADIUS * Math.sqrt(3);
            positions[i+1] = yDefault + (i - 24) * Ball.RADIUS;
        }

        return positions;
    }

    public static ArrayList<Ball> getStandardNineBallArray(){
        Integer[] nums = {2, 3, 4, 5, 6, 7, 8};
        Collections.shuffle(Arrays.asList(nums));
        double[] positions = getStandardEightBallPositions();

        Ball ball2 = new Ball(nums[0], positions[2], positions[3]);
        Ball ball3 = new Ball(nums[1], positions[4], positions[5]);
        Ball ball4 = new Ball(nums[2], positions[6], positions[7]);
        Ball ball5 = new Ball(nums[3], positions[10], positions[11]);
        Ball ball6 = new Ball(nums[4], positions[14], positions[15]);
        Ball ball7 = new Ball(nums[5], positions[16], positions[17]);
        Ball ball8 = new Ball(nums[6], positions[24], positions[25]);
        
        Ball cueBall = new Ball(0, 0.9, TableUI.getTableHeightMeters() / 2);
        Ball oneBall = new Ball(1, positions[0], positions[1]);
        Ball nineBall = new Ball(9, positions[8], positions[9]);

        Ball[] ret = {cueBall, oneBall, ball2, ball3, nineBall, ball4, ball5, ball6, ball7, ball8};
        return new ArrayList<Ball>(Arrays.asList(ret));
    }

    public static ArrayList<Ball> getStandardEightBallArray(){
        Integer[] nums = {1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15};
        ArrayList<Integer> numsList = new ArrayList<Integer>(Arrays.asList(nums));
        Collections.shuffle(numsList);

        double[] positions = getStandardEightBallPositions();
        ArrayList<Ball> lst = new ArrayList<Ball>();

        lst.add(new Ball(0, 0.9, positions[1]));

        Random rand = new Random();
        int firstCorner = rand.nextInt(7) + 1;
        int secondCorner = rand.nextInt(7) + 9;
        lst.add(new Ball(firstCorner, positions[20], positions[21]));
        lst.add(new Ball(secondCorner, positions[28], positions[29]));
        numsList.remove((Integer)firstCorner);
        numsList.remove((Integer)secondCorner);
        
        for(int i = 0; i < 4; i++){
            lst.add(new Ball(numsList.get(i), positions[2*i], positions[2*i+1]));
        }
        lst.add(new Ball(8, positions[8], positions[9]));
        for(int i = 4; i <= 8; i++){
            lst.add(new Ball(numsList.get(i), positions[2*i+2], positions[2*i+3]));
        }
        for(int i = 9; i <= 11; i++){
            lst.add(new Ball(numsList.get(i), positions[2*i+4], positions[2*i+5]));
        }

        return lst;
    }

    public static ArrayList<Ball> getOnlyNineBallArray(){
        double[] positions = getStandardEightBallPositions();
        ArrayList<Ball> lst = new ArrayList<Ball>();
        lst.add(new Ball(0, 0.9, positions[1]));
        lst.add(new Ball(9, positions[8], positions[9]));
        return lst;
    }

    @Override
    public String toString(){
        return String.format("Position: %s\nVelocity: %s\nAngular Velocity: %s",
                                getPosition(),
                                getVelocity(),
                                getAngularVelocity());
    }
}
