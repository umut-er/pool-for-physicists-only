package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import database.PoolDatabase;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;

public class SignUpFrame extends JFrame implements ActionListener, KeyListener{
    private MenuFrame menuFrame;
    private JLabel player;
    private JLabel username;
    private JLabel password;
    private JTextField usernameText;
    private JTextField passwordText;
    private JButton back;
    private JButton signUp;
    private JLabel warning1;
    private JLabel warning2;
    private JLabel warning3;
    public static final int FRAME_HEIGHT=700;
    public static final int FRAME_WIDTH=1200;
    public static final int BUTTON_WIDTH=200;
    public static final int BUTTON_HEIGHT=50;

    public SignUpFrame(MenuFrame menuFrame)
    {
        this.menuFrame=menuFrame;
        this.player=new JLabel("New Player");
        this.username=new JLabel("Username :");
        this.password=new JLabel("Password :");
        this.usernameText=new JTextField();
        this.passwordText=new JTextField();
        this.signUp=new JButton("Sign-Up");
        this.back=new JButton("Back");

        Font font=new Font("Dialog", Font.BOLD, 20);
        Font font1=new Font("DialogInput", Font.BOLD, 20);
        Font font2=new Font("DialogInput", Font.BOLD, 18);
        Border border=BorderFactory.createLineBorder(Color.DARK_GRAY,3);

        this.signUp.addActionListener(this);
        this.back.addActionListener(this);

        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("8-Ball Pool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0,153,0));
        this.setLayout(null);
        this.setVisible(true);

        this.player.setBounds(500, 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.username.setBounds(325, 175, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.password.setBounds(325, 250, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.usernameText.setBounds(500, 175, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.passwordText.setBounds(500, 250, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.signUp.setBounds(500, 350, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.back.setBounds(500, 450, BUTTON_WIDTH, BUTTON_HEIGHT);

        this.player.setOpaque(true);
        this.player.setVerticalAlignment(JLabel.CENTER);
        this.player.setHorizontalAlignment(JLabel.CENTER);
        this.player.setBackground(Color.WHITE);
        this.player.setForeground(Color.BLACK);
        this.username.setOpaque(false);
        this.username.setVerticalAlignment(JLabel.CENTER);
        this.username.setHorizontalAlignment(JLabel.CENTER);
        this.username.setForeground(Color.WHITE);
        this.password.setOpaque(false);
        this.password.setVerticalAlignment(JLabel.CENTER);
        this.password.setHorizontalAlignment(JLabel.CENTER);
        this.password.setForeground(Color.WHITE);

        this.player.setBorder(border);
        this.usernameText.setBorder(border);
        this.passwordText.setBorder(border);
        this.back.setBorder(border);
        this.signUp.setBorder(border);
        this.player.setFont(font);
        this.username.setFont(font);
        this.password.setFont(font);
        this.usernameText.setFont(font1);
        this.passwordText.setFont(font1);
        this.back.setFont(font);
        this.signUp.setFont(font);

        this.add(player);
        this.add(username);
        this.add(password);
        this.add(usernameText);
        this.add(passwordText);
        this.add(signUp);
        this.add(back);

        this.warning1=new JLabel("Account already exists.");
        this.warning1.setForeground(Color.RED);
        this.warning1.setVerticalAlignment(JLabel.CENTER);
        this.warning1.setHorizontalAlignment(JLabel.CENTER);
        this.warning1.setBounds(400, 550, 2*BUTTON_WIDTH, BUTTON_HEIGHT);
        this.warning1.setFont(font2);
        this.warning1.setVisible(false);
        this.add(warning1);

        this.warning2=new JLabel("Insufficient information.");
        this.warning2.setForeground(Color.RED);
        this.warning2.setVerticalAlignment(JLabel.CENTER);
        this.warning2.setHorizontalAlignment(JLabel.CENTER);
        this.warning2.setBounds(400, 550, 2*BUTTON_WIDTH, BUTTON_HEIGHT);
        this.warning2.setFont(font2);
        this.warning2.setVisible(false);
        this.add(warning2);

        this.warning3=new JLabel("Could not reach to accounts.");
        this.warning3.setForeground(Color.RED);
        this.warning3.setVerticalAlignment(JLabel.CENTER);
        this.warning3.setHorizontalAlignment(JLabel.CENTER);
        this.warning3.setBounds(400, 550, 2*BUTTON_WIDTH, BUTTON_HEIGHT);
        this.warning3.setFont(font2);
        this.warning3.setVisible(false);
        this.add(warning3);

        this.usernameText.addKeyListener(this);
        this.passwordText.addKeyListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==signUp)
        {
            try 
            {
                if(this.usernameText.getText()==null 
                || this.passwordText.getText()==null)
                {
                    this.warning2.setVisible(true);
                }
                else if(PoolDatabase.accountNotExists(this.usernameText.getText()))
                {
                    PoolDatabase.createAccount(this.usernameText.getText(), this.passwordText.getText());
                    this.setVisible(false);
                    this.menuFrame.setVisible(true);
                }
                else
                {
                    this.warning1.setVisible(true);
                }
            } 
            catch (Exception | FirebaseException | JacksonUtilityException e1) 
            {
                this.warning3.setVisible(true);
            }
        }
        else
        {
            this.setVisible(false);
            this.menuFrame.setVisible(true);
            this.warning1.setVisible(false);
            this.warning2.setVisible(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void keyPressed(KeyEvent e){}

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER ) {
            this.signUp.doClick();
        }
    }
}
