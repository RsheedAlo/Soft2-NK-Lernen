# 🚀 MVC Pattern - Quick Reference Card

## Die 3 Komponenten (auf einen Blick)

| Komponente | Verantwortung | Kennt | Beispiel-Methoden |
|------------|---------------|-------|-------------------|
| **MODEL** | Daten + Logik | Nur Listener-Interface | `addCircle()`, `removeCircle()`, `fireModelEvent()` |
| **VIEW** | Anzeige | Model | `paintComponent()`, `circleChanged()`, `repaint()` |
| **CONTROLLER** | Eingabe | Model | `mouseClicked()`, `keyPressed()` |

---

## Observer Pattern - Die 4 Teile

```
┌─────────────────┐
│  1. EVENT       │  extends EventObject
│  CircleEvent    │  - Trägt Info über Änderung
└─────────────────┘

┌─────────────────┐
│  2. LISTENER    │  extends EventListener
│  CircleListener │  - Interface für Callbacks
└─────────────────┘

┌─────────────────┐
│  3. SUBJECT     │  = Model
│  CircleModel    │  - Verwaltet Listener
│                 │  - Feuert Events
└─────────────────┘

┌─────────────────┐
│  4. OBSERVER    │  = View
│  CircleView     │  - Implementiert Listener
│                 │  - Reagiert auf Events
└─────────────────┘
```

---

## Code-Templates

### 1️⃣ Event Class
```java
import java.util.EventObject;

public class CircleEvent extends EventObject {
    public enum Kind { ADDED, DELETED }

    private final Kind kind;
    private final Circle circle;

    public CircleEvent(Object source, Kind kind, Circle circle) {
        super(source);
        this.kind = kind;
        this.circle = circle;
    }

    public Kind getKind() { return kind; }
    public Circle getCircle() { return circle; }
}
```

### 2️⃣ Listener Interface
```java
import java.util.EventListener;

public interface CircleListener extends EventListener {
    void circleChanged(CircleEvent event);
}
```

### 3️⃣ Model Class
```java
public class CircleModel {
    private List<Circle> circles = new ArrayList<>();
    private List<CircleListener> listeners = new ArrayList<>();

    // Listener-Verwaltung
    public void addCircleListener(CircleListener l) {
        listeners.add(l);
    }

    public void removeCircleListener(CircleListener l) {
        listeners.remove(l);
    }

    // Business Logic
    public void addCircle(Circle c) {
        circles.add(c);
        fireModelEvent(Kind.ADDED, c);  // ← Benachrichtige!
    }

    // Fire-Methode
    private void fireModelEvent(Kind kind, Circle c) {
        CircleEvent event = new CircleEvent(this, kind, c);
        for (CircleListener l : listeners) {
            l.circleChanged(event);  // ← Callback!
        }
    }

    public List<Circle> getCircles() {
        return new ArrayList<>(circles);  // Kopie!
    }
}
```

### 4️⃣ View Class
```java
public class CircleView extends JPanel implements CircleListener {
    private CircleModel model;

    public CircleView(CircleModel model) {
        this.model = model;
        model.addCircleListener(this);  // ← Registriere!
    }

    @Override
    public void circleChanged(CircleEvent event) {
        repaint();  // ← Male neu!
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Circle c : model.getCircles()) {
            g.fillOval(c.getX(), c.getY(), c.getRadius(), c.getRadius());
        }
    }
}
```

### 5️⃣ Controller Class
```java
public class CircleController extends MouseAdapter {
    private CircleModel model;

    public CircleController(CircleModel model) {
        this.model = model;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Circle c = new Circle(e.getX(), e.getY(), 30);
            model.addCircle(c);  // ← Ändere Model!
        }
    }
}
```

### 6️⃣ Main Class (Wiring)
```java
public class Main {
    public static void main(String[] args) {
        // 1. Model erstellen
        CircleModel model = new CircleModel();

        // 2. View erstellen (registriert sich selbst)
        CircleView view = new CircleView(model);

        // 3. Controller erstellen
        CircleController controller = new CircleController(model);

        // 4. Controller mit View verbinden
        view.addMouseListener(controller);

        // 5. GUI anzeigen
        JFrame frame = new JFrame("MVC Pattern");
        frame.add(view);
        frame.setVisible(true);
    }
}
```

---

## Event-Flow Diagramm

```
┌─────────┐
│  USER   │ klickt
└────┬────┘
     │
     ↓
┌──────────────────┐
│   CONTROLLER     │
│ mouseClicked()   │
└────┬─────────────┘
     │
     ↓ model.addCircle(circle)
┌──────────────────┐
│     MODEL        │
│  addCircle()     │
└────┬─────────────┘
     │
     ↓ circles.add(circle)
     ↓ fireModelEvent(ADDED, circle)
┌──────────────────┐
│  fireModelEvent()│
│  for each        │
│  listener:       │
└────┬─────────────┘
     │
     ↓ listener.circleChanged(event)
┌──────────────────┐
│      VIEW        │
│ circleChanged()  │
└────┬─────────────┘
     │
     ↓ repaint()
     ↓
┌──────────────────┐
│ paintComponent() │
│ for each circle: │
│   g.fillOval()   │
└────┬─────────────┘
     │
     ↓
┌─────────┐
│ DISPLAY │ ✅
└─────────┘
```

---

## Cheat Sheet

### ✅ DO (Richtig)

