package server;

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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gameobjects.Ball;
import gameobjects.Table;
import ui.AccountUI;
import ui.CueUI;
import ui.ElevationBar;
import ui.HitButton;
import ui.HitPosition;
import ui.InGameMenu;
import ui.PowerBar;
import ui.TableUI;

public class PoolClient extends JFrame{
    public static final int HIT_START_INFO = 1110;
    public static final int HIT_END_INFO = 1111;
    public static final int REQUEST_NEW_RACK = 1112;
    public static final int BALL_PLACEMENT_REQUEST = 1113;
    public static final int CURRENT_RACK_INFO = 1114;

    // public static final String IP_ADDRESS = "10.147.17.132";

    public static final int PANEL_HEIGHT=700;
    public static final int PANEL_WIDTH=1200;

    public PoolPanel panel;

    private ClientSideConnection csc;
    private String username = "UUE";
    private int playerID;
    private boolean playerTurn;

    public PoolClient(){ // TODO: Implement a pre-game menu
        connectToServer();      
    }

    public void activateTurn(){
        panel.setTurn(playerTurn);
        if(playerHasTurn(playerID, playerTurn))
            panel.enableTurn();
        else
            panel.disableTurn();
        panel.repaint();
    }

    public boolean playerHasTurn(int playerID, boolean playerTurn){
        return ((playerID == 1 && !playerTurn) || (playerID == 2 && playerTurn));
    }

