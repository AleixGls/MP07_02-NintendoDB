package com.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Character implements NintendoItem {
    private String name; 
    private String image; 
    private String color; 
    private String game; 
    
    @JsonCreator
    public Character(
        @JsonProperty("name") String name, 
        @JsonProperty("image") String image, 
        @JsonProperty("color") String color, 
        @JsonProperty("game") String game
    ) {
        this.name = name; 
        this.image = image; 
        this.color = color;
        this.game = game;
    } 
    
    public String getName() {
        return name;
    } 
    
    public String getImage() { 
        return image; 
    }
    
    public String getColor() { 
        return color; 
    } 

    public String getGame() { 
        return game; 
    }
}
