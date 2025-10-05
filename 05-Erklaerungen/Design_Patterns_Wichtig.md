═══════════════════════════════════════════════════════════════
  DESIGN PATTERNS FÜR DIE KLAUSUR - DIE 3 WICHTIGSTEN
═══════════════════════════════════════════════════════════════

Diese 3 Patterns kamen in den letzten Klausuren VOR!
Basierend auf:
- SS2020 Praktischer Teil (Visitor: 14pt, Composite: 12pt)
- November 2010 (Decorator: 15pt)
- Zusammenfassung_Soft2_DesignPatterns.pdf


🔥🔥🔥 1. VISITOR PATTERN (14 PUNKTE IN SS2020!) 🔥🔥🔥
═══════════════════════════════════════════════════════════════

WANN VERWENDEN?
→ Operationen auf Objektstruktur durchführen
→ Neue Operationen hinzufügen OHNE Klassen zu ändern
→ Verschiedene Typen unterschiedlich behandeln

PROBLEM OHNE VISITOR:
```java
// Wir wollen Expressions zählen
abstract class Expression {
    // Müssen count() in JEDE Klasse einbauen → schlecht!
}
```

LÖSUNG MIT VISITOR:
→ Operation (count) in SEPARATER Klasse (Visitor)
→ Objektstruktur bleibt unverändert


STRUKTUR:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. VISITOR INTERFACE:
```java
interface ExpressionVisitor {
    void visit(NumberExpr expr);
    void visit(AddExpr expr);
    void visit(MultExpr expr);
}
```
→ Eine visit() Methode für JEDEN Typ!

2. ACCEPT-METHODE in besuchten Klassen:
```java
abstract class Expression {
    public abstract void accept(ExpressionVisitor visitor);
}

class NumberExpr extends Expression {
    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);  // ← WICHTIG: this!
    }
}

class AddExpr extends Expression {
    private Expression left, right;

    @Override
    public void accept(ExpressionVisitor visitor) {
        visitor.visit(this);
        left.accept(visitor);   // ← Rekursiv!
        right.accept(visitor);  // ← Rekursiv!
    }
}
```

3. KONKRETE VISITOR-IMPLEMENTIERUNG:
```java
class CountVisitor implements ExpressionVisitor {
    private int count = 0;

    @Override
    public void visit(NumberExpr expr) {
        count++;  // Zähle Number
    }

    @Override
    public void visit(AddExpr expr) {
        count++;  // Zähle Add
    }

    @Override
    public void visit(MultExpr expr) {
        count++;  // Zähle Mult
    }

    public int getCount() {
        return count;
    }
}
```

VERWENDUNG:
```java
Expression expr = new AddExpr(
    new NumberExpr(5),
    new MultExpr(new NumberExpr(2), new NumberExpr(3))
);

CountVisitor visitor = new CountVisitor();
expr.accept(visitor);
System.out.println(visitor.getCount());  // 4 (1 Add + 1 Mult + 2 Number)
```


DOUBLE DISPATCH - WARUM SO KOMPLIZIERT?
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Java hat nur SINGLE Dispatch:
```java
Expression expr = new NumberExpr(5);
expr.accept(visitor);  // ← 1. Dispatch: NumberExpr.accept() wird gewählt
```

In accept():
```java
visitor.visit(this);  // ← 2. Dispatch: visit(NumberExpr) wird gewählt
```

→ Zur Laufzeit wird die RICHTIGE visit() Methode aufgerufen!
→ Das nennt man DOUBLE DISPATCH


CHECKLISTE FÜR KLAUSUR:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
□ Visitor Interface mit visit() für jeden Typ?
□ accept(Visitor v) in allen besuchten Klassen?
□ accept() ruft visitor.visit(this) auf?
□ Bei Composite: Rekursion in accept() (left.accept(), right.accept())?
□ Konkrete Visitor-Klasse implementiert Interface?


HÄUFIGE FEHLER:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
✗ visitor.visit(this) vergessen
✗ Rekursion in accept() vergessen (bei Composite)
✗ Falsche Signatur: visit(Expression) statt visit(NumberExpr)
✗ accept() gibt void zurück (nicht int oder String!)


🔥🔥🔥 2. COMPOSITE PATTERN (12 PUNKTE IN SS2020!) 🔥🔥🔥
═══════════════════════════════════════════════════════════════

WANN VERWENDEN?
→ Baumstrukturen (Teil-Ganzes Hierarchie)
→ Einzelobjekte UND Gruppen GLEICH behandeln
→ Rekursive Strukturen (Verzeichnisse, GUI, Messages)

BEISPIEL AUS KLAUSUR: Message-Hierarchie


STRUKTUR:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. COMPONENT (Interface oder Abstract):
```java
abstract class Message {
    public abstract int getSize();
}
```

2. LEAF (Einzelnes Element, KEINE Kinder):
```java
class SimpleMessage extends Message {
    private String text;

    @Override
    public int getSize() {
        return 1;  // ← Leaf hat immer Size 1
    }
}
```

