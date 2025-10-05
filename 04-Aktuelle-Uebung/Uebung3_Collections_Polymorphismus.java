// =============================================================================
// ÜBUNG 3: Collections mit Polymorphismus (20 Punkte Klausur-Aufgabe!)
// =============================================================================
// ZIEL: Verstehe Polymorphismus, @Override, abstract Klassen
//
// HINTERGRUND:
// In der letzten Klausur kamen 20 Punkte für Collections mit Override dran!
// Es ging um 3 Klassen mit Vererbung, alle mit einer printEntry() Methode.
// Die Klassen wurden in einer List<BaseClass> gespeichert und polymorphisch aufgerufen.
//
// WICHTIGSTE KONZEPTE:
// - Polymorphismus: entry.printEntry() ruft automatisch die richtige Version auf
// - List<BaseClass> kann alle Subklassen speichern
// - @Override ist wichtig!
// - super(id) im Konstruktor aufrufen
// =============================================================================

import java.util.*;

// =============================================================================
// TEIL 1: MESSAGE-HIERARCHIE (Composite Pattern ähnlich)
// =============================================================================
// Dieses Beispiel kommt aus den Altklausuren (SS2020 Praktisch)!
// Es ist eine Mischung aus Composite Pattern und Polymorphismus.

// =============================================================================
// AUFGABE 3.1a: Abstract Message Klasse (5 Punkte)
// =============================================================================
// Erstelle eine abstrakte Basisklasse Message.
// Jede Message hat eine bestimmte Anzahl von Zeilen (lines).

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - abstract class Message
// - public abstract int getLines()  (abstrakte Methode!)
// ▲▲▲ TIPPS ▲▲▲

