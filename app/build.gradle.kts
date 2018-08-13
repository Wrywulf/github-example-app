import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import com.android.tools.build.bundletool.utils.Versions
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
}

//android {
android {
    buildToolsVersion("28.0.1")
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "com.jayway.example.speakerrecognition"
        minSdkVersion(24)
        targetSdkVersion(28)
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

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
    }

    lintOptions {

    }
}

androidExtensions {
    // In order to enable the ViewHolder pattern used in groupie android extensions
    configure(delegateClosureOf<AndroidExtensionsExtension> {
        isExperimental = true
    })
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))

    // AndroidX Core
    implementation(Libs.androidxAppCompat)
    implementation(Libs.androidxLegacy)
    implementation(Libs.androidxMaterial)
    implementation(Libs.androidxConstraint)
    implementation(Libs.androidxConstraintSolver)
    // AndroidX KTX
    implementation(Libs.androidxKtxCore)
    // AndroidX Navigation
    implementation(Libs.androidxNavigationCommon)
    implementation(Libs.androidxNavigationFragment)
    implementation(Libs.androidxNavigationUi)
    // AndroidX Lifecycle
    implementation(Libs.androidxLifecycle)

    // Groupie
    implementation(Libs.groupie)
    implementation(Libs.groupieKotlinAndroidExtensions)

    // DI
    implementation(Libs.koinAndroid)
    implementation(Libs.koinAndroidXViewModel)

    // Rx
    implementation(Libs.rxJava2)
    implementation(Libs.rxKotlin2)
    implementation(Libs.rxAndroid2)
    implementation(Libs.rxRelay2)
    implementation(Libs.rxUiLib2Kotlin)
    implementation(Libs.rxBinding2Kotlin)
    implementation(Libs.rxBinding2AppCompatKotlin)

    // Coroutines
    implementation(Libs.kotlinCoroutinesAndroid)

    // Debug
    implementation(Libs.timber)
    debugImplementation(Libs.stetho)
    implementation(Libs.okHttp3LoggingInterceptor)

    // optional - Test helpers
    androidTestImplementation(Libs.androidxNavigationTestingKtx)

    testImplementation(Libs.junit)
    androidTestImplementation(Libs.androidxTestRunner)
    androidTestImplementation(Libs.androidxEspressoCore)
}
