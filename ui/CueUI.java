package ui;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javax.swing.JPanel;

import gameobjects.Cushion;
import gameobjects.Pocket;
import gameobjects.PointCushion;
import physics.PolynomialSolver;
import vectormath.Vector3;

public class CueUI extends JPanel{ // TODO: Make this make sense. (Especially with the cue ball position setter stuff)
    private final double CUE_HEIGHT = 420;
    private final int CUE_UPPER_WIDTH = 5;
    private final int CUE_LOWER_WIDTH = 11;
    private final int CUE_BALL_RADIUS = BallUI.BALL_PIXEL_RADIUS;
    private final int SECOND_LINE_LENGTH = CUE_BALL_RADIUS + 20;
    
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
    private TableUI table;
    private ArrayList<BallUI> ballUIs;

    private boolean isActive = true;
    
    public CueUI(TableUI table){
        this.table = table;
        this.ballUIs = table.getBallUIArray();
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
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
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
        double newLength1 = 10000000;
        double newLength2 = 10000000;
        double newLength3 = 10000000;
        double newLength4 = 10000000;
        double targetBallX = 0;
        double targetBallY = 0;
        g2.setColor(Color.BLACK);
        for(BallUI ball : ballUIs){
            if (ball.getNumber() != 0 && !ball.getPocketed()){
                double a = 1;

                double b = 2 * (Math.cos(angle + Math.PI) * (TableUI.getPixelFromMeters(ball.getBallXPosition(), false) + table.getTableFrameX() - cueBallX) + 
                Math.sin(angle + Math.PI) * (TableUI.getPixelFromMeters(ball.getBallYPosition(), true) + table.getTableFrameY() - cueBallY));
            
                double c = Math.pow((TableUI.getPixelFromMeters(ball.getBallXPosition(), false) + table.getTableFrameX() - cueBallX), 2) + 
                Math.pow((TableUI.getPixelFromMeters(ball.getBallYPosition(), true) + table.getTableFrameY() - cueBallY), 2) - 4*Math.pow(CUE_BALL_RADIUS, 2);

                double candidateLength = PolynomialSolver.solveQuadraticEquation(a, b, c);
                if (0 < candidateLength && candidateLength < newLength1 && b*b -4*a*c > 0){
                    newLength1 = candidateLength;
                    targetBallX = TableUI.getPixelFromMeters(ball.getBallXPosition(), false) + table.getTableFrameX();
                    targetBallY = TableUI.getPixelFromMeters(ball.getBallYPosition(), true) + table.getTableFrameY();
                }
            }
        }

        Cushion[] cushions = Cushion.getStandardCushionArray();
        for(Cushion cushion : cushions){
            Vector3 start = cushion.getStart();
            Vector3 end = cushion.getEnd();
            double startX = TableUI.getPixelFromMeters(start.getAxis(0), false) + table.getTableFrameX();
            double endX = TableUI.getPixelFromMeters(end.getAxis(0), false) + table.getTableFrameX();
            double startY = TableUI.getPixelFromMeters(start.getAxis(1), true) + table.getTableFrameY();
            double endY =  TableUI.getPixelFromMeters(end.getAxis(1), true) + table.getTableFrameY();
            if (startX - endX == 0){
                double candidateLength1 = (cueBallX - startX)/Math.cos(angle + Math.PI);
                if (endY < startY){
                    candidateLength1 = candidateLength1 - CUE_BALL_RADIUS/Math.cos(angle + Math.PI);
                    double yCoordinanteCheck = cueBallY - candidateLength1*Math.sin(angle + Math.PI);
                    if (candidateLength1 > 0 && candidateLength1 < newLength2 && yCoordinanteCheck <= Math.max(startY, endY) && yCoordinanteCheck >= Math.min(startY, endY)){
                        newLength2 = candidateLength1;
                    }
                }
                else{
                    candidateLength1 = candidateLength1 + CUE_BALL_RADIUS/Math.cos(angle + Math.PI);
                    double yCoordinanteCheck = cueBallY - candidateLength1*Math.sin(angle + Math.PI);
                    if (candidateLength1 > 0 && candidateLength1 < newLength2 && yCoordinanteCheck <= Math.max(startY, endY) && yCoordinanteCheck >= Math.min(startY, endY)){
                        newLength2 = candidateLength1;
                    }
                }         
            }

            if (startY - endY == 0){
                double candidateLength2 = (cueBallY - startY)/Math.sin(angle + Math.PI);
                if (endX < startX){
                    candidateLength2 = candidateLength2 + CUE_BALL_RADIUS/Math.sin(angle + Math.PI);
                    double xCoordinanteCheck = cueBallX - candidateLength2*Math.cos(angle + Math.PI);
                    if (candidateLength2 > 0 && candidateLength2 < newLength2 && xCoordinanteCheck <= Math.max(startX, endX) && xCoordinanteCheck >= Math.min(startX, endX)){
                        newLength2 = candidateLength2;
                    }
                }
                else{
                    candidateLength2 = candidateLength2 - CUE_BALL_RADIUS/Math.sin(angle + Math.PI);
                    double xCoordinanteCheck = cueBallX - candidateLength2*Math.cos(angle + Math.PI);
                    if (candidateLength2 > 0 && candidateLength2 < newLength2 && xCoordinanteCheck <= Math.max(startX, endX) && xCoordinanteCheck >= Math.min(startX, endX)){
                        newLength2 = candidateLength2;
                    }      
                }       
            }

            else{
                double a = (startY - endY)*Math.cos(angle+ Math.PI) + (endX - startX)*Math.sin(angle + Math.PI);

                double b = (endY - startY)*cueBallX + (startX - endX)*cueBallY + endX*startY - endY*startX;

                if(a != 0){
                    double tempLength = -b/a;
                    if( tempLength > 0 ){
                        double cushionAngle = Math.atan2(endY-startY,endX-startX);
                        tempLength = tempLength - Math.abs(CUE_BALL_RADIUS/Math.sin(angle + Math.PI - cushionAngle));
                        double tempX = cueBallX - tempLength*Math.cos(angle + Math.PI);
                        double tempY = cueBallY - tempLength*Math.sin(angle + Math.PI);
                        double maxX = Math.max(startX,endX);
                        double maxY = Math.max(startY,endY);
                        double minX = Math.min(startX,endX);
                        double minY = Math.min(startY,endY);
                        if(tempX <= maxX && tempX >= minX && tempY <= maxY && tempY >= minY && tempLength < newLength2){
                            newLength2 = tempLength;
                        }
                    }
                }
            }
        }      

        Pocket[] pockets = Pocket.getStandardPocketArray();
        for (Pocket pocket : pockets){
            double pocketX = TableUI.getPixelFromMeters(pocket.getX(), false) + table.getTableFrameX();
            double pocketY = TableUI.getPixelFromMeters(pocket.getY(), true) + table.getTableFrameY();
            double pocketR = TableUI.getPixelFromMeters(pocket.getRadius(), false);
                    
            double a = 1;

            double b = 2 * (Math.cos(angle + Math.PI) * (pocketX - cueBallX) + Math.sin(angle + Math.PI) * (pocketY - cueBallY));
            
            double c = Math.pow((pocketX - cueBallX), 2) + Math.pow((pocketY - cueBallY), 2) - Math.pow(pocketR, 2);

            double candidateLength = PolynomialSolver.solveQuadraticEquation(a, b, c);
            if (0 < candidateLength && candidateLength < newLength3){
                newLength3 = candidateLength;
            }
        }

        PointCushion[] pointCushions = PointCushion.getStandardPointCushionArray();
        for (PointCushion pointCushion : pointCushions){
            double pointCushionX = TableUI.getPixelFromMeters(pointCushion.getX(), false) + table.getTableFrameX();
            double pointCushionY = TableUI.getPixelFromMeters(pointCushion.getY(), true) + table.getTableFrameY();

            double a = 1;

            double b = 2 * (Math.cos(angle + Math.PI) * (pointCushionX - cueBallX) + Math.sin(angle + Math.PI) * (pointCushionY - cueBallY));
            
            double c = Math.pow((pointCushionX - cueBallX), 2) + Math.pow((pointCushionY - cueBallY), 2) - Math.pow(CUE_BALL_RADIUS, 2);

            double candidateLength = PolynomialSolver.solveQuadraticEquation(a, b, c);
            
            if (0 < candidateLength && candidateLength < newLength4){
                newLength4 = candidateLength;
            }
        }

        double newLength = Math.min(Math.min(Math.min(newLength1, newLength2),newLength3),newLength4);
        if(newLength == newLength1){
            helperLinePoint2X = cueBallX - (newLength) * Math.cos(angle + Math.PI);
            helperLinePoint2Y = cueBallY - (newLength) * Math.sin(angle + Math.PI);
            g2.draw(new Line2D.Double(helperLinePoint1X, helperLinePoint1Y, helperLinePoint2X, helperLinePoint2Y));
            g2.draw(new Ellipse2D.Double(helperLinePoint2X - CUE_BALL_RADIUS , helperLinePoint2Y - CUE_BALL_RADIUS, 2*CUE_BALL_RADIUS, 2*CUE_BALL_RADIUS));
            double secondLineAngle = Math.atan2(helperLinePoint2Y - targetBallY, helperLinePoint2X - targetBallX );
            double secondLineEndingX = targetBallX - SECOND_LINE_LENGTH*Math.cos(secondLineAngle);
            double secondLineEndingY = targetBallY - SECOND_LINE_LENGTH*Math.sin(secondLineAngle);
            g2.draw(new Line2D.Double(targetBallX, targetBallY, secondLineEndingX, secondLineEndingY));
        }

        else if (newLength == newLength2){
            helperLinePoint2X = cueBallX - (newLength) * Math.cos(angle + Math.PI);
            helperLinePoint2Y = cueBallY - (newLength) * Math.sin(angle + Math.PI);
            g2.draw(new Line2D.Double(helperLinePoint1X, helperLinePoint1Y, helperLinePoint2X, helperLinePoint2Y));
            g2.draw(new Ellipse2D.Double(helperLinePoint2X - CUE_BALL_RADIUS , helperLinePoint2Y - CUE_BALL_RADIUS, 2*CUE_BALL_RADIUS, 2*CUE_BALL_RADIUS));
        }

        else if(newLength == newLength3){
            helperLinePoint2X = cueBallX - (newLength) * Math.cos(angle + Math.PI);
            helperLinePoint2Y = cueBallY - (newLength) * Math.sin(angle + Math.PI);
            g2.draw(new Line2D.Double(helperLinePoint1X, helperLinePoint1Y, helperLinePoint2X, helperLinePoint2Y));
            g2.draw(new Ellipse2D.Double(helperLinePoint2X - CUE_BALL_RADIUS , helperLinePoint2Y - CUE_BALL_RADIUS, 2*CUE_BALL_RADIUS, 2*CUE_BALL_RADIUS));
        }
        else if(newLength == newLength4){
            helperLinePoint2X = cueBallX - (newLength) * Math.cos(angle + Math.PI);
            helperLinePoint2Y = cueBallY - (newLength) * Math.sin(angle + Math.PI);
            g2.draw(new Line2D.Double(helperLinePoint1X, helperLinePoint1Y, helperLinePoint2X, helperLinePoint2Y));
            g2.draw(new Ellipse2D.Double(helperLinePoint2X - CUE_BALL_RADIUS , helperLinePoint2Y - CUE_BALL_RADIUS, 2*CUE_BALL_RADIUS, 2*CUE_BALL_RADIUS));
        }
        else{
            g2.draw(new Line2D.Double(helperLinePoint1X, helperLinePoint1Y, helperLinePoint2X, helperLinePoint2Y));
        }

        g2.dispose();
    }
}
