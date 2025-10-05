import javax.swing.*;
import java.awt.*;

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