    public void initializeGUI(String p1, String p2, ArrayList<Ball> rack){
        panel = new PoolPanel(p1, p2, rack);
        add(panel);
        setSize(PANEL_WIDTH, PANEL_HEIGHT);
        setTitle("Pool For Physicists Only");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public void connectToServer(){
        csc = new ClientSideConnection();
    }

    private class ClientSideConnection{
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        private boolean clientResponsible = false;

        public ClientSideConnection(){
            System.out.println("---- Client ----");
            try {
                socket = new Socket(NineBallServer.IP_ADDRESS, 4999);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());

                playerID = dataIn.readInt();
                System.out.println("Connected to server as player #" + playerID);
                dataOut.writeUTF(username);
                dataOut.flush();
            } catch (IOException e) {
                System.out.println("Server connection can't be established.");
                e.printStackTrace();
                System.exit(-1);
            }

            startReceiving();
        }

        public boolean clientWillSendEndOfHit(){
            return clientResponsible;
        }

        private void startReceiving(){
            Thread t = new Thread(new Runnable() {
                public void run(){
                    while(true)
                        detectIncomingData();
                }
            });
            t.start();
        }

        public void detectIncomingData(){
            int n = -1;
            try {
                n = dataIn.readInt();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            if(n == NineBallServer.INITIALIZATION_INFO){
                receiveInitializationInformation();
            }
            else if(n == NineBallServer.TURN_INFO){
                receiveTurnInformation();
            }
            else if(n == NineBallServer.HIT_INFO){
                receiveHitInformation();
            }
            else if(n == NineBallServer.FOUL_INFO){
                receiveFoulInformation();
            }
            else if(n == NineBallServer.WIN_INFO){
                receiveWinInformation();
            }
            else if(n == NineBallServer.BALL_PLACEMENT_INFO){
                receiveBallPlacementInformation();
            }
            else if(n == NineBallServer.RACK_INFO){
                receiveRackInformation();
            }
        }

        @SuppressWarnings("unchecked")
        public void receiveInitializationInformation(){
            String p1 = "", p2 = "";
            ArrayList<Ball> rack = null; 
            try {    
                p1 = dataIn.readUTF();
                p2 = dataIn.readUTF();

                ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
                rack = (ArrayList<Ball>)objectIn.readObject();
                
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            initializeGUI(p1, p2, rack);
            activateTurn();
        } 

        public void receiveTurnInformation(){
            clientResponsible = false;
            try {
                playerTurn = dataIn.readBoolean();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            panel.setTurn(playerTurn);
            panel.repaint();
            sendCurrentRackInformation();
        }

        public void receiveHitInformation(){
            try {
                ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
                HitInfo hit = (HitInfo)objectIn.readObject();
                panel.handleHit(hit);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        public void receiveFoulInformation(){
            try {
                ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
                FoulInfo foul = (FoulInfo)objectIn.readObject();
                if(foul.getPlaceNineBall() && playerHasTurn(playerID, foul.getPlayerToUseFoul())){
                    double[] arr = panel.getTableUI().getPlacement();
                    sendBallPlacementInformation(9, arr[0], arr[1]);
                }
                if(foul.getBallInHand()){
                    panel.getTable().getCueBall().setPocketed(true);
                    panel.setFoulText();
                    panel.repaint();
                    if(playerHasTurn(playerID, foul.getPlayerToUseFoul()))
                        panel.ballInHand();
                }
                else{
                    activateTurn();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        public void receiveWinInformation(){
            boolean winner = false;
            try {
                winner = dataIn.readBoolean();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
            panel.awardWin(winner);
            if(playerHasTurn(playerID, winner)){
                sendRackRequest();
            }
        }

        public void receiveBallPlacementInformation(){
            try {
                int ballNumber = dataIn.readInt();
                double x = dataIn.readDouble(), y = dataIn.readDouble();
                if(ballNumber != 0)
                    panel.getTableUI().placeBall(ballNumber, x, y);
                else{
                    panel.getTableUI().ballInHandPlacer(x, y);
                    panel.repaint();
                    activateTurn();
                }
                panel.repaint();
    
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        @SuppressWarnings("unchecked")
        public void receiveRackInformation(){
            try {
                ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
                ArrayList<Ball> rack = (ArrayList<Ball>)objectIn.readObject();
                panel.getTable().setBallArray(rack);
                panel.getTableUI().setBallUIArray();
                panel.repaint();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        public void sendBallPlacementInformation(int ballNumber, double x, double y){
            try {
                dataOut.writeInt(BALL_PLACEMENT_REQUEST);
                dataOut.writeInt(ballNumber);
                dataOut.writeDouble(x);
                dataOut.writeDouble(y);
                dataOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        public void sendHitInformation(HitInfo info){
            try {
                clientResponsible = true;
                dataOut.writeInt(HIT_START_INFO);

                ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectOut.writeObject(info);

                dataOut.flush();
                objectOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        public void sendHitEndInformation(HitEndInfo info){
            try {
                dataOut.writeInt(HIT_END_INFO);

                ObjectOutputStream objectOut = new ObjectOutputStream(socket.getOutputStream());
                objectOut.writeObject(info);

                dataOut.flush();
                objectOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }

        public void sendRackRequest(){
            try {
                dataOut.writeInt(REQUEST_NEW_RACK);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    
        public void sendCurrentRackInformation(){
            try {
                dataOut.writeInt(CURRENT_RACK_INFO);
                for(Ball ball : panel.getTable().getBallArray()){
                    dataOut.writeDouble(ball.getPosition().getAxis(0));
                    dataOut.writeDouble(ball.getPosition().getAxis(1));
                    dataOut.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

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

        public boolean cueIsFixed = false;

        private MouseAdapter frameListener;
        private MouseAdapter hitPositionListener;
        private MouseAdapter hitButtonListener;
        private MouseAdapter powerBarListener;
        private MouseAdapter elevationBarListener;
        private MouseAdapter inGameMenuButtonListener;
        private MouseAdapter notificationsListener;

        private boolean turn = false;
        
        public PoolPanel(String username1, String username2, ArrayList<Ball> rack){
            setLayout(null);     
            setFocusable(true);
            
            Table table = new Table(rack);
            this.tableUI = new TableUI("table2.png", table, this);
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

            // textarea for notifications is implemented here
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
                    PoolClient.this.repaint();
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

            // Multiplayer listeners.
            hitButton.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt){
                    if(evt.getPropertyName().equals("Hit")){
                        sendHit();
                    }
                }
            });

            tableUI.addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt){
                    if(evt.getPropertyName().equals("Hit over")){
                        sendEndOfHit();
                    }
                    else if(evt.getPropertyName().equals("ball in hand placed")){
                        csc.sendBallPlacementInformation(0, (double)evt.getOldValue(), (double)evt.getNewValue());
                    }
                }
            });

            this.setVisible(true);
        }

        public void sendHit(){
            double xPosition = HitPosition.getXPos();
            double yPosition = HitPosition.getYPos();
            double powerValue = PowerBar.getPowerValue();
            double elevationAngle = ElevationBar.getAngleValue();
            double directionAngle = cue.getAngle();

            HitInfo hit = new HitInfo(xPosition, yPosition, powerValue, elevationAngle, directionAngle);
            csc.sendHitInformation(hit);
        }

        public void sendEndOfHit(){
            if(!csc.clientWillSendEndOfHit())
                return;
            int ballNumber = -1;
            if(getTable().getFirstCollision() != null){
                ballNumber = getTable().getFirstCollision().getSecondBallNumber();
            }
            HitEndInfo hitEnd = new HitEndInfo(getTable().getSmallestNumberedBallAtStart(),
                                                ballNumber,
                                                getTable().ballPocketed(9),
                                                getTable().ballPocketed(0),
                                                getTable().getBallPocketedThisTurn());
            csc.sendHitEndInformation(hitEnd);
        }

        public void handleHit(HitInfo hit){
            double xPosition = hit.getX();
            double yPosition = hit.getY();
            double powerValue = hit.getPower();
            double elevationAngle = hit.getElevation();
            double directionAngle = hit.getDirection();
            powerBar.setValue(PowerBar.INITIAL_VALUE);
            elevationBar.setValue(ElevationBar.INITIAL_VALUE);
            cue.setShotDistance(PowerBar.power/10 + 1);
            cue.setVisibleShotDistance(ElevationBar.getAngleValue());
            hitPosition.setValueOfX(50);
            hitPosition.setValueOfY(50);
            tableUI.hitBall(powerValue, directionAngle, elevationAngle, xPosition, yPosition);
            getNotif().setText("");
        }

        public void setTurn(boolean turn){
            this.turn = turn;
            repaint();
        }

        public TableUI getTableUI(){
            return tableUI;
        }

        public boolean isResumed(){
            return this.inGameMenuButton.isEnabled();
        }

        public void awardWin(boolean winner){
            setWinnerString(winner);
            if(winner){
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

        public void setWinnerString(boolean winner){
            notifications.setForeground(Color.GREEN);
            if(winner){
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
            hitButton.setEnabled(true);
        }

        public void enableTurn(){
            enableCue();
            enableElevationBar();
            enableHitPosition();
            enablePowerBar();
            enableHitButton();
            repaint();
        }

        public void disableTurn(){
            disableCue();
            disableElevationBar();
            disableHitPosition();
            disablePowerBar();
            disableHitButton();
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

        public void setFoulText(){
            notifications.setForeground(Color.RED);
            notifications.setText("FOUL!");
        }

        public void ballInHand(){
            tableUI.addListener();
        }

        public void placeBall(int ballNumber, double x, double y){
            tableUI.placeBall(ballNumber, x, y);
        }

        public CueUI getCue(){
            return this.cue;
        }

        public Table getTable(){
            return tableUI.getTable();
        }

        public JTextField getNotif(){
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
                this.inGameMenuButton.setText("cd");
                this.inGameMenuButton.setEnabled(false);
                disableCue();
                disableHitButton();
                disableElevationBar();
                disableHitPosition();
                disablePowerBar();
            }
        }
    } 

    public static void main(String[] args) {
        new PoolClient();
    }
}
