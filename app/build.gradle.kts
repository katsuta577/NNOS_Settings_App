plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.nnos_settings_app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.nnos_settings_app"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation("androidx.navigation:navigation-compose:2.7.7")

// Compose Material Design
    implementation("androidx.compose.material:material:1.6.8") // または最新の安定版
    implementation("androidx.compose.material:material-icons-extended:1.6.8") // SignalWifi4Bar, SignalWifiOff などのアイコン用

// Compose UI ツーリング（@Preview など）を使用する場合
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.8") // または最新の安定版
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.8") // ツーリング機能用

// activity-compose 用
    implementation("androidx.activity:activity-compose:1.9.0") // または最新の安定版
    implementation ("androidx.core:core-ktx:1.13.1") // 最新に合わせて
}
