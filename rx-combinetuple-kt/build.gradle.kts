plugins {
    id("com.android.library")
    kotlin("android")
    id("com.github.dcendents.android-maven")
}

android {
    compileSdkVersion(28)

    defaultConfig {
        minSdkVersion(9)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    lintOptions {
        isAbortOnError = false
    }

    compileOptions {
        this.setSourceCompatibility(JavaVersion.VERSION_1_7)
        this.setTargetCompatibility(JavaVersion.VERSION_1_7)
    }
}

dependencies {
    //implementation(mapOf("dir" to "libs", "include" to listOf("*.jar")))
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.61")
    api("com.github.Zhuinden:tuples-kt:1.0.0")
    api("io.reactivex.rxjava2:rxjava:2.2.19")

    testImplementation("junit:junit:4.12")
    testImplementation("org.assertj:assertj-core:3.9.1")
    testImplementation("org.mockito:mockito-core:2.15.0")
    androidTestImplementation("junit:junit:4.12")
}

// build a jar with source files
val sourcesJar by tasks.registering(Jar::class) {
    from(android.sourceSets["main"].java.srcDirs)
    archiveClassifier.set("sources")
}

val javadoc by tasks.registering(Javadoc::class) {
    isFailOnError = false
    source = android.sourceSets["main"].java.sourceFiles
    classpath += project.files(android.bootClasspath.joinToString(separator = File.pathSeparator))
    classpath += configurations.compile
}

// build a jar with javadoc
val javadocJar by tasks.registering(Jar::class) {
    dependsOn(javadoc)
    archiveClassifier.set("javadoc")
    from(javadoc.get().destinationDir)
}

artifacts {
    archives(sourcesJar)
    archives(javadocJar)
}
