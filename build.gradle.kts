plugins {
  id("application")
  id("org.openjfx.javafxplugin") version "0.0.13"
}

repositories {
  mavenCentral()
  google()
}

tasks.withType<JavaCompile> {
  options.encoding = "UTF-8"
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

application {
  mainModule.set("com.example.snake")
  mainClass.set("com.example.snake.SnakeApplication")
}

javafx {
  version = "19"
  modules = listOf("javafx.controls", "javafx.fxml", "javafx.media")
}

dependencies {
  implementation ("org.json:json:20220924")
    implementation("com.google.code.gson:gson:2.10")
}
