═══════════════════════════════════════════════════════════════
  HIGHER ORDER FUNCTIONS - KLAUSUR-WICHTIG!
═══════════════════════════════════════════════════════════════

🔥 WARUM WICHTIG?
In der letzten Klausur: Teil der Streams-Aufgabe (25 Punkte)!
doForEach(Function<String, String> action) kam vor!


═══════════════════════════════════════════════════════════════
  1. WAS SIND HIGHER ORDER FUNCTIONS?
═══════════════════════════════════════════════════════════════

DEFINITION:
Funktionen die andere Funktionen als Parameter nehmen ODER zurückgeben.

BEISPIEL:
```java
public void doForEach(Function<String, String> action) {
    for(String word : words) {
        String result = action.apply(word);
        // ...
    }
}
```
↑ Diese Methode nimmt eine FUNKTION als Parameter!


WARUM NÜTZLICH?
✓ Flexibler Code
✓ Wiederverwendbar
✓ Weniger Code-Duplikation

OHNE HOF (schlecht):
```java
public void toUpperCase() { ... }
public void toLowerCase() { ... }
public void addExclamation() { ... }
// Für jede Operation eine neue Methode!
```

MIT HOF (gut):
```java
doForEach(String::toUpperCase);
doForEach(String::toLowerCase);
doForEach(s -> s + "!");
// Eine Methode, viele Operationen!
```


═══════════════════════════════════════════════════════════════
  2. FUNCTIONAL INTERFACES - DIE 4 WICHTIGSTEN
═══════════════════════════════════════════════════════════════

1. FUNCTION<T, R>
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Nimmt T, gibt R zurück

SIGNATUR:
```java
@FunctionalInterface
interface Function<T, R> {
    R apply(T t);
}
```

VERWENDUNG:
```java
Function<String, Integer> length = String::length;
int len = length.apply("Hello");  // 5

Function<String, String> upper = String::toUpperCase;
String result = upper.apply("hello");  // "HELLO"
```

IN KLAUSUR:
```java
public void doForEach(Function<String, String> action) {
    for(int i = 0; i < words.size(); i++) {
        String result = action.apply(words.get(i));  // ← apply()!
        words.set(i, result);
    }
}
```


2. CONSUMER<T>
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Nimmt T, gibt NICHTS zurück (void)

SIGNATUR:
```java
@FunctionalInterface
interface Consumer<T> {
    void accept(T t);
}
```

VERWENDUNG:
```java
Consumer<String> print = System.out::println;
print.accept("Hello");  // Gibt "Hello" aus

Consumer<String> printUpper = s -> System.out.println(s.toUpperCase());
printUpper.accept("hello");  // HELLO
```

IN KLAUSUR:
```java
public void forEach(Consumer<String> action) {
    for(String movie : movies) {
        action.accept(movie);  // ← accept()!
    }
}
```


3. PREDICATE<T>
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Nimmt T, gibt boolean zurück

SIGNATUR:
```java
@FunctionalInterface
interface Predicate<T> {
    boolean test(T t);
}
```

VERWENDUNG:
```java
Predicate<String> isEmpty = String::isEmpty;
boolean empty = isEmpty.test("");  // true

Predicate<String> isLong = s -> s.length() > 7;
boolean long = isLong.test("Inception");  // true
```

IN KLAUSUR (kam vor!):
```java
public void doForEach(Predicate<String> pred, Consumer<String> consumer) {
    for(String movie : movies) {
        if(pred.test(movie)) {  // ← test()!
            consumer.accept(movie);
        }
    }
}
```


4. SUPPLIER<T>
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Nimmt NICHTS, gibt T zurück

SIGNATUR:
```java
@FunctionalInterface
interface Supplier<T> {
    T get();
}
```

VERWENDUNG:
```java
Supplier<Double> random = Math::random;
double r = random.get();  // 0.123...

Supplier<String> greeting = () -> "Hello";
String greet = greeting.get();  // "Hello"
```


═══════════════════════════════════════════════════════════════
  3. LAMBDA-AUSDRÜCKE
═══════════════════════════════════════════════════════════════

SYNTAX:
```java
(parameter) -> ausdruck
(parameter) -> { statements; return result; }
```

BEISPIELE:

OHNE PARAMETER:
```java
() -> 5
() -> { return 5; }
```

EIN PARAMETER (Klammern optional):
```java
s -> s.toUpperCase()
s -> s.length()
(s) -> s + "!"
```

MEHRERE PARAMETER:
```java
(a, b) -> a + b
(x, y) -> { return x * y; }
```

MEHRZEILIG:
```java
s -> {
    String upper = s.toUpperCase();
    return upper + "!";
}
```


TYPISCHE LAMBDA FÜR FUNCTION<String, String>:
```java
s -> s.toUpperCase()
s -> s.toLowerCase()
s -> s + "!"
s -> s.substring(0, 1)
s -> String.valueOf(s.length())
```


═══════════════════════════════════════════════════════════════
  4. METHODENREFERENZEN
═══════════════════════════════════════════════════════════════

SYNTAX:
```
Class::method
object::method
Class::new
```

WANN VERWENDEN?
Wenn Lambda NUR eine Methode aufruft, ohne zusätzlichen Code.

BEISPIELE:

INSTANZ-METHODE:
```java
String::toUpperCase    →  s -> s.toUpperCase()
String::length         →  s -> s.length()
String::isEmpty        →  s -> s.isEmpty()
```

