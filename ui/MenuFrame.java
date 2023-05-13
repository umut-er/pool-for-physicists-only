package ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame implements ActionListener{
    private JButton loginButton;
    private JButton signUpButton;
    private JButton exitButton;
    private JLabel mainTitle;
    public static final int FRAME_HEIGHT=700;
    public static final int FRAME_WIDTH=1200;
    public static final int BUTTON_WIDTH=200;
    public static final int BUTTON_HEIGHT=50;
    public static final int BUTTON_X=500;

    public MenuFrame(){
        Font font1=new Font("Serif", Font.BOLD, 30);
        Font font2=new Font("Dialog", Font.BOLD, 20);
        Border border1=BorderFactory.createLineBorder(Color.BLACK,10);
        Border border2=BorderFactory.createLineBorder(Color.DARK_GRAY,3);
        this.loginButton=new JButton("Login");
        this.signUpButton=new JButton("Sign-Up");
        this.exitButton=new JButton("Exit");
        this.mainTitle=new JLabel("Pool For Physicists Only");
        this.loginButton.addActionListener(this);
        this.signUpButton.addActionListener(this);
        this.exitButton.addActionListener(this);
        this.loginButton.setBounds(BUTTON_X, 300, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.signUpButton.setBounds(BUTTON_X, 400, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.exitButton.setBounds(BUTTON_X, 500, BUTTON_WIDTH, BUTTON_HEIGHT);
        this.mainTitle.setBounds(400, 150, 400, 100);
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
        this.add(loginButton);
        this.add(signUpButton);
        this.add(exitButton);
        this.add(mainTitle);

        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setTitle("8-Ball Pool");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0,153,0));
        this.setLayout(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==loginButton){
            LoginFrame login=new LoginFrame();
        }
        else if(e.getSource()==signUpButton){
            SignUpFrame signup=new SignUpFrame();
        }
        else{
            System.exit(0);
        }
    }
}
