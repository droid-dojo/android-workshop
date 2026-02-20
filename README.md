# Lab 5: Navigation & Detail Screen (Das Finale)

In diesem finalen Lab machen wir aus dem einzelnen Bildschirm eine echte, navigierbare App. Wir bauen einen Detail-Screen, der seine eigenen Daten aus dem Internet lädt. Dabei nutzen wir modernste "Type-Safe Navigation" und überlassen dem ViewModel die Aufgabe, sich die übergebenen Argumente selbst zu holen.

*Hinweis: Dies ist die umfangreichste Aufgabe. Sollte die Zeit im Workshop nicht reichen, ist dies die perfekte Übung, um sie zu Hause in Ruhe fertigzustellen!*

**Zielzeit:** ca. 90-120 Minuten

### 1. Vorbereitung
Stellen Sie sicher, dass Sie startklar sind:
* **Option A (Eigener Code):** Bleiben Sie auf Ihrem aktuellen Branch, wenn Sie die vorherige Aufgabe erfolgreich abgeschlossen haben.
* **Option B (Sicherheitsnetz):** Wechseln Sie auf den Branch `lab-5-navigation`, wenn Sie unsere Musterlösung als Startpunkt nutzen möchten (`git checkout lab-5-navigation`).

---

> **Voraussetzung:** Dieses Lab benötigt **Retrofit**, **kotlinx-serialization** und das dazugehörige Gradle-Plugin, die noch nicht im Projekt enthalten sind. Fügen Sie die Versionen, Libraries und das Plugin in `gradle/libs.versions.toml` hinzu und binden Sie `retrofit-core`, `retrofit-kotlinx-serialization` sowie `kotlinx-serialization-json` in der `app/build.gradle.kts` ein. Vergessen Sie nicht, das Serialization-Plugin im `plugins`-Block zu laden! Die genauen Einträge finden Sie im [📘 Anhang A](HANDOUT.md#anhang-a-setup--dependencies-modern-way).

### 2. Die Aufgaben

#### Schritt 1: Die Routen definieren (Type-Safety)
Damit der Compiler unsere Navigation überprüfen kann, definieren wir unsere Ziele als strikte Kotlin-Typen.
* Erstellen Sie eine neue Datei `Routes.kt`.
* Erstellen Sie ein Objekt (`object`) für den Listen-Screen (z.B. `ListRoute`).
* Erstellen Sie eine Datenklasse (`data class`) für den Detail-Screen (z.B. `DetailRoute`). Diese muss die `id` des Charakters (als `Int`) aufnehmen können.
* Annotieren Sie beide Typen mit `@Serializable`.
> **Tipp:** Sie benötigen das `kotlinx.serialization` Plugin, das wir bereits für das Networking eingerichtet haben.
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 11.3 - Type-Safe Navigation](HANDOUT.md#113-type-safe-navigation-modern-way)

#### Schritt 2: Das Netzwerk-Layer erweitern
Unser Detail-Screen braucht mehr Daten. Wir müssen einen einzelnen Charakter von der API laden.
* Öffnen Sie Ihr `RickAndMortyApi` Interface.
* Fügen Sie eine neue asynchrone Funktion (`suspend`) hinzu, die einen einzelnen `CharacterDto` zurückgibt.
* Annotieren Sie diese für einen GET-Request auf `"character/{id}"`.
* Öffnen Sie Ihr `CharacterRepository` und fügen Sie auch hier eine entsprechende `suspend` Funktion hinzu, die die API aufruft und das DTO in Ihr Domain-Modell (`Character`) umwandelt.
> **Tipp:** Um die ID dynamisch in die URL einzufügen, nutzen Sie die Retrofit-Annotation `@Path("id")` vor dem Parameter Ihrer API-Funktion.

#### Schritt 3: Das Detail-ViewModel & SavedStateHandle
Das ViewModel für die Details soll völlig selbstständig arbeiten und sich die ID, die beim Navigieren übergeben wurde, selbst besorgen.
* Erstellen Sie eine neue Klasse `CharacterDetailViewModel`, die von `ViewModel` erbt.
* Fügen Sie dem Konstruktor dieses ViewModels das `SavedStateHandle` als Parameter hinzu. (Android übergibt dieses automatisch!).
* Extrahieren Sie die `DetailRoute` direkt im ViewModel aus dem `SavedStateHandle` und speichern Sie die ID in einer Variable.
* Legen Sie einen `StateFlow` für den Detail-UI-State an (Nutzen Sie Ihr bestehendes Sealed Interface oder erstellen Sie ein neues, falls Sie spezifische Detail-Fehler anzeigen wollen).
* Laden Sie im `init`-Block des ViewModels den spezifischen Charakter über das Repository und aktualisieren Sie den State.
> **Tipp:** Um die typsichere Route auszulesen, rufen Sie `savedStateHandle.toRoute<DetailRoute>()` auf. Android Studio schlägt den Import eventuell nicht vor: fügen Sie `import androidx.navigation.toRoute` manuell hinzu.

#### Schritt 4: Den Detail-Screen bauen
Der Screen selbst wird extrem sauber, da er keine IDs mehr entgegennehmen muss.
* Erstellen Sie eine neue Datei `CharacterDetailScreen.kt` und darin eine `@Composable` Funktion `CharacterDetailScreen`.
* Die Funktion darf als **einzigen Parameter** das `CharacterDetailViewModel` entgegennehmen (nutzen Sie `= viewModel()` als Standardwert).
* Konsumieren Sie den State aus dem ViewModel (`collectAsStateWithLifecycle`).
* Bauen Sie mit einem `when(state)` Konstrukt das UI: Ladering, Fehlermeldung, oder bei Erfolg ein schönes Layout (z.B. großes `AsyncImage`, darunter Name, Status, etc.).
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 7.5 - UI State konsumieren](HANDOUT.md#75-der-kreis-schlie%C3%9Ft-sich-ui-state-konsumieren)

#### Schritt 5: Die Liste klickbar machen
Die `Card` in unserer Liste muss auf Klicks reagieren und diese Information an die Activity weitergeben.
* Erweitern Sie Ihre `CharacterItem` Funktion um ein neues Event-Lambda: `onItemClick: () -> Unit`.
* Fügen Sie der `Card` im `CharacterItem` den passenden Modifier hinzu, um sie klickbar zu machen, und rufen Sie dort das Lambda auf.
* Reichen Sie dieses Event durch `CharacterListContent` und `CharacterListScreen` nach oben durch. Das Lambda im Screen sollte die ID des angeklickten Charakters liefern (`onCharacterClick: (Int) -> Unit`).
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 6.3 - State Hoisting](HANDOUT.md#63-state-hoisting-teile-und-herrsche)

#### Schritt 6: Den Navigations-Controller aufsetzen (Das Finale)
Die `MainActivity` orchestriert nun die Screens.
* Öffnen Sie die `MainActivity.kt` und löschen Sie den direkten Aufruf Ihres `CharacterListScreen`.
* Erstellen Sie einen `NavController` (`rememberNavController()`) und fügen Sie den `NavHost` ein (Start-Ziel ist `ListRoute`).
* Definieren Sie im Block des `NavHost` eine `composable<ListRoute>`. Rufen Sie dort den `CharacterListScreen` auf.
* Nutzen Sie das Lambda `onCharacterClick` des Listen-Screens, um die Navigation auszulösen (`navController.navigate(DetailRoute(id = ...))`).
* Definieren Sie eine weitere `composable<DetailRoute>`.
* Da Ihr ViewModel die ID selbst ausliest, müssen Sie hier **nichts weiter tun**, als einfach `CharacterDetailScreen()` aufzurufen!
> **Hilfe:** Theorie und Syntax finden Sie in [📘 Modul 11.2 - Implementation](HANDOUT.md#112-implementation-single-activity)

---

### 3. Akzeptanzkriterien (Definition of Done)
- [ ] In der `MainActivity` existiert ein `NavHost` mit zwei verknüpften Routen.
- [ ] Das `CharacterDetailViewModel` holt sich seine ID selbst über das `SavedStateHandle`.
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