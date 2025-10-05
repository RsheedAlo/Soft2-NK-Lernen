═══════════════════════════════════════════════════════════════
  EXCEPTIONS, THREADS & TESTING - THEORIE-TEIL
═══════════════════════════════════════════════════════════════

Diese Themen kommen im THEORIE-TEIL der Klausur vor!
Meist Multiple Choice oder kurze Fragen.


🔥 TEIL 1: EXCEPTIONS 🔥
═══════════════════════════════════════════════════════════════

1. CHECKED VS. UNCHECKED EXCEPTIONS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

CHECKED EXCEPTIONS (Compiler zwingt zum Behandeln):
- Erben von Exception (aber NICHT RuntimeException)
- MUSS behandelt werden (try-catch ODER throws)
- Beispiele: IOException, SQLException, FileNotFoundException

```java
public void readFile(String path) throws IOException {  // ← throws!
    FileReader fr = new FileReader(path);  // IOException möglich
}

// Oder mit try-catch:
public void readFile(String path) {
    try {
        FileReader fr = new FileReader(path);
    } catch(IOException e) {
        e.printStackTrace();
    }
}
```

UNCHECKED EXCEPTIONS (Optional behandeln):
- Erben von RuntimeException
- Müssen NICHT behandelt werden
- Beispiele: NullPointerException, ArrayIndexOutOfBoundsException,
             IllegalArgumentException

```java
public void divide(int a, int b) {
    int result = a / b;  // ArithmeticException möglich, kein try-catch nötig!
}
```


EXCEPTION-HIERARCHIE:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```
Throwable
├── Error (schwerwiegend, nicht behandeln!)
│   └── OutOfMemoryError
└── Exception
    ├── IOException (CHECKED)
    ├── SQLException (CHECKED)
    └── RuntimeException (UNCHECKED)
        ├── NullPointerException
        ├── ArrayIndexOutOfBoundsException
        └── IllegalArgumentException
```


2. TRY-CATCH-FINALLY
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

STRUKTUR:
```java
try {
    // Code der Exception werfen kann
} catch(IOException e) {
    // Behandlung für IOException
} catch(SQLException e) {
    // Behandlung für SQLException
} finally {
    // Wird IMMER ausgeführt (auch bei return!)
}
```

REIHENFOLGE:
1. try-Block wird ausgeführt
2. Bei Exception: Passender catch-Block
3. finally-Block wird IMMER ausgeführt

WICHTIG:
- finally wird IMMER ausgeführt (auch bei return in try!)
- Mehrere catch-Blöcke möglich
- Spezifischere Exception ZUERST (IOException vor Exception)


3. TRY-WITH-RESOURCES
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Automatisches Schließen von Ressourcen:

ALT (ohne try-with-resources):
```java
FileReader fr = null;
try {
    fr = new FileReader("file.txt");
    // ...
} catch(IOException e) {
    e.printStackTrace();
} finally {
    if(fr != null) {
        try {
            fr.close();  // Vergisst man oft!
        } catch(IOException e) { }
    }
}
```

NEU (try-with-resources):
```java
try(FileReader fr = new FileReader("file.txt")) {
    // ...
}  // ← Automatisch geschlossen!
catch(IOException e) {
    e.printStackTrace();
}
```

VORTEILE:
✓ Kürzer
✓ Ressource wird automatisch geschlossen
✓ Kein finally nötig


4. EIGENE EXCEPTIONS ERSTELLEN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

```java
// Checked Exception:
class MyException extends Exception {
    public MyException(String message) {
        super(message);
    }
}

// Unchecked Exception:
class MyRuntimeException extends RuntimeException {
    public MyRuntimeException(String message) {
        super(message);
    }
}

// Verwendung:
public void checkAge(int age) throws MyException {
    if(age < 18) {
        throw new MyException("Too young!");
    }
}
```


🔥 TEIL 2: THREADS 🔥
═══════════════════════════════════════════════════════════════

1. THREAD MIT RUNNABLE ERSTELLEN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

METHODE 1: Runnable Interface implementieren
```java
class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread running!");
    }
}

// Verwendung:
Thread thread = new Thread(new MyRunnable());
thread.start();
```

METHODE 2: Anonyme Klasse
```java
Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        System.out.println("Thread running!");
    }
});
thread.start();
```

METHODE 3: Lambda (MODERN!)
```java
Thread thread = new Thread(() -> System.out.println("Thread running!"));
thread.start();

// Oder mehrzeilig:
Thread thread = new Thread(() -> {
    System.out.println("Thread running!");
    System.out.println("More code...");
});
thread.start();
```


