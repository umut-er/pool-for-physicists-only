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
    private boolean visible; // Required later on when we implement pockets.

    public BallUI(Ball ball, int ballNumber){
        this.ball = ball;
        this.ballNumber = ballNumber;
        this.visible = true;
        try{
            BufferedImage bufferedImage = ImageIO.read(getClass().getResource("ballImage.png"));
            this.ballImage = bufferedImage.getScaledInstance(17, 17, Image.SCALE_DEFAULT);
        } 
        catch (IOException e){
            this.ballImage=null;
            System.out.println(e.getMessage());
        }
    }

    public Image getImage(){
       return ballImage;
    }

    public boolean isVisible(){
        return visible;
    }

    public int getXPixel(double tableXLength, int pixelXLength){
        return (int)((ball.getDisplacement().getAxis(0) / tableXLength) * pixelXLength);
    }

    public int getYPixel(double tableYLength, int pixelYLength){
        return (int)((ball.getDisplacement().getAxis(0) / tableYLength) * pixelYLength);
    }
}
