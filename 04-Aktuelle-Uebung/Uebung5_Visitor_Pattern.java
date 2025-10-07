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
// 🎯 SCHRITT-FÜR-SCHRITT VISITOR TUTORIAL
// =============================================================================
// Wir bauen ein TIERPFLEGER-SYSTEM:
// - Verschiedene Tiere (Hund, Katze, Vogel)
// - Verschiedene Aktionen (Füttern, Impfen, Wiegen)
//
// OHNE Visitor würden wir in JEDE Tier-Klasse alle Methoden einbauen.
// MIT Visitor: Aktionen in separaten Visitor-Klassen!
// =============================================================================

// =============================================================================
// SCHRITT 1: Die Tier-Klassen (gegeben / alt / sollten nicht geändert werden)
// =============================================================================
// Stell dir vor: Diese Klassen sind schon fertig und in Produktion.
// Wir wollen neue Funktionen hinzufügen, OHNE sie ständig zu ändern!

abstract class Animal {
    protected String name;

    public Animal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // EINMALIGE Ergänzung für Visitor Pattern:
    // Diese Methode ermöglicht es Visitors, mit dem Tier zu arbeiten
    public abstract void accept(AnimalVisitor visitor);
}

class Dog extends Animal {
    public Dog(String name) {
        super(name);
    }

    public void bark() {
        System.out.println(name + " bellt: Wuff!");
    }

    // SCHRITT 1a: accept() implementieren
    // Was passiert hier?
    // 1. Ein Visitor kommt rein (z.B. FeedVisitor)
    // 2. Wir sagen dem Visitor: "Ich bin ein Hund, besuche mich!"
    // 3. visitor.visit(this) ruft die visit(Dog)-Methode im Visitor auf
    @Override
    public void accept(AnimalVisitor visitor) {
        visitor.visit(this);  // "this" ist ein Dog-Objekt!
    }
}

class Cat extends Animal {
    public Cat(String name) {
        super(name);
    }

    public void meow() {
        System.out.println(name + " miaut: Miau!");
    }

    // SCHRITT 1b: accept() für Katze
    // Gleiche Logik wie Dog, ABER this ist jetzt eine Cat!
    // → visitor.visit(this) ruft visit(Cat) auf
    @Override
    public void accept(AnimalVisitor visitor) {
        visitor.visit(this);  // "this" ist ein Cat-Objekt!
    }
}

class Bird extends Animal {
    public Bird(String name) {
        super(name);
    }

    public void chirp() {
        System.out.println(name + " zwitschert: Piep!");
    }

    // SCHRITT 1c: accept() für Vogel
    @Override
    public void accept(AnimalVisitor visitor) {
        visitor.visit(this);  // "this" ist ein Bird-Objekt!
    }
}

// =============================================================================
// SCHRITT 2: Das Visitor Interface
// =============================================================================
// Ein Visitor muss wissen, wie er mit JEDEM Tier umgeht.
// Deshalb: Eine visit()-Methode für jeden Tier-Typ!

interface AnimalVisitor {
    // Was tun mit einem Hund?
    void visit(Dog dog);

    // Was tun mit einer Katze?
    void visit(Cat cat);

    // Was tun mit einem Vogel?
    void visit(Bird bird);

    // WICHTIG: Wenn du ein neues Tier hinzufügst (z.B. Hamster),
    // musst du hier auch void visit(Hamster h) hinzufügen!
}

// =============================================================================
// SCHRITT 3: Konkrete Visitors (die eigentlichen Aktionen)
// =============================================================================

// VISITOR 1: Tiere füttern
class FeedVisitor implements AnimalVisitor {
    // Wie füttert man einen Hund?
    @Override
    public void visit(Dog dog) {
        System.out.println("🍖 " + dog.getName() + " bekommt Hundefutter");
    }

    // Wie füttert man eine Katze?
    @Override
    public void visit(Cat cat) {
        System.out.println("🐟 " + cat.getName() + " bekommt Katzenfutter");
    }

    // Wie füttert man einen Vogel?
    @Override
    public void visit(Bird bird) {
        System.out.println("🌾 " + bird.getName() + " bekommt Vogelfutter");
    }
}

// VISITOR 2: Tiere impfen
class VaccinateVisitor implements AnimalVisitor {
    @Override
    public void visit(Dog dog) {
        System.out.println("💉 " + dog.getName() + " wird gegen Tollwut geimpft");
    }

