plugins {
    id(Plugins.androidLibrary)
    id(Plugins.jetbrainsKotlinAndroid)
    kotlin(Plugins.kapt)
    kotlin(Plugins.pluginSerialization)
}

android {
    namespace = Application.namespace.plus(".core").plus(".data")
    compileSdk = Application.compileSdk
    defaultConfig {
        minSdk = Application.minSdk
        
        testInstrumentationRunner = Application.testInstrumentationRunner
        consumerProguardFiles("consumer-rules.pro")
    }

    Flavors.flavorDimensions.forEach {
        flavorDimensions.add(it)
    }

    productFlavors {
        Flavors.flavors.forEach { flavor ->
            create(flavor.name) {
                dimension = flavor.dimension
                flavor.buildConfigs.forEach {
                    buildConfigField(it.type, it.name, it.value)
                }
            }
        }
    }

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Application.jvmTarget
    }
}

dependencies {
    coreLibraryDesugaring (Android.androidToolsDesugar)

    implementation(project(":core:designsystem"))
    api(project(":core:permission"))
    api(project(":core:datastore"))
    implementation(project(":core:network"))
    api(project(":core:common"))

    implementation(Kotlin.stdlib)
    implementation(Google.guavaAndroid)

    implementation(Android.androidCore)
    implementation(Android.lifecycleRuntime)
    implementation(Android.androidxExifinterface)

    implementation(Google.playServiceLocation)
    implementation(Google.gson)

    implementation(Hilt.hiltAndroid)
    implementation(Hilt.hiltNavigationCompose)
    kapt(Hilt.hiltAndroidCompiler)

    implementation(Kotlin.kotlinCSV)
    implementation(Kotlin.kotlinSerializationJson)

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.3.2")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.3.2")

    implementation(Coroutines.androidCoroutines)
    implementation(Coroutines.coreCoroutines)

    implementation(Kotlin.kotlinxDatetime)

    with(Ktor) {
        implementation(ktorClientCore)
    }

    implementation(Google.material)

    implementation(Timber.timber)

//    with(Koin) {
//        implementation(koinAndroid)
//        implementation(koinAndroidCompat)
//        implementation(koinAndroidxWorkManager)
//        implementation(koinAndroidxNavigation)
//        implementation(koinAndroidxCompose)
//    }
}
