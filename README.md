# Lab 4: Going Online (Networking & Coroutines)

In diesem Lab verabschieden wir uns von den lokalen Dummy-Daten. Wir binden die App an die echte "Rick & Morty"-API an, verarbeiten asynchrone Netzwerkaufrufe mit Coroutines und gehen professionell mit Ladezeiten und Fehlern um.

**Zielzeit:** ca. 90-120 Minuten

### 1. Vorbereitung
Stellen Sie sicher, dass Sie startklar sind:
* **Option A (Eigener Code):** Bleiben Sie auf Ihrem aktuellen Branch, wenn Sie die vorherige Aufgabe erfolgreich abgeschlossen haben.
* **Option B (Sicherheitsnetz):** Wechseln Sie auf den Branch `lab-4-networking`, wenn Sie unsere Musterlösung als Startpunkt nutzen möchten (`git checkout lab-4-networking`).

---

> **Voraussetzung:** Dieses Lab benötigt **Retrofit**, **kotlinx-serialization** und das dazugehörige Gradle-Plugin, die noch nicht im Projekt enthalten sind. Fügen Sie die Versionen, Libraries und das Plugin in `gradle/libs.versions.toml` hinzu und binden Sie `retrofit`, `retrofit-converter-kotlinx-serialization` sowie `kotlinx-serialization-json` in der `app/build.gradle.kts` ein. Vergessen Sie nicht, das Serialization-Plugin (`alias(libs.plugins.kotlin.serialization)`) im `plugins`-Block zu laden! Außerdem braucht die App jetzt die `INTERNET`-Permission im `AndroidManifest.xml` (falls noch nicht aus Lab 2 vorhanden). Die genauen Einträge finden Sie im [📘 Anhang A](HANDOUT.md#anhang-a-setup--dependencies-modern-way).

### 2. Die Aufgaben

#### Schritt 1: Die API-Antwort modellieren (DTOs)
Die Rick & Morty API liefert uns JSON zurück. Wir benötigen Klassen, die exakt dieser JSON-Struktur entsprechen.
* Erstellen Sie eine neue Datei `CharacterDto.kt` (Data Transfer Object).
* Erstellen Sie eine `@Serializable` Data Class `CharacterDto` für einen einzelnen Charakter (Wichtige Felder laut API: `id`, `name`, `status`, `image`).
* Erstellen Sie eine zweite `@Serializable` Data Class `CharacterResponse`, welche die gesamte Antwort abbildet.
> **Tipp:** Die API liefert ein Objekt mit einer Liste namens `results`, in der die eigentlichen Charaktere liegen. Diese Liste müssen Sie in der `CharacterResponse` abbilden.
* Fügen Sie in dieser Datei eine Erweiterungsfunktion `fun CharacterDto.toDomain(): Character` hinzu, um das DTO (Netzwerk) später in unser sauberes App-Modell (aus Lab 2) umzuwandeln.
> **Tipp:** Beachten Sie beim Mapping, dass das API-Feld `image` in unserem Domain-Modell `imageUrl` heißt.
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 9.2 - JSON Parsing & Serialization](HANDOUT.md#92-json-parsing--serialization)

#### Schritt 2: Der API-Service (Retrofit)
Wir müssen definieren, wie unsere HTTP-Anfragen aussehen.
* Erstellen Sie ein Interface `RickAndMortyApi`.
* Definieren Sie darin eine asynchrone Funktion, um alle Charaktere abzurufen.
> **Tipp:** Nutzen Sie das Schlüsselwort `suspend`, damit die Funktion später in einer Coroutine ausgeführt werden kann, ohne die UI zu blockieren.
* Annotieren Sie diese Funktion für einen HTTP-GET-Request auf den Endpunkt `"character"`. Als Rückgabetyp erwarten wir unsere `CharacterResponse`.
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 9.1 - Retrofit](HANDOUT.md#91-retrofit-der-typ-sichere-http-client)

#### Schritt 3: Das Repository
Das ViewModel soll sich nicht direkt um HTTP-Requests kümmern. Wir bauen einen Vermittler.
* Erstellen Sie eine Klasse `CharacterRepository`.
* Übergeben Sie die `RickAndMortyApi` als Parameter im Konstruktor.
* Schreiben Sie eine asynchrone Funktion `getCharacters()`, welche die API aufruft, die `results` Liste nimmt, jedes DTO in unser Domain-Modell (`Character`) umwandelt und diese finale Liste zurückgibt.
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 10 - The Repository Pattern](HANDOUT.md#103-implementierung)

#### Schritt 4: Den Service-Locator (`Dependencies`) anlegen
Unser ViewModel wird gleich ein `CharacterRepository` brauchen — und das Repository wiederum eine `RickAndMortyApi`. Da `viewModel()` aber nur Konstruktoren ohne Argumente aufruft, brauchen wir eine zentrale Stelle, die diese Objekte einmal aufbaut und für uns bereithält.
Für den Workshop reicht ein simples Kotlin-`object` als Service Locator (vollwertige DI mit Hilt/Koin folgt in der Praxis später).
* Erstellen Sie eine neue Datei `Dependencies.kt` mit einem `object Dependencies`.
* Bauen Sie darin **einmalig** die `Json`-Konfiguration, den `Retrofit`-Builder (BaseUrl: `https://rickandmortyapi.com/api/`) und daraus die `RickAndMortyApi` (`retrofit.create<RickAndMortyApi>()` oder `retrofit.create(RickAndMortyApi::class.java)`).
* Stellen Sie eine `val characterRepository = CharacterRepository(rickAndMortyApi)` als öffentliche Property bereit.
> **Tipp:** Halten Sie Retrofit-Instanz und API als `private` — exponieren Sie nur das Repository nach außen.
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 9.3 - Setup: Retrofit trifft Serialization](HANDOUT.md#93-setup-retrofit-trifft-serialization)

#### Schritt 5: UI State definieren (Sealed Interface)
Wenn wir Daten aus dem Netz laden, brauchen wir mehr als nur eine Liste. Wir müssen wissen, ob wir gerade laden oder ob ein Fehler passiert ist.
* Erstellen Sie in (oder bei) der ViewModel-Datei ein `sealed interface UiState`.
* Definieren Sie drei Zustände: `Loading` (Object), `Success` (Data Class mit der Liste der Charaktere) und `Error` (Data Class mit einer Fehlermeldung als String).
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 7.2 - Der UI State](HANDOUT.md#72-der-ui-state-warum-eine-eigene-klasse)

#### Schritt 6: Das ViewModel umbauen
Jetzt verknüpfen wir die Logik und löschen die Dummy-Daten!
* Benennen Sie Ihre bisherige Klasse `CharacterViewModel` zu `CharacterListViewModel` um (das ViewModel gehört nun klar zum Listen-Screen — im nächsten Lab kommt ein zweites für den Detail-Screen dazu).
* Ersetzen Sie den alten `StateFlow` (der nur eine Liste hielt) durch einen, der unseren neuen `UiState` hält. Der Startwert sollte `UiState.Loading` sein.
* Entfernen Sie die initiale Dummy-Liste.
* Holen Sie sich das Repository **innerhalb des ViewModels** aus dem `Dependencies`-Object: `private val repository = Dependencies.characterRepository`.
* Schreiben Sie eine Funktion `loadCharacters()`. Starten Sie darin eine Coroutine (`viewModelScope.launch`).
* Führen Sie den asynchronen Repository-Aufruf aus. Setzen Sie den State bei Erfolg auf `Success` und fangen Sie Fehler mit einem `try-catch`-Block ab.
> **Tipp:** Setzen Sie im `catch`-Block den State auf `Error` und übergeben Sie die Fehlermeldung der geworfenen Exception.
* Rufen Sie `loadCharacters()` im `init`-Block des ViewModels auf, damit die Daten beim Start automatisch geladen werden.
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 8.1 - Coroutines](HANDOUT.md#81-coroutines-async-einfach-gemacht)

#### Schritt 7: State Hoisting im Screen
Damit wir das UI gut testen und anzeigen können, trennen wir den Screen in einen "smarten" und einen "dummen" Teil auf.
* Benennen Sie Ihre bisherige Composable `CharacterListScreen` um in `CharacterListContent` und markieren Sie sie als `private` — sie soll nur noch innerhalb der Datei verwendet werden.
* Passen Sie die Parameter von `CharacterListContent` an: Es darf kein ViewModel mehr kennen! Es soll stattdessen den `state: UiState` und das Event `onFavoriteClick: (Int) -> Unit` entgegennehmen.
* Nutzen Sie innerhalb von `CharacterListContent` ein `when(state)` Konstrukt:
    * Bei `Loading`: Zeigen Sie einen `CircularProgressIndicator` (zentriert) an.
    * Bei `Error`: Zeigen Sie einen Text mit der Fehlermeldung an.
    * Bei `Success`: Zeigen Sie Ihre `LazyColumn` (Ihre bisherige Liste) mit den geladenen Daten an!
* Erstellen Sie nun eine **neue**, öffentliche Funktion `CharacterListScreen`, welche das ViewModel via `viewModel<CharacterListViewModel>()` instanziiert, den State einsammelt (`collectAsStateWithLifecycle`) und an `CharacterListContent` weitergibt.
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 6.3 - State Hoisting](HANDOUT.md#63-state-hoisting-teile-und-herrsche) und [📘 Modul 7.5 - UI State konsumieren](HANDOUT.md#75-der-kreis-schlie%C3%9Ft-sich-ui-state-konsumieren)

#### Schritt 8: Previews für alle Zustände
* Erstellen Sie drei separate `@Preview` Funktionen.
* Preview 1 (`LoadingPreview`): Rufen Sie `CharacterListContent` auf und übergeben Sie `UiState.Loading`.
* Preview 2 (`ErrorPreview`): Übergeben Sie `UiState.Error("Keine Internetverbindung")`.
* Preview 3 (`SuccessPreview`): Übergeben Sie `UiState.Success` und nutzen Sie Ihre `getDummyCharacters()` Funktion, um Beispieldaten in die Preview zu laden.
> **Tipp:** Da `CharacterListContent` nun "stateless" ist, können wir jeden Zustand perfekt simulieren, ohne das ViewModel oder eine Internetverbindung zu benötigen.

---

### 3. Akzeptanzkriterien (Definition of Done)
- [ ] Die Dummy-Daten werden im laufenden Betrieb der App nicht mehr genutzt.
- [ ] Es existieren 3 Previews, die Ladering, Fehlertext und Liste korrekt darstellen.
- [ ] Wenn die App startet, ist für einen kurzen Moment ein Lade-Kringel (Spinner) zu sehen.
- [ ] Danach erscheint die Liste mit echten Namen ("Rick Sanchez", "Morty Smith") und Bildern aus dem Netz.
- [ ] **Der Härtetest:** Schalten Sie den Emulator/Ihr Gerät in den Flugmodus und starten Sie die App neu. Die App zeigt nun anstelle eines Absturzes Ihre modellierte Fehlermeldung auf dem Bildschirm an!

---

### 4. Abschluss & Nächster Schritt

Sie haben nun zwei Möglichkeiten, wie Sie im nächsten Lab weitermachen:

**Option A: Mit dem eigenen Code weiterarbeiten (Empfohlen)**
Wenn bei Ihnen alles funktioniert und die echten Daten aus dem Internet geladen werden, bleiben Sie auf Ihrem Branch. Committen Sie Ihre Arbeit.

**Option B: Das Sicherheitsnetz nutzen**
Wenn Sie Netzwerkfehler erhalten oder das Setup nicht fehlerfrei hinbekommen haben, wechseln Sie auf unseren Lösungs-Branch.
Verwerfen oder stashen Sie Ihre unfertigen Änderungen und führen Sie aus:
```git
git checkout lab-5-navigation
```

Dieser Branch enthält das voll funktionsfähige Netzwerk-Setup und ist die Basis für das letzte Modul: Navigation zwischen verschiedenen Screens.