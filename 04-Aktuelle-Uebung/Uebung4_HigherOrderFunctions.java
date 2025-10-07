// =============================================================================
// ÜBUNG 4: HIGHER ORDER FUNCTIONS (Teil der letzten Klausur!)
// =============================================================================
// ZIEL: Verstehe Function<T,R>, Consumer, Predicate und doForEach()
//
// HINTERGRUND:
// In der letzten Klausur kam doForEach(Function<String, String> action) vor!
// Higher Order Functions = Funktionen die Funktionen als Parameter nehmen
//
// WICHTIGSTE KONZEPTE:
// - Function<T, R>: nimmt T, gibt R zurück
// - Consumer<T>: nimmt T, gibt nichts zurück (void)
// - Predicate<T>: nimmt T, gibt boolean zurück
// - action.apply() aufrufen für Function
// - action.accept() aufrufen für Consumer
// =============================================================================

import java.util.*;
import java.util.function.*;

// =============================================================================
// TEIL 1: FUNCTION<T, R> - doForEach Implementation
// =============================================================================

// =============================================================================
// AUFGABE 4.1a: TextProcessor mit doForEach (10 Punkte) ⚠️ WICHTIG!
// =============================================================================
// Erstelle TextProcessor der eine Function auf alle Wörter anwenden kann.
// Die Methode doForEach() nimmt Function<String,String> und transformiert jedes Wort.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - abstract class TextProcessor
// - protected List<String> words
// - Konstruktor: TextProcessor(List<String> words)
// - doForEach(Function<String, String> action):
//   → for-Schleife mit Index i
//   → String result = action.apply(words.get(i))
//   → words.set(i, result)  (zurückspeichern!)
// - printWords(): System.out.println(words)
// - getWords(): return words
// ▲▲▲ TIPPS ▲▲▲

abstract class TextProcessor {
    // DEINE LÖSUNG:
    protected List<String> words;

    public TextProcessor(List<String> words){
        this.words=words;
    }

    public List<String> getWords(){return words;}

    public void doForEach(Function<String, String> action){
        for(int i = 0; i<words.size(); i++){
            String res = action.apply(words.get(i));
            words.set(i, res);
        }
    }

    public void printWrods(){System.out.println(words);} //kannst du mir den Unterschied erklären zwischen das und mit einer for schleife durch...
}


// =============================================================================
// AUFGABE 4.1b: MyTextProcessor Implementation (2 Punkte)
// =============================================================================
// Erstelle konkrete Klasse die von TextProcessor erbt.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - class MyTextProcessor extends TextProcessor
// - Konstruktor ruft super(words) auf
// ▲▲▲ TIPPS ▲▲▲

class MyTextProcessor extends TextProcessor {
    // DEINE LÖSUNG:
    public MyTestProcessor(List<String> words){
        super(words);
    }


}


// =============================================================================
// AUFGABE 4.1c: Test mit verschiedenen Functions (5 Punkte)
// =============================================================================
// Teste doForEach() mit:
// 1. Methodenreferenz: String::toUpperCase
// 2. Lambda: s -> s + "!"
// 3. Lambda: s -> String.valueOf(s.length())

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// List<String> words = new ArrayList<>(Arrays.asList("hello", "world", "java"));
// MyTextProcessor proc = new MyTextProcessor(words);
//
// proc.doForEach(String::toUpperCase);
// proc.printWords();  // [HELLO, WORLD, JAVA]
//
// proc.doForEach(s -> s + "!");
// proc.printWords();  // [HELLO!, WORLD!, JAVA!]
//
// proc.doForEach(s -> String.valueOf(s.length()));
// proc.printWords();  // [6, 6, 5]
// ▲▲▲ TIPPS ▲▲▲

class TestFunctions {
    public static void main(String[] args) {
        // DEINE LÖSUNG:
        List<String> words = new ArrayList<>(Arrays.asList("hello", "world", "java"));
        TextProcessor tp = new MyTextProcessor(words);

        tp.doForEach(String::toUpperCase);
        tp.printWrods();

        tp.doForEach(s-> s+"!");
        tp.printWrods();

        tp.doForEach(s-> String.valueOf(s.length()));
        tp.printWrods();

    }
}


// =============================================================================
// TEIL 2: CONSUMER<T> - forEach Implementation
// =============================================================================

