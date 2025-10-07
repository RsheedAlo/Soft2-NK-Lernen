# EXCEPTIONS, THREADS & TESTING - Schnell & Idiotensicher

## 🎯 Das Wichtigste in 3 Sätzen

1. **Exceptions** = Fehlerbehandlung (checked = muss behandelt werden, unchecked = optional)
2. **Threads** = Parallele Ausführung (`start()` = neuer Thread, `run()` = normaler Aufruf!)
3. **Testing** = Code testen mit JUnit (`@Test`, `assertEquals()`, `assertTrue()`)

---

# TEIL 1: EXCEPTIONS - Fehlerbehandlung

## 🧠 Eselsbrücke

**CHECKED** = **KONTROLLIERT** (Compiler zwingt dich zu behandeln)
- Beispiel: FileNotFoundException (Datei könnte fehlen)

**UNCHECKED** = **UNKONTROLLIERT** (Compiler egal, behandle wenn du willst)
- Beispiel: NullPointerException (dein Fehler!)

---

## Exception Hierarchie

```
                Throwable
                    |
        +-----------+-----------+
        |                       |
    Error                   Exception
  (schwer)                      |
                    +-----------+-----------+
                    |                       |
            IOException            RuntimeException
          (CHECKED)                  (UNCHECKED)
                                         |
                                   NullPointerException
                                   ArrayIndexOutOfBounds
                                   IllegalArgumentException
```

**WICHTIG:**
- **Error** = Schwerer Fehler (OutOfMemoryError) → NICHT catchen!
- **Checked Exception** = Muss behandelt werden oder mit `throws` deklarieren
- **Unchecked Exception** = RuntimeException → optional behandeln

---

## Checked vs Unchecked Exceptions

### Checked Exceptions (muss behandelt werden!)

```java
// MUSS behandelt werden! Compiler-Fehler ohne try-catch!
public void readFile() {
    try {
        FileReader file = new FileReader("file.txt");  // IOException!
    } catch (IOException e) {
        System.out.println("Fehler: " + e.getMessage());
    }
}

// ODER mit throws deklarieren:
public void readFile() throws IOException {
    FileReader file = new FileReader("file.txt");  // Caller muss behandeln!
}
```

**Beispiele:**
- IOException
- SQLException
- ClassNotFoundException

### Unchecked Exceptions (optional behandeln)

```java
// Kompiliert OHNE try-catch! (aber crasht zur Laufzeit)
public void divide(int a, int b) {
    int result = a / b;  // ArithmeticException bei b=0!
}

// Optional behandeln:
public void divide(int a, int b) {
    try {
        int result = a / b;
    } catch (ArithmeticException e) {
        System.out.println("Division durch 0!");
    }
}
```

**Beispiele:**
- NullPointerException
- ArrayIndexOutOfBoundsException
- ArithmeticException
- IllegalArgumentException

---

## Try-Catch-Finally

### Grundstruktur

```java
try {
    // Code der Exception werfen könnte
    int result = 10 / 0;
}
catch (ArithmeticException e) {
    // Behandlung für ArithmeticException
    System.out.println("Division durch 0!");
}
catch (Exception e) {
    // Behandlung für alle anderen Exceptions
    System.out.println("Fehler: " + e.getMessage());
}
finally {
    // WIRD IMMER AUSGEFÜHRT (auch bei return!)
    System.out.println("Cleanup");
}
```

**WICHTIG:**
- `finally` wird **IMMER** ausgeführt (auch bei return, break, continue)
- Reihenfolge: spezifische Exception VOR allgemeiner Exception

### Mehrere Exceptions catchen

```java
// Methode 1: Separate catch-Blöcke
try {
    // ...
} catch (IOException e) {
    System.out.println("IO Error");
} catch (SQLException e) {
    System.out.println("SQL Error");
}

// Methode 2: Multi-catch (seit Java 7)
try {
    // ...
} catch (IOException | SQLException e) {
    System.out.println("Error: " + e);
}
```

---

## Try-With-Resources (seit Java 7)

**Problem:** Ressourcen müssen geschlossen werden!

