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
    private JButton backToMenuButton;
    private PoolPanel panel;

    public InGameMenu(PoolPanel panel){
        this.panel=panel;
        Font font=new Font("Dialog", Font.PLAIN, 16);
        this.resumeButton=new JButton("Resume");
        this.backToMenuButton=new JButton("Menu");
        this.setBounds(1000, 375, 100, 100);
        this.setVisible(false);
        this.setLayout(new GridLayout(2,1));
        this.add(resumeButton);
        this.add(backToMenuButton);
        this.setBackground(Color.WHITE);
        this.resumeButton.setBackground(new Color(224, 224, 224));
        this.backToMenuButton.setBackground(new Color(224, 224, 224));
        this.resumeButton.setForeground(Color.BLACK);
        this.backToMenuButton.setForeground(Color.BLACK);
        this.resumeButton.addActionListener(this);
        this.backToMenuButton.addActionListener(this);
        this.backToMenuButton.setFont(font);
        this.resumeButton.setFont(font);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==this.backToMenuButton){
            panel.firePropertyChange("panel exited", 0, 1);
        }
        else{
            this.setVisible(false);
            panel.enableInGameMenuButton();
            panel.enableHitButton();
            panel.enableElevationBar();
            panel.enableHitPosition();
            panel.enablePowerBar();
        }
    } 
}

