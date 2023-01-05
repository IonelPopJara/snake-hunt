package com.example.snake.graphics;

import java.util.Collection;
import java.util.List;

import com.example.snake.game.GameEnvironment;
import com.example.snake.model.Food;
import com.example.snake.model.FoodType;
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

  private final Image snakeHead;
  private final Image snakeTongue;
  private final Image[] snakeBodyParts;
  private final Image heartFood;
  private final Image preyFood;

  public Renderer(Canvas canvas) {
    this.canvas = canvas;
    this.snakeBodyParts = new Image[3];
    this.snakeBodyParts[0] = IOUtils.loadImage("/Sprites/Snake/snake-body-0.png");
    this.snakeBodyParts[1] = IOUtils.loadImage("/Sprites/Snake/snake-body-1.png");
    this.snakeBodyParts[2] = IOUtils.loadImage("/Sprites/Snake/snake-body-2.png");

    this.snakeHead = IOUtils.loadImage("/Sprites/Snake/snake-head.png");
    this.snakeTongue = IOUtils.loadImage("/Sprites/Snake/tongue.png");
    this.heartFood = IOUtils.loadImage("/Sprites/Food/FoodBox.png");
    this.preyFood = IOUtils.loadImage("/Sprites/Food/prey-1.png");
  }

  /**
   * Renders the snake, food and walls on the given canvas
   *
   * @param gameEnvironment the environment to retrieve the snake, food and walls from
   */
  public void draw(GameEnvironment gameEnvironment) {
    GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();

    // Calculate the size of each grid cell in pixels. Since the aspect ratio is preserved by the
    // application, we only need measurement of one dimension
    double cellSize = canvas.getWidth() / gameEnvironment.getGameFieldWidth();

    // Set the background to pure black. Done by filling with a black rectangle since the clear color
    // in JavaFX seems to be white
    graphicsContext2D.setFill(Color.valueOf(GameColor.DARK_GREY.getHexValue()));
    graphicsContext2D.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

    drawSnake(graphicsContext2D, gameEnvironment.getSnake(), cellSize);
    drawFood(graphicsContext2D, gameEnvironment.getFoods(), cellSize);
    renderWalls(graphicsContext2D, gameEnvironment, cellSize);
  }

  /**
   * Draws the snake using the given graphics context
   *
   * @param graphicsContext2D the graphics context to use for drawing the foods
   * @param snake             the snake to draw
   * @param cellSize          the size of the grid cell squares, in pixels
   */
  private void drawSnake(GraphicsContext graphicsContext2D,
                         Snake snake,
                         double cellSize) {
    GridPoint head = snake.getHead();
    float rotation = switch (snake.getDirection()) {
      case RIGHT -> 90.0F;
      case LEFT -> 270.0f;
      case UP -> 0.0F;
      case DOWN -> 180.0F;
    };

    // Draw the head, rotated. Save the state of the context before that and restore after drawing the head
    graphicsContext2D.save();
    rotate(graphicsContext2D, rotation, head.x() * cellSize + cellSize / 2, head.y() * cellSize + cellSize / 2.0);
    graphicsContext2D.drawImage(snakeHead, head.x() * cellSize, head.y() * cellSize, cellSize, cellSize);
    graphicsContext2D.drawImage(snakeTongue, head.x() * cellSize, (head.y() - 1) * cellSize, cellSize, cellSize);
    graphicsContext2D.restore();

    // Draw all the body parts
    List<GridPoint> body = snake.getBody();
    for (int i = 1; i < body.size(); i++) {
      GridPoint bodyPart = body.get(i);
      graphicsContext2D.drawImage(snakeBodyParts[i % snakeBodyParts.length], bodyPart.x() * cellSize, bodyPart.y() * cellSize, cellSize, cellSize);
    }
  }

  /**
   * Draws a set of foods using the given graphics context
   *
   * @param graphicsContext2D the graphics context to use for drawing the foods
   * @param foods             a set of foods to draw
   * @param cellSize          the size of the grid cell squares, in pixels
   */
  private void drawFood(GraphicsContext graphicsContext2D,
                        Collection<Food> foods,
                        double cellSize) {

    for (Food food : foods) {
      GridPoint position = food.getPosition();
      FoodType foodType = food.getFoodType();
      if (foodType == FoodType.PREY) {
        graphicsContext2D.drawImage(preyFood, position.x() * cellSize, position.y() * cellSize, cellSize, cellSize);
      } else {
        graphicsContext2D.drawImage(heartFood, position.x() * cellSize, position.y() * cellSize, cellSize, cellSize);
      }
    }
  }

  /**
   * Sets the transform in the given graphics context to the given rotation around a specific pivot point
   *
   * @param gc     the graphics context to set the transform to
   * @param angle  the angle of the rotation, in degrees
   * @param pivotX the x component of the pivot position
   * @param pivotY the y component of the pivot position
   */
  private void rotate(GraphicsContext gc, double angle, double pivotX, double pivotY) {
    Transform t = new Rotate(angle, pivotX, pivotY);
    gc.setTransform(t.getMxx(), t.getMyx(), t.getMxy(), t.getMyy(), t.getTx(), t.getTy());
  }

  /**
   * Renders the walls from a given game environment, if walls in said environment exist
   *
   * @param graphicsContext2D the graphics context to use for drawing the walls
   * @param gameEnvironment   the environment to retrieve the walls from
   * @param cellSize          the size of the grid cell squares, in pixels
   */
  private void renderWalls(GraphicsContext graphicsContext2D,
                           GameEnvironment gameEnvironment,
                           double cellSize) {
    graphicsContext2D.setFill(Color.DARKRED);

    // Draw edge walls, which are defined only by a boolean
    if (gameEnvironment.hasEdgeWalls()) {
      drawEdgeWalls(graphicsContext2D, gameEnvironment, cellSize);
    }

    // Draw level specific walls
    for (GridPoint wall : gameEnvironment.getWalls()) {
      graphicsContext2D.fillRect(wall.x() * cellSize, wall.y() * cellSize, cellSize, cellSize);
    }
  }

  /**
   * Draws walls on the edges of a given game environment, assuming said environment has walls on edges
   *
   * @param graphicsContext2D the graphics context to use for drawing the walls
   * @param gameEnvironment   the environment to retrieve the game field dimensions from
   * @param cellSize          the size of the grid cell squares, in pixels
   */
  private static void drawEdgeWalls(GraphicsContext graphicsContext2D,
                                    GameEnvironment gameEnvironment,
                                    double cellSize) {
    for (int x = 0; x < gameEnvironment.getGameFieldWidth(); x++) {
      graphicsContext2D.fillRect(x * cellSize, 0, cellSize, cellSize);
      graphicsContext2D.fillRect(x * cellSize, (gameEnvironment.getGameFieldHeight() - 1) * cellSize, cellSize, cellSize);
    }

    for (int y = 0; y < gameEnvironment.getGameFieldHeight(); y++) {
      graphicsContext2D.fillRect(0, y * cellSize, cellSize, cellSize);
      graphicsContext2D.fillRect((gameEnvironment.getGameFieldWidth() - 1) * cellSize, y * cellSize, cellSize, cellSize);
    }
  }
}
