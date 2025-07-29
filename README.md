# Turquoise News

Turquoise News is an offline‑first Android app that fetches and displays news results for four queries—Microsoft, Apple, Google, and Tesla—in a round‑robin fashion.

## Features

- **Offline‑first** caching with Room  
- **Round‑robin querying** of four topics: Microsoft, Apple, Google, Tesla  
- **Fine‑grained error handling** for network & database operations  
- **Kotlin coroutines & Flow** for asynchronous, reactive data streams  
- **Dark mode & dynamic theming** with Material 3

## Further Improvements

- **Pagination** So we wouldn't have to load all the articles at once.

## Architecture

- **core/data**: Local database implementation, HttpClient implementation  
- **core/domain**: Interactors, interfaces, domain models and generally all platform agnostic code that defines the behavior of the app  
- **core/framework**: Platform-specific code that defines the behavior of the app  
- **core/di**: Shared Hilt modules & bindings  
- **core/presentation**: Shared UI components & base classes  
- **navigation**: Top‑level `NavHost` and route definitions  
- **feature/…**: Self‑contained features each following the same 5‑layer pattern

## API Key Security

- The API key is injected securely when building the release APK's.  
- For local forks & builds, the api key is provided in the `build.gradle.kts` file. This is done for educational purposes.

## Dependencies

- [**Jetpack Compose**](https://developer.android.com/jetpack/compose) – Modern, declarative UI toolkit for building native user interfaces in Kotlin.  
- [**Compose Navigation**](https://developer.android.com/jetpack/compose/navigation) – For navigating between screens.  
- [**Kotlin Coroutines & Flow**](https://kotlinlang.org/docs/coroutines-overview.html) – Asynchronous programming primitives for non‑blocking code and reactive streams.  
- [**Hilt**](https://dagger.dev/hilt/) – For dependency-injection.  
- [**Ktor**](https://ktor.io/) – Used to fetch news data.  
- [**Room**](https://developer.android.com/jetpack/androidx/releases/room) – An abstraction layer over SQLite for offline caching.  
- [**DataStore-Preferences**](https://developer.android.com/topic/libraries/architecture/datastore) – Modern, asynchronous key‑value storage solution.  
- [**Coil**](https://coil-kt.github.io/coil/) – Image‑loading.  
- [**Kotest**](https://kotest.io/) – For better looking assertions!  
- [**Turbine**](https://github.com/cashapp/turbine) – To test Kotlin Flow streams.


## Getting Started

1. Clone the repository  
2. Build & run on an Android device or emulator  
3. Enjoy round‑robin news updates with offline support!
