package com.example.snake.model;

import com.example.snake.utils.IOUtils;
import javafx.scene.image.Image;

public enum FoodType {
  PREY("/Sprites/Food/prey-1.png"),
  FOOD_BOX("/Sprites/Food/FoodBox.png"),
  FOOD_CAKE("/Sprites/Food/FoodCake.png"),
  FOOD_CANDY("/Sprites/Food/FoodLolipop.png"),
  FOOD_MUSHROOM("/Sprites/Food/FoodMushroom.png");

  private Image image;

  private FoodType(String imageUrl) {
    image = IOUtils.loadImage(imageUrl);
  }

  public Image getImage() {
    return image;
  }
}
