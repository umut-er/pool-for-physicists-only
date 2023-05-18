package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import gameobjects.Ball;
import gameobjects.Table;

public class PoolFrame extends JFrame{
    private TableUI tableUI;
    private CueUI cue;

    public static final int FRAME_HEIGHT=700;
    public static final int FRAME_WIDTH=1200;

    public PoolFrame(){
        Ball[] ballArray = Ball.getStandardBallArray(); 
        ArrayList<BallUI> ballUIs = new ArrayList<BallUI>();
        for(int i = 0; i < ballArray.length; i++)
            ballUIs.add(new BallUI(ballArray[i]));
        Table table = new Table(new ArrayList<Ball>(Arrays.asList(ballArray)));
        this.tableUI = new TableUI("table7.png", table, ballUIs);
        cue = new CueUI();
        cue.setCueBallX(this.tableUI.getCueBallXPixels()+157);
        cue.setCueBallY(this.tableUI.getCueBallYPixels()+180);

        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("8-Ball Pool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(255, 170, 0));
        setLayout(null);
        
        tableUI.setBounds(150, 150, TableUI.getTableWidthPixels(), TableUI.getTableHeightPixels());
        this.add(tableUI);
        this.setVisible(true);

        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                cue.setMouseX(e.getX());
                cue.setMouseY(e.getY());
                repaint();
            }
        });
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D gr=(Graphics2D)g;
        cue.draw(gr);

    }

    public void start(){
        tableUI.shootBall();
    }
}
