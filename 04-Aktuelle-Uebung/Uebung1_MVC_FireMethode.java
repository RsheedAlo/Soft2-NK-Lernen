// =============================================================================
// ÜBUNG 1: MVC mit FIRE-METHODE (25 Punkte Klausur-Aufgabe!)
// =============================================================================
// ZIEL: Verstehe wie fire-Methoden funktionieren und Listener benachrichtigt werden
//
// AUFGABENSTELLUNG:
// Erstelle ein einfaches MVC-System für eine Todo-Liste:
// 1. TodoModel - speichert Todos und benachrichtigt Listener
// 2. TodoListener Interface - wird bei Änderungen aufgerufen
// 3. TodoView - zeigt Todos an und implementiert Listener
// 4. TodoEvent - Event-Objekt mit Infos über Änderung
// =============================================================================

import java.util.*;

// =============================================================================
// AUFGABE 1a: TodoEvent - Event Klasse (2 Punkte)
// =============================================================================
// Erstelle eine Event-Klasse TodoEvent die Informationen über eine Todo-Änderung speichert.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - Eine Referenz zum Source-Objekt speichert (Object source)
// - Einen Konstruktor hat der source nimmt
// - Eine getSource() Methode hat
// ▲▲▲ TIPPS ▲▲▲

class TodoEvent extends EventObject {
    // DEINE LÖSUNG:
    private String todo;
    public TodoEvent(Object source, String todo){
        super(source);
        this.todo = todo;
    }

    public String getTodo(){
        return todo;
    }
}


// =============================================================================
// AUFGABE 1b: TodoListener Interface (3 Punkte)
// =============================================================================
// Erstelle ein Listener-Interface das benachrichtigt wird wenn Todos hinzugefügt oder entfernt werden.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - void todoAdded(TodoEvent event)
// - void todoRemoved(TodoEvent event)
// ▲▲▲ TIPPS ▲▲▲

interface TodoListener extends EventListener{
    // DEINE LÖSUNG:
    void todoAdded(TodoEvent event);
    void todoRemoved(TodoEvent event);
}


// =============================================================================
// AUFGABE 1c: TodoModel (12 Punkte) ⚠️ WICHTIGSTER TEIL!
// =============================================================================
// Erstelle ein TodoModel das:
// - Todos speichert (als List<String>)
// - Listener registrieren kann
// - Bei jeder Änderung (addTodo/removeTodo) ALLE registrierten Listener benachrichtigt
// - Eine fire-Methode hat die alle Listener durchgeht und aufruft

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// ATTRIBUTE:
// - private List<String> todos
// - private List<TodoListener> listeners
//
// METHODEN:
// 1. public void addTodoListener(TodoListener listener)
// 2. public void addTodo(String todo) → ruft fireTodoAdded() auf
// 3. public void removeTodo(String todo) → ruft fireTodoRemoved() auf
// 4. private void fireTodoAdded(String todo) ⚠️ KRITISCH!
//    → Erstelle TodoEvent
//    → For-Schleife über ALLE listeners
//    → Rufe listener.todoAdded(event) für jeden
// 5. private void fireTodoRemoved(String todo)
// 6. public List<String> getTodos()
// ▲▲▲ TIPPS ▲▲▲

class TodoModel {
    // DEINE LÖSUNG:
    private List<String> todos;
    private List<TodoListener> listeners;

    public TodoModel(){
        todos = new ArrayList<>();
        listeners = new ArrayList<>();
    }

    public List<String> getTodos(){
        return new ArrayList<>(todos);
    }

    public void addTodoListener(TodoListener l){
        listeners.add(l);
    }

    public void removeTodoListener(TodoListener l){
        listeners.remove(l);
    }

    public void addTodo(String todo){
        todos.add(todo);
        fireTodoAdded(todo);
    }

    public void fireTodoAdded(String todo){
        TodoEvent event = new TodoEvent(this, todo);
        for(TodoListener l : listeners){
            l.todoAdded(event);
        }
    }

    public void removeTodo(String todo){
        todos.remove(todo);
        fireTodoRemoved(todo);
    }

    public void fireTodoRemoved(String todo){
        TodoEvent event = new TodoEvent(this, todo);
        for(TodoListener l : listeners){
            l.todoRemoved(event);
        }
    }
}


// =============================================================================
// AUFGABE 1d: TodoView (5 Punkte)
// =============================================================================
// Erstelle eine View-Klasse die:
// - Sich beim Model als Listener registriert
// - Bei Änderungen die aktuelle Todo-Liste ausgibt

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - TodoListener implementieren
// - Im Konstruktor: model.addTodoListener(this)
// - todoAdded(): Gib aus "Todo added! Current todos: " + model.getTodos()
// - todoRemoved(): Gib aus "Todo removed! Current todos: " + model.getTodos()
// ▲▲▲ TIPPS ▲▲▲

class TodoView implements TodoListener{
    // DEINE LÖSUNG:
    private TodoModel model;

    public TodoView(TodoModel model){
        this.model = model;
        model.addTodoListener(this);
    }

    public void todoAdded(TodoEvent event){
        System.out.print("Todo added! Current todos: ");
        for(String t : model.getTodos()){
            System.out.println(t);
        }
    }

    public void todoRemoved(TodoEvent event){
        System.out.print("Todo removed! Current todos: ");
        for(String t : model.getTodos()){
            System.out.println(t);
        }
    }
}


// =============================================================================
// AUFGABE 1e: Main zum Testen (3 Punkte)
// =============================================================================
// Teste das MVC-System: Erstelle Model und View, füge Todos hinzu und entferne eins.
// Die View soll automatisch alle Änderungen ausgeben.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// 1. TodoModel erstellen
// 2. TodoView erstellen (mit Model)
// 3. Todos hinzufügen: "Lernen", "Schlafen", "Klausur schreiben"
// 4. Todo entfernen: "Schlafen"
// ▲▲▲ TIPPS ▲▲▲

// ERWARTETE AUSGABE:
// Todo added! Current todos: [Lernen]
// Todo added! Current todos: [Lernen, Schlafen]
// Todo added! Current todos: [Lernen, Schlafen, Klausur schreiben]
// Todo removed! Current todos: [Lernen, Klausur schreiben]

class Uebung1_MVC_FireMethode {
    public static void main(String[] args) {
        // DEINE LÖSUNG:
        TodoModel model = new TodoModel();
        TodoView view = new TodoView(model);

        model.addTodo("Lernen");
        model.addTodo("Schlafen");
        model.addTodo("Klausur schreiben");

        model.removeTodo("Schlafen");
    }
}


// =============================================================================
// CHECKLISTE - HAST DU ALLES?
// =============================================================================
// □ TodoEvent hat source und getSource()
// □ TodoListener Interface mit 2 Methoden
// □ TodoModel hat List<TodoListener>
// □ addTodoListener() Methode
// □ fireTodoAdded() macht for-Schleife über listeners
// □ fireTodoRemoved() macht for-Schleife über listeners
// □ TodoView implementiert TodoListener
// □ TodoView registriert sich im Konstruktor
// □ Main erstellt Model und View
// □ Ausgabe stimmt mit erwartet überein
//
// HÄUFIGE FEHLER:
// ✗ fire-Methode vergessen aufzurufen in addTodo()/removeTodo()
// ✗ View vergisst sich zu registrieren
// ✗ For-Schleife in fire-Methode falsch
// ✗ Event wird nicht erstellt
