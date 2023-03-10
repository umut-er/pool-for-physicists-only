package temporaryUIPanel;

import java.awt.Color;
import javax.swing.JFrame;

public class BilliardsFrame extends JFrame{
    BilliardsPanel panel;
    
    public BilliardsFrame()
    {
        panel = new BilliardsPanel();
        this.setVisible(true);
        this.setSize(Main.TABLE_WIDTH,Main.TABLE_HEIGHT);
        this.setTitle("billiardsPanel");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(0, 170, 0));
        this.add(panel);
    }
}
