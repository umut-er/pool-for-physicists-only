package ui;

import java.awt.Color;

import gameobjects.Ball;

public class BallUI {
    private static final Color[] BALL_COLORS = {Color.WHITE, Color.YELLOW, Color.BLUE, Color.RED, new Color(128, 0, 128), new Color(255, 89, 0), new Color(0, 100, 0), new Color(70, 23, 11), Color.BLACK, new Color(255, 233, 0)};
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
