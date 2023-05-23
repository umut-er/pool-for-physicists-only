package ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

import database.PoolDatabase;
import net.thegreshams.firebase4j.error.FirebaseException;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.UnsupportedEncodingException;

public class MenuFrame extends JFrame implements ActionListener{
    private JButton loginButton;
    private JButton signUpButton;
    private JButton exitButton;
    private JButton forgotButton;
    private JLabel mainTitle;

    private LoginFrame loginFrame;
    private PoolDatabase database;

    public static final int FRAME_HEIGHT=700;
    public static final int FRAME_WIDTH=1200;
    public static final int BUTTON_WIDTH=200;
    public static final int BUTTON_HEIGHT=50;
    public static final int BUTTON_X=500;

    public MenuFrame() throws FirebaseException{
        this.database=new PoolDatabase();
        Font font1=new Font("Serif", Font.BOLD, 30);
        Font font2=new Font("Dialog", Font.BOLD, 20);
        Border border1=BorderFactory.createLineBorder(Color.BLACK,10);
        Border border2=BorderFactory.createLineBorder(Color.DARK_GRAY,3);
        this.loginButton=new JButton("Login");
        this.signUpButton=new JButton("Sign-Up");
        this.exitButton=new JButton("Exit");
        this.mainTitle=new JLabel("Pool For Physicists Only");
        this.forgotButton=new JButton("Forgot Password?");

        this.loginButton.addActionListener(this);
        this.signUpButton.addActionListener(this);
        this.exitButton.addActionListener(this);
        this.forgotButton.addActionListener(this);

        this.loginButton.setBounds(BUTTON_X, 250, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.signUpButton.setBounds(BUTTON_X, 350, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.exitButton.setBounds(BUTTON_X, 550, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.forgotButton.setBounds(BUTTON_X, 450, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.mainTitle.setBounds(400, 100, 400, 100);

        this.mainTitle.setOpaque(true);
        this.mainTitle.setFont(font1);
        this.mainTitle.setVerticalAlignment(JLabel.CENTER);
        this.mainTitle.setHorizontalAlignment(JLabel.CENTER);
        this.mainTitle.setBackground(Color.WHITE);
        this.mainTitle.setForeground(Color.BLACK);
        this.mainTitle.setBorder(border1);
        
        this.loginButton.setBorder(border2);
        this.signUpButton.setBorder(border2);
        this.exitButton.setBorder(border2);
        this.loginButton.setFont(font2);
        this.signUpButton.setFont(font2);
        this.exitButton.setFont(font2);
        this.forgotButton.setBorder(border2);
        this.forgotButton.setFont(font2);

        this.add(loginButton);
        this.add(signUpButton);
        this.add(exitButton);
        this.add(mainTitle);
        this.add(forgotButton);

        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("8-Ball Pool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0,153,0));
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==loginButton){
            loginFrame = new LoginFrame(this);
            loginFrame.addPropertyChangeListener(new PropertyChangeListener(){
                public void propertyChange(PropertyChangeEvent evt){
                    if(evt.getPropertyName().equals("player names entered")){
                        PoolPanel p = null;
                        try{
                            p = new PoolPanel(loginFrame.getUsername1(), loginFrame.getUsername2());
                        }
                        catch(UnsupportedEncodingException | FirebaseException e){
                            e.printStackTrace();
                            System.exit(0);
                        }
                        firePropertyChange("pool panel created", null, p);
                    }
                }
            });
            this.setVisible(false);
        }
        else if(e.getSource()==signUpButton){
            new SignUpFrame(this);
            this.setVisible(false);
        }
        else if(e.getSource()==forgotButton)
        {
            new ForgotPasswordFrame(this);
            this.setVisible(false);
        }
        else{
            System.exit(0);
        }
    }
}
