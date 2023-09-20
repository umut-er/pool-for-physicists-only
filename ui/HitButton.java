package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import server.PoolClient.PoolPanel;

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
        this.setBackground(Color.WHITE);
    }

    // This is the shot animation.
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
        firePropertyChange("Hit", 0, 1);
    }
}
