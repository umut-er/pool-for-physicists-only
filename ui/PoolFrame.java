package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

import gameobjects.Ball;
import gameobjects.BallType;
import gameobjects.Table;

public class PoolFrame extends JFrame{
    private TableUI tableUI;

    // public static final int FRAME_HEIGHT=786;
    // public static final int FRAME_WIDTH=450;
    public static final int FRAME_HEIGHT=450;
    public static final int FRAME_WIDTH=786;

    public PoolFrame(){
        Ball cueBall = new Ball(BallType.CUE, 0.5, 0.5, 0, 3, 0, 0, 0, 0, 0);
        Ball anotherBall = new Ball(BallType.CUE, 1.5, 0.505, 0, 0, 0, 0, 0, 0, 0);
        BallUI cueBallUI = new BallUI(cueBall, 0);
        BallUI anotherBallUI = new BallUI(anotherBall, 0);
        Ball[] ballArray = {cueBall, anotherBall};
        BallUI[] ballUIs = {cueBallUI, anotherBallUI};
        // Ball[] ballArray = {cueBall};
        // BallUI[] ballUIs = {cueBallUI};
        Table table = new Table(new ArrayList<Ball>(Arrays.asList(ballArray)));
        this.tableUI = new TableUI(table, ballUIs);
        
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("8-Ball Pool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(255, 170, 0));
        setLayout(null);
        
        tableUI.setBounds(0, 0, TableUI.getTableWidth(), TableUI.getTableHeight());
        this.add(tableUI);
        this.setVisible(true);
    }

    public void start(){
        tableUI.startAction();
    }
}
