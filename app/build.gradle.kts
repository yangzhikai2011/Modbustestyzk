plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.modbustest0309"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.modbustest0309"
        minSdk = 14
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation(files("libs\\androidthings-1.0.jar"))
    implementation(files("libs\\commons-lang3-3.13.0.jar"))
    implementation(files("libs\\jSerialComm-2.0.2.jar"))
    implementation(files("libs\\jssc-2.8.0.jar"))
    implementation(files("libs\\modbus4j-3.0.3.jar"))
    implementation(files("libs\\purejavacomm.jar"))
    implementation(files("libs\\RXTXcomm.jar"))
    implementation(files("libs\\commons-logging-1.3.0.jar"))
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}