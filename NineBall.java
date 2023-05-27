import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

import gameobjects.Ball;
import physics.event.BallBallCollisionEvent;
import ui.PoolPanel;

public class NineBall extends JFrame{
    private PoolPanel gamePanel;
    private int currentLowestNumber = 1;
    private boolean turn = false;

    public NineBall(){
        gamePanel = new PoolPanel();
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
                        // System.exit(0);
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
        if(gamePanel.getTable().cueBallPocketed()){
            switchTurns();
            if(gamePanel.getTable().nineBallPocketed()){
                gamePanel.placeNineBall();
            }
            gamePanel.ballInHand();
            return true;
        }

        else if(firstCollision == null || firstCollision.getFirstBallNumber() != 0 || firstCollision.getSecondBallNumber() != currentLowestNumber){
            switchTurns();
            gamePanel.getTable().getBallArray().remove(0);
            gamePanel.getTableUI().getBallUIArray().remove(0);
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
        gamePanel.getTable().setBallArray(Ball.getStandardBallArray());
        gamePanel.getTableUI().setBallUIArray();
        gamePanel.enableHitButton();
        gamePanel.repaint();
    }

    public void switchTurns(){
        turn = !turn;
    }

    public static void main(String[] args) {
        new NineBall();
    }
}
