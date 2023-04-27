package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import gameobjects.Table;

public class TableUI extends JPanel implements ActionListener{
    private Timer timer;
    private Table table;
    private BallUI[] ballUIs;

    private static final double TABLE_WIDTH_METERS = 2.7432;
    private static final double TABLE_HEIGHT_METERS = 2.7432 / 2;
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
            // graphics.drawImage(ball.getImage(), ball.getXPixel(TABLE_WIDTH_METERS, TABLE_WIDTH_PIXELS) - ball.getBallPixelRadius(), 
            //                     ball.getYPixel(TABLE_HEIGHT_METERS, TABLE_HEIGHT_PIXELS) - ball.getBallPixelRadius(), null);
            graphics.setColor(new Color(255, 255, 255));
            graphics.fillOval(ball.getXPixel(TABLE_WIDTH_METERS, TABLE_WIDTH_PIXELS) - ball.getBallPixelRadius(), 
                            ball.getYPixel(TABLE_HEIGHT_METERS, TABLE_HEIGHT_PIXELS) - ball.getBallPixelRadius(),
                            2 * ball.getBallPixelRadius(), 2 * ball.getBallPixelRadius());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        table.evolveTable(UPDATION_INTERVAL / 1000.);
        repaint();
    }
}
