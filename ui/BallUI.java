package ui;

import java.awt.Color;

import gameobjects.Ball;

public class BallUI {
    private static final Color[] BALL_COLORS = {Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE};
    private int ballNumber;
    private Ball ball;
    private Color color;
    private int ballPixelRadius = (int)(Ball.RADIUS * TableUI.getTableWidthPixels() / TableUI.getTableWidthMeters());

    public BallUI(Ball ball, int ballNumber){
        this.ball = ball;
        this.ballNumber = ballNumber;
        this.color = BALL_COLORS[ballNumber];
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

    public Color getColor(){
        return this.color;
    }
}
