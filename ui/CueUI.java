package ui;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.JPanel;

import vectormath.Vector3;

public class CueUI extends JPanel{
    private final double CUE_HEIGHT = 420;
    private final int CUE_UPPER_WIDTH = 5;
    private final int CUE_LOWER_WIDTH = 11;
    private final int CUE_BALL_RADIUS = BallUI.BALL_PIXEL_RADIUS;
    
    private int cueBallX;
    private int cueBallY;
    private int mouseX;
    private int mouseY;
    private int helperLineLength = 100;  //this means that length of the helper line will be 20*sqrt(2)
    private double shotDistance = 5;
    private double visibleShotDistance;
    private double visibleHeight;
    private double visibleLowerWidth;
    private double visibleBlueTipHeight;

    private boolean isActive = true;
    
    CueUI(){
        visibleHeight = CUE_HEIGHT;
        visibleLowerWidth = CUE_LOWER_WIDTH;
        visibleBlueTipHeight = CUE_UPPER_WIDTH;
        visibleShotDistance = shotDistance;
    }

    //getters
    public int getCueUpperWidth() {return this.CUE_UPPER_WIDTH;}
    public int getCueLowerWidth() {return this.CUE_LOWER_WIDTH;}
    public int getCueBallRadius() {return this.CUE_BALL_RADIUS;}
    public int getMouseX() {return this.mouseX;}
    public int getMouseY() {return this.mouseY;}
    public int getCueBallX() {return this.cueBallX;}
    public int getCueBallY() {return this.cueBallY;}
    public boolean getActive() {return isActive;}
    public double getCueStickHeight() {return this.CUE_HEIGHT;}
    public double getShotDistance() {return this.shotDistance;}
    public double getVisibleShotDistance() {return this.visibleShotDistance;}
    public double getVisibleHeight() {return visibleHeight;}
    public double getVisibleLowerWidth() {return visibleLowerWidth;}
    public double getVisibleBlueTipHeight() {return visibleBlueTipHeight;}

    public double getAngle(){
        Vector3 direction = new Vector3(cueBallX - mouseX, -(cueBallY - mouseY), 0);
        return Vector3.getSignedAngle2D(new Vector3(0, -1, 0), direction);
    }

    //setters
    public void setShotDistance(double distance) {this.shotDistance = distance;}
    public void setMouseX(int newMouseX) {this.mouseX = newMouseX;}
    public void setMouseY(int newMouseY) {this.mouseY = newMouseY;}
    public void setCueBallX(int newCueBallX) {this.cueBallX = newCueBallX;}
    public void setCueBallY(int newCueBallY) {this.cueBallY = newCueBallY;}
    public void setActive(boolean active){
        isActive = active;
        PoolPanel.cueIsFixed = false;
    }
    public void setVisibleHeight(double newHeight) {visibleHeight = newHeight;}
    public void setVisibleLowerWidth(double newWidth) {visibleLowerWidth = newWidth;}
    public void setVisibleBlueTipHeight(double newHeight) {visibleBlueTipHeight = newHeight;}
    public void setVisibleShotDistance(double Angle) {visibleShotDistance = shotDistance * Math.cos(Angle);}
  
