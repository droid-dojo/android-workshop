# Lab 5: Navigation & Detail Screen (Das Finale)

In diesem finalen Lab machen wir aus dem einzelnen Bildschirm eine echte, navigierbare App. Wir bauen einen Detail-Screen, der seine eigenen Daten aus dem Internet lädt. Dabei nutzen wir die aktuelle **Jetpack Navigation 3** Bibliothek und übergeben Argumente typsicher über eine ViewModel-`Factory`.

*Hinweis: Dies ist die umfangreichste Aufgabe. Sollte die Zeit im Workshop nicht reichen, ist dies die perfekte Übung, um sie zu Hause in Ruhe fertigzustellen!*

**Zielzeit:** ca. 90-120 Minuten

### 1. Vorbereitung
Stellen Sie sicher, dass Sie startklar sind:
* **Option A (Eigener Code):** Bleiben Sie auf Ihrem aktuellen Branch, wenn Sie die vorherige Aufgabe erfolgreich abgeschlossen haben.
* **Option B (Sicherheitsnetz):** Wechseln Sie auf den Branch `lab-5-navigation`, wenn Sie unsere Musterlösung als Startpunkt nutzen möchten (`git checkout lab-5-navigation`).

---

> **Voraussetzung:** Dieses Lab benötigt **Jetpack Navigation 3** — konkret drei Libraries: `navigation3-runtime`, `navigation3-ui` und `lifecycle-viewmodel-navigation3`, die noch nicht im Projekt enthalten sind. Fügen Sie die Version und Libraries in `gradle/libs.versions.toml` hinzu und binden Sie die drei Aliase in der `app/build.gradle.kts` ein. Die genauen Einträge finden Sie im [📘 Anhang A](HANDOUT.md#anhang-a-setup--dependencies-modern-way).

### 2. Die Aufgaben

#### Schritt 1: Die Routen definieren (Type-Safety + NavKey)
Damit der Compiler unsere Navigation überprüfen kann, definieren wir unsere Ziele als strikte Kotlin-Typen, die das `NavKey`-Interface implementieren. Wir legen jede Route **direkt neben dem Screen**, zu dem sie gehört — so bleibt jedes Feature in sich geschlossen.
* Erstellen Sie eine neue Datei `CharacterListRoute.kt` neben Ihrer `CharacterListScreen.kt`.
    * Definieren Sie darin ein `@Serializable data object CharacterListRoute : NavKey`.
* Erstellen Sie eine neue Datei `CharacterDetailRoute.kt` (sie wird gleich neben dem noch zu bauenden `CharacterDetailScreen.kt` liegen — legen Sie sie schon an).
    * Definieren Sie darin eine `@Serializable data class CharacterDetailRoute(val id: Int) : NavKey`.
> **Tipp:** Sie benötigen das `kotlinx.serialization` Plugin, das wir bereits für das Networking eingerichtet haben. `NavKey` kommt aus `androidx.navigation3.runtime`.
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 11.3 - Type-Safe Routes mit NavKey](HANDOUT.md#113-type-safe-routes-mit-navkey)

#### Schritt 2: Das Netzwerk-Layer erweitern
Unser Detail-Screen braucht mehr Daten. Wir müssen einen einzelnen Charakter von der API laden.
* Öffnen Sie Ihr `RickAndMortyApi` Interface.
* Fügen Sie eine neue asynchrone Funktion (`suspend`) hinzu, die einen einzelnen `CharacterDto` zurückgibt.
* Annotieren Sie diese für einen GET-Request auf `"character/{id}"`.
* Öffnen Sie Ihr `CharacterRepository` und fügen Sie auch hier eine entsprechende `suspend` Funktion hinzu, die die API aufruft und das DTO in Ihr Domain-Modell (`Character`) umwandelt.
> **Tipp:** Um die ID dynamisch in die URL einzufügen, nutzen Sie die Retrofit-Annotation `@Path("id")` vor dem Parameter Ihrer API-Funktion.

#### Schritt 3: Das Detail-ViewModel & die Factory
Das ViewModel für die Details bekommt seine `id` direkt im Konstruktor — keine `SavedStateHandle`-Akrobatik mehr. Stattdessen geben wir ihm eine `Factory`, die die ID weiterreicht.
* Erstellen Sie eine neue Klasse `CharacterDetailViewModel`, die von `ViewModel` erbt.
* Konstruktor: `class CharacterDetailViewModel(private val id: Int) : ViewModel()`.
* Legen Sie einen `StateFlow` für den Detail-UI-State an (nutzen Sie Ihr bestehendes Sealed Interface oder erstellen Sie ein neues, falls Sie spezifische Detail-Fehler anzeigen wollen).
* Laden Sie im `init`-Block des ViewModels den spezifischen Charakter über das Repository und aktualisieren Sie den State.
* Definieren Sie **innerhalb** der `CharacterDetailViewModel`-Klasse eine geschachtelte `class Factory(private val id: Int) : ViewModelProvider.Factory`, deren `create()` ein `CharacterDetailViewModel(id)` zurückgibt.
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 11.4 - ViewModels mit Navigations-Argumenten](HANDOUT.md#114-viewmodels-mit-navigations-argumenten)

#### Schritt 4: Den Detail-Screen bauen
Der Screen selbst wird extrem sauber, da er sein ViewModel **explizit** als Parameter bekommt (kein `= viewModel()` Default).
* Erstellen Sie eine neue Datei `CharacterDetailScreen.kt` und darin eine `@Composable` Funktion `CharacterDetailScreen`.
* Parameter: `viewModel: CharacterDetailViewModel` (kein Default!) und `onNavigateBack: () -> Unit`.
* Konsumieren Sie den State aus dem ViewModel (`collectAsStateWithLifecycle`).
* Bauen Sie mit einem `when(state)` Konstrukt das UI: Ladering, Fehlermeldung, oder bei Erfolg ein schönes Layout (z.B. großes `AsyncImage`, darunter Name, Status, etc.).
> **Warum kein `= viewModel()`?** Weil wir das ViewModel mit einer `Factory` aufbauen müssen, um die `id` zu übergeben. Den Aufbau übernimmt die `MainActivity` (siehe Schritt 6).
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 7.5 - UI State konsumieren](HANDOUT.md#75-der-kreis-schlie%C3%9Ft-sich-ui-state-konsumieren)

#### Schritt 5: Die Liste klickbar machen
Die `Card` in unserer Liste muss auf Klicks reagieren und diese Information an die Activity weitergeben.
* Erweitern Sie Ihre `CharacterItem` Funktion um ein neues Event-Lambda: `onItemClick: () -> Unit`.
* Fügen Sie der `Card` im `CharacterItem` den passenden Modifier hinzu, um sie klickbar zu machen, und rufen Sie dort das Lambda auf.
* Reichen Sie dieses Event durch `CharacterListContent` und `CharacterListScreen` nach oben durch. Das Lambda im Screen sollte die ID des angeklickten Charakters liefern (`onCharacterClick: (Int) -> Unit`).
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 6.3 - State Hoisting](HANDOUT.md#63-state-hoisting-teile-und-herrsche)

#### Schritt 6: Den Back-Stack aufsetzen (Das Finale)
Die `MainActivity` orchestriert nun die Screens — diesmal mit `NavDisplay` statt eines `NavController`.
* Öffnen Sie die `MainActivity.kt` und löschen Sie den direkten Aufruf Ihres `CharacterListScreen`.
* Erstellen Sie einen Back-Stack: `val backStack = rememberNavBackStack(CharacterListRoute)`.
* Rufen Sie `NavDisplay(...)` auf mit:
    * `backStack = backStack`
    * `onBack = { backStack.removeLastOrNull() }`
    * `entryDecorators = listOf(rememberSaveableStateHolderNavEntryDecorator(), rememberViewModelStoreNavEntryDecorator())`
    * `entryProvider = entryProvider { ... }`
* Im `entryProvider` definieren Sie zwei Einträge:
    * `entry<CharacterListRoute> { CharacterListScreen(onCharacterClick = { id -> backStack.add(CharacterDetailRoute(id)) }) }`
    * `entry<CharacterDetailRoute> { key -> CharacterDetailScreen(viewModel = viewModel(factory = CharacterDetailViewModel.Factory(key.id)), onNavigateBack = { backStack.removeLastOrNull() }) }`
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 11.2 - Implementation](HANDOUT.md#112-implementation-single-activity)

---

### 3. Akzeptanzkriterien (Definition of Done)
- [ ] In der `MainActivity` existiert ein `NavDisplay` mit Back-Stack und `entryProvider`.
- [ ] Das `CharacterDetailViewModel` empfängt seine ID über eine `Factory`.
- [ ] Wenn man in der Liste auf eine Charakter-Karte klickt, öffnet sich ein neuer Screen.
- [ ] Auf dem neuen Screen erscheint kurz ein Ladering, danach werden die vollen Daten des angeklickten Charakters über das Netzwerk geladen und angezeigt.
- [ ] Man kann über den Zurück-Button des Geräts wieder zur Liste gelangen.

---

### 4. Abschluss des Workshops 🎉

Herzlichen Glückwunsch! Sie haben eine moderne, voll funktionsfähige Android-App mit professioneller Architektur (MVVM), State Management, API-Anbindung und Type-Safe Navigation gebaut!

**Option A: Genießen Sie Ihren Code!**
Wenn Ihre App navigiert, fehlerfrei lädt und den Rotationstest besteht, haben Sie alle Konzepte des Modern Android Developments erfolgreich angewendet. Seien Sie stolz auf diesen Branch!

**Option B: Das finale Projekt ansehen**
Möchten Sie den perfekten, aufgeräumten Endstand des gesamten Workshops ansehen? Wechseln Sie auf den finalen Branch:
```git
git checkout lab-6-final
```

Hier finden Sie die fertige App als Referenz für Ihre eigenen zukünftigen Projekte.
