package ui;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PowerBar extends JSlider{
    private static final int MIN = 0;
    private static final int MAX = 1000;
    private static final int INITIAL_VALUE = 300;
    private static int power = INITIAL_VALUE;
    public PowerBar(){
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
        }
    }
    
    public static double getPowerValue(){
        return power / 150.;  
    }
}
