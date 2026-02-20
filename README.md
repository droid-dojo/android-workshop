# 🚀 Workshop: Modern Android Development (2026)

Herzlich willkommen zum Workshop! In den kommenden Tagen werden wir gemeinsam von Grund auf eine moderne, native Android-App entwickeln. Vergessen Sie Java und XML – wir nutzen ausschließlich den modernsten Tech-Stack, den Google aktuell empfiehlt.

## 📱 Das Projekt: Rick & Morty Guide
Wir bauen nicht nur trockene Beispiele, sondern eine voll funktionsfähige App: Den **Rick & Morty Character Guide**.

**Unsere Features:**
* Abrufen echter Daten von einer Live-API (REST).
* Darstellen einer performanten, scrollbaren Liste mit Bildern.
* Klickbare Favoriten mit sauberem State-Management.
* Typsichere Navigation zu einer Detailansicht.
* Professionelles Fehler-Handling (z.B. bei fehlendem Internet).

**Unser Tech-Stack:**
* **Sprache:** Kotlin (inklusive Coroutines)
* **UI:** 100% Jetpack Compose & Material Design 3
* **Architektur:** MVVM (Model-View-ViewModel) mit UDF (Unidirectional Data Flow) & StateFlow
* **Networking:** Retrofit & Kotlinx Serialization
* **Image Loading:** Coil

---

## 📘 Das Workshop-Handout
Alle theoretischen Konzepte, Code-Beispiele und Erklärungen zur Syntax finden Sie im beiliegenden Handout. **Bitte halten Sie diese Datei während des Workshops immer griffbereit!**

👉 **[Hier klicken, um das HANDOUT.md zu öffnen](HANDOUT.md)**

---

## 🔀 Wie dieses Repository funktioniert

Dieses Projekt ist in aufeinanderfolgende **Labs (Praxis-Aufgaben)** unterteilt. Jeder Schritt baut auf dem vorherigen auf.

Wir nutzen Git-Branches, um die verschiedenen Phasen des Workshops abzubilden. Sie haben bei jedem Lab zwei Möglichkeiten:
1. **Eigenen Code schreiben:** Sie arbeiten auf Ihrem aktuellen Branch und bauen Ihre eigene App Schritt für Schritt auf.
2. **Das Sicherheitsnetz nutzen:** Wenn Sie feststecken, können Sie jederzeit auf den Lösungs-Branch des nächsten Labs wechseln, um einen fehlerfreien Startpunkt zu haben.

---

## 🗺️ Inhaltsverzeichnis & Fahrplan

Hier finden Sie die Übersicht aller Aufgaben. Um mit dem Workshop zu starten, wechseln Sie bitte auf den ersten Branch!

### [Lab 1: Der visuelle Prototyp](https://github.com/droid-dojo/android-workshop/tree/lab-1-basics)
* **Start-Branch:** `git checkout lab-1-basics`
* **Inhalt:** Einführung in Jetpack Compose, Layouts (`Row`, `Column`, `Box`) und Modifier. Wir bauen eine statische Charakter-Karte.

### [Lab 2: Die dynamische Liste & Theming](https://github.com/droid-dojo/android-workshop/tree/lab-2-lists)
* **Start-Branch:** `git checkout lab-2-lists`
* **Inhalt:** Datenmodellierung, Material Design, Listen (`LazyColumn`) und das Laden von Bildern aus dem Internet mit Coil.

### [Lab 3: Interaktivität & Architektur (Das Gehirn)](https://github.com/droid-dojo/android-workshop/tree/lab-3-architecture)
* **Start-Branch:** `git checkout lab-3-architecture`
* **Inhalt:** Wir führen das `ViewModel` und `StateFlow` ein, lernen State Hoisting und machen unsere UI interaktiv (Favoriten-Herzen klicken).

### [Lab 4: Going Online (Networking & Coroutines)](https://github.com/droid-dojo/android-workshop/tree/lab-4-networking)
* **Start-Branch:** `git checkout lab-4-networking`
* **Inhalt:** Anbindung an die echte REST-API mittels Retrofit. Wir ersetzen Dummy-Daten durch echte JSON-Antworten und behandeln Lade- und Fehlerzustände.

### [Lab 5: Navigation & Detail Screen (Das Finale)](https://github.com/droid-dojo/android-workshop/tree/lab-5-navigation)
* **Start-Branch:** `git checkout lab-5-navigation`
* **Inhalt:** Einführung der modernen, typsicheren "Navigation Compose". Wir bauen einen Detail-Screen und übergeben Daten zwischen den Bildschirmen.

### [🏁 Lab 6: Die fertige App (Referenz)](https://github.com/droid-dojo/android-workshop/tree/lab-6-final)
* **Lösungs-Branch:** `git checkout lab-6-final`
* **Inhalt:** Der komplette, aufgeräumte und fehlerfreie Endstand des Workshops zum Nachschlagen für Ihre eigenen Projekte.

---

## 🚀 Los geht's!

Öffnen Sie Ihr Terminal in Android Studio (unten im Reiter "Terminal") und starten Sie mit dem ersten Lab:

```bash
git checkout lab-1-basics
```