```java
// ALT (schlecht):
FileReader file = null;
try {
    file = new FileReader("file.txt");
    // ...
} catch (IOException e) {
    // ...
} finally {
    if (file != null) {
        try {
            file.close();  // Muss manuell geschlossen werden!
        } catch (IOException e) { }
    }
}
```

**NEU (gut):**

```java
// Try-with-resources: Schließt automatisch!
try (FileReader file = new FileReader("file.txt")) {
    // ...
}
catch (IOException e) {
    System.out.println("Fehler: " + e);
}
// file.close() wird AUTOMATISCH aufgerufen!
```

**Regel:** Funktioniert mit allen Klassen die `AutoCloseable` implementieren

---

## Eigene Exceptions erstellen

```java
// Eigene checked Exception
class InvalidAgeException extends Exception {
    public InvalidAgeException(String message) {
        super(message);
    }
}

// Verwendung:
public void setAge(int age) throws InvalidAgeException {
    if (age < 0 || age > 120) {
        throw new InvalidAgeException("Alter ungültig: " + age);
    }
    this.age = age;
}

// Caller muss behandeln:
try {
    person.setAge(150);
} catch (InvalidAgeException e) {
    System.out.println(e.getMessage());  // "Alter ungültig: 150"
}
```

**Eigene unchecked Exception:**
```java
// Extends RuntimeException statt Exception!
class InvalidAgeException extends RuntimeException {
    public InvalidAgeException(String message) {
        super(message);
    }
}
```

---

## 🔥 Klausur-Wissen: Exceptions

**Frage: Checked oder Unchecked?**
- IOException → CHECKED
- NullPointerException → UNCHECKED
- SQLException → CHECKED
- IllegalArgumentException → UNCHECKED

**Frage: Was gibt folgender Code aus?**
```java
try {
    System.out.println("A");
    return;
} finally {
    System.out.println("B");
}
```
**Antwort:** A, dann B (finally wird IMMER ausgeführt!)

---

# TEIL 2: THREADS - Parallele Ausführung

## 🧠 Eselsbrücke

**start()** = **START** neuer Thread (parallel!)
**run()** = **RUN** normaler Methodenaufruf (kein neuer Thread!)

```java
thread.start();  // Neuer Thread → läuft parallel ✅
thread.run();    // Normaler Aufruf → KEIN neuer Thread ❌
```

---

## Thread erstellen - 2 Methoden

### Methode 1: Runnable Interface (besser!)

```java
// Alt: Anonyme Klasse
Runnable task = new Runnable() {
    @Override
    public void run() {
        System.out.println("Thread läuft!");
    }
};
Thread thread = new Thread(task);
thread.start();  // Startet neuen Thread!
```

**Neu: Lambda (seit Java 8)**
```java
Runnable task = () -> System.out.println("Thread läuft!");
Thread thread = new Thread(task);
thread.start();

// Oder direkt:
new Thread(() -> System.out.println("Running")).start();
```

### Methode 2: Thread-Klasse erweitern (veraltet)

```java
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread läuft!");
    }
}

MyThread thread = new MyThread();
thread.start();
```

**Problem:** Kann nur 1x erben! Deshalb Runnable bevorzugen!

---

## start() vs run() - WICHTIG!

```java
Runnable task = () -> {
    for (int i = 0; i < 5; i++) {
        System.out.println("Thread: " + i);
    }
};
Thread thread = new Thread(task);

// RICHTIG: Neuer Thread
thread.start();  // Läuft PARALLEL zum Main-Thread!

// FALSCH: Kein neuer Thread
thread.run();    // Läuft SEQUENZIELL im Main-Thread!
```

**Visualisierung start():**
```
Main-Thread:  A ─── B ─── C ─── D
                     ↓
Neuer Thread:        X ─── Y ─── Z
                   (parallel!)
```

**Visualisierung run():**
```
Main-Thread: A ─── B ─── X ─── Y ─── Z ─── C ─── D
                        (kein neuer Thread!)
```

---

## Thread-Beispiele

### Beispiel 1: Einfacher Thread

```java
public class ThreadExample {
    public static void main(String[] args) {
        // Thread mit Lambda
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 1: " + i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                System.out.println("Thread 2: " + i);
            }
        });

        t1.start();  // Startet parallel
        t2.start();  // Startet parallel

        System.out.println("Main fertig!");
    }
}
```

