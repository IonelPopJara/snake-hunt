package com.example.snake.graphics;

import java.util.Collection;
import java.util.List;

import com.example.snake.game.GameEnvironment;
import com.example.snake.model.Food;
import com.example.snake.model.GridPoint;
import com.example.snake.model.Snake;
import com.example.snake.utils.GameColor;
import com.example.snake.utils.IOUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;

public class Renderer {

  private final Canvas canvas;
  private final GraphicsContext graphicsContext2D;

  private final Image snakeHeadImage;
  private final Image snakeTongueImage;
  private final Image[] snakeBodyPartsImages;
  private final Image wallImage;

  public Renderer(Canvas canvas) {
    this.canvas = canvas;
    this.graphicsContext2D = canvas.getGraphicsContext2D();

    this.snakeBodyPartsImages = new Image[3];
    this.snakeBodyPartsImages[0] = IOUtils.loadImage("/Sprites/Snake/snake-body-0.png");
    this.snakeBodyPartsImages[1] = IOUtils.loadImage("/Sprites/Snake/snake-body-1.png");
    this.snakeBodyPartsImages[2] = IOUtils.loadImage("/Sprites/Snake/snake-body-2.png");

    this.snakeHeadImage = IOUtils.loadImage("/Sprites/Snake/snake-head.png");
    this.snakeTongueImage = IOUtils.loadImage("/Sprites/Snake/tongue.png");

    this.wallImage = IOUtils.loadImage("/Sprites/Snake/Wall-01-0.png");
  }

  /**
   * Renders the snake, food and walls on the given canvas
   *
   * @param gameEnvironment the environment to retrieve the snake, food and walls from
   */
  public void draw(GameEnvironment gameEnvironment) {

    // Calculate the size of each grid cell in pixels. Since the aspect ratio is preserved by the
    // application, we only need measurement of one dimension
    double cellSize = canvas.getWidth() / gameEnvironment.getGameFieldWidth();

    // Set the background to pure black. Done by filling with a black rectangle since the clear color
    // in JavaFX seems to be white
    graphicsContext2D.setFill(Color.valueOf(GameColor.DARK_GREY.getHexValue()));
    graphicsContext2D.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

    drawSnake(gameEnvironment.getSnake(), cellSize);
    drawFood(gameEnvironment.getFoods(), cellSize);
    renderWalls(gameEnvironment, cellSize);
  }

  /**
   * Draws the entire snake
   *
   * @param snake    the snake to draw
   * @param cellSize the size of the grid cell squares, in pixels
   */
  private void drawSnake(Snake snake, double cellSize) {
    GridPoint head = snake.getHead();
    float rotation = switch (snake.getDirection()) {
      case RIGHT -> 90.0F;
      case LEFT -> 270.0f;
      case UP -> 0.0F;
      case DOWN -> 180.0F;
    };

    // Draw the head, rotated. Save the state of the context before that and restore after drawing the head
    graphicsContext2D.save();
    rotate(rotation, head.x() * cellSize + cellSize / 2, head.y() * cellSize + cellSize / 2.0);
    graphicsContext2D.drawImage(snakeHeadImage, head.x() * cellSize, head.y() * cellSize, cellSize, cellSize);
    graphicsContext2D.drawImage(snakeTongueImage, head.x() * cellSize, (head.y() - 1) * cellSize, cellSize, cellSize);
    graphicsContext2D.restore();

    // Draw all the body parts
    List<GridPoint> body = snake.getBody();
    for (int i = 1; i < body.size(); i++) {
      GridPoint bodyPart = body.get(i);
      graphicsContext2D.drawImage(snakeBodyPartsImages[i % snakeBodyPartsImages.length], bodyPart.x() * cellSize, bodyPart.y() * cellSize, cellSize, cellSize);
    }
  }

  /**
   * Draws a set of foods
   *
   * @param foods    a set of foods to draw
   * @param cellSize the size of the grid cell squares, in pixels
   */
  private void drawFood(Collection<Food> foods,
                        double cellSize) {
    for (Food food : foods) {
      GridPoint position = food.getPosition();
      graphicsContext2D.drawImage(food.getFoodType().getImage(), position.x() * cellSize, position.y() * cellSize, cellSize, cellSize);
    }
  }

  /**
   * Sets the transform in the graphics context to the given rotation around a specific pivot point
   *
   * @param angle  the angle of the rotation, in degrees
   * @param pivotX the x component of the pivot position
   * @param pivotY the y component of the pivot position
   */
  private void rotate(double angle, double pivotX, double pivotY) {
    Transform t = new Rotate(angle, pivotX, pivotY);
    graphicsContext2D.setTransform(t.getMxx(), t.getMyx(), t.getMxy(), t.getMyy(), t.getTx(), t.getTy());
  }

  /**
   * Renders the walls from a given game environment, if walls in said environment exist
   *
   * @param gameEnvironment the environment to retrieve the walls from
   * @param cellSize        the size of the grid cell squares, in pixels
   */
  private void renderWalls(GameEnvironment gameEnvironment, double cellSize) {
    graphicsContext2D.setFill(Color.DARKRED);

    // Draw edge walls, which are defined only by a boolean
    if (gameEnvironment.hasEdgeWalls()) {
      drawEdgeWalls(gameEnvironment, cellSize);
    }

    // Draw level specific walls
    for (GridPoint wall : gameEnvironment.getWalls()) {
      graphicsContext2D.drawImage(wallImage, wall.x() * cellSize, wall.y() * cellSize, cellSize, cellSize);
    }
  }

  /**
   * Draws walls on the edges of a given game environment, assuming said environment has walls on edges
   *
   * @param gameEnvironment the environment to retrieve the game field dimensions from
   * @param cellSize        the size of the grid cell squares, in pixels
   */
  private void drawEdgeWalls(GameEnvironment gameEnvironment, double cellSize) {
    for (int x = 0; x < gameEnvironment.getGameFieldWidth(); x++) {
      graphicsContext2D.drawImage(wallImage, x * cellSize, 0, cellSize, cellSize);
      graphicsContext2D.drawImage(wallImage, x * cellSize, (gameEnvironment.getGameFieldHeight() - 1) * cellSize, cellSize, cellSize);
    }

    for (int y = 0; y < gameEnvironment.getGameFieldHeight(); y++) {
      graphicsContext2D.drawImage(wallImage, 0, y * cellSize, cellSize, cellSize);
      graphicsContext2D.drawImage(wallImage, (gameEnvironment.getGameFieldWidth() - 1) * cellSize, y * cellSize, cellSize, cellSize);
    }
  }
}
