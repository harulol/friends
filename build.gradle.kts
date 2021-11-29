plugins {
    groovy
    java
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "dev.hawu.plugins"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
    maven("https://jitpack.io")
}

val shade: Configuration by configurations.creating {
    configurations.getByName("implementation").extendsFrom(this)
}

dependencies {
    implementation("org.codehaus.groovy:groovy-all:3.0.9")
    implementation("org.bukkit:bukkit:1.8-R0.1-SNAPSHOT")
    shade("com.github.harulol.hikari-library:hikari-library-commands:master-SNAPSHOT")
    shade("com.github.harulol.hikari-library:hikari-library-collections:master-SNAPSHOT")
    shade("com.github.harulol.hikari-library:hikari-library-inventories:master-SNAPSHOT")
    shade("com.github.harulol.hikari-library:hikari-library-core:1de9c26819")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.shadowJar {
    configurations = listOf(shade)
    dependencies {
        include(dependency("com.github.harulol.hikari-library:.*:.*"))
    }
    relocate("dev.hawu.plugins.api", "dev.hawu.plugins.friends.api")
    minimize()
}
