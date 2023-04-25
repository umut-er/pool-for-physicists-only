package ui;

import javax.imageio.ImageIO;
import gameobjects.Ball;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BallUI {
    private Image ballImage;
    private Ball ball;
    private int ballNumber; // This will be used in selecting the image to load.
    private int ballPixelRadius = (int)(Ball.BALL_RADIUS * TableUI.getTableWidthPixels() / TableUI.getTableWidthMeters());

    public BallUI(Ball ball, int ballNumber){
        this.ball = ball;
        this.ballNumber = ballNumber;
        try{
            BufferedImage bufferedImage = ImageIO.read(getClass().getResource("ballImage.png"));
            this.ballImage = bufferedImage.getScaledInstance(2 * ballPixelRadius, 2 * ballPixelRadius, Image.SCALE_DEFAULT);
        } 
        catch (IOException e){
            this.ballImage=null;
            System.out.println(e.getMessage());
        }
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
