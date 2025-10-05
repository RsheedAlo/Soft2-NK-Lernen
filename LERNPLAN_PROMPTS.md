═══════════════════════════════════════════════════════════════
  LERNPLAN FÜR MORGEN - STRUKTURIERTE PROMPTS
═══════════════════════════════════════════════════════════════

Basierend auf:
✓ Letzte Klausur (LETZTE_KLAUSUR_WICHTIG.txt)
✓ Altklausuren (01-Klausuren/)
✓ Design Patterns (03-Design-Patterns-Reference/)
✓ Deine bisherigen Übungen (04-Aktuelle-Uebung/)


🔥🔥🔥 TOP-PRIORITÄT (MORGEN ZUERST!) 🔥🔥🔥
═══════════════════════════════════════════════════════════════

PROMPT 1: MVC mit fire-Methode vertiefen
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
"Ich übe für die Klausur morgen. In der letzten Klausur kamen 25 Punkte
MVC mit fire-Methode dran. Ich habe schon Uebung1 gemacht.

Erstelle mir NEUE Übungsaufgaben für fire-Methoden:
1. CircleModel mit fire-Methode (wie in klausur-uebung/src/)
2. Ein anderes Beispiel: StudentModel mit Studenten
3. Zeige verschiedene Event-Typen (AddEvent, RemoveEvent, UpdateEvent)

WAS ich üben muss:
- fire-Methode schreiben (for-Schleife über Listener)
- Event-Objekt erstellen
- View registriert sich selbst
- Model benachrichtigt bei JEDER Änderung

FORMAT: Aufgabenstellung ohne Schritte, mit ▼▼▼ TIPPS ▼▼▼ zum Aufklappen"


PROMPT 2: Streams mit flatMap() vertiefen
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
"Ich übe für die Klausur morgen. In der letzten Klausur kamen 25 Punkte
Streams mit flatMap() dran. Ich habe schon Uebung2 gemacht.

Erstelle mir NEUE schwierigere Stream-Aufgaben:
1. Sätze → Wörter → Buchstaben zählen
2. Verschachtelte Listen flach machen
3. Files → Zeilen → Wörter mit filter()
4. anyMatch() und allMatch() kombinieren
5. Collectors.groupingBy() für Wort-Häufigkeit

