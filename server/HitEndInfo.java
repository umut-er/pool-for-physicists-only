package server;

import java.io.Serializable;

public class HitEndInfo implements Serializable{
    private int smallestBallAtTheStart;
    private int firstBallBallContact; 
    private boolean nineBallPotted;
    private boolean cueBallPotted;
    private boolean ballPotted;

    public HitEndInfo(int smallestBall, int firstContact, boolean ninePotted, boolean cuePotted, boolean ballPotted){
        smallestBallAtTheStart = smallestBall;
        firstBallBallContact = firstContact;
        nineBallPotted = ninePotted;
        cueBallPotted = cuePotted;
        this.ballPotted = ballPotted;
    }

    public int getSmallestBall(){return smallestBallAtTheStart;}
    public int getFirstBallContact(){return firstBallBallContact;}
    public boolean getNineBallPotted(){return nineBallPotted;}
    public boolean getCueBallPotted(){return cueBallPotted;}
    public boolean getBallPotted(){return ballPotted;}
}
