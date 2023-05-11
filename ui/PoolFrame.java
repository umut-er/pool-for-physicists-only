package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

import gameobjects.Ball;
import gameobjects.BallType;
import gameobjects.Table;
import vectormath.Vector3;

public class PoolFrame extends JFrame{
    private TableUI tableUI;

    public static final int FRAME_HEIGHT=700;
    public static final int FRAME_WIDTH=1200;

    public PoolFrame(){
        Ball cueBall2 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 1, TableUI.getTableHeightMeters() / 2, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall3 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.999 + Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 + Ball.RADIUS + 0.0001, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall4 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.999 + Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 - Ball.RADIUS - 0.0001, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall5 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.998 + 2 * Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 + 2 * Ball.RADIUS + 0.0002, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall6 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.998 + 2 * Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall7 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.998 + 2 * Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 - 2 * Ball.RADIUS - 0.0002, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall8 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.997 + 2 * Ball.RADIUS * Math.sqrt(3) + Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 + Ball.RADIUS + 0.0001, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall9 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.997 + 2 * Ball.RADIUS * Math.sqrt(3) + Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2 - Ball.RADIUS - 0.0001, 0, 0, 0, 0, 0, 0, 0);
        Ball cueBall10 = new Ball(BallType.CUE, TableUI.getTableWidthMeters() - 0.996 + 4 * Ball.RADIUS * Math.sqrt(3), TableUI.getTableHeightMeters() / 2, 0, 0, 0, 0, 0, 0, 0);
        
        Ball cueBall1 = new Ball(BallType.CUE, 0.825, TableUI.getTableHeightMeters() / 2, 0, 0, 0, 0, 0, 0, 0); // circular cushion?
        Vector3 shot = new Vector3(11, 0.01, 0);
        double angle = Vector3.getSignedAngle2D(Vector3.subtract(cueBall2.getDisplacement(), cueBall1.getDisplacement()), new Vector3(1, 0, 0));
        shot = Vector3.rotateAboutZAxis(shot, -angle); // +angle causes bugs 
        cueBall1.setVelocity(shot);

        Ball[] ballArray = {cueBall1, cueBall2, cueBall3, cueBall4, cueBall5, cueBall6, cueBall7, cueBall8, cueBall9, cueBall10};
        ArrayList<BallUI> ballUIs = new ArrayList<BallUI>();
        for(int i = 0; i < ballArray.length; i++)
            ballUIs.add(new BallUI(ballArray[i], i));
        Table table = new Table(new ArrayList<Ball>(Arrays.asList(ballArray)));
        this.tableUI = new TableUI("table7.png", table, ballUIs);
        
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("8-Ball Pool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(255, 170, 0));
        setLayout(null);
        
        tableUI.setBounds(100, 100, TableUI.getTableWidthPixels(), TableUI.getTableHeightPixels());
        this.add(tableUI);
        this.setVisible(true);
    }

    public void start(){
        tableUI.startAction();
    }
}