```java
// ✅ Controller ändert Model
controller.mouseClicked() {
    model.addCircle(circle);
}

// ✅ Model benachrichtigt Listener
model.addCircle(circle) {
    circles.add(circle);
    fireModelEvent(ADDED, circle);
}

// ✅ View liest von Model
view.paintComponent() {
    for (Circle c : model.getCircles()) {
        g.fillOval(...);
    }
}

// ✅ View reagiert auf Events
view.circleChanged(event) {
    repaint();
}

// ✅ Kopie zurückgeben
model.getCircles() {
    return new ArrayList<>(circles);
}
```

### ❌ DON'T (Falsch)

```java
// ❌ View ändert Model direkt
view.mouseClicked() {
    model.circles.add(circle);  // NEIN!
}

// ❌ Model ohne Event-Benachrichtigung
model.addCircle(circle) {
    circles.add(circle);
    // fireModelEvent() vergessen!
}

// ❌ View ändert Model ohne Controller
view.someMethod() {
    model.addCircle(circle);  // NEIN!
}

// ❌ Direkte Referenz zurückgeben
model.getCircles() {
    return circles;  // GEFÄHRLICH!
}

// ❌ Event empfangen aber nicht reagieren
view.circleChanged(event) {
    // repaint() vergessen!
}
```

---

## Debugging-Checklist

### Problem: View aktualisiert nicht

- [ ] View als Listener registriert? `model.addCircleListener(view)`
- [ ] `fireModelEvent()` aufgerufen in Model?
- [ ] `repaint()` aufgerufen in `circleChanged()`?
- [ ] `paintComponent()` implementiert?

### Problem: NullPointerException

- [ ] Model im Constructor gesetzt? `this.model = model`
- [ ] Alle Variablen initialisiert?
- [ ] Listener-Liste erstellt? `listeners = new ArrayList<>()`

### Problem: Keine Events empfangen

- [ ] Interface implementiert? `implements CircleListener`
- [ ] `@Override` bei `circleChanged()`?
- [ ] Registrierung im Constructor? `model.addCircleListener(this)`

---

## Wichtige Java-Klassen

| Klasse/Interface | Package | Zweck |
|------------------|---------|-------|
| `EventObject` | `java.util` | Basis für Events |
| `EventListener` | `java.util` | Basis für Listener |
| `MouseAdapter` | `java.awt.event` | Basis für Controller |
| `JPanel` | `javax.swing` | Basis für View |
| `MouseEvent` | `java.awt.event` | Maus-Events |
| `Graphics` | `java.awt` | Zeichen-API |

---

## Methoden-Übersicht

### Model
```java
void addCircleListener(CircleListener l)
void removeCircleListener(CircleListener l)
void addCircle(Circle c)
void removeCircle(Circle c)
List<Circle> getCircles()
private void fireModelEvent(Kind k, Circle c)  // ← Wichtigste!
```

### View
```java
CircleView(CircleModel model)  // Constructor
void circleChanged(CircleEvent e)  // ← Observer-Callback
void paintComponent(Graphics g)
```

### Controller
```java
CircleController(CircleModel model)  // Constructor
void mouseClicked(MouseEvent e)
void mousePressed(MouseEvent e)
void mouseDragged(MouseEvent e)
```

---

## Event-Klassen

```java
// Event
class CircleEvent extends EventObject {
    enum Kind { ADDED, DELETED, MOVED }
    Kind getKind()
    Circle getCircle()
    Object getSource()  // Von EventObject
}

// Listener
interface CircleListener extends EventListener {
    void circleChanged(CircleEvent event)
}
```

---

## Typische Fehler & Lösungen

| Fehler | Ursache | Lösung |
|--------|---------|--------|
| View zeigt nichts | `fireModelEvent()` fehlt | In allen Model-Methoden aufrufen |
| NPE in paintComponent | Model nicht gesetzt | `this.model = model` im Constructor |
| Events nicht empfangen | Nicht registriert | `model.addCircleListener(this)` |
| Model von außen änderbar | Direkte Referenz | Kopie zurückgeben `new ArrayList<>()` |
| Falscher Button | Falsche Konstante | `MouseEvent.BUTTON1/2/3` verwenden |

---

## Pattern-Varianten

### Minimal MVC (ohne Events)
```java
// View holt Daten aktiv (Polling)
class SimpleView {
    void update() {
        List<Circle> data = model.getCircles();
        for (Circle c : data) paint(c);
    }
}
```

### MVC mit Command Pattern
```java
interface Command {
    void execute();
    void undo();
}

class AddCircleCommand implements Command {
    void execute() { model.addCircle(circle); }
    void undo() { model.removeCircle(circle); }
}
```

### MVC mit mehreren Views
```java
CircleModel model = new CircleModel();
GraphicalView view1 = new GraphicalView(model);
ListView view2 = new ListView(model);
// Beide automatisch synchron!
```

---

## Zusammenfassung

**3 Regeln:**
1. Controller → Model (Änderungen)
2. Model → View (Benachrichtigungen)
3. View → Display (Anzeige)

**Goldene Regel:**
> View liest, ändert nie!
> Controller ändert, zeigt nie!
> Model benachrichtigt, kennt niemanden!

**Wichtigste Methode:**
```java
fireModelEvent(kind, data)  // Das Herzstück!
```

---

## Kompilieren & Ausführen

```bash
# Im src/ Verzeichnis:
javac *.java
java Main

# Mit Package:
javac -d bin src/*.java
java -cp bin Main
```

---

**Viel Erfolg! 🎯**
