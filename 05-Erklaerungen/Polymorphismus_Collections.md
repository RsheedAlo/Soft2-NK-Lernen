═══════════════════════════════════════════════════════════════
  POLYMORPHISMUS & COLLECTIONS - KLAUSUR-WICHTIG!
═══════════════════════════════════════════════════════════════

🔥 WARUM WICHTIG?
In der letzten Klausur: 20 PUNKTE!
Kommt in JEDER Klausur vor!


═══════════════════════════════════════════════════════════════
  1. WAS IST POLYMORPHISMUS?
═══════════════════════════════════════════════════════════════

DEFINITION:
"Viele Formen" - Ein Objekt kann als sein eigener Typ ODER als
Basistyp verwendet werden.

BEISPIEL:
```java
Employee emp = new Developer("D1", 5000);
```
↑ Statischer Typ    ↑ Dynamischer Typ

- Statischer Typ (Deklaration): Employee
- Dynamischer Typ (new): Developer

WICHTIG:
Zur Laufzeit zählt der DYNAMISCHE Typ!
Deshalb ruft emp.getSalary() die Developer-Version auf!


═══════════════════════════════════════════════════════════════
  2. POLYMORPHISMUS MIT COLLECTIONS
═══════════════════════════════════════════════════════════════

TYPISCHES KLAUSUR-SZENARIO:

```java
// BAD - Keine Polymorphismus:
List<Developer> devs = new ArrayList<>();
List<Manager> managers = new ArrayList<>();
// Problem: Wie berechne ich Gesamt-Gehalt von ALLEN?

// GOOD - Mit Polymorphismus:
List<Employee> employees = new ArrayList<>();
employees.add(new Developer("D1", 5000));
employees.add(new Manager("M1", 7000, 3000));

double total = 0;
for(Employee emp : employees) {
    total += emp.getSalary(); // ← Automatisch richtige Version!
}
```

VORTEILE:
✓ Eine Liste für alle Typen
✓ Einfache Berechnungen
✓ Erweiterbar: Neue Employee-Typen einfach hinzufügen


═══════════════════════════════════════════════════════════════
  3. ABSTRACT KLASSEN
═══════════════════════════════════════════════════════════════

WARUM ABSTRACT?
→ Basis-Funktionalität definieren
→ Subklassen MÜSSEN bestimmte Methoden implementieren
→ Kann NICHT instanziiert werden (new Employee() → Fehler!)

BEISPIEL:
```java
abstract class Employee {
    protected String id;  // ← Alle Subklassen haben ID

    public Employee(String id) {
        this.id = id;
    }

    // Abstrakte Methode: MUSS überschrieben werden!
    public abstract double getSalary();

    // Konkrete Methode: Kann verwendet werden
    public String getId() {
        return id;
    }
}
```

REGELN:
- abstract class → kann abstract Methoden haben
- abstract Methode → KEINE Implementierung, nur Signatur
- Subklasse MUSS alle abstract Methoden überschreiben
- abstract class → kann NICHT instanziiert werden


═══════════════════════════════════════════════════════════════
  4. @OVERRIDE - WARUM WICHTIG?
═══════════════════════════════════════════════════════════════

@Override ist NICHT optional in der Klausur!

WARUM?
✓ Compiler prüft: Methode existiert in Superklasse?
✓ Verhindert Tippfehler
✓ Macht Code lesbarer

BEISPIEL - FEHLER OHNE @Override:
```java
class Developer extends Employee {
    public double getSlary() {  // ← Tippfehler!
        return 5000;
    }
}
// Kompiliert, aber getSalary() ist nicht überschrieben!
// → Fehler zur Laufzeit (abstract Methode nicht implementiert)
```

MIT @Override:
```java
class Developer extends Employee {
    @Override
    public double getSlary() {  // ← Compiler-Fehler!
        return 5000;
    }
}
// Kompiliert NICHT → Fehler sofort gefunden!
```


═══════════════════════════════════════════════════════════════
  5. SUPER() - KONSTRUKTOR VERKETTUNG
═══════════════════════════════════════════════════════════════

WICHTIG FÜR KLAUSUR:
Jede Subklasse MUSS super() aufrufen!

BEISPIEL:
```java
abstract class Employee {
    protected String id;

    public Employee(String id) {
        this.id = id;
    }
}

class Developer extends Employee {
    private double salary;

    public Developer(String id, double salary) {
        super(id);  // ← MUSS als ERSTES aufgerufen werden!
        this.salary = salary;
    }
}
```

REGELN:
- super(id) MUSS erste Zeile im Konstruktor sein
- Wenn nicht explizit: Compiler fügt super() ein (ohne Parameter)
- Wenn Superklasse keinen parameterlosen Konstruktor hat → Fehler!


═══════════════════════════════════════════════════════════════
  6. LATE BINDING (DYNAMIC DISPATCH)
═══════════════════════════════════════════════════════════════

