package event;

import java.util.EventListener;

/**
 * CircleListener - Listener interface for circle model changes
 * Extends EventListener to integrate with Java's event listener system
 *
 * Any class that wants to be notified of model changes must implement this interface
 * and register itself with the CircleModel using addCircleListener()
 */
public interface CircleListener extends EventListener {

    /**
     * Called when a circle is added or removed from the model
     * @param event The event containing information about what changed
     */
    void circleChanged(CircleEvent event);
}
