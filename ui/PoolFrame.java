package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

import gameobjects.Ball;
import gameobjects.Table;

public class PoolFrame extends JFrame{
    private TableUI tableUI;
    private CueUI cue;

    public static final int FRAME_HEIGHT=700;
    public static final int FRAME_WIDTH=1200;

    public PoolFrame(){

        PowerBar powerbar = new PowerBar();
        HitPosition hitPosition = new HitPosition();
        ElevationBar elevationBar = new ElevationBar();
        HitButton hitButton = new HitButton();

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

        JLabel powerBarField = new JLabel("Power Bar:");
        powerBarField.setBounds(605,560,100,50);
        this.add(powerBarField);
        powerbar.setBounds(600,600, 100, 30);
        this.add(powerbar);
        this.setVisible(true);

        elevationBar.setBounds(750,600, 100,30);
        this.add(elevationBar);
        JLabel elevationBarLabel = new JLabel("Elevation Bar:");
        elevationBarLabel.setBounds(755,560,100,50);
        this.add(elevationBarLabel);

        hitPosition.setBounds(450, 550, 100, 100);
        this.add(hitPosition);

        hitButton.setBounds(300,550,100,100);
        this.add(hitButton);
    }

    public void start(){
        tableUI.shootBall();
    }
}
