package ui;

import java.awt.Font;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ElevationBar extends JSlider{
    private static final int MIN = 0;
    private static final int MAX = 80;
    private static final int INITIAL_VALUE = 0;
    private static int angle = INITIAL_VALUE;
    public ElevationBar(){    
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
        }
    }
    
    public static double getAngleValue(){
        return angle * Math.PI / 180;  
    }
}
