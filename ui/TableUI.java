package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;
import gameobjects.Table;

public class TableUI extends JPanel implements ActionListener{
    private Timer timer;
    private Table table;
    private ArrayList<BallUI> ballsOnTableUI;
    // 1 cm = 3 pixels
    // A classic 8-ball pool table is 112 cm x 224 cm (inner rectangle)
    final public static int TABLE_WIDTH=336;
    final public static int TABLE_HEIGHT=672;

    public TableUI(){
        ballsOnTableUI=new ArrayList<BallUI>();
        //Adding balls////////////////////////////////////////////////
        BallUI firstBall=new BallUI();
        this.addBall(firstBall);
        //////////////////////////////////////////////////////////////
        this.setBackground(new Color(0, 170, 0));
        //According to official pool sizes
        this.setBounds(57, 57, TABLE_WIDTH, TABLE_HEIGHT);
        this.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        this.setLayout(null);
        //Change of frame in miliseconds
        timer=new Timer(50,this);
    }

    public void addBall(BallUI ballUI){
        ballsOnTableUI.add(ballUI);
        ballUI.setTableUI(this);
    }

    public void startAction(){
        timer.start();
    }

    public ArrayList<BallUI> getBallsOnTable()
    {
        return ballsOnTableUI;
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D graphics=(Graphics2D)g;
        for(BallUI ball:ballsOnTableUI)
        {
            graphics.drawImage(ball.getImage(), ball.getXCoordinate(), ball.getYCoordinate(), null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        boolean check=false;
        for(BallUI ball:ballsOnTableUI)
        {
            int[] coordinates=ball.getFrameActionCoordinates();
            boolean bothNull=false;
            for(int i:coordinates)
            {
                if(i==-1)
                {
                    bothNull=true;
                }
            }
            if(!bothNull)
            {
                ball.setXCoordinate(coordinates[0]);
                ball.setYCoordinate(coordinates[1]);
            }
            else
            {
                ball.clearActionCoordinates();
                check=true;
                timer.stop();   
            }
        }
        if(!check)
        {
            repaint();
        }
        // Continue / Fix the problem
    }
}
