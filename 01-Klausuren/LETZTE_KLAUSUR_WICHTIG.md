═══════════════════════════════════════════════════════════════
  ⚠️  LETZTE KLAUSUR (NEUESTE) - SEHR WICHTIG! ⚠️
═══════════════════════════════════════════════════════════════

PUNKTEVERTEILUNG: 100 Punkte gesamt
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

10pt → JA/NEIN Fragen
20pt → Multiple Choice (Ankreuzen)
20pt → Collections + Vererbung (printEntry Override)
25pt → MVC mit Listeners & Fire-Methode
25pt → Streams + Higher-Order Functions (HOF)


═══════════════════════════════════════════════════════════════
  TEIL 1: JA/NEIN FRAGEN (10 Punkte)
═══════════════════════════════════════════════════════════════
Ähnlich wie Altklausuren, z.B.:
- Kann eine Klasse mehrere Interfaces implementieren? → JA
- Kann man final Methoden überschreiben? → NEIN
- Ist protected sichtbar in Subklassen? → JA

TIPP: Altklausuren SS2020 Theoretisch durchgehen!


═══════════════════════════════════════════════════════════════
  TEIL 2: MULTIPLE CHOICE (20 Punkte)
═══════════════════════════════════════════════════════════════
Ähnlich wie Theorie-Teil SS2020:
- Wildcards: <? extends T> vs <? super T>
- Functional Interfaces
- JUnit Assertions
- UML Diagramme

TIPP: Theorie wiederholen!


═══════════════════════════════════════════════════════════════
  TEIL 3: COLLECTIONS + VERERBUNG (20 Punkte) ⚠️
═══════════════════════════════════════════════════════════════

AUFGABE: 3 Klassen mit Vererbung, printEntry() überall

BEISPIEL-STRUKTUR:
```java
class Entry {
    public void printEntry() {
        System.out.println("Base Entry");
    }
}

class PersonEntry extends Entry {
    private String name;

    @Override
    public void printEntry() {
        System.out.println("Person: " + name);
    }
}

class CompanyEntry extends Entry {
    private String company;

    @Override
    public void printEntry() {
        System.out.println("Company: " + company);
    }
}

// MAIN:
public static void main(String[] args) {
    List<Entry> entries = new ArrayList<>();
    entries.add(new PersonEntry("Max"));
    entries.add(new CompanyEntry("Google"));

    for(Entry e : entries) {
        e.printEntry();  // Polymorphismus!
    }
}
```

WICHTIG:
✓ @Override nicht vergessen!
✓ Polymorphismus verstehen
✓ List<Entry> für alle Subklassen


═══════════════════════════════════════════════════════════════
  TEIL 4: MVC MIT LISTENERS (25 Punkte) ⚠️⚠️⚠️
═══════════════════════════════════════════════════════════════

AUFGABE: "fire"-Methode schreiben, Listener erstellen

WICHTIGSTE KONZEPTE:

1. LISTENER INTERFACE:
```java
public interface MyListener {
    void onDataChanged(MyEvent event);
}
```

2. MODEL MIT FIRE-METHODE:
```java
public class Model {
    private List<MyListener> listeners = new ArrayList<>();

    public void addListener(MyListener listener) {
        listeners.add(listener);
    }

    // FIRE-METHODE = Alle Listener benachrichtigen!
    private void fireDataChanged() {
        MyEvent event = new MyEvent(this);
        for(MyListener listener : listeners) {
            listener.onDataChanged(event);
        }
    }

    public void setData(String data) {
        this.data = data;
        fireDataChanged();  // ← WICHTIG: Bei jeder Änderung!
    }
}
```

3. VIEW (LISTENER IMPLEMENTIEREN):
```java
public class View implements MyListener {
    private Model model;

    public View(Model model) {
        this.model = model;
        model.addListener(this);  // ← Registrieren!
    }

    @Override
    public void onDataChanged(MyEvent event) {
        System.out.println("Data changed!");
        // View aktualisieren
    }
}
```

CHECKLISTE FÜR MVC:
□ Listener-Interface erstellt?
□ Model hat List<Listener>?
□ addListener() Methode?
□ fire-Methode ruft alle Listener auf?
□ View implementiert Listener?
□ View registriert sich im Konstruktor?


═══════════════════════════════════════════════════════════════
  TEIL 5: STREAMS + HOF (25 Punkte) ⚠️⚠️⚠️
═══════════════════════════════════════════════════════════════

AUFGABE 1: Higher-Order Function (doForEach)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Abstract Class mit doForEach(Function<String, String> action):

