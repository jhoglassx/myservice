// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0-rc01" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.gms.google-services") version "4.3.15" apply false
}
buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        // other plugins...
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
    }
}
