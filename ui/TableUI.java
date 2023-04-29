package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import gameobjects.Cushion;
import gameobjects.Table;

public class TableUI extends JPanel implements ActionListener{
    private Timer timer;
    private Table table;
    private BallUI[] ballUIs;

    private static final double TABLE_WIDTH_METERS = 2.7432;
    private static final double TABLE_HEIGHT_METERS = 1.3716;
    private static final int TABLE_WIDTH_PIXELS = 672;
    private static final int TABLE_HEIGHT_PIXELS = 336;

    private static final int UPDATION_INTERVAL = 5;

    public TableUI(Table table, BallUI... ballUIs){
        this.table = table;
        this.ballUIs = ballUIs;
        this.setBackground(new Color(0, 170, 0));
        // setLayout(null);
        // this.setPreferredSize(new Dimension(TABLE_WIDTH_PIXELS, TABLE_HEIGHT_PIXELS));
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

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D graphics=(Graphics2D)g;
        for(BallUI ball : ballUIs){
            graphics.setColor(new Color(255, 255, 255));
            graphics.fillOval(getPixelFromMeters(ball.getBallXPosition(), false) - ball.getBallPixelRadius(), 
                            getPixelFromMeters(ball.getBallYPosition(), true) - ball.getBallPixelRadius(),
                            2 * ball.getBallPixelRadius(), 2 * ball.getBallPixelRadius());
        }
        graphics.setColor(new Color(0, 0, 0));
        graphics.setStroke(new BasicStroke(5));
        for(Cushion cushion : table.getCushionArray()){
            graphics.drawLine(getPixelFromMeters(cushion.getStart().getAxis(0), false), 
                            getPixelFromMeters(cushion.getStart().getAxis(1), true),
                            getPixelFromMeters(cushion.getEnd().getAxis(0), false),
                            getPixelFromMeters(cushion.getEnd().getAxis(1), true));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        table.evolveTable(UPDATION_INTERVAL / 1000.);
        repaint();
    }
}
