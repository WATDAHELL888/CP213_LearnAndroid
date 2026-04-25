plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.a522lablearnandroid"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.a522lablearnandroid"
        minSdk = 30
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    // 1. เพิ่มการตั้งค่า Keystore สำหรับ Release Mode
    signingConfigs {
        create("release") {
            storeFile = file("my-release-key.jks") // ไฟล์ Keystore ของจริง (ต้องมีไฟล์นี้อยู่ในโฟลเดอร์ app)
            storePassword = "YourStorePassword"    // รหัสผ่าน Store
            keyAlias = "YourKeyAlias"              // ชื่อ Alias
            keyPassword = "YourKeyPassword"        // รหัสผ่าน Key
        }
    }

    // 2. ตั้งค่า Build Variants (Debug & Release)
    buildTypes {
        // 🟢 DEBUG MODE สำหรับทดสอบ
        getByName("debug") {
            isDebuggable = true
            // applicationIdSuffix = ".debug" // แยกลงแอปทดสอบกับแอปจริงในเครื่องเดียวกันได้ (ปิดไว้เพื่อให้ตรงกับ google-services.json)
            versionNameSuffix = "-DEBUG"
        }

        // 🔴 RELEASE MODE สำหรับขึ้น Store
        getByName("release") {
            isDebuggable = false
            // เปลี่ยนเป็น true เพื่อเปิดการบีบอัดโค้ด (ช่วยให้แอปเล็กลงและปลอดภัยขึ้น)
            isMinifyEnabled = true
            // ลบ Resource (เช่น รูปภาพ) ที่ไม่ได้ใช้งานออก
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // ผูกกับการตั้งค่า Keystore ด้านบน
            signingConfig = signingConfigs.getByName("release")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    // จัดกลุ่ม buildFeatures ที่ซ้ำกันมารวมไว้ที่เดียว
    buildFeatures {
        buildConfig = true
        compose = true
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
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("io.coil-kt:coil-compose:2.5.0") // Check for the latest version

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Converter สำหรับแปลง JSON เป็น Data Class (Gson)
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    // Lifecycle & ViewModel สำหรับ Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    // Firebase BOM (ตัวจัดการเวอร์ชั่น ให้มันเลือกเวอร์ชั่นที่เข้ากันได้เอง)
    implementation(platform("com.google.firebase:firebase-bom:33.0.0"))

    // Cloud Firestore Library
    implementation("com.google.firebase:firebase-firestore")

    implementation("com.google.android.gms:play-services-location:21.2.0")
}