// =============================================================================
// AUFGABE 4.2a: MovieList mit forEach (8 Punkte)
// =============================================================================
// Erstelle MovieList die forEach(Consumer<String>) unterstützt.
// (Wie in der letzten Klausur!)

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// - class MovieList
// - private List<String> movies
// - Konstruktor initialisiert movies
// - addMovie(String movie): movies.add(movie)
// - forEach(Consumer<String> action):
//   → for-Schleife über movies
//   → action.accept(movie) für jedes Movie
//   → KEIN return! Consumer gibt void zurück
// ▲▲▲ TIPPS ▲▲▲

class MovieList {
    // DEINE LÖSUNG:
    private List<String> movies;

    public MovieList(){
        this.movies = new ArrayList<>();
    }

    public void addMovie(String movie){
        movies.add(movie);
    }

    public void forEach(Consumer<String> action){
        for(String m : movies){
            action.accept(m);
        }
    }
    public void doForEach(Predicate<String> pred, Consumer<String> con){
	for(String movie : movies){
		if(pred.test(movie)){
			con.accept(movie);
		}
	}

}
    
}


// =============================================================================
// AUFGABE 4.2b: Test forEach mit Consumer (4 Punkte)
// =============================================================================
// Teste forEach() mit verschiedenen Consumers:
// 1. Lambda: movie -> System.out.println(movie)
// 2. Lambda: movie -> System.out.println(movie.toUpperCase())
// 3. Lambda mit Filter: if(movie.length() > 7) print

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// MovieList list = new MovieList();
// list.addMovie("Inception");
// list.addMovie("Avatar");
// list.addMovie("Titanic");
//
// list.forEach(movie -> System.out.println(movie));
//
// list.forEach(movie -> {
//     if(movie.length() > 7) {
//         System.out.println(movie);
//     }
// });
// ▲▲▲ TIPPS ▲▲▲

class TestConsumers {
    public static void main(String[] args) {
        // DEINE LÖSUNG:

        MovieList list = new MovieList();

        list.add("suii");
        list.add("meeeesi");
        list.add("eyeliner");

        list.forEach(movie -> System.out.println(movie));
        list.forEach(movie -> System.out.println(movie.toUpperCase()));
        list.forEach(movie -> {
            if(movie.length()>7)
                System.out.println(movie);
        });



    }
}


// =============================================================================
// TEIL 3: PREDICATE<T> - doForEach mit Filter
// =============================================================================

// =============================================================================
// AUFGABE 4.3a: doForEach mit Predicate und Consumer (12 Punkte) ⚠️ WICHTIG!
// =============================================================================
// Erweitere MovieList mit doForEach(Predicate, Consumer).
// Nur Movies die das Predicate erfüllen werden an Consumer übergeben!
// (Genau wie in der letzten Klausur!)

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// public void doForEach(Predicate<String> pred, Consumer<String> consumer) {
//     for(String movie : movies) {
//         if(pred.test(movie)) {  // ← Predicate testen!
//             consumer.accept(movie);  // ← Consumer aufrufen
//         }
//     }
// }
// ▲▲▲ TIPPS ▲▲▲

// → Füge diese Methode zu MovieList hinzu (oben)!


// =============================================================================
// AUFGABE 4.3b: Test doForEach mit Filter (5 Punkte)
// =============================================================================
// Teste doForEach(Predicate, Consumer):
// - Predicate: movie.length() > 7
// - Consumer: System.out.println(movie)

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// list.doForEach(
//     movie -> movie.length() > 7,           // Predicate
//     movie -> System.out.println(movie)     // Consumer
// );
//
// Oder mit Methodenreferenz:
// list.doForEach(
//     movie -> movie.length() > 7,
//     System.out::println
// );
// ▲▲▲ TIPPS ▲▲▲

class TestPredicates {
    public static void main(String[] args) {
        // DEINE LÖSUNG:
        Consumer<String> con = System.out::println;
        Predicate<String> pred = m -> m.length>7;

        MovieList list = new MovieList();
        list.add("Inception");
        list.add("Avatar");
        list.add("Titanic");

        list.doForEach(pred, con);
    }
}


// =============================================================================
// TEIL 4: ALLE FUNCTIONAL INTERFACES ZUSAMMEN
// =============================================================================

