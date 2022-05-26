package com.example.cookingrecipe.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Recipe implements Serializable {
    private int  img, idRecipe, numRate;
    private String foodName, author, tag;
    private float rating;
    private String date;
    private String description,material,practice,urlImage;


    public Recipe() {
    }


    public Recipe(int img, int idRecipe, String foodName, String author, String tag, float rating, String date) {
        this.img = img;
        this.idRecipe = idRecipe;
        this.foodName = foodName;
        this.author = author;
        this.tag = tag;
        this.rating = rating;
        this.date = date;
    }

    public Recipe(int idRecipe, int numRate, String foodName, String author, String tag, float rating, String date, String description, String material, String practice, String urlImage) {
        this.idRecipe = idRecipe;
        this.numRate = numRate;
        this.foodName = foodName;
        this.author = author;
        this.tag = tag;
        this.rating = rating;
        this.date = date;
        this.description = description;
        this.material = material;
        this.practice = practice;
        this.urlImage = urlImage;
    }

    public int getNumRate() {
        return numRate;
    }

    public void setNumRate(int numRate) {
        this.numRate = numRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }




    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "img=" + img +
                ", idRecipe=" + idRecipe +
                ", numRate=" + numRate +
                ", foodName='" + foodName + '\'' +
                ", author='" + author + '\'' +
                ", tag='" + tag + '\'' +
                ", rating='" + rating + '\'' +
                ", date='" + date + '\'' +
                ", description='" + description + '\'' +
                ", material='" + material + '\'' +
                ", practice='" + practice + '\'' +
                ", urlImage='" + urlImage + '\'' +
                '}';
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("idRecipe",idRecipe);
        result.put("numRate",numRate);
        result.put("foodName",foodName);
        result.put("author",author);
        result.put("tag",tag);
        result.put("rating",rating);
        result.put("date",date);
        result.put("description",description);
        result.put("material",material);
        result.put("practice",practice);
        result.put("urlImage",urlImage);




        return result;
    }
}