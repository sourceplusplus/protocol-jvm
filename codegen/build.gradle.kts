plugins {
    kotlin("jvm")
}

val vertxVersion: String by project
val joorVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.vertx:vertx-codegen:$vertxVersion")
    implementation("org.jooq:joor:$joorVersion")
}