    @Override
    public void visit(Cat cat) {
        System.out.println("💉 " + cat.getName() + " wird gegen Katzenschnupfen geimpft");
    }

    @Override
    public void visit(Bird bird) {
        System.out.println("💉 " + bird.getName() + " wird gegen Vogelgrippe geimpft");
    }
}

// VISITOR 3: Tiere wiegen
class WeighVisitor implements AnimalVisitor {
    @Override
    public void visit(Dog dog) {
        System.out.println("⚖️  " + dog.getName() + " wiegt ca. 15kg (Hund)");
    }

    @Override
    public void visit(Cat cat) {
        System.out.println("⚖️  " + cat.getName() + " wiegt ca. 4kg (Katze)");
    }

    @Override
    public void visit(Bird bird) {
        System.out.println("⚖️  " + bird.getName() + " wiegt ca. 0.5kg (Vogel)");
    }
}

// =============================================================================
// SCHRITT 4: VERWENDUNG - So funktioniert's!
// =============================================================================

class VisitorTutorial {
    public static void main(String[] args) {
        // Tiere erstellen
        Animal bello = new Dog("Bello");
        Animal mimi = new Cat("Mimi");
        Animal tweety = new Bird("Tweety");

        System.out.println("=== FÜTTERN ===");
        // Erstelle einen Feed-Visitor
        AnimalVisitor feeder = new FeedVisitor();

        // Schritt für Schritt was passiert:
        // 1. bello.accept(feeder) wird aufgerufen
        // 2. In Dog.accept(): visitor.visit(this) → feeder.visit(bello)
        // 3. feeder.visit(Dog) wird ausgeführt
        // 4. Output: "Bello bekommt Hundefutter"
        bello.accept(feeder);   // 🍖 Bello bekommt Hundefutter
        mimi.accept(feeder);    // 🐟 Mimi bekommt Katzenfutter
        tweety.accept(feeder);  // 🌾 Tweety bekommt Vogelfutter

        System.out.println("\n=== IMPFEN ===");
        AnimalVisitor vaccinator = new VaccinateVisitor();
        bello.accept(vaccinator);   // 💉 Bello wird gegen Tollwut geimpft
        mimi.accept(vaccinator);    // 💉 Mimi wird gegen Katzenschnupfen geimpft
        tweety.accept(vaccinator);  // 💉 Tweety wird gegen Vogelgrippe geimpft

        System.out.println("\n=== WIEGEN ===");
        AnimalVisitor scale = new WeighVisitor();
        bello.accept(scale);   // ⚖️  Bello wiegt ca. 15kg
        mimi.accept(scale);    // ⚖️  Mimi wiegt ca. 4kg
        tweety.accept(scale);  // ⚖️  Tweety wiegt ca. 0.5kg

        System.out.println("\n🎯 DAS WICHTIGSTE:");
        System.out.println("- Tiere (Dog, Cat, Bird) wurden NICHT geändert für neue Aktionen!");
        System.out.println("- Neue Aktion? → Neue Visitor-Klasse schreiben!");
        System.out.println("- Alle 3 Tiere nutzen denselben Visitor → Polymorphismus!");
    }
}

// =============================================================================
// 💡 DOUBLE DISPATCH ERKLÄRT
// =============================================================================
//
// Warum heißt es "Double Dispatch"? Weil 2 Methoden aufgerufen werden:
//
// BEISPIEL: bello.accept(feeder)
//
// DISPATCH 1: accept() wird aufgerufen
// → Java weiß: bello ist ein Dog
// → Dog.accept() wird ausgeführt
//
// DISPATCH 2: visit() wird aufgerufen
// → In Dog.accept() steht: visitor.visit(this)
// → "this" ist ein Dog
// → feeder.visit(Dog) wird ausgeführt (nicht visit(Cat)!)
//
// Resultat: Java weiß SOWOHL den Tier-Typ (Dog) ALS AUCH den Visitor-Typ (FeedVisitor)
// → Die richtige Methode wird aufgerufen: FeedVisitor.visit(Dog)
//
// =============================================================================

