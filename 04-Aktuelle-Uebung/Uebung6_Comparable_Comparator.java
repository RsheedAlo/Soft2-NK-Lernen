// =============================================================================
// ÜBUNG 6: COMPARABLE & COMPARATOR
// =============================================================================
// ZIEL: Teste dein Wissen über Sortierung in Java
//
// WICHTIG: Lies zuerst die Erklärung in:
//          05-Erklaerungen/Comparable_Comparator.md
//
// AUFGABEN:
// 1. Student-Klasse mit Comparable implementieren
// 2. Verschiedene Comparators schreiben
// 3. TreeSet mit Comparable und Comparator nutzen
// 4. Strings nach Länge sortieren
// =============================================================================

import java.util.*;

// =============================================================================
// AUFGABE 1: Student-Klasse mit Comparable (10 Punkte)
// =============================================================================
// Erstelle eine Student-Klasse die Comparable implementiert.
// Natural Ordering: Erst nach studyProgram, dann nach lastName, dann nach firstName

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - class Student implements Comparable<Student>
// - Felder: String studyProgram, String lastName, String firstName, int semester
// - compareTo(): Vergleiche Schritt für Schritt (if != 0 return)
// - equals(): Alle Felder vergleichen
// - hashCode(): Objects.hash(alle Felder)
// ▲▲▲ TIPPS ▲▲▲

class Student implements Comparable<Student> {
    // DEINE LÖSUNG:

}


// =============================================================================
// AUFGABE 2a: Test Student Natural Ordering (3 Punkte)
// =============================================================================
// Teste die Natural Ordering von Student:
// - Erstelle 4 Studenten (verschiedene Programme und Namen)
// - Sortiere mit Collections.sort()
// - Gib sortierte Liste aus

class TestStudentComparable {
    public static void main(String[] args) {
        // DEINE LÖSUNG:

    }
}


// =============================================================================
// AUFGABE 2b: TreeSet mit Student (2 Punkte)
// =============================================================================
// Erstelle TreeSet<Student> OHNE Comparator (nutzt Comparable)
// - Füge 3 Studenten hinzu
// - Gib alle aus (sollte automatisch sortiert sein)

class TestStudentTreeSet {
    public static void main(String[] args) {
        // DEINE LÖSUNG:

    }
}


// =============================================================================
// AUFGABE 3: Comparator für Student nach Semester (5 Punkte)
// =============================================================================
// Schreibe 3 verschiedene Comparators für Student:

// 3a) Nach Semester (aufsteigend) - mit Lambda
// ▼▼▼ TIPP ▼▼▼
// Comparator<Student> bySemester = (s1, s2) -> Integer.compare(s1.getSemester(), s2.getSemester());
// ▲▲▲ TIPP ▲▲▲

class StudentComparators {
    // DEINE LÖSUNG 3a: Lambda
    public static Comparator<Student> bySemester() {
        // return ...
        return null;
    }

    // DEINE LÖSUNG 3b: Comparator.comparing()
    public static Comparator<Student> byLastName() {
        // return Comparator.comparing(...);
        return null;
    }

    // DEINE LÖSUNG 3c: Mehrere Kriterien (Semester absteigend, dann lastName)
    public static Comparator<Student> bySemesterDescThenName() {
        // return Comparator.comparingInt(...).reversed().thenComparing(...);
        return null;
    }
}


// =============================================================================
// AUFGABE 4: Test Comparators (3 Punkte)
// =============================================================================
// Teste alle 3 Comparators:
// - Erstelle Liste mit 4 Studenten
// - Sortiere mit jedem Comparator
// - Gib Ergebnisse aus

class TestStudentComparators {
    public static void main(String[] args) {
        // DEINE LÖSUNG:

    }
}


// =============================================================================
// AUFGABE 5: TreeSet mit Comparator (3 Punkte)
// =============================================================================
// Erstelle TreeSet<Student> MIT Comparator (Semester aufsteigend)
// - Füge 3 Studenten hinzu (verschiedene Semester)
// - Gib alle aus

class TestTreeSetWithComparator {
    public static void main(String[] args) {
        // DEINE LÖSUNG:

    }
}


// =============================================================================
// AUFGABE 6: String-Liste nach Länge sortieren (4 Punkte)
// =============================================================================
// Gegeben ist eine Liste von Strings.
// Sortiere sie:
// 6a) Nach Länge (kürzeste zuerst)
// 6b) Nach Länge (längste zuerst)
// 6c) Nach Länge, dann alphabetisch

class TestStringLength {
    public static void main(String[] args) {
        List<String> words = new ArrayList<>(
            Arrays.asList("Java", "is", "awesome", "Programming", "fun")
        );

        // DEINE LÖSUNG 6a: Nach Länge (kürzeste zuerst)
        System.out.println("--- Kürzeste zuerst ---");
        List<String> byLength = new ArrayList<>(words);
        // byLength.sort(...);
        // byLength.forEach(System.out::println);

        // DEINE LÖSUNG 6b: Nach Länge (längste zuerst)
        System.out.println("\n--- Längste zuerst ---");
        List<String> byLengthDesc = new ArrayList<>(words);
        // byLengthDesc.sort(...);

        // DEINE LÖSUNG 6c: Nach Länge, dann alphabetisch
        System.out.println("\n--- Länge, dann alphabetisch ---");
        List<String> complex = new ArrayList<>(words);
        // complex.sort(...);
    }
}


