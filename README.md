# 🚀 Das Finale: Die fertige Rick & Morty App

**Herzlichen Glückwunsch!** Sie haben das Ende des Workshops "Modern Android Development" erreicht.

Dieser Branch (`lab-6-final`) enthält die vollständige, fehlerfreie und aufgeräumte Referenz-Implementierung unserer App. Sie können diesen Code als "Goldstandard" und als Vorlage für Ihre eigenen, zukünftigen Android-Projekte nutzen.

---

## 🛠 Was wir gemeinsam gebaut haben

Sie haben in den letzten Tagen nicht nur Code abgetippt, sondern eine App nach den aktuellsten Architektur-Standards von Google (Stand 2026) entwickelt:

* **100% Jetpack Compose:** Ein vollständig deklaratives UI ohne ein einziges XML-Layout.
* **Moderne Architektur (MVVM):** Saubere Trennung von UI (`Screen`), Logik (`ViewModel`) und Datenbeschaffung (`Repository`).
* **State Management:** Reaktive UIs durch `StateFlow` und striktes State Hoisting. Ein UI, das sich selbst heilt und Rotationen überlebt.
* **Networking & Coroutines:** Asynchrones, blockierungsfreies Laden von JSON-Daten mit *Retrofit*, *Kotlinx Serialization* und *Coil*.
* **Type-Safe Navigation (Nav 3):** Typsichere Routen über `NavKey`, ein direkt manipulierbarer Back-Stack via `NavDisplay`, und Navigations-Argumente, die explizit über eine `ViewModelProvider.Factory` ans ViewModel fließen.

---

## 💻 Den Code ausführen

Wenn Sie diesen Branch gerade ausgecheckt haben:
1.  Klicken Sie in Android Studio oben rechts auf den **"Sync Project with Gradle Files"**-Button (den kleinen Elefanten), falls dieser angezeigt wird.
2.  Starten Sie Ihren Emulator oder schließen Sie Ihr physisches Gerät an.
3.  Klicken Sie auf **"Run"** (den grünen Play-Button).
4.  *Wichtig:* Stellen Sie sicher, dass Ihr Emulator mit dem Internet verbunden ist, da wir jetzt Livedaten von der API abrufen!

---

## 🎯 Wie geht es jetzt weiter? (Hausaufgaben & Herausforderungen)

Der Workshop ist vorbei, aber Ihre Reise als Android-Entwickler beginnt gerade erst. Wenn Sie das Gelernte festigen möchten, versuchen Sie, diese App zu Hause weiterzubauen:

1.  **Pagination (Unendliches Scrollen):** Aktuell laden wir nur die erste Seite (20 Charaktere). Die API bietet eine Paginierung an. Können Sie die Liste so umbauen, dass sie automatisch die nächsten 20 Charaktere lädt, wenn man unten ankommt?
2.  **Suchfunktion:** Fügen Sie der TopBar ein Textfeld hinzu, um nach Namen zu filtern. (Tipp: Die Rick & Morty API unterstützt das Filtern über Query-Parameter: `?name=rick`).
3.  **Offline-Fähigkeit (Room):** Speichern Sie die markierten Favoriten nicht nur im Speicher, sondern persistent in einer lokalen SQLite-Datenbank mit *Room*, damit sie auch nach einem App-Neustart noch da sind.
4.  **Pull-to-Refresh:** Fügen Sie der Liste eine Geste zum Herunterziehen und Aktualisieren der Daten hinzu (`PullToRefreshBox` aus Material 3).

---

## 📚 Wichtige Ressourcen zum Nachschlagen

Hier sind die besten Anlaufstellen, wenn Sie in Ihren eigenen Projekten nicht weiterwissen:

* **Unser Workshop Handout:** [📘 HANDOUT.md](HANDOUT.md) (Ihre lokale Referenz für alle behandelten Themen)
* **Offizielle Compose Dokumentation:** [developer.android.com/compose](https://developer.android.com/compose)
* **Kotlin Sprachreferenz:** [kotlinlang.org/docs](https://kotlinlang.org/docs/home.html)
* **Material Design 3 Guidelines:** [m3.material.io](https://m3.material.io/)

**Vielen Dank für Ihre großartige Mitarbeit im Workshop und viel Erfolg bei Ihren zukünftigen Android-Projekten!**