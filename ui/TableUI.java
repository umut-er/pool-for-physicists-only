package ui;

import java.awt.RenderingHints;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
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
import gameobjects.Table;
import physics.Physics;
import server.PoolClient.PoolPanel;

public class TableUI extends JPanel implements ActionListener{
    private PoolPanel mainPanel;
    private Timer timer;
    private Table table;
    private ArrayList<BallUI> ballUIs = new ArrayList<BallUI>();
    private Image tableImage;
    private BallPlacement bp = new BallPlacement();

    private boolean cueBallDrag = false;

    private static final double TABLE_WIDTH_METERS = 3.0534;
    private static final double TABLE_HEIGHT_METERS = 1.6818;
    private static final int TABLE_WIDTH_PIXELS = 748;
    private static final int TABLE_HEIGHT_PIXELS = 412;
    public static final int UPDATION_INTERVAL = 33;
    private static final int TABLE_FRAME_X = 200;
    private static final int TABLE_FRAME_Y = 100;

    private boolean numbersOn = true;
    private int cueBallX;
    private int cueBallY;

    public TableUI(String imageFileName, Table table, PoolPanel mainPanel){
        this.mainPanel = mainPanel;
        changeTableImage(imageFileName);
        this.table = table;
        setBallUIArray();
        timer = new Timer(UPDATION_INTERVAL,this);  
    }

    /**
     * Returns the pixel location, (0, 0) being the top left corner, from the location measured in meters.
     * @param meters Location in meters
     * @param axis false -> x, true -> y
     * @return An int, the pixel location of object.
     */
    public static int getPixelFromMeters(double meters, boolean axis){
        if(axis)
            return (int)((TABLE_HEIGHT_METERS -  meters) / TABLE_HEIGHT_METERS * TABLE_HEIGHT_PIXELS);
        return (int)(meters / TABLE_WIDTH_METERS * TABLE_WIDTH_PIXELS);    
    }
    
    // this is the reverse method of getPixelFromMeters
    public static double getMetersFromPixels(int pixels, boolean axis)
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

    public int getTableFrameX(){
        return TABLE_FRAME_X;
    }

    public int getTableFrameY(){
        return TABLE_FRAME_Y;
    }

    public void startAction(){
        getTable().resetTurn();
        getTable().setSmallestNumberedBall();
        mainPanel.disableTurn();
        mainPanel.disablePause();
        numbersOn = false;
        timer.start();
    }

    public void stopAction(){
        numbersOn = true;
        timer.stop();
        firePropertyChange("Hit over", 0, 1);
    }

    public void hitBall(double cueSpeed, double directionAngle, double elevationAngle, double horizontalSpin, double verticalSpin){
        Physics.hitBall(table.getBallArray().get(0), cueSpeed, directionAngle, elevationAngle, horizontalSpin, verticalSpin);
        startAction();
    }

    public ArrayList<BallUI> getBallUIArray(){
        return this.ballUIs;
    }

