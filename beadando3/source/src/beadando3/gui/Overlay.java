package beadando3.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

/**
 * A dark overlay with a transparent circle in it
 */
public class Overlay extends JPanel {

    private Point center;

    /**
     * A unit used to scale grid coordinates to pixels
     */
    public int unitSize;

    public Overlay() {
        setOpaque(false);
    }

    /**
     * Set the new Center of the transparent circle
     * @param center The Coordinates of the new center
     */
    public void setCenter(Point center) {
        this.center = center;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        Rectangle fullScreen = new Rectangle(0, 0, getWidth(), getHeight());

        int circleRadius = unitSize * 3;
        int circleX = (center.x * unitSize) - circleRadius;
        int circleY = (center.y * unitSize) - circleRadius;
        Ellipse2D circle = new Ellipse2D.Double(circleX, circleY, circleRadius*2, circleRadius*2);

        Area blackOverlay = new Area(fullScreen);
        blackOverlay.subtract(new Area(circle));

        g2d.setColor(Color.BLACK);
        g2d.fill(blackOverlay);
    }
}
