import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.UnsupportedEncodingException;

import javax.swing.JFrame;

import net.thegreshams.firebase4j.error.FirebaseException;
import physics.event.BallBallCollisionEvent;
import ui.MenuFrame;
import ui.PoolPanel;

public class NineBall extends JFrame{
    private PoolPanel gamePanel;
    private int currentLowestNumber = 1;
    private boolean turn = false;
    //////TEMPORARY
    private String username1;
    private String username2;

    public NineBall(String username1, String username2) throws UnsupportedEncodingException, FirebaseException{
        this.username1=username1;
        this.username2=username2;
        gamePanel = new PoolPanel(username1,username2);
        //////////////
        gamePanel.getTableUI().addPropertyChangeListener(new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt){
                if(evt.getPropertyName() == "turn start"){
                    currentLowestNumber = gamePanel.getTable().getLowestNumberOnTable();
                }
                if(evt.getPropertyName() == "turn over"){
                    boolean foulOccured = foulCheck();
                    if(foulOccured)
                        return;
                    boolean win = winCheck();
                    if(!win){
                        switchTurns();
                        gamePanel.enableHitButton();
                    }
                    else{
                        resetTable();
                    }
                }
            }
        });
        add(gamePanel);
        setSize(PoolPanel.PANEL_WIDTH, PoolPanel.PANEL_HEIGHT);
        setTitle("8-Ball Pool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    public boolean foulCheck(){
        BallBallCollisionEvent firstCollision = gamePanel.getTable().getFirstCollision();
        if(firstCollision == null || firstCollision.getFirstBallNumber() != 0 || firstCollision.getSecondBallNumber() != currentLowestNumber){
            System.out.println("FOUL");
            switchTurns();
            if(gamePanel.getTable().nineBallPocketed()){
                gamePanel.placeNineBall();
            }
            gamePanel.ballInHand();
            return true;
        }

        if(gamePanel.getTable().cueBallPocketed()){
            System.out.println("FOUL");
            switchTurns();
            if(gamePanel.getTable().nineBallPocketed()){
                gamePanel.placeNineBall();
            }
            gamePanel.ballInHand();
            return true;
        }

        return false;
    }

    // Assumes there is no fouls.
    public boolean winCheck(){
        boolean won = gamePanel.getTable().nineBallPocketed();
        return won;
    }

    public void resetTable(){
        // TODO: Complete
    }

    public void switchTurns(){
        turn = !turn;
    }

    public static void main(String[] args) throws FirebaseException {
        //////
        new MenuFrame();
        ///////
    }
}
