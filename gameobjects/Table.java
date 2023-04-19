package gameobjects;

public class Table{
    private Ball[] ballArray;
    private Cushion[] cushions; // Will be implemented later.

    public Table(Ball[] ballArray){
        this.ballArray = ballArray;
    }

    public Ball[] getBallArray(){
        return this.ballArray;
    }
}
