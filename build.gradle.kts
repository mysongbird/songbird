import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "io.juici"
version = "0.1"

plugins {
    kotlin("jvm") version "1.2.10"
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

task<Wrapper>("wrapper") {
    gradleVersion = "4.5-rc-1"
    distributionType = Wrapper.DistributionType.ALL
}