WAS PASSIERT BEI:
```java
Employee emp = new Manager("M1", 7000, 3000);
double salary = emp.getSalary();
```

ZUR COMPILE-ZEIT:
- Compiler prüft: Gibt es getSalary() in Employee? → JA (abstract)
- Statischer Typ: Employee

ZUR LAUFZEIT:
- JVM schaut: Welcher Typ ist emp wirklich? → Manager
- Ruft Manager.getSalary() auf (NICHT Employee.getSalary!)
- Dynamischer Typ: Manager

→ Das nennt man LATE BINDING oder DYNAMIC DISPATCH
→ Entscheidung welche Methode aufgerufen wird erfolgt ZUR LAUFZEIT!


═══════════════════════════════════════════════════════════════
  7. COMPOSITE PATTERN MIT POLYMORPHISMUS
═══════════════════════════════════════════════════════════════

TYPISCHES KLAUSUR-BEISPIEL:
Message-Hierarchie (kam in SS2020 vor!)

STRUKTUR:
```java
abstract class Message {
    public abstract int getLines();
}

class SingleLineMessage extends Message {
    @Override
    public int getLines() {
        return 1;  // ← Immer 1 Zeile
    }
}

class CompoundMessage extends Message {
    private List<Message> messages;

    @Override
    public int getLines() {
        int total = 0;
        for(Message msg : messages) {
            total += msg.getLines();  // ← Rekursion!
        }
        return total;
    }
}
```

WICHTIG:
- List<Message> kann SOWOHL Single ALS AUCH Compound speichern!
- Rekursion in getLines()
- Polymorphismus macht es einfach


═══════════════════════════════════════════════════════════════
  8. HÄUFIGE KLAUSUR-FEHLER
═══════════════════════════════════════════════════════════════

FEHLER 1: @Override vergessen
```java
class Developer extends Employee {
    public double getSalary() { ... }  // ✗ Kein @Override
}
```
→ In Klausur IMMER @Override schreiben!

FEHLER 2: super() nicht aufgerufen
```java
class Developer extends Employee {
    public Developer(String id, double salary) {
        // ✗ super(id) fehlt!
        this.salary = salary;
    }
}
```
→ Compiler-Fehler!

FEHLER 3: Falsche Collection
```java
List<Developer> devs = new ArrayList<>();
devs.add(new Manager(...));  // ✗ Geht nicht!
```
→ List<Employee> verwenden!

FEHLER 4: Composite - return size() statt Summe
```java
class CompoundMessage extends Message {
    @Override
    public int getLines() {
        return messages.size();  // ✗ FALSCH!
    }
}
```
→ MUSS Summe aller getLines() sein!

FEHLER 5: Abstrakte Methode nicht implementiert
```java
class Developer extends Employee {
    // ✗ getSalary() fehlt!
}
```
→ Compiler-Fehler (abstract Methode nicht überschrieben)


═══════════════════════════════════════════════════════════════
  9. CHECKLISTE FÜR KLAUSUR
═══════════════════════════════════════════════════════════════

□ Abstract class mit abstract Methoden?
□ @Override bei ALLEN überschriebenen Methoden?
□ super(id) als ERSTE Zeile im Konstruktor?
□ List<BaseClass> statt List<SubClass>?
□ Bei Composite: Rekursion in Methoden?
□ Polymorphismus verstanden?


═══════════════════════════════════════════════════════════════
  10. TYPISCHE KLAUSUR-AUFGABE
═══════════════════════════════════════════════════════════════

AUFGABENSTELLUNG:
"Erstelle eine Hierarchie für Mitarbeiter:
- Abstract Employee mit ID und abstract getSalary()
- Developer mit fixem Gehalt
- Manager mit Basis + Bonus
- Team mit List<Employee> und getTotalSalary()"

VORGEHEN:
1. Abstract Employee mit protected String id
2. Konstruktor Employee(String id)
3. abstract double getSalary()
4. Developer extends Employee mit @Override getSalary()
5. Manager extends Employee mit @Override getSalary()
6. Team mit List<Employee> members
7. getTotalSalary() summiert alle getSalary()

ZEITAUFWAND: ~30 Minuten
PUNKTE: 15-20 Punkte


═══════════════════════════════════════════════════════════════
  ZUSAMMENFASSUNG
═══════════════════════════════════════════════════════════════

POLYMORPHISMUS = Objekt als Basistyp verwenden
LATE BINDING = Zur Laufzeit wird richtige Methode gewählt
ABSTRACT CLASS = Basis-Funktionalität + erzwungene Methoden
@OVERRIDE = Compiler-Prüfung + Lesbarkeit
SUPER() = Konstruktor-Verkettung, MUSS erste Zeile sein
LIST<BASECLASS> = Alle Subklassen speichern möglich

→ In JEDER Klausur wichtig!
→ 15-20 Punkte sicher!
→ MUSS perfekt sitzen!
