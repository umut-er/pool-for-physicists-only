import java.util.ArrayList;

import gameobjects.Ball;
import physics.event.BallBallCollisionEvent;

public class EightBall extends PoolRule{
    private int[] ballSet1 = {1, 2, 3, 4, 5, 6, 7};
    private int[] ballSet2 = {9, 10, 11, 12, 13, 14, 15};

    private boolean selection = false;
    private boolean teamSelected = false;

    public boolean preserveTurn(){
        if(foulThisTurn)
            return false;
        if(!teamSelected)
            return panel.getTable().getBallPocketedThisTurn();

        boolean lookup = selection ^ turn;
        for(Integer ballNumber : panel.getTable().getPocketedBalls()){
            if(lookup){
                if(ballNumber >= 9 && ballNumber <= 15){
                    return true;
                }
            }
            else{
                if(ballNumber <= 7 && ballNumber >= 1){
                    return true;
                }
            }
        }
        return false;
    
}

    public ArrayList<Integer> foulCheck(){
        ArrayList<Integer> foulCodes = new ArrayList<Integer>();
        BallBallCollisionEvent firstCollision = panel.getTable().getFirstCollision();
        if(panel.getTable().cueBallPocketed()){
            foulCodes.add(1);
        }
        else if(firstCollision == null || (!teamSelected && firstCollision.getSecondBallNumber() == 8)){
            foulCodes.add(2);
        }
        else if(teamSelected){
            int ballInGroup = -1;
            if(firstCollision.getSecondBallNumber() < 8)
                ballInGroup = 1;
            else if(firstCollision.getSecondBallNumber() > 8)
                ballInGroup = 2;
            else
                ballInGroup = 3;

            if(ballInGroup == 1 && (selection ^ turn))
                foulCodes.add(3);
            else if(ballInGroup == 2 && !(selection ^ turn))
                foulCodes.add(4);
            else if(ballInGroup == 3){
                boolean allPocketed = true;
                if(selection ^ turn){
                    for(int i = 0; i < 7; i++){
                        allPocketed &= panel.getTable().ballPocketed(ballSet2[i]);
                    }
                }
                else{
                    for(int i = 0; i < 7; i++){
                        allPocketed &= panel.getTable().ballPocketed(ballSet1[i]);
                    }
                }

                if(!allPocketed){
                    foulCodes.add(5);
                }
            }
        }
        else if(!teamSelected && panel.getTable().getPocketedBalls().size() > 0 && foulCodes.size() == 0){
            System.out.println("Pocketed ball was number: " + panel.getTable().getPocketedBalls().get(0));
            teamSelected = true;
            boolean ballGroup = false;
            if(panel.getTable().getPocketedBalls().get(0) <= 7)
                ballGroup = false;
            else
                ballGroup = true;

            selection = ballGroup ^ turn;

            System.out.println("SELECTION DONE: " + selection);
        }

        if(foulCodes.size() > 0 && panel.getTable().ballPocketed(8)){
            foulCodes.clear();
            foulThisTurn = true;
        }

        return foulCodes;
    }

    public void dealWithFoulOnce(){
        panel.ballInHand();
        panel.disablePause();
    }

    public void dealWithFoul(Integer code){
        panel.removeBall(0);
    }

    public boolean turnStartWinCheck(){
        // start of turn
        return panel.getTable().ballPocketed(8);
    }

    public boolean turnEndWinCheck(){
        boolean allPocketed = true;
        if(selection ^ turn){
            for(int i = 0; i < 7; i++){
                allPocketed &= panel.getTable().ballPocketed(ballSet2[i]);
            }
        }
        else{
            for(int i = 0; i < 7; i++){
                allPocketed &= panel.getTable().ballPocketed(ballSet1[i]);
            }
        }

        if(allPocketed){
            return panel.getTable().ballPocketed(8) && !foulThisTurn;
        }

        return false;
    }

    public ArrayList<Ball> rackingRule(){
        return Ball.getStandardEightBallArray();
    }

    public int tableSelector(){
        return 2;
    }

    public void startOfTurnInstructions(){};

    public void endOfTurnInstructions(){};

    public void endOfRackInstructions(){
        selection = false;
        teamSelected = false;
    }

    public static void main(String[] args) {
        new EightBall();
    }
}
