# 📚 MVC Pattern - Übungen

## Level 1: Verstehen ⭐

### Übung 1.1: Code lesen
**Ziel:** Den bestehenden Code verstehen

**Aufgabe:**
1. Öffne `CircleModel.java`
2. Finde die `fireModelEvent()` Methode
3. Erkläre in eigenen Worten, was jede Zeile macht

**Lösung:**
<details>
<summary>Klick für Lösung</summary>

```java
private void fireModelEvent(CircleEvent.Kind kind, Circle circle) {
    // Zeile 1: Erstelle Event-Objekt mit allen Infos
    CircleEvent event = new CircleEvent(this, kind, circle);

    // Zeile 2-4: Schleife über alle registrierten Listener
    for (CircleListener listener : listeners) {
        // Zeile 3: Rufe Callback-Methode für jeden Listener auf
        listener.circleChanged(event);
    }
}
```

**Was passiert:**
1. Ein Event wird erstellt mit: Quelle (this = Model), Art (ADDED/DELETED), Daten (circle)
2. Für jeden Listener in der Liste wird die `circleChanged()` Methode aufgerufen
3. Der Listener (z.B. View) empfängt das Event und reagiert (z.B. repaint)
</details>

---

### Übung 1.2: Ablauf nachvollziehen
**Ziel:** Den Event-Flow verstehen

**Aufgabe:**
Beschreibe Schritt für Schritt, was passiert wenn:
1. User klickt bei Position (200, 300)
2. Bis der Kreis auf dem Bildschirm erscheint

**Lösung:**
<details>
<parameter>Klick für Lösung</summary>

1. **User klickt** → MouseEvent wird generiert
2. **Java ruft auf:** `controller.mouseClicked(event)`
3. **Controller:** Erstellt `Circle(200, 300, radius)` und ruft `model.addCircle(circle)`
4. **Model:** Fügt Kreis zur Liste hinzu: `circles.add(circle)`
5. **Model:** Ruft `fireModelEvent(ADDED, circle)` auf
6. **fireModelEvent:** Erstellt Event und ruft für jeden Listener `listener.circleChanged(event)` auf
7. **View:** `circleChanged(event)` wird aufgerufen
8. **View:** Ruft `repaint()` auf
9. **Java:** Ruft automatisch `paintComponent(g)` auf
10. **View:** Malt alle Kreise aus `model.getCircles()`
11. **Bildschirm:** Zeigt neuen Kreis an ✅
</details>

---

### Übung 1.3: Fehler finden
**Ziel:** Typische Fehler erkennen

**Aufgabe:** Was ist falsch an diesem Code?

```java
public class CircleView extends JPanel {
    private CircleModel model;

    public void mouseClicked(MouseEvent e) {
        Circle c = new Circle(e.getX(), e.getY(), 30);
        model.circles.add(c);  // Zeile A
        repaint();              // Zeile B
    }
}
```

**Lösung:**
<details>
<summary>Klick für Lösung</summary>

**Fehler:**
- **Zeile A:** View ändert Model direkt! Das sollte Controller machen
- **Zeile A:** Zugriff auf private Variable `circles` (sollte nicht public sein)
- **Zeile A:** `fireModelEvent()` wird nicht aufgerufen → andere Views werden nicht benachrichtigt
- **Konzept:** View sollte NICHT Model ändern, nur anzeigen!

**Richtig wäre:**
```java
// In Controller (nicht View):
public void mouseClicked(MouseEvent e) {
    Circle c = new Circle(e.getX(), e.getY(), 30);
    model.addCircle(c);  // Model-Methode nutzen
    // repaint() nicht nötig - kommt automatisch durch Event!
}
```
</details>

---

## Level 2: Anwenden ⭐⭐

### Übung 2.1: Neue Funktionalität hinzufügen
**Ziel:** Das Pattern anwenden

**Aufgabe:** Füge folgende Funktion hinzu:
- **Taste 'C'** löscht ALLE Kreise
- Console-Ausgabe: "All circles cleared"

**Schritte:**
1. Erstelle neue Methode im **Model**
2. Erstelle neuen **Controller** für Tastatur
3. Registriere Controller in **Main**

**Lösung:**
<details>
<summary>Klick für Lösung</summary>

**1. In CircleModel.java:**
```java
public void clearAllCircles() {
    // Benachrichtige für jeden Kreis DELETED
    for (Circle c : new ArrayList<>(circles)) {
        fireModelEvent(CircleEvent.Kind.DELETED, c);
    }
    circles.clear();
    System.out.println("All circles cleared");
}
```