    public void setBallUIArray(){
        ballUIs.clear();
        for(Ball ball : getTable().getBallArray()){
            ballUIs.add(new BallUI(ball));
        }
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

    public void removeBall(int ballNumber){
        for(BallUI ballUI : ballUIs){
            if(ballUI.getNumber() == ballNumber){
                ballUIs.remove(ballUI);
                break;
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D graphics=(Graphics2D)g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.drawImage(tableImage, 0, 0, TABLE_WIDTH_PIXELS, TABLE_HEIGHT_PIXELS, null);

        graphics.setColor(Color.BLACK);
        for(Cushion cushion : table.getCushionArray()){
            graphics.drawLine(getPixelFromMeters(cushion.getStart().getAxis(0), false), getPixelFromMeters(cushion.getStart().getAxis(1), true), 
                                getPixelFromMeters(cushion.getEnd().getAxis(0), false), getPixelFromMeters(cushion.getEnd().getAxis(1), true));
        }

        for(BallUI ball : ballUIs){
            if(ball.getPocketed())
                continue;
            graphics.setColor(ball.getColor());
            graphics.fillOval(getPixelFromMeters(ball.getBallXPosition(), false) - BallUI.BALL_PIXEL_RADIUS, 
                            getPixelFromMeters(ball.getBallYPosition(), true) - BallUI.BALL_PIXEL_RADIUS,
                            2 * BallUI.BALL_PIXEL_RADIUS, 2 * BallUI.BALL_PIXEL_RADIUS);
        }

        if(numbersOn){
            graphics.setColor(new Color(134, 74, 154));
            for(BallUI ball : ballUIs){
                if(ball.getBall().getNumber() != 0 && !ball.getPocketed()){
                    if(ball.getNumber() < 10){
                        graphics.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
                        graphics.drawString(String.valueOf(ball.getBall().getNumber()), 
                                            getPixelFromMeters(ball.getBallXPosition(), false) - (int)(BallUI.BALL_PIXEL_RADIUS / 1.55),
                                            getPixelFromMeters(ball.getBallYPosition(), true) + (int)(BallUI.BALL_PIXEL_RADIUS / 1.3));
                    }
                    else{
                        graphics.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
                        graphics.drawString(String.valueOf(ball.getBall().getNumber()), 
                                            getPixelFromMeters(ball.getBallXPosition(), false) - (int)(BallUI.BALL_PIXEL_RADIUS / 1.),
                                            getPixelFromMeters(ball.getBallYPosition(), true) + (int)(BallUI.BALL_PIXEL_RADIUS / 1.5));
                    }
                }
            }    
        }

        if(cueBallDrag){
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setColor(Color.WHITE);
            graphics.fillOval(cueBallX  - BallUI.BALL_PIXEL_RADIUS, cueBallY - BallUI.BALL_PIXEL_RADIUS, 2 * BallUI.BALL_PIXEL_RADIUS , 2 * BallUI.BALL_PIXEL_RADIUS);
        }
    }

    public void addListener(){
        cueBallDrag = true;
        this.addMouseListener(bp);
        this.addMouseMotionListener(bp);
        cueBallX = MouseInfo.getPointerInfo().getLocation().x;
        cueBallY = MouseInfo.getPointerInfo().getLocation().y;
        mainPanel.repaint();
    }

    public void ballInHandChecker(double x, double y){
        if(!table.isPositionValid(x, y))
            return;
        firePropertyChange("ball in hand placed", x, y); // One of the ugliest things of all time
        
    }

    public void ballInHandPlacer(double x, double y){
        cueBallDrag = false;
        placeBall(0, x, y);
        this.removeMouseMotionListener(bp);
        this.removeMouseListener(bp);
        mainPanel.getNotif().setText("");
        mainPanel.repaint();
    }

    public double[] getPlacement(){
        double x = TableUI.TABLE_WIDTH_METERS / 2;
        double y = TableUI.TABLE_HEIGHT_METERS / 2;

        while(!table.isPositionValid(x, y)){
            x += Ball.RADIUS;
        }

        double[] arr = {x, y};
        return arr; 
    }

    public void placeBall(int ballNumber, double x, double y){
        for(Ball ball : table.getBallArray()){
            if(ball.getNumber() == ballNumber){
                ball.setPosition(x, y, 0);
                ball.setVelocity(0, 0, 0);
                ball.setAngularVelocity(0, 0, 0);
                ball.setPocketed(false);
                break;
            }
        }
    }

    public boolean getCueBallDrag(){
        return this.cueBallDrag;
    }

    class BallPlacement extends MouseAdapter{       
        @Override
        public void mouseClicked(MouseEvent e){
            ballInHandChecker(getMetersFromPixels(e.getX(), false), getMetersFromPixels(e.getY(), true));
        }

        @Override
        public void mouseMoved(MouseEvent e){
            cueBallX = e.getX();
            cueBallY = e.getY();
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent evt){
            cueBallDrag = false;
            mainPanel.repaint();
        }

        @Override
        public void mouseEntered(MouseEvent evt){
            cueBallDrag = true;
            mainPanel.repaint();
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

