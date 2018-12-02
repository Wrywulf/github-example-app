import com.android.build.gradle.ProguardFiles.getDefaultProguardFile
import com.android.tools.build.bundletool.utils.Versions
import de.mannodermaus.gradle.plugins.junit5.junitPlatform
import org.gradle.internal.impldep.org.junit.platform.launcher.EngineFilter.includeEngines
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs")
    id("de.mannodermaus.android-junit5") // junit5 doesn't support android projects out of the box
}

//android {
android {
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

    testOptions {
//        junitPlatform {
//            filters(fun(): Any {
//                return includeEngines("spek")
//            })
//        }
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

    // Networking
    implementation(Libs.retrofit2)
    implementation(Libs.retrofit2Rx2Adapter)
    implementation(Libs.moshi)
    implementation(Libs.retrofit2MoshiConverter)

    // Rx
    implementation(Libs.rxJava2)
    implementation(Libs.rxKotlin2)
    implementation(Libs.rxAndroid2)
    implementation(Libs.rxRelay2)
    implementation(Libs.rxUiLib2Kotlin)
    implementation(Libs.rxBinding2Kotlin)
    implementation(Libs.rxBinding2AppCompatKotlin)
    implementation(Libs.rxRedux)

    // Coroutines
    implementation(Libs.kotlinCoroutinesAndroid)

    // Debug
    implementation(Libs.timber)
    debugImplementation(Libs.stetho)
    implementation(Libs.okHttp3LoggingInterceptor)

    // Test
    testImplementation(Libs.junit)
    testImplementation(Libs.rxUiLib2Test)
    testImplementation(Libs.assertJ)
    testImplementation(Libs.mockito)
    testImplementation(Libs.mockitoKotlin)
    testImplementation(Libs.spek1Api)
    testImplementation(Libs.spek1PlatformEngine)
    testImplementation(Libs.junitPlatformRunner)

    androidTestImplementation(Libs.androidxNavigationTestingKtx)
    androidTestImplementation(Libs.androidxTestRunner)
    androidTestImplementation(Libs.androidxEspressoCore)

}