**2. Neue Datei: KeyboardController.java**
```java
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardController extends KeyAdapter {
    private CircleModel model;

    public KeyboardController(CircleModel model) {
        this.model = model;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_C) {
            model.clearAllCircles();
        }
    }
}
```

**3. In Main.java:**
```java
// Nach CircleController:
KeyboardController keyController = new KeyboardController(model);
view.addKeyListener(keyController);
view.setFocusable(true);  // Wichtig für Keyboard-Events!
```
</details>

---

### Übung 2.2: Zweite View hinzufügen
**Ziel:** Mehrere Observer verstehen

**Aufgabe:** Erstelle eine zweite View die:
- Die Anzahl der Kreise anzeigt
- Jedes Mal aktualisiert wenn sich Model ändert

**Lösung:**
<details>
<summary>Klick für Lösung</summary>

**Neue Datei: CircleCountView.java**
```java
import javax.swing.*;
import java.awt.*;

public class CircleCountView extends JPanel implements CircleListener {
    private CircleModel model;
    private JLabel label;

    public CircleCountView(CircleModel model) {
        this.model = model;
        model.addCircleListener(this);  // Registriere!

        label = new JLabel("Circles: 0");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label);
    }

    @Override
    public void circleChanged(CircleEvent event) {
        int count = model.getCircles().size();
        label.setText("Circles: " + count);

        System.out.println("CountView updated: " + count + " circles");
    }
}
```

**In Main.java:**
```java
CircleModel model = new CircleModel();

CircleView mainView = new CircleView(model);
CircleCountView countView = new CircleCountView(model);  // Neue View!

JFrame frame = new JFrame("MVC Pattern");
frame.setLayout(new BorderLayout());
frame.add(mainView, BorderLayout.CENTER);
frame.add(countView, BorderLayout.SOUTH);  // Unten hinzufügen
```

**Ergebnis:** Beide Views werden automatisch synchronisiert! 🎉
</details>

---

### Übung 2.3: Event-Art erweitern
**Ziel:** Events verstehen

**Aufgabe:** Füge neuen Event-Typ hinzu:
- `Kind.MOVED` wenn Kreis bewegt wird
- Zeige in Console: "Circle moved from (x1,y1) to (x2,y2)"

**Lösung:**
<details>
<summary>Klick für Lösung</summary>

**1. In CircleEvent.java:**
```java
public enum Kind {
    ADDED,
    DELETED,
    MOVED  // ← Neu!
}

// Optionale neue Felder für MOVED:
private final int oldX, oldY;

public CircleEvent(Object source, Kind kind, Circle circle, int oldX, int oldY) {
    super(source);
    this.kind = kind;
    this.circle = circle;
    this.oldX = oldX;
    this.oldY = oldY;
}

public int getOldX() { return oldX; }
public int getOldY() { return oldY; }
```

**2. In Circle.java (erweitere Klasse):**
```java
public void setPosition(int newX, int newY) {
    this.x = newX;
    this.y = newY;
}
```

**3. In CircleModel.java:**
```java
public void moveCircle(Circle circle, int newX, int newY) {
    int oldX = circle.getX();
    int oldY = circle.getY();

    circle.setPosition(newX, newY);

    fireModelEvent(Kind.MOVED, circle, oldX, oldY);
}

// Überladen für MOVED:
private void fireModelEvent(Kind kind, Circle c, int oldX, int oldY) {
    CircleEvent event = new CircleEvent(this, kind, c, oldX, oldY);
    for (CircleListener l : listeners) {
        l.circleChanged(event);
    }
}
```

**4. In CircleView.java:**
```java
@Override
public void circleChanged(CircleEvent event) {
    if (event.getKind() == CircleEvent.Kind.MOVED) {
        System.out.println("Circle moved from (" +
            event.getOldX() + "," + event.getOldY() + ") to (" +
            event.getCircle().getX() + "," + event.getCircle().getY() + ")");
    }
    repaint();
}
```
</details>

---

## Level 3: Erweitern ⭐⭐⭐

### Übung 3.1: Farb-System
**Ziel:** Komplexere Model-Erweiterung

**Aufgabe:** Erweitere das System so dass:
1. Jeder Kreis eine Farbe hat
2. Taste 'R' = Rot, 'G' = Grün, 'B' = Blau
3. Nächster Klick erstellt Kreis in gewählter Farbe

**Hinweise:**
- Erweitere `Circle` Klasse
- Füge `currentColor` zum Controller hinzu
- View nutzt `circle.getColor()`

**Lösung:**
<details>
<summary>Klick für Lösung</summary>

