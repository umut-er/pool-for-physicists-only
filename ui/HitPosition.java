package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class HitPosition extends JPanel implements MouseListener{
    private static int xValue = 50;
    private static int yValue = 50;

    public HitPosition(){
        this.setBackground(Color.DARK_GRAY);
        this.setFocusable(false);
        this.setOpaque(true);
        addMouseListener(this);
        // this.setBackground(new Color(255, 170, 0));
    }

    //setters
    public void setValueOfX(int newValueOfX) {xValue = newValueOfX;}
    public void setValueOfY(int newValueOfY) {yValue = newValueOfY;}
   
    @Override
    public void mouseClicked(MouseEvent e) {
        xValue = e.getX();
        yValue = e.getY();
        if(Math.pow(xValue - this.getWidth()/2 , 2) + Math.pow(yValue - this.getHeight()/2, 2) <= Math.pow(this.getWidth()/2, 2)){
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent e){}
    @Override
    public void mouseReleased(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawOval(0,0,this.getWidth(),this.getHeight());
        g.setColor(Color.WHITE);
        g.fillOval(0,0,this.getWidth(),this.getHeight());
        if(xValue > -1 && yValue > -1){
            g.setColor(Color.RED);
            g.fillOval(xValue -this.getWidth()/ 20, yValue - this.getHeight()/ 20, this.getWidth()/10, this.getHeight()/10);
        }  
    }
    
    public static double getXPos(){
        return (xValue - 50) / 50.;
    }

    public static double getYPos(){
        return (yValue - 50) / 50.;
    }
}
