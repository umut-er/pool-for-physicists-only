package ui;

import gameobjects.Ball;

public class BallUI {
    private Ball ball;
    private int ballNumber; // This will be used in selecting the image to load.
    private int ballPixelRadius = (int)(Ball.RADIUS * TableUI.getTableWidthPixels() / TableUI.getTableWidthMeters());

    public BallUI(Ball ball, int ballNumber){
        this.ball = ball;
        this.ballNumber = ballNumber;
    }

    public int getBallPixelRadius(){
        return this.ballPixelRadius;
    }

    public double getBallXPosition(){
        return ball.getDisplacement().getAxis(0);
    }

    public double getBallYPosition(){
        return ball.getDisplacement().getAxis(1);
    }
}
