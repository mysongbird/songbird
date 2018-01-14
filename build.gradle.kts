import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "xyz.mysongbird"
version = "0.1"

plugins {
    kotlin("jvm") version "1.2.10"
    idea
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation(group = "com.mashape.unirest", name = "unirest-java", version = "1.4.9")
    implementation(group = "com.google.code.gson", name = "gson", version = "2.8.2")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

task<Wrapper>("wrapper") {
    gradleVersion = "4.5-rc-1"
    distributionType = Wrapper.DistributionType.ALL
}