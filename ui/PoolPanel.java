package ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
    private HitButton hitButton;

    public static final int PANEL_HEIGHT=700;
    public static final int PANEL_WIDTH=1200;

    public PoolPanel(){
        setLayout(null);     

        Ball[] ballArray = Ball.getStandardBallArray(); 
        ArrayList<BallUI> ballUIs = new ArrayList<BallUI>();
        for(int i = 0; i < ballArray.length; i++)
            ballUIs.add(new BallUI(ballArray[i]));
        Table table = new Table(new ArrayList<Ball>(Arrays.asList(ballArray)));
        this.tableUI = new TableUI("table7.png", table, ballUIs, this);
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
        hitPosition.setEnabled(false);

        cue = new CueUI();
        cue.setCueBallX(100 + tableUI.getCueBallXPixels());
        cue.setCueBallY(100 + tableUI.getCueBallYPixels());
        this.add(cue);

        hitButton = new HitButton(tableUI, hitPosition, elevationBar, cue, powerbar);
        hitButton.setBounds(300,550,100,100);
        this.add(hitButton);
        
        // This idea is courtesy of ChatGPT.
        MouseAdapter frameListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                cue.setMouseX(e.getX());
                cue.setMouseY(e.getY());
                repaint();
            }
        };

        MouseAdapter hitPositionListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                cue.setMouseX(e.getX() + 450);
                cue.setMouseY(e.getY() + 550);
                repaint();
            }
        };

        MouseAdapter hitButtonListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                cue.setMouseX(e.getX() + 300);
                cue.setMouseY(e.getY() + 550);
                repaint();
            }
        };

        MouseAdapter powerBarListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                cue.setMouseX(e.getX() + 600);
                cue.setMouseY(e.getY() + 600);
                repaint();
            }
        };

        MouseAdapter elevationBarListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                cue.setMouseX(e.getX() + 750);
                cue.setMouseY(e.getY() + 600);
                repaint();
            }
        };

        hitButton.addMouseMotionListener(hitButtonListener);
        hitPosition.addMouseMotionListener(hitPositionListener);
        powerbar.addMouseMotionListener(powerBarListener);
        elevationBar.addMouseMotionListener(elevationBarListener);
        addMouseMotionListener(frameListener);

        // hitPosition.addMouseListener(new MouseListener() {
        //     @Override
        //     public void mouseClicked(MouseEvent e){
        //         PoolPanel.this.repaint();
        //     }
        //     @Override
        //     public void mousePressed(MouseEvent e){}
        //     @Override
        //     public void mouseReleased(MouseEvent e){}
        //     @Override
        //     public void mouseEntered(MouseEvent e){}
        //     @Override
        //     public void mouseExited(MouseEvent e){}
        // });

        this.setVisible(true);
    }

    public CueUI getCue(){
        return this.cue;
    }

    public void paint(Graphics g){
        super.paint(g);
        cue.paintComponent(g);
    }
}
