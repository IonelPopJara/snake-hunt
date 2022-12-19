plugins {
  id("application")
  id("org.openjfx.javafxplugin") version "0.0.13"
}

repositories {
  mavenCentral()
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
  implementation("com.fasterxml.jackson.core:jackson-databind:2.14.1")
}
