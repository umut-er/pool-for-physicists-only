package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class HitPosition extends JPanel implements MouseListener{
    private static int xValue = -1;
    private static int yValue = -1;

   public HitPosition()
   {
        this.setFocusable(true);
        addMouseListener(this);
        // this.setBackground(new Color(255, 170, 0));
   }
   
    @Override
    public void mouseClicked(MouseEvent e) {
        xValue = e.getX();
        yValue = e.getY();
        System.out.println(xValue);
        System.out.println(yValue);
        if(Math.pow(xValue - this.getWidth()/2 , 2) + Math.pow(yValue - this.getHeight()/2, 2) <= Math.pow(this.getWidth()/2, 2)) 
        {
            repaint();
        }
        
       
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawOval(0,0,this.getWidth(),this.getHeight());
        g.setColor(Color.LIGHT_GRAY);
        g.fillOval(0,0,this.getWidth(),this.getHeight());
        if(xValue > -1 && yValue > -1){
            g.setColor(Color.RED);
            g.fillOval(xValue -this.getWidth()/ 20, yValue - this.getHeight()/ 20, this.getWidth()/10, this.getHeight()/10);
        }
        
    }
    
    public static int getXPos()
    {
        return xValue;
    }

    public static int getYPos()
    {
        return yValue;
    }

    
}
