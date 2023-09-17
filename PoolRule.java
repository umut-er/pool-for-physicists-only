import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import gameobjects.Ball;
import ui.PoolPanel;

public abstract class PoolRule extends JFrame{
    protected PoolPanel panel;
    protected int currentLowestNumber;
    protected boolean turn = false;
    protected boolean foulThisTurn = false;
    private String userName1 = "Player 1"; // tentative
    private String userName2 = "Player 2"; // tentative

    public PoolRule(){
        // getUserNames(); Later on when local multiplayer gets added
        initializePoolFrame();
    }

    public void initializePoolFrame(){
        panel = new PoolPanel(userName1, userName2, rackingRule());

        this.panel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt){
                if(evt.getPropertyName().equals("hit button enabled")){
                    startOfTurnInstructions();
                    if(turnStartWinCheck())
                        processWin();
                }
            }
        });

        this.panel.getTableUI().addPropertyChangeListener(new PropertyChangeListener(){
            @Override
            public void propertyChange(PropertyChangeEvent evt){
                if(evt.getPropertyName().equals("turn start")){
                    currentLowestNumber = panel.getTable().getLowestNumberOnTable();
                }
                else if(evt.getPropertyName().equals("turn over")){
                    ArrayList<Integer> foulCodes = foulCheck();
                    if(foulCodes.size() != 0){
                        foulThisTurn = true;
                        for(Integer code : foulCodes){
                            dealWithFoul(code);
                        }
                        switchTurns();
                        resetTurn();
                        dealWithFoulOnce();
                    }
                    else{
                        if(turnEndWinCheck())
                            processWin();
                        if(!preserveTurn())
                            switchTurns();
                        resetTurn();
                        panel.enableHitButton();  
                    }
                }
            }
        });

        add(panel);
        setSize(PoolPanel.PANEL_WIDTH, PoolPanel.PANEL_HEIGHT);
        setTitle("Pool For Physicists Only");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    private void processWin(){
        panel.awardWin();
        endOfRackInstructions();
        resetTable();
    }

    private void resetTurn(){
        endOfTurnInstructions();
        foulThisTurn = false;
    }

    private void switchTurns(){
        turn = !turn;
        panel.switchTurns();
    }

    public void resetTable(){
        panel.getTable().setBallArray(rackingRule());
        panel.getTableUI().setBallUIArray();
        panel.enableHitButton();
        panel.repaint();
    }

    abstract boolean preserveTurn(); // do you want to preserve the turn?
    abstract ArrayList<Integer> foulCheck(); // give fouls custom codes
    abstract void dealWithFoulOnce(); // things to do (ONLY ONCE) regardless of which foul happened
    abstract void dealWithFoul(Integer foulCode); // deal with the fouls you created
    abstract boolean turnStartWinCheck(); // win checking, assuming no foul happened
    abstract boolean turnEndWinCheck(); // win checking, assuming no foul happened
    abstract ArrayList<Ball> rackingRule(); // a racking rule
    abstract int tableSelector(); // table selecting, currently not functional
    abstract void startOfTurnInstructions();
    abstract void endOfTurnInstructions(); // what to do before starting the other turn.
    abstract void endOfRackInstructions(); // what to do end of each rack, this is mainly for your local variables
}
