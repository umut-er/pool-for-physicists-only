package ui;

import java.awt.Color;

import javax.swing.JFrame;

import gameobjects.Ball;
import gameobjects.BallType;
import gameobjects.Table;

public class PoolFrame extends JFrame{
    private TableUI tableUI;

    public static final int FRAME_HEIGHT=786;
    public static final int FRAME_WIDTH=450;

    public PoolFrame(){
        Ball cueBall = new Ball(BallType.CUE, 0.5, 0.5, 0, 0, 2, 0, 0, 0, 0);
        BallUI cueBallUI = new BallUI(cueBall, 0);
        Ball[] ballArray = {cueBall};
        BallUI[] ballUIs = {cueBallUI};
        Table table = new Table(ballArray);
        this.tableUI = new TableUI(table, ballUIs);

        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("8-Ball Pool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0, 170, 0));
        this.add(tableUI);
        this.setVisible(true);
    }

    public void start(){
        tableUI.startAction();
    }
}
