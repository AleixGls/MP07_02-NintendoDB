package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReader {

    private final ObjectMapper objectMapper;

    public JsonReader() { 
        objectMapper = new ObjectMapper(); 
    }

    // Pasar datos de json a lista de clases Game
    public Game[] leerGames() throws Exception {
        return objectMapper.readValue(
            getClass().getResourceAsStream("/assets/games.json"),
            Game[].class
        );
    }

    // Pasar datos de json a lista de clases Character
    public Character[] leerCharacters() throws Exception {
        return objectMapper.readValue(
            getClass().getResourceAsStream("/assets/characters.json"),
            Character[].class
        );
    }

    // Pasar datos de json a lista de clases Console
    public Console[] leerConsoles() throws Exception {
        return objectMapper.readValue(
            getClass().getResourceAsStream("/assets/consoles.json"),
            Console[].class
        );
    }
}
