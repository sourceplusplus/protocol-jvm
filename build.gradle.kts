import org.apache.tools.ant.taskdefs.condition.Os

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    kotlin("kapt")
    id("org.jetbrains.kotlin.plugin.noarg")
    id("maven-publish")
    id("com.diffplug.spotless")
}

val vertxVersion: String by project
val kotlinVersion: String by project
val projectVersion: String by project
val jacksonVersion: String by project
val slf4jVersion: String by project

group = "spp.protocol"
version = projectVersion

repositories {
    mavenCentral()
}

configure<PublishingExtension> {
    repositories {
        maven("file://${System.getenv("HOME")}/.m2/repository")
    }
}

kotlin {
    jvm {
        withJava()
    }
    js {
        browser { }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("org.slf4j:slf4j-api:$slf4jVersion")
                implementation("io.vertx:vertx-core:$vertxVersion")
                implementation("io.vertx:vertx-codegen:$vertxVersion")
                implementation("io.vertx:vertx-tcp-eventbus-bridge:$vertxVersion")
                implementation("io.vertx:vertx-service-proxy:$vertxVersion")
                implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
                implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:$jacksonVersion")
                implementation("com.fasterxml.jackson.datatype:jackson-datatype-guava:$jacksonVersion")
                implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
                implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
                implementation("org.jooq:jooq:3.16.4")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("io.vertx:vertx-core:$vertxVersion")
                implementation("com.google.guava:guava:31.1-jre")
                implementation("junit:junit:4.13.2")
                implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
                implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
                implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
                implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:$jacksonVersion")
                implementation("com.fasterxml.jackson.datatype:jackson-datatype-guava:$jacksonVersion")
                implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
                implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
            }
        }
    }
}

dependencies {
    "kapt"("io.vertx:vertx-codegen:$vertxVersion:processor")
}

tasks.register<Copy>("setupJsonMappers") {
    from(file("$projectDir/src/jvmMain/resources/META-INF/vertx/json-mappers.properties"))
    into(file("$buildDir/generated/source/kapt/main/META-INF/vertx"))
}
tasks.getByName("compileKotlinJvm").dependsOn("setupJsonMappers")

tasks.register<Exec>("restrictDeletionOfJsonMappers") {
    mustRunAfter("setupJsonMappers")
    doFirst {
        if (!Os.isFamily(Os.FAMILY_UNIX)) {
            ProcessBuilder(
                "cmd.exe", "/C",
                "start \"\" notepad >> $buildDir\\generated\\source\\kapt\\main\\META-INF\\vertx\\json-mappers.properties"
            ).start()
        }
    }
    if (Os.isFamily(Os.FAMILY_UNIX)) {
        if (System.getProperty("user.name") == "root") {
            commandLine("chattr", "+i", "$buildDir/generated/source/kapt/main/META-INF/vertx")
        } else {
            commandLine("chmod", "a-w", "$buildDir/generated/source/kapt/main/META-INF/vertx")
        }
    } else {
        executable("cmd.exe")
        args("/C") //no-op
    }
}
tasks.getByName("compileKotlinJvm").dependsOn("restrictDeletionOfJsonMappers")

tasks.register<Exec>("unrestrictDeletionOfJsonMappers") {
    mustRunAfter("compileKotlinJvm")
    if (Os.isFamily(Os.FAMILY_UNIX)) {
        if (file("$buildDir/generated/source/kapt/main/META-INF/vertx").exists()) {
            if (System.getProperty("user.name") == "root") {
                commandLine("chattr", "-i", "$buildDir/generated/source/kapt/main/META-INF/vertx")
            } else {
                commandLine("chmod", "a+w", "$buildDir/generated/source/kapt/main/META-INF/vertx")
            }
        } else {
            commandLine("true") //no-op
        }
    } else {
        executable("cmd.exe")
        args("/C") //no-op
    }
}
tasks.getByName("jvmJar").dependsOn("unrestrictDeletionOfJsonMappers")
tasks.getByName("clean").dependsOn("unrestrictDeletionOfJsonMappers")

configure<org.jetbrains.kotlin.noarg.gradle.NoArgExtension> {
    annotation("kotlinx.serialization.Serializable")
}

tasks.withType<JavaCompile> {
    options.release.set(8)
    sourceCompatibility = "1.8"
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

spotless {
    kotlin {
        target("**/*.kt")
        licenseHeaderFile(file("LICENSE-HEADER.txt"))
    }
}
