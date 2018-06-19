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
        /*
        /Users/ubh/source/apps/JabraSpeakerRecognition/app: Error: httpclient defines classes that conflict with classes now provided by Android. Solutions include finding newer versions or alternative libraries that don't have the same problem (for example, for httpclient use HttpUrlConnection or okhttp instead), or repackaging the library using something like jarjar. [DuplicatePlatformClasses]

   Explanation for issues of type "DuplicatePlatformClasses":
   There are a number of libraries that duplicate not just functionality of
   the Android platform but using the exact same class names as the ones
   provided in Android -- for example the apache http classes. This can lead
   to unexpected crashes.

   To solve this, you need to either find a newer version of the library which
   no longer has this problem, or to repackage the library (and all of its
   dependencies) using something like the jarjar tool, or finally, rewriting
   the code to use different APIs (for example, for http code, consider using
   HttpUrlConnection or a library like okhttp).
         */
        isCheckReleaseBuilds = false
    }
}

androidExtensions {
    // In order to enable the ViewHolder pattern used in groupie android extensions
    configure(delegateClosureOf<AndroidExtensionsExtension> {
        isExperimental = true
    })
}
val nav_version = "1.0.0-alpha04"
val room_version = "2.0.0-alpha1"

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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:0.23.3")

    // Debug
    implementation(Libs.timber)
    debugImplementation(Libs.stetho)
    implementation(Libs.okHttp3LoggingInterceptor)

    // optional - Test helpers
    androidTestImplementation("android.arch.navigation:navigation-testing-ktx:$nav_version")

    testImplementation("junit:junit:4.12")
    testImplementation("androidx.room:room-testing:$room_version")
    androidTestImplementation("com.android.support.test:runner:1.0.2")
    androidTestImplementation("com.android.support.test.espresso:espresso-core:3.0.2")
}
