/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    // Support convention plugins written in Kotlin. Convention plugins are build scripts in 'src/main' that automatically become available as plugins in the main build.
    `kotlin-dsl`
    `maven-publish`

}

repositories {
    // Use the plugin portal to apply community plugins in convention plugins.
    gradlePluginPortal()
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/austral-ingsis/printscript-parser-common")
        credentials {
            username = /*project.findProperty("user") as String?*/ System.getenv("GITHUB_ACTOR")
            password = /*project.findProperty("token") as String?*/ System.getenv("GITHUB_TOKEN")
        }
    }
}


dependencies {
    implementation("com.github.sherter.google-java-format:com.github.sherter.google-java-format.gradle.plugin:0.9")
    implementation("org.austral.ingsis.printscript:printscript-parser-common:0.1.3")

}



