package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import gameobjects.Ball;
import gameobjects.Cushion;
import gameobjects.Pocket;
import gameobjects.Table;
import physics.Physics;
import vectormath.Vector3;

public class TableUI extends JPanel implements ActionListener{
    private PoolPanel mainPanel;
    private Timer timer;
    private Table table;
    private ArrayList<BallUI> ballUIs;
    private Image tableImage;
    private BallPlacement bp = new BallPlacement();

    private boolean cueBallDrag = false;

    private static final double TABLE_WIDTH_METERS = 3.0534;
    private static final double TABLE_HEIGHT_METERS = 1.6818;
    private static final int TABLE_WIDTH_PIXELS = 748;
    private static final int TABLE_HEIGHT_PIXELS = 412;
    public static final int UPDATION_INTERVAL = 33;

    private static final int leftCushion = 38;
    private static final int rightCushion = 710;
    private static final int topCushion = 374;
    private static final int bottomCushion = 38;

    private boolean numbersOn = true;
    private int cueBallX;
    private int cueBallY;

    public TableUI(String imageFileName, Table table, ArrayList<BallUI> ballUIs, PoolPanel mainPanel){
        this.mainPanel = mainPanel;
        changeTableImage(imageFileName);
        this.table = table;
        this.ballUIs = ballUIs;
        timer = new Timer(UPDATION_INTERVAL,this);  
    }

    /**
     * Returns the pixel location, (0, 0) being the top left corner, from the location measured in meters.
     * @param meters Location in meters
     * @param axis false -> x, true -> y
     * @return An int, the pixel location of object.
     */
    private static int getPixelFromMeters(double meters, boolean axis){
        if(axis)
            return (int)((TABLE_HEIGHT_METERS -  meters) / TABLE_HEIGHT_METERS * TABLE_HEIGHT_PIXELS);
        return (int)(meters / TABLE_WIDTH_METERS * TABLE_WIDTH_PIXELS);    
    }
    
