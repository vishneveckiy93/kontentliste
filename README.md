# KontentListe — KMP, Compose Multiplatform & Ktor

## Überblick
Bewerberaufgabe „Kontenliste“ in der **alternativ erlaubten** Variante:
**Kotlin Multiplatform (KMP)**, **Compose Multiplatform** (UI) und **Ktor** (Netzwerk).

## Architektur
- `composeApp` – Einstieg (Android/iOS)
- `core/network` – Ktor-Client, ContentNegotiation (`text/html -> JSON`), Fehlertexte
- `core/di`, `core/navigation`
- `feature/accounts-list`, `feature/turnovers` – API, Repository, ViewModel, UI

## API
Base: `https://testapi.io/api/jpt/v1`
- `GET /accounts`
- `GET /turnovers`
  Hinweis: Mock-Backend liefert teils JSON mit `Content-Type: text/html`. Ktor akzeptiert dies via registriertem JSON-Konverter.

## Build & Run
**Android**
```bash
./gradlew :composeApp:assembleDebug
