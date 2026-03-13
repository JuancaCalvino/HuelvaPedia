# HuelvaPedia 🌍 🌴

An informative Android application acting as a digital encyclopedia for the province and city of Huelva, Spain.

[![Platform](https://img.shields.io/badge/Platform-Android-green.svg)](https://developer.android.com/)
[![Language](https://img.shields.io/badge/Language-Java-orange.svg)](https://docs.oracle.com/en/java/)
[![Database](https://img.shields.io/badge/Database-Firebase_Realtime_DB-FFCA28.svg)](https://firebase.google.com/)

HuelvaPedia is designed to showcase the cultural, historical, and geographical richness of Huelva. Users can explore various categories such as local gastronomy, historical landmarks, points of interest, and more. 

## 🚀 Features

- **Cloud-Based Data**: All information is fetched dynamically from a Firebase Realtime Database in the cloud, ensuring data is always up-to-date without requiring app updates.
- **Categorized Information**: Browse through different aspects of Huelva (History, Gastronomy, Places of Interest).
- **Lightweight**: The app does not store heavy encyclopedic data locally. It queries the database only when specific information is requested by the user.
- **Modern UI**: Clean and intuitive Android interface utilizing Material Design components.

## 🛠️ Tech Stack & Architecture

- **Language:** Java
- **Minimum SDK:** API 27 (Android 8.1 - Oreo)
- **Target SDK:** API 30 (Android 11)
- **Backend/Database:** Google Firebase Realtime Database
- **Network Requests:** Android Volley 
- **Image Loading:** Picasso 
- **UI Components:** AndroidX, Material Design, ConstraintLayout, CardView

## 📱 Prerequisites

- Android Studio (Electric Eel or newer recommended)
- Android SDK (API 30+)
- Java JDK 11 (or higher, depending on the Gradle JVM setting)
- Minimum Android Device requirement: Android 8.1 (Oreo)
- **An active internet connection is required** for the app to function, as all content is hosted in the cloud.

## ⚙️ Setup and Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/JuancaCalvino/HuelvaPedia.git
   ```
2. **Open in Android Studio:**
   - Open Android Studio and select `File > Open...`
   - Navigate to the cloned folder and select it.
3. **Important: Firebase Configuration (`google-services.json`)**
   For security reasons, the `google-services.json` file is **not included** in this public repository (it is added to `.gitignore`). 
   To build and run the application, you must connect it to your own Firebase project:
   - Create a new project in [Firebase Console](https://console.firebase.google.com/).
   - Add an Android App to the project with the package name `com.example.huelvapedia`.
   - Download the generated `google-services.json` file.
   - Place this file inside the `app/` directory of the cloned project.
   - Replicate the database structure (JSON) used by the application in your Realtime Database.
4. **Build and Run:**
   - Wait for Android Studio to finish the Gradle Sync.
   - Click the **Run 'app'** button (▶) or use `Shift + F10`.

## 🔒 Scalability & Security

As an informative application, it's designed to be easily extensible. New categories or data points can be added seamlessly via the Firebase console without requiring users to update the APK. 
The application accesses Firebase purely via Android's internal `google-services.json` secure integration.
