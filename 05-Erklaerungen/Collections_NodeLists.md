# JAVA COLLECTIONS & NODE-BASIERTE LISTEN - Klausur-Guide

## 🎯 Das Wichtigste in 3 Sätzen

1. **List** = Duplikate erlaubt, Reihenfolge erhalten, Index-Zugriff möglich
2. **Set** = KEINE Duplikate, keine garantierte Reihenfolge (außer TreeSet/LinkedHashSet)
3. **Node-basierte Liste** = Selbst gebaut mit Node-Objekten und next-Referenzen (kommt in Klausur!)

---

## 🧠 Eselsbrücke für die Klausur

**LIST** = **PLAYLIST** (Songs dürfen mehrfach vorkommen, Reihenfolge wichtig)
- ArrayList = CD (schneller Zugriff auf Track 5)
- LinkedList = Kassette (schnell vor/zurück spulen)

**SET** = **GÄSTELISTE** (jeder Name nur 1x, Reihenfolge egal)
- HashSet = ungeordnet
- TreeSet = alphabetisch sortiert
- LinkedHashSet = Reihenfolge wie hinzugefügt

**MAP** = **TELEFONBUCH** (Name → Nummer, jeder Name nur 1x)
- HashMap = ungeordnet
- TreeMap = sortiert nach Key

---

# TEIL 1: COLLECTION HIERARCHIE

## Die Java Collection Hierarchie

```
                    Collection<E>
                         |
        +----------------+----------------+
        |                |                |
     List<E>          Set<E>          Queue<E>
        |                |
        |                +--- SortedSet<E>
        |                         |
        |                    TreeSet<E>
        |
    ArrayList<E>
    LinkedList<E>
    Vector<E>


    Map<E> (NICHT Teil von Collection!)
        |
        +--- SortedMap<E>
                  |
             TreeMap<E>
```

**WICHTIG:** Map ist NICHT Teil der Collection-Hierarchie!

---

## List Interface - Die geordnete Sammlung

### Was ist eine List?

**Definition:** Geordnete Collection die Duplikate erlaubt

**Eigenschaften:**
- ✅ Duplikate erlaubt: `[1, 2, 2, 3]` ist ok
- ✅ Reihenfolge erhalten: Element 0, 1, 2, ...
- ✅ Index-Zugriff: `list.get(5)`
- ✅ null-Werte erlaubt

### List Implementierungen

#### 1. ArrayList<E>

```java
List<String> list = new ArrayList<>();
list.add("A");
list.add("B");
list.add("A");  // Duplikat ok!
```

**Vorteile:**
- Schneller Zugriff: `get(index)` → O(1)
- Gut für viel Lesen

**Nachteile:**
- Langsames Einfügen/Löschen in der Mitte → O(n)
- Speicher verschwendet wenn Array vergrößert wird

**Wann nutzen?** Viel lesen, wenig einfügen/löschen

#### 2. LinkedList<E>

```java
List<String> list = new LinkedList<>();
list.add("A");
list.addFirst("B");  // Am Anfang hinzufügen
list.addLast("C");   // Am Ende hinzufügen
```

**Vorteile:**
- Schnelles Einfügen/Löschen am Anfang/Ende → O(1)
- Kein Speicher verschwendet

**Nachteile:**
- Langsamer Zugriff: `get(index)` → O(n)
- Mehr Speicher pro Element (next-Referenz)

**Wann nutzen?** Viel einfügen/löschen, wenig Index-Zugriff

#### 3. Vector<E> (veraltet!)

```java
List<String> list = new Vector<>();  // NICHT nutzen!
```

**Veraltet!** Thread-safe aber langsam. Nutze stattdessen ArrayList + Collections.synchronizedList()

---

## Set Interface - Die Menge ohne Duplikate

### Was ist ein Set?

**Definition:** Collection ohne Duplikate

**Eigenschaften:**
- ❌ KEINE Duplikate: `add("A")` zweimal → nur 1x gespeichert
- ❌ Keine garantierte Reihenfolge (außer TreeSet/LinkedHashSet)
- ❌ KEIN Index-Zugriff: `set.get(5)` geht nicht!
- ✅ null-Werte erlaubt (außer TreeSet)

### Set Implementierungen

#### 1. HashSet<E>

