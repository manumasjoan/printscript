/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    // Apply the java Plugin to add support for Java.
    java

    id("com.github.sherter.google-java-format")

    checkstyle

    jacoco

    `maven-publish`

}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()

    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/austral-ingsis/printscript-parser-common")
            credentials {
                username = /*project.findProperty("user") as String?*/ System.getenv("GITHUB_ACTOR")
                password = /*project.findProperty("token") as String?*/ System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

dependencies {
    constraints {
        // Define dependency versions as constraints
        implementation("org.apache.commons:commons-text:1.9")
    }

    implementation("org.junit.jupiter:junit-jupiter:5.7.0")
    // Use JUnit test framework.
    testImplementation("junit:junit:4.13.2")

    // This dependency is used by the application.
    implementation("com.google.guava:guava:30.1.1-jre")

    implementation("org.austral.ingsis.printscript:printscript-parser-common:0.1.0")

    compileOnly ("org.projectlombok:lombok:1.18.22")
    annotationProcessor ("org.projectlombok:lombok:1.18.22")
    testCompileOnly ("org.projectlombok:lombok:1.18.22")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.22")


}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/manumasjoan/printscript")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {
            version = "1.1.5"
            from(components["java"])
        }
    }
}


googleJavaFormat {
    toolVersion = "1.12.0" }

checkstyle {
    toolVersion = "9.3"
    configFile =file("${rootDir}/config/checkstyle/checkstyle.xml")
}

jacoco{
    toolVersion = "0.8.7"
}

tasks.test {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report

    reports {
        xml.required.set(false)
        csv.required.set(false)
        html.required.set(true)
    }
}


tasks.build{
    dependsOn(tasks.checkstyleMain)
}

tasks.checkstyleMain{
    dependsOn(tasks.googleJavaFormat)
}




