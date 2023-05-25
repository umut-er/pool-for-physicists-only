package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;
import javax.swing.JButton;

public class HitButton extends JButton implements ActionListener{
    
    public static final int MAX_SHOT_DISTANCE = PowerBar.MAX/10 +1;

    Timer timer;
    PoolPanel pool;
    TableUI table;
    HitPosition hitPositionElement;
    ElevationBar elevationElement;
    CueUI cueElement;
    PowerBar powerElement;

    public HitButton(TableUI table, HitPosition hitPosition, ElevationBar elevation, CueUI cue, PowerBar powerBar, PoolPanel pool){
        this.table = table;
        this.pool = pool;
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
        timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cueElement.getShotDistance() >= 20){
                    cueElement.setShotDistance(cueElement.getShotDistance() - 20);
                    cueElement.setVisibleShotDistance(ElevationBar.getAngleValue());
                    pool.repaint();
                }
                else{
                    cueElement.setShotDistance(0);
                    cueElement.setVisibleShotDistance(ElevationBar.getAngleValue());
                    pool.repaint();
                    timer.stop();
                    hit();
                }
            }
        });
        timer.start();
    }

    public void hit(){
        double xPosition = HitPosition.getXPos();
        double yPosition = HitPosition.getYPos();
        double powerValue = PowerBar.getPowerValue();
        double elevationAngle = ElevationBar.getAngleValue();
        double directionAngle = cueElement.getAngle();
        powerElement.setValue(PowerBar.INITIAL_VALUE);
        elevationElement.setValue(ElevationBar.INITIAL_VALUE);
        cueElement.setShotDistance(PowerBar.power/10 + 1);
        cueElement.setVisibleShotDistance(ElevationBar.getAngleValue());
        hitPositionElement.setValueOfX(50);
        hitPositionElement.setValueOfY(50);
        table.hitBall(powerValue, directionAngle, elevationAngle, xPosition, yPosition);
    }
}
