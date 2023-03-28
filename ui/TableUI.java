package ui;

import java.awt.Color;
import java.awt.Dimension;
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
    // 1 cm = 3 pixels
    // A classic 8-ball pool table is 112 cm x 224 cm (inner rectangle)
    final public static int TABLE_WIDTH=336;
    final public static int TABLE_HEIGHT=672;

    public TableUI(){
        this.setBackground(new Color(0, 170, 0));
        //According to official pool sizes
        this.setBounds(57, 57, TABLE_WIDTH, TABLE_HEIGHT);
        this.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        this.setLayout(null);
        //Change of frame in miliseconds
        timer=new Timer(50,this);
        //Starting the timer for simulating movement when it is needed
        timer.start();
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D graphics=(Graphics2D)g;
        //Balls will be initiated here, using the ballsInPlay array
        //graphics.drawImage(randomBall.getImage(), starting x coordinate, starting y coordinate, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        //Repainting and moving to the next frame:
        repaint();
    }
}
