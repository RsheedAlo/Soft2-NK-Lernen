# MVC Pattern - Circle Example

Complete implementation of the **MVC (Model-View-Controller)** pattern with **Observer Pattern** integration, based on the Design Patterns PDF.

## Project Structure

```
mvc-circle-example/
├── src/
│   ├── model/                   # MODEL Package
│   │   ├── Circle.java         # Data object (POJO)
│   │   └── CircleModel.java    # MODEL - data + business logic
│   │
│   ├── view/                    # VIEW Package
│   │   └── CircleView.java     # VIEW - displays data, listens to model
│   │
│   ├── controller/              # CONTROLLER Package
│   │   └── CircleController.java # CONTROLLER - handles input, updates model
│   │
│   ├── event/                   # EVENT Package
│   │   ├── CircleEvent.java    # Event class (extends EventObject)
│   │   └── CircleListener.java # Listener interface (extends EventListener)
│   │
│   └── Main.java                # Application entry point
│
├── bin/                         # Compiled .class files (auto-generated)
│
├── README.md                    # This file
├── MVC_ERKLAERUNG.md           # Detailed German explanation
├── BEISPIEL_ABLAUF.md          # Step-by-step example walkthrough
├── FAQ.md                       # Frequently asked questions
├── QUICK_REFERENCE.md          # Cheat sheet
├── UEBUNGEN.md                 # Exercises
└── PACKAGE_STRUCTURE.md        # Package organization explained
```

## How to Compile and Run

**Option 1: Quick Compile (from src directory)**
```bash
cd mvc-circle-example/src
javac model/*.java event/*.java view/*.java controller/*.java Main.java
java Main
```

**Option 2: With Output Directory (recommended)**
```bash
cd mvc-circle-example

# Compile with output to bin/
javac -d bin src/model/*.java src/event/*.java src/view/*.java src/controller/*.java src/Main.java

# Run
java -cp bin Main
```

**Option 3: All files at once**
```bash
cd mvc-circle-example
javac -d bin src/**/*.java src/*.java
java -cp bin Main
```

## MVC Pattern Components

### 1. **Model** (CircleModel.java)
- **Holds data**: `List<Circle> circles`
- **Manages listeners**: `List<CircleListener> listeners`
- **Add/Remove listener methods**: `addCircleListener()`, `removeCircleListener()`
- **Fire method**: `fireModelEvent(kind, circle)` - notifies all listeners
- **Business logic**: `addCircle()`, `removeCircleAt()`

**Key Method - fireModelEvent():**
```java
private void fireModelEvent(CircleEvent.Kind kind, Circle circle) {
    CircleEvent event = new CircleEvent(this, kind, circle);
    for (CircleListener listener : listeners) {
        listener.circleChanged(event);
    }
}
```

### 2. **View** (CircleView.java)
- **Implements CircleListener** - observes model changes
- **Registers with model**: `model.addCircleListener(this)`
- **Displays data**: `paintComponent()` draws all circles
- **Reacts to changes**: `circleChanged()` calls `repaint()`

**Key Method - circleChanged():**
```java
@Override
public void circleChanged(CircleEvent event) {
    System.out.println("View notified: " + event);
    repaint();  // Redraw to show changes
}
```

### 3. **Controller** (CircleController.java)
- **Extends MouseAdapter** - handles mouse events
- **Left-click**: adds circle via `model.addCircle()`
- **Right-click**: removes circle via `model.removeCircleAt()`
- **ONLY component that modifies the model**

### 4. **Event System**

**CircleEvent** (extends EventObject):
```java
public enum Kind { ADDED, DELETED }
- source: Object (the model)
- kind: Kind (ADDED or DELETED)
- circle: Circle (the affected circle)
```

**CircleListener** (extends EventListener):
```java
void circleChanged(CircleEvent event);
```

## Event Flow