// =============================================================================
// AUFGABE 7: Book-Klasse mit mehreren Sortierungen (10 Punkte)
// =============================================================================
// Erstelle Book-Klasse:
// - Felder: String title, String author, int year, double price
// - Comparable: Natural Ordering nach author, dann title
// - Erstelle 3 Comparators:
//   1. Nach year (neueste zuerst)
//   2. Nach price (billigste zuerst)
//   3. Nach title (alphabetisch)

// ▼▼▼ TIPPS ▼▼▼
// - implements Comparable<Book>
// - compareTo(): author, dann title
// - Comparator 1: Comparator.comparingInt(Book::getYear).reversed()
// - Comparator 2: Comparator.comparingDouble(Book::getPrice)
// - Comparator 3: Comparator.comparing(Book::getTitle)
// ▲▲▲ TIPPS ▲▲▲

class Book implements Comparable<Book> {
    // DEINE LÖSUNG:

}

class BookComparators {
    // DEINE LÖSUNG: 3 Comparators

}


// =============================================================================
// AUFGABE 8: Null-Werte behandeln (5 Punkte)
// =============================================================================
// Gegeben ist eine Liste mit null-Werten.
// Sortiere sie mit nullsFirst() und nullsLast()

class TestNullHandling {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        // students.add(new Student(...));
        // students.add(null);
        // students.add(new Student(...));
        // students.add(null);

        // DEINE LÖSUNG:
        // Sortiere mit nullsFirst (Nulls zuerst, dann nach lastName)
        System.out.println("--- Nulls zuerst ---");
        // students.sort(Comparator.nullsFirst(...));

        // Sortiere mit nullsLast (Nulls zuletzt, dann nach lastName)
        System.out.println("\n--- Nulls zuletzt ---");
        // students.sort(Comparator.nullsLast(...));
    }
}


// =============================================================================
// AUFGABE 9: Player Ranking (8 Punkte) ⚠️ WICHTIG!
// =============================================================================
// Erstelle Player-Klasse für ein Spiel:
// - Felder: String name, int score, int level
// - KEINE Comparable Implementierung!
// - Erstelle Comparator für Ranking:
//   1. Höchster Score zuerst (absteigend)
//   2. Bei gleichem Score: Höchstes Level zuerst (absteigend)
//   3. Bei gleichem Level: Alphabetisch nach Name

// ▼▼▼ TIPP ▼▼▼
// Comparator.comparingInt(Player::getScore).reversed()
//           .thenComparingInt(Player::getLevel).reversed()
//           .thenComparing(Player::getName)
// ▲▲▲ TIPP ▲▲▲

class Player {
    // DEINE LÖSUNG:

}

class TestPlayerRanking {
    public static void main(String[] args) {
        List<Player> players = new ArrayList<>();
        // players.add(new Player("Alice", 1000, 5));
        // players.add(new Player("Bob", 1000, 7));
        // players.add(new Player("Charlie", 1200, 5));
        // players.add(new Player("Dave", 1000, 7));

        // DEINE LÖSUNG: Ranking-Comparator erstellen und sortieren

    }
}


// =============================================================================
// AUFGABE 10: Fehlersuche (5 Punkte)
// =============================================================================
// Finde und korrigiere die Fehler in folgendem Code:

class BrokenCourse implements Comparable<BrokenCourse> {
    private String name;
    private int year;

    public BrokenCourse(String name, int year) {
        this.name = name;
        this.year = year;
    }

    public String getName() { return name; }
    public int getYear() { return year; }

    // FEHLER 1: Was ist hier falsch?
    @Override
    public int compareTo(BrokenCourse other) {
        return this.year - other.year;  // Ist das sicher?
    }

    // FEHLER 2: Was fehlt hier?
    // (Tipp: equals() und hashCode())

    @Override
    public String toString() {
        return name + " (" + year + ")";
    }
}

// FEHLER 3: Was ist hier falsch?
class TestBrokenTreeSet {
    public static void main(String[] args) {
        // Klasse ohne Comparable/Comparator:
        class NoCourse {
            String name;
        }

        TreeSet<NoCourse> set = new TreeSet<>();  // Was passiert hier?
        // set.add(new NoCourse());  // Runtime Error!
    }
}

// DEINE AUFGABE:
// Schreibe die Fehler als Kommentar auf und korrigiere sie!


// =============================================================================
// CHECKLISTE: Hast du alles verstanden?
// =============================================================================
// □ compareTo() gibt negativ/0/positiv zurück?
// □ compare() gibt negativ/0/positiv zurück?
// □ Wann Comparable vs Comparator verwenden?
// □ Integer.compare() statt Subtraktion?
// □ equals() und hashCode() konsistent mit compareTo()?
// □ Comparator.comparing() nutzen können?
// □ thenComparing() für mehrere Kriterien?
// □ reversed() für umgekehrte Reihenfolge?
// □ nullsFirst() / nullsLast() für null-Werte?
// □ TreeSet braucht Comparable ODER Comparator?
//
// Wenn du alle Aufgaben gelöst hast, bist du bereit für die Klausur! 💪
// =============================================================================