WICHTIG für Klausur:
- split(\"\\\\s+\") nicht vergessen!
- flatMap() vs map() verstehen
- limit(10) für erste 10
- anyMatch() für Boolean
- collect() nicht vergessen

FORMAT: Aufgabenstellung ohne Schritte, mit ▼▼▼ TIPPS ▼▼▼ zum Aufklappen"


PROMPT 3: Higher Order Functions (doForEach)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
"Ich übe für die Klausur morgen. In der letzten Klausur kam eine Aufgabe
mit doForEach(Function<String, String> action) dran.

Erkläre Higher Order Functions und erstelle Übungen:

1. FUNCTIONAL INTERFACES:
   - Function<T, R>: nimmt T, gibt R zurück
   - Consumer<T>: nimmt T, gibt void zurück
   - Predicate<T>: nimmt T, gibt boolean zurück
   - Supplier<T>: nimmt nichts, gibt T zurück

2. doForEach() IMPLEMENTIEREN:
   - Parameter: Function<String, String> action
   - For-Schleife über alle Elemente
   - action.apply(element) aufrufen
   - Ergebnis zurückspeichern

3. VERWENDUNG:
   - Methodenreferenz: String::toUpperCase
   - Lambda: s -> s + "!"
   - Lambda: s -> String.valueOf(s.length())

Erstelle 3 Übungsaufgaben mit verschiedenen Function-Typen.
FORMAT: Aufgabenstellung ohne Schritte, mit ▼▼▼ TIPPS ▼▼▼"


PROMPT 4: Collections mit Override (printEntry)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
"Ich übe für die Klausur morgen. In der letzten Klausur kamen 20 Punkte
Collections mit Override dran. Ich habe schon Uebung3 gemacht.

Erstelle mir NEUE Übungsaufgaben für Polymorphismus:
1. Message-Hierarchie (wie in Altklausuren):
   - Abstract Message mit abstract getLines()
   - SingleLineMessage extends Message
   - CompoundMessage extends Message (enthält List<Message>)

2. Employee-Hierarchie:
   - Abstract Employee mit abstract getSalary()
   - Developer, Manager extends Employee
   - Team mit List<Employee>

WICHTIG:
- @Override nicht vergessen!
- super(id) im Konstruktor
- List<BaseClass> für Polymorphismus
- printEntry() oder getSalary() in allen Klassen

FORMAT: Aufgabenstellung ohne Schritte, mit ▼▼▼ TIPPS ▼▼▼"


🟠🟠🟠 HOHE PRIORITÄT (Falls Zeit übrig) 🟠🟠🟠
═══════════════════════════════════════════════════════════════

PROMPT 5: Visitor Pattern
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
"Ich übe für die Klausur. Visitor Pattern kam in SS2020 Klausur vor (14 Punkte).

Erkläre Visitor Pattern komplett und erstelle Übung:

KONZEPT:
1. Visitor Interface mit visit() Methoden für jeden Typ
2. Accept-Methode in besuchten Klassen: visitor.visit(this)
3. Double Dispatch: Zur Laufzeit wird richtige visit() gewählt

BEISPIEL aus Klausur (siehe: 01-Klausuren/Klausur_SS2020_Praktisch.txt):
- ExpressionVisitor mit visit(NumberExpr), visit(AddExpr), visit(MultExpr)
- Jede Expression hat accept(ExpressionVisitor v)
- CountVisitor zählt alle Expressions

Erstelle eine Übungsaufgabe mit Message-Hierarchie:
- MessageVisitor mit visit(SingleLine), visit(CompoundMessage)
- CountWordsVisitor zählt alle Wörter
- PrintVisitor gibt alles aus

FORMAT: Aufgabenstellung ohne Schritte, mit ▼▼▼ TIPPS ▼▼▼"


PROMPT 6: Composite Pattern
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
"Ich übe für die Klausur. Composite Pattern kam in SS2020 Klausur vor (12 Punkte).

Erkläre Composite Pattern und erstelle Übung:

KONZEPT:
1. Component (Interface/Abstract): Gemeinsame Methoden
2. Leaf: Einzelnes Element, keine Kinder
3. Composite: Enthält List<Component>, delegiert an Kinder

BEISPIEL aus Klausur:
- Message (Component) mit getSize()
- SimpleMessage (Leaf): return 1
- GroupMessage (Composite): List<Message>, getSize() summiert alle Kinder

WICHTIG:
- add() und remove() NUR in Composite!
- Rekursion in Composite für getSize(), print(), etc.
- List<Component> speichert Leaf UND Composite

Erstelle 2 Übungsaufgaben:
1. File-System (File, Directory)
2. Employee-Hierarchie (Employee, Team)

FORMAT: Aufgabenstellung ohne Schritte, mit ▼▼▼ TIPPS ▼▼▼"


PROMPT 7: Comparable und Comparator
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
"Ich übe für die Klausur. Comparable/Comparator kam in SS2020 vor (8 Punkte).

Erkläre den Unterschied und erstelle Übungen:

COMPARABLE:
- Interface Comparable<T>
- Methode: int compareTo(T other)
- Natürliche Ordnung definieren
- Beispiel: Person nach Alter sortieren

COMPARATOR:
- Interface Comparator<T>
- Methode: int compare(T o1, T o2)
- Alternative Sortierungen
- Lambda: Comparator.comparing(Person::getName)
- Reverse: comparator.reversed()

VERWENDUNG:
- Collections.sort(list) für Comparable
- Collections.sort(list, comparator) für Comparator
- list.stream().sorted() bzw sorted(comparator)

Erstelle Übungsaufgaben:
1. Person mit compareTo() nach Alter
2. Verschiedene Comparatoren (Name, Alter, Stadt)
3. Stream mit sorted(Comparator.comparing(...))

FORMAT: Aufgabenstellung ohne Schritte, mit ▼▼▼ TIPPS ▼▼▼"


🟡🟡🟡 MITTLERE PRIORITÄT (Theorie-Teil) 🟡🟡🟡
═══════════════════════════════════════════════════════════════

PROMPT 8: Generics & Wildcards
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
"Erkläre Generics und Wildcards für die Klausur (Multiple Choice Teil).

WICHTIGE KONZEPTE:
1. <? extends T> - Upper Bounded Wildcard
   - Lesen: OK (gibt T oder Subklasse zurück)
   - Schreiben: NICHT möglich (außer null)
   - Beispiel: List<? extends Number> → kann Integer, Double enthalten

2. <? super T> - Lower Bounded Wildcard
   - Lesen: Nur als Object
   - Schreiben: OK (T oder Subklasse)
   - Beispiel: List<? super Integer> → kann Number, Object enthalten

3. WARUM List<Student> nicht zu List<Person> zuweisbar:
   - Arrays: String[] zu Object[] OK (Kovarianz)
   - Generics: List<String> zu List<Object> NICHT OK (Invarianz)
   - Grund: Type Safety

4. Type Erasure:
   - Generics nur zur Compile-Zeit
   - Zur Laufzeit: List<String> wird zu List

Erstelle Multiple-Choice Fragen wie in Klausur.
Zeige typische Fallen und Beispiele."


PROMPT 9: Interfaces (Default Methods, Functional)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
"Erkläre Interfaces für die Klausur (kam in SS2020 vor).

KONZEPTE:
1. Functional Interface:
   - Genau EINE abstrakte Methode
   - @FunctionalInterface Annotation
   - Kann für Lambda verwendet werden

2. Default Methods:
   - Methode MIT Implementierung in Interface
   - Keyword: default
   - Beispiel: default void doubled() { scale(2); }

3. Static Methods in Interface:
   - Utility-Methoden
   - Aufruf: InterfaceName.staticMethod()

4. Multiple Interface Implementation:
   - class X implements A, B
   - Diamond Problem bei default methods

BEISPIEL aus Klausur:
- Interface Scalable mit scale(double factor)
- Default-Methode doubled() ruft scale(2) auf
- Point implementiert Scalable

Erstelle Übungsaufgaben mit Scalable, Printable, Comparable.
FORMAT: Aufgabenstellung ohne Schritte, mit ▼▼▼ TIPPS ▼▼▼"


PROMPT 10: Vererbung & Sichtbarkeiten
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
"Erkläre Vererbung für die Klausur (Theorie-Teil).

SICHTBARKEITEN:
1. private: Nur in eigener Klasse
2. package-private (kein Keyword): Nur im selben Package
3. protected: Im selben Package UND in Subklassen
4. public: Überall

OVERRIDE REGELN:
1. Rückgabetyp: Gleich ODER Subklasse (Kovarianz)
2. Exceptions: Gleich ODER weniger/spezieller
3. Sichtbarkeit: Gleich ODER mehr (protected → public OK)
4. final Methoden: NICHT überschreibbar

WICHTIG:
- super() Konstruktor-Aufruf (erste Zeile!)
- final class: Nicht erweiterbar
- abstract class: Nicht instanziierbar
- Dynamischer Typ (new) vs Statischer Typ (Deklaration)

Erstelle Multiple-Choice Fragen wie in Klausur.
Zeige typische Fehler und Fallen."


🟢🟢🟢 NIEDRIGE PRIORITÄT (Falls alles fertig) 🟢🟢🟢
═══════════════════════════════════════════════════════════════

PROMPT 11: JUnit Testing
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
"Erkläre JUnit kurz für Theorie-Teil.

BLACK-BOX vs WHITE-BOX:
- Black-Box: Nur Spezifikation, nicht Code
- White-Box: Code-basiert, Coverage

ANNOTATIONS:
- @Test: Testmethode
- @BeforeEach: Vor jedem Test
- @AfterEach: Nach jedem Test
- @BeforeAll: Einmal vor allen (static!)

ASSERTIONS:
- assertEquals(expected, actual)
- assertTrue(condition)
- assertThrows(Exception.class, () -> code)

COVERAGE:
- Statement Coverage: Jede Zeile einmal
- Branch Coverage: Jeder Zweig (if/else)
- Path Coverage: Alle Pfade durch Code

Kurze Zusammenfassung mit Beispiel."


PROMPT 12: Decorator Pattern
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
"Erkläre Decorator Pattern (kam in November 2010 Klausur).

KONZEPT:
- Wrapper-Klasse die gleiches Interface implementiert
- Delegation an wrapped Object
- Zusätzliche Funktionalität hinzufügen

BEISPIEL aus Klausur (siehe: 01-Klausuren/Klausur_November2010.txt):
- Rectangle mit draw()
- GridRectangle extends Rectangle (Decorator)
- Konstruktor nimmt Rectangle
- draw() ruft super.draw() + zeichnet Grid

WICHTIG:
- Decorator hat gleiches Interface wie Original
- Delegation: wrapped.method()
- Kann verschachtelt werden

Kurzes Beispiel mit InputStream (BufferedInputStream decoriert InputStream)."


PROMPT 13: Module System & UML
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
"Erkläre kurz für Theorie-Teil:

MODULE SYSTEM:
- module-info.java
- exports com.example.package
- requires other.module

UML:
- Sequenzdiagramm: Objekte, Lifelines, Messages
- Zustandsdiagramm: trigger [condition] / action

Nur kurze Übersicht, nicht tief."


═══════════════════════════════════════════════════════════════
  WIE DIE PROMPTS MORGEN VERWENDEN
═══════════════════════════════════════════════════════════════

VORMITTAG (4h):
1. PROMPT 1 (MVC fire-Methode) → 1.5h üben
2. PROMPT 2 (Streams flatMap) → 1.5h üben
3. PROMPT 3 (Higher Order Functions) → 1h üben

MITTAG (2h):
4. PROMPT 4 (Collections Override) → 1h üben
5. PROMPT 8 (Generics Theorie) → 30min lesen
6. PROMPT 10 (Vererbung Theorie) → 30min lesen

NACHMITTAG (2h):
7. PROMPT 5 (Visitor Pattern) → 1h
8. PROMPT 6 (Composite Pattern) → 1h

ABEND:
- LETZTE_KLAUSUR_WICHTIG.txt nochmal lesen
- Theorie-Prompts durchgehen
- Früh schlafen! 😴


═══════════════════════════════════════════════════════════════
  WICHTIGSTE DATEI FÜR MORGEN
═══════════════════════════════════════════════════════════════

📌 01-Klausuren/LETZTE_KLAUSUR_WICHTIG.txt
   → Hier steht GENAU was in der letzten Klausur dran kam!
   → 50 Punkte = MVC + Streams!

📌 KLAUSUR_OVERVIEW.txt
   → Kompletter Überblick mit Prioritäten

📌 03-Design-Patterns-Reference/Design_Patterns_Uebersicht.txt
   → Alle 9 Patterns mit Ranking


VIEL ERFOLG MORGEN! 🍀🔥
Du schaffst das! Konzentrier dich auf TOP-PRIORITÄT!
