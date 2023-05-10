package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import gameobjects.Cushion;
import gameobjects.Table;

public class TableUI extends JPanel implements ActionListener{
    private Timer timer;
    private Table table;
    private ArrayList<BallUI> ballUIs;
    private Image tableImage;

    private static final double TABLE_WIDTH_METERS = 3.0534;
    private static final double TABLE_HEIGHT_METERS = 1.6818;
    private static final int TABLE_WIDTH_PIXELS = 748;
    private static final int TABLE_HEIGHT_PIXELS = 412;

    private static final int UPDATION_INTERVAL = 5;

    public TableUI(String imageFileName, Table table, ArrayList<BallUI> ballUIs){
        changeTableImage(imageFileName);
        this.table = table;
        this.ballUIs = ballUIs;
        timer = new Timer(UPDATION_INTERVAL,this);
    }

    /**
     * Returns the pixel location from the location measured in meters.
     * @param meters Location in meters
     * @param axis false -> x, true -> y
     * @return An int, the pixel location of object.
     */
    private static int getPixelFromMeters(double meters, boolean axis){
        if(axis)
            return (int)((TABLE_HEIGHT_METERS -  meters) / TABLE_HEIGHT_METERS * TABLE_HEIGHT_PIXELS);
        return (int)(meters / TABLE_WIDTH_METERS * TABLE_WIDTH_PIXELS);    
    }

    public static double getTableWidthMeters(){
        return TABLE_WIDTH_METERS;
    }

    public static double getTableHeightMeters(){
        return TABLE_HEIGHT_METERS;
    }

    public static int getTableWidthPixels(){
        return TABLE_WIDTH_PIXELS;
    }

    public static int getTableHeightPixels(){
        return TABLE_HEIGHT_PIXELS;
    }

    public void startAction(){
        timer.start();
    }

    public void changeTableImage(String imageFileName){
        BufferedImage bufferedTableImage;
        try{
            bufferedTableImage=ImageIO.read(new File("./ui/assets", imageFileName));
            this.tableImage= bufferedTableImage.getScaledInstance(TABLE_WIDTH_PIXELS, TABLE_HEIGHT_PIXELS, Image.SCALE_DEFAULT);
        } 
        catch(IOException e){
            this.tableImage=null;
        }
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D graphics=(Graphics2D)g;
        graphics.drawImage(tableImage, 0, 0, TABLE_WIDTH_PIXELS, TABLE_HEIGHT_PIXELS, null);

        graphics.setColor(Color.BLACK);
        for(Cushion cushion : table.getCushionArray()){
            graphics.drawLine(getPixelFromMeters(cushion.getStart().getAxis(0), false), getPixelFromMeters(cushion.getStart().getAxis(1), true), 
                                getPixelFromMeters(cushion.getEnd().getAxis(0), false), getPixelFromMeters(cushion.getEnd().getAxis(1), true));
        }

        for(BallUI ball : ballUIs){
            // graphics.setColor(new Color(255, 255, 255));
            graphics.setColor(ball.getColor());
            graphics.fillOval(getPixelFromMeters(ball.getBallXPosition(), false) - ball.getBallPixelRadius(), 
                            getPixelFromMeters(ball.getBallYPosition(), true) - ball.getBallPixelRadius(),
                            2 * ball.getBallPixelRadius(), 2 * ball.getBallPixelRadius());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        int idx = table.evolveTable(UPDATION_INTERVAL / 1000.);
        if(idx == -2){
            timer.stop();
        }
        if(idx >= 0){
            ballUIs.remove(idx);
        }
        repaint();
    }
}
