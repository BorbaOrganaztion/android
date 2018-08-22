package com.example.games4all;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Game {
   private String Title,Description, Price, Console, ImgUrl;
    //private double gameOverallRate;



    public Game(){}

    public Game(String title, String description, String price, String console, String imgUrl) {
        Title = title;
        Description = description;
        Price = price;
        Console = console;
        ImgUrl = imgUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getConsole() {
        return Console;
    }

    public void setConsole(String console) {
        Console = console;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }
}