// =============================================================================
// 🏆 WANN VISITOR IN DER KLAUSUR?
// =============================================================================
//
// Aufgabenstellung enthält oft:
// ✓ "Implementiere verschiedene Operationen auf einer Objektstruktur"
// ✓ "Füge neue Funktionalität hinzu ohne Klassen zu ändern"
// ✓ "Zähle/Berechne etwas in einer Baumstruktur"
// ✓ "accept() und visit() Methoden"
//
// HÄUFIGSTE FEHLER:
// ✗ accept() vergessen zu implementieren
// ✗ visitor.visit(this) falsch schreiben (z.B. this.visit(visitor))
// ✗ Visitor Interface vergessen (direkt mit konkretem Visitor arbeiten)
// ✗ Bei Composite: Rekursion vergessen!
//
// =============================================================================

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
        visitor.visit(this);
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
        visitor.visit(this);
        left.accept(visitor);
        right.accept(visitor);
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
        visitor.visit(this);
        left.accept(visitor);
        right.accept(visitor);
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
    void visit(NumberExpr nx);
    void visit(AddExpr ax);
    void visit(MultExpr mx);

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
    private int count = 0;

    @Override
    void visit(NumberExpr nx){
        count++
    }

    @Override
    void visit(AddExpr ax){
        count++;
    }


    @Override
    void visit(MultExpr mx){
        count++;
    }

    public int getCount(){return count;}
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
        Expression expr = new AddExpr(
            new NumberExpr(5),
            new MultExpr(new NumberExpr(2), new NumberExpr(3))
        );

        CountVisitor visitor = new CountVisitor();
        expr.accept(visitor);
        System.out.println(visitor.getCount());  
    }
}


// =============================================================================
// TEIL 2: MESSAGE VISITOR (Neue Aufgabe zum Üben)
// =============================================================================
//
// 📧 SZENARIO: E-Mail System mit verschiedenen Nachrichtentypen
//
// Du baust ein E-Mail-System mit zwei Arten von Nachrichten:
// 1. EINZELNE ZEILE: "Hallo Welt" (SingleLineMessage)
// 2. ZUSAMMENGESETZTE NACHRICHT: Enthält mehrere andere Nachrichten (CompoundMessage)
//
// WICHTIG: Du willst später verschiedene Operationen durchführen:
// - Zeilen zählen
// - Alle Texte sammeln
// - Nachrichten formatieren
//
// → Benutze Visitor Pattern, um flexibel zu bleiben!
//
// =============================================================================



// =============================================================================
// AUFGABE 5.2a: Message Hierarchie mit accept() (5 Punkte)
// =============================================================================
//
// AUFGABE: Erstelle eine Message-Hierarchie für das E-Mail-System
//
// WAS DU BRAUCHST:
//
// 1. ABSTRACT CLASS Message
//    - Alle Nachrichten können von einem Visitor besucht werden
//    - Methode: accept(MessageVisitor visitor)
//
// 2. CLASS SingleLineMessage (eine einzelne Text-Zeile)
//    - Speichert: String text
//    - Konstruktor: SingleLineMessage(String text)
//    - Getter: getText()
//    - accept(): Rufe visitor.visit(this) auf
//
// 3. CLASS CompoundMessage (Container für mehrere Nachrichten)
//    - Speichert: List<Message> messages
//    - Konstruktor: Initialisiere leere Liste
//    - Methode: add(Message msg) → fügt Nachricht hinzu
//    - Getter: getMessages()
//    - accept():
//      a) Rufe visitor.visit(this) auf
//      b) DANN: Rufe für JEDE enthaltene Nachricht accept() auf (Rekursion!)
//
// BEISPIEL WAS RAUSKOMMEN SOLL:
// Message m1 = new SingleLineMessage("Zeile 1");
// Message m2 = new SingleLineMessage("Zeile 2");
// CompoundMessage cm = new CompoundMessage();
// cm.add(m1);
// cm.add(m2);
//
// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// abstract class Message {
//     public abstract void accept(MessageVisitor visitor);
// }
//
// class SingleLineMessage extends Message {
//     private String text;
//
//     public SingleLineMessage(String text) { this.text = text; }
//     public String getText() { return text; }
//
//     @Override
//     public void accept(MessageVisitor visitor) {
//         visitor.visit(this);  // Besuche mich!
//     }
// }
//
// class CompoundMessage extends Message {
//     private List<Message> messages = new ArrayList<>();
//
//     public void add(Message msg) { messages.add(msg); }
//     public List<Message> getMessages() { return messages; }
//
//     @Override
//     public void accept(MessageVisitor visitor) {
//         visitor.visit(this);  // Besuche mich zuerst!
//         // WICHTIG: Dann alle Kinder besuchen (Rekursion!)
//         for(Message msg : messages) {
//             msg.accept(visitor);
//         }
//     }
// }
// ▲▲▲ TIPPS ▲▲▲

