plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("com.google.gms.google-services")
}

android {
    namespace = "vtsen.hashnode.dev.signindemoapp"
    compileSdk = 33

    defaultConfig {
        applicationId = "vtsen.hashnode.dev.signindemoapp"
        minSdk = 21
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled  = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro")
        }
    }
    compileOptions {
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
        //https://developer.android.com/jetpack/androidx/releases/compose-compiler
        kotlinCompilerExtensionVersion = "1.4.5"
    }
    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.1")

    implementation("androidx.compose.ui:ui:1.4.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.2")

    implementation("androidx.compose.material3:material3:1.1.0-rc01")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.28.0")

    implementation("androidx.core:core-splashscreen:1.0.1")

    implementation("com.github.vinchamp77:buildutils:0.0.8")

    //Note: doesn't seem to work
    /*
    implementation (platform("com.google.firebase:firebase-bom:32.0.0"))
    implementation ("com.firebaseui:firebase-ui-auth")
    implementation ("com.google.android.gms:play-services-auth") //required by email/passwd auth
    */

    implementation ("com.firebaseui:firebase-ui-auth:7.2.0")
    implementation ("com.google.android.gms:play-services-auth:20.5.0") //required by email/passwd auth
    implementation("com.google.firebase:firebase-database-ktx:20.2.2")
}