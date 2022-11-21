package com.company.laboratorium02.model;

import java.io.*;

public class Planet {
    public String name;
    private PlanetColour colour;
    private int mass;
    private float radius;
    private int satellitesCount;

    public Planet(String name) {
        this.name = name;
    }

    public Planet(String name, int mass, float radius, int satellitesCount) throws PlanetException {
        setName(name);
        colour = PlanetColour.UNKNOWN;
        setMass(mass);
        setRadius(radius);
        setSatellitesCount(satellitesCount);
    }

    public static Planet readFromFile(BufferedReader reader) throws PlanetException {
        try {
            String line = reader.readLine();
            String[] txt = line.split(";");
            Planet planet = new Planet(txt[0]);
            planet.setMass(Integer.parseInt(txt[1]));
            planet.setRadius(Float.parseFloat(txt[2]));
            planet.setSatellitesCount(Integer.parseInt(txt[3]));
            planet.setColour(txt[4]);
            return planet;
        } catch (IOException e) {
            throw new PlanetException("Reading wasn't successful");
        }
    }

    public static Planet readFromFile(String file_name) throws PlanetException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
            return Planet.readFromFile(reader);
        } catch (FileNotFoundException e) {
            throw new PlanetException("Document wasn't found" + file_name);
        } catch (IOException e) {
            throw new PlanetException("Reading wasn't succesful");
        }
    }

    public static void printToFile(PrintWriter writer, Planet planet) {
        writer.println(planet.name + ";" + planet.mass +
                ";" + planet.radius + ";" + planet.satellitesCount + ";" + planet.colour);
    }

    public static void writeToTheDocument(String fileName, Planet planet) throws PlanetException {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            printToFile(writer, planet);
        } catch (FileNotFoundException e) {
            throw new PlanetException("Document wasn't found " + fileName);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws PlanetException {
        if (name == null || name.isBlank()) {
            throw new PlanetException("Name of planet is empty.");
        }
        this.name = name;

    }

    public PlanetColour getColour() {
        return colour;
    }

    public void setColour(PlanetColour colour) {
        this.colour = colour;
    }

    public void setColour(String inputColor) throws PlanetException {
        if (inputColor == null || inputColor.isBlank()) {
            this.colour = PlanetColour.UNKNOWN;
            return;
        }
        for (PlanetColour colour : PlanetColour.values()) {
            if (colour.getPlanetColour().equalsIgnoreCase(inputColor)) {
                this.colour = colour;
                return;
            }
        }
        throw new PlanetException("No such colour.");
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) throws PlanetException {
        if (mass <= 0) {
            throw new PlanetException("Mass of planet needs to be more than 0.");
        }
        this.mass = mass;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) throws PlanetException {
        if (radius <= 0) {
            throw new PlanetException("Radius of planet needs to be more than 0.");
        }
        this.radius = radius;
    }

    public int getSatellitesCount() {
        return satellitesCount;
    }

    public void setSatellitesCount(int satellitesCount) throws PlanetException {
        if (satellitesCount <= 0) {
            throw new PlanetException("Count of satellites needs to be more than 0.");
        }
        this.satellitesCount = satellitesCount;
    }

    @Override
    public String toString() {
        return "Planet{" +
                "name='" + name + '\'' +
                ", colour=" + colour +
                ", mass=" + mass +
                ", radius=" + radius +
                ", satellitesCount=" + satellitesCount +
                '}';
    }
}
