package ui;

import java.awt.Color;

import gameobjects.Ball;

public class BallUI {
    private static final Color[] BALL_COLORS = {Color.WHITE, Color.YELLOW, Color.BLUE, Color.RED, new Color(128, 0, 128), new Color(255, 89, 0), new Color(0, 120, 0), new Color(70, 23, 11), Color.BLACK, new Color(255, 233, 0)};
    private Ball ball;
    private Color color;
    public static final int BALL_PIXEL_RADIUS = (int)(Ball.RADIUS * TableUI.getTableWidthPixels() / TableUI.getTableWidthMeters());

    public BallUI(Ball ball){
        this.ball = ball;
        this.color = BALL_COLORS[ball.getNumber()];
    }

    public Ball getBall(){
        return this.ball;
    }

    public double getBallXPosition(){
        return ball.getPosition().getAxis(0);
    }

    public double getBallYPosition(){
        return ball.getPosition().getAxis(1);
    }

    public Color getColor(){
        return this.color;
    }
}
