package gameobjects;

import ui.TableUI;

public class Table{
    private Ball[] ballArray;
    private Cushion[] cushions; // Will be implemented later.
    private TableUI tableUI;

    public Table(Ball[] ballArray){
        this.ballArray = ballArray;
    }

    public Ball[] getBallArray(){
        return this.ballArray;
    }
}
