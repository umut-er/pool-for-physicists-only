package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class InGameMenu extends JPanel implements ActionListener{
    private JButton resumeButton;
    private JButton exitButton;
    private PoolPanel panel;

    public InGameMenu(PoolPanel panel){
        this.panel=panel;
        Font font=new Font("Dialog", Font.PLAIN, 16);
        this.resumeButton=new JButton("Resume");
        this.exitButton=new JButton("Exit");
        this.setBounds(1000, 375, 100, 100);
        this.setVisible(false);
        this.setLayout(new GridLayout(2,1));
        this.add(resumeButton);
        this.add(exitButton);
        this.setBackground(Color.WHITE);
        this.resumeButton.setBackground(new Color(224, 224, 224));
        this.exitButton.setBackground(new Color(224, 224, 224));
        this.resumeButton.setForeground(Color.BLACK);
        this.exitButton.setForeground(Color.BLACK);
        this.resumeButton.addActionListener(this);
        this.exitButton.addActionListener(this);
        this.exitButton.setFont(font);
        this.resumeButton.setFont(font);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.exitButton)
        {
            //TODO Exit Fix
        }
        else
        {
            this.setVisible(false);
            this.panel.enableInGameMenuButton();
            this.panel.enableHitButton();
            this.panel.enableElevationBar();
            this.panel.enableHitPosition();
            this.panel.enablePowerBar();
        }
    } 
}

