
# Sample Application for **Pulse Insights SDK** Integration

## Overview

This sample Android application demonstrates the integration of the **Pulse Insights SDK**. It includes utility classes for simplified SDK configuration, view mapping, and runtime behavior. The application sets up lifecycle callbacks to dynamically manage SDK functionality and trigger surveys based on configurable rules.

---

## Features

- **Pulse Insights SDK** initialization via Maven dependency.
- Configurable view mapping for analytics and survey triggers.
- Lifecycle management for activities and fragments.
- Survey frequency control with debouncing.

---

## Prerequisites

- **Android Studio** version [X.X] or later.
- Minimum Android SDK level [XX].
- An Account key for **Pulse Insights SDK**.

---

## Setup Instructions

### 1. Add Pulse Insights SDK to Your Project

1. Open your app's `settings.gradle.kts` file and add the **Pulse Insights SDK** dependency from the Maven repository:

   ```kotlin
   dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
        repositories {
            google()
            mavenCentral()
            maven { setUrl ("https://pi-sdk.s3.us-east-1.amazonaws.com/android") }
        }
    }
   ```
   Alternatively the dependency may be defined in the application or project build.gradle

   In this sample application, the maven repository is resolved in settings.gradle.kts
2.  declare dependency in build.gradle.kts

```kotlin
    dependencies {
    
    implementation("com.pulseinsights:android-sdk:2.4.2")
    implementation("com.google.code.gson:gson:2.11.0")  // implement gson
    
    }
```

3. Add the following elements to AndroidManifest.xml Permissions for sensors
    ```xml
        <uses-permission android:name="android.permission.HIGH_SAMPLING_RATE_SENSORS" />
    ```
   If targeting Android api version 28 and above the following is required to load our httpcore
    ```xml
    <uses-library
            android:name="org.apache.http.legacy"
            android:required="false" /> 
   ```

---

### 3. Add Your Account Identifier

Replace `account_id` with your Pulse Insights Account ID in the `MyApp.kt` file:

```kotlin
    //example custom data object and config on init
val config = ExtraConfig().apply {
    automaticStart = false
    customData = mapOf(
        "name" to "tester",
        "type" to "worker",
        "age" to "12"
    )
}
PulseInsightsManager.initialize(this, "account_id", config)
```

---

### 4. Configure View Mappings

Add your custom view mappings in `res/raw/view_mappings.json`:

```json
{
  "viewMappings": [
    {
      "className": "MainActivity",
      "analyticsName": "Main",
      "surveyConfig": { "enabled": true, "frequency": 1 }
    },
    {
      "className": "DashboardFragment",
      "analyticsName": "Main.Dashboard",
      "surveyConfig": { "enabled": true, "frequency": 0 }
    }
  ]
}
```

---

### 5. Build and Run the Application

1. Open the project in **Android Studio**.
2. Connect your device or start an emulator.
3. Build and run the project to explore the integrated Pulse Insights features.

---

## Project Structure

### 1. `MyApp.kt`
- Initializes the **Pulse Insights SDK** with an Account key and custom configurations.
- Registers lifecycle callbacks for activity and fragment tracking.
- Loads view mappings during application startup.

### 2. `PulseInsightsLifecycleCallback.kt`
- Handles activity and fragment lifecycle events to set context and trigger surveys dynamically.
- Ensures surveys are debounced to prevent frequent triggers.

### 3. `ViewMappingManager.kt`
- Manages view mappings for activities and fragments.
- Loads mappings from a JSON configuration file or defaults.
- Retrieves configuration details like survey rules and analytics names.

### 4. `PulseInsightsManager.kt`
- Encapsulates SDK initialization and interaction logic.
- The PulseInsights SDK API is directly accessible from the PulseInsights Instance, The manager provides a potential implementation for more concise management of survey concepts
- Provides methods to set context and trigger surveys programmatically.

### 5. `ViewConfig.kt`
- Defines the structure for view configurations, including:
    - `analyticsName`: Analytics identifier for the view.
    - `shouldTriggerSurvey`: Whether the survey should trigger for this view.
    - `surveyFrequency`: Frequency of survey triggers.

