/**
 * Nazwa: Planety
 * Autor: Valeriia Tykhoniuk (266319)
 * Data utworzenia: 11.10.2022
 */

package com.company.laboratorium01.run;

import com.company.laboratorium01.model.Planet;
import com.company.laboratorium01.model.PlanetColour;
import com.company.laboratorium01.model.PlanetException;
import com.company.laboratorium01.ui.ConsoleUserDialog;

import java.util.Arrays;

public class PlanetConsoleApp {
    public static final String MENU = """
            1) New planet\s
            2) Delete planet
            3) Change information of the planet
            4) Read data from document
            5) Write data to the document
            6) End program""";

    public static final String CHANGE_MENU = """
            1) Choose name\s
            2) Choose mass
            3) Choose radius
            4) Choose the count of satellites
            5) Choose the colour of planet
            6) None
            """;

    public static final String AUTHOR = "Nazwa: Planety. Autor: Valeriia Tykhoniuk (266319). Data utworzenia: 11.10.2022";

    private static Planet currentPlanet = null;

    private static ConsoleUserDialog UI = new ConsoleUserDialog();

    public static void main(String[] args) {
        new PlanetConsoleApp().appLoop();
    }

    private void appLoop() {
        UI.printMessage(AUTHOR);

        while (true) {
            UI.clearConsole();
            showCurrentPlanet();
            try {
                UI.printMessage(MENU);
                String fileName;
                switch (UI.enterInt("What do you choose? -> ")) {
                    case 1:
                        currentPlanet = createNewPlanet();
                        break;
                    case 2:
                        currentPlanet = null;
                        UI.printMessage("Planet has been deleted");
                        break;
                    case 3:
                        if (currentPlanet == null) throw new PlanetException("Planet hasn't been chosen");
                        changePlanetData(currentPlanet);
                        break;
                    case 4:
                        fileName = UI.enterString("Name of the file: ");
                        currentPlanet = Planet.readFromFile(fileName);
                        UI.printInfoMessage("The information was given: " + fileName);
                        break;
                    case 5:
                        fileName = UI.enterString("Name of the file: ");
                        Planet.writeToTheDocument(fileName, currentPlanet);
                        UI.printInfoMessage("The information was written to the: " + fileName);
                        break;
                    case 6:
                        UI.printInfoMessage("\nThanks for using this program");
                        System.exit(0);
                }
            } catch (PlanetException e) {
                UI.printErrorMessage(e.getMessage());
            }
        }
    }

    private void showCurrentPlanet() {
        showPlanet(currentPlanet);
    }

    private static void showPlanet(Planet planet) {
        if (planet != null) {
            UI.printMessage("Current Planet: ");
            UI.printMessage(planet.toString());
        } else
            UI.printMessage("No planet");
    }

    public static Planet createNewPlanet() {
        String name = UI.enterString("Enter the planet name: ");
        int mass = UI.enterInt("Enter the planet mass: ");
        float radius = UI.enterFloat("Enter the planet radius: ");
        int satellites = UI.enterInt("Enter the count of satellites: ");
        UI.printMessage("Available colours:" + Arrays.deepToString(PlanetColour.values()));
        String planetColour = UI.enterString("Write the colour: ");
        Planet planet = null;
        try {
            planet = new Planet(name, mass, radius, satellites);
            planet.setColour(planetColour);

        } catch (PlanetException e) {
            UI.printErrorMessage(e.getMessage());
        }
        return planet;
    }

    static void changePlanetData(Planet planet) {
        while (true) {
            UI.clearConsole();
            showPlanet(planet);

            try {
                switch (UI.enterInt(CHANGE_MENU + "==>> ")) {
                    case 1:
                        planet.setName(UI.enterString("Write the new name of planet: "));
                        break;
                    case 2:
                        planet.setMass(UI.enterInt("Write the new mass of planet: "));
                        break;
                    case 3:
                        planet.setRadius(UI.enterFloat("Write the new radius of planet: "));
                        break;
                    case 4:
                        planet.setSatellitesCount(UI.enterInt("Write the new count of satellites of planet: "));
                    case 5:
                        UI.printMessage("Available colours:" + Arrays.deepToString(PlanetColour.values()));
                        planet.setColour(UI.enterString("Write the colour: "));
                        break;
                    case 6:
                        return;
                }
            } catch (PlanetException e) {
                UI.printErrorMessage(e.getMessage());
            }
        }
    }
}
