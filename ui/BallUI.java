package ui;

import javax.imageio.ImageIO;
import gameobjects.Ball;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BallUI {
    private Image ballImage;
    private Ball ball;

    public BallUI(){
        BufferedImage bufferedImage;
        try 
        {
            bufferedImage = ImageIO.read(new File("ballImage.png"));
            this.ballImage = bufferedImage.getScaledInstance(17, 17, Image.SCALE_DEFAULT);
        } 
        catch (IOException e) 
        {
             this.ballImage=null;
        }
    }

    public Image getImage()
    {
       return this.ballImage;
    }
}