```java
public abstract class TextProcessor {
    protected List<String> words;

    // Higher-Order Function: Nimmt Funktion als Parameter!
    public void doForEach(Function<String, String> action) {
        for(int i = 0; i < words.size(); i++) {
            String result = action.apply(words.get(i));
            words.set(i, result);
        }
    }
}

// VERWENDUNG:
public class MyProcessor extends TextProcessor {
    public void processWords() {
        // Lambda: String → String
        doForEach(s -> s.toUpperCase());

        // Oder Methodenreferenz:
        doForEach(String::toUpperCase);
    }
}

// MAIN:
MyProcessor proc = new MyProcessor();
proc.doForEach(String::toUpperCase);  // Alle Wörter → GROSS
```

WICHTIG:
- Function<String, String>: nimmt String, gibt String zurück
- action.apply(wort) aufrufen!
- Lambda: s -> s.toUpperCase()


AUFGABE 2: Streams - Wörter zählen & finden
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

BEISPIEL: Sätze → Wörter finden, zählen

```java
List<String> sentences = Arrays.asList(
    "Hello World!",
    "Java Streams sind toll.",
    "Hello Java!"
);

// 1. ALLE WÖRTER FINDEN (lowercase, ohne Satzzeichen):
List<String> words = sentences.stream()
    .flatMap(s -> Arrays.stream(s.split("\\s+")))  // Satz → Wörter
    .map(w -> w.replaceAll("[,.!?-]", ""))         // Satzzeichen weg
    .map(String::toLowerCase)                       // Kleinbuchstaben
    .collect(Collectors.toList());

// 2. ERSTE 10 WÖRTER MIT INDEX:
List<String> first10 = sentences.stream()
    .flatMap(s -> Arrays.stream(s.split("\\s+")))
    .map(w -> w.replaceAll("[,.!?-]", ""))
    .limit(10)                                      // Erste 10
    .collect(Collectors.toList());

// Index ausgeben:
for(int i = 0; i < first10.size(); i++) {
    System.out.println(i + ": " + first10.get(i));
}

// 3. SATZ MIT 2 BESTIMMTEN WÖRTERN FINDEN:
boolean hasBothWords = sentences.stream()
    .anyMatch(s -> s.toLowerCase().contains("hello")
                && s.toLowerCase().contains("java"));

// 4. WÖRTER ZÄHLEN:
long count = sentences.stream()
    .flatMap(s -> Arrays.stream(s.split("\\s+")))
    .map(String::toLowerCase)
    .filter(w -> w.equals("hello"))
    .count();

System.out.println("'hello' kommt " + count + " mal vor");
```

WICHTIGE STREAM-OPERATIONEN:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

flatMap()       → Liste von Listen → Flache Liste
                  ["Hello World", "Java"] → ["Hello", "World", "Java"]

split("\\s+")   → String → Array (bei Leerzeichen trennen)

replaceAll()    → Satzzeichen entfernen: "[,.!?-]"

toLowerCase()   → ALLES KLEIN

limit(10)       → Nur erste 10 Elemente

anyMatch()      → true wenn MINDESTENS 1 passt

allMatch()      → true wenn ALLE passen

count()         → Anzahl der Elemente

collect()       → Stream → Collection (List, Set, Map)


HÄUFIGE FEHLER:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
✗ collect() vergessen am Ende
✗ flatMap() vs map() verwechseln
✗ split() ohne \\s+ (Regex!)
✗ anyMatch() ohne ()


KEY-VALUE AUSGABE (Index + Wort):
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
```java
List<String> words = /* ... */;
IntStream.range(0, words.size())
    .forEach(i -> System.out.println(i + ": " + words.get(i)));

// Oder klassisch:
for(int i = 0; i < words.size(); i++) {
    System.out.println(i + ": " + words.get(i));
}
```


═══════════════════════════════════════════════════════════════
  LERNSTRATEGIE FÜR MORGEN
═══════════════════════════════════════════════════════════════

PRIORITÄT 1 (50 Punkte!):
→ MVC mit fire-Methode PERFEKT können! (25pt)
→ Streams + flatMap + limit (25pt)

PRIORITÄT 2 (20 Punkte):
→ Collections mit Override üben

PRIORITÄT 3 (30 Punkte):
→ Theorie wiederholen (MC, Ja/Nein)


ÜBUNGSPLAN:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
1. MVC fire-Methode selbst schreiben (1h)
2. Streams mit flatMap + split üben (1h)
3. Higher-Order Functions (doForEach) (30min)
4. Collections + Override (30min)
5. Theorie durchgehen (1h)


MUST-KNOW FÜR KLAUSUR:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
✓ fire-Methode = for-Schleife über alle Listeners
✓ flatMap() für String[] → Stream
✓ split("\\s+") für Wörter trennen
✓ limit(10) für erste 10
✓ anyMatch() für Boolean zurückgeben
✓ Function<String, String> = apply() aufrufen!


VIEL ERFOLG MORGEN! 🍀🔥
Du schaffst das! Konzentrier dich auf MVC und Streams!
