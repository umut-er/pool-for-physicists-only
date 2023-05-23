package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.UnsupportedEncodingException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import database.PoolDatabase;
import net.thegreshams.firebase4j.error.FirebaseException;
import net.thegreshams.firebase4j.error.JacksonUtilityException;

public class ForgotPasswordFrame extends JFrame implements ActionListener, KeyListener{
    private MenuFrame menuFrame;
    private JLabel player;
    private JLabel backUpQuestion;
    private JLabel username;
    private JLabel password;
    private JLabel question;
    private JTextField usernameText;
    private JTextField passwordText;
    private JTextField questionText;
    private JButton back;
    private JButton changePassword;
    private JButton enter;
    private JLabel warning2;
    private JLabel warning3;
    private JLabel warning4;
    public static final int FRAME_HEIGHT=700;
    public static final int FRAME_WIDTH=1200;
    public static final int BUTTON_WIDTH=200;
    public static final int BUTTON_HEIGHT=50;

    public ForgotPasswordFrame(MenuFrame menuFrame)
    {
        this.menuFrame=menuFrame;
        this.player=new JLabel("Player");
        this.username=new JLabel("Username :");
        this.password=new JLabel("New Password :");
        this.usernameText=new JTextField();
        this.passwordText=new JTextField();
        this.enter=new JButton("Enter");
        this.back=new JButton("Back");

        this.backUpQuestion=new JLabel("Back-up Question");
        this.question=new JLabel();
        this.questionText=new JTextField();
        this.changePassword=new JButton("Change Password");
        this.backUpQuestion.setVisible(false);
        this.question.setVisible(false);
        this.questionText.setVisible(false);
        this.changePassword.setVisible(false);


        Font font=new Font("Dialog", Font.BOLD, 20);
        Font font1=new Font("DialogInput", Font.BOLD, 20);
        Font font2=new Font("DialogInput", Font.BOLD, 18);
        Border border=BorderFactory.createLineBorder(Color.DARK_GRAY,3);

        this.changePassword.addActionListener(this);
        this.back.addActionListener(this);
        this.enter.addActionListener(this);

        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("8-Ball Pool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0,153,0));
        this.setLayout(null);
        this.setVisible(true);

        this.backUpQuestion.setBounds(800, 175, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.player.setBounds(400, 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.username.setBounds(180, 175, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.password.setBounds(180, 250, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.question.setBounds(800, 250, 300, BUTTON_HEIGHT);
        this.questionText.setBounds(800, 300, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.usernameText.setBounds(400, 175, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.passwordText.setBounds(400, 250, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.changePassword.setBounds(800, 375, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.back.setBounds(400, 425, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.enter.setBounds(400, 350, BUTTON_WIDTH, BUTTON_HEIGHT);

        this.player.setOpaque(true);
        this.player.setVerticalAlignment(JLabel.CENTER);
        this.player.setHorizontalAlignment(JLabel.CENTER);
        this.player.setBackground(Color.WHITE);
        this.player.setForeground(Color.BLACK);
        this.backUpQuestion.setOpaque(true);
        this.backUpQuestion.setVerticalAlignment(JLabel.CENTER);
        this.backUpQuestion.setHorizontalAlignment(JLabel.CENTER);
        this.backUpQuestion.setBackground(Color.WHITE);
        this.backUpQuestion.setForeground(Color.BLACK);
        this.username.setOpaque(false);
        this.username.setVerticalAlignment(JLabel.CENTER);
        this.username.setHorizontalAlignment(JLabel.RIGHT);
        this.username.setForeground(Color.WHITE);
        this.password.setOpaque(false);
        this.password.setVerticalAlignment(JLabel.CENTER);
        this.password.setHorizontalAlignment(JLabel.RIGHT);
        this.password.setForeground(Color.WHITE);
        this.question.setOpaque(false);
        this.question.setVerticalAlignment(JLabel.CENTER);
        this.question.setHorizontalAlignment(JLabel.LEFT);
        this.question.setForeground(Color.WHITE);

        this.player.setBorder(border);
        this.backUpQuestion.setBorder(border);
        this.usernameText.setBorder(border);
        this.passwordText.setBorder(border);
        this.questionText.setBorder(border);
        this.back.setBorder(border);
        this.changePassword.setBorder(border);
        this.enter.setBorder(border);
        this.player.setFont(font);
        this.backUpQuestion.setFont(font);
        this.username.setFont(font);
        this.password.setFont(font);
        this.question.setFont(font);
        this.usernameText.setFont(font1);
        this.passwordText.setFont(font1);
        this.questionText.setFont(font1);
        this.back.setFont(font);
        this.changePassword.setFont(font);
        this.enter.setFont(font);

        this.add(player);
        this.add(username);
        this.add(password);
        this.add(usernameText);
        this.add(passwordText);
        this.add(changePassword);
        this.add(back);
        this.add(question);
        this.add(questionText);
        this.add(backUpQuestion);
        this.add(enter);

        this.warning4=new JLabel("Account does not exist.");
        this.warning4.setForeground(Color.RED);
        this.warning4.setVerticalAlignment(JLabel.CENTER);
        this.warning4.setHorizontalAlignment(JLabel.CENTER);
        this.warning4.setBounds(400, 575, 2*BUTTON_WIDTH, BUTTON_HEIGHT);
        this.warning4.setFont(font2);
        this.warning4.setVisible(false);
        this.add(warning4);

        this.warning2=new JLabel("Insufficient information.");
        this.warning2.setForeground(Color.RED);
        this.warning2.setVerticalAlignment(JLabel.CENTER);
        this.warning2.setHorizontalAlignment(JLabel.CENTER);
        this.warning2.setBounds(400, 575, 2*BUTTON_WIDTH, BUTTON_HEIGHT);
        this.warning2.setFont(font2);
        this.warning2.setVisible(false);
        this.add(warning2);

        this.warning3=new JLabel("Could not reach to accounts.");
        this.warning3.setForeground(Color.RED);
        this.warning3.setVerticalAlignment(JLabel.CENTER);
        this.warning3.setHorizontalAlignment(JLabel.CENTER);
        this.warning3.setBounds(400, 650, 2*BUTTON_WIDTH, BUTTON_HEIGHT);
        this.warning3.setFont(font2);
        this.warning3.setVisible(false);
        this.add(warning3);

        this.usernameText.addKeyListener(this);
        this.passwordText.addKeyListener(this);
        this.questionText.addKeyListener(this);
    }

    private void removeAllWarnings(){
        this.warning2.setVisible(false);
        this.warning3.setVisible(false);
        this.warning4.setVisible(false);
    }

    private void setVisibleBackUpQuestion(String username) throws UnsupportedEncodingException, FirebaseException{
        this.backUpQuestion.setVisible(true);
        this.question.setVisible(true);
        this.questionText.setVisible(true);
        this.changePassword.setVisible(true);
        this.question.setText(PoolDatabase.getAccountBackUpQuestion(username));
    }

    private void setInvisibleBackUpQuestion(){
        this.backUpQuestion.setVisible(false);
        this.question.setVisible(false);
        this.questionText.setVisible(false);
        this.changePassword.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==changePassword)
        {
            try 
            {
                if(this.usernameText.getText().equals("") 
                || this.passwordText.getText().equals("")
                || this.questionText.getText().equals(""))
                {
                    setInvisibleBackUpQuestion();
                    removeAllWarnings();
                    this.warning2.setVisible(true);
                }
                else if(!PoolDatabase.accountNotExists(this.usernameText.getText()))
                {
                    if(PoolDatabase.doesBackUpAnswersMatch(this.usernameText.getText(), this.questionText.getText()))
                    {
                        PoolDatabase.changePassword(this.usernameText.getText(), this.passwordText.getText());
                        this.setVisible(false);
                        this.menuFrame.setVisible(true);
                    }
                    else
                    {
                        removeAllWarnings();
                        this.warning4.setVisible(true);
                    }
                }
                else
                {
                    setInvisibleBackUpQuestion();
                    removeAllWarnings();
                    this.warning4.setVisible(true);
                }
            }
            catch (Exception | FirebaseException | JacksonUtilityException e1) 
            {
                removeAllWarnings();
                this.warning3.setVisible(true);
            } 
        }
        else if(e.getSource()==enter)
        {
            try 
            {
                if(this.usernameText.getText().equals("") 
                || this.passwordText.getText().equals(""))
                {
                    setInvisibleBackUpQuestion();
                    removeAllWarnings();
                    this.warning2.setVisible(true);
                }
                else if(!PoolDatabase.accountNotExists(this.usernameText.getText()))
                {
                    setVisibleBackUpQuestion(this.usernameText.getText());
                }
                else
                {
                    setInvisibleBackUpQuestion();
                    removeAllWarnings();
                    this.warning4.setVisible(true);
                }
            }
            catch (Exception | FirebaseException e1) 
            {
                removeAllWarnings();
                this.warning3.setVisible(true);
            }
        }
        else
        {
            this.setVisible(false);
            this.menuFrame.setVisible(true);
            removeAllWarnings();
        }
    }

    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void keyPressed(KeyEvent e){}

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER ) {
            if(this.enter.isVisible())
            {
                this.enter.doClick();
            }
            else
            {
                this.changePassword.doClick();
            }
        }
    }
}
