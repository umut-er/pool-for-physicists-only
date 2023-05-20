package ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ElevationBar extends JSlider{
    private final int MIN = 0;
    private final int MAX = 90;
    private final int INITIAL_VALUE = 45;
    private static double angle;
    public ElevationBar(){
        
        PowerListener pl = new PowerListener();
        this.addChangeListener(pl);
        this.setMaximum(MAX);
        this.setMinimum(MIN);
        this.setValue(INITIAL_VALUE);
        this.setOrientation(JSlider.HORIZONTAL);

        this.setPaintTicks(true);
        this.setMinorTickSpacing((MAX - MIN)/6);
        this.setPaintTrack(true);
        this.setPaintLabels(true);
        this.setFont(new Font("MV Boli",Font.PLAIN,15));
        // this.setBackground(new Color(255, 170, 0));
        

    }
    class PowerListener implements ChangeListener{

        @Override
        public void stateChanged(ChangeEvent e) {
            angle = ElevationBar.this.getValue();
        }

        
    }
    
    public static double getAngleValue()
    {
        return angle;  
    }
}
