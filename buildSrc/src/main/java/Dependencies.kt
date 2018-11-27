@file:Suppress("unused")

import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

object Versions {
    const val kotlinVersion = "1.3.10"
    const val kotlinCoroutines = "0.23.3"
    const val minSdk = 21
    const val targetSdk = 28
    const val compileSdk = 28
    const val buildTools = "27.0.3"

    // Google
    const val playServices = "11.8.0"
    const val androidxAppCompat = "1.0.0-rc01"
    const val androidxLegacy = "1.0.0-rc01"
    const val androidxPreference = "1.0.0-rc01"
    const val androidxMaterial = "1.0.0-rc01"
    const val androidxConstraint = "2.0.0-alpha2"
    const val androidxKtx = "1.0.0-alpha3"
    const val androidxNavigation = "1.0.0-alpha04"
    const val androidxRoom = "2.0.0-alpha1"
    const val androidxLifecycle = "2.0.0-rc01"
    const val supportLibs = "27.1.0"
    const val archComponents = "1.0.0"
    const val firebase = "11.8.0"
    const val fabricGradlePlugin = "1.24.4"
    const val googleServicesGradlePlugin = "3.1.2"
    const val crashlytics = "2.7.1@aar"


    // UI libs.
    const val picasso = "2.5.2"
    const val rxBinding = "1.0.1"
    const val rxBinding2 = "2.1.1"
    const val butterKnife = "8.1.0"
    const val conductor = "2.1.4"
    const val groupie = "2.1.0"
    const val gravitySnapHelper = "1.5"
    const val glide = "4.5.0"
    const val bright = "v1.1.0-release"


    // Reactive.
    const val rxJava = "1.3.0"
    const val rxJava2 = "2.2.0"
    const val rxKotlin2 = "2.3.0"
    const val rxJavaInterop = "0.10.1"
    const val rxJavaProGuardRules = "1.1.6.0"
    const val rxJavaAsyncUtil = "0.21.0"
    const val rxAndroid = "1.2.1"
    const val rxAndroid2 = "2.1.0"
    const val rxRelay = "1.2.0"
    const val rxRelay2 = "2.0.0"
    const val rxLifecycle = "1.0"
    const val rxLifecycle2 = "2.1.0"
    const val rxReplayingShare = "2.0.1"
    const val rxPreferences2 = "2.0.0-RC2"
    const val rxUiLib2 = "2.0.0"
    const val rxRedux = "1.0.0"

    // Others.
    const val retrofit = "2.4.0"
    const val retrolambda = "2.3.0"
    const val dagger = "2.15"
    const val koin = "1.0.0-alpha-19"
    const val jsr305 = "3.0.1"
    const val okHttp = "3.10.0"
    const val okhttpCookieJar = "v1.0.1"
    const val okio = "1.14.0"
    const val gson = "2.6.2"
    const val moshi = "1.6.0"
    const val jackson = "2.8.6"
    const val guava = "19.0"
    const val javapoet = "1.8.0"
    const val immutables = "2.2.1"
    const val supportMultiDex = "1.0.1"
    const val javaxInject = "1"
    const val autoFactory = "1.0-beta3"
    const val autoService = "1.0-rc2"
    const val autoCommon = "0.6"
    const val autovalue = "1.3"
    const val autovalueMoshi = "0.4.2"
    const val autovalueParcel = "0.2.5"
    const val autovalueWith = "1.0.0"
    const val javaWriter = "2.5.1"
    const val javax = "1"
    const val jodaTime = "2.9.9"
    const val trueTime = "2.2"
    const val sqlBrite = "3.1.1"
    const val libphonenumber = "8.5.1"

    // Debugging & Inspecting.
    const val slf4j = "1.7.19"
    const val timber = "4.7.0"
    const val debugDrawer = "0.7.0"
    const val scalpel = "1.1.2"
    const val leakCanary = "1.5.1"
    const val rxLifecycleLint = "1.0.3"
    const val processPhoenix = "2.0.0"
    const val stetho = "1.5.0"
    const val takt = "1.0.2"
    const val timberCrashlyticsTrees = "1.0.0"