// LÖSUNG SCHRITT 1: Abstract Message Klasse
// Was wird gemacht: Basis für alle Nachrichten, definiert accept() Methode
abstract class Message2 {
    // Alle Nachrichten können von einem Visitor besucht werden
    // Das ist die EINZIGE Methode die wir hinzufügen müssen!
    public abstract void accept(MessageVisitor visitor);
}

// LÖSUNG SCHRITT 2: SingleLineMessage (einfache Text-Zeile)
// Was wird gemacht: Speichert Text, kann von Visitor besucht werden
class SingleLineMessage extends Message2 {
    private String text;  // Der Text der Nachricht

    // Konstruktor: Text setzen
    public SingleLineMessage(String text) {
        this.text = text;
    }

    // Getter: Text abrufen
    public String getText() {
        return text;
    }

    // accept(): Visitor kommt rein und besucht DIESE Nachricht
    // WICHTIG: visitor.visit(this) ruft visit(SingleLineMessage) auf!
    @Override
    public void accept(MessageVisitor visitor) {
        visitor.visit(this);  // "this" ist SingleLineMessage!
    }
}

// LÖSUNG SCHRITT 3: CompoundMessage (Container für mehrere Nachrichten)
// Was wird gemacht: Sammelt mehrere Messages, Rekursion bei accept()
class CompoundMessage extends Message2 {
    private List<Message2> messages = new ArrayList<>();  // Liste von Messages

    // Methode: Nachricht hinzufügen
    public void add(Message2 msg) {
        messages.add(msg);
    }

    // Getter: Alle Messages abrufen
    public List<Message2> getMessages() {
        return messages;
    }

    // accept(): ZUERST den Visitor auf sich selbst anwenden
    // DANN auf alle enthaltenen Messages (REKURSION!)
    @Override
    public void accept(MessageVisitor visitor) {
        // SCHRITT 1: Besuche MICH zuerst
        visitor.visit(this);

        // SCHRITT 2: Dann besuche alle KINDER (Rekursion!)
        // Das ist wichtig für Composite Pattern!
        for(Message2 msg : messages) {
            msg.accept(visitor);  // Rekursiver Aufruf!
        }
    }
}


// =============================================================================
// AUFGABE 5.2b: MessageVisitor Interface (2 Punkte)
// =============================================================================
// Erstelle MessageVisitor Interface.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - void visit(SingleLineMessage msg)
// - void visit(CompoundMessage msg)
// ▲▲▲ TIPPS ▲▲▲

// LÖSUNG SCHRITT 4: MessageVisitor Interface
// Was wird gemacht: Definiert visit() Methode für JEDEN Message-Typ
interface MessageVisitor {
    // Was tun mit einer SingleLineMessage?
    void visit(SingleLineMessage msg);

    // Was tun mit einer CompoundMessage?
    void visit(CompoundMessage msg);

    // WICHTIG: Eine visit() Methode für JEDEN Typ!
    // Wenn du später einen neuen Message-Typ hinzufügst,
    // musst du hier auch eine neue visit() Methode hinzufügen
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

// LÖSUNG SCHRITT 5: CountWordsVisitor
// Was wird gemacht: Zählt alle Wörter in allen Messages
class CountWordsVisitor implements MessageVisitor {
    private int wordCount = 0;  // Zähler für Wörter

    // SCHRITT 1: Was tun mit SingleLineMessage?
    // → Text holen, in Wörter aufteilen, zählen
    @Override
    public void visit(SingleLineMessage msg) {
        String text = msg.getText();  // Text holen
        // \\s+ = ein oder mehrere Whitespaces (besser als " ")
        // Verhindert leere Strings bei mehreren Leerzeichen
        String[] words = text.trim().split("\\s+");
        wordCount += words.length;  // Anzahl Wörter zum Counter addieren
    }

