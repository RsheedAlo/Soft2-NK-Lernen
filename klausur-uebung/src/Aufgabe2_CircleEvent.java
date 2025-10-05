import java.util.EventObject;

// =============================================================================
// AUFGABE 2: CircleEvent
// =============================================================================
// Schreibe eine CircleEvent-Klasse die von EventObject erbt:
// - Ein Enum "Kind" mit Werten: ADDED, REMOVED, CHANGED
// - private Attribute: kind (Kind), affectedCircle (Circle)
// - Konstruktor: CircleEvent(Object source, Kind kind, Circle affectedCircle)
// - Getter: getKind(), getAffectedCircle()
// - getSource() ist schon von EventObject geerbt!

// DEINE LÖSUNG:

class CircleEvent extends EventObject{
public enum Kind{ADDED, REMOVED, CHANGED}

private Kind Kind;
private Circle affectedCircle;

public CircleEvent(Object source, Kind kind, Circle circle){
super(source);
this.kind = Kind;
this.affectedCircle = circle;

}

public Kind getKind(){return this.kind;}
public void setKind(Kind k){this.kind = k;}

public Circle getCircle(){return affectedCircle;}
public void setCircle(Circle c){this.affectedCircle = c;}


}