// =============================================================================
// AUFGABE 4.4: Supplier<T> - Bonus (3 Punkte)
// =============================================================================
// Supplier<T> gibt T zurück OHNE Parameter.

// ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
// Supplier<String> randomMovie = () -> {
//     String[] movies = {"Inception", "Avatar", "Titanic"};
//     return movies[(int)(Math.random() * movies.length)];
// };
//
// System.out.println(randomMovie.get());  // Zufälliger Film
// System.out.println(randomMovie.get());  // Anderer Film
// ▲▲▲ TIPPS ▲▲▲

class TestSupplier {
    public static void main(String[] args) {
        // DEINE LÖSUNG:

    }
}


// =============================================================================
// ERKLÄRUNG: FUNCTIONAL INTERFACES ÜBERSICHT
// =============================================================================
//
// 1. FUNCTION<T, R>
//    - Signatur: R apply(T t)
//    - Nimmt: T
//    - Gibt zurück: R
//    - Beispiel: Function<String, Integer> length = String::length
//    - Verwendung: int len = length.apply("Hello")  // 5
//
// 2. CONSUMER<T>
//    - Signatur: void accept(T t)
//    - Nimmt: T
//    - Gibt zurück: NICHTS (void)
//    - Beispiel: Consumer<String> print = System.out::println
//    - Verwendung: print.accept("Hello")  // Gibt "Hello" aus
//
// 3. PREDICATE<T>
//    - Signatur: boolean test(T t)
//    - Nimmt: T
//    - Gibt zurück: boolean
//    - Beispiel: Predicate<String> isEmpty = String::isEmpty
//    - Verwendung: boolean empty = isEmpty.test("")  // true
//
// 4. SUPPLIER<T>
//    - Signatur: T get()
//    - Nimmt: NICHTS
//    - Gibt zurück: T
//    - Beispiel: Supplier<Double> random = Math::random
//    - Verwendung: double r = random.get()  // 0.123...
//
// =============================================================================


// =============================================================================
// LAMBDA VS. METHODENREFERENZ
// =============================================================================
//
// LAMBDA-AUSDRUCK:
// Function<String, String> upper = s -> s.toUpperCase();
//
// METHODENREFERENZ (kürzer):
// Function<String, String> upper = String::toUpperCase;
//
// WANN METHODENREFERENZ?
// - Wenn Lambda nur EINE Methode aufruft
// - Kein zusätzlicher Code
//
// BEISPIELE:
// s -> s.toUpperCase()        → String::toUpperCase
// s -> System.out.println(s)  → System.out::println
// s -> s.length()             → String::length
//
// WANN LAMBDA?
// - Wenn mehr als eine Operation
// - z.B.: s -> s.toUpperCase() + "!"
//
// =============================================================================


// =============================================================================
// CHECKLISTE FÜR KLAUSUR
// =============================================================================
// □ Function<T,R>: action.apply(t) aufrufen?
// □ Consumer<T>: action.accept(t) aufrufen?
// □ Predicate<T>: pred.test(t) aufrufen?
// □ Supplier<T>: supplier.get() aufrufen?
// □ doForEach(): Ergebnis zurückspeichern bei Function?
// □ forEach(): KEIN return bei Consumer?
// □ Lambda-Syntax korrekt: (param) -> expression
// □ Methodenreferenz: Class::method
//
// HÄUFIGE FEHLER:
// ✗ action.apply() vergessen bei Function
// ✗ Ergebnis nicht zurückspeichern: words.set(i, result)
// ✗ Lambda falsch: s => s.toUpperCase() (FALSCH, muss -> sein!)
// ✗ Methodenreferenz falsch: String:toUpperCase (nur EIN :)
// ✗ Consumer soll return haben (NEIN, void!)
// ✗ Predicate ohne test(): if(pred(movie)) → FALSCH, muss pred.test(movie)
//
// =============================================================================


// =============================================================================
// TYPISCHE KLAUSUR-AUFGABE
// =============================================================================
//
// AUFGABE (wie in letzter Klausur):
// "Implementiere doForEach(Function<String, String> action) in einer
//  abstrakten Klasse TextProcessor. Die Methode soll die Funktion auf
//  alle Wörter anwenden und das Ergebnis zurückspeichern."
//
// LÖSUNG:
// public void doForEach(Function<String, String> action) {
//     for(int i = 0; i < words.size(); i++) {
//         String result = action.apply(words.get(i));
//         words.set(i, result);
//     }
// }
//
// VERWENDUNG:
// processor.doForEach(String::toUpperCase);
//
// =============================================================================


