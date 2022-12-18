open module com.example.snake {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.media;
  requires java.desktop;
    requires com.fasterxml.jackson.databind;
    exports com.example.snake;
  exports com.example.snake.player;
}