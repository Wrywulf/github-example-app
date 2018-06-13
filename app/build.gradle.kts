
import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.config.KotlinCompilerVersion

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("androidx.navigation.safeargs")
}

//android {
android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.jayway.example.deskadmin"
        minSdkVersion(21)
        targetSdkVersion(27)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
}

val nav_version = "1.0.0-alpha02"
val ktx_version = "1.0.0-alpha3"
val constraint_layout_version = "1.0.0-alpha3"
val androidx_version = "1.0.0-alpha3"

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))
    implementation("androidx.appcompat:appcompat:1.0.0-alpha3")
    implementation("com.google.android.material:material:1.0.0-alpha3") // weird, but correct (as of june 2018) AndroidX package name
    implementation("androidx.constraintlayout:constraintlayout:1.1.2")
    implementation("androidx.constraintlayout:constraintlayout-solver:1.1.2")

    implementation("androidx.core:core-ktx:$ktx_version")
    implementation("android.arch.navigation:navigation-common-ktx:$ktx_version")

    implementation("android.arch.navigation:navigation-fragment-ktx:$nav_version")
    implementation("android.arch.navigation:navigation-ui-ktx:$nav_version")

    // optional - Test helpers
    androidTestImplementation("android.arch.navigation:navigation-testing-ktx:$nav_version")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
}
