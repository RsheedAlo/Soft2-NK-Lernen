DESIGN PATTERNS ÜBERSICHT
=========================
(Aus: Zusammenfassung_Soft2_DesignPatterns.pdf - 31 Seiten)

1. ABSTRACT FACTORY ★★★☆☆
   - Familien von verwandten Objekten
   - Factory für Factories
   - Beispiel: UI-Komponenten für verschiedene OS

2. ADAPTER ★★★★☆
   - Inkompatible Interfaces verbinden
   - Wrapper-Klasse
   - Beispiel: Legacy-Code einbinden

3. COMPOSITE ★★★★★
   - Baumstrukturen (Part-Whole Hierarchie)
   - Component, Leaf, Composite
   - Beispiel: Dateisystem, GUI-Komponenten
   - SEHR KLAUSURRELEVANT!

4. DECORATOR ★★★★☆
   - Funktionalität dynamisch hinzufügen
   - Wrapper ohne Subclassing
   - Beispiel: InputStream, OutputStream

5. FACTORY METHOD ★★★☆☆
   - Objekt-Erzeugung in Subklassen
   - Flexible Instanziierung
   - Beispiel: Document-Framework

6. MVC (Model-View-Controller) ★★★★★
   - Trennung: Daten, Darstellung, Steuerung
   - Observer für View-Updates
   - SEHR WICHTIG FÜR KLAUSUR!

7. OBSERVER ★★★★★
   - 1:n Abhängigkeit zwischen Objekten
   - Subject + Observer Interface
   - Beispiel: Event-Handling, MVC
   - KERNPATTERN - IMMER LERNEN!

8. SINGLETON ★★★☆☆
   - Nur eine Instanz
   - Private Constructor
   - Beispiel: Logger, Configuration

9. VISITOR ★★★★☆
   - Operationen auf Objektstruktur
   - Double Dispatch
   - Beispiel: AST-Traversierung
   - Komplex aber klausurrelevant!

KLAUSUR-RANKING:
1. Observer ★★★★★
2. MVC ★★★★★
3. Composite ★★★★★
4. Visitor ★★★★☆
5. Adapter ★★★★☆
6. Decorator ★★★★☆

LERN-REIHENFOLGE:
1. Observer (Basis für MVC)
2. MVC (Aufbaut auf Observer)
3. Composite (Häufig in Klausuren)
4. Visitor (Komplex aber wichtig)
5. Adapter & Decorator (Ähnliche Konzepte)
