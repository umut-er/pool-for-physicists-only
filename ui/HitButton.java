package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class HitButton extends JButton implements ActionListener{
    TableUI table;
    HitPosition hitPositionElement;
    ElevationBar elevationElement;
    CueUI cueElement;
    PowerBar powerElement;


    public HitButton(TableUI table, HitPosition hitPosition, ElevationBar elevation, CueUI cue, PowerBar powerBar){
        this.table = table;
        hitPositionElement = hitPosition;
        elevationElement = elevation;
        cueElement = cue;
        powerElement = powerBar;
        this.addActionListener(this);
        this.setText("HIT");
        this.setBackground(Color.RED);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        hit();
    }

    public void hit(){
        double xPosition = HitPosition.getXPos();
        double yPosition = HitPosition.getYPos();
        double powerValue = PowerBar.getPowerValue();
        double elevationAngle = ElevationBar.getAngleValue();
        double directionAngle = cueElement.getAngle();
        table.hitBall(powerValue, directionAngle, elevationAngle, xPosition, yPosition);
    }
}
