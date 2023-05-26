package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

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
    private JLabel backUpQuestion;
    private JLabel question;
    private JTextField usernameText;
    private JTextField passwordText;
    private JTextField questionText;
    private JButton back;
    private JButton signUp;
    private JButton delete;
    private JLabel warning1;
    private JLabel warning2;
    private JLabel warning3;
    private JLabel warning4;
    private String uniqueQuestion;
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
        this.backUpQuestion=new JLabel("Back-Up Question");
        this.questionText=new JTextField();
        this.uniqueQuestion=generateBackUpQuestion();
        this.question=new JLabel(uniqueQuestion);
        this.delete=new JButton("Delete");

        Font font=new Font("Dialog", Font.BOLD, 16);
        Font font1=new Font("DialogInput", Font.PLAIN, 16);
        Font font2=new Font("DialogInput", Font.ITALIC, 16);
        Border border=BorderFactory.createLineBorder(Color.LIGHT_GRAY,3);

        this.signUp.addActionListener(this);
        this.back.addActionListener(this);
        this.delete.addActionListener(this);

        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("8-Ball Pool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0,153,50));
        this.setLayout(null);
        this.setVisible(true);

        this.player.setBounds(400, 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.username.setBounds(225, 175, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.password.setBounds(225, 250, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.usernameText.setBounds(400, 185, BUTTON_WIDTH, 40);
        this.passwordText.setBounds(400, 260, BUTTON_WIDTH, 40);
        this.signUp.setBounds(400, 350, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.back.setBounds(400, 500, BUTTON_WIDTH, BUTTON_HEIGHT);
        //
        this.backUpQuestion.setBounds(700, 175, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.question.setBounds(700, 225, 2*BUTTON_WIDTH, BUTTON_HEIGHT);
        this.questionText.setBounds(700, 275, BUTTON_WIDTH, 40);
        this.delete.setBounds(400, 425, BUTTON_WIDTH, BUTTON_HEIGHT);

        this.player.setOpaque(true);
        this.player.setVerticalAlignment(JLabel.CENTER);
        this.player.setHorizontalAlignment(JLabel.CENTER);
        this.player.setBackground(new Color(224, 224, 224));
        this.player.setForeground(Color.BLACK);
        this.username.setOpaque(false);
        this.username.setVerticalAlignment(JLabel.CENTER);
        this.username.setHorizontalAlignment(JLabel.CENTER);
        this.username.setForeground(Color.WHITE);
        this.password.setOpaque(false);
        this.password.setVerticalAlignment(JLabel.CENTER);
        this.password.setHorizontalAlignment(JLabel.CENTER);
        this.password.setForeground(Color.WHITE);
        this.backUpQuestion.setOpaque(true);
        this.backUpQuestion.setVerticalAlignment(JLabel.CENTER);
        this.backUpQuestion.setHorizontalAlignment(JLabel.CENTER);
        this.backUpQuestion.setForeground(Color.BLACK);
        this.backUpQuestion.setBackground(new Color(224, 224, 224));
        this.question.setOpaque(false);
        this.question.setVerticalAlignment(JLabel.CENTER);
        this.question.setHorizontalAlignment(JLabel.LEFT);
        this.question.setForeground(Color.WHITE);
        this.signUp.setBackground(new Color(224, 224, 224));
        this.delete.setBackground(new Color(224, 224, 224));
        this.back.setBackground(new Color(224, 224, 224));

        this.usernameText.setBorder(border);
        this.passwordText.setBorder(border);
        this.questionText.setBorder(border);
        this.back.setBorder(border);
        this.signUp.setBorder(border);
        this.delete.setBorder(border);
        this.player.setFont(font);
        this.backUpQuestion.setFont(font);
        this.username.setFont(font);
        this.password.setFont(font);
        this.question.setFont(font);
        this.usernameText.setFont(font1);
        this.passwordText.setFont(font1);
        this.questionText.setFont(font1);
        this.back.setFont(font);
        this.signUp.setFont(font);
        this.delete.setFont(font);
        

        this.add(player);
        this.add(username);
        this.add(password);
        this.add(usernameText);
        this.add(passwordText);
        this.add(signUp);
        this.add(back);
        this.add(backUpQuestion);
        this.add(question);
        this.add(questionText);
        this.add(delete);

        this.warning1=new JLabel("Account already exists.");
        this.warning1.setForeground(Color.RED);
        this.warning1.setVerticalAlignment(JLabel.CENTER);
        this.warning1.setHorizontalAlignment(JLabel.CENTER);
        this.warning1.setBounds(300, 550, 2*BUTTON_WIDTH, BUTTON_HEIGHT);
        this.warning1.setFont(font2);
        this.warning1.setVisible(false);
        this.add(warning1);

        this.warning2=new JLabel("Insufficient information.");
        this.warning2.setForeground(Color.RED);
        this.warning2.setVerticalAlignment(JLabel.CENTER);
        this.warning2.setHorizontalAlignment(JLabel.CENTER);
        this.warning2.setBounds(300, 550, 2*BUTTON_WIDTH, BUTTON_HEIGHT);
        this.warning2.setFont(font2);
        this.warning2.setVisible(false);
        this.add(warning2);

        this.warning3=new JLabel("Could not reach to accounts.");
        this.warning3.setForeground(Color.RED);
        this.warning3.setVerticalAlignment(JLabel.CENTER);
        this.warning3.setHorizontalAlignment(JLabel.CENTER);
        this.warning3.setBounds(300, 550, 2*BUTTON_WIDTH, BUTTON_HEIGHT);
        this.warning3.setFont(font2);
        this.warning3.setVisible(false);
        this.add(warning3);

        this.warning4=new JLabel("Account does not exist.");
        this.warning4.setForeground(Color.RED);
        this.warning4.setVerticalAlignment(JLabel.CENTER);
        this.warning4.setHorizontalAlignment(JLabel.CENTER);
        this.warning4.setBounds(300, 550, 2*BUTTON_WIDTH, BUTTON_HEIGHT);
        this.warning4.setFont(font2);
        this.warning4.setVisible(false);
        this.add(warning4);

        this.usernameText.addKeyListener(this);
        this.passwordText.addKeyListener(this);
        this.questionText.addKeyListener(this);
    }

    private String generateBackUpQuestion(){
        Random rand=new Random();
        int randomNum=rand.nextInt(0,6);
        switch(randomNum){
            case(0):
            return "Favourite animal? :";
            case(1):
            return "Favourite subject in school? :";
            case(2):
            return "Favourite celebrity? :";
            case(3):
            return "Favourite food? :";
            case(4):
            return "Favourite holiday place? :";
            case(5):
            return "Favorite brand? :";
        }
        return "";
    }

    private void removeAllWarnings(){
        this.warning1.setVisible(false);
        this.warning2.setVisible(false);
        this.warning3.setVisible(false);
        this.warning4.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==signUp)
        {
            try 
            {
                if(this.usernameText.getText().equals("") 
                || this.passwordText.getText().equals("")
                || this.questionText.getText().equals(""))
                {
                    removeAllWarnings();
                    this.warning2.setVisible(true);
                }
                else if(PoolDatabase.accountNotExists(this.usernameText.getText()))
                {
                    PoolDatabase.createAccount(this.usernameText.getText(), this.passwordText.getText(), uniqueQuestion, this.questionText.getText());
                    this.setVisible(false);
                    this.menuFrame.setVisible(true);
                }
                else
                {
                    removeAllWarnings();
                    this.warning1.setVisible(true);
                }
            } 
            catch (Exception | FirebaseException | JacksonUtilityException e1) 
            {
                removeAllWarnings();
                this.warning3.setVisible(true);
            }
        }
        else if(e.getSource()==delete)
        {
            try 
            {
                if(this.usernameText.getText().equals("") 
                || this.passwordText.getText().equals(""))
                {
                    removeAllWarnings();
                    this.warning2.setVisible(true);
                }
                else if(!PoolDatabase.accountNotExists(this.usernameText.getText()))
                {
                    PoolDatabase.deleteAccount(this.usernameText.getText());
                    this.setVisible(false);
                    this.menuFrame.setVisible(true);
                }
                else
                {
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
