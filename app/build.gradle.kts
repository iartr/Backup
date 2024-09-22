plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.artrblog.backup"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.artrblog.backup"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("${rootDir}/tools/keystore_debug")
            storePassword = "debugkey"
            keyAlias = "debug"
            keyPassword = "debugkey"
        }

        create("release") {
            storeFile = file("${rootDir}/tools/keystore_debug")
            storePassword = "debugkey"
            keyAlias = "debug"
            keyPassword = "debugkey"
        }
    }

    buildTypes {

        debug {
            isDefault = true
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs["debug"]
        }

        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            isCrunchPngs = true
            signingConfig = signingConfigs["release"]
            proguardFiles(
                "${rootDir}/tools/configs/proguard-rules-release.pro",
                "${rootDir}/tools/configs/proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}