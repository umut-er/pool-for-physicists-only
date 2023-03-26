package ui;

import java.awt.Color;

import javax.swing.JFrame;

import gameobjects.Table;

public class PoolFrame extends JFrame{
    Table table;
    public static final int FRAME_HEIGHT=786;
    public static final int FRAME_WIDTH=450;
    public PoolFrame()
    {
        table = new Table();
        this.setVisible(true);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("8-Ball Pool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0, 170, 0));
        // this.add(table);
    }
}
