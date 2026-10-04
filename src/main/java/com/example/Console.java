package com.example;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Console implements NintendoItem {
    private String name; 
    private String date; 
    private String processor; 
    private String color; 
    private long unitsSold; 
    private String image;

    @JsonCreator
    public Console(
        @JsonProperty("name") String name, 
        @JsonProperty("date") String date, 
        @JsonProperty("procesador") String processor, 
        @JsonProperty("color") String color, 
        @JsonProperty("units_sold") long unitsSold, 
        @JsonProperty("image") String image
    ) {
        this.name = name; 
        this.date = date;
        this.processor = processor; 
        this.color = color; 
        this.unitsSold = unitsSold; 
        this.image = image; 
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getProcessor() {
        return  processor;
    }

    public String getColor() {
        return color; 
    }

    public long getUnitsSold() {
        return unitsSold;
    }

    public String getImage() {
        return image;
    }
}
