val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val koinVersion: String by project
val exposedVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.6.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.0"
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "com.herokuapp.ourairports"
version = "0.0.2"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    project.setProperty("mainClassName", mainClass.get())
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

val sshAntTask = configurations.create("sshAntTask")

dependencies {
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("io.ktor:ktor-mustache:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")

    implementation("io.insert-koin:koin-ktor:$koinVersion")

    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("com.h2database:h2:1.4.199")

    implementation("aws.sdk.kotlin:s3:0.9.4-beta")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("io.ktor:ktor-client-mock:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
    testImplementation("io.insert-koin:koin-test:$koinVersion")

    sshAntTask("org.apache.ant:ant-jsch:1.10.11")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=kotlin.RequiresOptIn"
}

tasks.create("stage") {
    dependsOn("installDist")
}

tasks{
    shadowJar {
        manifest {
            attributes("Main-Class" to application.mainClass.get())
        }
    }
}

ant.withGroovyBuilder {
    "taskdef"(
        "name" to "scp",
        "classname" to "org.apache.tools.ant.taskdefs.optional.ssh.Scp",
        "classpath" to configurations.get("sshAntTask").asPath
    )
    "taskdef"(
        "name" to "ssh",
        "classname" to "org.apache.tools.ant.taskdefs.optional.ssh.SSHExec",
        "classpath" to configurations.get("sshAntTask").asPath
    )
}


task("deploy") {
    dependsOn("clean", "shadowJar")
    ant.withGroovyBuilder {
        doLast {
            val knownHosts = File.createTempFile("knownhosts", "txt")
            val user = "ubuntu"
            val host = "ec2-13-51-70-132.eu-north-1.compute.amazonaws.com"
            val pk = file("keys/amazon-1.pem")
            val jarFileName = "com.herokuapp.ourairports-$version-all.jar"
            try {
                "scp"(
                    "file" to file("build/libs/$jarFileName"),
                    "todir" to "$user@$host:/home/$user/our-airports",
                    "keyfile" to pk,
                    "trust" to true,
                    "knownhosts" to knownHosts
                )
                "ssh"(
                    "host" to host,
                    "username" to user,
                    "keyfile" to pk,
                    "trust" to true,
                    "knownhosts" to knownHosts,
                    "command" to "mv /home/$user/our-airports/$jarFileName /home/$user/our-airports/our-airports-v1.jar"
                )
                "ssh"(
                    "host" to host,
                    "username" to user,
                    "keyfile" to pk,
                    "trust" to true,
                    "knownhosts" to knownHosts,
                    "command" to "sudo systemctl stop our-airports"
                )
                "ssh"(
                    "host" to host,
                    "username" to user,
                    "keyfile" to pk,
                    "trust" to true,
                    "knownhosts" to knownHosts,
                    "command" to "sudo systemctl start our-airports"
                )
            } finally {
                knownHosts.delete()
            }
        }
    }
}
