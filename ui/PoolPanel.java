package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gameobjects.Ball;
import gameobjects.Table;

public class PoolPanel extends JPanel implements ActionListener{
    private TableUI tableUI;
    private CueUI cue; 
    private PowerBar powerBar;
    private HitPosition hitPosition = new HitPosition(this);
    private ElevationBar elevationBar;
    private HitButton hitButton;
    private JButton inGameMenuButton;
    private JTextField notifications;
    
    private InGameMenu gameMenu;
    private AccountUI userAccount1;
    private AccountUI userAccount2;
    private JLabel scoreLabel1;
    private JLabel scoreLabel2;
    private int score1 = 0;
    private int score2 = 0;

    public static boolean cueIsFixed = false;
    public static final int PANEL_HEIGHT=700;
    public static final int PANEL_WIDTH=1200;

    private MouseAdapter frameListener;
    private MouseAdapter hitPositionListener;
    private MouseAdapter hitButtonListener;
    private MouseAdapter powerBarListener;
    private MouseAdapter elevationBarListener;
    private MouseAdapter inGameMenuButtonListener;
    private MouseAdapter notificationsListener;

    private boolean turn = false;
    
    public PoolPanel(String username1, String username2){
        setLayout(null);     
        setFocusable(true);
        
        ArrayList<Ball> ballArray = Ball.getStandardBallArray(); 
        ArrayList<BallUI> ballUIs = new ArrayList<BallUI>();
        for(Ball ball : ballArray)
            ballUIs.add(new BallUI(ball));
        Table table = new Table(ballArray);
        this.tableUI = new TableUI("table7.png", table, ballUIs, this);
        tableUI.setBounds(tableUI.getTableFrameX(), tableUI.getTableFrameY(), TableUI.getTableWidthPixels(), TableUI.getTableHeightPixels());
        this.add(tableUI);

        userAccount1=new AccountUI(username1);
        userAccount2=new AccountUI(username2);
        userAccount1.setBounds(AccountUI.X_COORDINATE_1, AccountUI.Y_COORDINATE, AccountUI.WIDTH, AccountUI.HEIGHT);
        userAccount2.setBounds(AccountUI.X_COORDINATE_2, AccountUI.Y_COORDINATE, AccountUI.WIDTH, AccountUI.HEIGHT);
        this.setBackground(Color.DARK_GRAY);
        this.add(userAccount1);
        this.add(userAccount2);
        inGameMenuButton=new JButton("Pause");
        inGameMenuButton.setBounds(1000,250,100,100);
        inGameMenuButton.setBackground(Color.WHITE);
        inGameMenuButton.addActionListener(this);
        this.add(inGameMenuButton);
        gameMenu=new InGameMenu(this);
        gameMenu.setVisible(false);
        this.add(gameMenu);
        
        cue = new CueUI(tableUI);
        powerBar = new PowerBar(cue, this);
        JLabel powerBarField = new JLabel("Power Bar:");
        powerBarField.setBounds(605,560,100,50);
        powerBarField.setForeground(Color.WHITE);
        this.add(powerBarField);
        powerBar.setBounds(600,600, 100, 30);
        this.add(powerBar);

        elevationBar = new ElevationBar(cue, this);
        elevationBar.setBounds(750,600, 100,30);
        this.add(elevationBar);
        JLabel elevationBarLabel = new JLabel("Elevation Bar:");
        elevationBarLabel.setBounds(755,560,100,50);
        elevationBarLabel.setForeground(Color.WHITE);
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

        // textarea for notificiations is implemented here
        notifications = new JTextField();
        notifications.setBounds(975, 100,150, 50);
        notifications.setBackground(Color.BLACK);
        notifications.setForeground(Color.RED);
        notifications.setFont(new Font("Dialog", Font.BOLD, 14));
        notifications.setHorizontalAlignment(JTextField.CENTER);
        notifications.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
        this.add(notifications);

        scoreLabel1 = new JLabel(String.valueOf(score1));
        scoreLabel1.setForeground(Color.CYAN);
        scoreLabel1.setFont(new Font("Dialog", Font.BOLD, 25));
        scoreLabel1.setBounds(AccountUI.X_COORDINATE_1 + AccountUI.WIDTH + 20, AccountUI.Y_COORDINATE + 5, 40, 40);
        scoreLabel2 = new JLabel(String.valueOf(score2));
        scoreLabel2.setForeground(Color.CYAN);
        scoreLabel2.setFont(new Font("Dialog", Font.BOLD, 25));
        scoreLabel2.setBounds(AccountUI.X_COORDINATE_2 + AccountUI.WIDTH + 20, AccountUI.Y_COORDINATE + 5, 40, 40);
        this.add(scoreLabel1);
        this.add(scoreLabel2);
        
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

        inGameMenuButtonListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if(!cueIsFixed){
                    cue.setMouseX(e.getX() + 1000);
                    cue.setMouseY(e.getY() + 250);
                    repaint();
                }        
            }
        };

        notificationsListener = new MouseAdapter() {
            public void mouseMoved(MouseEvent e) {
                if(!cueIsFixed){
                    cue.setMouseX(e.getX() + 975);
                    cue.setMouseY(e.getY() + 100);
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
        inGameMenuButton.addMouseMotionListener(inGameMenuButtonListener);
        notifications.addMouseMotionListener(notificationsListener);
        addMouseMotionListener(frameListener);

        hitPosition.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                PoolPanel.this.repaint();
            }
            @Override
            public void mousePressed(MouseEvent e){}
            @Override
            public void mouseReleased(MouseEvent e){}
            @Override
            public void mouseEntered(MouseEvent e){}
            @Override
            public void mouseExited(MouseEvent e){}
        });

        this.setVisible(true);
    }

    public void switchTurns(){
        turn = !turn;
    }

    public TableUI getTableUI(){
        return tableUI;
    }

    public boolean isResumed(){
        return this.inGameMenuButton.isEnabled();
    }

    public void awardWin(){
        setWinnerString();
        if(turn){
            userAccount2.levelUpAccount();
            score2++;
            scoreLabel2.setText(String.valueOf(score2));
        }
        else{
            userAccount1.levelUpAccount();
            score1++;
            scoreLabel1.setText(String.valueOf(score1));
        }
    }

    public void setWinnerString()
    {
        notifications.setForeground(Color.GREEN);
        if(turn){
            notifications.setText(userAccount2.getAccountName() + " wins!!");
        }
        else{
            notifications.setText(userAccount1.getAccountName() + " wins!!");
        }   
    }

    public void disableCue(){
        this.removeMouseMotionListener(frameListener);
        hitPosition.removeMouseMotionListener(hitPositionListener);
        hitButton.removeMouseMotionListener(hitButtonListener);
        powerBar.removeMouseMotionListener(powerBarListener);
        elevationBar.removeMouseMotionListener(elevationBarListener);
        inGameMenuButton.removeMouseMotionListener(inGameMenuButtonListener);
        notifications.removeMouseMotionListener(notificationsListener);
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
        inGameMenuButton.addMouseMotionListener(inGameMenuButtonListener);
        notifications.addMouseMotionListener(notificationsListener);
        enableInGameMenuButton();
        cue.setActive(true);
        repaint();
    }

    public void enableInGameMenuButton(){
        this.inGameMenuButton.setText("Pause");
        this.inGameMenuButton.setEnabled(true);
    }

    public void disableHitButton(){
        hitButton.setEnabled(false);
    }

    public void disablePowerBar(){
        powerBar.setEnabled(false);
    }

    public void disableElevationBar(){
        elevationBar.setEnabled(false);
    }

    public void disableHitPosition(){
        hitPosition.setEnabled(false);
    }

    public void disablePause(){
        inGameMenuButton.setEnabled(false);
    }

    public void enableHitButton(){
        enableCue();
        hitButton.setEnabled(true);
        repaint();
    }

    public void enableHitPosition(){
        hitPosition.setEnabled(true);
    }

    public void enablePowerBar(){
        powerBar.setEnabled(true);
    }

    public void enableElevationBar(){
        elevationBar.setEnabled(true);
    }

    public void ballInHand(){
        notifications.setForeground(Color.RED);
        tableUI.addListener();
        notifications.setText("FOUL!");
    }

    public void placeNineBall(){
        tableUI.placeNineBall();     
    }

    public CueUI getCue(){
        return this.cue;
    }

    public Table getTable(){
        return tableUI.getTable();
    }

    public JTextField getNotif()
    {
        return this.notifications;
    }

    public void paint(Graphics g){
        requestFocusInWindow(true);
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setColor(Color.RED);
        if(turn)
            g2d.fillOval(AccountUI.X_COORDINATE_2 - 50, AccountUI.Y_COORDINATE + 15, 20, 20);
        else
            g2d.fillOval(AccountUI.X_COORDINATE_1 - 50, AccountUI.Y_COORDINATE + 15, 20, 20);

        cue.paintComponent(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==inGameMenuButton)
        {
            this.gameMenu.setVisible(true);
            this.inGameMenuButton.setText("Paused");
            this.inGameMenuButton.setEnabled(false);
            disableCue();
            disableHitButton();
            disableElevationBar();
            disableHitPosition();
            disablePowerBar();
        }
    }
}
