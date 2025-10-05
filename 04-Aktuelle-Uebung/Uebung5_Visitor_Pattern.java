// =============================================================================
// ÜBUNG 5: VISITOR PATTERN (14 Punkte in SS2020 Klausur!)
// =============================================================================
// ZIEL: Verstehe Visitor Pattern und Double Dispatch
//
// HINTERGRUND:
// Visitor Pattern kam in SS2020 Praktischer Teil vor (14 Punkte)!
// Es ist eines der komplexesten Patterns, aber sehr klausurrelevant.
//
// WICHTIGSTE KONZEPTE:
// - Visitor Interface mit visit() für jeden Typ
// - accept() Methode in besuchten Klassen
// - visitor.visit(this) → Double Dispatch!
// - Rekursion bei Composite-Strukturen
// =============================================================================

import java.util.*;

// =============================================================================
// TEIL 1: EXPRESSION VISITOR (Wie in SS2020 Klausur!)
// =============================================================================

// =============================================================================
// AUFGABE 5.1a: Expression Hierarchie (Gegeben) (0 Punkte - Vorgabe)
// =============================================================================
// Diese Hierarchie ist gegeben (wie in Klausur):

abstract class Expression {
    // WICHTIG: Diese Methode MUSS jede Expression haben!
    public abstract void accept(ExpressionVisitor visitor);
}

class NumberExpr extends Expression {
    private int value;

    public NumberExpr(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // TODO: accept() implementieren!
    @Override
    public void accept(ExpressionVisitor visitor) {
        // DEINE LÖSUNG (Aufgabe 5.1c):

    }
}

class AddExpr extends Expression {
    private Expression left, right;

    public AddExpr(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() { return left; }
    public Expression getRight() { return right; }

    // TODO: accept() implementieren!
    @Override
    public void accept(ExpressionVisitor visitor) {
        // DEINE LÖSUNG (Aufgabe 5.1c):

    }
}

class MultExpr extends Expression {
    private Expression left, right;

