# Lab 2: Die dynamische Liste & Theming

In diesem Lab machen wir aus dem einzelnen, statischen Layout eine echte, scrollbare Liste. Wir führen ein Datenmodell ein, nutzen Material-Design-Theming für eine konsistente Optik und laden echte Bilder aus dem Internet.

**Zielzeit:** ca. 60-90 Minuten
> **Hilfe:** Theorie und Syntax finden Sie im [📘 Handout](HANDOUT.md).

### 1. Vorbereitung
Stellen Sie sicher, dass Sie startklar sind:
* **Option A (Eigener Code):** Bleiben Sie auf Ihrem aktuellen Branch, wenn Sie die vorherige Aufgabe erfolgreich abgeschlossen haben.
* **Option B (Sicherheitsnetz):** Wechseln Sie auf den Branch `lab-2-lists`, wenn Sie unsere Musterlösung als Startpunkt nutzen möchten (`git checkout lab-2-lists`).

---

### 2. Die Aufgaben

#### Schritt 1: Das Datenmodell & Dummy-Daten
Bevor wir Listen bauen können, brauchen wir Daten.
* Erstellen Sie eine neue Kotlin-Datei `Character.kt`.
* Erstellen Sie darin eine Kotlin `data class` namens `Character`.
* Fügen Sie folgende Eigenschaften (Properties) hinzu: `id` (Int), `name` (String), `status` (String) und `imageUrl` (String).
* Erstellen Sie in der gleichen Datei eine Hilfsfunktion `getDummyCharacters()`, die eine Liste (`List<Character>`) mit ca. 5-10 ausgedachten Einträgen zurückgibt. Nutzen Sie für die `imageUrl` echte Bild-Links aus dem Internet (z.B. `https://rickandmortyapi.com/api/character/avatar/1.jpeg`).
> Theorie und Syntax finden Sie in [📘 Modul 3.1 - Datenmodellierung](HANDOUT.md#31-datenmodellierung-die-data-class)

#### Schritt 2: Das Item aufrüsten (Parameter & Card)
Unser `CharacterItem` soll nun echte Daten anzeigen und besser aussehen.
* Fügen Sie der Funktion `CharacterItem` einen Parameter vom Typ `Character` hinzu.
* Ersetzen Sie die harten Strings ("Rick Sanchez", "Alive") durch die Variablen aus dem übergebenen Charakter-Objekt.
* Umhüllen Sie die bestehende `Row` mit einer `Card`.
    * Verschieben Sie die Modifier (wie die Breitenangabe) von der `Row` auf die `Card`.
    * Geben Sie der `Row` *innerhalb* der Card ein eigenes Innen-Padding (`16.dp`).
> Theorie und Syntax finden Sie in [📘 Modul 4.3 - Cards](HANDOUT.md#43-cards-inhalte-gruppieren)

#### Schritt 3: Theming anwenden (Weg mit harten Werten!)
Wir wollen konsistente Schriften nutzen, die auch im Dark Mode funktionieren.
* Passen Sie die `Text`-Elemente in Ihrem `CharacterItem` an.
* Entfernen Sie die festen Schriftgrößen (wie `20.sp` oder `14.sp`).
* Weisen Sie dem Namen einen Schriftstil aus dem `MaterialTheme` zu (nutzen Sie z.B. `MaterialTheme.typography.titleLarge`).
* Weisen Sie dem Status ebenfalls einen passenden Stil zu (z.B. `bodyMedium`).
> Theorie und Syntax finden Sie in [📘 Modul 4.1 - Theming](HANDOUT.md#41-theming-konsistentes-design--dark-mode)

#### Schritt 4: Echte Bilder laden (Coil)
Der blaue Kasten hat ausgedient. Wir wollen das Bild aus der URL laden.

> **Voraussetzung:** Für diesen Schritt benötigen Sie die **Coil**-Bibliothek. Diese ist noch nicht im Projekt enthalten. Fügen Sie die Versionen und Libraries in `gradle/libs.versions.toml` hinzu und binden Sie `coil-compose` sowie `coil-network-okhttp` in der `app/build.gradle.kts` ein. Die genauen Einträge finden Sie im [📘 Anhang A](HANDOUT.md#anhang-a-setup--dependencies-modern-way).

* Ersetzen Sie die `Box` durch die `AsyncImage` Composable aus der Coil-Bibliothek.
* Übergeben Sie als `model` die `imageUrl` des Charakters.
* Wenden Sie Modifier an, um die Größe auf `80.dp` zu belassen und das Bild kreisrund zuzuschneiden.
> Theorie und Syntax finden Sie in [📘 Modul 5 - Bilder laden](HANDOUT.md#modul-5-bilder-laden-images--networking-basics)

#### Schritt 5: Der Listen-Screen (Scaffold & LazyColumn)
Jetzt bauen wir den eigentlichen Bildschirm.
* Erstellen Sie eine neue Datei `CharacterListScreen.kt` und darin eine `@Composable` Funktion `CharacterListScreen`.
* Nutzen Sie ein `Scaffold` als Grundgerüst. Fügen Sie der TopBar einen Titel hinzu (z.B. "Rick & Morty Guide").
* Der Inhalt des Scaffolds soll eine `LazyColumn` sein.
    * Konfigurieren Sie die Liste so, dass zwischen den einzelnen Listenelementen ein Abstand von `16.dp` herrscht.
    * Nutzen Sie die Eigenschaften der Liste, um sicherzustellen, dass das erste und das letzte Element einen Außenabstand nach oben und unten haben.
* Lassen Sie die `LazyColumn` über Ihre Dummy-Daten iterieren und rufen Sie für jedes Element Ihr aktualisiertes `CharacterItem` auf.
> Theorie und Syntax finden Sie in [📘 Modul 4.2 - Scaffold](HANDOUT.md#42-die-app-struktur-scaffold) und [📘 Modul 3.1 - LazyColumn](HANDOUT.md#31-lazycolumn-listen-performant-anzeigen)

#### Schritt 6: Previews reparieren & Theme anwenden
Da Ihr `CharacterItem` nun einen Parameter verlangt, wird Ihre alte `@Preview` einen Fehler werfen.
* Übergeben Sie in der Preview ein einzelnes Dummy-Objekt an das Item.
* Erstellen Sie eine zusätzliche `@Preview` für den gesamten `CharacterListScreen`.
* **Wichtig:** Wickeln Sie den Aufruf in Ihren Previews in Ihr App-Theme ein (z.B. `RickAndMortyTheme { ... }`), damit die neuen Typografie-Stile korrekt dargestellt werden!

---

### 3. Akzeptanzkriterien (Definition of Done)
- [ ] Es existiert eine Datenklasse `Character` mit Dummy-Daten.
- [ ] Die Preview des ListScreens zeigt eine scrollbare Liste an.
- [ ] Ganz oben auf dem Bildschirm ist eine Kopfzeile (TopBar) zu sehen.
- [ ] Jeder Eintrag sieht wie eine abgehobene Karte (`Card`) aus.
- [ ] Die Texte nutzen das `MaterialTheme` (keine harten `.sp` Werte mehr).
- [ ] Statt blauen Vierecken werden runde Bilder aus dem Internet geladen.
- [ ] Die Listenelemente haben exakt `16.dp` Abstand zueinander.
- [ ] Die Liste beginnt und endet mit einem Abstand zum Rand.

---

### 4. Abschluss & Nächster Schritt

Sie haben nun zwei Möglichkeiten, wie Sie im nächsten Lab weitermachen:

**Option A: Mit dem eigenen Code weiterarbeiten (Empfohlen)**
Wenn bei Ihnen alles funktioniert und die Liste wie gewünscht aussieht, bleiben Sie einfach auf Ihrem aktuellen Branch. Sie bauen im nächsten Lab direkt auf Ihrem eigenen Code auf! Committen Sie Ihre Änderungen.

**Option B: Das Sicherheitsnetz nutzen**
Wenn Sie feststecken, Bugs haben oder einfach mit einem garantiert fehlerfreien Stand in das nächste Thema starten möchten, wechseln Sie auf unseren Lösungs-Branch.
Verwerfen oder stashen Sie in diesem Fall Ihre unfertigen Änderungen und führen Sie aus:

```
git checkout lab-3-architecture
```

Dieser Branch enthält die perfekte Ausgangslage für das kommende Lab (Zustandsverwaltung & MVVM).