```java
Set<String> set = new HashSet<>();
set.add("A");
set.add("B");
set.add("A");  // Wird NICHT hinzugefügt!
System.out.println(set.size());  // 2 (nicht 3!)
```

**Eigenschaften:**
- Ungeordnet (Reihenfolge zufällig)
- Schnellste Set-Implementierung: add, remove, contains → O(1)
- Nutzt hashCode() für Speicherung

**Wann nutzen?** Schnelle Membership-Tests, Reihenfolge egal

#### 2. TreeSet<E>

```java
Set<String> set = new TreeSet<>();
set.add("C");
set.add("A");
set.add("B");
System.out.println(set);  // [A, B, C] - sortiert!
```

**Eigenschaften:**
- **Automatisch sortiert** (natural ordering oder Comparator)
- Langsamer: add, remove, contains → O(log n)
- KEINE null-Werte!
- Braucht Comparable oder Comparator

**Wann nutzen?** Sortierte Menge ohne Duplikate

#### 3. LinkedHashSet<E>

```java
Set<String> set = new LinkedHashSet<>();
set.add("C");
set.add("A");
set.add("B");
System.out.println(set);  // [C, A, B] - Einfügereihenfolge!
```

**Eigenschaften:**
- Einfügereihenfolge erhalten
- Etwas langsamer als HashSet
- Nutzt hashCode() + doubly-linked list

**Wann nutzen?** Set ohne Duplikate, Reihenfolge wie eingefügt

---

## Map Interface - Key-Value Paare

### Was ist eine Map?

**Definition:** Speichert Key-Value Paare (wie Wörterbuch)

**Eigenschaften:**
- Keys sind eindeutig (wie Set)
- Values können Duplikate sein
- Kein Index-Zugriff

### Map Implementierungen

#### 1. HashMap<K, V>

```java
Map<String, Integer> map = new HashMap<>();
map.put("Alice", 25);
map.put("Bob", 30);
map.put("Alice", 26);  // Überschreibt alten Wert!
System.out.println(map.get("Alice"));  // 26
```

**Eigenschaften:**
- Ungeordnet
- Schnell: put, get, remove → O(1)
- null als Key und Value erlaubt

#### 2. TreeMap<K, V>

```java
Map<String, Integer> map = new TreeMap<>();
map.put("C", 3);
map.put("A", 1);
map.put("B", 2);
// Keys sortiert: A, B, C
```

**Eigenschaften:**
- Keys automatisch sortiert
- Langsamer: O(log n)
- KEIN null als Key

#### 3. LinkedHashMap<K, V>

```java
Map<String, Integer> map = new LinkedHashMap<>();
map.put("C", 3);
map.put("A", 1);
// Keys in Einfügereihenfolge: C, A
```

**Eigenschaften:**
- Einfügereihenfolge erhalten
- Etwas langsamer als HashMap

---

# TEIL 2: LIST VS SET - Die Unterschiede

## Vergleichstabelle

| Aspekt | List | Set |
|--------|------|-----|
| **Duplikate** | ✅ Erlaubt | ❌ NICHT erlaubt |
| **Reihenfolge** | ✅ Erhalten | ⚠️ Nur bei TreeSet/LinkedHashSet |
| **Index-Zugriff** | ✅ `get(5)` | ❌ Nicht möglich |
| **null-Werte** | ✅ Erlaubt | ⚠️ Nicht bei TreeSet |
| **Verwendung** | Playlist, Schritte | Gästeliste, IDs |

## Wann was verwenden?

### ✅ Verwende LIST wenn:

1. **Duplikate erlaubt** sein sollen
   - Einkaufsliste: "Milch" kann 2x vorkommen

2. **Reihenfolge wichtig** ist
   - Schritte eines Rezepts

3. **Index-Zugriff** nötig ist
   - "Gib mir das 5. Element"

**Beispiele:** Playlist, Todo-Liste, Rezept-Schritte

### ✅ Verwende SET wenn:

1. **Keine Duplikate** erlaubt
   - Gästeliste: jeder Name nur 1x

2. **Membership-Test** wichtig
   - "Ist User X in der Gruppe?"

3. **Reihenfolge egal** oder sortiert sein soll
   - Menge aller IDs

**Beispiele:** Gästeliste, User-IDs, Wörterbuch (eindeutige Wörter)

---

## Beispiel: List → Set Konvertierung

```java
List<String> list = Arrays.asList("A", "B", "A", "C", "B");
System.out.println(list);  // [A, B, A, C, B] - Duplikate!

// In HashSet konvertieren (Duplikate entfernen)
Set<String> set = new HashSet<>(list);
System.out.println(set);  // [A, B, C] - Duplikate weg!

// In TreeSet konvertieren (Duplikate weg + sortiert)
Set<String> treeSet = new TreeSet<>(list);
System.out.println(treeSet);  // [A, B, C] - sortiert!
```

---

# TEIL 3: EIGENE NODE-BASIERTE LISTE (KLAUSUR!)

## Warum selbst implementieren?

**In der Klausur kommt oft:**
"Implementiere eine verkettete Liste mit Node-Objekten"

**Beispiel:** November 2010 Klausur

---

## Die Node-Klasse

### Node ohne Generics (Object)

```java
class Node {
    Object obj;    // Das gespeicherte Objekt
    Node next;     // Referenz auf nächsten Node

    public Node(Object obj) {
        this.obj = obj;
        this.next = null;  // Standardmäßig kein Nachfolger
    }
}
```

**Visualisierung:**
```
Node 1          Node 2          Node 3
┌─────┬────┐   ┌─────┬────┐   ┌─────┬────┐
│ "A" │ ●──┼──→│ "B" │ ●──┼──→│ "C" │null│
└─────┴────┘   └─────┴────┘   └─────┴────┘
 obj   next     obj   next     obj   next
```

### Node mit Generics (Type-safe)

```java
class Node<E> {
    E obj;         // Generischer Typ statt Object
    Node<E> next;  // Next ist auch generisch

    public Node(E obj) {
        this.obj = obj;
        this.next = null;
    }
}
```

**Vorteil:** Type-safety zur Compile-Zeit!

```java
Node<String> node = new Node<>("A");
String s = node.obj;  // Kein Cast nötig!
```

---

## Die List-Klasse (verkettete Liste)

### Komplette Implementierung

```java
class MyList {
    private Node head;  // Erstes Element
    private Node tail;  // Letztes Element

    // Konstruktor: Leere Liste
    public MyList() {
        this.head = null;
        this.tail = null;
    }

    // METHODE 1: add(obj) - Am Ende hinzufügen
    public void add(Object obj) {
        Node newNode = new Node(obj);

        // Fall 1: Liste ist leer
        if (head == null) {
            head = newNode;
            tail = newNode;
        }
        // Fall 2: Liste hat Elemente
        else {
            tail.next = newNode;  // Alter tail zeigt auf neuen Node
            tail = newNode;       // Neuer Node wird tail
        }
    }

    // METHODE 2: remove(obj) - Erstes Vorkommen löschen
    public boolean remove(Object obj) {
        // Fall 1: Liste ist leer
        if (head == null) {
            return false;
        }

        // Fall 2: Erstes Element soll gelöscht werden
        if (head.obj.equals(obj)) {
            head = head.next;           // Head überspringen
            if (head == null) {         // War das einzige Element?
                tail = null;            // Tail auch auf null
            }
            return true;
        }

        // Fall 3: Element in der Mitte/Ende
        Node current = head;
        while (current.next != null) {
            if (current.next.obj.equals(obj)) {
                current.next = current.next.next;  // Node überspringen

                // War es das letzte Element?
                if (current.next == null) {
                    tail = current;
                }
                return true;
            }
            current = current.next;
        }

        // Fall 4: Nicht gefunden
        return false;
    }

    // METHODE 3: contains(obj) - Membership Test
    public boolean contains(Object obj) {
        Node current = head;
        while (current != null) {
            if (current.obj.equals(obj)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    // METHODE 4: size() - Anzahl Elemente
    public int size() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    // METHODE 5: isEmpty() - Liste leer?
    public boolean isEmpty() {
        return head == null;
    }
}
```

---

## Schritt-für-Schritt: add() Methode

### Visualisierung add("A")

**Vorher (leere Liste):**
```
head → null
tail → null
```

**Nachher:**
```
head → ┌─────┬────┐
       │ "A" │null│ ← tail
       └─────┴────┘
```

### Visualisierung add("B")

**Vorher:**
```
head → ┌─────┬────┐
       │ "A" │null│ ← tail
       └─────┴────┘
```

**Nachher:**
```
head → ┌─────┬────┐   ┌─────┬────┐
       │ "A" │ ●──┼──→│ "B" │null│ ← tail
       └─────┴────┘   └─────┴────┘
```

**Code-Ablauf:**
```java
public void add(Object obj) {
    Node newNode = new Node("B");  // Schritt 1: Neuer Node

    // Liste ist NICHT leer:
    tail.next = newNode;  // Schritt 2: Alter tail zeigt auf neuen
    tail = newNode;       // Schritt 3: tail wird verschoben
}
```

---

## Schritt-für-Schritt: remove() Methode

### Fall 1: Erstes Element löschen

**Vorher:**
```
head → ┌─────┬────┐   ┌─────┬────┐
       │ "A" │ ●──┼──→│ "B" │null│ ← tail
       └─────┴────┘   └─────┴────┘
```

**remove("A"):**
```java
if (head.obj.equals("A")) {
    head = head.next;  // Head überspringen!
}
```

**Nachher:**
```
       ┌─────┬────┐
head → │ "B" │null│ ← tail
       └─────┴────┘
```

### Fall 2: Element in der Mitte löschen

**Vorher:**
```
head → ┌─────┬────┐   ┌─────┬────┐   ┌─────┬────┐
       │ "A" │ ●──┼──→│ "B" │ ●──┼──→│ "C" │null│ ← tail
       └─────┴────┘   └─────┴────┘   └─────┴────┘
```

**remove("B"):**
```java
// current zeigt auf "A"
if (current.next.obj.equals("B")) {
    current.next = current.next.next;  // Überspringe "B"!
}
```

**Nachher:**
```
head → ┌─────┬────┐   ┌─────┬────┐
       │ "A" │ ●──┼──→│ "C" │null│ ← tail
       └─────┴────┘   └─────┴────┘
```

---

# TEIL 4: ITERATOR PATTERN (KLAUSUR!)

## Was ist ein Iterator?

**Definition:** Objekt zum Durchlaufen einer Collection

**Zwei Methoden:**
- `hasNext()` - Gibt es noch ein Element?
- `next()` - Gib nächstes Element zurück

---

## Eigener Iterator für MyList

### Iterable Interface

```java
interface Iterable {
    Object next();         // Nächstes Element
    boolean hasNext();     // Noch Elemente?
}
```

### Iterator-Implementierung

```java
class MyIterator implements Iterable {
    private Node current;  // Aktueller Node

    // Konstruktor: Starte bei head
    public MyIterator(Node head) {
        this.current = head;
    }

    // Gibt es noch ein Element?
    @Override
    public boolean hasNext() {
        return current != null;
    }

    // Gib nächstes Element zurück
    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        Object obj = current.obj;      // Speichere Objekt
        current = current.next;        // Gehe zum nächsten Node
        return obj;                    // Gib Objekt zurück
    }
}
```

### Iterator in MyList integrieren

```java
class MyList {
    // ... andere Methoden ...

    // Erstelle Iterator
    public Iterable iterator() {
        return new MyIterator(head);
    }
}
```

### Verwendung

```java
MyList list = new MyList();
list.add("A");
list.add("B");
list.add("C");

// Iterator erstellen
Iterable it = list.iterator();

// Durchlaufen
while (it.hasNext()) {
    Object obj = it.next();
    System.out.println(obj);
}
// Output: A, B, C
```

---

## Java Iterable Interface (echtes)

```java
class MyList implements java.lang.Iterable<Object> {
    // ... andere Methoden ...

    @Override
    public Iterator<Object> iterator() {
        return new Iterator<Object>() {
            private Node current = head;

            public boolean hasNext() {
                return current != null;
            }

            public Object next() {
                Object obj = current.obj;
                current = current.next;
                return obj;
            }
        };
    }
}
```

**Vorteil:** Funktioniert mit for-each Loop!

```java
MyList list = new MyList();
list.add("A");
list.add("B");

for (Object obj : list) {  // For-each funktioniert!
    System.out.println(obj);
}
```

---

# TEIL 5: GENERISCHE LISTE MIT <E>

## Warum Generics?

**Ohne Generics (Object):**
```java
MyList list = new MyList();
list.add("A");
String s = (String) list.get(0);  // Cast nötig! 😞
list.add(123);  // Kompiliert, aber Fehler zur Laufzeit!
```

**Mit Generics (<E>):**
```java
MyList<String> list = new MyList<>();
list.add("A");
String s = list.get(0);  // Kein Cast! 😊
list.add(123);  // Compiler-Fehler! ✅
```

---

## Generische Liste Implementierung

```java
class Node<E> {
    E obj;
    Node<E> next;

    public Node(E obj) {
        this.obj = obj;
        this.next = null;
    }
}

class MyList<E> {
    private Node<E> head;
    private Node<E> tail;

    public MyList() {
        this.head = null;
        this.tail = null;
    }

    public void add(E obj) {
        Node<E> newNode = new Node<>(obj);

        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
    }

    public boolean remove(E obj) {
        if (head == null) return false;

        if (head.obj.equals(obj)) {
            head = head.next;
            if (head == null) tail = null;
            return true;
        }

        Node<E> current = head;
        while (current.next != null) {
            if (current.next.obj.equals(obj)) {
                current.next = current.next.next;
                if (current.next == null) tail = current;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = head;

            public boolean hasNext() {
                return current != null;
            }

            public E next() {
                E obj = current.obj;
                current = current.next;
                return obj;
            }
        };
    }
}
```

**Verwendung:**
```java
MyList<String> list = new MyList<>();
list.add("A");
list.add("B");

for (String s : list) {
    System.out.println(s);  // Kein Cast nötig!
}
```

---

# TEIL 6: SET OPERATIONEN (KLAUSUR!)

## Wichtige Set-Methoden

### 1. add(E e) - Element hinzufügen

```java
Set<String> set = new HashSet<>();
boolean added1 = set.add("A");  // true (neu)
boolean added2 = set.add("A");  // false (Duplikat!)
```

**Rückgabe:**
- `true` wenn hinzugefügt
- `false` wenn schon vorhanden

### 2. remove(Object o) - Element entfernen

```java
Set<String> set = new HashSet<>(Arrays.asList("A", "B"));
boolean removed1 = set.remove("A");  // true (vorhanden)
boolean removed2 = set.remove("C");  // false (nicht vorhanden)
```

### 3. contains(Object o) - Membership Test

```java
Set<String> set = new HashSet<>(Arrays.asList("A", "B"));
boolean has1 = set.contains("A");  // true
boolean has2 = set.contains("C");  // false
```

**Performance:**
- HashSet: O(1) - sehr schnell!
- TreeSet: O(log n) - etwas langsamer

### 4. size() und isEmpty()

```java
Set<String> set = new HashSet<>();
System.out.println(set.isEmpty());  // true
set.add("A");
System.out.println(set.size());     // 1
```

### 5. Iterator für Durchlauf

```java
Set<String> set = new HashSet<>(Arrays.asList("A", "B", "C"));

// Methode 1: Iterator
Iterator<String> it = set.iterator();
while (it.hasNext()) {
    System.out.println(it.next());
}

// Methode 2: For-each
for (String s : set) {
    System.out.println(s);
}
```

---

## equals() und hashCode() für Sets

### Warum wichtig?

**HashSet nutzt hashCode() zum Speichern!**

```java
class Person {
    String name;

    public Person(String name) {
        this.name = name;
    }

    // OHNE equals() und hashCode():
    // Set speichert ZWEI "Alice" Objekte! ❌
}
```

### Korrekte Implementierung

```java
class Person {
    String name;

    public Person(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Person p = (Person) obj;
        return Objects.equals(name, p.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
```

**Jetzt funktioniert's:**
```java
Set<Person> set = new HashSet<>();
set.add(new Person("Alice"));
set.add(new Person("Alice"));  // Duplikat wird erkannt!
System.out.println(set.size());  // 1 ✅
```

---

# TEIL 7: DUPLIKATE ENTFERNEN MIT SET

## List → Set → List (Duplikate weg!)

```java
// Liste mit Duplikaten
List<String> list = Arrays.asList("A", "B", "A", "C", "B", "D");
System.out.println(list);  // [A, B, A, C, B, D]

// METHODE 1: HashSet (Reihenfolge verloren)
Set<String> set1 = new HashSet<>(list);
System.out.println(set1);  // [A, B, C, D] - Reihenfolge zufällig

// METHODE 2: LinkedHashSet (Reihenfolge erhalten)
Set<String> set2 = new LinkedHashSet<>(list);
System.out.println(set2);  // [A, B, C, D] - Reihenfolge wie zuerst eingefügt

// METHODE 3: TreeSet (sortiert)
Set<String> set3 = new TreeSet<>(list);
System.out.println(set3);  // [A, B, C, D] - alphabetisch sortiert

// Zurück zu Liste
List<String> uniqueList = new ArrayList<>(set2);
```

---

## Stream API für Duplikate (modern)

```java
List<String> list = Arrays.asList("A", "B", "A", "C", "B");

// Duplikate entfernen + Reihenfolge erhalten
List<String> unique = list.stream()
                          .distinct()  // Entfernt Duplikate
                          .collect(Collectors.toList());
System.out.println(unique);  // [A, B, C]
```

---

# 🔥 KLAUSUR-CHEATSHEET

## Schnelle Entscheidung: List vs Set

**Frage: "Speichere User-IDs ohne Duplikate"**
→ **Set** (keine Duplikate)

**Frage: "Speichere Schritte eines Rezepts"**
→ **List** (Reihenfolge wichtig)

**Frage: "Speichere sortierte Menge eindeutiger Zahlen"**
→ **TreeSet** (sortiert + keine Duplikate)

---

## Node-Liste in der Klausur

**Typische Aufgabe:** "Implementiere verkettete Liste mit add() und remove()"

**Checkliste:**
- [ ] Node-Klasse mit `obj` und `next`
- [ ] List-Klasse mit `head` und `tail`
- [ ] add(): Fall 1 (leer) und Fall 2 (nicht leer)
- [ ] remove(): Fall 1 (head), Fall 2 (Mitte), Fall 3 (nicht gefunden)
- [ ] tail aktualisieren nicht vergessen!

---

## Häufige Fehler

### ❌ FEHLER 1: tail nicht aktualisiert

```java
// FALSCH:
public void add(Object obj) {
    Node newNode = new Node(obj);
    tail.next = newNode;
    // tail = newNode;  ← VERGESSEN!
}
```

### ❌ FEHLER 2: equals() und hashCode() fehlen

```java
// FALSCH:
class Person {
    String name;
    // equals() und hashCode() fehlen!
}
Set<Person> set = new HashSet<>();  // Duplikate werden NICHT erkannt!
```

### ❌ FEHLER 3: TreeSet ohne Comparable

```java
// FALSCH:
class Person { }  // Kein Comparable!
Set<Person> set = new TreeSet<>();  // ClassCastException!
```

### ❌ FEHLER 4: Index-Zugriff auf Set

```java
// FALSCH:
Set<String> set = new HashSet<>();
String s = set.get(0);  // Geht nicht! Set hat keinen Index!
```

---

## 📝 Klausur-Checkliste

- [ ] List erlaubt Duplikate, Set nicht?
- [ ] List hat Index-Zugriff, Set nicht?
- [ ] HashSet ist ungeordnet, TreeSet sortiert?
- [ ] TreeSet braucht Comparable oder Comparator?
- [ ] Node hat `obj` und `next`?
- [ ] head und tail in Liste?
- [ ] add() behandelt leere Liste?
- [ ] remove() behandelt alle Fälle?
- [ ] Iterator hat hasNext() und next()?
- [ ] equals() und hashCode() für Set?

---

## 🎓 Zusammenfassung

**List:**
- Duplikate ✅
- Reihenfolge ✅
- Index-Zugriff ✅
- Beispiel: ArrayList, LinkedList

**Set:**
- Duplikate ❌
- Reihenfolge ⚠️ (nur TreeSet/LinkedHashSet)
- Index-Zugriff ❌
- Beispiel: HashSet, TreeSet

**Node-Liste:**
- Node mit `obj` und `next`
- Liste mit `head` und `tail`
- add(), remove(), iterator()

**Iterator:**
- hasNext() - noch Elemente?
- next() - nächstes Element

**Das war's! Wenn du das kannst, bist du safe! 💪**