    public MultExpr(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() { return left; }
    public Expression getRight() { return right; }

    // TODO: accept() implementieren!
    @Override
    public void accept(ExpressionVisitor visitor) {
        // DEINE LÖSUNG (Aufgabe 5.1c):

    }
}


// =============================================================================
// AUFGABE 5.1b: ExpressionVisitor Interface (4 Punkte)
// =============================================================================
// Erstelle das Visitor Interface mit visit() Methoden für jeden Expression-Typ.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - interface ExpressionVisitor
// - void visit(NumberExpr expr)
// - void visit(AddExpr expr)
// - void visit(MultExpr expr)
// - WICHTIG: Eine visit() für JEDEN Typ!
// ▲▲▲ TIPPS ▲▲▲

interface ExpressionVisitor {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 5.1c: accept() in allen Expressions (6 Punkte) ⚠️ WICHTIG!
// =============================================================================
// Implementiere accept() in NumberExpr, AddExpr, MultExpr (siehe oben).
//
// WAS MUSS accept() MACHEN?
// 1. visitor.visit(this) aufrufen
// 2. Bei Composite (Add, Mult): Rekursiv left.accept() und right.accept()!

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// NumberExpr:
//   visitor.visit(this);  // Das wars!
//
// AddExpr / MultExpr:
//   visitor.visit(this);
//   left.accept(visitor);   // Rekursion!
//   right.accept(visitor);  // Rekursion!
// ▲▲▲ TIPPS ▲▲▲

// → Gehe zurück nach oben und implementiere accept() in den Expression-Klassen!


// =============================================================================
// AUFGABE 5.1d: CountVisitor (4 Punkte)
// =============================================================================
// Erstelle einen Visitor der alle Expressions zählt.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - class CountVisitor implements ExpressionVisitor
// - private int count = 0
// - Jede visit() Methode: count++
// - public int getCount() { return count; }
// ▲▲▲ TIPPS ▲▲▲

class CountVisitor implements ExpressionVisitor {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 5.1e: Test Expression Visitor (2 Punkte)
// =============================================================================
// Teste den CountVisitor:
// - Erstelle Expression: 5 + (2 * 3)
// - Zähle alle Expressions (sollte 4 sein: 1 Add, 1 Mult, 2 Number)

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// Expression expr = new AddExpr(
//     new NumberExpr(5),
//     new MultExpr(new NumberExpr(2), new NumberExpr(3))
// );
// CountVisitor visitor = new CountVisitor();
// expr.accept(visitor);
// System.out.println(visitor.getCount());  // 4
// ▲▲▲ TIPPS ▲▲▲

class TestExpressionVisitor {
    public static void main(String[] args) {
        // DEINE LÖSUNG:

    }
}


// =============================================================================
// TEIL 2: MESSAGE VISITOR (Neue Aufgabe zum Üben)
// =============================================================================

// =============================================================================
// AUFGABE 5.2a: Message Hierarchie mit accept() (5 Punkte)
// =============================================================================
// Erstelle Message-Hierarchie die Visitor unterstützt.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// abstract class Message {
//     public abstract void accept(MessageVisitor visitor);
// }
//
// class SingleLineMessage extends Message {
//     private String text;
//     // Konstruktor, getText()
//     // accept() → visitor.visit(this)
// }
//
// class CompoundMessage extends Message {
//     private List<Message> messages = new ArrayList<>();
//     // add(Message)
//     // accept() → visitor.visit(this) + Rekursion über messages!
// }
// ▲▲▲ TIPPS ▲▲▲

abstract class Message2 {
    // DEINE LÖSUNG:

}

class SingleLineMessage {
    // DEINE LÖSUNG:

}

class CompoundMessage {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 5.2b: MessageVisitor Interface (2 Punkte)
// =============================================================================
// Erstelle MessageVisitor Interface.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - void visit(SingleLineMessage msg)
// - void visit(CompoundMessage msg)
// ▲▲▲ TIPPS ▲▲▲

interface MessageVisitor {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 5.2c: CountWordsVisitor (5 Punkte)
// =============================================================================
// Erstelle Visitor der alle Wörter in allen Messages zählt.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - visit(SingleLineMessage): count += msg.getText().split(" ").length
// - visit(CompoundMessage): Nichts tun (Rekursion in accept() passiert!)
// - getWordCount() Methode
// ▲▲▲ TIPPS ▲▲▲

class CountWordsVisitor implements MessageVisitor {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 5.2d: PrintVisitor (3 Punkte)
// =============================================================================
// Erstelle Visitor der alle Messages ausgibt.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - visit(SingleLineMessage): System.out.println(msg.getText())
// - visit(CompoundMessage): System.out.println("Compound with X messages")
// ▲▲▲ TIPPS ▲▲▲

class PrintVisitor implements MessageVisitor {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 5.2e: Test Message Visitor (2 Punkte)
// =============================================================================
// Teste die Message Visitors:
// - Erstelle 3 SingleLineMessages
// - Erstelle CompoundMessage mit den 3 Messages
// - Zähle Wörter mit CountWordsVisitor
// - Gib alles mit PrintVisitor aus

class TestMessageVisitor {
    public static void main(String[] args) {
        // DEINE LÖSUNG:

    }
}


// =============================================================================
// ERKLÄRUNG: DOUBLE DISPATCH
// =============================================================================
//
// WARUM SO KOMPLIZIERT?
//
// PROBLEM: Java hat nur SINGLE Dispatch
// Expression expr = new NumberExpr(5);
// expr.doSomething();  → Zur Laufzeit: NumberExpr.doSomething()
//
// ABER: Wir wollen auch den VISITOR-TYP berücksichtigen!
// → Brauchen ZWEI Dispatches
//
// LÖSUNG: DOUBLE DISPATCH
//
// 1. ERSTER DISPATCH (Expression-Typ):
// Expression expr = new NumberExpr(5);
// expr.accept(visitor);  → NumberExpr.accept() wird gewählt
//
// 2. ZWEITER DISPATCH (Visitor-Typ):
// In NumberExpr.accept():
// visitor.visit(this);  → visit(NumberExpr) wird gewählt
//
// → Zur Laufzeit wird BEIDES berücksichtigt:
//   - Welcher Expression-Typ (Number, Add, Mult)
//   - Welcher Visitor-Typ (Count, Print, etc.)
//
// =============================================================================


// =============================================================================
// CHECKLISTE FÜR KLAUSUR
// =============================================================================
// □ Visitor Interface mit visit() für JEDEN Typ?
// □ accept(Visitor v) in allen besuchten Klassen?
// □ accept() ruft visitor.visit(this) auf?
// □ Bei Composite: Rekursion (left.accept(), right.accept())?
// □ visit() Methoden korrekt implementiert?
// □ Keine Typfehler bei visit(NumberExpr) vs visit(AddExpr)?
//
// HÄUFIGE FEHLER:
// ✗ visitor.visit(this) vergessen in accept()
// ✗ Rekursion in accept() vergessen (bei Add/Mult)
// ✗ Falsche Signatur: visit(Expression) statt visit(NumberExpr)
// ✗ accept() gibt nicht void zurück
// ✗ Visitor nicht implementiert (nur Interface erstellt)
//
// =============================================================================
