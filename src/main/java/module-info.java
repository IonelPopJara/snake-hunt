module com.example.snake {
  requires javafx.controls;
    requires javafx.fxml;
  requires javafx.media;
  exports com.example.snake;
  exports com.example.snake.player;
  requires org.json;
  requires com.google.gson;
}