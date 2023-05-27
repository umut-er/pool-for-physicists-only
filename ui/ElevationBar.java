package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ElevationBar extends JSlider{
    PoolPanel pool;
    CueUI cue;
    
    private static final double EYE_COEFFICIENT = 0.8; 
    private static final int MIN = 0;
    private static final int MAX = 45;
    public static final int INITIAL_VALUE = 0;
    private static int angle = INITIAL_VALUE;
    private static double poolDiameter = Math.sqrt(Math.pow(PoolPanel.PANEL_HEIGHT, 2) + Math.pow(PoolPanel.PANEL_WIDTH, 2));
    
    public ElevationBar(CueUI cue, PoolPanel pool){ 
        this.setBackground(Color.DARK_GRAY);   
        this.cue = cue;
        this.pool = pool;
        this.addChangeListener(new ElevationListener());
        this.setMaximum(MAX);
        this.setMinimum(MIN);
        this.setValue(INITIAL_VALUE);
        this.setOrientation(JSlider.HORIZONTAL);

        this.setPaintTicks(true);
        this.setMinorTickSpacing((MAX - MIN)/6);
        this.setPaintTrack(true);
        this.setPaintLabels(true);
        this.setFont(new Font("MV Boli",Font.PLAIN,15));    
    }
    
    class ElevationListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e) {
            angle = ElevationBar.this.getValue();
            cue.setVisibleShotDistance(getAngleValue());
            cue.setVisibleBlueTipHeight(cue.getCueUpperWidth() * Math.cos(getAngleValue()));
            cue.setVisibleHeight(cue.getCueStickHeight() * Math.cos(getAngleValue()));
            cue.setVisibleLowerWidth(EYE_COEFFICIENT * poolDiameter * cue.getCueLowerWidth() / (EYE_COEFFICIENT * poolDiameter - (cue.getCueStickHeight() + cue.getShotDistance()) * Math.sin(getAngleValue())));
            pool.repaint();
        }
    }
    
    public static double getAngleValue(){
        return angle * Math.PI / 180;  
    }
}
