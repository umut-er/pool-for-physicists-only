package ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gameobjects.Ball;
import gameobjects.Table;

public class PoolPanel extends JPanel{
    private TableUI tableUI;
    private CueUI cue; 
    private PowerBar powerBar;
    private HitPosition hitPosition = new HitPosition();
    private ElevationBar elevationBar;
    private HitButton hitButton;

    public static boolean cueIsFixed = false;
    public static final int PANEL_HEIGHT=700;
    public static final int PANEL_WIDTH=1200;

    private MouseAdapter frameListener;
    private MouseAdapter hitPositionListener;
    private MouseAdapter hitButtonListener;
    private MouseAdapter powerBarListener;
    private MouseAdapter elevationBarListener;
    
    public PoolPanel(){
        setLayout(null);     
        setFocusable(true);
        
        ArrayList<Ball> ballArray = Ball.getStandardBallArray(); 
        ArrayList<BallUI> ballUIs = new ArrayList<BallUI>();
        for(Ball ball : ballArray)
            ballUIs.add(new BallUI(ball));
        Table table = new Table(ballArray);
        this.tableUI = new TableUI("tableNew.png", table, ballUIs, this);
        tableUI.setBounds(tableUI.getTableFrameX(), tableUI.getTableFrameY(), TableUI.getTableWidthPixels(), TableUI.getTableHeightPixels());
        this.add(tableUI);

        cue = new CueUI(tableUI);

        powerBar = new PowerBar(cue, this);
        JLabel powerBarField = new JLabel("Power Bar:");
        powerBarField.setBounds(605,560,100,50);
        this.add(powerBarField);
        powerBar.setBounds(600,600, 100, 30);
        this.add(powerBar);

        elevationBar = new ElevationBar(cue, this);
        elevationBar.setBounds(750,600, 100,30);
        this.add(elevationBar);
        JLabel elevationBarLabel = new JLabel("Elevation Bar:");
        elevationBarLabel.setBounds(755,560,100,50);
        this.add(elevationBarLabel);

        cue.setCueBallX(tableUI.getTableFrameX() + tableUI.getCueBallXPixels());
        cue.setCueBallY(tableUI.getTableFrameY() + tableUI.getCueBallYPixels());
        this.add(cue);

        hitPosition.setBounds(450, 550, 100, 100);
        this.add(hitPosition);
        hitPosition.setEnabled(false);

        hitButton = new HitButton(tableUI, hitPosition, elevationBar, cue, powerBar, this);
        hitButton.setBounds(300,550,100,100);
        this.add(hitButton);
        
        // This idea is courtesy of ChatGPT.
        frameListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if(!cueIsFixed){
                    cue.setMouseX(e.getX());
                    cue.setMouseY(e.getY());
                    repaint();
                }    
            }
        };

        hitPositionListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if(!cueIsFixed){    
                    cue.setMouseX(e.getX() + 450);
                    cue.setMouseY(e.getY() + 550);
                    repaint();
                }    
            }
        };

        hitButtonListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (!cueIsFixed){
                    cue.setMouseX(e.getX() + 300);
                    cue.setMouseY(e.getY() + 550);
                    repaint();
                }
            }
        };

        powerBarListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (!cueIsFixed){
                    cue.setMouseX(e.getX() + 600);
                    cue.setMouseY(e.getY() + 600);
                    repaint();
                }
            }
        };

        elevationBarListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if(!cueIsFixed){
                    cue.setMouseX(e.getX() + 750);
                    cue.setMouseY(e.getY() + 600);
                    repaint();
                }        
            }
        };

        KeyListener frameKeyListener = new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(key == KeyEvent.VK_W){
                    cueIsFixed = true;
                }
                else if(key == KeyEvent.VK_S){
                    cueIsFixed = false;
                }
                else{
                    e.consume();
                }
            }
            @Override
            public void keyTyped(KeyEvent e){}
        
            @Override
            public void keyReleased(KeyEvent e){}
        };

        addKeyListener(frameKeyListener);
        hitButton.addMouseMotionListener(hitButtonListener);
        hitPosition.addMouseMotionListener(hitPositionListener);
        powerBar.addMouseMotionListener(powerBarListener);
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

    public TableUI getTableUI(){
        return tableUI;
    }

    public void disableCue(){
        this.removeMouseMotionListener(frameListener);
        hitPosition.removeMouseMotionListener(hitPositionListener);
        hitButton.removeMouseMotionListener(hitButtonListener);
        powerBar.removeMouseMotionListener(powerBarListener);
        elevationBar.removeMouseMotionListener(elevationBarListener);
        cue.setActive(false);
        repaint();
    }

    public void enableCue(){
        cueIsFixed = false;
        cue.setCueBallX(tableUI.getCueBallXPixels() + tableUI.getTableFrameX());
        cue.setCueBallY(tableUI.getCueBallYPixels() + tableUI.getTableFrameY());
        this.addMouseMotionListener(frameListener);
        hitPosition.addMouseMotionListener(hitPositionListener);
        hitButton.addMouseMotionListener(hitButtonListener);
        powerBar.addMouseMotionListener(powerBarListener);
        elevationBar.addMouseMotionListener(elevationBarListener);
        cue.setActive(true);
        repaint();
    }

    public void disableHitButton(){
        hitButton.setEnabled(false);
    }

    public void enableHitButton(){
        enableCue();
        hitButton.setEnabled(true);
        repaint();
    }

    public void ballInHand(){
        tableUI.addListener();
    }

    public void placeNineBall(){
        // TODO: Complete
        
    }

    public CueUI getCue(){
        return this.cue;
    }

    public Table getTable(){
        return tableUI.getTable();
    }

    public void paint(Graphics g){
        requestFocusInWindow(true);
        super.paint(g);
        cue.paintComponent(g);
    }
}
