import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ui.PoolPanel;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            PoolPanel poolPanel = new PoolPanel();
            frame.add(poolPanel);
            frame.setSize(PoolPanel.FRAME_WIDTH, PoolPanel.FRAME_HEIGHT);
            frame.setTitle("8-Ball Pool");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            poolPanel.start();
            frame.setVisible(true);
        });
    }
}
