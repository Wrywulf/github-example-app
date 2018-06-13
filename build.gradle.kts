// Top-level build file where you can add configuration options common to all sub-projects/modules.


//var kotlinVersion: String by extra

buildscript {
val kotlinVersion = "1.2.41"
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.2.0-alpha17")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
        classpath("android.arch.navigation:navigation-safe-args-gradle-plugin:1.0.0-alpha02")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
