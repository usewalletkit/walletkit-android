plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-parcelize")
    id("org.openapi.generator") version "6.6.0"
    id("maven-publish")
    id("signing")
}

object Versions {
    const val retrofit = "2.9.0"
}

val openApiSpecUrl: String by project

android {
    namespace = "com.usewalletkit.sdk"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath("org.openapitools:openapi-generator-gradle-plugin:6.6.0")
    }
}

openApiGenerate {
    generatorName.set("kotlin")
    remoteInputSpec.set("$openApiSpecUrl")
    outputDir.set("$projectDir")
    configFile.set("$projectDir/openapi-config.yml")
}

signing {
    val signingKey: String? by project
    val signingPassword: String? by project
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = "com.usewalletkit.sdk"
            artifactId = "sdk"
            version = System.getenv("SDK_RELEASE_VERSION")
            afterEvaluate { artifact(tasks.getByName("bundleReleaseAar")) }

            pom {
                name.set("sdk")
                description.set("Official WalletKit SDK<")
                url.set("https://usewalletkit.com")
                licenses {
                    license {
                        name.set("The Apache Software License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("emanuelconunaemme")
                        name.set("Emanuel Mazzilli")
                        email.set("emanuel.mazzilli@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/usewalletkit/walletkit-android.git")
                    developerConnection.set(
                        "scm:git:ssh://github.com:usewalletkit/walletkit-android.git"
                    )
                    url.set("https://github.com/usewalletkit/walletkit-android/tree/main")
                }
            }
        }
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.security:security-crypto:1.1.0-alpha01")
    implementation("com.google.android.material:material:1.10.0")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")
    implementation("com.squareup.moshi:moshi-adapters:1.13.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:converter-moshi:${Versions.retrofit}")
    implementation("com.squareup.retrofit2:converter-scalars:${Versions.retrofit}")
    implementation("io.github.jan-tennert.supabase:gotrue-kt:1.4.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$version")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