3. COMPOSITE (Enthält andere Components):
```java
class GroupMessage extends Message {
    private List<Message> messages = new ArrayList<>();

    public void add(Message msg) {
        messages.add(msg);
    }

    public void remove(Message msg) {
        messages.remove(msg);
    }

    @Override
    public int getSize() {
        int total = 0;
        for(Message msg : messages) {
            total += msg.getSize();  // ← Rekursion!
        }
        return total;
    }
}
```

VERWENDUNG:
```java
// Einzelne Messages
Message msg1 = new SimpleMessage("Hello");
Message msg2 = new SimpleMessage("World");

// Gruppe von Messages
GroupMessage group = new GroupMessage();
group.add(msg1);
group.add(msg2);

System.out.println(group.getSize());  // 2

// Verschachtelte Gruppen!
GroupMessage outerGroup = new GroupMessage();
outerGroup.add(group);
outerGroup.add(new SimpleMessage("!"));
System.out.println(outerGroup.getSize());  // 3
```


WICHTIGE KONZEPTE:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. POLYMORPHISMUS:
   - List<Message> speichert SOWOHL Simple ALS AUCH Group!
   - msg.getSize() ruft automatisch richtige Version

2. REKURSION:
   - Composite delegiert an Kinder
   - Kinder können selbst Composites sein
   - → Baum wird rekursiv durchlaufen

3. add() und remove() NUR in Composite:
   - Leaf hat KEINE add() Methode!
   - Macht keinen Sinn (Leaf = Blatt, keine Kinder)


CHECKLISTE FÜR KLAUSUR:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
□ Abstract Component mit gemeinsamen Methoden?
□ Leaf mit einfacher Implementierung (return 1, kein add())?
□ Composite mit List<Component>?
□ Composite hat add() und remove()?
□ Rekursion in Composite-Methoden (getSize(), print(), etc.)?
□ Polymorphismus: List<Component> speichert Leaf UND Composite?


HÄUFIGE FEHLER:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
✗ Leaf hat add() Methode → Falsch!
✗ Composite: return children.size() statt Summe aller getSize()
✗ Rekursion vergessen
✗ List<Leaf> statt List<Component> → kein Polymorphismus!


🔥🔥 3. DECORATOR PATTERN (15 PUNKTE IN NOV 2010) 🔥🔥
═══════════════════════════════════════════════════════════════

WANN VERWENDEN?
→ Funktionalität dynamisch hinzufügen
→ Alternative zu Vererbung
→ Wrapper um Objekt legen

BEISPIEL AUS KLAUSUR: GridRectangle decoriert Rectangle


STRUKTUR:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. COMPONENT (Interface oder Klasse):
```java
class Rectangle {
    private int x, y, width, height;

    public void draw() {
        System.out.println("Drawing rectangle at (" + x + "," + y + ")");
    }
}
```

2. DECORATOR (erweitert Component):
```java
class GridRectangle extends Rectangle {
    private Rectangle wrapped;  // ← Das decorierte Objekt

    public GridRectangle(Rectangle rect) {
        this.wrapped = rect;
    }

    @Override
    public void draw() {
        wrapped.draw();  // ← Delegation an Original
        drawGrid();      // ← Zusätzliche Funktionalität
    }

    private void drawGrid() {
        System.out.println("Drawing grid");
    }
}
```

VERWENDUNG:
```java
Rectangle rect = new Rectangle(0, 0, 100, 100);
rect.draw();  // Drawing rectangle

Rectangle decorated = new GridRectangle(rect);
decorated.draw();
// Drawing rectangle
// Drawing grid
```

VERSCHACHTELUNG MÖGLICH:
```java
Rectangle rect = new Rectangle(0, 0, 100, 100);
rect = new GridRectangle(rect);
rect = new BorderRectangle(rect);
rect.draw();
// Drawing rectangle
// Drawing grid
// Drawing border
```


DECORATOR VS. VERERBUNG:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

VERERBUNG (statisch):
```java
class GridRectangle extends Rectangle { }
class BorderRectangle extends Rectangle { }
// Problem: GridBorderRectangle extends ??? → Explosion!
```

DECORATOR (dynamisch):
```java
new BorderRectangle(new GridRectangle(new Rectangle()))
```
→ Flexibler!


REAL-WORLD BEISPIEL: Java I/O
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```java
InputStream in = new FileInputStream("file.txt");
in = new BufferedInputStream(in);  // ← Decorator!
in = new GZIPInputStream(in);      // ← Decorator!
```


CHECKLISTE FÜR KLAUSUR:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
□ Decorator hat gleiches Interface wie Component?
□ Decorator hat Referenz auf wrapped Object?
□ Konstruktor nimmt Component als Parameter?
□ Methode ruft wrapped.method() auf (Delegation)?
□ Zusätzliche Funktionalität NACH Delegation?


HÄUFIGE FEHLER:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
✗ Delegation vergessen (nur eigene Funktionalität)
✗ wrapped.method() nicht aufgerufen
✗ Decorator implementiert nicht gleiches Interface
✗ Konstruktor nimmt keine Component


═══════════════════════════════════════════════════════════════
  VERGLEICH DER 3 PATTERNS
