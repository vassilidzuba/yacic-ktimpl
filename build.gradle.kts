plugins {
    kotlin("jvm") version "2.1.21"
}

group = "vassilidzuba.yacic"
version = "1.0-SNAPSHOT"


val kotlinVersion = "2.1.21"

repositories {
    maven {
        url = uri("http://192.168.0.20:8081/repository/maven-public/")
        isAllowInsecureProtocol = true
    }
    maven {
        url = uri("http://192.168.0.20:8081/repository/maven-releases/")
        isAllowInsecureProtocol = true
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.slf4j:slf4j-api:1.7.30")
    implementation("net.java.dev.jna:jna:4.2.2")
    implementation("org.slf4j:slf4j-api:2.0.17")

    implementation("vassilidzuba.yacic:model:1.0-SNAPSHOT")
    implementation("vassilidzuba.yacic:persistence:1.0-SNAPSHOT")
    implementation("vassilidzuba.yacic:podmanutil:1.0-SNAPSHOT")

    runtimeOnly("org.jetbrains.kotlin:kotlin-main-kts:${kotlinVersion}")
    runtimeOnly("org.jetbrains.kotlin:kotlin-scripting-jsr223:${kotlinVersion}")


    testImplementation(kotlin("test"))
    testImplementation("ch.qos.logback:logback-classic:1.5.18")
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-main-kts:${kotlinVersion}")
    testRuntimeOnly("org.jetbrains.kotlin:kotlin-scripting-jsr223:${kotlinVersion}")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}