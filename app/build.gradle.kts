typealias and = com.example.internal.Android
typealias dep = com.example.internal.Dependencies

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.weatherforecast"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.weatherforecast"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    dep.other.apply {// Miscellaneous required libraries
        implementation(ktxCore)
        implementation(ktxActivity)
        implementation(ktxFragment)
        implementation(appcompat)
        implementation(constraintLayout)
        implementation(material)
        implementation(navigationFragment)
        implementation(navigationUi)
        implementation(coroutines)
        implementation(swipeRefresh)
        implementation(viewBindingPropDel)
        implementation(viewBindingPropDelNoRef)
    }

    dep.test.apply {
        testImplementation(junit)
    }
}