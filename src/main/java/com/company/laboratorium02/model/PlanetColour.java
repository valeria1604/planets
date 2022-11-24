/**
 * Nazwa: Planety
 * Autor: Valeriia Tykhoniuk (266319)
 * Data utworzenia: 11.10.2022
 */

package com.company.laboratorium02.model;

public enum PlanetColour {
    UNKNOWN("-------"),
    RED("Red"),
    ORANGE("Orange"),
    YELLOW("Yellow"),
    BLUE("Blue"),
    VIOLET("Violet");




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