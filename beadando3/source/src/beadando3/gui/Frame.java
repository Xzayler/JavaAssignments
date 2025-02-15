package beadando3.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * The main window of the game
 */
public class Frame extends JFrame {

    /**
     * Creates a Frame component and assembles the graphical components of the game
     * @param grid The component containing the Cells in a grid pattern
     * @param overlay A component rendered on top of the grid
     * @param menu The top interactable menu with actions
     * @param time A label displaying the passed time
     */
    public Frame(Grid grid, Overlay overlay, Menu menu, JLabel time) {

        JPanel topPanel = new JPanel();
        topPanel.add(menu, BorderLayout.WEST);
        topPanel.add(time, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(600, 600));
        grid.setBounds(0, 0, 600, 600);
        layeredPane.add(grid, Integer.valueOf(0));
        overlay.setBounds(0, 0, 600, 600);
        layeredPane.add(overlay, Integer.valueOf(1));
        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = layeredPane.getSize();
                grid.setBounds(0, 0, size.width, size.height);
                overlay.setBounds(0, 0, size.width, size.height);
            }
        });
        overlay.unitSize = grid.getWidth() / 8;

        getContentPane().add(layeredPane, BorderLayout.CENTER);
        setFrameProps();
    }

    private void setFrameProps() {
        setTitle("Labyrinth");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocation(100,100);
        setSize(600,600);
    }
}
