package temporaryUIPanel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class WhiteBall{

    Image imageBall;
    int xMoveDistance;
    int yMoveDistance;
    int x;
    int y;

    public WhiteBall()
    {
        BufferedImage bufferedImage;
        try
        {
            bufferedImage = ImageIO.read(new File("ballImage.png"));
            imageBall = bufferedImage.getScaledInstance(17, 17, Image.SCALE_DEFAULT);
        }
        catch (IOException e)
        {
            imageBall=null;
        }
    }

    public Image getImage()
    {
        return this.imageBall;
    }
}
