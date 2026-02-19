# Lab 3: Interaktivität & Architektur (Das Gehirn der App)

Bisher ist unsere App "dumm". Sie zeigt nur statische Dummy-Daten an. In diesem Lab bringen wir der App bei, sich Zustände zu merken (Favoriten) und diese Änderungen sauber durch eine moderne Architektur (MVVM) fließen zu lassen.

**Zielzeit:** ca. 90-120 Minuten
> **Hilfe:** Theorie und Syntax finden Sie im [📘 Handout](HANDOUT.md).

### 1. Vorbereitung
Stellen Sie sicher, dass Sie startklar sind:
* **Option A (Eigener Code):** Bleiben Sie auf Ihrem aktuellen Branch, wenn Sie die vorherige Aufgabe erfolgreich abgeschlossen haben.
* **Option B (Sicherheitsnetz):** Wechseln Sie auf den Branch `lab-3-architecture`, wenn Sie unsere Musterlösung als Startpunkt nutzen möchten (`git checkout lab-3-architecture`).

---

### 2. Die Aufgaben

#### Schritt 1: Das Datenmodell erweitern
Wir möchten Charaktere als Favoriten markieren können.
* Öffnen Sie Ihre `Character` Data Class.
* Fügen Sie eine neue Eigenschaft `isFavorite` (Typ `Boolean`) hinzu.
* Geben Sie der Eigenschaft den Standardwert `false`.

#### Schritt 2: Das ViewModel erstellen
Wir trennen die Logik von der UI.
* Erstellen Sie eine neue Klasse `CharacterViewModel`, die von `ViewModel` erbt.
* Legen Sie darin einen internen, veränderbaren Zustand (`MutableStateFlow`) an, der eine Liste von Charakteren hält. Initialisieren Sie diesen mit Ihren Dummy-Daten.
* Exponieren Sie diesen Zustand zusätzlich als öffentlichen, unveränderbaren `StateFlow` (read-only für die UI).
> Theorie und Syntax finden Sie in [📘 Modul 7.1 - Die Architektur MVVM](HANDOUT.md#71-die-architektur-mvvm) und [📘 Modul 7.3 - StateFlow](HANDOUT.md#73-stateflow-der-state-container)

#### Schritt 3: Die Logik (Favoriten umschalten)
Das ViewModel muss in der Lage sein, Daten zu ändern.
* Schreiben Sie im ViewModel eine Funktion `toggleFavorite(characterId: Int)`.
* Diese Funktion soll den Charakter mit der passenden ID in der Liste suchen, dessen `isFavorite` Status umkehren und die Liste im `StateFlow` aktualisieren.
* *Tipp für Kotlin:* Da Data Classes unveränderlich (`val`) sind, iterieren Sie durch die Liste (z.B. mit `.map { ... }`), prüfen Sie die ID, und nutzen Sie `.copy(isFavorite = !it.isFavorite)` für den getroffenen Charakter, um eine neue Liste zu erzeugen.

#### Schritt 4: State Hoisting im Item
Unser `CharacterItem` muss nun ein klickbares Herz anzeigen, darf seinen Zustand aber nicht selbst verwalten.
* Fügen Sie der `CharacterItem` Funktion einen neuen Parameter hinzu: Ein Event-Lambda namens `onFavoriteClick`, das aufgerufen wird, wenn das Herz geklickt wird.
* Fügen Sie im Layout (z.B. rechts neben Name/Status) ein `Icon`-Composable ein.
* Machen Sie das Icon über den Modifier klickbar (`clickable`) und rufen Sie dort Ihr neues Lambda auf.
* Ändern Sie das Aussehen des Icons basierend auf `character.isFavorite` (z.B. ein gefülltes Herz vs. ein leeres Herz). Färben Sie ein aktives Herz farbig ein (z.B. Rot).
> Theorie und Syntax finden Sie in [📘 Modul 6.3 - State Hoisting](HANDOUT.md#63-state-hoisting-teile-und-herrsche)

#### Schritt 5: Die UI verdrahten
Nun bringen wir Screen, Item und ViewModel zusammen.
* Öffnen Sie Ihren `CharacterListScreen`.
* Erstellen Sie das ViewModel als Parameter der Screen-Funktion (nutzen Sie die Compose-Funktion `viewModel()`).
* Wandeln Sie den `StateFlow` des ViewModels in einen Compose-State um, damit die UI auf Änderungen reagiert.
* Übergeben Sie die Liste aus dem nun beobachteten State an Ihre `LazyColumn`.
* Reichen Sie bei jedem `CharacterItem` in der Liste das `onFavoriteClick`-Event an das ViewModel durch (`viewModel.toggleFavorite(id)`).
> Theorie und Syntax finden Sie in [📘 Modul 7.4 - Side Effects & State konsumieren](HANDOUT.md#74-side-effects-launchedeffect)

#### Schritt 6: Previews anpassen
Ihre Previews benötigen nun Dummy-Werte für die neuen Parameter (z.B. ein leeres Lambda `{ }` für Events). Passen Sie diese an, damit Android Studio keine Fehler anzeigt.

---

### 3. Akzeptanzkriterien (Definition of Done)
- [ ] Jedes Element in der Liste zeigt ein Herz-Icon.
- [ ] Wenn man auf das Herz klickt, füllt es sich bzw. wird wieder leer.
- [ ] Die UI (`CharacterItem`) verwaltet keinen eigenen State (`remember` wird hier *nicht* für den Favoriten-Status genutzt).
- [ ] **Der Härtetest:** Markieren Sie ein Element als Favorit. Drehen Sie Ihr Gerät (oder den Emulator) ins Querformat. Der Favoriten-Status muss erhalten bleiben! (Das beweist, dass das ViewModel greift).

---

### 4. Abschluss & Nächster Schritt

Sie haben nun zwei Möglichkeiten, wie Sie im nächsten Lab weitermachen:

**Option A: Mit dem eigenen Code weiterarbeiten (Empfohlen)**
Wenn bei Ihnen alles funktioniert und der "Härtetest" (Bildschirm drehen) erfolgreich war, bleiben Sie auf Ihrem Branch. Committen Sie Ihre Arbeit.

**Option B: Das Sicherheitsnetz nutzen**
Wenn etwas nicht klappt (z.B. der Klick nicht reagiert) oder Sie das ViewModel überspringen wollen, wechseln Sie auf unseren Lösungs-Branch.
Verwerfen oder stashen Sie Ihre unfertigen Änderungen und führen Sie aus:
```git
git checkout lab-4-networking
```

Dieser Branch enthält das fertige ViewModel und ist die Basis für das Anbinden echter Daten aus dem Internet.