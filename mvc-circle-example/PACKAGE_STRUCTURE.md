# 📦 Package-Struktur - MVC Organisation

## Warum Packages?

**Problem ohne Packages:**
- Alle Klassen in einem Ordner → unübersichtlich
- Schwer zu erkennen, welche Klasse was macht
- Keine klare Trennung der Verantwortlichkeiten

**Lösung mit Packages:**
- Klassen nach **Verantwortung** gruppiert
- **model/** = Daten und Logik
- **view/** = Anzeige
- **controller/** = Eingabe-Verwaltung
- **event/** = Event-System

---

## 📂 Neue Verzeichnis-Struktur

```
mvc-circle-example/
├── src/
│   ├── model/                    ← MODEL Package
│   │   ├── Circle.java          (Daten-Objekt)
│   │   └── CircleModel.java     (Model mit Listener-Verwaltung)
│   │
│   ├── view/                     ← VIEW Package
│   │   └── CircleView.java      (Anzeige, implementiert CircleListener)
│   │
│   ├── controller/               ← CONTROLLER Package
│   │   └── CircleController.java (Eingabe-Verwaltung)
│   │
│   ├── event/                    ← EVENT Package
│   │   ├── CircleEvent.java     (Event-Klasse)
│   │   └── CircleListener.java  (Listener-Interface)
│   │
│   └── Main.java                 ← Einstiegspunkt (kein Package)
│
├── README.md
├── MVC_ERKLAERUNG.md
├── BEISPIEL_ABLAUF.md
├── FAQ.md
├── QUICK_REFERENCE.md
└── UEBUNGEN.md
```

---

## 🔗 Package-Abhängigkeiten

### Wer kennt wen?

```
┌─────────────────────────────────────────┐
│         Main (kein Package)             │
│                                         │
│  imports:                               │
│  - model.CircleModel                    │
│  - view.CircleView                      │
│  - controller.CircleController          │
└─────────────────────────────────────────┘
                   │
                   ↓
    ┌──────────────┴──────────────┐
    ↓                             ↓
┌─────────────┐            ┌─────────────┐
│   MODEL     │            │    VIEW     │
│   package   │            │   package   │
│             │            │             │
│ Circle      │            │ CircleView  │
│ CircleModel │◄───────────│             │
└─────────────┘            └─────────────┘
      ↑                            ↑
      │                            │
      │         ┌─────────────┐    │
      │         │ CONTROLLER  │    │
      │         │   package   │    │
      │         │             │    │
      └─────────│CircleCtrl   │────┘
                └─────────────┘
                      ↑
                      │
                ┌─────────────┐
                │    EVENT    │
                │   package   │
                │             │
                │ CircleEvent │
                │ CircleList. │
                └─────────────┘
```

**Wichtig:**
- **model/** kennt nur **event/** (für Listener-Interface)
- **view/** kennt **model/** und **event/**
- **controller/** kennt **model/** und **event/**
- **event/** kennt **model/** (für Circle in Event)

---

## 📝 Package-Deklarationen

### 1. model/Circle.java
```java
package model;

public class Circle {
    // Kein Import nötig - standalone Klasse
}
```

### 2. model/CircleModel.java
```java
package model;

import event.CircleEvent;      // ← Braucht Event
import event.CircleListener;   // ← Braucht Listener

import java.util.ArrayList;
import java.util.List;

public class CircleModel {
    private List<Circle> circles;           // ← Circle aus gleichem Package
    private List<CircleListener> listeners; // ← Import von event
}
```

### 3. event/CircleEvent.java
```java
package event;

import model.Circle;           // ← Braucht Circle
import java.util.EventObject;

public class CircleEvent extends EventObject {
    private Circle circle;     // ← Import von model
}
```

### 4. event/CircleListener.java
```java
package event;

import java.util.EventListener;

public interface CircleListener extends EventListener {
    void circleChanged(CircleEvent event);  // ← CircleEvent aus gleichem Package
}
```

### 5. view/CircleView.java
```java
package view;

import event.CircleEvent;      // ← Braucht Event
import event.CircleListener;   // ← Braucht Listener
import model.Circle;           // ← Braucht Circle
import model.CircleModel;      // ← Braucht Model

import javax.swing.*;
import java.awt.*;

public class CircleView extends JPanel implements CircleListener {
    private CircleModel model;  // ← Import von model
}
```

### 6. controller/CircleController.java
```java
package controller;

import model.Circle;           // ← Braucht Circle
import model.CircleModel;      // ← Braucht Model

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class CircleController extends MouseAdapter {
    private CircleModel model;  // ← Import von model
}
```

### 7. Main.java (kein Package!)
```java
// KEIN package Statement - liegt im root

import controller.CircleController;  // ← Import aller Packages
import model.CircleModel;
import view.CircleView;

import javax.swing.*;

public class Main {
    // ...
}
```

---

## 🚀 Kompilieren & Ausführen

### Alte Struktur (alle Dateien in src/):
```bash
cd mvc-circle-example/src
javac *.java
java Main
```

### Neue Struktur (mit Packages):

**Option 1: Manuell kompilieren**
```bash
cd mvc-circle-example/src

# Kompiliere alle Java-Dateien
javac model/*.java event/*.java view/*.java controller/*.java Main.java

# Ausführen (vom src/ Verzeichnis aus!)
java Main
```

**Option 2: Mit Output-Verzeichnis (empfohlen)**
```bash
cd mvc-circle-example

# Kompiliere mit Output in bin/
javac -d bin src/model/*.java src/event/*.java src/view/*.java src/controller/*.java src/Main.java

# Ausführen
java -cp bin Main
```

**Option 3: Alle Dateien auf einmal**
```bash
cd mvc-circle-example

# Finde und kompiliere alle .java Dateien
javac -d bin src/**/*.java src/*.java

# Ausführen
java -cp bin Main
```

---

## ✅ Vorteile der Package-Struktur

### 1. **Klare Trennung**
```
model/     → "Was sind die Daten?"
view/      → "Wie sieht es aus?"
controller/→ "Was tut der User?"
event/     → "Wie kommunizieren sie?"
```

### 2. **Leicht zu finden**
- Suchst du Daten? → `model/`
- Suchst du Anzeige? → `view/`
- Suchst du Input-Handling? → `controller/`

### 3. **Skalierbar**
```
model/
├── Circle.java
├── Rectangle.java         ← Einfach neue Klassen hinzufügen
├── Shape.java
└── ShapeModel.java

view/
├── CircleView.java
├── ListView.java          ← Mehrere Views möglich
└── ToolbarView.java

controller/
├── MouseController.java
├── KeyboardController.java ← Mehrere Controller
└── MenuController.java
```

### 4. **Wiederverwendbar**
```java
// Anderes Projekt kann model/ nutzen:
import com.myapp.model.CircleModel;

// Ohne GUI:
CircleModel model = new CircleModel();
model.addCircle(new Circle(10, 20, 30));
// Funktioniert auch ohne View!
```

### 5. **Testbar**
```java
// Test nur für Model (ohne GUI)
package test;

import model.CircleModel;
import model.Circle;
import org.junit.Test;

public class CircleModelTest {
    @Test
    public void testAddCircle() {
        CircleModel model = new CircleModel();
        model.addCircle(new Circle(10, 20, 30));
        assertEquals(1, model.getCircles().size());
    }
}
```

---

## 🔄 Migration (alte → neue Struktur)

### Was wurde geändert?

**1. Dateien verschoben:**
```
src/Circle.java          → src/model/Circle.java
src/CircleModel.java     → src/model/CircleModel.java
src/CircleView.java      → src/view/CircleView.java
src/CircleController.java → src/controller/CircleController.java
src/CircleEvent.java     → src/event/CircleEvent.java
src/CircleListener.java  → src/event/CircleListener.java
src/Main.java            → src/Main.java (bleibt im root)
```

**2. Package-Deklarationen hinzugefügt:**
```java
// An den Anfang jeder Datei (außer Main.java):
package model;           // in model/...
package view;            // in view/...
package controller;      // in controller/...
package event;           // in event/...
```

**3. Imports hinzugefügt:**
```java
// Wenn Klasse aus anderem Package genutzt wird:
import model.Circle;
import event.CircleEvent;
import view.CircleView;
```

---

## 📚 Best Practices

### 1. **Package-Namen klein schreiben**
```java
✅ package model;
✅ package view;
✅ package controller;

❌ package Model;         // Großbuchstabe nicht üblich
❌ package myView;        // camelCase nicht üblich
```

### 2. **Klassen-Namen groß schreiben**
```java
✅ public class Circle
✅ public class CircleModel

❌ public class circle    // klein nicht üblich
```

### 3. **Eine Klasse = eine Datei**
```
✅ Circle.java enthält nur class Circle
❌ Circle.java enthält class Circle UND class Rectangle
```

### 4. **Package entspricht Ordner-Struktur**
```
✅ model/Circle.java → package model;
✅ view/CircleView.java → package view;

❌ model/Circle.java → package controller; // Falsch!
```

### 5. **Main bleibt im Root (optional)**
```
src/
├── Main.java           ← Kein Package (Einstiegspunkt)
├── model/
├── view/
└── controller/
```

Oder mit Package:
```
src/
└── app/
    ├── Main.java       ← package app;
    ├── model/
    ├── view/
    └── controller/
```

---

## 🎯 Zusammenfassung

**Vorher:**
```
src/
├── Circle.java
├── CircleModel.java
├── CircleView.java
├── CircleController.java
├── CircleEvent.java
├── CircleListener.java
└── Main.java
```
→ Unübersichtlich bei vielen Klassen

**Nachher:**
```
src/
├── model/
│   ├── Circle.java
│   └── CircleModel.java
├── view/
│   └── CircleView.java
├── controller/
│   └── CircleController.java
├── event/
│   ├── CircleEvent.java
│   └── CircleListener.java
└── Main.java
```
→ Klar strukturiert, leicht zu erweitern!

**Merke:**
- `package X;` = "Ich gehöre zu Package X"
- `import X.Y;` = "Ich brauche Klasse Y aus Package X"
- Packages = Ordner-Struktur

---

Viel Erfolg! 🚀
