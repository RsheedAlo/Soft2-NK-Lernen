package controller;

import model.Circle;
import model.CircleModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * CircleController - The CONTROLLER in MVC pattern
 *
 * Responsibilities:
 * 1. Handles user input (mouse clicks via MouseListener)
 * 2. Translates user actions into model updates
 * 3. Modifies the model based on input
 *
 * The Controller is the ONLY component that modifies the model
 * It acts as the mediator between View (user input) and Model (data)
 */
public class CircleController extends MouseAdapter {

    private CircleModel model;
    private Random random;

    public CircleController(CircleModel model) {
        this.model = model;
        this.random = new Random();
    }

    /**
     * Handle mouse clicks
     * - Left click (button 1): Add a circle at mouse position
     * - Right click (button 3): Remove circle at mouse position
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        if (e.getButton() == MouseEvent.BUTTON1) {
            // Left click - ADD circle
            // Random radius between 20 and 50
            int radius = 20 + random.nextInt(31);

            Circle newCircle = new Circle(x, y, radius);
            model.addCircle(newCircle);

            System.out.println("Controller: Added circle at (" + x + ", " + y + ")");

        } else if (e.getButton() == MouseEvent.BUTTON3) {
            // Right click - DELETE circle
            model.removeCircleAt(x, y);

            System.out.println("Controller: Attempted to remove circle at (" + x + ", " + y + ")");
        }
    }

    /**
     * Optional: Provide visual feedback when mouse is pressed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Could add visual feedback here
    }

    /**
     * Optional: Handle mouse release
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Could add drag functionality here
    }
}