    // SCHRITT 2: Was tun mit CompoundMessage?
    // → NICHTS! Die Rekursion passiert in accept()!
    // CompoundMessage.accept() ruft bereits accept() auf allen Kindern auf
    @Override
    public void visit(CompoundMessage msg) {
        // Nichts tun! Die Kinder werden automatisch besucht
        // durch die Rekursion in CompoundMessage.accept()
    }

    // Getter: Wort-Anzahl abrufen
    public int getWordCount() {
        return wordCount;
    }
}


// =============================================================================
// AUFGABE 5.2d: PrintVisitor (3 Punkte)
// =============================================================================
// Erstelle Visitor der alle Messages ausgibt.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - visit(SingleLineMessage): System.out.println(msg.getText())
// - visit(CompoundMessage): System.out.println("Compound with X messages")
// ▲▲▲ TIPPS ▲▲▲

// LÖSUNG SCHRITT 6: PrintVisitor
// Was wird gemacht: Gibt alle Messages auf der Konsole aus
class PrintVisitor implements MessageVisitor {

    // SCHRITT 1: Was tun mit SingleLineMessage?
    // → Einfach den Text ausgeben
    @Override
    public void visit(SingleLineMessage msg) {
        System.out.println("📄 Text: " + msg.getText());
    }

    // SCHRITT 2: Was tun mit CompoundMessage?
    // → Ausgeben dass es eine Compound-Message ist + wie viele Kinder
    @Override
    public void visit(CompoundMessage msg) {
        System.out.println("📦 Compound mit " + msg.getMessages().size() + " Nachrichten");
        // Die Kinder werden automatisch besucht durch Rekursion in accept()
    }
}


// =============================================================================
// AUFGABE 5.2e: Test Message Visitor (2 Punkte)
// =============================================================================
// Teste die Message Visitors:
// - Erstelle 3 SingleLineMessages
// - Erstelle CompoundMessage mit den 3 Messages
// - Zähle Wörter mit CountWordsVisitor
// - Gib alles mit PrintVisitor aus

// LÖSUNG SCHRITT 7: Test Message Visitor
// Was wird gemacht: Alles zusammen testen
class TestMessageVisitor {
    public static void main(String[] args) {
        System.out.println("=== MESSAGE VISITOR TEST ===\n");

        // SCHRITT 1: Einzelne Messages erstellen
        Message2 msg1 = new SingleLineMessage("Hallo Welt wie geht es");  // 5 Wörter
        Message2 msg2 = new SingleLineMessage("Java ist toll");  // 3 Wörter
        Message2 msg3 = new SingleLineMessage("Visitor Pattern funktioniert");  // 3 Wörter

        // SCHRITT 2: CompoundMessage erstellen und Messages hinzufügen
        CompoundMessage compound = new CompoundMessage();
        compound.add(msg1);
        compound.add(msg2);
        compound.add(msg3);

        // SCHRITT 3: Wörter zählen mit CountWordsVisitor
        System.out.println("--- WÖRTER ZÄHLEN ---");
        CountWordsVisitor wordCounter = new CountWordsVisitor();
        compound.accept(wordCounter);  // Besuche alle Messages!
        System.out.println("Gesamtanzahl Wörter: " + wordCounter.getWordCount());
        // Erwartung: 5 + 3 + 3 = 11 Wörter

        // SCHRITT 4: Alles ausgeben mit PrintVisitor
        System.out.println("\n--- MESSAGES AUSGEBEN ---");
        PrintVisitor printer = new PrintVisitor();
        compound.accept(printer);  // Besuche alle Messages!

        // SCHRITT 5: Verschachtelte CompoundMessages testen
        System.out.println("\n--- VERSCHACHTELTE MESSAGES ---");
        CompoundMessage outer = new CompoundMessage();
        outer.add(compound);  // Die erste Compound-Message
        outer.add(new SingleLineMessage("Extra Nachricht außen"));  // 3 Wörter

        CountWordsVisitor counter2 = new CountWordsVisitor();
        outer.accept(counter2);
        System.out.println("Wörter in verschachtelter Struktur: " + counter2.getWordCount());
        // Erwartung: 11 + 3 = 14 Wörter

        System.out.println("\n🎯 WICHTIG:");
        System.out.println("- CompoundMessage.accept() ruft automatisch accept() auf allen Kindern auf!");
        System.out.println("- Deshalb müssen wir in visit(CompoundMessage) nichts tun");
        System.out.println("- Die Rekursion passiert in der accept() Methode!");
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