STATISCHE METHODE:
```java
Math::random           →  () -> Math.random()
Integer::parseInt      →  s -> Integer.parseInt(s)
```

KONSTRUKTOR:
```java
ArrayList::new         →  () -> new ArrayList()
String::new            →  () -> new String()
```

AUSGABE:
```java
System.out::println    →  s -> System.out.println(s)
```


WANN LAMBDA STATT METHODENREFERENZ?
Wenn mehr als nur Methodenaufruf:
```java
s -> s.toUpperCase() + "!"        // Kann nicht mit ::
s -> s.length() > 5               // Kann nicht mit ::
s -> System.out.println(">" + s)  // Kann nicht mit ::
```


═══════════════════════════════════════════════════════════════
  5. doForEach() IMPLEMENTIEREN (KLAUSUR!)
═══════════════════════════════════════════════════════════════

TYPISCHE KLAUSUR-AUFGABE:
"Implementiere doForEach(Function<String, String> action) die
 die Funktion auf alle Elemente anwendet und zurückspeichert."

LÖSUNG:
```java
public void doForEach(Function<String, String> action) {
    for(int i = 0; i < words.size(); i++) {
        String result = action.apply(words.get(i));  // 1. apply()
        words.set(i, result);                        // 2. zurückspeichern!
    }
}
```

WICHTIG:
1. action.apply() aufrufen (nicht action()!)
2. Ergebnis zurückspeichern mit set()
3. Index-basierte for-Schleife (wegen set())


VERWENDUNG:
```java
List<String> words = Arrays.asList("hello", "world");
TextProcessor proc = new TextProcessor(words);

proc.doForEach(String::toUpperCase);
// words ist jetzt: ["HELLO", "WORLD"]

proc.doForEach(s -> s + "!");
// words ist jetzt: ["HELLO!", "WORLD!"]

proc.doForEach(s -> String.valueOf(s.length()));
// words ist jetzt: ["6", "6"]
```


═══════════════════════════════════════════════════════════════
  6. forEach() MIT CONSUMER (KLAUSUR!)
═══════════════════════════════════════════════════════════════

UNTERSCHIED ZU doForEach():
- Consumer gibt VOID zurück
- Kein Zurückspeichern!
- Nur "durchgehen und etwas tun"

IMPLEMENTATION:
```java
public void forEach(Consumer<String> action) {
    for(String movie : movies) {
        action.accept(movie);  // NUR accept(), kein return!
    }
}
```

VERWENDUNG:
```java
movies.forEach(movie -> System.out.println(movie));
movies.forEach(System.out::println);  // Kürzer
```


═══════════════════════════════════════════════════════════════
  7. HÄUFIGE KLAUSUR-FEHLER
═══════════════════════════════════════════════════════════════

FEHLER 1: apply() vergessen
```java
String result = action(word);  // ✗ FALSCH
String result = action.apply(word);  // ✓ RICHTIG
```

FEHLER 2: Ergebnis nicht zurückspeichern
```java
public void doForEach(Function<String, String> action) {
    for(String word : words) {
        action.apply(word);  // ✗ Ergebnis verloren!
    }
}
```
→ Muss mit set() zurückspeichern!

FEHLER 3: Lambda-Syntax falsch
```java
s => s.toUpperCase()  // ✗ FALSCH (=> statt ->)
s -> s.toUpperCase()  // ✓ RICHTIG
```

FEHLER 4: Methodenreferenz falsch
```java
String:toUpperCase   // ✗ FALSCH (nur ein :)
String::toUpperCase  // ✓ RICHTIG (zwei ::)
```

FEHLER 5: test() vergessen bei Predicate
```java
if(pred(movie)) { ... }       // ✗ FALSCH
if(pred.test(movie)) { ... }  // ✓ RICHTIG
```

FEHLER 6: accept() vergessen bei Consumer
```java
action(movie);        // ✗ FALSCH
action.accept(movie); // ✓ RICHTIG
```


═══════════════════════════════════════════════════════════════
  8. CHECKLISTE FÜR KLAUSUR
═══════════════════════════════════════════════════════════════

□ Function<T,R>: action.apply(t) verwenden?
□ Consumer<T>: action.accept(t) verwenden?
□ Predicate<T>: pred.test(t) verwenden?
□ Supplier<T>: supplier.get() verwenden?
□ doForEach(): Ergebnis zurückspeichern (set())?
□ forEach(): Kein return bei Consumer?
□ Lambda: (param) -> expression ?
□ Methodenreferenz: Class::method ?


═══════════════════════════════════════════════════════════════
  9. ZUSAMMENFASSUNG
═══════════════════════════════════════════════════════════════

FUNCTIONAL INTERFACE ÜBERSICHT:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Interface          Methode        Parameter  Return   Beispiel
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Function<T,R>      apply(T)       T          R        String::length
Consumer<T>        accept(T)      T          void     System.out::println
Predicate<T>       test(T)        T          boolean  String::isEmpty
Supplier<T>        get()          -          T        Math::random
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

LAMBDA VS. METHODENREFERENZ:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
Lambda                          Methodenreferenz
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
s -> s.toUpperCase()            String::toUpperCase
s -> s.length()                 String::length
s -> System.out.println(s)      System.out::println
() -> Math.random()             Math::random
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

→ Higher Order Functions sind SEHR klausurrelevant!
→ doForEach() Implementation MUSS sitzen!
→ apply(), accept(), test() nicht vergessen!
