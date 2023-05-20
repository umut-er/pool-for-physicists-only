package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class HitButton extends JButton implements ActionListener{

    private int xPosition;
    private int yPosition;
    private int powerValue;
    private double angleValue;

    public HitButton()
    {
        this.addActionListener(this);
        this.setText("HIT");
        this.setBackground(Color.RED);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        hit();
    }

    public void hit()
    {
        xPosition = HitPosition.getXPos();
        yPosition = HitPosition.getYPos();
        powerValue = PowerBar.getPowerValue();
        angleValue = ElevationBar.getAngleValue();

    }
}
