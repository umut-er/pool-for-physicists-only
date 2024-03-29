package ui;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class AccountUI extends JLabel{
    private String accountNameUI;
    private int accountLevelUI;
    public static final int Y_COORDINATE=30;
    public static final int WIDTH=200;
    public static final int HEIGHT=50;
    public static final int X_COORDINATE_1=315; // 365
    public static final int X_COORDINATE_2=635; // 585

    public AccountUI(String username){
        Font font=new Font("Dialog", Font.BOLD, 14);
        this.accountNameUI=username;
        this.accountLevelUI = 0;
        this.setText(accountNameUI + "   Wins: " + accountLevelUI);
        this.setOpaque(true);
        this.setFont(font);
        setAccountLabels();
        this.setVerticalAlignment(JLabel.CENTER);
        this.setHorizontalAlignment(JLabel.CENTER);
    }

    public void setAccountLabels(){
        if(accountLevelUI<10)
            this.changeAccountLabel(Color.WHITE, Color.BLACK);
        else if(accountLevelUI<20 && accountLevelUI>=10)
            this.changeAccountLabel(Color.BLUE, Color.WHITE);
        else if(accountLevelUI<30 && accountLevelUI>=20)
            this.changeAccountLabel(Color.BLUE, Color.YELLOW);
        else if(accountLevelUI<40 && accountLevelUI>=30)
            this.changeAccountLabel(Color.MAGENTA, Color.YELLOW);
        else if(accountLevelUI<50 && accountLevelUI>=40)
            this.changeAccountLabel(Color.MAGENTA, Color.CYAN);
        else if(accountLevelUI<60 && accountLevelUI>=50){
            this.changeAccountLabel(Color.RED, Color.GREEN);
        }
        else if(accountLevelUI<70 && accountLevelUI>=60)
            this.changeAccountLabel(Color.RED, Color.CYAN);
        else if(accountLevelUI<80 && accountLevelUI>=70)
            this.changeAccountLabel(Color.DARK_GRAY, Color.CYAN);
        else if(accountLevelUI<90 && accountLevelUI>=80)
            this.changeAccountLabel(Color.DARK_GRAY, Color.GREEN);
        else if(accountLevelUI<100 && accountLevelUI>=90)
            this.changeAccountLabel(Color.RED, Color.BLACK);
        else
            this.changeAccountLabel(Color.BLACK, Color.RED);
    }

    public String getAccountName(){
        return this.accountNameUI;
    }

    public int getAccountLevel(){
        return this.accountLevelUI;
    }

    public void levelUpAccount(){
        accountLevelUI++;
        setAccountLabels();
        setText(accountNameUI + "   Wins: " + accountLevelUI);
    }

    private void changeAccountLabel(Color backgroundColor, Color foregroundColor)
    {
        this.setBackground(backgroundColor);
        this.setForeground(foregroundColor);
        Border border=BorderFactory.createLineBorder(foregroundColor,5);
        this.setBorder(border);
    }
}