**Output (nicht deterministisch!):**
```
Main fertig!
Thread 1: 0
Thread 2: 0
Thread 1: 1
Thread 2: 1
...
```

### Beispiel 2: Thread mit sleep()

```java
Thread thread = new Thread(() -> {
    for (int i = 0; i < 5; i++) {
        System.out.println("Count: " + i);
        try {
            Thread.sleep(1000);  // 1 Sekunde warten
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
});
thread.start();
```

---

## Thread-States (Lifecycle)

```
NEW → RUNNABLE ⇄ BLOCKED/WAITING → TERMINATED
      (start())  (synchronized/wait)  (fertig)
```

**States:**
1. **NEW** - Thread erstellt, aber nicht gestartet
2. **RUNNABLE** - Thread läuft oder bereit zu laufen
3. **BLOCKED** - Wartet auf synchronized Lock
4. **WAITING** - Wartet auf notify()
5. **TIMED_WAITING** - Wartet mit Timeout (sleep())
6. **TERMINATED** - Thread fertig

---

## synchronized - Thread-Sicherheit

**Problem:** Mehrere Threads greifen auf gleiche Variable zu!

```java
class Counter {
    private int count = 0;

    // OHNE synchronized: Race Condition!
    public void increment() {
        count++;  // NICHT atomar! Kann falsch werden!
    }

    // MIT synchronized: Thread-sicher!
    public synchronized void incrementSafe() {
        count++;  // Nur ein Thread gleichzeitig!
    }
}
```

**synchronized Block:**
```java
public void increment() {
    synchronized(this) {
        count++;
    }
}
```

---

## wait() und notify() - Thread Kommunikation

```java
class SharedResource {
    private boolean ready = false;

    // Thread 1: Wartet
    public synchronized void waitForSignal() throws InterruptedException {
        while (!ready) {
            wait();  // Gibt Lock frei und wartet
        }
        System.out.println("Signal empfangen!");
    }

    // Thread 2: Signalisiert
    public synchronized void sendSignal() {
        ready = true;
        notify();  // Weckt wartenden Thread auf
    }
}
```

**WICHTIG:**
- `wait()` und `notify()` nur in synchronized Block!
- `wait()` gibt Lock frei
- `notify()` weckt EINEN wartenden Thread
- `notifyAll()` weckt ALLE wartenden Threads

---

## 🔥 Klausur-Wissen: Threads

**Frage: Was ist der Unterschied zwischen start() und run()?**
- `start()` → Neuer Thread (parallel)
- `run()` → Normaler Methodenaufruf (kein neuer Thread)

**Frage: Wie erstellt man einen Thread mit Lambda?**
```java
new Thread(() -> System.out.println("Running")).start();
```

**Frage: Wofür ist synchronized?**
- Thread-Sicherheit: Nur ein Thread gleichzeitig in der Methode

**Frage: Was macht wait()?**
- Gibt Lock frei und wartet auf notify()

---

# TEIL 3: TESTING - JUnit

## 🧠 Eselsbrücke

**BLACK-BOX** = **BLIND** (Ohne Code zu sehen, nur Input/Output)
**WHITE-BOX** = **WISSEN** (Mit Code-Kenntnis, teste alle Pfade)

---

## Black-Box vs White-Box Testing

### Black-Box Testing

**Was:** Teste OHNE Code zu kennen (nur Spezifikation)

**Ziel:** Funktioniert es wie spezifiziert?

**Beispiel:**
```
Spezifikation: divide(a, b) gibt a/b zurück
Tests:
- divide(10, 2) → 5 ✅
- divide(10, 0) → Exception? ✅
- divide(-10, 2) → -5 ✅
```

**Vorteile:**
- Unabhängig von Implementation
- Testet User-Perspektive

**Nachteile:**
- Kann nicht alle Code-Pfade testen

### White-Box Testing

**Was:** Teste MIT Code-Kenntnis (alle Pfade abdecken)

**Ziel:** Alle Statements/Branches/Paths testen

**Beispiel:**
```java
public String grade(int score) {
    if (score >= 90) return "A";      // Path 1
    else if (score >= 80) return "B"; // Path 2
    else if (score >= 70) return "C"; // Path 3
    else return "F";                  // Path 4
}
```

