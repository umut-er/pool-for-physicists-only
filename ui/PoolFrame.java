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

    public static final int FRAME_HEIGHT=700;
    public static final int FRAME_WIDTH=1200;

    public PoolFrame(){
        //Ball cueBall = new Ball(BallType.CUE, 0.65, 0.5, 0, 0, 1.5, 0, 0, 0, 150); // circular cushion?
        Ball cueBall2 = new Ball(BallType.CUE, 0.5, 0.5, 0, 0, 5, 0, 0, 0, -50);
        //Ball cueBall4 = new Ball(BallType.CUE, 0.2, 0.6, 0, 2.91, 1, 0, 0, 0, 0);
        // Ball cueBall5 = new Ball(BallType.CUE, 0.5, 0.5, 0, 5, 0, 0, 0, -200, 0);
        //Ball cueBall6 = new Ball(BallType.CUE, 1.5, 0.5, 0, 0, 0, 0, 0, 0, 0);

        Ball[] ballArray = {cueBall2};
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
