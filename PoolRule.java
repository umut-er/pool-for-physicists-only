import ui.PoolPanel;

public abstract class PoolRule {
    private PoolPanel panel;
    private int currentLowestNumber;
    private boolean turn = false;

    public PoolRule(){

    }

    private void switchTurns(){
        turn = !turn;
        panel.switchTurns();
    }

    abstract boolean foulCheck();
    abstract boolean winCheck();
}