2. START() VS. RUN()
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

WICHTIGER UNTERSCHIED:

start():
- Startet NEUEN Thread
- run() wird in NEUEM Thread ausgeführt
- Asynchron (läuft parallel)

```java
thread.start();  // ✓ RICHTIG: Neuer Thread!
```

run():
- Führt Code im AKTUELLEN Thread aus
- KEIN neuer Thread!
- Synchron (wie normaler Methodenaufruf)

```java
thread.run();  // ✗ FALSCH: Kein neuer Thread!
```

BEISPIEL:
```java
Thread t = new Thread(() -> {
    System.out.println("Thread: " + Thread.currentThread().getName());
});

t.run();   // Thread: main (im Haupt-Thread!)
t.start(); // Thread: Thread-0 (im neuen Thread!)
```


3. SYNCHRONIZED, WAIT(), NOTIFY()
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

PROBLEM: Race Conditions
```java
class Counter {
    private int count = 0;

    public void increment() {
        count++;  // Nicht thread-safe!
    }
}
// Zwei Threads können gleichzeitig increment() aufrufen → Datenverlust!
```

LÖSUNG: synchronized
```java
class Counter {
    private int count = 0;

    public synchronized void increment() {
        count++;  // Jetzt thread-safe!
    }
}
```

SYNCHRONIZED BLOCK:
```java
public void increment() {
    synchronized(this) {
        count++;
    }
}
```

WAIT() UND NOTIFY():
```java
class Producer {
    private List<Integer> queue = new ArrayList<>();

    public synchronized void produce(int value) {
        queue.add(value);
        notify();  // ← Wecke wartenden Thread!
    }

    public synchronized int consume() throws InterruptedException {
        while(queue.isEmpty()) {
            wait();  // ← Warte bis etwas da ist
        }
        return queue.remove(0);
    }
}
```


4. THREAD-STATES
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

```
NEW → RUNNABLE → RUNNING → TERMINATED
         ↓           ↓
      BLOCKED    WAITING
```

- NEW: Thread erstellt, noch nicht gestartet
- RUNNABLE: Nach start(), wartet auf CPU
- RUNNING: Wird gerade ausgeführt
- WAITING: Wartet auf notify() oder join()
- BLOCKED: Wartet auf synchronized Lock
- TERMINATED: Fertig


🔥 TEIL 3: JUNIT TESTING 🔥
═══════════════════════════════════════════════════════════════

1. BLACK-BOX VS. WHITE-BOX TESTING
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BLACK-BOX:
- Nur SPEZIFIKATION bekannt, KEIN Code
- Basiert auf Input/Output
- Äquivalenzklassen und Grenzwerte
- Beispiel: "Methode soll positive Zahlen verdoppeln"
  → Teste: 0, 1, 100, -1, Integer.MAX_VALUE

WHITE-BOX:
- Code ist BEKANNT
- Basiert auf Code-Struktur
- Code Coverage (Statement, Branch, Path)
- Beispiel: Code hat if-else → Teste beide Zweige


2. JUNIT ANNOTATIONS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

```java
class CalculatorTest {
    private Calculator calc;

    @BeforeAll  // Einmal VOR allen Tests (static!)
    static void setupAll() {
        System.out.println("Starting tests...");
    }

    @BeforeEach  // VOR jedem Test
    void setUp() {
        calc = new Calculator();
    }

    @Test  // Eine Testmethode
    void testAdd() {
        assertEquals(5, calc.add(2, 3));
    }

    @AfterEach  // NACH jedem Test
    void tearDown() {
        calc = null;
    }

    @AfterAll  // Einmal NACH allen Tests (static!)
    static void tearDownAll() {
        System.out.println("Tests finished.");
    }
}
```

REIHENFOLGE:
1. @BeforeAll (einmal)
2. @BeforeEach
3. @Test
4. @AfterEach
5. (2-4 wiederholen für jeden Test)
6. @AfterAll (einmal)


3. ASSERTIONS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

```java
// Gleichheit:
assertEquals(expected, actual);
assertEquals(5, calc.add(2, 3));

// Boolean:
assertTrue(condition);
assertFalse(condition);
assertTrue(5 > 3);

// Null:
assertNull(object);
assertNotNull(object);

// Exception:
assertThrows(ArithmeticException.class, () -> {
    calc.divide(5, 0);
});

// Arrays:
assertArrayEquals(expectedArray, actualArray);

// Mit Message:
assertEquals(5, calc.add(2, 3), "Addition failed!");
```


4. TEST-COVERAGE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

