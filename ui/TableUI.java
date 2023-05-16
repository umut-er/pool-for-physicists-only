package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import gameobjects.Ball;
import gameobjects.Cushion;
import gameobjects.Pocket;
import gameobjects.Table;
import vectormath.Vector3;

public class TableUI extends JPanel implements ActionListener{
    private Timer timer;
    private Table table;
    private ArrayList<BallUI> ballUIs;
    private Image tableImage;

    private static final double TABLE_WIDTH_METERS = 3.0534;
    private static final double TABLE_HEIGHT_METERS = 1.6818;
    private static final int TABLE_WIDTH_PIXELS = 748;
    private static final int TABLE_HEIGHT_PIXELS = 412;

    public static final int UPDATION_INTERVAL = 33;

    private boolean numbersOn = true;

    public TableUI(String imageFileName, Table table, ArrayList<BallUI> ballUIs){
        changeTableImage(imageFileName);
        this.table = table;
        this.ballUIs = ballUIs;
        timer = new Timer(UPDATION_INTERVAL,this);
    }

    /**
     * Returns the pixel location from the location measured in meters.
     * @param meters Location in meters
     * @param axis false -> x, true -> y
     * @return An int, the pixel location of object.
     */
    private static int getPixelFromMeters(double meters, boolean axis){
        if(axis)
            return (int)((TABLE_HEIGHT_METERS -  meters) / TABLE_HEIGHT_METERS * TABLE_HEIGHT_PIXELS);
        return (int)(meters / TABLE_WIDTH_METERS * TABLE_WIDTH_PIXELS);    
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

    public void startAction(){
        numbersOn = false;
        timer.start();
    }

    public void stopAction(){
        numbersOn = true;
        timer.stop();
        paint(getGraphics());
        shootBall();
    }

    // Currently terminal output for aiming aid.
    public void shootBall(){
        Scanner in = new Scanner(System.in);
        Vector3 position = getOptimalPosition();
        if(position == null){
            System.out.print("No possible pocket found! Give your (x, y) velocity values, (0, 0) to enable quitting: ");
            double x = in.nextDouble(), y = in.nextDouble();
            table.getBallArray().get(0).setVelocity(x, y, 0);
            if(x != 0 || y != 0)
                startAction();
        }
        Vector3 v = new Vector3(position.getAxis(0) - ballUIs.get(0).getBallXPosition(), position.getAxis(1) - ballUIs.get(0).getBallYPosition(), 0);
        v.normalize();
        System.out.println("Suggested shot: " + v);
        System.out.print("This shot is currently at 1m/s. Enter a positive value to multiply this by (negatives will do nothing): ");
        double mul = in.nextDouble();
        if(mul > 0){
            v.inPlaceMultiply(mul);
        }
        in.nextLine();
        double x, y;
        System.out.println("New shot: " + v);
        System.out.print("Do you want to execute this shot (y or n)?: ");
        String ans = in.nextLine();
        if(ans.toLowerCase().equals("y")){
            x = v.getAxis(0); y = v.getAxis(1);
        }
        else{
            System.out.print("Give your (x, y) velocity values, (0, 0) to enable quitting: ");
            x = in.nextDouble(); y = in.nextDouble();
        }
        table.getBallArray().get(0).setVelocity(x, y, 0);
        if(x != 0 || y != 0)
            startAction();
    }

    // Part of aiming aid
    public Pocket getOptimalPocket(){
        Pocket closestPocket = null;
        double dist = 5;
        Vector3 fullHitVector = Vector3.subtract(table.getBallArray().get(1).getDisplacement(), table.getBallArray().get(0).getDisplacement());
        for(Pocket p : table.getPocketArray()){
            Vector3 towardsPocket = Vector3.subtract(p.getPosition(), table.getBallArray().get(1).getDisplacement());
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
        Pocket closestPocket = getOptimalPocket();
        if(closestPocket == null)
            return null;
        Vector3 towardsPocket = Vector3.subtract(closestPocket.getPosition(), table.getBallArray().get(1).getDisplacement());
        towardsPocket.normalize();
        towardsPocket.inPlaceMultiply(-2 * Ball.RADIUS);
        return Vector3.add(table.getBallArray().get(1).getDisplacement(), towardsPocket);
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

    public void paint(Graphics g){
        super.paint(g);
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
            if(closestPocket != null){
                Vector3 position = getOptimalPosition();
                graphics.drawLine(getPixelFromMeters(position.getAxis(0), false), 
                                getPixelFromMeters(position.getAxis(1), true), 
                                getPixelFromMeters(ballUIs.get(0).getBallXPosition(), false),
                                getPixelFromMeters(ballUIs.get(0).getBallYPosition(), true));

                graphics.drawLine(getPixelFromMeters(closestPocket.getX(), false),
                                getPixelFromMeters(closestPocket.getY(), true),
                                getPixelFromMeters(ballUIs.get(1).getBallXPosition(), false),
                                getPixelFromMeters(ballUIs.get(1).getBallYPosition(), true));

                graphics.drawOval(getPixelFromMeters(position.getAxis(0) - Ball.RADIUS, false),
                                getPixelFromMeters(position.getAxis(1) + Ball.RADIUS, true),
                                2 * BallUI.BALL_PIXEL_RADIUS,
                                2 * BallUI.BALL_PIXEL_RADIUS);
            }
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