**1. Circle.java erweitern:**
```java
import java.awt.Color;

public class Circle {
    private int x, y, radius;
    private Color color;  // ← Neu!

    public Circle(int x, int y, int radius, Color color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.color = color;
    }

    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
}
```

**2. CircleController.java erweitern:**
```java
import java.awt.Color;

public class CircleController extends MouseAdapter {
    private CircleModel model;
    private Color currentColor = Color.BLUE;  // ← Neu!

    public void setCurrentColor(Color color) {
        this.currentColor = color;
        System.out.println("Color changed to: " + color);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Circle c = new Circle(e.getX(), e.getY(), 30, currentColor);  // ← Farbe!
            model.addCircle(c);
        }
    }
}
```

**3. KeyboardController.java erweitern:**
```java
public class KeyboardController extends KeyAdapter {
    private CircleModel model;
    private CircleController mouseController;  // ← Neu!

    public KeyboardController(CircleModel model, CircleController mouseController) {
        this.model = model;
        this.mouseController = mouseController;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                mouseController.setCurrentColor(Color.RED);
                break;
            case KeyEvent.VK_G:
                mouseController.setCurrentColor(Color.GREEN);
                break;
            case KeyEvent.VK_B:
                mouseController.setCurrentColor(Color.BLUE);
                break;
            case KeyEvent.VK_C:
                model.clearAllCircles();
                break;
        }
    }
}
```

**4. CircleView.java anpassen:**
```java
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    for (Circle circle : model.getCircles()) {
        g.setColor(circle.getColor());  // ← Nutze Farbe des Kreises!
        g.fillOval(
            circle.getX() - circle.getRadius(),
            circle.getY() - circle.getRadius(),
            circle.getRadius() * 2,
            circle.getRadius() * 2
        );
    }

    // Zeige aktuelle Farbe (optional)
    g.setColor(Color.BLACK);
    g.drawString("Press R/G/B to change color", 10, 60);
}
```

**5. Main.java aktualisieren:**
```java
CircleController mouseController = new CircleController(model);
KeyboardController keyController = new KeyboardController(model, mouseController);

view.addMouseListener(mouseController);
view.addKeyListener(keyController);
view.setFocusable(true);
```
</details>

---

### Übung 3.2: Undo/Redo System
**Ziel:** Command Pattern mit MVC kombinieren

**Aufgabe:** Implementiere Undo/Redo:
- **Ctrl+Z** = Undo (letzten Kreis entfernen)
- **Ctrl+Y** = Redo (wieder hinzufügen)

**Lösung:**
<details>
<summary>Klick für Lösung</summary>

**1. Command Interface:**
```java
public interface Command {
    void execute();
    void undo();
}
```

**2. AddCircleCommand:**
```java
public class AddCircleCommand implements Command {
    private CircleModel model;
    private Circle circle;

    public AddCircleCommand(CircleModel model, Circle circle) {
        this.model = model;
        this.circle = circle;
    }

    @Override
    public void execute() {
        model.addCircle(circle);
    }

    @Override
    public void undo() {
        model.removeCircle(circle);
    }
}
```

**3. CommandManager:**
```java
import java.util.Stack;

public class CommandManager {
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();

    public void execute(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();  // Redo-Historie löschen
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }
}
```

**4. Controller mit Commands:**
```java
public class CircleController extends MouseAdapter {
    private CircleModel model;
    private CommandManager commandManager;

    public CircleController(CircleModel model, CommandManager commandManager) {
        this.model = model;
        this.commandManager = commandManager;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            Circle c = new Circle(e.getX(), e.getY(), 30);
            Command cmd = new AddCircleCommand(model, c);
            commandManager.execute(cmd);  // Statt model.addCircle()!
        }
    }
}
```

**5. Keyboard für Undo/Redo:**
```java
@Override
public void keyPressed(KeyEvent e) {
    if (e.isControlDown()) {
        if (e.getKeyCode() == KeyEvent.VK_Z) {
            commandManager.undo();
        } else if (e.getKeyCode() == KeyEvent.VK_Y) {
            commandManager.redo();
        }
    }
}
```
</details>

---

### Übung 3.3: Persistenz (Speichern/Laden)
**Ziel:** Model unabhängig von GUI testen

**Aufgabe:** Implementiere:
- **Ctrl+S** = Speichere Kreise in Datei
- **Ctrl+L** = Lade Kreise aus Datei

**Lösung:**
<details>
<summary>Klick für Lösung</summary>

