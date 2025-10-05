// =============================================================================
// AUFGABE 1: Reine Daten-Klasse Circle
// =============================================================================
// Schreibe eine einfache Circle-Klasse mit:
// - private Attribute: radius (double), x (int), y (int)
// - Konstruktor mit allen 3 Parametern
// - Getter und Setter für alle Attribute

// DEINE LÖSUNG:

class Circle{
    private double radius;
    private int x;
    private int y;



    public Circle(double radius, int x, int y){
        this.radius = radius;
        this.x = x;
        this.y = y;

    }

    public double getRadius(){
        return radius;
    }
    public void setRadius(double radius){
        if(radius < 0){
            throw new IllegalArgumentException("Radius must be non-negative");
        }
        this.radius = radius;
    }

    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }   
}