    // this is the reverse method of getPixelFromMeters
    private static double getMetersFromPixels(int pixels, boolean axis)
    {
        if(axis)
            return (TABLE_HEIGHT_PIXELS - pixels) * TABLE_HEIGHT_METERS / TABLE_HEIGHT_PIXELS ;
        return pixels * TABLE_WIDTH_METERS / TABLE_WIDTH_PIXELS;
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

    public int getCueBallXPixels(){
        return getPixelFromMeters(ballUIs.get(0).getBallXPosition(), false);
    }

    public int getCueBallYPixels(){
        return getPixelFromMeters(ballUIs.get(0).getBallYPosition(), true);
    }

    public Table getTable(){
        return this.table;
    }

    public void startAction(){
        getTable().resetTurn();
        mainPanel.disableHitButton();
        numbersOn = false;
        mainPanel.disableCue();
        firePropertyChange("turn start", 0, 1);
        timer.start();
    }

    public void stopAction(){
        numbersOn = true;
        timer.stop();
        firePropertyChange("turn over", 0, 1);
    }

    public void hitBall(double cueSpeed, double directionAngle, double elevationAngle, double horizontalSpin, double verticalSpin){
        Physics.hitBall(table.getBallArray().get(0), cueSpeed, directionAngle, elevationAngle, horizontalSpin, verticalSpin);
        startAction();
    }

    // Part of aiming aid
    public Pocket getOptimalPocket(){
        Ball correctBall = table.getLowestNumberedBall();
        Pocket closestPocket = null;
        double dist = 5;
        Vector3 fullHitVector = Vector3.subtract(correctBall.getDisplacement(), table.getBallArray().get(0).getDisplacement());
        for(Pocket p : table.getPocketArray()){
            Vector3 towardsPocket = Vector3.subtract(p.getPosition(), correctBall.getDisplacement());
            if(Math.abs(Vector3.getSignedAngle2D(fullHitVector, towardsPocket)) >= Math.PI / 3)
                continue;
            double cur_dist = towardsPocket.getVectorLength();
            if(cur_dist < dist){
                dist = cur_dist;
                closestPocket = p;
            }
        }
        return closestPocket;
    }

    // Part of aiming aid.
    public Vector3 getOptimalPosition(){
        Ball correctBall = table.getLowestNumberedBall();
        Pocket closestPocket = getOptimalPocket();
        if(closestPocket == null)
            return null;
        Vector3 towardsPocket = Vector3.subtract(closestPocket.getPosition(), correctBall.getDisplacement());
        towardsPocket.normalize();
        towardsPocket.inPlaceMultiply(-2 * Ball.RADIUS);
        return Vector3.add(correctBall.getDisplacement(), towardsPocket);
    }

    public ArrayList<BallUI> getBallUIArray(){
        return this.ballUIs;
    }

    public void changeTableImage(String imageFileName){
        BufferedImage bufferedTableImage;
        try{
            bufferedTableImage=ImageIO.read(new File("./ui/assets", imageFileName));
            this.tableImage= bufferedTableImage.getScaledInstance(TABLE_WIDTH_PIXELS, TABLE_HEIGHT_PIXELS, Image.SCALE_DEFAULT);
        } 
        catch(IOException e){
            this.tableImage=null;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D graphics=(Graphics2D)g;
        graphics.drawImage(tableImage, 0, 0, TABLE_WIDTH_PIXELS, TABLE_HEIGHT_PIXELS, null);

        graphics.setColor(Color.BLACK);
        for(Cushion cushion : table.getCushionArray()){
            graphics.drawLine(getPixelFromMeters(cushion.getStart().getAxis(0), false), getPixelFromMeters(cushion.getStart().getAxis(1), true), 
                                getPixelFromMeters(cushion.getEnd().getAxis(0), false), getPixelFromMeters(cushion.getEnd().getAxis(1), true));
        }

        for(BallUI ball : ballUIs){
            graphics.setColor(ball.getColor());
            graphics.fillOval(getPixelFromMeters(ball.getBallXPosition(), false) - BallUI.BALL_PIXEL_RADIUS, 
                            getPixelFromMeters(ball.getBallYPosition(), true) - BallUI.BALL_PIXEL_RADIUS,
                            2 * BallUI.BALL_PIXEL_RADIUS, 2 * BallUI.BALL_PIXEL_RADIUS);
        }

        if(numbersOn){
            graphics.setColor(new Color(134, 74, 154));
            for(BallUI ball : ballUIs){
                if(ball.getBall().getNumber() != 0){
                    graphics.drawString(String.valueOf(ball.getBall().getNumber()), 
                                        getPixelFromMeters(ball.getBallXPosition(), false) - (int)(BallUI.BALL_PIXEL_RADIUS / 1.5),
                                        getPixelFromMeters(ball.getBallYPosition(), true) + (int)(BallUI.BALL_PIXEL_RADIUS / 1.5));
                }
            }    
        }

        // Aiming aid visuals
        if(numbersOn){
            graphics.setColor(Color.BLACK);
            Pocket closestPocket = getOptimalPocket();
            Ball correctBall = table.getLowestNumberedBall();
            if(closestPocket != null){
                Vector3 position = getOptimalPosition();
                graphics.drawLine(getPixelFromMeters(position.getAxis(0), false), 
                                getPixelFromMeters(position.getAxis(1), true), 
                                getPixelFromMeters(ballUIs.get(0).getBallXPosition(), false),
                                getPixelFromMeters(ballUIs.get(0).getBallYPosition(), true));

                graphics.drawLine(getPixelFromMeters(closestPocket.getX(), false),
                                getPixelFromMeters(closestPocket.getY(), true),
                                getPixelFromMeters(correctBall.getDisplacement().getAxis(0), false),
                                getPixelFromMeters(correctBall.getDisplacement().getAxis(1), true));

                graphics.drawOval(getPixelFromMeters(position.getAxis(0) - Ball.RADIUS, false),
                                getPixelFromMeters(position.getAxis(1) + Ball.RADIUS, true),
                                2 * BallUI.BALL_PIXEL_RADIUS,
                                2 * BallUI.BALL_PIXEL_RADIUS);
            }
        }

        if(cueBallDrag)
        {
            graphics.setColor(Color.WHITE);
            graphics.fillOval(cueBallX  - BallUI.BALL_PIXEL_RADIUS, cueBallY - BallUI.BALL_PIXEL_RADIUS, 2 * BallUI.BALL_PIXEL_RADIUS , 2 * BallUI.BALL_PIXEL_RADIUS);
        }
    }

    public void addListener(){
        this.addMouseListener(bp);
        this.addMouseMotionListener(bp);
    }

    public void ballInHand(double x, double y){
        Ball cueBall = new Ball(0,x,y,0,0,0,0,0,0,0);
        BallUI cueBallUI = new BallUI(cueBall);
        if(isValidPosition(x,y)){
            cueBallDrag = false;
            ballUIs.add(0,cueBallUI);
            table.getBallArray().add(0, cueBall);
            this.removeMouseMotionListener(bp);
            this.removeMouseListener(bp);
            mainPanel.enableHitButton();
            mainPanel.repaint();
        }
        else{
            System.out.println("select a proper place");
        }
    }

    public boolean isValidPosition(double x, double y)
    {
        if((x > getMetersFromPixels(rightCushion, false) - Ball.RADIUS) || x < getMetersFromPixels(leftCushion, false) + Ball.RADIUS || y > getMetersFromPixels(topCushion, false) - Ball.RADIUS || y <  getMetersFromPixels(bottomCushion, false) + Ball.RADIUS)
            return false;
        
        for(BallUI ball : ballUIs)
        {
            if(Math.pow(x - ball.getBallXPosition(), 2) + Math.pow(y - ball.getBallYPosition(), 2) <= 4 * Math.pow(Ball.RADIUS, 2))
            {
                return false;
            }
        }
        return true;
    }

    class BallPlacement extends MouseAdapter{       
        @Override
        public void mouseClicked(MouseEvent e){
            ballInHand(getMetersFromPixels(e.getX(), false), getMetersFromPixels(e.getY(), true));
        }

        @Override
        public void mouseMoved(MouseEvent e){
            cueBallDrag = true;
            cueBallX = e.getX();
            cueBallY = e.getY();
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        int idx = table.evolveTable(this, UPDATION_INTERVAL / 1000.);
        if(idx == -2){
            stopAction();
        }
        repaint();
    }
}

