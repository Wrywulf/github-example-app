// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories(BuildLibs.repositories)

    dependencies {
        classpath("com.android.tools.build:gradle:3.2.0-beta05")
        classpath(kotlin("gradle-plugin", version = Versions.kotlinVersion))
        classpath(BuildLibs.androidXNavigationPlugin)
        classpath(BuildLibs.junitPlatformGradlePlugin)
        classpath(BuildLibs.junit5AndroidPlugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories(Libs.repositories)
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