    @Override
    public void paintComponent(Graphics g){
        if(!isActive)
            return;

        //the angle of the line that passes through mouse cursor and cueBall center 
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        double angle = Math.atan2(this.cueBallY - this.mouseY, this.cueBallX - this.mouseX );
        
        //central points of upper and lower edges 
        double upperEdgeCenterX = cueBallX - (CUE_BALL_RADIUS + visibleShotDistance) * Math.cos(angle);
        double upperEdgeCenterY = cueBallY - (CUE_BALL_RADIUS + visibleShotDistance) * Math.sin(angle);
        double lowerEdgeCenterX = cueBallX - (CUE_BALL_RADIUS + visibleShotDistance + visibleHeight) * Math.cos(angle);
        double lowerEdgeCenterY = cueBallY - (CUE_BALL_RADIUS + visibleShotDistance + visibleHeight) * Math.sin(angle);
        
        //coordinates of the blue tip of the cue
        double blueTipPoint1X = upperEdgeCenterX + CUE_UPPER_WIDTH/2 * Math.sin(angle);
        double blueTipPoint1Y = upperEdgeCenterY - CUE_UPPER_WIDTH/2 * Math.cos(angle);
        double blueTipPoint2X = upperEdgeCenterX - CUE_UPPER_WIDTH/2 * Math.sin(angle);
        double blueTipPoint2Y = upperEdgeCenterY + CUE_UPPER_WIDTH/2 * Math.cos(angle);
        
        double blueTipPoint3X = upperEdgeCenterX - visibleBlueTipHeight*Math.cos(angle) - ((visibleHeight - visibleBlueTipHeight) * CUE_UPPER_WIDTH + visibleLowerWidth * visibleBlueTipHeight)/2/visibleHeight * Math.sin(angle);
        double blueTipPoint3Y = upperEdgeCenterY - visibleBlueTipHeight*Math.sin(angle) + ((visibleHeight - visibleBlueTipHeight) * CUE_UPPER_WIDTH + visibleLowerWidth * visibleBlueTipHeight)/2/visibleHeight * Math.cos(angle);
        double blueTipPoint4X = upperEdgeCenterX - visibleBlueTipHeight*Math.cos(angle) + ((visibleHeight - visibleBlueTipHeight) * CUE_UPPER_WIDTH + visibleLowerWidth * visibleBlueTipHeight)/2/visibleHeight * Math.sin(angle);
        double blueTipPoint4Y = upperEdgeCenterY - visibleBlueTipHeight*Math.sin(angle) - ((visibleHeight - visibleBlueTipHeight) * CUE_UPPER_WIDTH + visibleLowerWidth * visibleBlueTipHeight)/2/visibleHeight * Math.cos(angle);
        
        //coordinates of the middle part of the cue
        double middlePartPoint1X = blueTipPoint4X;
        double middlePartPoint1Y = blueTipPoint4Y;
        double middlePartPoint2X = blueTipPoint3X;
        double middlePartPoint2Y = blueTipPoint3Y;
    
        double middlePartPoint3X = upperEdgeCenterX - visibleHeight/2 * Math.cos(angle) - (CUE_UPPER_WIDTH + visibleLowerWidth)/4 * Math.sin(angle);
        double middlePartPoint3Y = upperEdgeCenterY - visibleHeight/2 * Math.sin(angle) + (CUE_UPPER_WIDTH + visibleLowerWidth)/4 * Math.cos(angle);
        double middlePartPoint4X = upperEdgeCenterX - visibleHeight/2 * Math.cos(angle) + (CUE_UPPER_WIDTH + visibleLowerWidth)/4 * Math.sin(angle);
        double middlePartPoint4Y = upperEdgeCenterY - visibleHeight/2 * Math.sin(angle) - (CUE_UPPER_WIDTH + visibleLowerWidth)/4 * Math.cos(angle);
        
        //coordinates of the lower part of the cue
        double lowerPartPoint1X = middlePartPoint4X;
        double lowerPartPoint1Y = middlePartPoint4Y;
        double lowerPartPoint2X = middlePartPoint3X;
        double lowerPartPoint2Y = middlePartPoint3Y;
    
        double lowerPartPoint3X = lowerEdgeCenterX - visibleLowerWidth/2 * Math.sin(angle);
        double lowerPartPoint3Y = lowerEdgeCenterY + visibleLowerWidth/2 * Math.cos(angle);
        double lowerPartPoint4X = lowerEdgeCenterX + visibleLowerWidth/2 * Math.sin(angle);
        double lowerPartPoint4Y = lowerEdgeCenterY - visibleLowerWidth/2 * Math.cos(angle);

        //coordinates of helper line
        double helperLinePoint1X = cueBallX; 
        double helperLinePoint1Y = cueBallY;
        double helperLinePoint2X = cueBallX - (CUE_BALL_RADIUS + helperLineLength) * Math.cos(angle + Math.PI);
        double helperLinePoint2Y = cueBallY - (CUE_BALL_RADIUS + helperLineLength) * Math.sin(angle + Math.PI);
    
        //draw blue tip
        Path2D.Double path1 = new Path2D.Double();
        path1.moveTo(blueTipPoint1X, blueTipPoint1Y);
        path1.lineTo(blueTipPoint2X, blueTipPoint2Y);
        path1.lineTo(blueTipPoint3X, blueTipPoint3Y);
        path1.lineTo(blueTipPoint4X, blueTipPoint4Y);
        path1.closePath();
        g2.setColor(Color.BLUE);
        g2.fill(path1);

        //draw middle part
        Path2D.Double path2 = new Path2D.Double();
        path2.moveTo(middlePartPoint1X, middlePartPoint1Y);
        path2.lineTo(middlePartPoint2X, middlePartPoint2Y);
        path2.lineTo(middlePartPoint3X, middlePartPoint3Y);
        path2.lineTo(middlePartPoint4X, middlePartPoint4Y);
        path2.closePath();
        g2.setColor(new Color(102, 51, 0));
        g2.fill(path2);

        //draw lower part
        Path2D.Double path3 = new Path2D.Double();
        path3.moveTo(lowerPartPoint1X, lowerPartPoint1Y);
        path3.lineTo(lowerPartPoint2X, lowerPartPoint2Y);
        path3.lineTo(lowerPartPoint3X, lowerPartPoint3Y);
        path3.lineTo(lowerPartPoint4X, lowerPartPoint4Y);
        path3.closePath();
        g2.setColor(Color.BLACK);
        g2.fill(path3);

        //draw helper line
        g2.setColor(Color.BLACK);
        g2.draw(new Line2D.Double(helperLinePoint1X, helperLinePoint1Y, helperLinePoint2X, helperLinePoint2Y));


        g2.dispose();
    }
}
