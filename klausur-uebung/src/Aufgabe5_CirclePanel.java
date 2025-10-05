import javax.swing.*;
import java.awt.*;

public class CirclePanel extends JPanel implements CircleListener{
    private CircleModel model;

    public CirclePanel(CircleModel model){
        this.model = model;
        this.model.addCircleListener(this);
    }

    @Override
    public void circleChanged(CircleEvent event){
        repaint();
    }

     @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        List<Circle> circles = model.getCircles();

        for(Circle circle : circles){
            g.fillOval(circle.getX(), circle.getY(), circle.getRadius()*2, circle.getRadius()*2);
        }
    }
}
// =============================================================================
// AUFGABE 5: CirclePanel - Swing View mit Observer
// =============================================================================
// Schreibe ein CirclePanel das:
// - von JPanel erbt
// - CircleListener implementiert
//
// ATTRIBUTE:
// - private CircleModel model
//
// KONSTRUKTOR:
// - Parameter: CircleModel model
// - Speichere model in Attribut
// - Registriere DICH SELBST als Listener: model.addCircleListener(this)
//
// METHODEN:
// 1. paintComponent(Graphics g)
//    - Rufe super.paintComponent(g) auf
//    - Hole alle Circles vom Model (getCircles())
//    - Zeichne jeden Circle mit g.fillOval(...)
//
// 2. circleChanged(CircleEvent event)
//    - Rufe repaint() auf (NICHT paintComponent direkt!)

// DEINE LÖSUNG:




