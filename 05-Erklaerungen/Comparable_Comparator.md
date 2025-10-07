# COMPARABLE & COMPARATOR - Idiotensichere Erklärung für die Klausur

## 🎯 Das Wichtigste in 3 Sätzen

1. **Comparable** = Sortierung **IN** der Klasse (wie natürliche Ordnung von Zahlen)
2. **Comparator** = Sortierung **AUßERHALB** der Klasse (flexibel, mehrere möglich)
3. **Beide** geben zurück: **negativ** (kleiner), **0** (gleich), **positiv** (größer)

---

## 🧠 Eselsbrücke für die Klausur

**COMPARABLE** = **SINGLE** (nur eine Sortierung, in der Klasse selbst)
- **compareTo(other)** - vergleiche MICH mit OTHER
- Wie Zahlen: `5.compareTo(10)` → negativ (5 ist kleiner)

**COMPARATOR** = **MULTIPLE** (viele Sortierungen möglich, außerhalb)
- **compare(o1, o2)** - vergleiche O1 mit O2
- Wie Schiedsrichter: Du stehst außen und sagst wer größer ist

---

## 📊 Die Rückgabewerte (SUPER WICHTIG!)

```
compareTo(other)   oder   compare(o1, o2)

Rückgabe:
  NEGATIV (z.B. -1)  →  this/o1 ist KLEINER  →  kommt VOR
  0                  →  GLEICH
  POSITIV (z.B. +1)  →  this/o1 ist GRÖßER  →  kommt NACH
```

**Beispiel Integer:**
```java
5.compareTo(10)  → -1  (5 < 10, also 5 kommt vor 10)
10.compareTo(5)  → +1  (10 > 5, also 10 kommt nach 5)
5.compareTo(5)   → 0   (gleich)
```

---

# TEIL 1: COMPARABLE<T>

## Was ist Comparable?

**Stell dir vor:** Du hast Zahlen (5, 10, 3). Die wissen von SELBST wie sie sich sortieren!

```java
class MeineZahl implements Comparable<MeineZahl> {
    int wert;

    public int compareTo(MeineZahl other) {
        return this.wert - other.wert;  // Ich minus Other
    }
}
```

## Die Methode: compareTo(T other)

**Signatur:**
```java
public int compareTo(T other)
```

**Was sie macht:**
- Vergleicht **MICH (this)** mit **OTHER**
- Gibt zurück: negativ, 0, oder positiv

## Beispiel: Course-Klasse

**Natürliche Sortierung:** Erst nach `name`, dann nach `year`

```java
class Course implements Comparable<Course> {
    private String name;
    private int year;

    // Konstruktor, Getter...

    @Override
    public int compareTo(Course other) {
        // SCHRITT 1: Vergleiche Namen
        int nameComp = this.name.compareTo(other.name);

        // SCHRITT 2: Wenn Namen unterschiedlich, fertig!
        if (nameComp != 0) {
            return nameComp;  // Namen sind verschieden
        }

        // SCHRITT 3: Namen gleich → vergleiche Jahr
        return Integer.compare(this.year, other.year);
    }
}
```

**Verwendung:**
```java
List<Course> courses = new ArrayList<>();
courses.add(new Course("Soft2", 2023));
courses.add(new Course("Mathe", 2022));

Collections.sort(courses);  // Nutzt compareTo() automatisch!
// Ergebnis: Mathe (2022), Soft2 (2023)
```

## TreeSet nutzt Comparable automatisch!

```java
TreeSet<Course> set = new TreeSet<>();  // Keine Comparator nötig!
set.add(new Course("Soft2", 2023));
set.add(new Course("Mathe", 2022));
// TreeSet ist automatisch sortiert via compareTo()
```

## ⚠️ WICHTIG: equals() konsistent halten!

**Regel:** Wenn `compareTo()` 0 zurückgibt, sollte `equals()` true sein!

```java
@Override
public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Course c = (Course) obj;
    return year == c.year && Objects.equals(name, c.name);
}

@Override
public int hashCode() {
    return Objects.hash(name, year);  // Gleiche Felder wie equals!
}
```

**Warum?** TreeSet verliert sonst Elemente!

---

## 🚨 HÄUFIGE FEHLER bei compareTo()

### ❌ FEHLER 1: Overflow bei Subtraktion

```java
// GEFÄHRLICH!
public int compareTo(Course other) {
    return this.year - other.year;  // Kann überlaufen!
}
```

**Problem:** Wenn `year` sehr groß (z.B. Integer.MAX_VALUE), kann Overflow entstehen!

**Lösung:**
```java
return Integer.compare(this.year, other.year);  // Sicher!
```

### ❌ FEHLER 2: Inkonsistent mit equals()

```java
// SCHLECHT:
compareTo() gibt 0 zurück (gleich)
equals() gibt false zurück (ungleich)
```

**Folge:** TreeSet denkt Elemente sind gleich, speichert nur eins!

### ❌ FEHLER 3: Reihenfolge vertauscht

```java
// FALSCH:
return other.name.compareTo(this.name);  // Umgekehrt!
```

**Richtig:**
```java
return this.name.compareTo(other.name);
```

### ❌ FEHLER 4: Null nicht behandelt

```java
public int compareTo(Course other) {
    if (other == null) return 1;  // this > null
    // Rest...
}
```

---

# TEIL 2: COMPARATOR<T>

## Was ist Comparator?

**Stell dir vor:** Du bist ein **Schiedsrichter** der von außen sagt: "Spieler A ist besser als B!"

Du änderst NICHT die Spieler-Klasse, du bist nur der Bewerter!

## Die Methode: compare(T o1, T o2)

**Signatur:**
```java
public int compare(T o1, T o2)
```

**Was sie macht:**
- Vergleicht **O1** mit **O2** (beide von außen)
- Gibt zurück: negativ, 0, oder positiv

## Warum Comparator?

**3 Gründe:**

1. **Mehrere Sortierungen möglich**
   - Nach Name sortieren
   - Nach Jahr sortieren
   - Nach Länge sortieren
   - Alles OHNE die Klasse zu ändern!

2. **Klasse kann/darf nicht geändert werden**
   - Klasse ist in Library
   - Du hast keinen Zugriff

3. **Kontextabhängige Sortierung**
   - Manchmal aufsteigend, manchmal absteigend
   - Je nach Situation anders

---

## 5 Arten Comparator zu erstellen

### 1. Anonyme Klasse (klassisch, alt)

```java
Comparator<Course> byYear = new Comparator<Course>() {
    @Override
    public int compare(Course c1, Course c2) {
        return Integer.compare(c1.getYear(), c2.getYear());
    }
};
```

**Merkmal:** Viel Code, aber sehr explizit

### 2. Lambda (modern, kurz)

```java
Comparator<Course> byYear = (c1, c2) -> Integer.compare(c1.getYear(), c2.getYear());
```

**Merkmal:** Kurz und prägnant!

### 3. Method Reference + comparing() (am besten!)

```java
Comparator<Course> byYear = Comparator.comparing(Course::getYear);
```

**Merkmal:** Kürzester Code, sehr lesbar!

### 4. Mehrere Kriterien mit thenComparing()

```java
Comparator<Course> comp = Comparator.comparing(Course::getName)
                                     .thenComparing(Course::getYear);
```

**Bedeutung:** Erst nach Name, dann nach Jahr sortieren

### 5. Umgekehrte Reihenfolge mit reversed()

```java
Comparator<Course> byYearDesc = Comparator.comparing(Course::getYear)
                                          .reversed();
```

**Bedeutung:** Jahr absteigend (größte zuerst)

---

## Verwendung von Comparator

### Collections.sort() mit Comparator

```java
List<Course> courses = ...;

// Mit Comparator:
Collections.sort(courses, Comparator.comparing(Course::getYear));

// Oder kürzer (seit Java 8):
courses.sort(Comparator.comparing(Course::getYear));
```

### TreeSet mit Comparator

```java
// TreeSet MIT Comparator im Konstruktor!
Comparator<Course> byYear = Comparator.comparing(Course::getYear);
TreeSet<Course> set = new TreeSet<>(byYear);

set.add(new Course("Soft2", 2023));
set.add(new Course("Mathe", 2022));
// TreeSet sortiert mit dem Comparator
```

---

## 🔥 Wichtige Comparator-Methoden (Klausurrelevant!)

### 1. `Comparator.comparing(Function)`

```java
Comparator.comparing(Course::getName)
```

**Was es macht:** Sortiert nach dem Rückgabewert der Funktion (getName())

### 2. `thenComparing(Function)`

```java
Comparator.comparing(Course::getName)
          .thenComparing(Course::getYear)
```

**Was es macht:** Sekundäre Sortierung (wenn Namen gleich, nach Jahr)

### 3. `reversed()`

```java
Comparator.comparing(Course::getYear).reversed()
```

**Was es macht:** Umgekehrte Reihenfolge (größte zuerst)

### 4. `nullsFirst()` / `nullsLast()`

```java
Comparator.nullsFirst(Comparator.comparing(Course::getName))
```

**Was es macht:** Null-Werte am Anfang/Ende, verhindert NullPointerException!

### 5. `comparingInt()` / `comparingDouble()`

```java
Comparator.comparingInt(Course::getYear)
```

**Was es macht:** Optimiert für int/double (vermeidet Boxing)

---

## Beispiele aus der Praxis

### Beispiel 1: String nach Länge sortieren

```java
List<String> words = Arrays.asList("Java", "is", "awesome");

// Nach Länge (kürzeste zuerst):
words.sort(Comparator.comparingInt(String::length));
// Ergebnis: [is, Java, awesome]

// Nach Länge (längste zuerst):
words.sort(Comparator.comparingInt(String::length).reversed());
// Ergebnis: [awesome, Java, is]
```

### Beispiel 2: Player nach verschiedenen Kriterien

```java
class Player {
    String name;
    int score;
}

// Nach Score (höchster zuerst):
Comparator<Player> byScore = Comparator.comparingInt(Player::getScore).reversed();

// Nach Name (alphabetisch):
Comparator<Player> byName = Comparator.comparing(Player::getName);

// Nach Score, dann Name:
Comparator<Player> comp = Comparator.comparingInt(Player::getScore).reversed()
                                     .thenComparing(Player::getName);
```

### Beispiel 3: Null-Werte behandeln

```java
List<Course> courses = new ArrayList<>();
courses.add(new Course("Soft2", 2023));
courses.add(null);  // Null-Element!
courses.add(new Course("Mathe", 2022));

// OHNE nullsFirst → NullPointerException!
// courses.sort(Comparator.comparing(Course::getName));  // CRASH!

// MIT nullsFirst → funktioniert!
courses.sort(Comparator.nullsFirst(Comparator.comparing(Course::getName)));
// Null kommt zuerst, dann sortiert
```

---

# COMPARABLE vs COMPARATOR - Der Vergleich

| Aspekt | Comparable | Comparator |
|--------|------------|------------|
| **Methode** | `compareTo(T other)` | `compare(T o1, T o2)` |
| **Wo definiert?** | **IN** der Klasse | **AUßERHALB** |
| **Anzahl Sortierungen** | **EINE** (natural) | **MEHRERE** möglich |
| **Klasse ändern?** | **JA** (implements) | **NEIN** |
| **Lambda möglich?** | **NEIN** | **JA** ✅ |
| **TreeSet** | `new TreeSet<>()` | `new TreeSet<>(comparator)` |
| **Collections.sort()** | `sort(list)` | `sort(list, comparator)` |
| **Beispiel** | Integer, String | Verschiedene Sortierungen |

---

## 🎯 Wann was verwenden?

### ✅ Verwende COMPARABLE wenn:

1. Es eine **OFFENSICHTLICHE** natürliche Sortierung gibt
   - Zahlen: klein → groß
   - Strings: alphabetisch
   - Datum: alt → neu

2. Du die Klasse **kontrollierst** (kannst sie ändern)

3. Es nur **EINE** Standard-Sortierung braucht

**Beispiele:** Integer, String, Date

---

### ✅ Verwende COMPARATOR wenn:

1. Du **MEHRERE** Sortierkriterien brauchst
   - Mal nach Name, mal nach Alter, mal nach Score

2. Du die Klasse **NICHT ändern** kannst/willst
   - Klasse aus Library
   - Fremder Code

3. Sortierung ist **kontextabhängig**
   - In UI aufsteigend, in Report absteigend

**Beispiele:** Player (nach Score ODER Name), Course (nach Jahr ODER Name)

---

# 🔥 Klausur-Cheatsheet

## Schnelle Entscheidung

**Frage: "Sortiere Course nach Jahr"**

**Option 1: Comparable (in der Klasse)**
```java
class Course implements Comparable<Course> {
    public int compareTo(Course other) {
        return Integer.compare(this.year, other.year);
    }
}

Collections.sort(courses);  // Fertig!
```

**Option 2: Comparator (außerhalb)**
```java
courses.sort(Comparator.comparing(Course::getYear));  // Fertig!
```

**Wann was?**
- Wenn du Course ändern darfst → Comparable
- Wenn du Course NICHT ändern darfst → Comparator
- Wenn mehrere Sortierungen nötig → Comparator

---

## Typische Klausur-Aufgaben

### Aufgabe 1: "Implementiere compareTo() für Player (position, name)"

```java
class Player implements Comparable<Player> {
    String position, name;

    public int compareTo(Player other) {
        int posComp = this.position.compareTo(other.position);
        if (posComp != 0) return posComp;
        return this.name.compareTo(other.name);
    }
}
```

### Aufgabe 2: "Sortiere Liste nach Länge (kürzeste zuerst)"

```java
List<String> words = ...;
words.sort(Comparator.comparingInt(String::length));
```

### Aufgabe 3: "TreeSet mit Comparator erstellen (Jahr absteigend)"

```java
TreeSet<Course> set = new TreeSet<>(
    Comparator.comparing(Course::getYear).reversed()
);
```

### Aufgabe 4: "Comparator für zwei Kriterien (name, dann year)"

```java
Comparator<Course> comp = Comparator.comparing(Course::getName)
                                     .thenComparing(Course::getYear);
```

---

## ⚠️ Typische Fehler in der Klausur

### ❌ FEHLER 1: Overflow

```java
// FALSCH:
return this.year - other.year;  // Kann überlaufen!

// RICHTIG:
return Integer.compare(this.year, other.year);
```

### ❌ FEHLER 2: equals() vergessen

```java
// FALSCH: Nur compareTo(), kein equals()
class Course implements Comparable<Course> {
    public int compareTo(Course other) { ... }
    // equals() fehlt! → TreeSet-Probleme!
}

// RICHTIG:
public int compareTo(Course other) { ... }
public boolean equals(Object obj) { ... }
public int hashCode() { ... }
```

### ❌ FEHLER 3: TreeSet ohne Comparable/Comparator

```java
// FALSCH:
class Course { }  // Kein Comparable!
TreeSet<Course> set = new TreeSet<>();  // CRASH!

// RICHTIG:
class Course implements Comparable<Course> { ... }
// ODER:
TreeSet<Course> set = new TreeSet<>(comparator);
```

### ❌ FEHLER 4: Reihenfolge vertauscht

```java
// FALSCH:
return other.name.compareTo(this.name);  // Umgekehrt!

// RICHTIG:
return this.name.compareTo(other.name);
```

### ❌ FEHLER 5: Null nicht behandelt

```java
// FALSCH:
courses.sort(Comparator.comparing(Course::getName));  // NullPointer!

// RICHTIG:
courses.sort(Comparator.nullsFirst(Comparator.comparing(Course::getName)));
```

---

## 📝 Klausur-Checkliste

Vor der Abgabe prüfen:

- [ ] `compareTo()` gibt negativ/0/positiv zurück?
- [ ] `compare()` gibt negativ/0/positiv zurück?
- [ ] `equals()` konsistent mit `compareTo()`?
- [ ] `hashCode()` überschrieben wenn `equals()` überschrieben?
- [ ] `Integer.compare()` statt Subtraktion?
- [ ] TreeSet hat Comparable ODER Comparator?
- [ ] Null-Werte behandelt?
- [ ] Reihenfolge richtig (this vs other)?

---

## 🎓 Zusammenfassung für die Klausur

**Das Wichtigste:**

1. **Comparable** = IN der Klasse, EINE Sortierung
   - `compareTo(other)`
   - TreeSet ohne Parameter

2. **Comparator** = AUßERHALB, MEHRERE Sortierungen
   - `compare(o1, o2)`
   - TreeSet mit Parameter

3. **Rückgabewerte:** negativ (kleiner), 0 (gleich), positiv (größer)

4. **Beste Praxis:**
   - `Integer.compare()` statt Subtraktion
   - `Comparator.comparing()` für einfache Fälle
   - `thenComparing()` für mehrere Kriterien
   - `nullsFirst()` für null-Werte

5. **Nicht vergessen:**
   - `equals()` konsistent mit `compareTo()`
   - `hashCode()` wenn `equals()` überschrieben

**Das war's! Wenn du das kannst, bist du safe! 💪**
