plugins {
    id(Plugins.androidLibrary)
    id(Plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = Application.namespace.plus(".core").plus(".designsystem")
    compileSdk = Application.compileSdk

    defaultConfig {
        minSdk = Application.minSdk

        testInstrumentationRunner = Application.testInstrumentationRunner
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Application.kotlinCompilerExtensionVersion
    }
    kotlinOptions {
        jvmTarget = Application.jvmTarget
    }
}

dependencies {
    coreLibraryDesugaring (Android.androidToolsDesugar)
    with(Kotlin){
        implementation(stdlib)
    }
    with(Android) {
        implementation(androidCore)
        implementation(appcompat)
    }
    with(Compose) {
        implementation(composeBom)
        implementation(activity)
        implementation(ui)
        implementation(composeCoil)
        implementation(uiToolingPreview)
        implementation(uiTooling)
        implementation(material3)
        implementation(material)
        implementation(windowSizeClass)
        implementation(foundation)
        implementation(navigation)
    }
    with(IconsPack) {
        api(fontAwesome)
        api(lineAwesome)
        api(evaIcons)
        api(octicons)
        api(simpleIcon)
    }
    with(CountryCode){
        implementation(countryCode)
    }
}