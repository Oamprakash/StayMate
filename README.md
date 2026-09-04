# StayMate — Hostel / PG booking app

Native Android app (Kotlin + Jetpack Compose, Material 3) for discovering and booking beds in
hostels and PG accommodations.

## Features

- Explore listings with search (name / locality / city) and filters for city, property type
  (Hostel or PG), gender policy and monthly budget
- Property detail with rating, amenities, description, per-room-type pricing and bed availability
- Booking flow: guest details with validation, move-in date picker, stay duration and a rent +
  deposit payment summary
- Booking confirmation with a booking ID, plus a "My bookings" tab where a booking can be cancelled

Listings come from an in-memory mock dataset (`MockProperties`); bookings live in
`StayRepository` for the lifetime of the process. Swapping `StayRepository` for a network or
Room-backed implementation is the natural next step.

## Project layout

```
app/src/main/java/com/staymate/booking/
├── MainActivity.kt
├── data/            # models, mock listings, repository
└── ui/              # theme, view model, navigation graph, screens
```

## Build

```bash
./gradlew assembleDebug
# APK: app/build/outputs/apk/debug/app-debug.apk
```

Requires JDK 17 and the Android SDK (compileSdk 34, minSdk 24). Point Gradle at your SDK with a
`local.properties` file containing `sdk.dir=/path/to/android-sdk`.
