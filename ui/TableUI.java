package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import gameobjects.Ball;
import gameobjects.Table;
import physics.Physics;

public class TableUI extends JPanel implements ActionListener{
    private Timer timer;
    private Table table;
    private BallUI[] ballUIs;

    private static final double TABLE_WIDTH_METERS = 2.7432 / 2;
    private static final double TABLE_HEIGHT_METERS = 2.7432;
    private static final int TABLE_WIDTH_PIXELS = 336;
    private static final int TABLE_HEIGHT_PIXELS = 672;

    public TableUI(Table table, BallUI... ballUIs){
        this.table = table;
        this.ballUIs = ballUIs;
        this.setBackground(new Color(0, 170, 0));
        this.setBounds(57, 57, TABLE_WIDTH_PIXELS, TABLE_HEIGHT_PIXELS);
        this.setPreferredSize(new Dimension(TABLE_WIDTH_PIXELS, TABLE_HEIGHT_PIXELS));
        this.setLayout(null);
        timer = new Timer(33,this);
    }

    public void startAction(){
        timer.start();
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D graphics=(Graphics2D)g;
        for(BallUI ball : ballUIs){
            if(ball.isVisible())
                graphics.drawImage(ball.getImage(), ball.getXPixel(TABLE_WIDTH_METERS, TABLE_WIDTH_PIXELS), 
                                    ball.getYPixel(TABLE_HEIGHT_METERS, TABLE_HEIGHT_PIXELS), null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        // Tentative, this for loop will be implemented in Table class.
        for(Ball ball : table.getBallArray()){
            Physics.evolveBallMotion(ball, 0.033); 
        }

        repaint();
    }
}
