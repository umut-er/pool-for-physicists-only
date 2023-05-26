package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.UnsupportedEncodingException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import database.PoolDatabase;
import gameobjects.Ball;
import gameobjects.Table;
import net.thegreshams.firebase4j.error.FirebaseException;

public class PoolPanel extends JPanel{
    private TableUI tableUI;
    private CueUI cue = new CueUI();
    private PowerBar powerbar;
    private HitPosition hitPosition = new HitPosition();
    private ElevationBar elevationBar;
    private HitButton hitButton;
    private AccountUI userAccount1;
    private AccountUI userAccount2;
    private String username1;
    private String username2;

    public static boolean cueIsFixed = false;
    public static final int PANEL_HEIGHT=700;
    public static final int PANEL_WIDTH=1200;
    
    public PoolPanel(String username1, String username2) throws UnsupportedEncodingException, FirebaseException{
        if(!PoolDatabase.initialized())
            new PoolDatabase();
        this.username1=username1;
        this.username2=username2;
        setLayout(null);     
        setFocusable(true);
        
        ArrayList<Ball> ballArray = Ball.getStandardBallArray(); 
        ArrayList<BallUI> ballUIs = new ArrayList<BallUI>();
        for(Ball ball : ballArray)
            ballUIs.add(new BallUI(ball));
        Table table = new Table(ballArray);
        this.tableUI = new TableUI("table7.png", table, ballUIs, this);
        tableUI.setBounds(100, 100, TableUI.getTableWidthPixels(), TableUI.getTableHeightPixels());
        this.add(tableUI);

        this.userAccount1=new AccountUI(username1);
        this.userAccount2=new AccountUI(username2);
        this.userAccount1.setBounds(AccountUI.X_COORDINATE_1, AccountUI.Y_COORDINATE, AccountUI.WIDTH, AccountUI.HEIGHT);
        this.userAccount2.setBounds(AccountUI.X_COORDINATE_2, AccountUI.Y_COORDINATE, AccountUI.WIDTH, AccountUI.HEIGHT);
        this.setBackground(new Color(0, 153, 50));
        this.add(userAccount1);
        this.add(userAccount2);

        powerbar = new PowerBar(cue, this);
        JLabel powerBarField = new JLabel("Power Bar:");
        powerBarField.setBounds(605,560,100,50);
        powerBarField.setForeground(Color.WHITE);
        this.add(powerBarField);
        powerbar.setBounds(600,600, 100, 30);
        this.add(powerbar);

        elevationBar = new ElevationBar(cue, this);
        elevationBar.setBounds(750,600, 100,30);
        this.add(elevationBar);
        JLabel elevationBarLabel = new JLabel("Elevation Bar:");
        elevationBarLabel.setBounds(755,560,100,50);
        elevationBarLabel.setForeground(Color.WHITE);
        this.add(elevationBarLabel);

        hitPosition.setBounds(450, 550, 100, 100);
        this.add(hitPosition);
        hitPosition.setEnabled(false);

        cue.setCueBallX(100 + tableUI.getCueBallXPixels());
        cue.setCueBallY(100 + tableUI.getCueBallYPixels());
        this.add(cue);

        hitButton = new HitButton(tableUI, hitPosition, elevationBar, cue, powerbar, this);
        hitButton.setBounds(300,550,100,100);
        this.add(hitButton);
        
        // This idea is courtesy of ChatGPT.
        MouseAdapter frameListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if(!cueIsFixed){    
                    cue.setMouseX(e.getX());
                    cue.setMouseY(e.getY());
                    repaint();
                }    
            }
        };

        MouseAdapter hitPositionListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if(!cueIsFixed){    
                    cue.setMouseX(e.getX() + 450);
                    cue.setMouseY(e.getY() + 550);
                    repaint();
                }    
            }
        };

        MouseAdapter hitButtonListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (!cueIsFixed){
                    cue.setMouseX(e.getX() + 300);
                    cue.setMouseY(e.getY() + 550);
                    repaint();
                }
            }
        };

        MouseAdapter powerBarListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if (!cueIsFixed){
                    cue.setMouseX(e.getX() + 600);
                    cue.setMouseY(e.getY() + 600);
                    repaint();
                }
            }
        };

        MouseAdapter elevationBarListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if(!cueIsFixed){
                    cue.setMouseX(e.getX() + 750);
                    cue.setMouseY(e.getY() + 600);
                    repaint();
                }        
            }
        };

        KeyListener frameKeyListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                if(key == KeyEvent.VK_UP){
                    cueIsFixed = true;
                }
                if(key == KeyEvent.VK_DOWN){
                    cueIsFixed = false;
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {}
        
            @Override
            public void keyReleased(KeyEvent e) {}
        };

        addKeyListener(frameKeyListener);
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

    public TableUI getTableUI(){
        return tableUI;
    }

    public void disableHitButton(){
        hitButton.setEnabled(false);
    }

    public void enableHitButton(){
        hitButton.setEnabled(true);
    }

    public void ballInHand(){
        // TODO: complete
        // Note: You may want to do this in the TableUI class. In that case just write tableUI.ballInHand() here.

        enableHitButton(); // No matter what this stays here
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
