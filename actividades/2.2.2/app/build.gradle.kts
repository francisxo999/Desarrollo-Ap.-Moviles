plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // 🔥 Este plugin es OBLIGATORIO en Kotlin 2.0+ cuando usas Jetpack Compose:
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.example.appadaptable_grupo7"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.appadaptable_grupo7"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    // ✅ Activar Jetpack Compose
    buildFeatures {
        compose = true
    }

    // ✅ Versión del compilador de Compose
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    // Core y ciclo de vida
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.4")
    implementation("androidx.activity:activity-compose:1.9.2")

    // Compose BOM para manejar versiones consistentes
    implementation(platform("androidx.compose:compose-bom:2024.06.00"))

    // Librerías de Compose
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // 📐 Window Size Class (para adaptabilidad)
    implementation("androidx.compose.material3:material3-window-size-class:1.3.1")

    // ViewModel para Jetpack Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.06.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}