CODE-BEISPIEL:
```java
public int max(int a, int b) {
    if(a > b) {
        return a;  // Zeile 3
    } else {
        return b;  // Zeile 5
    }
}
```

STATEMENT COVERAGE (Zeilen):
- Test: max(5, 3) → Zeile 3 ausgeführt
- Test: max(2, 7) → Zeile 5 ausgeführt
- 100% Statement Coverage ✓

BRANCH COVERAGE (Zweige):
- Test: max(5, 3) → if-Zweig
- Test: max(2, 7) → else-Zweig
- 100% Branch Coverage ✓

PATH COVERAGE (Pfade):
- Bei diesem Beispiel gleich wie Branch Coverage
- Bei komplexerem Code: Alle Kombinationen!


BEISPIEL MIT MEHREREN IFS:
```java
public void check(int a, int b) {
    if(a > 0) {  // Branch A
        // ...
    }
    if(b > 0) {  // Branch B
        // ...
    }
}
```

BRANCH COVERAGE: 2 Branches → 2 Tests
PATH COVERAGE: 4 Pfade (A ja/nein × B ja/nein) → 4 Tests


5. VOLLSTÄNDIGE TESTKLASSE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

```java
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int divide(int a, int b) {
        if(b == 0) {
            throw new ArithmeticException("Division by zero");
        }
        return a / b;
    }
}

class CalculatorTest {
    private Calculator calc;

    @BeforeEach
    void setUp() {
        calc = new Calculator();
    }

    @Test
    void testAdd() {
        assertEquals(5, calc.add(2, 3));
        assertEquals(0, calc.add(-5, 5));
        assertEquals(-2, calc.add(-1, -1));
    }

    @Test
    void testAddNegative() {
        assertEquals(-5, calc.add(-2, -3));
    }

    @Test
    void testDivide() {
        assertEquals(2, calc.divide(10, 5));
        assertEquals(0, calc.divide(0, 5));
    }

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class, () -> {
            calc.divide(10, 0);
        });
    }

    @AfterEach
    void tearDown() {
        calc = null;
    }
}
```


═══════════════════════════════════════════════════════════════
  CHECKLISTE FÜR KLAUSUR
═══════════════════════════════════════════════════════════════

EXCEPTIONS:
□ Checked vs Unchecked unterscheiden?
□ try-catch-finally Reihenfolge?
□ try-with-resources Syntax?
□ Exception-Hierarchie (Error, Exception, RuntimeException)?

THREADS:
□ Lambda für Runnable: () -> ...?
□ start() vs run() Unterschied?
□ synchronized für Thread-Safety?
□ wait() und notify() Verwendung?
□ Thread-States kennen?

TESTING:
□ Black-Box vs White-Box Unterschied?
□ @Test, @BeforeEach, @AfterEach?
□ assertEquals, assertTrue, assertThrows?
□ Statement vs Branch vs Path Coverage?


═══════════════════════════════════════════════════════════════
  HÄUFIGE KLAUSUR-FRAGEN
═══════════════════════════════════════════════════════════════

EXCEPTIONS:
Q: Ist IOException checked oder unchecked?
A: CHECKED (extends Exception)

Q: Muss finally immer ausgeführt werden?
A: JA (auch bei return im try-Block!)

THREADS:
Q: Was ist der Unterschied zwischen start() und run()?
A: start() → neuer Thread, run() → aktueller Thread

Q: Wozu synchronized?
A: Verhindert Race Conditions (Thread-Safety)

TESTING:
Q: Wann @BeforeAll statt @BeforeEach?
A: @BeforeAll = einmal vor ALLEN Tests, @BeforeEach = vor JEDEM Test

Q: Was ist Branch Coverage?
A: Alle if/else Zweige getestet


═══════════════════════════════════════════════════════════════
  ZUSAMMENFASSUNG
═══════════════════════════════════════════════════════════════

EXCEPTIONS:
→ Checked: MUSS behandelt werden (IOException)
→ Unchecked: Optional (RuntimeException)
→ try-catch-finally: finally IMMER
→ try-with-resources: Automatisch close()

THREADS:
→ new Thread(() -> ...).start()
→ start() = neuer Thread, run() = aktueller Thread
→ synchronized für Thread-Safety
→ wait()/notify() für Kommunikation

TESTING:
→ Black-Box = nur Spezifikation, White-Box = Code bekannt
→ @Test, @BeforeEach, @AfterEach
→ assertEquals, assertTrue, assertThrows
→ Coverage: Statement (Zeilen), Branch (Zweige), Path (Pfade)

→ Meist im Theorie-Teil (Multiple Choice)!
→ ~10-20 Punkte möglich!
