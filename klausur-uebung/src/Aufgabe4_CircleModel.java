import java.util.ArrayList;
import java.util.List;

class CircleModel{
    private List<Circle> circles;
    private List<CircleListener> listeners;

    public CircleModel(){
        circles = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public void addCircleListener(CircleListener l){
        listeners.add(l);
    }

    public void removeCircleListener(CircleListener l){
        listeners.remove(l);
    }

    public List<Circle> getCircles(){
        return new ArrayList<>(circles);
    }

    public void addCircle(Circle c){
        circles.add(c);
        fireCircleChanged(CircleEvent.Kind.ADDED, c);
    }

    public void fireCircleChanged(CircleEvent.Kind kind, Circle c){
        CircleEvent event = new CircleEvent(this, kind, c);

        for(CircleListener l : listeners){
            l.circleChanged(event);
        }
    }

    public void removeCircle(Circle c){
        circles.remove(c);
        fireCircleChanged(CircleEvent.Kind.REMOVED, c);
    }
}

// =============================================================================
// AUFGABE 4: CircleModel - Das WICHTIGSTE für die Klausur!
// =============================================================================
// Schreibe eine CircleModel-Klasse mit Observer-Pattern:
//
// ATTRIBUTE:
// - private List<Circle> circles
// - private List<CircleListener> listeners
//
// KONSTRUKTOR:
// - Initialisiere beide Listen als neue ArrayLists
//
// METHODEN:
// 1. addCircle(Circle c)
//    - Füge Circle zur Liste hinzu
//    - Rufe fireCircleChanged(Kind.ADDED, c) auf
//
// 2. removeCircle(Circle c)
//    - Entferne Circle aus Liste
//    - Rufe fireCircleChanged(Kind.REMOVED, c) auf
//
// 3. getCircles()
//    - Gib eine KOPIE der circles-Liste zurück (new ArrayList<>(circles))
//
// 4. addCircleListener(CircleListener l)
//    - Füge Listener zur listeners-Liste hinzu
//
// 5. removeCircleListener(CircleListener l)
//    - Entferne Listener aus listeners-Liste
//
// 6. fireCircleChanged(Kind kind, Circle c)
//    - Erstelle ein neues CircleEvent(this, kind, c)
//    - Gehe durch ALLE Listener in der Liste (for-Schleife oder for-each)
//    - Rufe bei jedem: listener.circleChanged(event)

// DEINE LÖSUNG:




