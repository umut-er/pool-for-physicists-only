package ui;
import java.awt.Color;
import java.awt.Font;
import java.io.UnsupportedEncodingException;

import javax.swing.JLabel;

import database.PoolDatabase;
import net.thegreshams.firebase4j.error.FirebaseException;

public class AccountUI extends JLabel{
    private String accountNameUI;
    private int accountLevelUI;

    public AccountUI(String username) throws UnsupportedEncodingException, FirebaseException{
        this.accountNameUI=username;
        this.accountLevelUI=PoolDatabase.getAccountLevel(username);
        this.setText(accountNameUI+" Level: "+accountLevelUI);
        if(accountLevelUI<10){
            this.changeAccountLabel(null, null, null);
        }
        else if(accountLevelUI<20 && accountLevelUI>=10){
            this.changeAccountLabel(null, null, null);
        }
        else if(accountLevelUI<30 && accountLevelUI>=20){
            this.changeAccountLabel(null, null, null);
        }
        else if(accountLevelUI<40 && accountLevelUI>=30){
            this.changeAccountLabel(null, null, null);
        }
        else if(accountLevelUI<50 && accountLevelUI>=40){
            this.changeAccountLabel(null, null, null);
        }
        else if(accountLevelUI<60 && accountLevelUI>=50){
            this.changeAccountLabel(null, null, null);
        }
        else if(accountLevelUI<70 && accountLevelUI>=60){
            this.changeAccountLabel(null, null, null);
        }
        else if(accountLevelUI<80 && accountLevelUI>=70){
            this.changeAccountLabel(null, null, null);
        }
        else if(accountLevelUI<90 && accountLevelUI>=80){
            this.changeAccountLabel(null, null, null);
        }
        else if(accountLevelUI<100 && accountLevelUI>=90){
            this.changeAccountLabel(null, null, null);
        }
        else{
            this.changeAccountLabel(null, null, null);
        }
        this.setVerticalAlignment(JLabel.CENTER);
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setBounds(0,0,100,50); //Considered later
    }

    public String getAccountName(){
        return this.accountNameUI;
    }

    public int getAccountLevel(){
        return this.accountLevelUI;
    }

    private void changeAccountLabel(Color backgroundColor, Color foregroundColor, Font font)
    {
        this.setBackground(backgroundColor);
        this.setForeground(foregroundColor);
        this.setFont(font);
    }
}