    // Testing.
    const val junit = "4.12"
    const val junitParams = "1.0.5"
    const val junitPlatform = "1.2.0"
    const val junit5Android = "1.2.0.0"
    const val assertJ = "3.9.0"
    const val mockito = "2.13.0"
    const val robolectric = "3.1.2"
    const val supportTestRunner = "1.0.2"
    const val espresso = "3.0.1"
    const val compileTesting = "0.10"
    const val wireMock = "2.6.0"
    const val dexMaker = "1.2"
    const val mockitoKotlin = "1.5.0"
    const val androidxEspresso = "3.1.0-alpha1"
    const val androidxTestRunner = "1.1.0-alpha1"
    const val spek1 = "1.2.0"
}

object Libs {

    /**
     * The repositories hosting the libraries below
     *
     * Invoke this in place of repositories { .. } in the module's build.gradle.kts. (or in the allProjects closure in the root build.gradle.kts)
     *
     * Ie.
     * ```
     * repositories(Libs.repositories)
     * ```
     */
    val repositories: RepositoryHandler.() -> Unit = {
        google()
        jcenter()
        maven("https://dl.bintray.com/spekframework/spek/")
    }


    // Kotlin
    const val kotlinStdLibJre7 = "org.jetbrains.kotlin:kotlin-stdlib-jre7:${Versions.kotlinVersion}"
    const val kotlinStdLibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinVersion}"
    const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlinVersion}"
    const val kotlinCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlinCoroutines}"

    // Google AndroidX
    const val androidxAppCompat = "androidx.appcompat:appcompat:${Versions.androidxAppCompat}"
    const val androidxLegacy = "androidx.legacy:legacy-support-v4:${Versions.androidxLegacy}"
    const val androidxPreference = "androidx.preference:preference:${Versions.androidxPreference}"
    const val androidxMaterial = "com.google.android.material:material:${Versions.androidxMaterial}"
    const val androidxConstraint = "androidx.constraintlayout:constraintlayout:${Versions.androidxConstraint}"
    const val androidxConstraintSolver = "androidx.constraintlayout:constraintlayout-solver:${Versions.androidxConstraint}"
    const val androidxKtxCore = "androidx.core:core-ktx:${Versions.androidxKtx}"
    const val androidxNavigationCommon= "android.arch.navigation:navigation-common-ktx:${Versions.androidxNavigation}"
    const val androidxNavigationFragment= "android.arch.navigation:navigation-fragment-ktx:${Versions.androidxNavigation}"
    const val androidxNavigationUi= "android.arch.navigation:navigation-ui-ktx:${Versions.androidxNavigation}"
    const val androidxLifecycle= "androidx.lifecycle:lifecycle-extensions:${Versions.androidxLifecycle}"
    const val androidxRoomRuntime = "androidx.room:room-runtime:${Versions.androidxRoom}"
    const val androidxRoomRxJava = "androidx.room:room-rxjava2:${Versions.androidxRoom}"
    const val androidxRoomCompiler = "androidx.room:room-compiler:${Versions.androidxRoom}"


    // Google
    const val archLifecycle = "android.arch.lifecycle:common-java8:${Versions.archComponents}"
    const val crashlytics = "com.crashlytics.sdk.android:crashlytics:${Versions.crashlytics}"
    const val fabricGradlePlugin = "io.fabric.tools:gradle:${Versions.fabricGradlePlugin}"
    const val firebaseCore = "com.google.firebase:firebase-core:${Versions.firebase}"
    const val firebasePerf = "com.google.firebase:firebase-perf:${Versions.firebase}"
    const val googleServicesGradlePlug = "com.google.gms:google-services:${Versions.googleServicesGradlePlugin}"
    const val playServicesMaps = "com.google.android.gms:play-services-maps:${Versions.playServices}"
    const val playServicesGcm = "com.google.android.gms:play-services-gcm:${Versions.playServices}"
    const val playServicesLocation = "com.google.android.gms:play-services-location:${Versions.playServices}"
    const val playServicesAnalytics = "com.google.android.gms:play-services-analytics:${Versions.playServices}"
