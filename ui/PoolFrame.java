package ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

import gameobjects.Ball;
import gameobjects.BallType;
import gameobjects.Cushion;
import gameobjects.Table;

public class PoolFrame extends JFrame{
    private TableUI tableUI;

    // public static final int FRAME_HEIGHT=786;
    // public static final int FRAME_WIDTH=450;
    public static final int FRAME_HEIGHT=450;
    public static final int FRAME_WIDTH=786;

    public PoolFrame(){
        // Ball cueBall = new Ball(BallType.CUE, 0.5, 1.1, 0, 0, 1, 0, 0, 0, 100); // circular cushion?
        Ball cueBall2 = new Ball(BallType.CUE, 0.5, 0.5, 0, 0, 5, 0, 0, 0, -100);
        // Ball cueBall3 = new Ball(BallType.CUE, 0.5, 0.5, 0, 3, 0, 0, 0, 1000, 0); // investigate
        // Ball cueBall4 = new Ball(BallType.CUE, 0.5, 0.5, 0, 4, 3, 0, 0, 0, 0);

        Ball[] ballArray = {cueBall2};
        BallUI[] ballUIs = new BallUI[ballArray.length];
        for(int i = 0; i < ballUIs.length; i++)
            ballUIs[i] = new BallUI(ballArray[i], 0);
        Cushion[] cushions = Cushion.getStandartCushionArray();
        Table table = new Table(new ArrayList<Ball>(Arrays.asList(ballArray)), cushions);
        this.tableUI = new TableUI("table7.png", table, ballUIs);
        
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("8-Ball Pool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(255, 170, 0));
        setLayout(null);
        
        tableUI.setBounds(0, 0, TableUI.getTableWidthPixels(), TableUI.getTableHeightPixels());
        this.add(tableUI);
        this.setVisible(true);
    }

    public void start(){
        tableUI.startAction();
    }
}
