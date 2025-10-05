// =============================================================================
// ÜBUNG 2: STREAMS mit flatMap() (25 Punkte Klausur-Aufgabe!)
// =============================================================================
// ZIEL: Verstehe flatMap(), split(), limit(), anyMatch()
//
// AUFGABENSTELLUNG:
// Arbeite mit einer Liste von Sätzen und extrahiere/analysiere Wörter
// =============================================================================

import java.util.*;
import java.util.stream.*;

public class Uebung2_Streams_FlatMap {

    // Gegeben: Liste von Sätzen
    static List<String> sentences = Arrays.asList(
        "Java ist eine tolle Programmiersprache.",
        "Streams machen das Leben einfacher!",
        "Java Streams sind sehr mächtig.",
        "Lerne Java für die Klausur.",
        "Viel Erfolg bei der Klausur!"
    );

    // =============================================================================
    // AUFGABE 2a: Alle Wörter extrahieren (8 Punkte)
    // =============================================================================
    // Erstelle getAllWords(List<String> sentences) die eine Liste mit ALLEN Wörtern
    // aus allen Sätzen zurückgibt (lowercase, ohne Satzzeichen).

    // ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
    // - flatMap() um Sätze → Wörter zu machen
    // - split("\\s+") für Wörter trennen
    // - replaceAll("[.!?,-]", "") für Satzzeichen entfernen
    // - toLowerCase()
    // - collect(Collectors.toList())
    // ▲▲▲ TIPPS ▲▲▲

    public static List<String> getAllWords(List<String> sentences) {
        // DEINE LÖSUNG:
        return sentences.stream()
            .flatMap(l -> Arrays.stream(l.split("\\s+")))
            .map(w -> w.replaceAll("[.!?,-]", ""))
            .map(w -> w.toLowerCase())
            .collect(Collectors.toList());
            
    }


    // =============================================================================
    // AUFGABE 2b: Erste 10 Wörter (5 Punkte)
    // =============================================================================
    // Erstelle getFirst10Words(List<String> sentences) die nur die ersten 10 Wörter zurückgibt.

    // ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
    // - Wie getAllWords() aber mit limit(10)
    // ▲▲▲ TIPPS ▲▲▲

    public static List<String> getFirst10Words(List<String> sentences) {
        // DEINE LÖSUNG:
        return sentences.stream()
            .flatMap(l -> Arrays.stream(l.split("\\s+")))
            .map(w -> w.replaceAll("[!?.,-]",""))
            .limit(10)
            .collect(Collectors.toList());
    }


    // =============================================================================
    // AUFGABE 2c: Wörter mit Index ausgeben (4 Punkte)
    // =============================================================================
    // Erstelle printWordsWithIndex(List<String> words) die jedes Wort mit Index ausgibt.
    // Format: "0: wort1", "1: wort2", ...

    // ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
    // - for-Schleife mit Index i
    // ▲▲▲ TIPPS ▲▲▲

    public static void printWordsWithIndex(List<String> words) {
        // DEINE LÖSUNG:
        for(int i = 0; i< words.size(); i++){
            System.out.print(i+": "+words.get(i));
            if(i<words.size()-1){
                System.out.print(", ");
            }
        }
    }


    // =============================================================================
    // AUFGABE 2d: Satz mit 2 Wörtern finden (5 Punkte)
    // =============================================================================
    // Erstelle containsBothWords(List<String> sentences, String word1, String word2)
    // Gibt true zurück wenn mindestens EIN Satz beide Wörter enthält (case-insensitive).

    // ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
    // - anyMatch() verwenden
    // - s.toLowerCase().contains(word1) && s.toLowerCase().contains(word2)
    // ▲▲▲ TIPPS ▲▲▲

    public static boolean containsBothWords(List<String> sentences, String word1, String word2) {
        // DEINE LÖSUNG:
        return sentences.stream()
            .anyMatch(l -> l.contains(word1)&&l.contains(word2));
    }


    // =============================================================================
    // AUFGABE 2e: Wort zählen (3 Punkte)
    // =============================================================================
    // Erstelle countWord(List<String> sentences, String word)
    // Zählt wie oft das Wort in allen Sätzen vorkommt (case-insensitive).

    // ▼▼▼ TIPPS (aufklappen bei Bedarf) ▼▼▼
    // - flatMap() für Wörter extrahieren
    // - filter() für das gesuchte Wort
    // - count()
    // ▲▲▲ TIPPS ▲▲▲

    public static long countWord(List<String> sentences, String word) {
        // DEINE LÖSUNG:
        return sentences.stream()
            .flatMap(l -> Arrays.stream(l.split("\\s+")))
            .map(String::toLowerCase)
            .filter(w -> w.equals(word.toLowerCase()))
            .count();
    }


    // =============================================================================
    // MAIN zum Testen
    // =============================================================================
    public static void main(String[] args) {
        System.out.println("=== AUFGABE 2a: Alle Wörter ===");
        List<String> allWords = getAllWords(sentences);
        System.out.println(allWords);
        // Erwartet: [java, ist, eine, tolle, programmiersprache, streams, ...]

        System.out.println("\n=== AUFGABE 2b: Erste 10 Wörter ===");
        List<String> first10 = getFirst10Words(sentences);
        System.out.println(first10);
        // Erwartet: [java, ist, eine, tolle, programmiersprache, streams, machen, das, leben, einfacher]

        System.out.println("\n=== AUFGABE 2c: Mit Index ===");
        printWordsWithIndex(first10);
        // Erwartet:
        // 0: java
        // 1: ist
        // 2: eine
        // ...

        System.out.println("\n=== AUFGABE 2d: Beide Wörter? ===");
        boolean hasJavaStreams = containsBothWords(sentences, "java", "streams");
        System.out.println("Hat 'java' und 'streams': " + hasJavaStreams);
        // Erwartet: true

        boolean hasTestPython = containsBothWords(sentences, "test", "python");
        System.out.println("Hat 'test' und 'python': " + hasTestPython);
        // Erwartet: false

        System.out.println("\n=== AUFGABE 2e: Wort zählen ===");
        long javaCount = countWord(sentences, "java");
        System.out.println("'java' kommt vor: " + javaCount + " mal");
        // Erwartet: 3

        long klausurCount = countWord(sentences, "klausur");
        System.out.println("'klausur' kommt vor: " + klausurCount + " mal");
        // Erwartet: 2
    }
}


// =============================================================================
// CHECKLISTE - HAST DU ALLES?
// =============================================================================
// □ getAllWords() verwendet flatMap()
// □ split("\\s+") zum Wörter trennen
// □ replaceAll("[.!?,-]", "") für Satzzeichen
// □ toLowerCase() für case-insensitive
// □ limit(10) für erste 10 Wörter
// □ anyMatch() für boolean zurückgeben
// □ count() für Anzahl
// □ collect(Collectors.toList()) am Ende
//
// HÄUFIGE FEHLER:
// ✗ map() statt flatMap() → gibt Stream<String[]> zurück!
// ✗ collect() vergessen
// ✗ split() ohne \\s+ (nur " ")
// ✗ anyMatch() ohne && für beide Wörter
