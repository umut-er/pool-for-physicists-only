package ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gameobjects.Ball;
import gameobjects.Table;

public class PoolPanel extends JPanel{
    private TableUI tableUI;
    private CueUI cue;
    private PowerBar powerbar = new PowerBar();
    private HitPosition hitPosition = new HitPosition();
    private ElevationBar elevationBar = new ElevationBar();
    private HitButton hitButton = new HitButton();

    public static final int FRAME_HEIGHT=700;
    public static final int FRAME_WIDTH=1200;

    public PoolPanel(){
        setLayout(null);     

        Ball[] ballArray = Ball.getStandardBallArray(); 
        ArrayList<BallUI> ballUIs = new ArrayList<BallUI>();
        for(int i = 0; i < ballArray.length; i++)
            ballUIs.add(new BallUI(ballArray[i]));
        Table table = new Table(new ArrayList<Ball>(Arrays.asList(ballArray)));
        this.tableUI = new TableUI("table7.png", table, ballUIs);
        tableUI.setBounds(100, 100, TableUI.getTableWidthPixels(), TableUI.getTableHeightPixels());
        this.add(tableUI);

        JLabel powerBarField = new JLabel("Power Bar:");
        powerBarField.setBounds(605,560,100,50);
        this.add(powerBarField);
        powerbar.setBounds(600,600, 100, 30);
        this.add(powerbar);

        elevationBar.setBounds(750,600, 100,30);
        this.add(elevationBar);
        JLabel elevationBarLabel = new JLabel("Elevation Bar:");
        elevationBarLabel.setBounds(755,560,100,50);
        this.add(elevationBarLabel);

        hitPosition.setBounds(450, 550, 100, 100);
        this.add(hitPosition);

        hitButton.setBounds(300,550,100,100);
        this.add(hitButton);

        addMouseMotionListener(new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                cue.setMouseX(e.getX());
                cue.setMouseY(e.getY());
                repaint();
            }
        });

        this.setVisible(true);

        cue = new CueUI();
        cue.setCueBallX(100 + tableUI.getCueBallXPixels());
        cue.setCueBallY(100 + tableUI.getCueBallYPixels());
        this.add(cue);
    }

    public void paint(Graphics g){
        // long s = System.currentTimeMillis();
        super.paint(g);
        // System.out.println(System.currentTimeMillis() - s);
        // Graphics2D gr=(Graphics2D)g;
        cue.paintComponent(g);
    }

    public void start(){
        tableUI.shootBall();
    }
}
