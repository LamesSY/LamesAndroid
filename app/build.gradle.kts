import java.util.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "com.lames.standard"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.lames.standard"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
        setProperty("archivesBaseName", "LamesApp_${versionName}_${versionCode}")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
        compose = true
    }

    buildTypes {
        debug { signingConfig = null }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    signingConfigs {
        create("lames") {
            storeFile = file(keystoreProperties.getProperty("storeFile"))
            storePassword = keystoreProperties.getProperty("storePassword")
            keyAlias = keystoreProperties.getProperty("keyAlias")
            keyPassword = keystoreProperties.getProperty("keyPassword")
        }
    }
    flavorDimensions += "api"
    productFlavors {
        create("standard") {
            dimension = "api"
            signingConfig = signingConfigs.getByName("lames")
        }
    }

}

dependencies {
    implementation(libs.androidxKtx)
    implementation(libs.appcompat)
    implementation(libs.activityKtx)
    implementation(libs.fragmentKtx)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.okhttp)
    implementation(libs.rxhttp)
    implementation(libs.glide)
    implementation(libs.brv)
    implementation(libs.mmkv)
    implementation(libs.refreshKernel)
    implementation(libs.refreshHeader)
    implementation(libs.refreshFooter)
    implementation(libs.gson)
    implementation(libs.flexbox)
    implementation(libs.loadsir)
    implementation(libs.permissions)

    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    kapt(libs.rxhttpCompiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.exJunit)
    androidTestImplementation(libs.espressoCore)

}
