package ui;

import javax.swing.JFrame;

import database.PoolDatabase;

public class SignUpFrame extends JFrame{
    private PoolDatabase database;
    private MenuFrame menuFrame;

    public SignUpFrame(MenuFrame menuFrame, PoolDatabase database)
    {
        this.database=database;
        this.menuFrame=menuFrame;
    }
    // Construct
}
