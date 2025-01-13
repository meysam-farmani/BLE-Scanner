plugins {
    id(Plugins.androidLibrary)
    id(Plugins.jetbrainsKotlinAndroid)
    kotlin(Plugins.kapt)
    id(Plugins.kotlinxSerialization)
}

android {
    namespace = Application.namespace.plus(".core").plus(".network")
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
        Flavors.flavors.forEach { flavor->
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
    implementation(project(":core:model"))
    api(project(":core:common"))
    with(Kotlin){
        implementation(stdlib)
    }
    with(Android) {
        implementation(androidCore)
        implementation(lifecycleRuntime)
    }
    with(Coroutines) {
        implementation(androidCoroutines)
        implementation(coreCoroutines)
    }

    with(Hilt) {
        implementation(hiltAndroid)
        implementation(hiltNavigationCompose)
        kapt(hiltAndroidCompiler)
    }

    with(Ktor) {
        implementation(ktorSerializationJson)
        implementation(ktorClientCore)
        implementation(clientSerialization)
        implementation(ktorClientCio)
        implementation(ktorClientAndroid)
        implementation(ktorClientOkhttp)
        implementation(ktorLogging)
        implementation(ktorClientContentNegotiation)
        implementation(ktorMock)
    }
    with(Google) {
        implementation(gson)
    }
}