// =============================================================================
// EIGENES FUNCTIONAL INTERFACE ERSTELLEN
// =============================================================================
//
// Du kannst dein eigenes FunctionalInterface erstellen mit @FunctionalInterface!
// Wichtig: GENAU EINE abstrakte Methode!
//
// BEISPIEL 1: StringTransformer (wie Function<String, String>)
// -----------------------------------------------------------------------------
@FunctionalInterface
interface StringTransformer {
    String transform(String input);  // ← Genau eine abstrakte Methode!
}

// VERWENDUNG:
// StringTransformer upper = s -> s.toUpperCase();
// String result = upper.transform("hello");  // "HELLO"
//
// StringTransformer exclaim = s -> s + "!!!";
// String result2 = exclaim.transform("wow");  // "wow!!!"
//
// -----------------------------------------------------------------------------
//
// BEISPIEL 2: NumberValidator (wie Predicate<Integer>)
// -----------------------------------------------------------------------------
@FunctionalInterface
interface NumberValidator {
    boolean isValid(int number);  // ← Genau eine abstrakte Methode!
}

// VERWENDUNG:
// NumberValidator isEven = n -> n % 2 == 0;
// boolean result = isEven.isValid(4);  // true
//
// NumberValidator isPositive = n -> n > 0;
// boolean result2 = isPositive.isValid(-5);  // false
//
// -----------------------------------------------------------------------------
//
// BEISPIEL 3: MathOperation (zwei Parameter!)
// -----------------------------------------------------------------------------
@FunctionalInterface
interface MathOperation {
    int calculate(int a, int b);  // ← Genau eine abstrakte Methode!
}

// VERWENDUNG:
// MathOperation add = (a, b) -> a + b;
// int sum = add.calculate(5, 3);  // 8
//
// MathOperation multiply = (a, b) -> a * b;
// int product = multiply.calculate(5, 3);  // 15
//
// -----------------------------------------------------------------------------
//
// BEISPIEL 4: Logger (wie Consumer<String>)
// -----------------------------------------------------------------------------
@FunctionalInterface
interface Logger {
    void log(String message);  // ← Genau eine abstrakte Methode!
}

// VERWENDUNG:
// Logger consoleLogger = msg -> System.out.println("[LOG] " + msg);
// consoleLogger.log("Application started");  // [LOG] Application started
//
// Logger errorLogger = msg -> System.err.println("[ERROR] " + msg);
// errorLogger.log("Something went wrong");  // [ERROR] Something went wrong
//
// -----------------------------------------------------------------------------
//
// WARUM EIGENE FUNCTIONAL INTERFACES?
// - Bessere Lesbarkeit: "StringTransformer" ist klarer als "Function<String,String>"
// - Domain-spezifisch: "NumberValidator" zeigt Intent besser als "Predicate<Integer>"
// - Custom-Methoden: Du kannst default/static Methoden hinzufügen!
//
// -----------------------------------------------------------------------------
//
// ADVANCED: Mit default-Methoden
// -----------------------------------------------------------------------------
@FunctionalInterface
interface StringProcessor {
    String process(String input);  // ← Abstrakte Methode

    // Default-Methode (optional):
    default String processAndPrint(String input) {
        String result = process(input);
        System.out.println(result);
        return result;
    }

    // Static-Methode (optional):
    static StringProcessor createUpperCase() {
        return s -> s.toUpperCase();
    }
}

// VERWENDUNG:
// StringProcessor proc = s -> s + "!";
// proc.processAndPrint("hello");  // Ausgabe: hello!
//
// StringProcessor upper = StringProcessor.createUpperCase();
// String result = upper.process("hello");  // "HELLO"
//
// -----------------------------------------------------------------------------
//
// REGELN FÜR @FunctionalInterface:
// ✓ GENAU EINE abstrakte Methode (SAM - Single Abstract Method)
// ✓ Beliebig viele default-Methoden
// ✓ Beliebig viele static-Methoden
// ✗ NICHT mehr als eine abstrakte Methode!
//
// =============================================================================
