ÜBUNGSAUFGABE: OBSERVER PATTERN MIT SWING MVC
=============================================

AUFGABE 5: CirclePanel - View mit Observer
-------------------------------------------
ZIEL: JPanel erstellen das auf Model-Änderungen reagiert

ANFORDERUNGEN:
1. Erbt von JPanel
2. Implementiert CircleListener
3. Registriert sich beim Model
4. Zeichnet Circles in paintComponent()
5. Ruft repaint() bei circleChanged() auf

CODE-GERÜST:
```java
public class CirclePanel extends JPanel implements CircleListener {
    private CircleModel model;

    public CirclePanel(CircleModel model) {
        this.model = model;
        this.model.addCircleListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);  // WICHTIG: Altes löschen!
        List<Circle> circles = model.getCircles();

        for(Circle circle : circles) {
            g.fillOval(circle.getX(), circle.getY(),
                       circle.getRadius()*2, circle.getRadius()*2);
        }
    }

    @Override
    public void circleChanged(CircleEvent event) {
        repaint();  // NICHT paintComponent() direkt!
    }
}
```

WICHTIGE KONZEPTE:
✓ Observer Pattern: Listener registrieren
✓ MVC: View kennt Model
✓ Swing: paintComponent() wird automatisch aufgerufen
✓ repaint() triggert paintComponent() asynchron

AUFGABE 6: Main-Methode
-----------------------
1. CircleModel erstellen
2. Circles hinzufügen
3. CirclePanel erstellen
4. JFrame setup

HÄUFIGE FEHLER:
✗ super.paintComponent(g) vergessen
✗ paintComponent() direkt aufrufen statt repaint()
✗ Listener nicht registrieren
✗ Durchmesser statt Radius verwenden

SCHWIERIGKEIT: Mittel
PATTERN: Observer + MVC
