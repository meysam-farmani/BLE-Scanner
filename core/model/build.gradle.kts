plugins {
    id(Plugins.androidLibrary)
    id(Plugins.jetbrainsKotlinAndroid)
    kotlin(Plugins.kapt)
}


android {
    namespace = Application.namespace.plus(".core").plus(".model")
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
    composeOptions {
        kotlinCompilerExtensionVersion = Application.kotlinCompilerExtensionVersion
    }
    kotlinOptions {
        jvmTarget = Application.jvmTarget
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))

    coreLibraryDesugaring (Android.androidToolsDesugar)

    with(Room){
        implementation(roomRuntime)
        implementation(roomKTX)
        kapt(roomCompiler)
    }

    implementation(Timber.timber)
}