**White-Box Tests:**
- score = 95 → Path 1
- score = 85 → Path 2
- score = 75 → Path 3
- score = 60 → Path 4

**Vorteile:**
- Hohe Code-Coverage
- Findet versteckte Bugs

**Nachteile:**
- Aufwändig
- Ändert sich mit Code

---

## JUnit Annotations

### @Test - Testmethode

```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    void testAdd() {
        Calculator calc = new Calculator();
        assertEquals(5, calc.add(2, 3));  // Erwartet 5
    }
}
```

### @BeforeEach - Vor jedem Test

```java
class CalculatorTest {
    private Calculator calc;

    @BeforeEach
    void setUp() {
        calc = new Calculator();  // Wird vor JEDEM Test aufgerufen!
        System.out.println("Setup");
    }

    @Test
    void testAdd() {
        assertEquals(5, calc.add(2, 3));
    }

    @Test
    void testSubtract() {
        assertEquals(1, calc.subtract(3, 2));
    }
}
```

### @AfterEach - Nach jedem Test

```java
@AfterEach
void tearDown() {
    System.out.println("Cleanup");  // Nach JEDEM Test
}
```

### @BeforeAll und @AfterAll - Einmalig

```java
@BeforeAll
static void setUpAll() {
    System.out.println("Setup ALL");  // Einmal VOR allen Tests
}

@AfterAll
static void tearDownAll() {
    System.out.println("Cleanup ALL");  // Einmal NACH allen Tests
}
```

**Reihenfolge:**
```
@BeforeAll
    @BeforeEach
        @Test (Test 1)
    @AfterEach
    @BeforeEach
        @Test (Test 2)
    @AfterEach
@AfterAll
```

---

## Assertions - Testbedingungen

### assertEquals - Gleich?

```java
@Test
void testAdd() {
    assertEquals(5, calc.add(2, 3));  // Erwartet: 5, Ist: ?
    assertEquals(5, calc.add(2, 3), "Addition falsch!");  // Mit Message
}
```

### assertTrue / assertFalse - Boolean?

```java
@Test
void testIsEven() {
    assertTrue(isEven(4));   // Muss true sein
    assertFalse(isEven(3));  // Muss false sein
}
```

### assertNull / assertNotNull - Null?

```java
@Test
void testGetUser() {
    assertNotNull(getUser(1));  // Darf nicht null sein
    assertNull(getUser(999));   // Muss null sein
}
```

### assertThrows - Exception erwartet?

```java
@Test
void testDivideByZero() {
    Calculator calc = new Calculator();

    // Erwartet ArithmeticException!
    assertThrows(ArithmeticException.class, () -> {
        calc.divide(10, 0);
    });
}
```

### assertAll - Mehrere Assertions

```java
@Test
void testPerson() {
    Person p = new Person("Alice", 25);

    assertAll("Person",
        () -> assertEquals("Alice", p.getName()),
        () -> assertEquals(25, p.getAge())
    );
}
```

---

## Test-Coverage

### 1. Statement Coverage (Zeilen-Abdeckung)

**Ziel:** Jede Zeile mindestens 1x ausführen

```java
public int max(int a, int b) {
    if (a > b) return a;  // Zeile 1
    return b;              // Zeile 2
}
```

**100% Statement Coverage:**
- Test 1: max(5, 3) → Zeile 1 ✅
- Test 2: max(3, 5) → Zeile 2 ✅

### 2. Branch Coverage (Zweig-Abdeckung)

**Ziel:** Jede if/else-Verzweigung testen

```java
public String grade(int score) {
    if (score >= 50) {       // Branch 1: true
        return "Pass";
    } else {                 // Branch 2: false
        return "Fail";
    }
}
```

**100% Branch Coverage:**
- Test 1: grade(60) → Branch 1 (true) ✅
- Test 2: grade(40) → Branch 2 (false) ✅

### 3. Path Coverage (Pfad-Abdeckung)

**Ziel:** Alle möglichen Pfade testen

