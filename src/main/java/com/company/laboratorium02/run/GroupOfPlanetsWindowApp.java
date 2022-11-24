/**
 * Nazwa: Planety
 * Autor: Valeriia Tykhoniuk (266319)
 * Data utworzenia: 11.10.2022
 */

package com.company.laboratorium02.run;


import com.company.laboratorium02.model.Planet;
import com.company.laboratorium02.model.PlanetColour;
import com.company.laboratorium02.model.PlanetException;
import com.company.laboratorium02.ui.PlanetWindowDialog;
import com.company.laboratorium02.ui.ViewGroupOfPlanets;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.Serial;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

public class GroupOfPlanetsWindowApp extends JDialog implements ActionListener {
    @Serial
    private static final long serialVersionUID = 1L;
    public static final String AUTHOR = "Nazwa: Planety. Autor: Valeriia Tykhoniuk (266319). Data utworzenia: 15.11.2022";

    public static void main(String[] args) {
        Vector<Planet> group = new Vector<Planet>();
        try {
            Planet availablePlanet;
            availablePlanet = new Planet("Earth");
            availablePlanet.setColour(PlanetColour.BLUE);
            availablePlanet.setMass(60);
            availablePlanet.setRadius(780);
            availablePlanet.setSatellitesCount(1);
            group.add(availablePlanet);

            availablePlanet = new Planet("Mars");
            availablePlanet.setColour(PlanetColour.RED);
            availablePlanet.setMass(70);
            availablePlanet.setRadius(530);
            availablePlanet.setSatellitesCount(80);
            group.add(availablePlanet);

            availablePlanet = new Planet("Saturn");
            availablePlanet.setColour(PlanetColour.ORANGE);
            availablePlanet.setMass(54);
            availablePlanet.setRadius(348);
            availablePlanet.setSatellitesCount(2);
            group.add(availablePlanet);

        } catch (PlanetException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Unexpected error", JOptionPane.ERROR_MESSAGE);
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GroupOfPlanetsWindowApp(null, group);
            }
        });
    }


    private final Vector<Planet> currentGroupOfPlanets;
    JButton newPlanetButton = new JButton("New planet");
    JButton deletePlanetButton = new JButton("Delete planet");
    JButton editPlanetButton = new JButton("Change planet");
    JButton loadFromDocumentButton = new JButton("Read data from document");
    JButton saveToDocumentButton = new JButton("Write data to the document");
    JButton infoButton = new JButton("About author");
    JButton buttonSortMass = new JButton("Sort by the mass");
    JButton buttonSortColour = new JButton("Sort by the colour");

    ViewGroupOfPlanets viewList;

    public GroupOfPlanetsWindowApp(Window parent, Vector<Planet> group) {

        super(parent, ModalityType.DOCUMENT_MODAL);

        setTitle("Changing the group");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setSize(450, 450);
        setResizable(false);
        if (parent != null) {
            Point location = parent.getLocation();
            location.translate(100, 100);
            setLocation(location);
        } else setLocationRelativeTo(null);

        currentGroupOfPlanets = group;

        viewList = new ViewGroupOfPlanets(currentGroupOfPlanets, 400, 250);
        viewList.refreshView();


        newPlanetButton.addActionListener(this);
        deletePlanetButton.addActionListener(this);
        editPlanetButton.addActionListener(this);
        loadFromDocumentButton.addActionListener(this);
        saveToDocumentButton.addActionListener(this);
        infoButton.addActionListener(this);

        buttonSortMass.addActionListener(this);
        buttonSortColour.addActionListener(this);

        JPanel panel = new JPanel();

        panel.add(viewList);

        panel.add(newPlanetButton);
        panel.add(deletePlanetButton);
        panel.add(editPlanetButton);
        panel.add(loadFromDocumentButton);
        panel.add(saveToDocumentButton);
        panel.add(infoButton);

        panel.add(buttonSortMass);
        panel.add(buttonSortColour);

        setContentPane(panel);

        panel.setBackground(new java.awt.Color(6, 6, 28));

        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        try {
            if (source == newPlanetButton) {
                Planet newPlanet = PlanetWindowDialog.createPlanet(this);
                if (newPlanet != null) currentGroupOfPlanets.add(newPlanet);
            }

            if (source == editPlanetButton) {
                int index = viewList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<Planet> iterator = currentGroupOfPlanets.iterator();
                    while (index-- > 0)
                        iterator.next();
                    PlanetWindowDialog.changePlanet(this, iterator.next());
                }
            }

            if (source == deletePlanetButton) {
                int index = viewList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<Planet> iterator = currentGroupOfPlanets.iterator();
                    while (index-- >= 0)
                        iterator.next();
                    iterator.remove();
                }
            }

            if (source == loadFromDocumentButton) {
                JFileChooser fc = new JFileChooser();
                int i = fc.showOpenDialog(this);
                if (i == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    Planet planet = Planet.readFromFile(f);
                    currentGroupOfPlanets.add(planet);
                }
            }

            if (source == saveToDocumentButton) {
                int index = viewList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<Planet> iterator = currentGroupOfPlanets.iterator();
                    while (index-- > 0)
                        iterator.next();
                    Planet planet = iterator.next();

                    String fileName = JOptionPane.showInputDialog("Write the name of document");
                    if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku.
                    Planet.writeToTheDocument(fileName, planet);
                }
            }

            if (source == buttonSortMass) {
                currentGroupOfPlanets.sort(new Comparator<Planet>() {
                    @Override
                    public int compare(Planet o1, Planet o2) {
                        if (o1.getMass() < o2.getMass())
                            return -1;
                        if (o1.getMass() > o2.getMass())
                            return 1;
                        return 0;
                    }
                });
            }

            if (source == buttonSortColour) {
                currentGroupOfPlanets.sort(new Comparator<Planet>() {

                    @Override
                    public int compare(Planet o1, Planet o2) {
                        return o1.getColour().toString().compareTo(o2.getColour().toString());
                    }
                });
            }

            if (source == infoButton) {
                JOptionPane.showMessageDialog(this, AUTHOR);
            }

        } catch (PlanetException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Unexpected error", JOptionPane.ERROR_MESSAGE);
        }

        viewList.refreshView();
    }
}

