import controller.CircleController;
import model.CircleModel;
import view.CircleView;

import javax.swing.*;

/**
 * Main - Application entry point
 *
 * This class demonstrates how to wire up the MVC components:
 * 1. Create the Model
 * 2. Create the View (passing the model)
 * 3. Create the Controller (passing the model)
 * 4. Register the Controller with the View to handle mouse events
 *
 * This follows the same pattern as MVCPatternDemo in the PDF (page 21)
 */
public class Main {

    public static void main(String[] args) {
        // Use SwingUtilities to ensure thread safety
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        System.out.println("=== MVC Circle Example ===");
        System.out.println("Creating MVC components...\n");

        // Step 1: Create the MODEL
        CircleModel model = new CircleModel();
        System.out.println("✓ Model created");

        // Step 2: Create the VIEW (automatically registers itself as listener)
        CircleView view = new CircleView(model);
        System.out.println("✓ View created and registered as listener");

        // Step 3: Create the CONTROLLER
        CircleController controller = new CircleController(model);
        System.out.println("✓ Controller created");

        // Step 4: Connect Controller to View (for mouse input)
        view.addMouseListener(controller);
        System.out.println("✓ Controller registered to handle mouse events");

        // Step 5: Create the JFrame and display
        JFrame frame = new JFrame("MVC Pattern - Circle Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);

        System.out.println("\n=== Application Started ===");
        System.out.println("Instructions:");
        System.out.println("- Left-click to add a circle");
        System.out.println("- Right-click to remove a circle");
        System.out.println("\nWatch the console for event notifications!\n");
    }
}
