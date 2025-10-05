// =============================================================================
// ÜBUNG 6: DECORATOR PATTERN (15 Punkte in November 2010 Klausur!)
// =============================================================================
// ZIEL: Verstehe Decorator Pattern und Delegation
//
// HINTERGRUND:
// Decorator Pattern kam in November 2010 Klausur vor (15 Punkte)!
// Es ist einfacher als Visitor, aber sehr praktisch.
//
// WICHTIGSTE KONZEPTE:
// - Wrapper-Klasse mit gleichem Interface
// - Delegation an wrapped Object
// - Zusätzliche Funktionalität VOR/NACH Delegation
// - Verschachtelung möglich
// =============================================================================

import java.util.*;

// =============================================================================
// TEIL 1: RECTANGLE DECORATOR (Wie in November 2010 Klausur!)
// =============================================================================

// =============================================================================
// AUFGABE 6.1a: Rectangle Klasse (Gegeben) (0 Punkte - Vorgabe)
// =============================================================================
// Diese Klasse ist gegeben (wie in Klausur):

class Rectangle {
    private int x, y, width, height;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void draw() {
        System.out.println("Drawing rectangle at (" + x + "," + y +
                          ") with size " + width + "x" + height);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}


// =============================================================================
// AUFGABE 6.1b: GridRectangle Decorator (8 Punkte) ⚠️ WICHTIG!
// =============================================================================
// Erstelle GridRectangle der ein Gitter über ein Rectangle zeichnet.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - class GridRectangle extends Rectangle
// - private Rectangle wrapped  (das decorierte Objekt!)
// - Konstruktor: GridRectangle(Rectangle rect)
//   → super(0, 0, 0, 0)  (Dummy-Werte, weil wir delegieren)
//   → this.wrapped = rect
// - @Override draw()
//   → wrapped.draw()  (DELEGATION!)
//   → drawGrid()      (zusätzliche Funktionalität)
// - private void drawGrid()
//   → System.out.println("Drawing grid...")
// ▲▲▲ TIPPS ▲▲▲

class GridRectangle extends Rectangle {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 6.1c: BorderRectangle Decorator (7 Punkte)
// =============================================================================
// Erstelle BorderRectangle der einen Rahmen zeichnet.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - Wie GridRectangle, aber drawBorder() statt drawGrid()
// - Optional: private int borderWidth
// ▲▲▲ TIPPS ▲▲▲

class BorderRectangle extends Rectangle {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 6.1d: Test Rectangle Decorators (3 Punkte)
// =============================================================================
// Teste die Decorators:
// 1. Einfaches Rectangle zeichnen
// 2. Rectangle mit Grid
// 3. Rectangle mit Border
// 4. Rectangle mit Grid UND Border (Verschachtelung!)

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// Rectangle rect = new Rectangle(10, 10, 100, 50);
// rect.draw();
//
// Rectangle withGrid = new GridRectangle(rect);
// withGrid.draw();
//
// Rectangle withBoth = new BorderRectangle(new GridRectangle(rect));
// withBoth.draw();
// ▲▲▲ TIPPS ▲▲▲

class TestRectangleDecorators {
    public static void main(String[] args) {
        // DEINE LÖSUNG:

    }
}


// =============================================================================
// TEIL 2: COFFEE DECORATOR (Klassisches Beispiel)
// =============================================================================

// =============================================================================
// AUFGABE 6.2a: Beverage Interface (2 Punkte)
// =============================================================================
// Erstelle ein Beverage Interface.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - interface Beverage
// - String getDescription()
// - double getCost()
// ▲▲▲ TIPPS ▲▲▲

interface Beverage {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 6.2b: Coffee Basis-Klasse (3 Punkte)
// =============================================================================
// Erstelle einfachen Coffee.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - class Coffee implements Beverage
// - getDescription() → return "Coffee"
// - getCost() → return 2.0
// ▲▲▲ TIPPS ▲▲▲

class Coffee implements Beverage {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 6.2c: MilkDecorator (5 Punkte)
// =============================================================================
// Erstelle Decorator der Milch hinzufügt.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - class MilkDecorator implements Beverage
// - private Beverage beverage  (wrapped!)
// - Konstruktor: MilkDecorator(Beverage bev)
// - getDescription() → return beverage.getDescription() + ", Milk"
// - getCost() → return beverage.getCost() + 0.5
// ▲▲▲ TIPPS ▲▲▲

class MilkDecorator implements Beverage {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 6.2d: SugarDecorator (5 Punkte)
// =============================================================================
// Erstelle Decorator der Zucker hinzufügt.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - Wie MilkDecorator, aber + 0.2 für Zucker
// - getDescription() → + ", Sugar"
// ▲▲▲ TIPPS ▲▲▲

class SugarDecorator implements Beverage {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 6.2e: Test Coffee Decorators (2 Punkte)
// =============================================================================
// Teste die Coffee Decorators:
// - Einfacher Coffee: 2.0
// - Coffee mit Milk: 2.5
// - Coffee mit Milk und Sugar: 2.7

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// Beverage coffee = new Coffee();
// coffee = new MilkDecorator(coffee);
// coffee = new SugarDecorator(coffee);
// System.out.println(coffee.getDescription() + ": $" + coffee.getCost());
// ▲▲▲ TIPPS ▲▲▲

class TestCoffeeDecorators {
    public static void main(String[] args) {
        // DEINE LÖSUNG:

    }
}


// =============================================================================
// ERKLÄRUNG: DECORATOR VS. VERERBUNG
// =============================================================================
//
// WARUM DECORATOR STATT VERERBUNG?
//
// PROBLEM MIT VERERBUNG:
// class Coffee { }
// class CoffeeWithMilk extends Coffee { }
// class CoffeeWithSugar extends Coffee { }
// class CoffeeWithMilkAndSugar extends ??? // Kombinationsexplosion!
//
// LÖSUNG MIT DECORATOR:
// Beverage coffee = new Coffee();
// coffee = new MilkDecorator(coffee);
// coffee = new SugarDecorator(coffee);
// → Beliebige Kombinationen möglich!
//
// VORTEILE DECORATOR:
// ✓ Funktionalität ZUR LAUFZEIT hinzufügen
// ✓ Beliebig verschachtelbar
// ✓ Keine Klassen-Explosion
// ✓ Open-Closed Principle (offen für Erweiterung, geschlossen für Änderung)
//
// =============================================================================


// =============================================================================
// REAL-WORLD BEISPIEL: JAVA I/O
// =============================================================================
//
// Java's InputStream/OutputStream nutzen Decorator Pattern!
//
// InputStream in = new FileInputStream("file.txt");
// in = new BufferedInputStream(in);     // ← Decorator: Puffert Daten
// in = new DataInputStream(in);         // ← Decorator: Liest primitive Typen
//
// Oder sogar:
// InputStream in = new GZIPInputStream(
//                    new BufferedInputStream(
//                      new FileInputStream("file.gz")));
//
// → Jeder Decorator fügt Funktionalität hinzu!
//
// =============================================================================


// =============================================================================
// CHECKLISTE FÜR KLAUSUR
// =============================================================================
// □ Decorator hat gleiches Interface wie Component?
// □ Decorator hat Referenz auf wrapped Object?
// □ Konstruktor nimmt Component als Parameter?
// □ Methode ruft wrapped.method() auf (Delegation)?
// □ Zusätzliche Funktionalität VOR oder NACH Delegation?
// □ Verschachtelung möglich?
//
// HÄUFIGE FEHLER:
// ✗ Delegation vergessen (nur eigene Funktionalität)
// ✗ wrapped.method() nicht aufgerufen
// ✗ Decorator hat KEIN wrapped Object
// ✗ Falsches Interface (implementiert nicht gleiches wie Component)
// ✗ Konstruktor nimmt falschen Parameter
//
// DECORATOR PATTERN STRUKTUR MERKEN:
// 1. Wrapped object speichern
// 2. Konstruktor nimmt Component
// 3. Methode: wrapped.method() + eigene Funktionalität
//
// =============================================================================
