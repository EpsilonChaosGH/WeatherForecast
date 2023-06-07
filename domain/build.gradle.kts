typealias and = com.example.internal.Android
typealias dep = com.example.internal.Dependencies

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {

    implementation(dep.other.coroutines)

}