package server;

import java.io.Serializable;

public class FoulInfo implements Serializable{
    private boolean ballInHand;
    private boolean placeNineBall;
    private boolean playerToUseFoul;

    public FoulInfo(boolean ballInHand, boolean placeNineBall, boolean playerToUseFoul){
        this.ballInHand = ballInHand;
        this.placeNineBall = placeNineBall;
        this.playerToUseFoul = playerToUseFoul;
    }

    public boolean getBallInHand(){return ballInHand;}
    public boolean getPlaceNineBall(){return placeNineBall;}
    public boolean getPlayerToUseFoul(){return playerToUseFoul;}

    public void setBallInHand(boolean b){ballInHand = b;}
    public void setPlaceNineBall(boolean b){placeNineBall = b;}
    public void setPlayerToUseFoul(boolean b){playerToUseFoul = b;}
}
