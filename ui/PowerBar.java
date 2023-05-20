package ui;

import java.awt.Color;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PowerBar extends JSlider{
    private final int MIN = 0;
    private final int MAX = 100;
    private final int INITIAL_VALUE = 50;
    private static int power;
    public PowerBar(){
        this.setToolTipText("Power Bar");
        this.setMaximum(MAX);
        this.setMinimum(MIN);
        this.setValue(INITIAL_VALUE);
        this.setOrientation(JSlider.HORIZONTAL);
        // this.setBackground(new Color(255, 170, 0));
    }

    class PowerListener implements ChangeListener{
        @Override
        public void stateChanged(ChangeEvent e){
            power = PowerBar.this.getValue();
        }
    }
    
    public static int getPowerValue(){
        return power;  
    }
    
}
