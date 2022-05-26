package com.example.cookingrecipe.model;

public class FavoriteFood {
    int key;
    int idRecipe;

    public FavoriteFood() {
    }

    public FavoriteFood(int key, int idRecipe) {
        this.key = key;
        this.idRecipe = idRecipe;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }
}
