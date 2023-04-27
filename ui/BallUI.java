package ui;

import gameobjects.Ball;
import java.awt.Image;

public class BallUI {
    private Image ballImage;
    private Ball ball;
    private int ballNumber; // This will be used in selecting the image to load.
    private int ballPixelRadius = (int)(Ball.BALL_RADIUS * TableUI.getTableWidthPixels() / TableUI.getTableWidthMeters());

    public BallUI(Ball ball, int ballNumber){
        this.ball = ball;
        this.ballNumber = ballNumber;
    }

    public int getBallPixelRadius(){
        return this.ballPixelRadius;
    }

    public Image getImage(){
       return ballImage;
    }

    public int getXPixel(double tableXLength, int pixelXLength){
        return (int)((ball.getDisplacement().getAxis(0) / tableXLength) * pixelXLength);
    }

    public int getYPixel(double tableYLength, int pixelYLength){
        return (int)(((tableYLength - ball.getDisplacement().getAxis(1)) / tableYLength) * pixelYLength);
    }
}
