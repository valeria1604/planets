package com.company.laboratorium02.model;

public enum PlanetColour {
    UNKNOWN("-------"),
    BLUE("Blue"),
    VIOLET("Violet"),
    RED("Red"),
    YELLOW("Yellow"),
    ORANGE("Orange");

    private String planetColour;

    PlanetColour(String planetColour) {
        this.planetColour = planetColour;
    }

    public String getPlanetColour() {
        return planetColour;
    }

    @Override
    public String toString() {
        return planetColour;
    }
}