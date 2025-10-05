package view;

import event.CircleEvent;
import event.CircleListener;
import model.Circle;
import model.CircleModel;

import javax.swing.*;
import java.awt.*;

/**
 * CircleView - The VIEW in MVC pattern
 *
 * Responsibilities:
 * 1. Displays the model data (draws circles on screen)
 * 2. Listens to model changes (implements CircleListener)
 * 3. Reacts to changes by repainting (repaint() in circleChanged method)
 *
 * The View NEVER modifies the model directly - it only observes and displays
 */
public class CircleView extends JPanel implements CircleListener {

    private CircleModel model;

    public CircleView(CircleModel model) {
        this.model = model;

        // Register this view as a listener to the model
        model.addCircleListener(this);

        // Set preferred size for the panel
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
    }

    /**
     * Paint all circles from the model
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw all circles from the model
        g2d.setColor(Color.BLUE);
        for (Circle circle : model.getCircles()) {
            int diameter = circle.getRadius() * 2;
            g2d.fillOval(
                circle.getX() - circle.getRadius(),
                circle.getY() - circle.getRadius(),
                diameter,
                diameter
            );

            // Draw outline
            g2d.setColor(Color.BLACK);
            g2d.drawOval(
                circle.getX() - circle.getRadius(),
                circle.getY() - circle.getRadius(),
                diameter,
                diameter
            );
            g2d.setColor(Color.BLUE);
        }

        // Display instructions
        g2d.setColor(Color.BLACK);
        g2d.drawString("Left-click: Add circle | Right-click: Remove circle", 10, 20);
        g2d.drawString("Circles in model: " + model.getCircles().size(), 10, 40);
    }

    /**
     * LISTENER METHOD - Called when the model changes
     * This is the Observer pattern callback
     *
     * When the model fires an event, this method is called automatically
     * The view responds by repainting itself to show the updated data
     */
    @Override
    public void circleChanged(CircleEvent event) {
        // Print event for debugging
        System.out.println("View notified: " + event);

        // Repaint the view to show the changes
        repaint();
    }
}
