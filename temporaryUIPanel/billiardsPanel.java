package temporaryUIPanel;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class BilliardsPanel extends JPanel implements ActionListener{

    WhiteBall whiteBall = new WhiteBall();
    Timer timer;
    Image imageBall;
    int xMoveDistance=10;
    int yMoveDistance=10;
    int x=213;
    int y=568;
    //In order to smoothen the fraction
    int count=1;

    public BilliardsPanel()
    {
        this.setBackground(new Color(0, 170, 0));
        this.setBounds(0, 0, Main.TABLE_WIDTH, Main.TABLE_HEIGHT);
        this.setPreferredSize(new Dimension(Main.TABLE_WIDTH, Main.TABLE_HEIGHT));
        this.setLayout(null);
        imageBall=whiteBall.getImage();
        //Delay in miliseconds
        timer=new Timer(50,this);
        timer.start();
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D graphics=(Graphics2D)g;
        graphics.drawImage(imageBall, x, y, null);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        //Actions will be simulated in this area
        count++;
        y=y-yMoveDistance;
        x=x-xMoveDistance;
        if(yMoveDistance>0 && count%2==1)
        {
            yMoveDistance--;
        }
        if(xMoveDistance>0 && count%2==1)
        {
            xMoveDistance--;
        }
        repaint();
        /*
        Variables
        -Timer
        -Move Distances
        -Count
        */
    }
}
