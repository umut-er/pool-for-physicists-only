import java.util.ArrayList;

import gameobjects.Ball;
import physics.event.BallBallCollisionEvent;

public class NineBall extends PoolRule{
    public boolean preserveTurn(){
        if(!foulThisTurn)
            return panel.getTable().getBallPocketedThisTurn();
        return false;
    }

    public void dealWithFoulOnce(){
        panel.ballInHand();
        panel.disablePause();
    }

    public void dealWithFoul(Integer code){
        if(code == 2){
            panel.placeBall(9);
        }
        else if(code == 3){
            panel.removeBall(0);
        }
    }

    public ArrayList<Integer> foulCheck(){
        ArrayList<Integer> foulCodes = new ArrayList<Integer>();
        BallBallCollisionEvent firstCollision = panel.getTable().getFirstCollision();
        if(panel.getTable().ballPocketed(9)){
            foulCodes.add(2);
        }
        if(panel.getTable().cueBallPocketed()){
            foulCodes.add(1);
        }
        else if(firstCollision == null || firstCollision.getFirstBallNumber() != 0 || firstCollision.getSecondBallNumber() != currentLowestNumber){
            foulCodes.add(3);
        }
        
        return foulCodes;
    }

    // Assumes there are no fouls.
    public boolean turnStartWinCheck(){
        boolean won = panel.getTable().ballPocketed(9);
        return won;
    }

    public boolean turnEndWinCheck(){
        return false;
    }

    public ArrayList<Ball> rackingRule(){
        return Ball.getStandardNineBallArray();
    }

    public int tableSelector(){
        return 2;
    }

    public void endOfRackInstructions(){};

    public static void main(String[] args){
        new NineBall();
    }
}