abstract class Message {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 3.1b: SingleLineMessage (5 Punkte)
// =============================================================================
// Erstelle SingleLineMessage die genau 1 Zeile hat.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - extends Message
// - private String text
// - Konstruktor: String text
// - @Override getLines() → return 1
// - Optional: getText() Methode
// ▲▲▲ TIPPS ▲▲▲

class SingleLineMessage extends Message {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 3.1c: CompoundMessage (10 Punkte) ⚠️ WICHTIG!
// =============================================================================
// Erstelle CompoundMessage die mehrere Messages enthält.
// Die Anzahl Zeilen ist die SUMME aller enthaltenen Messages!
// (Das ist Composite Pattern!)

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - extends Message
// - private List<Message> messages
// - Konstruktor: Initialisiere messages = new ArrayList<>()
// - public void add(Message msg) → messages.add(msg)
// - @Override getLines() → Schleife über messages, summiere alle getLines()
// ▲▲▲ TIPPS ▲▲▲

class CompoundMessage extends Message {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 3.1d: Test Message-Hierarchie (3 Punkte)
// =============================================================================
// Teste die Message-Hierarchie:
// - Erstelle 3 SingleLineMessages
// - Erstelle CompoundMessage und füge die 3 Messages hinzu
// - Gib getLines() aus (sollte 3 sein)
// - Erstelle eine weitere CompoundMessage mit 2 CompoundMessages drin (Schachtelung!)

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - new SingleLineMessage("Hello")
// - compound.add(single1)
// - Polymorphismus: List<Message> kann Single UND Compound speichern!
// ▲▲▲ TIPPS ▲▲▲

class TestMessages {
    public static void main(String[] args) {
        // DEINE LÖSUNG:

    }
}


// =============================================================================
// TEIL 2: EMPLOYEE-HIERARCHIE (Typisches Klausur-Beispiel)
// =============================================================================
// Ein klassisches Beispiel für Polymorphismus in Collections.
// Kommt sehr häufig in Klausuren vor!

// =============================================================================
// AUFGABE 3.2a: Abstract Employee Klasse (5 Punkte)
// =============================================================================
// Erstelle eine abstrakte Employee-Klasse.
// Jeder Employee hat eine ID und ein Gehalt.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - abstract class Employee
// - protected String id
// - Konstruktor mit Parameter id
// - public abstract double getSalary()
// - public String getId() { return id; }
// ▲▲▲ TIPPS ▲▲▲

abstract class Employee {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 3.2b: Developer Klasse (4 Punkte)
// =============================================================================
// Erstelle Developer mit fixem Gehalt.
// Ausgabe: "Developer [ID: D1] - Salary: 5000.0"

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - extends Employee
// - private double salary
// - Konstruktor: String id, double salary → super(id) aufrufen!
// - @Override getSalary() → return salary
// ▲▲▲ TIPPS ▲▲▲

class Developer extends Employee {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 3.2c: Manager Klasse (4 Punkte)
// =============================================================================
// Erstelle Manager mit Basis-Gehalt + Bonus.
// Ausgabe: "Manager [ID: M1] - Salary: 8000.0 (Base: 6000.0 + Bonus: 2000.0)"

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - extends Employee
// - private double baseSalary, bonus
// - Konstruktor: String id, double baseSalary, double bonus
// - @Override getSalary() → return baseSalary + bonus
// ▲▲▲ TIPPS ▲▲▲

class Manager extends Employee {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 3.2d: Team Klasse (7 Punkte) ⚠️ WICHTIG!
// =============================================================================
// Erstelle Team-Klasse die mehrere Employees verwaltet.
// Kann SOWOHL Developer ALS AUCH Manager speichern (Polymorphismus!)

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - private List<Employee> members
// - Konstruktor: Initialisiere members
// - public void addMember(Employee emp) → members.add(emp)
// - public double getTotalSalary() → Schleife, summiere alle getSalary()
// - public void printAll() → Schleife, rufe für jeden eine print-Methode
// ▲▲▲ TIPPS ▲▲▲

class Team {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 3.2e: Test Employee-Hierarchie (3 Punkte)
// =============================================================================
// Teste die Employee-Hierarchie:
// - Erstelle Team
// - Füge 2 Developers hinzu (jeweils 5000, 6000)
// - Füge 1 Manager hinzu (Base: 7000, Bonus: 3000)
// - Gib Total Salary aus (sollte 21000 sein)

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - new Developer("D1", 5000)
// - new Manager("M1", 7000, 3000)
// - team.addMember(dev1)
// - Polymorphismus: List<Employee> speichert beide Typen!
// ▲▲▲ TIPPS ▲▲▲

class TestEmployees {
    public static void main(String[] args) {
        // DEINE LÖSUNG:

    }
}


// =============================================================================
// ERKLÄRUNG: WARUM POLYMORPHISMUS SO WICHTIG IST
// =============================================================================
//
// OHNE POLYMORPHISMUS (Schlecht):
// List<Developer> developers = new ArrayList<>();
// List<Manager> managers = new ArrayList<>();
// // Problem: 2 separate Listen! Wie berechne ich Gesamt-Gehalt?
//
// MIT POLYMORPHISMUS (Gut):
// List<Employee> employees = new ArrayList<>();
// employees.add(new Developer("D1", 5000));  // ← Developer ist ein Employee
// employees.add(new Manager("M1", 7000, 3000)); // ← Manager ist ein Employee
//
// for(Employee emp : employees) {
//     total += emp.getSalary(); // ← Ruft automatisch richtige Version!
// }
//
// WICHTIG:
// - Statischer Typ: Employee (Deklaration)
// - Dynamischer Typ: Developer oder Manager (new ...)
// - Zur Laufzeit wird DYNAMISCHER Typ verwendet!
// - Deshalb: emp.getSalary() ruft Developer.getSalary() oder Manager.getSalary()
//
// Das nennt man: LATE BINDING oder DYNAMIC DISPATCH
//
// =============================================================================


// =============================================================================
// CHECKLISTE FÜR KLAUSUR
// =============================================================================
// □ Abstrakte Klasse mit abstract Methoden erstellt?
// □ @Override bei allen überschriebenen Methoden?
// □ super(id) im Konstruktor der Subklassen aufgerufen?
// □ List<BaseClass> verwendet (nicht List<SubClass>)?
// □ Polymorphismus verstanden: baseClass.method() ruft richtige Version
// □ Bei Composite: Rekursion in getLines() / getTotalSalary()?
//
// HÄUFIGE FEHLER:
// ✗ @Override vergessen
// ✗ super(id) nicht aufgerufen → Compiler-Fehler!
// ✗ List<Developer> statt List<Employee> → kein Polymorphismus!
// ✗ Abstrakte Methode in Subklasse nicht implementiert
// ✗ Bei CompoundMessage: return messages.size() statt Summe aller getLines()
//
// =============================================================================