```java
public String check(int a, int b) {
    if (a > 0) {             // Branch A
        if (b > 0) {         // Branch B
            return "Both positive";
        } else {
            return "A positive";
        }
    } else {
        if (b > 0) {
            return "B positive";
        } else {
            return "Both negative";
        }
    }
}
```

**Alle Pfade:**
- Path 1: A true, B true → check(1, 1)
- Path 2: A true, B false → check(1, -1)
- Path 3: A false, B true → check(-1, 1)
- Path 4: A false, B false → check(-1, -1)

---

## Vollständige Testklasse Beispiel

```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int divide(int a, int b) {
        if (b == 0) throw new ArithmeticException("Division by zero");
        return a / b;
    }

    public boolean isEven(int n) {
        return n % 2 == 0;
    }
}

class CalculatorTest {
    private Calculator calc;

    @BeforeAll
    static void setUpAll() {
        System.out.println("=== Starting Tests ===");
    }

    @BeforeEach
    void setUp() {
        calc = new Calculator();
        System.out.println("Calculator created");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test finished\n");
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("=== All Tests Done ===");
    }

    // Test 1: Addition
    @Test
    void testAdd() {
        assertEquals(5, calc.add(2, 3), "2 + 3 should be 5");
        assertEquals(0, calc.add(-1, 1), "-1 + 1 should be 0");
    }

    // Test 2: Division
    @Test
    void testDivide() {
        assertEquals(5, calc.divide(10, 2), "10 / 2 should be 5");
        assertEquals(-5, calc.divide(-10, 2), "-10 / 2 should be -5");
    }

    // Test 3: Division by Zero (Exception erwartet)
    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> {
            calc.divide(10, 0);
        }, "Division by zero should throw exception");
    }

    // Test 4: isEven
    @Test
    void testIsEven() {
        assertTrue(calc.isEven(4), "4 should be even");
        assertTrue(calc.isEven(0), "0 should be even");
        assertFalse(calc.isEven(3), "3 should not be even");
        assertFalse(calc.isEven(-1), "-1 should not be even");
    }

    // Test 5: Mehrere Assertions
    @Test
    void testMultipleOperations() {
        assertAll("Calculator operations",
            () -> assertEquals(5, calc.add(2, 3)),
            () -> assertEquals(2, calc.divide(10, 5)),
            () -> assertTrue(calc.isEven(4))
        );
    }
}
```

**Output:**
```
=== Starting Tests ===
Calculator created
Test finished

Calculator created
Test finished

Calculator created
Test finished

Calculator created
Test finished

Calculator created
Test finished

=== All Tests Done ===
```

---

## 🔥 Klausur-Cheatsheet

### Exceptions
- **Checked** = muss behandelt werden (IOException)
- **Unchecked** = optional (NullPointerException)
- **finally** = wird IMMER ausgeführt
- **try-with-resources** = auto-close

### Threads
- `start()` = neuer Thread (parallel)
- `run()` = normaler Aufruf (kein Thread)
- `synchronized` = thread-safe
- `wait()` / `notify()` = Kommunikation

### Testing
- **Black-Box** = ohne Code (nur Spec)
- **White-Box** = mit Code (alle Pfade)
- `@Test` = Testmethode
- `@BeforeEach` = vor jedem Test
- `assertEquals()` = Wert prüfen
- `assertThrows()` = Exception prüfen
- **Statement Coverage** = alle Zeilen
- **Branch Coverage** = alle if/else
- **Path Coverage** = alle Pfade

---

## ✅ Klausur-Checkliste

**Exceptions:**
- [ ] Checked vs Unchecked kennen?
- [ ] try-catch-finally Reihenfolge?
- [ ] finally wird immer ausgeführt?
- [ ] try-with-resources für AutoCloseable?

**Threads:**
- [ ] start() vs run() Unterschied?
- [ ] Thread mit Lambda erstellen?
- [ ] synchronized für Thread-Sicherheit?
- [ ] wait() nur in synchronized?

**Testing:**
- [ ] Black-Box vs White-Box?
- [ ] @Test für Testmethode?
- [ ] @BeforeEach vor jedem Test?
- [ ] assertEquals() für Vergleich?
- [ ] assertThrows() für Exceptions?
- [ ] Statement/Branch/Path Coverage?

**Das war's! Viel Erfolg in der Klausur! 💪🔥**