//    const val supportAppCompat = "com.android.support:appcompat-v7:${Versions.supportLibs}"
//    const val supportCardView = "com.android.support:cardview-v7:${Versions.supportLibs}"
//    const val supportDesign = "com.android.support:design:${Versions.supportLibs}"
//    const val supportPercent = "com.android.support:percent:${Versions.supportLibs}"
//    const val supportV13 = "com.android.support:support-v13:${Versions.supportLibs}"
//    const val supportRecyclerView = "com.android.support:recyclerview-v7:${Versions.supportLibs}"
//    const val supportVectorDrawable = "com.android.support:support-vector-drawable:${Versions.supportLibs}"
//    const val supportAnimatedDrawable = "com.android.support:animated-vector-drawable:${Versions.supportLibs}"


    // UI libs.
    const val bright = "com.github.damson:Bright:${Versions.bright}"
    const val butterKnife = "com.jakewharton:butterknife:${Versions.butterKnife}"
    const val butterKnifeCompiler = "com.jakewharton:butterknife-compiler:${Versions.butterKnife}"
    const val conductor = "com.bluelinelabs:conductor:${Versions.conductor}"
    const val conductorSupport = "com.bluelinelabs:conductor-support:${Versions.conductor}"
    const val groupie = "com.xwray:groupie:${Versions.groupie}"
    const val groupieDatabinding = "com.xwray:groupie-databinding:${Versions.groupie}"
    const val groupieKotlinAndroidExtensions = "com.xwray:groupie-kotlin-android-extensions:${Versions.groupie}"
    const val gravitySnapHelper = "com.github.rubensousa:gravitysnaphelper:${Versions.gravitySnapHelper}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"


    // Reactive.
    const val rxJava = "io.reactivex:rxjava:${Versions.rxJava}"
    const val rxJava2 = "io.reactivex.rxjava2:rxjava:${Versions.rxJava2}"
    const val rxKotlin2 = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlin2}"
    const val rxJavaInterop = "com.github.akarnokd:rxjava2-interop:${Versions.rxJavaInterop}"
    const val rxJavaAsyncUtil = "io.reactivex:rxjava-async-util:${Versions.rxJavaAsyncUtil}"
    const val rxJavaProGuardRules = "com.artemzin.rxjava:proguard-rules:${Versions.rxJavaProGuardRules}"
    const val rxAndroid = "io.reactivex:rxandroid:${Versions.rxAndroid}"
    const val rxAndroid2 = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid2}"
    const val rxRelay = "com.jakewharton.rxrelay:rxrelay:${Versions.rxRelay}"
    const val rxRelay2 = "com.jakewharton.rxrelay2:rxrelay:${Versions.rxRelay2}"
    const val rxLifecycle2 = "com.trello.rxlifecycle2:rxlifecycle:${Versions.rxLifecycle2}"
    const val rxLifecycle2Android = "com.trello.rxlifecycle2:rxlifecycle-android:${Versions.rxLifecycle2}"
    const val rxReplayingShare = "com.jakewharton.rx2:replaying-share:${Versions.rxReplayingShare}"
    const val rxReplayingShareKotlin = "com.jakewharton.rx2:replaying-share-kotlin:${Versions.rxReplayingShare}"
    const val rxPreferences2 = "com.f2prateek.rx.preferences2:rx-preferences:${Versions.rxPreferences2}"
    const val rxBinding = "com.jakewharton.rxbinding:rxbinding:${Versions.rxBinding}"
    const val rxBinding2 = "com.jakewharton.rxbinding2:rxbinding:${Versions.rxBinding2}"
    const val rxBinding2Support = "com.jakewharton.rxbinding2:rxbinding-support-v4:${Versions.rxBinding2}"
    const val rxBinding2AppCompat = "com.jakewharton.rxbinding2:rxbinding-appcompat-v7:${Versions.rxBinding2}"
    const val rxBinding2Design = "com.jakewharton.rxbinding2:rxbinding-design:${Versions.rxBinding2}"
    const val rxBinding2Recyclerview = "com.jakewharton.rxbinding2:rxbinding-recyclerview-v7:${Versions.rxBinding2}"
    const val rxBinding2Kotlin = "com.jakewharton.rxbinding2:rxbinding-kotlin:${Versions.rxBinding2}"
    const val rxBinding2SupportKotlin =  "com.jakewharton.rxbinding2:rxbinding-support-v4-kotlin:${Versions.rxBinding2}"
    const val rxBinding2AppCompatKotlin = "com.jakewharton.rxbinding2:rxbinding-appcompat-v7-kotlin:${Versions.rxBinding2}"
    const val rxBinding2DesignKotlin = "com.jakewharton.rxbinding2:rxbinding-design-kotlin:${Versions.rxBinding2}"
    const val rxBinding2RecyclerviewKotlin = "com.jakewharton.rxbinding2:rxbinding-recyclerview-v7-kotlin:${Versions.rxBinding2}"
    const val rxUiLib2 = "com.artemzin.rxui2:rxui:${Versions.rxUiLib2}"
    const val rxUiLib2Kotlin = "com.artemzin.rxui2:rxui-kotlin:${Versions.rxUiLib2}"
    const val rxRedux = "com.freeletics.rxredux:rxredux:${Versions.rxRedux}"

    // Others.              =
    const val retrofit2 = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit2GsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val retrofit2MoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    const val retrofit2RxAdapter = "com.squareup.retrofit2:adapter-rxjava:${Versions.retrofit}"
    const val retrofit2Rx2Adapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
    const val retrolambda = "net.orfjackal.retrolambda:retrolambda:${Versions.retrolambda}"
    const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
    const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val daggerAndroid = "com.google.dagger:dagger-android:${Versions.dagger}"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
    const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
    const val jsr305 = "com.google.code.findbugs:jsr305:${Versions.jsr305}"
    const val okHttp3 = "com.squareup.okhttp3:okhttp:${Versions.okHttp}"
    const val okHttp3LoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
    const val okhttp3CookieJar = "com.github.franmontiel:PersistentCookieJar:${Versions.okhttpCookieJar}"
    const val okio = "com.squareup.okio:okio:${Versions.okio}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val moshi = "com.squareup.moshi:moshi:${Versions.moshi}"
    const val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshi}"
    const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val jacksonCore = "com.fasterxml.jackson.core:jackson-core:${Versions.jackson}"
    const val jacksonDatabind = "com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}"
    const val guava = "com.google.guava:guava:${Versions.guava}"
    const val javapoet = "com.squareup:javapoet:${Versions.javapoet}"
    const val immutablesvalue = "org.immutables:const value:${Versions.immutables}"
    const val immutablesvalueProcessor = "org.immutables:const value-processor:${Versions.immutables}"
    const val immutablesGson = "org.immutables:gson:${Versions.immutables}"
    const val supportAnnotations = "com.android.support:support-annotations:${Versions.supportLibs}"
    const val supportMultiDex = "com.android.support:multidex:${Versions.supportMultiDex}"
    const val javaxInject = "javax.inject:javax.inject:${Versions.javaxInject}"
    const val autoFactory = "com.google.auto.factory:auto-factory:${Versions.autoFactory}"
    const val autoService = "com.google.auto.service:auto-service:${Versions.autoService}"
    const val autoCommon = "com.google.auto:auto-common:${Versions.autoCommon}"
    const val autovalue = "com.google.auto.const value:auto-const value:${Versions.autovalue}"
    const val autovalueMoshi = "com.ryanharter.auto.const value:auto-const value-moshi:${Versions.autovalueMoshi}"
    const val autovalueWith = "com.gabrielittner.auto.const value:auto-const value-with:${Versions.autovalueWith}"
    const val autovalueParcel = "com.ryanharter.auto.const value:auto-const value-parcel:${Versions.autovalueParcel}"
    const val autovalueParcelAdapter = "com.ryanharter.auto.const value:auto-const value-parcel-adapter:${Versions.autovalueParcel}"
    const val javaWriter = "com.squareup:javawriter:${Versions.javaWriter}"
    const val javax = "javax.inject:javax.inject:${Versions.javax}"
    const val jodaTime = "joda-time:joda-time:${Versions.jodaTime}"
    const val trueTime = "com.github.instacart.truetime-android:library-extension-rx:${Versions.trueTime}"
    const val sqlBrite = "com.squareup.sqlbrite:sqlbrite:${Versions.sqlBrite}"
    const val libPhonenumber = "com.googlecode.libphonenumber:libphonenumber:${Versions.libphonenumber}"
    const val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    const val koinAndroidXViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"

    // Debugging & Inspection.
    const val slf4jNoOp = "org.slf4j:slf4j-api:${Versions.slf4j}"
    const val slf4jTesting = "org.slf4j:slf4j-simple:${Versions.slf4j}"
    const val slf4jAndroid = "org.slf4j:slf4j-android:${Versions.slf4j}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val timberCrashlyticsTrees = "net.ypresto.timbertreeutils:timbertreeutils:${Versions.timberCrashlyticsTrees}"
    const val retrofitMock = "com.squareup.retrofit2:retrofit-mock:${Versions.retrofit}"
    const val debugDrawerView = "io.palaima.debugdrawer:debugdrawer-view:${Versions.debugDrawer}"
    const val debugDrawerCommons = "io.palaima.debugdrawer:debugdrawer-commons:${Versions.debugDrawer}"
    const val debugDrawerScalpel = "io.palaima.debugdrawer:debugdrawer-scalpel:${Versions.debugDrawer}"
    const val debugDrawerLocation = "io.palaima.debugdrawer:debugdrawer-location:${Versions.debugDrawer}"
    const val debugDrawerTimber = "io.palaima.debugdrawer:debugdrawer-timber:${Versions.debugDrawer}"
    const val debugDrawerFps = "io.palaima.debugdrawer:debugdrawer-fps:${Versions.debugDrawer}"
    const val scalpel = "com.jakewharton.scalpel:scalpel:${Versions.scalpel}"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
    const val rxLifecycleLint = "io.vokal.lint:rxlifecycle:${Versions.rxLifecycleLint}"
    const val processPhoenix = "com.jakewharton:process-phoenix:${Versions.processPhoenix}"
    const val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"
    const val stethoOkhttp = "com.facebook.stetho:stetho-okhttp3:${Versions.stetho}"
    const val takt = "jp.wasabeef:takt:${Versions.takt}"

    // Testing.             
    const val junit = "junit:junit:${Versions.junit}"
    const val junitParams = "pl.pragmatists:JUnitParams:${Versions.junitParams}"
    const val junitPlatformRunner = "org.junit.platform:junit-platform-runner:${Versions.junitPlatform}" //For running JUnit5 tests as part of JUnit4 run
    const val assertJ = "org.assertj:assertj-core:${Versions.assertJ}"
    const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
    const val robolectricMultiDex = "org.robolectric:shadows-multidex:${Versions.robolectric}"
    const val supportTestRunner = "com.android.support.test:runner:${Versions.supportTestRunner}"
    const val supportTestRules = "com.android.support.test:rules:${Versions.supportTestRunner}"
    const val espressoCore = "com.android.support.test.espresso:espresso-core:${Versions.espresso}"
    const val espressoContrib = "com.android.support.test.espresso:espresso-contrib:${Versions.espresso}"
    const val espressoIntents = "com.android.support.test.espresso:espresso-intents:${Versions.espresso}"
    const val espressoAccessibility = "com.android.support.test.espresso:espresso-accessibility:${Versions.espresso}"
    const val espressoWeb = "com.android.support.test.espresso:espresso-web:${Versions.espresso}"
    const val espressoIdlingResource = "com.android.support.test.espresso:espresso-idling-resource:${Versions.espresso}"
    const val espressoIdlingConcurrent = "com.android.support.test.espresso:espresso.idling:idling-concurrent:${Versions.espresso}"
    const val mockWebserver = "com.squareup.okhttp3:mockwebserver:${Versions.okHttp}"
    const val compileTesting = "com.google.testing.compile:compile-testing:${Versions.compileTesting}"
    const val wireMock = "com.github.tomakehurst:wiremock:${Versions.wireMock}"
    const val dexMaker = "com.google.dexmaker:dexmaker:${Versions.dexMaker}"
    const val dexMakerMockito = "com.google.dexmaker:dexmaker-mockito:${Versions.dexMaker}"
    const val rxUiLib2Test = "com.artemzin.rxui2:rxui-test:${Versions.rxUiLib2}"
    const val mockitoKotlin = "com.nhaarman:mockito-kotlin:${Versions.mockitoKotlin}"
    const val androidxRoomTesting = "androidx.room:room-testing:${Versions.androidxRoom}"
    const val androidxTestRunner = "androidx.test:runner:${Versions.androidxTestRunner}"
    const val androidxEspressoCore = "androidx.test.espresso:espresso-core:${Versions.androidxEspresso}"
    const val androidxEspressoContrib = "androidx.test.espresso:espresso-contrib:${Versions.androidxEspresso}"
    const val androidxEspressoIntents = "androidx.test.espresso:espresso-intents:${Versions.androidxEspresso}"
    const val androidxEspressoAccessibility = "androidx.test.espresso:espresso-accessibility:${Versions.androidxEspresso}"
    const val androidxEspressoWeb = "androidx.test.espresso:espresso-web:${Versions.androidxEspresso}"
    const val androidxEspressoIdlingResource = "androidx.test.espresso:espresso-idling-resource:${Versions.androidxEspresso}"
    const val androidxEspressoIdlingConcurrent = "androidx.test.espresso.idling:idling-concurrent:${Versions.androidxEspresso}"
    const val androidxEspressoIdlingNet = "androidx.test.espresso.idling:idling-net:${Versions.androidxEspresso}"
    const val androidxNavigationTestingKtx = "android.arch.navigation:navigation-testing-ktx:${Versions.androidxNavigation}"
    const val spek1PlatformEngine = "org.jetbrains.spek:spek-junit-platform-engine:${Versions.spek1}"
    const val spek1Api = "org.jetbrains.spek:spek-api:${Versions.spek1}"


}

/**
 * The build-script dependencies, gradle plugins etc.
 *
 * Applied using ``` classpath(BuildLibs.xxx) ``` in the root build.gradle.kts
 */
object BuildLibs {


    /**
     * The repositories hosting the build scripts/plugins below
     *
     * Invoke this in place of repositories { .. } in the root build.gradle.kts
     *
     * Ie.
     * ```
     * repositories(BuildLibs.repositories)
     * ```
     */
    val repositories: RepositoryHandler.() -> Unit = {
        google()
        jcenter()
    }

    const val androidXNavigationPlugin = "android.arch.navigation:navigation-safe-args-gradle-plugin:${Versions.androidxNavigation}"
    const val junitPlatformGradlePlugin = "org.junit.platform:junit-platform-gradle-plugin:${Versions.junitPlatform}"
    const val junit5AndroidPlugin = "de.mannodermaus.gradle.plugins:android-junit5:${Versions.junit5Android}"
}
