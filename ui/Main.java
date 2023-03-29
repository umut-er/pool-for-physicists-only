package ui;

public class Main {
    public static void main(String[] args) {
        PoolFrame frame=new PoolFrame();
        //Example/////////////////////////////////////
        frame.table.getBallsOnTable().get(0).setActionCoordinates(210, 210);
        frame.table.getBallsOnTable().get(0).setActionCoordinates(220, 220);
        frame.table.getBallsOnTable().get(0).setActionCoordinates(230, 230);
        frame.table.getBallsOnTable().get(0).setActionCoordinates(240, 240);
        frame.table.getBallsOnTable().get(0).setActionCoordinates(250, 250);
        ////////////////////////////////////////////////
        frame.table.getBallsOnTable().get(0).move();
    }
}
