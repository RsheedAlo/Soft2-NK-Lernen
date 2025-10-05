package model;

import event.CircleEvent;
import event.CircleListener;

import java.util.ArrayList;
import java.util.List;

/**
 * CircleModel - The MODEL in MVC pattern
 *
 * Responsibilities:
 * 1. Holds the data (list of circles)
 * 2. Provides business logic (add/remove circles)
 * 3. Manages listeners (add/remove listener methods)
 * 4. Notifies listeners when data changes (fireModelEvent method)
 *
 * This implements the Observer pattern - the model is the Subject
 */
public class CircleModel {

    // The data - list of circles
    private List<Circle> circles;

    // List of registered listeners that want to be notified of changes
    private List<CircleListener> listeners;

    public CircleModel() {
        circles = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    /**
     * Register a listener to be notified of model changes
     */
    public void addCircleListener(CircleListener listener) {
        listeners.add(listener);
    }

    /**
     * Unregister a listener
     */
    public void removeCircleListener(CircleListener listener) {
        listeners.remove(listener);
    }

    /**
     * Add a circle to the model
     * Fires an ADDED event to all listeners
     */
    public void addCircle(Circle circle) {
        circles.add(circle);
        fireModelEvent(CircleEvent.Kind.ADDED, circle);
    }

    /**
     * Remove a circle from the model (circle at specific coordinates)
     * Fires a DELETED event to all listeners
     */
    public void removeCircleAt(int x, int y) {
        // Find circle at position
        Circle toRemove = null;
        for (Circle circle : circles) {
            int dx = circle.getX() - x;
            int dy = circle.getY() - y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= circle.getRadius()) {
                toRemove = circle;
                break;
            }
        }

        if (toRemove != null) {
            circles.remove(toRemove);
            fireModelEvent(CircleEvent.Kind.DELETED, toRemove);
        }
    }

    /**
     * FIRE METHOD - Notifies all registered listeners of a change
     * This is the key method of the Observer pattern
     *
     * @param kind The type of event (ADDED or DELETED)
     * @param circle The circle that was affected
     */
    private void fireModelEvent(CircleEvent.Kind kind, Circle circle) {
        // Create the event object
        CircleEvent event = new CircleEvent(this, kind, circle);

        // Notify all registered listeners
        for (CircleListener listener : listeners) {
            listener.circleChanged(event);
        }
    }

    /**
     * Get all circles (for the view to display)
     */
    public List<Circle> getCircles() {
        return new ArrayList<>(circles); // Return a copy to prevent external modification
    }
}
