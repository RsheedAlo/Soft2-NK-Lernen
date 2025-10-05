package event;

import model.Circle;

import java.util.EventObject;

/**
 * CircleEvent - Event class that carries information about model changes
 * Extends EventObject to integrate with Java's event system
 * Similar to the Observer pattern example in the PDF
 */
public class CircleEvent extends EventObject {

    // Enum to distinguish between different event types
    public enum Kind {
        ADDED,    // A circle was added to the model
        DELETED   // A circle was deleted from the model
    }

    private final Kind kind;
    private final Circle circle;

    /**
     * Constructor for CircleEvent
     * @param source The object that fired the event (usually the CircleModel)
     * @param kind The type of change (ADDED or DELETED)
     * @param circle The circle that was affected
     */
    public CircleEvent(Object source, Kind kind, Circle circle) {
        super(source);
        this.kind = kind;
        this.circle = circle;
    }

    public Kind getKind() {
        return kind;
    }

    public Circle getCircle() {
        return circle;
    }

    @Override
    public String toString() {
        return "CircleEvent [kind=" + kind + ", circle=" + circle + "]";
    }
}
