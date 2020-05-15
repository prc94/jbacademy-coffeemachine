import java.util.Date
import java.text.SimpleDateFormat

plugins {
    kotlin("jvm") version "1.3.72"
    id("maven-publish")
    application
}

project.properties["groupId"]?.let { group = it }
project.properties["version"]?.let { version = it }

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

application {
    mainClassName = "machine.MainKt"
}

tasks.jar {
    manifest {
        attributes(
                "Implementation-Title" to project.name,
                "Implementation-Vendor" to project.group,
                "Implementation-Version" to project.version,
                "Implementation-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz").format(Date())
        )
    }
}

publishing {
    publications {
        create<MavenPublication>("dist") {
            groupId = "$group"
            artifactId = project.name
            version = "${project.version}"
            artifact("$buildDir/distributions/${project.name}.tar")
            artifact("$buildDir/distributions/${project.name}.zip")
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/prc94/${project.name}")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}