# Lab 1: Der erste Layout-Entwurf

In diesem Lab erstellen Sie die Struktur für einen einzelnen Charakter. Wir nutzen primitive Layout-Bausteine, um Bild und Text anzuordnen.

**Zielzeit:** ca. 60-90 Minuten
**Hilfe:** Theorie und Syntax finden Sie im [📘 Handout](HANDOUT.md).

### 1. Vorbereitung
Stellen Sie sicher, dass Sie sich auf dem korrekten Branch befinden.
* **Aktueller Branch:** `lab-1-basics`

---

### 2. Die Aufgaben

#### Schritt 1: Datei anlegen
Erstellen Sie im Package `ninja.droiddojo.rickandmorty` eine neue Kotlin-Datei: `CharacterItem.kt`.

#### Schritt 2: Die Composable Funktion
Erstellen Sie eine Funktion `CharacterItem`, annotiert mit `@Composable`.

#### Schritt 3: Der Container (Row)
Wir benötigen eine horizontale Anordnung (Bild links, Text rechts).
* Nutzen Sie als äußerstes Element eine `Row`.
* Wenden Sie folgende Modifier auf die Row an:
    * Der Eintrag sollte die gesamte verfügbare Breite nutzen.
    * Geben Sie der Zeile vorübergehend eine Hintergrundfarbe (`Color.LightGray`), damit wir die Grenzen sehen.
    * Der Inhalt sollte einen Abstand zum Rand von `16.dp` haben.

#### Schritt 4: Der Bild-Platzhalter (Box)
Da wir noch keine Bilder laden, nutzen wir eine farbige Fläche.
* Erstellen Sie innerhalb der `Row` eine `Box`.
* Geben Sie der Box eine feste Größe: `80.dp`.
* Färben Sie die Box blau (`Color.Blue`), damit sie sich vom grauen Hintergrund abhebt.

#### Schritt 5: Die Text-Informationen (Column)
Rechts neben der Box sollen Name und Status untereinander stehen.
* Stellen Sie sicher, dass zwischen der Column und dem Bild-Container ein Abstand von `16.dp` besteht.
* Erstellen Sie danach eine `Column`.
* Fügen Sie in die Column zwei `Text`-Elemente ein:
    1.  Text: "Rick Sanchez".
        * Die Textgröße sollte `20.sp` betragen.
        * Der Text sollte **Fett** (`Bold`) dargestellt werden.
    2.  Text: "Status: Alive".
        * Die Textgröße sollte `14.sp` betragen.
        * Der Text sollte normal dargestellt werden.

#### Schritt 6: Die Vorschau
Erstellen Sie eine `@Preview` Funktion unterhalb Ihrer Komponente, um das Layout zu prüfen, ohne den Emulator zu starten.

---

### 3. Akzeptanzkriterien (Definition of Done)
- [ ] Die Datei `CharacterItem.kt` existiert.
- [ ] Die Preview zeigt einen grauen Balken über die volle Breite.
- [ ] Links befindet sich ein blaues Quadrat (80x80).
- [ ] Rechts daneben stehen Name und Status untereinander.
- [ ] Der Name hebt sich durch Fettdruck deutlich vom Status ab.

---

### 4. Abschluss & Nächster Schritt

Sie haben nun zwei Möglichkeiten, wie Sie im nächsten Lab weitermachen:

**Option A: Mit dem eigenen Code weiterarbeiten (Empfohlen)**
Wenn bei Ihnen alles funktioniert und die Akzeptanzkriterien erfüllt sind, bleiben Sie einfach auf Ihrem aktuellen Branch. Sie bauen im nächsten Lab direkt auf Ihrem eigenen Code auf! Committen Sie Ihre Änderungen.

**Option B: Das Sicherheitsnetz nutzen**
Wenn Sie feststecken, unlösbare Fehler haben oder einfach mit einem garantiert fehlerfreien Stand in das nächste Thema starten möchten, wechseln Sie auf unseren Lösungs-Branch.
Verwerfen oder stashen Sie in diesem Fall Ihre unfertigen Änderungen und führen Sie aus:

`git checkout lab-2-lists`

Dieser Branch enthält die perfekte Ausgangslage für das kommende Lab.