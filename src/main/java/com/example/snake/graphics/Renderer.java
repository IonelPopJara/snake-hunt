package com.example.snake.graphics;

import java.util.Collection;
import java.util.List;

import com.example.snake.model.GridPoint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Renderer {

  private static final boolean DRAW_GRID = true;

  private final Canvas canvas;

  public Renderer(Canvas canvas) {
    this.canvas = canvas;
  }

  /**
   * @param gameFieldWidth  The width of the game field in the number of grid cells
   * @param gameFieldHeight The height of the game field in the number of grid cells
   * @param snake           A list of the snakes body parts, where the first element
   *                        is the head
   * @param foods           A set of positions of food items
   */
  public void draw(int gameFieldWidth, int gameFieldHeight, List<GridPoint> snake, Collection<GridPoint> foods) {
    GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
    double cellWidth = canvas.getWidth() / gameFieldWidth;
    double cellHeight = canvas.getHeight() / gameFieldHeight;

    // Set the background to pure black. Done by filling with a black rectangle since the clear color
    // in JavaFX seems to be white
    graphicsContext2D.setFill(Color.BLACK);
    graphicsContext2D.fillRect(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);

    // Draw a grid to help visualize for debugging purposes
    if (DRAW_GRID) {
      drawGrid(graphicsContext2D, gameFieldWidth, gameFieldHeight, cellWidth, cellHeight);
    }

    // Draw the snake and the food
    drawSnake(graphicsContext2D, snake, cellWidth, cellHeight);
    drawFood(graphicsContext2D, foods, cellWidth, cellHeight);
  }

  private static void drawGrid(GraphicsContext graphicsContext2D,
                               int gameFieldWidth,
                               int gameFieldHeight,
                               double cellWidth,
                               double cellHeight) {
    graphicsContext2D.setStroke(Color.WHITE);

    for (int x = 0; x < gameFieldWidth; x++) {
      graphicsContext2D.strokeLine(x * cellWidth, 0, x * cellWidth, Integer.MAX_VALUE);
    }
    for (int y = 0; y < gameFieldHeight; y++) {
      graphicsContext2D.strokeLine(0, y * cellHeight, Integer.MAX_VALUE, y * cellWidth);
    }
  }

  private static void drawSnake(GraphicsContext graphicsContext2D,
                                List<GridPoint> snake,
                                double cellWidth,
                                double cellHeight) {
    // Draw the head in a different color. It's the first element of the list
    graphicsContext2D.setFill(Color.RED);
    GridPoint head = snake.get(0);
    graphicsContext2D.fillRect(head.x() * cellWidth, head.y() * cellHeight, cellWidth, cellHeight);

    // Draw the rest of the body, starting at the second element of the list
    graphicsContext2D.setFill(Color.WHITE);
    for (int i = 1; i < snake.size(); i++) {
      GridPoint bodyPart = snake.get(i);
      graphicsContext2D.fillRect(bodyPart.x() * cellWidth, bodyPart.y() * cellHeight, cellWidth, cellHeight);
    }
  }

  private static void drawFood(GraphicsContext graphicsContext2D,
                               Collection<GridPoint> foods,
                               double cellWidth,
                               double cellHeight) {
    graphicsContext2D.setFill(Color.YELLOWGREEN);
    for (GridPoint foodPosition : foods) {
      graphicsContext2D.fillOval(foodPosition.x() * cellWidth, foodPosition.y() * cellHeight, cellWidth, cellHeight);
    }
  }
}