**1. In CircleModel.java:**
```java
import java.io.*;
import java.util.ArrayList;

public void saveToFile(String filename) throws IOException {
    try (ObjectOutputStream out = new ObjectOutputStream(
            new FileOutputStream(filename))) {
        out.writeObject(circles);
        System.out.println("Saved " + circles.size() + " circles");
    }
}

@SuppressWarnings("unchecked")
public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
    try (ObjectInputStream in = new ObjectInputStream(
            new FileInputStream(filename))) {
        List<Circle> loaded = (List<Circle>) in.readObject();

        circles.clear();
        for (Circle c : loaded) {
            addCircle(c);  // Nutze addCircle für Events!
        }

        System.out.println("Loaded " + circles.size() + " circles");
    }
}
```

**2. Circle muss Serializable sein:**
```java
import java.io.Serializable;

public class Circle implements Serializable {
    private static final long serialVersionUID = 1L;
    // Rest wie vorher
}
```

**3. In KeyboardController:**
```java
@Override
public void keyPressed(KeyEvent e) {
    if (e.isControlDown()) {
        if (e.getKeyCode() == KeyEvent.VK_S) {
            try {
                model.saveToFile("circles.dat");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_L) {
            try {
                model.loadFromFile("circles.dat");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
```
</details>

---

## Level 4: Meistern ⭐⭐⭐⭐

### Übung 4.1: Animation System
**Ziel:** Model als Datenquelle für Animation

**Aufgabe:** Kreise pulsieren (Radius ändert sich):
- Timer im Model ändert Radius periodisch
- View zeigt Animation

**Hinweis:** `javax.swing.Timer` nutzen

---

### Übung 4.2: Drag & Drop
**Ziel:** Komplexe Interaktion

**Aufgabe:** Kreise mit Maus verschieben:
- `mousePressed`: Kreis auswählen
- `mouseDragged`: Position aktualisieren
- `mouseReleased`: Loslassen

**Hinweis:** Neues Event `Kind.MOVED` nutzen

---

### Übung 4.3: Mehrere Controller
**Ziel:** Controller-Variabilität verstehen

**Aufgabe:** Zwei Modi:
- **Draw-Mode**: Linksklick fügt Kreis hinzu
- **Delete-Mode**: Linksklick löscht Kreis
- **Taste 'M'**: Wechselt zwischen Modi

**Lösung:** State Pattern oder Strategy Pattern nutzen

---

## Test Yourself 📝

### Multiple Choice

**1. Welche Komponente benachrichtigt die View?**
- [ ] Controller
- [x] Model
- [ ] View selbst
- [ ] Main

**2. Wer darf das Model ändern?**
- [x] Nur Controller
- [ ] Nur View
- [ ] Beide
- [ ] Niemand

**3. Was macht `fireModelEvent()`?**
- [ ] Löscht Events
- [x] Benachrichtigt Listener
- [ ] Erstellt Model
- [ ] Startet Timer

**4. Warum implementiert View das Listener-Interface?**
- [ ] Zur Dekoration
- [x] Um Callbacks zu empfangen
- [ ] Um Model zu ändern
- [ ] Weil Java es verlangt

**5. Was passiert wenn `repaint()` fehlt?**
- [ ] Programm stürzt ab
- [ ] Model ist falsch
- [x] View zeigt alte Daten
- [ ] Nichts

---

## Projekt-Ideen 🚀

### Anfänger
1. **Todo-Liste** mit MVC
   - Model: Liste von Tasks
   - View: JList
   - Controller: Add/Remove Buttons

2. **Einfacher Counter**
   - Model: int counter
   - View: JLabel zeigt Zahl
   - Controller: +/- Buttons

### Fortgeschritten
3. **Zeichenprogramm**
   - Mehrere Formen (Kreis, Rechteck, Linie)
   - Farben, Größen
   - Mehrere Views (Grafisch + Liste)

4. **Chat-Anwendung**
   - Model: Nachrichten-Liste
   - View: Chat-Fenster
   - Controller: Eingabefeld

5. **Spiel (z.B. Tic-Tac-Toe)**
   - Model: Spielfeld-Status
   - View: Grafisches Board
   - Controller: Klick auf Feld

### Experte
6. **Bildbearbeitung**
   - Model: Pixel-Daten
   - Views: Original + Vorschau
   - Controller: Filter-Buttons

7. **Musik-Player**
   - Model: Playlist + Status
   - View: GUI + Visualisierung
   - Controller: Play/Pause/Skip

---

## Lösungen testen

**Kompilieren:**
```bash
cd mvc-circle-example/src
javac *.java
```

**Ausführen:**
```bash
java Main
```

**Debug-Modus (mit Ausgaben):**
```java
// In allen Methoden:
System.out.println("METHOD_NAME called");
```

---

Viel Erfolg beim Üben! 💪🎓
