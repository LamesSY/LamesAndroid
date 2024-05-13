import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.util.archivesName
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

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.github.liujingxing.rxhttp:rxhttp:3.2.4")
    implementation("com.github.bumptech.glide:glide:4.13.1")
    implementation("com.github.liangjingkanji:BRV:1.5.8")
    implementation("com.tencent:mmkv-static:1.2.12")
    implementation("io.github.scwang90:refresh-layout-kernel:2.1.0")
    implementation("io.github.scwang90:refresh-header-classics:2.1.0")
    implementation("io.github.scwang90:refresh-footer-classics:2.1.0")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.android.flexbox:flexbox:3.0.0")
    implementation("com.kingja.loadsir:loadsir:1.3.8")
    implementation("com.github.getActivity:XXPermissions:18.62")

    kapt("com.github.liujingxing.rxhttp:rxhttp-compiler:3.2.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}
