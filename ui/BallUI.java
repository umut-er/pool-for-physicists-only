package ui;

import javax.imageio.ImageIO;
import gameobjects.Ball;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class BallUI {
    private Image ballImage;
    private Ball ball;
    private TableUI tableUI;
    private int x_coordinate;
    private int y_coordinate;
    private ArrayList<Integer> actionCoordinates;

    public BallUI(){
        ball=new Ball(null);
        tableUI=null;
        actionCoordinates=new ArrayList<Integer>();
        // Starting x and y coordinates
        x_coordinate=200;
        y_coordinate=200;
        BufferedImage bufferedImage;
        try 
        {
            bufferedImage = ImageIO.read(getClass().getResource("ballImage.png"));
            this.ballImage = bufferedImage.getScaledInstance(17, 17, Image.SCALE_DEFAULT);
        } 
        catch (IOException e) 
        {
            this.ballImage=null;
            System.out.println(e.getMessage());
        }
    }

    public Image getImage()
    {
       return ballImage;
    }

    public int[] getFrameActionCoordinates(){
        int[] xyCoordinates=new int[2];
        xyCoordinates[0]=this.actionCoordinates.get(0);
        xyCoordinates[1]=this.actionCoordinates.get(1);
        this.actionCoordinates.add(-1);
        this.actionCoordinates.add(-1);
        this.actionCoordinates.remove(0);
        this.actionCoordinates.remove(1);
        return xyCoordinates;
    }

    public int getXCoordinate(){
        return x_coordinate;
    }

    public int getYCoordinate(){
        return y_coordinate;
    }

    public void setXCoordinate(int x){
        this.x_coordinate=x;
    }

    public void setYCoordinate(int y){
        this.y_coordinate=y;
    }

    public void setTableUI(TableUI tableUI){
        this.tableUI=tableUI;
    }

    public void setCoordinate(int x_coordinate, int y_coordinate){
        this.x_coordinate=x_coordinate;
        this.y_coordinate=y_coordinate;
    }

    public void setActionCoordinates(int x, int y){
        this.actionCoordinates.add(x);
        this.actionCoordinates.add(y);
    }

    public void clearActionCoordinates(){
        this.actionCoordinates.clear();
    }

    public void move(){
        tableUI.startAction();
    }
}
