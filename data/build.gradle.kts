typealias and = com.example.internal.Android
typealias dep = com.example.internal.Dependencies

plugins {
    id("dagger.hilt.android.plugin")
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    kotlin("kapt")
}

android {
    namespace = "com.example.data"
    compileSdk = and.compileSdk

    defaultConfig {
        minSdk = and.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.21")

    dep.androidX.apply {
        implementation(dataStore)
    }

    dep.hilt.apply { // https://dagger.dev/hilt/
        implementation(hiltAndroid)
        kapt(daggerHiltCompiler)
        kaptAndroidTest(daggerHiltCompiler)
    }

    dep.retrofit.apply { // https://square.github.io/retrofit/
        implementation(converterMoshi)
        implementation(retrofit2)
        implementation(loggingInterceptor)
    }

    dep.room.apply { // https://developer.android.com/jetpack/androidx/releases/room
        implementation(runtime)
        implementation(ktx)
        kapt(compiler)
    }

    dep.test.apply {
        testImplementation(junit)
    }
}