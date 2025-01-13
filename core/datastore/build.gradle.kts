import com.google.protobuf.gradle.*

plugins {
    id(Plugins.androidLibrary)
    id(Plugins.jetbrainsKotlinAndroid)
    id (Plugins.protobuf) version Plugins.protobufPluginVersion
    kotlin(Plugins.kapt)
}

android {
    namespace = Application.namespace.plus(".core").plus(".datastore")
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
    kapt {
        correctErrorTypes = true
    }
}

// Setup protobuf configuration, generating lite Java and Kotlin classes
protobuf {
    protoc {
        artifact = DataStore.protobufProtoc
    }

    plugins {
        generateProtoTasks {
            all().forEach {
                it.builtins {
                    create("java") {
                        option("lite")
                    }
                    create("kotlin") {
                        option("lite")
                    }
                }
            }
        }
    }

}
dependencies {
    coreLibraryDesugaring (Android.androidToolsDesugar)
    implementation(project(":core:common"))
    api(project(":core:model"))
    with(Kotlin){
        implementation(stdlib)
        implementation(kotlinSerializationJson)
    }

    with(Android) {
        implementation(androidCore)
        implementation(lifecycleRuntime)
    }

    with(Hilt){
        implementation(hiltAndroid)
        implementation(hiltNavigationCompose)
        kapt(hiltAndroidCompiler)
    }

    with(Room){
        implementation(roomRuntime)
        implementation(roomKTX)
        kapt(roomCompiler)
    }

    with(Google) {
        implementation(gson)
    }
}
