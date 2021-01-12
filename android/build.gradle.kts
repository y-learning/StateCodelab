plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(Libs.AndroidX.coreKtx)
    implementation(Libs.AndroidX.appcompat)
    implementation(Libs.AndroidX.lifecycle)
    implementation(Libs.AndroidX.activity)

    implementation(Libs.AndroidMaterial.material)

    implementation(Libs.Compose.ui)
    implementation(Libs.Compose.uiTooling)
    implementation(Libs.Compose.material)
    implementation(Libs.Compose.icons)
    implementation(Libs.Compose.animation)
    implementation(Libs.Compose.runtime)
    implementation(Libs.Compose.runtimeLivedata)
    implementation(Libs.Compose.foundation)
    implementation(Libs.Compose.foundationLayout)

    testImplementation(Libs.Kotest.runner)
    testImplementation(Libs.Kotest.assertions)
    testImplementation(Libs.Kotest.property)
}

android {
    compileSdkVersion(30)
    buildToolsVersion("30.0.3")

    defaultConfig {
        applicationId = "com.why.codelabs.state"
        minSdkVersion(22)
        targetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = Libs.jvmTarget
        useIR = true
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Libs.Compose.version
        kotlinCompilerVersion = Libs.kotlinVersion
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
