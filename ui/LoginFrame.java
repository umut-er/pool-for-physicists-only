package ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

import database.PoolDatabase;
import net.thegreshams.firebase4j.error.FirebaseException;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.UnsupportedEncodingException;

public class LoginFrame extends JFrame implements ActionListener, KeyListener{
    private JLabel player1;
    private JLabel player2;
    private JLabel username1;
    private JLabel password1;
    private JTextField usernameText1;
    private JPasswordField passwordText1;
    private JTextField usernameText2;
    private JPasswordField passwordText2;
    private JButton play;
    private JButton back;
    private MenuFrame menuFrame;
    private JLabel warning1;
    private JLabel warning2;
    private JLabel warning3;
    public static final int FRAME_HEIGHT=700;
    public static final int FRAME_WIDTH=1200;
    public static final int BUTTON_WIDTH=200;
    public static final int BUTTON_HEIGHT=50;

    public LoginFrame(MenuFrame menuFrame){
        Font font=new Font("Dialog", Font.BOLD, 20);
        Font font1=new Font("DialogInput", Font.BOLD, 20);
        Font font2=new Font("DialogInput", Font.BOLD, 18);
        Border border=BorderFactory.createLineBorder(Color.DARK_GRAY,3);
        this.menuFrame=menuFrame;
        this.player1=new JLabel("Player 1");
        this.player2=new JLabel("Player 2");
        this.usernameText1=new JTextField();
        this.passwordText1=new JPasswordField();
        this.usernameText2=new JTextField();
        this.passwordText2=new JPasswordField();
        this.play=new JButton("Play");
        this.back=new JButton("Back");
        this.username1=new JLabel("Username :");
        this.password1=new JLabel("Password :");

        this.play.addActionListener(this);
        this.back.addActionListener(this);

        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("8-Ball Pool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0,153,0));
        this.setLayout(null);
        this.setVisible(true);

        this.player1.setBounds(375, 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.player2.setBounds(625, 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.usernameText1.setBounds(375, 175, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.usernameText2.setBounds(625, 175, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.passwordText1.setBounds(375, 250, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.passwordText2.setBounds(625, 250, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.play.setBounds(500, 350, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.back.setBounds(500, 450, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.username1.setBounds(200, 175, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.password1.setBounds(200, 250, BUTTON_WIDTH, BUTTON_HEIGHT);

        this.player1.setOpaque(true);
        this.player1.setVerticalAlignment(JLabel.CENTER);
        this.player1.setHorizontalAlignment(JLabel.CENTER);
        this.player1.setBackground(Color.WHITE);
        this.player1.setForeground(Color.BLACK);
        this.player2.setOpaque(true);
        this.player2.setVerticalAlignment(JLabel.CENTER);
        this.player2.setHorizontalAlignment(JLabel.CENTER);
        this.player2.setBackground(Color.WHITE);
        this.player2.setForeground(Color.BLACK);
        this.username1.setOpaque(false);
        this.username1.setVerticalAlignment(JLabel.CENTER);
        this.username1.setHorizontalAlignment(JLabel.CENTER);
        this.username1.setForeground(Color.WHITE);
        this.password1.setOpaque(false);
        this.password1.setVerticalAlignment(JLabel.CENTER);
        this.password1.setHorizontalAlignment(JLabel.CENTER);
        this.password1.setForeground(Color.WHITE);
        
        this.player1.setBorder(border);
        this.player2.setBorder(border);
        this.usernameText1.setBorder(border);
        this.usernameText2.setBorder(border);
        this.passwordText2.setBorder(border);
        this.passwordText1.setBorder(border);
        this.play.setBorder(border);
        this.back.setBorder(border);
        this.player1.setFont(font);
        this.player2.setFont(font);
        this.usernameText1.setFont(font1);
        this.usernameText2.setFont(font1);
        this.passwordText1.setFont(font1);
        this.passwordText2.setFont(font1);
        this.back.setFont(font);
        this.play.setFont(font);
        this.username1.setFont(font);
        this.password1.setFont(font);

        this.add(player1);
        this.add(player2);
        this.add(usernameText1);
        this.add(usernameText2);
        this.add(passwordText1);
        this.add(passwordText2);
        this.add(play);
        this.add(back);
        this.add(username1);
        this.add(password1);

        this.warning1=new JLabel("Account/accounts does not exist.");
        this.warning1.setForeground(Color.RED);
        this.warning1.setVerticalAlignment(JLabel.CENTER);
        this.warning1.setHorizontalAlignment(JLabel.CENTER);
        this.warning1.setBounds(400, 550, 2*BUTTON_WIDTH, BUTTON_HEIGHT);
        this.warning1.setFont(font2);
        this.warning1.setVisible(false);
        this.add(warning1);

        this.warning2=new JLabel("Could not reach to accounts.");
        this.warning2.setForeground(Color.RED);
        this.warning2.setVerticalAlignment(JLabel.CENTER);
        this.warning2.setHorizontalAlignment(JLabel.CENTER);
        this.warning2.setBounds(400, 550, 2*BUTTON_WIDTH, BUTTON_HEIGHT);
        this.warning2.setFont(font2);
        this.warning2.setVisible(false);
        this.add(warning2);

        this.warning3=new JLabel("Could not play with the same account.");
        this.warning3.setForeground(Color.RED);
        this.warning3.setVerticalAlignment(JLabel.CENTER);
        this.warning3.setHorizontalAlignment(JLabel.CENTER);
        this.warning3.setBounds(400, 550, 2*BUTTON_WIDTH, BUTTON_HEIGHT);
        this.warning3.setFont(font2);
        this.warning3.setVisible(false);
        this.add(warning3);

        this.usernameText1.addKeyListener(this);
        this.usernameText2.addKeyListener(this);
        this.passwordText1.addKeyListener(this);
        this.passwordText2.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==play){
            try
            {
                String passwordPlayer1="";
                String passwordPlayer2="";
                for(char i:this.passwordText1.getPassword())
                {
                    passwordPlayer1+=i;
                }
                for(char i:this.passwordText2.getPassword())
                {
                    passwordPlayer2+=i;
                }
                if(this.usernameText1.getText().equals("")
                ||passwordPlayer1.equals("")
                ||this.usernameText2.getText().equals("")
                ||passwordPlayer2.equals(""))
                {
                    this.warning1.setVisible(true);
                }
                else if(PoolDatabase.loginAccount(this.usernameText1.getText(), passwordPlayer1)
                && PoolDatabase.loginAccount(this.usernameText2.getText(), passwordPlayer2))
                {
                    if(!this.usernameText1.getText().equals(this.usernameText2.getText()))
                    {
                        this.setVisible(false);
                        // Must be NineBall    //Fix
                        new PoolPanel(this.usernameText1.getText(), this.usernameText2.getText());
                    }
                    else
                    {
                        this.warning3.setVisible(true);
                    }
                }
                else
                {
                    this.warning1.setVisible(true);
                }
            }
            catch (NullPointerException | UnsupportedEncodingException | FirebaseException e1)
            {
                this.warning2.setVisible(true);
            }
        }
        else{
            this.setVisible(false);
            this.menuFrame.setVisible(true);
            this.warning1.setVisible(false);
            this.warning2.setVisible(false);
            this.warning3.setVisible(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER ) {
            this.play.doClick();
        }
    }
}
