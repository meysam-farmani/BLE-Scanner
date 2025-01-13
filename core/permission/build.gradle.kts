plugins {
    id(Plugins.androidLibrary)
    id(Plugins.jetbrainsKotlinAndroid)
    kotlin(Plugins.kapt)
}

android {
    namespace = Application.namespace.plus(".core").plus(".permission")
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
    implementation(project(":core:designsystem"))
    with(Kotlin){
        implementation(stdlib)
    }
    with(Compose) {
        implementation(composeBom)
        implementation(activity)
        implementation(ui)
//        implementation(composeLifecycle)
        implementation(composeCoil)
        implementation(uiToolingPreview)
        implementation(uiTooling)
        implementation(material3)
        implementation(material)
        implementation(windowSizeClass)
        implementation(foundation)
        implementation(navigation)
    }
    with(Hilt){
        implementation(hiltAndroid)
        implementation(hiltNavigationCompose)
        kapt(hiltAndroidCompiler)
    }
    with(Accompanist) {
        api(permission)
    }
}