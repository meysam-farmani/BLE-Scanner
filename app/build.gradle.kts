plugins {
    id(Plugins.androidApplication)
    id(Plugins.jetbrainsKotlinAndroid)
    id(Plugins.daggerHiltAndroid)
    id(Plugins.kotlinPluginKapt)
}

android {
    namespace = Application.namespace
    compileSdk = Application.compileSdk

    defaultConfig {
        applicationId = Application.applicationId
        minSdk = Application.minSdk
        versionCode = Application.versionCode
        versionName = Application.versionName
        targetSdk = Application.targetSdk
        testInstrumentationRunner = Application.testInstrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    Flavors.flavorDimensions.forEach {
        flavorDimensions.add(it)
    }

    productFlavors {
        Flavors.flavors.forEach { flavor ->
            create(flavor.name) {
                dimension = flavor.dimension
//                flavor.applicationIdSuffix?.let { applicationIdSuffix = it }
                flavor.versionNameSuffix?.let { versionNameSuffix = it }
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
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {

        kotlinCompilerExtensionVersion = Application.kotlinCompilerExtensionVersion
    }
}

dependencies {
    coreLibraryDesugaring (Android.androidToolsDesugar)
    implementation(project(":core:designsystem"))
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":feature:splash"))
    implementation(project(":feature:ble"))
    implementation(project(":core:common"))

    with(Kotlin){
        implementation(stdlib)
    }

    with(Android){
        implementation(androidCore)
        implementation(lifecycleRuntime)
        implementation(androidxActivity)
        implementation(animation)
    }

    with(Canary){
        debugImplementation(canary)
    }

    with(Accompanist) {
        implementation(systemUiController)
        implementation(permission)
        implementation(flowLayout)
    }

    with(Coroutines){
        implementation(androidCoroutines)
        implementation(coreCoroutines)
        implementation(workManager)
    }

    with(Compose){
        implementation(composeBom)
        implementation(activity)
        implementation(ui)
//        implementation ((composeLifecycle))
        implementation(composeCoil)
        implementation(uiToolingPreview)
        implementation(uiTooling)
        implementation(material3)
        implementation(material)
        implementation(windowSizeClass)
        implementation(foundation)
        implementation(navigation)
    }

    with(Test){
        testImplementation(junit)
        androidTestImplementation(testJunit)
        androidTestImplementation(espresso)
        androidTestImplementation(uiTestJunit4)
        debugImplementation(uiTestManifest)
    }
    
    with(Ktor){
        implementation(ktorSerializationJson)
        implementation(ktorClientCore)
        implementation(clientSerialization)
        implementation(ktorClientCio)
        implementation(ktorClientAndroid)
        implementation(ktorClientOkhttp)
        implementation(ktorLogging)
        implementation(ktorClientContentNegotiation)
    }

    with(Hilt) {
        implementation(hiltAndroid)
        implementation(hiltNavigationCompose)
        kapt(hiltAndroidCompiler)
        implementation(hiltWork)
        kapt(hiltWorkCompiler)
    }

    with(Room){
        implementation(roomRuntime)
        implementation(roomKTX)
        kapt(roomCompiler)
    }

    implementation(Timber.timber)
}