═══════════════════════════════════════════════════════════════

VISITOR:
✓ Neue Operationen hinzufügen OHNE Klassen zu ändern
✓ Double Dispatch
✗ Komplex zu verstehen

COMPOSITE:
✓ Baumstrukturen
✓ Einzelobjekte = Gruppen
✓ Rekursion

DECORATOR:
✓ Funktionalität dynamisch hinzufügen
✓ Alternative zu Vererbung
✓ Verschachtelbar


KLAUSUR-HÄUFIGKEIT:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
1. Composite   ★★★★★ (Fast immer!)
2. Visitor     ★★★★☆ (Oft)
3. Decorator   ★★★☆☆ (Manchmal)


═══════════════════════════════════════════════════════════════
  LERN-REIHENFOLGE FÜR MORGEN
═══════════════════════════════════════════════════════════════

1. COMPOSITE (30 min)
   → Am wichtigsten!
   → Einfach zu verstehen
   → Oft in Klausur

2. VISITOR (45 min)
   → Komplex aber wichtig
   → Double Dispatch verstehen
   → accept() + visit() üben

3. DECORATOR (20 min)
   → Weniger häufig
   → Aber einfach zu verstehen
   → Delegation merken


🔥 4. OBSERVER PATTERN (WICHTIG FÜR MVC!) 🔥
═══════════════════════════════════════════════════════════════

WANN VERWENDEN?
→ 1:n Abhängigkeit zwischen Objekten
→ Wenn sich ein Objekt ändert, sollen andere benachrichtigt werden
→ Lose Kopplung (Subject kennt Observer nicht konkret)

BEISPIEL: MVC - Model benachrichtigt View bei Änderungen


STRUKTUR:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. OBSERVER INTERFACE:
```java
interface Observer {
    void update(Subject subject);
}
```

2. SUBJECT (Observable):
```java
class Subject {
    private List<Observer> observers = new ArrayList<>();
    private int state;

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for(Observer obs : observers) {
            obs.update(this);  // ← Benachrichtigung!
        }
    }

    public void setState(int state) {
        this.state = state;
        notifyObservers();  // ← Automatisch!
    }

    public int getState() {
        return state;
    }
}
```

3. CONCRETE OBSERVER:
```java
class ConcreteObserver implements Observer {
    private String name;

    public ConcreteObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(Subject subject) {
        System.out.println(name + " notified. New state: " + subject.getState());
    }
}
```

VERWENDUNG:
```java
Subject subject = new Subject();

Observer obs1 = new ConcreteObserver("Observer1");
Observer obs2 = new ConcreteObserver("Observer2");

subject.attach(obs1);
subject.attach(obs2);

subject.setState(5);
// Observer1 notified. New state: 5
// Observer2 notified. New state: 5
```


MVC MIT OBSERVER:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

MODEL (Subject):
```java
class CircleModel {
    private List<CircleListener> listeners = new ArrayList<>();
    private List<Circle> circles = new ArrayList<>();

    public void addCircleListener(CircleListener listener) {
        listeners.add(listener);
    }

    public void addCircle(Circle c) {
        circles.add(c);
        fireCircleAdded();  // ← Fire-Methode = notify!
    }

    private void fireCircleAdded() {
        CircleEvent event = new CircleEvent(this);
        for(CircleListener l : listeners) {
            l.circleChanged(event);  // ← Benachrichtigung!
        }
    }
}
```

VIEW (Observer):
```java
class CirclePanel extends JPanel implements CircleListener {
    private CircleModel model;

    public CirclePanel(CircleModel model) {
        this.model = model;
        model.addCircleListener(this);  // ← Registrieren!
    }

    @Override
    public void circleChanged(CircleEvent event) {
        repaint();  // ← Reagieren auf Änderung!
    }
}
```


VORTEILE:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
✓ Lose Kopplung (Subject kennt nur Observer-Interface)
✓ Beliebig viele Observer
✓ Observer können dynamisch hinzugefügt/entfernt werden
✓ Subject muss nicht wissen wer benachrichtigt wird


CHECKLISTE FÜR KLAUSUR:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
□ Observer Interface mit update() oder spezifischer Methode?
□ Subject hat List<Observer>?
□ attach() und detach() Methoden?
□ notifyObservers() mit for-Schleife?
□ Bei Änderung wird notifyObservers() aufgerufen?
□ Observer registriert sich selbst?


HÄUFIGE FEHLER:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
✗ notifyObservers() vergessen nach Änderung
✗ Observer registriert sich nicht
✗ For-Schleife in notify() falsch
✗ update() nicht implementiert


ZUSAMMENFASSUNG:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
→ Visitor: accept(visitor), visitor.visit(this), Double Dispatch
→ Composite: Rekursion, List<Component>, add() nur in Composite
→ Decorator: Delegation, wrapped.method(), gleiches Interface
→ Observer: attach/notify, List<Observer>, fire-Methode = notify

→ Alle 4 nutzen POLYMORPHISMUS!
→ Zusammen: ~50 Punkte möglich!