```
User clicks mouse
    ↓
Controller.mouseClicked()
    ↓
model.addCircle(circle) or model.removeCircleAt(x, y)
    ↓
model.fireModelEvent(kind, circle)
    ↓
Creates CircleEvent
    ↓
Calls listener.circleChanged(event) for ALL registered listeners
    ↓
view.circleChanged(event)
    ↓
view.repaint()
    ↓
Screen updates with new circles
```

## Key Design Patterns

### Observer Pattern
- **Subject**: CircleModel (maintains listener list)
- **Observer**: CircleView (implements CircleListener)
- **Event**: CircleEvent (carries change information)
- **Notification**: fireModelEvent() method

### MVC Pattern
- **Separation of Concerns**:
  - Model: data + logic
  - View: display + observation
  - Controller: input handling

- **Unidirectional Flow**:
  - Controller → Model (updates)
  - Model → View (notifications)
  - View → Controller (via event listeners)

## Usage

1. **Run the application**: `java Main`
2. **Left-click**: Add a circle with random radius (20-50px)
3. **Right-click**: Remove a circle at cursor position
4. **Watch console**: See event notifications in real-time

## Console Output Example

```
=== MVC Circle Example ===
Creating MVC components...

✓ Model created
✓ View created and registered as listener
✓ Controller created
✓ Controller registered to handle mouse events

=== Application Started ===
Instructions:
- Left-click to add a circle
- Right-click to remove a circle

Watch the console for event notifications!

Controller: Added circle at (150, 200)
View notified: CircleEvent [kind=ADDED, circle=Circle [x=150, y=200, radius=35]]
Controller: Attempted to remove circle at (150, 200)
View notified: CircleEvent [kind=DELETED, circle=Circle [x=150, y=200, radius=35]]
```

## Comparison to PDF Examples

Based on **Zusammenfassung_Soft2_DesignPatterns.pdf**:

### MVC Pattern (Page 18-21)
- Student/StudentView/StudentController → Circle/CircleView/CircleController
- Same structure: Model (POJO), View (display), Controller (mediator)

### Observer Pattern (Page 21-24)
- Subject/Observer → CircleModel/CircleListener
- Same components: attach/detach, notifyAllObservers → add/removeListener, fireModelEvent
- Event system: EventObject/EventListener integration

## Class Diagram

```
┌─────────────────┐
│      Main       │
│   +main()       │
└────────┬────────┘
         │ creates
         ↓
    ┌────────────────────────────────────┐
    │                                    │
    ↓                                    ↓
┌─────────────────┐              ┌─────────────────┐
│  CircleModel    │              │ CircleView      │
│  (MODEL)        │              │ (VIEW)          │
├─────────────────┤              ├─────────────────┤
│ -circles: List  │◄─────────────│ -model          │
│ -listeners:List │   observes   │                 │
├─────────────────┤              ├─────────────────┤
│ +addListener()  │              │ +paintComponent│
│ +removeListener│              │ +circleChanged()│
│ +addCircle()    │              │                 │
│ +removeCircle() │              │ implements      │
│ -fireModelEvent│              │ CircleListener  │
└────────┬────────┘              └────────┬────────┘
         │                                │
         │ updates                        │ mouse events
         ↓                                ↓
    ┌─────────────────┐         ┌─────────────────┐
    │ CircleController│◄────────│  MouseAdapter   │
    │  (CONTROLLER)   │         └─────────────────┘
    ├─────────────────┤
    │ -model          │
    ├─────────────────┤
    │ +mouseClicked() │
    └─────────────────┘

         Uses
         ↓
    ┌─────────────────┐
    │  CircleEvent    │
    │ (EventObject)   │
    ├─────────────────┤
    │ +kind: Kind     │
    │ +circle: Circle │
    └─────────────────┘
```

## Learning Objectives ✓

- [x] Model with listener list
- [x] add/removeListener methods
- [x] Fire method that sends events to all listeners
- [x] Event class extending EventObject
- [x] Listener interface extending EventListener
- [x] View/Controller with MouseListener
- [x] CircleEvent with Kind.ADDED/DELETED
- [x] fireModelEvent() implementation
- [x] View reacts with repaint()
