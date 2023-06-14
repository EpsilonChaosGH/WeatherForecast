typealias and = com.example.internal.Android
typealias dep = com.example.internal.Dependencies

plugins {
    id("dagger.hilt.android.plugin")
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    kotlin("kapt")
}

android {
    namespace = "com.example.weatherforecast"
    compileSdk = and.compileSdk

    defaultConfig {
        applicationId = "com.example.weatherforecast"
        minSdk = and.minSdk
        targetSdk = and.targetSdk
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    //implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.21")

    implementation(project(path = ":domain"))
    implementation(project(path = ":data"))


    dep.hilt.apply { // https://dagger.dev/hilt/
        implementation(hiltAndroid)
        kapt(daggerHiltCompiler)
        kaptAndroidTest(daggerHiltCompiler)
    }

    dep.androidX.apply {
        implementation(ktxCore)
        implementation(ktxActivity)
        implementation(ktxFragment)
        implementation(appcompat)
        implementation(constraintLayout)
        implementation(navigationFragment)
        implementation(navigationUi)
        implementation(swipeRefresh)
    }

    dep.other.apply {// Miscellaneous required libraries
        implementation(playServices)
        implementation(material)
        implementation(coroutines)
        implementation(viewBindingPropDel)
        implementation(viewBindingPropDelNoRef)
    }

    dep.test.apply {
        testImplementation(junit)
    }
}