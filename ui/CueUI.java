package ui;

import java.awt.*;
import java.awt.geom.*;

public class CueUI {
    private final double CUE_HEIGHT = 420;
    private final double CUE_UPPER_WIDTH = 5;
    private final double CUE_LOWER_WIDTH = 11;
    private final double CUE_BALL_RADIUS = BallUI.BALL_PIXEL_RADIUS;
    
    private double cueBallX;
    private double cueBallY;
    private double mouseX;
    private double mouseY;
    private double shotDistance = 5;
    
    CueUI(){
    }

    //getters
    public double getCueStickHeight() {return this.CUE_HEIGHT;}
    public double getCueUpperWidth() {return this.CUE_UPPER_WIDTH;}
    public double getCueLowerWidth() {return this.CUE_LOWER_WIDTH;}
    public double getCueBallRadius() {return this.CUE_BALL_RADIUS;}
    public double getShotDistance() {return this.shotDistance;}
    public double getMouseX() {return this.mouseX;}
    public double getMouseY() {return this.mouseY;}
    public double getCueBallX() {return this.cueBallX;}
    public double getCueBallY() {return this.cueBallY;}


    //setters
    public void setShotDistance(double distance) {this.shotDistance = distance;}
    public void setMouseX(double newMouseX) {this.mouseX = newMouseX;}
    public void setMouseY(double newMouseY) {this.mouseY = newMouseY;}
    public void setCueBallX(double newCueBallX) {this.cueBallX = newCueBallX;}
    public void setCueBallY(double newCueBallY) {this.cueBallY = newCueBallY;}
  

    public void draw(Graphics2D g2){
        //the angle of the line that passes through mouse cursor and cueBall center 
        double angle = Math.atan2(this.cueBallY - this.mouseY, this.cueBallX - this.mouseX );
        
        //central points of upper and lower edges 
        double upperEdgeCenterX = cueBallX - (CUE_BALL_RADIUS + shotDistance) * Math.cos(angle);
        double upperEdgeCenterY = cueBallY - (CUE_BALL_RADIUS + shotDistance) * Math.sin(angle);
        double lowerEdgeCenterX = cueBallX - (CUE_BALL_RADIUS + shotDistance + CUE_HEIGHT) * Math.cos(angle);
        double lowerEdgeCenterY = cueBallY - (CUE_BALL_RADIUS + shotDistance + CUE_HEIGHT) * Math.sin(angle);
        
        //coordinates of the blue tip of the cue
        double blueTipPoint1X = upperEdgeCenterX + CUE_UPPER_WIDTH/2 * Math.sin(angle);
        double blueTipPoint1Y = upperEdgeCenterY - CUE_UPPER_WIDTH/2 * Math.cos(angle);
        double blueTipPoint2X = upperEdgeCenterX - CUE_UPPER_WIDTH/2 * Math.sin(angle);
        double blueTipPoint2Y = upperEdgeCenterY + CUE_UPPER_WIDTH/2 * Math.cos(angle);
        
        double blueTipPoint3X = upperEdgeCenterX - CUE_UPPER_WIDTH*Math.cos(angle) - (CUE_HEIGHT - CUE_UPPER_WIDTH + CUE_LOWER_WIDTH) * CUE_UPPER_WIDTH/2/CUE_HEIGHT * Math.sin(angle);
        double blueTipPoint3Y = upperEdgeCenterY - CUE_UPPER_WIDTH*Math.sin(angle) + (CUE_HEIGHT - CUE_UPPER_WIDTH + CUE_LOWER_WIDTH) * CUE_UPPER_WIDTH/2/CUE_HEIGHT * Math.cos(angle);
        double blueTipPoint4X = upperEdgeCenterX - CUE_UPPER_WIDTH*Math.cos(angle) + (CUE_HEIGHT - CUE_UPPER_WIDTH + CUE_LOWER_WIDTH) * CUE_UPPER_WIDTH/2/CUE_HEIGHT * Math.sin(angle);
        double blueTipPoint4Y = upperEdgeCenterY - CUE_UPPER_WIDTH*Math.sin(angle) - (CUE_HEIGHT - CUE_UPPER_WIDTH + CUE_LOWER_WIDTH) * CUE_UPPER_WIDTH/2/CUE_HEIGHT * Math.cos(angle);
        
        //coordinates of the middle part of the cue
        double middlePartPoint1X = blueTipPoint4X;
        double middlePartPoint1Y = blueTipPoint4Y;
        double middlePartPoint2X = blueTipPoint3X;
        double middlePartPoint2Y = blueTipPoint3Y;
    
        double middlePartPoint3X = upperEdgeCenterX - CUE_HEIGHT/2 * Math.cos(angle) - (CUE_UPPER_WIDTH + CUE_LOWER_WIDTH)/4 * Math.sin(angle);
        double middlePartPoint3Y = upperEdgeCenterY - CUE_HEIGHT/2 * Math.sin(angle) + (CUE_UPPER_WIDTH + CUE_LOWER_WIDTH)/4 * Math.cos(angle);
        double middlePartPoint4X = upperEdgeCenterX - CUE_HEIGHT/2 * Math.cos(angle) + (CUE_UPPER_WIDTH + CUE_LOWER_WIDTH)/4 * Math.sin(angle);
        double middlePartPoint4Y = upperEdgeCenterY - CUE_HEIGHT/2 * Math.sin(angle) - (CUE_UPPER_WIDTH + CUE_LOWER_WIDTH)/4 * Math.cos(angle);
        
        //coordinates of the lower part of the cue
        double lowerPartPoint1X = middlePartPoint4X;
        double lowerPartPoint1Y = middlePartPoint4Y;
        double lowerPartPoint2X = middlePartPoint3X;
        double lowerPartPoint2Y = middlePartPoint3Y;
    
        double lowerPartPoint3X = lowerEdgeCenterX - CUE_LOWER_WIDTH/2 * Math.sin(angle);
        double lowerPartPoint3Y = lowerEdgeCenterY + CUE_LOWER_WIDTH/2 * Math.cos(angle);
        double lowerPartPoint4X = lowerEdgeCenterX + CUE_LOWER_WIDTH/2 * Math.sin(angle);
        double lowerPartPoint4Y = lowerEdgeCenterY - CUE_LOWER_WIDTH/2 * Math.cos(angle);
    
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

        g2.dispose();
    }
}