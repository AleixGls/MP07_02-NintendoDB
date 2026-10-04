package com.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Game implements NintendoItem {
    private String name; 
    private int year; 
    private String type; 
    private String plot; 
    private String image;

    @JsonCreator
    public Game(
        @JsonProperty("name") String name, 
        @JsonProperty("year") int year, 
        @JsonProperty("type") String type, 
        @JsonProperty("plot") String plot, 
        @JsonProperty("image") String image
    ) {
        this.name = name; 
        this.year = year; 
        this.type = type; 
        this.plot = plot; 
        this.image = image;
    }

    public String getName() {
        return name;
    } 
    
    public int getYear() {
        return year; 
    } 

    public String getType() {
        return type; 
    } 
    
    public String getPlot() {
        return plot;
    } 
    
    public String getImage() {
        return image;
    }
}
