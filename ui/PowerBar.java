package ui;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PowerBar extends JSlider{
    private CueUI cue;
    private PoolPanel pool;
    private static final int MIN = 0;
    public static final int MAX = 1000;
    public static final int INITIAL_VALUE = 300;
    public static int power = INITIAL_VALUE;
    public PowerBar(CueUI cue, PoolPanel pool){
        this.pool = pool;
        this.cue = cue;
        this.setToolTipText("Power Bar");
        this.setMaximum(MAX);
        this.setMinimum(MIN);
        this.setValue(INITIAL_VALUE);
        this.setOrientation(JSlider.HORIZONTAL);
        this.addChangeListener(new PowerListener());
    }

    class PowerListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e){
            power = PowerBar.this.getValue();
            cue.setShotDistance(power/10 + 1);
            cue.setVisibleShotDistance(ElevationBar.getAngleValue());
            pool.repaint();
        }
    }
    
    public static double getPowerValue(){
        return power / 150.;  
    }
}
